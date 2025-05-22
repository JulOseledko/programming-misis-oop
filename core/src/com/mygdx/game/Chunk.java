package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Chunk {
    public static final int CHUNK_SIZE = 8; // Размер чанка в клетках
    private Texture textureGrass;
    private Texture textureWalls;
    private TextureRegion[] regions;
    private byte[][] dataLayer;
    private byte[][] typeLayer;
    private int x, y; // Координаты чанка на карте

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
        dataLayer = new byte[CHUNK_SIZE][CHUNK_SIZE];
        typeLayer = new byte[CHUNK_SIZE][CHUNK_SIZE];
        for (int i = 0; i < 15; i++) {
            int cellX = MathUtils.random(0, CHUNK_SIZE - 1);
            int cellY = MathUtils.random(0, CHUNK_SIZE - 1);
            dataLayer[cellX][cellY] = 1;
            typeLayer[cellX][cellY] = (byte) MathUtils.random(0, 3);
        }
        textureGrass = new Texture("Grass.png");
        textureWalls = new Texture("Walls.png");
        regions = new TextureRegion(textureWalls).split(100, 100)[0];
    }

    public boolean isCellPassable(int cellX, int cellY) {
        return dataLayer[cellX % CHUNK_SIZE][cellY % CHUNK_SIZE] == 0;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                batch.draw(textureGrass, (x * CHUNK_SIZE + i) * 80, (y * CHUNK_SIZE + j) * 80);
            }
        }
        for (int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                if (dataLayer[i][j] == 1) {
                    batch.draw(regions[typeLayer[i][j]], (x * CHUNK_SIZE + i) * 80 - 10, (y * CHUNK_SIZE + j) * 80 - 10);
                }
            }
        }
    }
}
