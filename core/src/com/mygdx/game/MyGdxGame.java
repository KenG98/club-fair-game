package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private MainCharacter mainchar;
    private int charRotation = 0;
    private boolean upKey, downKey, rightKey, leftKey, currentlyWalking;
    private ArrayList<BlueArrowEnemy> blueEnemies = new ArrayList<BlueArrowEnemy>();
    private int waveNumber = 10;
    private boolean gameOver = false;


    public MyGdxGame() {
    }

    @Override
    public void create() {
        init();
    }

    public void init(){
        batch = new SpriteBatch();
        mainchar = new MainCharacter();
        for(int i = 0; i < waveNumber; i++){
            blueEnemies.add(new BlueArrowEnemy());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        {
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
        }

        mainchar.draw(charRotation, currentlyWalking);

        allEnemies();

        try {
            Thread.sleep((long)(1000/30-Gdx.graphics.getDeltaTime()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void allEnemies(){
        //draws, moves, checks for collisions all blue arrow enemies
        int numEnemies = blueEnemies.size();
        int mainX = mainchar.getX();
        int mainY = mainchar.getY();
        for(int i = 0; i < numEnemies; i++){
            blueEnemies.get(i).follow(mainX,mainY);
            blueEnemies.get(i).draw();
            if(blueEnemies.get(i).didCapture()){
                gameOver = true;
                Gdx.app.exit();
            }
        }
    }
}
