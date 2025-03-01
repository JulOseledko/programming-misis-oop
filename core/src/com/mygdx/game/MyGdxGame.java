package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    /*
    === Идеи ===
    1. + Движение по пикселям
    2. Преграды
    3. Анимация
    4. Пускание снарядов
    5. Хаотичное движение для монстра
    6. + Преследование монстром героя
    7. Аптечки, Монеты, Зелья (все что можно поднять)
    8. Параметры героям/монстрам(ХП, крит шанс, скорость)
    9. Система уровней игры
    10. Опыт герою
    11. Оружие
    12. Финальный босс
    13. +- Драка с монстрами
    14. + Полоска ХП
     */

    private SpriteBatch batch;
    private Hero hero;
    private Monster monster;

    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        hero = new Hero();
        monster = new Monster(this);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        hero.render(batch);
        monster.render(batch);
        batch.end();
    }

    public void update(float dt) {
        hero.update(dt);
        monster.update(dt);
        float dst = (float) Math.sqrt((hero.getX() - monster.getX()) * (hero.getX() - monster.getX()) + (hero.getY() - monster.getY()) * (hero.getY() - monster.getY()));
        if (dst < 40.0f) {
            hero.takeDamage(dt * 10.0f);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
