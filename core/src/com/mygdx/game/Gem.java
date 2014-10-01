package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by kengarber on 10/1/14.
 */
public class Gem {
    private int x,y;
    private boolean isGotten;
    private SpriteBatch batch;
    private Sprite gemSprite;


    public Gem(){
        batch = new SpriteBatch();
        gemSprite = new Sprite((new Texture(Gdx.files.internal("data/gem2.png"))));
        //place the gem on creation
        Random randGen = new Random();
        int locationToPlace = randGen.nextInt(3);
        switch (locationToPlace){
            case 0:
                x = 40;
                y = 40;
                break;
            case 1:
                x = 40;
                y = 440;
                break;
            case 2:
                x = 600;
                y = 40;
                break;
            case 3:
                x = 600;
                y = 440;
        }
    }

    public void checkIfGotten(int mainX, int mainY){
        if (Math.abs(x-mainX) < 32 && Math.abs(y-mainY) < 32) isGotten = true;
    }

    public boolean gemIsGotten(int mainX, int mainY){
        checkIfGotten(mainX, mainY);
        return isGotten;
    }

    public void draw(){
        batch.begin();
        gemSprite.setPosition(x, y);
        gemSprite.draw(batch);
        batch.end();
    }
}
