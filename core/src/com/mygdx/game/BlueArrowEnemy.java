package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by kengarber on 9/30/14.
 */
public class BlueArrowEnemy {
    private float x, y;
    private Sprite theEnemy;
    private float horizontalMovement = 7f;
    private float diagonalMovement = 5f;
    private int rotation;
    private SpriteBatch batch;
    private int distanceFromMainX;
    private int distanceFromMainY;
    public boolean capturedMain = false;
    //
    boolean mainIsAbove = false;
    boolean mainIsToRight = false;
    boolean moveHorizontal = false;
    boolean moveVertical = false;
    boolean moveDiagonal = false;
    double slopeBetween = 0;

    public BlueArrowEnemy() {
        batch = new SpriteBatch();
        theEnemy = new Sprite(new Texture(Gdx.files.internal("data/triangleEnemy.png")));
        //set random x
        Random randGen = new Random();
        boolean topBar = randGen.nextBoolean(); //renders enemy at top bar or side bars
        if(topBar){
            x = randGen.nextInt(660) - 10;
            boolean upSide = randGen.nextBoolean(); //upper or lower bar
            y = randGen.nextInt(20) - 10;
            if(upSide) y += 480;
        }
        else {
            y = randGen.nextInt(500) - 10;
            boolean rightSide = randGen.nextBoolean();
            x = randGen.nextInt(20) - 10;
            if(rightSide) x += 640;
        }

        theEnemy.setPosition(x, y);
    }

    public void follow(int mainX, int mainY) {
        //follow code (there are examples elsewhere)
        distanceFromMainX = (int) (x - mainX);
        if (distanceFromMainX == 0) distanceFromMainX = 1;
        distanceFromMainY = (int) (y - mainY);
        slopeBetween = distanceFromMainY / distanceFromMainX;
        mainIsAbove = false;
        mainIsToRight = false;
        moveHorizontal = false;
        moveVertical = false;
        moveDiagonal = false;

        if (distanceFromMainY < 0) {
            mainIsAbove = true;
        }
        if (distanceFromMainX < 0) {
            mainIsToRight = true;
        }
        if (slopeBetween > -0.5 && slopeBetween < 0.5) {
            moveHorizontal = true;
        } else if (slopeBetween > 2 || slopeBetween < -2) {
            moveVertical = true;
        } else {
            moveDiagonal = true;
        }
        if (moveHorizontal) {
            if (mainIsToRight) {
                x += horizontalMovement;
                rotation = 90;
            } else {
                x -= horizontalMovement;
                rotation = 270;
            }
        } else if (moveVertical) {
            if (mainIsAbove) {
                y += horizontalMovement;
                rotation = 180;
            } else {
                y -= horizontalMovement;
                rotation = 0;
            }
        } else if (moveDiagonal) {
            if (mainIsAbove) {
                y += diagonalMovement;
                if(mainIsToRight) rotation = 135;
                else rotation = 225;
            }

            else {
                y -= diagonalMovement;
                if(mainIsToRight) rotation = 45;
                else rotation = 315;
            }

            if (mainIsToRight) {
                x += diagonalMovement;
            } else {
                x -= diagonalMovement;
            }
        }

        if (Math.abs(distanceFromMainY) < 20 && Math.abs(distanceFromMainX) < 20) capturedMain = true;


    }

    public boolean didCapture(){
        return capturedMain;
    }

    public void draw() {
        batch.begin();
        theEnemy.setPosition(x, y);
        theEnemy.setRotation(rotation);
        theEnemy.draw(batch);
        batch.end();
    }
}
