package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by kengarber on 9/29/14.
 */
public class MainCharacter {
    private int x, y;
    private SpriteBatch batch;
    private Texture walkSheet;
    private Animation mainWalking;
    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    private float stateTime;
    private Sprite walkSprite, standSprite;
    private int horizontalMovement = 9;
    private int diagonalMovement = 6;
    private boolean outOfBounds = false;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MainCharacter() {
        //all the setup
        x = 300;
        y = 200;
        batch = new SpriteBatch();
        //generate movement (animation)
        walkSheet = new Texture(Gdx.files.internal("data/walksheet3.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 4, walkSheet.getHeight());
        walkFrames = new TextureRegion[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            walkFrames[index++] = tmp[0][j];
        }
        mainWalking = new Animation(0.10f, walkFrames);
        stateTime = 0f;
        //generate standing image (sprite)
        standSprite = new Sprite(walkFrames[1]);
    }

    public void draw(int rotation, boolean walking) {
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        if (walking) {
            currentFrame = mainWalking.getKeyFrame(stateTime, true);
            walkSprite = new Sprite(currentFrame);
            walkSprite.setRotation(rotation);
            walkSprite.setPosition(x, y);
            walkSprite.draw(batch);
            //change position
            switch (rotation) {
                case 0:
                    y -= horizontalMovement;
                    break;
                case 90:
                    x += horizontalMovement;
                    break;
                case 180:
                    y += horizontalMovement;
                    break;
                case 270:
                    x -= horizontalMovement;
                    break;
                case 45:
                    y -= diagonalMovement;
                    x += diagonalMovement;
                    break;
                case 135:
                    y += diagonalMovement;
                    x += diagonalMovement;
                    break;
                case 225:
                    y += diagonalMovement;
                    x -= diagonalMovement;
                    break;
                case 315:
                    y -= diagonalMovement;
                    x -= diagonalMovement;
                    break;
            }
        } else {
            standSprite.setRotation(rotation);
            standSprite.setPosition(x, y);
            standSprite.draw(batch);
        }
        batch.end();

        if(x > 700 || x < -40 || y > 520 || y < -40) outOfBounds = true;
    }

}
