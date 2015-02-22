package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kengarber on 10/10/14.
 */
public class Projectile {
    float x,y;
    int direction;
    Sprite laser;
    SpriteBatch batch;

    public Projectile(int direction) {
        batch = new SpriteBatch();
        this.direction = direction;
        laser = new Sprite(new Texture(Gdx.files.internal("laserbeam.png")));
    }

    public void moveLaser() {
        laser.setPosition(x,y);
        batch.begin();
        laser.draw(batch);
        batch.begin();
    }
}
