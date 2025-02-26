package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Monster {
    private Texture texture;
    private float x;
    private float y;
    private float speed;
    private float activityRadius;

    private MyGdxGame game;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Monster(MyGdxGame game) {
        this.texture = new Texture("Skeleton.png");
        this.x = 400.0f;
        this.y = 400.0f;
        this.speed = 40.0f;
        this.activityRadius = 200.0f;
        this.game = game;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void update(float dt) {
        float dst = (float) Math.sqrt((game.getHero().getX() - this.x) * (game.getHero().getX() - this.x) + (game.getHero().getY() - this.y) * (game.getHero().getY() - this.y));
        if (dst <= activityRadius) {
            if (x < game.getHero().getX()) {
                x += speed * dt;
            }
            if (x > game.getHero().getX()) {
                x -= speed * dt;
            }
            if (y < game.getHero().getY()) {
                y += speed * dt;
            }
            if (y > game.getHero().getY()) {
                y -= speed * dt;
            }
        } else {
            x += speed * dt;
            if (x > 1280.0f) {
                x = 0.0f;
            }
        }
    }
}
