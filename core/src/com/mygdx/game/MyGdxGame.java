package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private MainCharacter mainchar;
    private int charRotation = 0;
    private boolean upKey, downKey, rightKey, leftKey, currentlyWalking;
    private ArrayList<BlueArrowEnemy> blueEnemies = new ArrayList<BlueArrowEnemy>();
    public int waveNumber = 1;
    private boolean gameOver = false;
    private Gem theGem;
    private Texture instructions;
    private int gameStage = 0; //0)instructions 1)gameplay 2)post death
    private long startTime;
    private TimeUtils timeutil;
    private long totalTime = 0;
    private BitmapFont finalStats;
    private String finalString;
    private String name;
    private int gemsGotten;

    public MyGdxGame() {
    }

    @Override
    public void create() {
        init();
    }

    public void init() {
        timeutil = new TimeUtils();
        batch = new SpriteBatch();
        mainchar = new MainCharacter(waveNumber);
        for (int i = 0; i < waveNumber; i++) {
            blueEnemies.add(new BlueArrowEnemy());
        }
        theGem = new Gem();
        instructions = new Texture(Gdx.files.internal("data/instructions.png"));
        finalStats = new BitmapFont();
        finalStats.setColor(Color.BLACK);
        finalStats.setScale(3);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (gameStage){
            case 0:
                batch.begin();
                batch.draw(instructions,0,0);
                batch.end();
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)||Gdx.input.isKeyPressed(Input.Keys.LEFT)||Gdx.input.isKeyPressed(Input.Keys.UP)||Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                    gameStage = 1;
                    startTime = timeutil.millis();
                }
                break;
            case 1:
                rightKey = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
                leftKey = Gdx.input.isKeyPressed(Input.Keys.LEFT);
                upKey = Gdx.input.isKeyPressed(Input.Keys.UP);
                downKey = Gdx.input.isKeyPressed(Input.Keys.DOWN);
                if (rightKey || leftKey || upKey || downKey) currentlyWalking = true;
                else currentlyWalking = false;
                if (upKey && !(downKey || rightKey || leftKey)) charRotation = 180; //walking up
                else if (downKey && !(upKey || rightKey || leftKey)) charRotation = 0; //walking down
                else if (rightKey && !(downKey || upKey || leftKey)) charRotation = 90; //walking right
                else if (leftKey && !(downKey || rightKey || upKey)) charRotation = 270; //walking left
                else if ((upKey && rightKey) && !(leftKey || downKey)) charRotation = 135; //walking up + right
                else if ((downKey && rightKey) && !(leftKey || upKey)) charRotation = 45; //walking down + right
                else if ((upKey && leftKey) && !(rightKey || downKey)) charRotation = 225; //walking up + left
                else if ((downKey && leftKey) && !(rightKey || upKey)) charRotation = 315; //walking down + left


                mainchar.draw(charRotation, currentlyWalking);



                theGem.draw();

                boolean gotGem = theGem.gemIsGotten(mainchar.getX(), mainchar.getY());
                if (gotGem) {
                    waveNumber++;
                    System.out.println("Starting wave " + waveNumber + "!");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    theGem = new Gem();
                    blueEnemies.add(new BlueArrowEnemy());
                    blueEnemies.clear();
                    mainchar = new MainCharacter(waveNumber);
                    for (int i = 0; i < waveNumber; i++) {
                        blueEnemies.add(new BlueArrowEnemy());
                    }
                }

                allEnemies();

                try {
                    Thread.sleep((long) (1000 / 30 - Gdx.graphics.getDeltaTime()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                //add code for final screen here.
                batch.begin();
                finalStats.setScale(3);
                finalString = "YOU GOT: " + gemsGotten + " GEMS";
                finalStats.draw(batch, finalString,20,400);
                finalString = "IT TOOK " + totalTime + " SECONDS";
                finalStats.draw(batch, finalString, 20, 300);
                finalStats.setScale(1);
                finalStats.draw(batch, "To submit your score, type your name",20, 200);
                finalStats.draw(batch, "Press enter to continue",20, 20);
                finalStats.draw(batch,name,20,100);
                batch.end();

                if(Gdx.input.isKeyJustPressed(Input.Keys.A)) name += "A";
                if(Gdx.input.isKeyJustPressed(Input.Keys.B)) name += "B";
                if(Gdx.input.isKeyJustPressed(Input.Keys.C)) name += "C";
                if(Gdx.input.isKeyJustPressed(Input.Keys.D)) name += "D";
                if(Gdx.input.isKeyJustPressed(Input.Keys.E)) name += "E";
                if(Gdx.input.isKeyJustPressed(Input.Keys.F)) name += "F";
                if(Gdx.input.isKeyJustPressed(Input.Keys.G)) name += "G";
                if(Gdx.input.isKeyJustPressed(Input.Keys.H)) name += "H";
                if(Gdx.input.isKeyJustPressed(Input.Keys.I)) name += "I";
                if(Gdx.input.isKeyJustPressed(Input.Keys.J)) name += "J";
                if(Gdx.input.isKeyJustPressed(Input.Keys.K)) name += "K";
                if(Gdx.input.isKeyJustPressed(Input.Keys.L)) name += "L";
                if(Gdx.input.isKeyJustPressed(Input.Keys.M)) name += "M";
                if(Gdx.input.isKeyJustPressed(Input.Keys.N)) name += "N";
                if(Gdx.input.isKeyJustPressed(Input.Keys.O)) name += "O";
                if(Gdx.input.isKeyJustPressed(Input.Keys.P)) name += "P";
                if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) name += "Q";
                if(Gdx.input.isKeyJustPressed(Input.Keys.R)) name += "R";
                if(Gdx.input.isKeyJustPressed(Input.Keys.S)) name += "S";
                if(Gdx.input.isKeyJustPressed(Input.Keys.T)) name += "T";
                if(Gdx.input.isKeyJustPressed(Input.Keys.U)) name += "U";
                if(Gdx.input.isKeyJustPressed(Input.Keys.V)) name += "V";
                if(Gdx.input.isKeyJustPressed(Input.Keys.W)) name += "W";
                if(Gdx.input.isKeyJustPressed(Input.Keys.X)) name += "X";
                if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) name += "Y";
                if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) name += "Z";
                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) name += " ";
                if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) name = name.substring(0,name.length() - 1);


                if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    gameStage = 0;
                    //"gameStats.csv"

                    File file = new File("gameStats.csv");
                    // if file doesnt exists, then create it
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    FileWriter fw = null;
                    //BufferedWriter bw = null;
                    try {
                        //fw = new FileWriter(file.getAbsoluteFile());
                        //bw = new BufferedWriter(fw);
                        //bw.write(name + "," + (gemsGotten) + "," + totalTime);
                        //bw.close();

                        fw = new FileWriter(file,true);
                        fw.write(name + "," + (gemsGotten) + "," + totalTime + "\n");
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    try {
//                        PrintStream filewriter = new PrintStream("gameStats.csv");
//                        filewriter.println(name + "," + (gemsGotten) + "," + totalTime);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
        }


    }

    public void allEnemies() {
        //draws, moves, checks for collisions all blue arrow enemies
        int numEnemies = blueEnemies.size();
        int mainX = mainchar.getX();
        int mainY = mainchar.getY();
        for (int i = 0; i < numEnemies; i++) {
            blueEnemies.get(i).follow(mainX, mainY);
            blueEnemies.get(i).draw();
            if (blueEnemies.get(i).didCapture()) {
                gameOver = true;
                //Gdx.app.exit(); //instead of this, advance game stage to post-death stage
            }
        }
        if (gameOver){
            long endTime = timeutil.millis();
            totalTime = (endTime - startTime)/1000;
            //move this all to the "final screen"
            System.out.println("You got " + (waveNumber - 1) + " gems");
            System.out.println("It took you " + totalTime + " seconds");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            name = ""; //resets the name string



            gameOver = false;
            gameStage = 2;
            gemsGotten = waveNumber - 1; //store the number of gems to save later
            waveNumber = 1;
            blueEnemies.clear();
            blueEnemies.add(new BlueArrowEnemy());
            mainchar = new MainCharacter(1);
        }
    }
}
