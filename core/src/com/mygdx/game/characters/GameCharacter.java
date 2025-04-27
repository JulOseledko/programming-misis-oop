package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Map;
import com.mygdx.game.Weapon;

public abstract class GameCharacter {
    GameScreen gameScreen;

    Texture texture;
    Texture textureHp;
    Vector2 position;
    Vector2 direction;
    float speed;

    TextureRegion[] regions;
    float animationTimer;
    float secondsPerFrame;

    float hp, hpMax;

    float damageEffectTimer;
    float attackTimer;

    Weapon weapon;

    Vector2 temp;
    StringBuilder stringHelper;

    public boolean isAlive() {
        return hp > 0;
    }

    public Vector2 getPosition() {
        return position;
    }

    public abstract void update(float dt);

    public GameCharacter() {
        temp = new Vector2(0, 0);
        stringHelper = new StringBuilder();
    }

    public void render(SpriteBatch batch, BitmapFont font24) {
        if (damageEffectTimer > 0.0f) {
            batch.setColor(1, 1 - damageEffectTimer, 1 - damageEffectTimer, 1);
        }
        int frameIndex = (int) (animationTimer / secondsPerFrame) % regions.length;
        batch.draw(regions[frameIndex], position.x - 40, position.y - 40);
        batch.setColor(1, 1, 1, 1);

        batch.setColor(0, 0, 0, 1);
        batch.draw(textureHp, position.x - 42, position.y + 80 - 42 + (int) (Math.sin(animationTimer * 10) * 15 * damageEffectTimer), 84, 16);
        batch.setColor(1, 0, 0, 1);
        batch.draw(textureHp, position.x - 40, position.y + 80 - 40 + (int) (Math.sin(animationTimer * 10) * 15 * damageEffectTimer), 0, 0, hp / hpMax * 80, 12, 1, 1, 0, 0, 0, 80, 12, false, false);
        batch.setColor(1, 1, 1, 1);
        stringHelper.setLength(0);
        stringHelper.append((int) hp);
        font24.draw(batch, stringHelper, position.x - 40, position.y + 80 - 22 + (int) (Math.sin(animationTimer * 10) * 15 * damageEffectTimer), 80, 1, false);
    }

    public void checkScreenBounds() {
        if (position.x < 0) {
            position.x = Map.WORLD_WIDTH + position.x; // Зацикливание по X
        } else if (position.x > Map.WORLD_WIDTH) {
            position.x = position.x - Map.WORLD_WIDTH; // Зацикливание по X
        }

        if (position.y < 0) {
            position.y = Map.WORLD_HEIGHT + position.y; // Зацикливание по Y
        } else if (position.y > Map.WORLD_HEIGHT) {
            position.y = position.y - Map.WORLD_HEIGHT; // Зацикливание по Y
        }
    }


    public void takeDamage(float amount) {
        hp -= amount;
        damageEffectTimer += 0.5f;
        if (damageEffectTimer > 1.0f) {
            damageEffectTimer = 1.0f;
        }
    }

    public void moveForward(float dt) {
        Vector2 nextPosition = new Vector2(position).mulAdd(direction, speed * dt);

        if (gameScreen.getMap().isCellPassable(nextPosition)) {
            position.set(nextPosition);
        } else {
            // Попытка скорректировать движение (избегаем застревания)
            Vector2 tempX = new Vector2(nextPosition.x, position.y);
            Vector2 tempY = new Vector2(position.x, nextPosition.y);

            if (gameScreen.getMap().isCellPassable(tempX)) {
                position.set(tempX);
            } else if (gameScreen.getMap().isCellPassable(tempY)) {
                position.set(tempY);
            }
        }
        checkScreenBounds(); // Проверка границ после перемещения
    }
}
