package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Camera;

public class Map {
    public static final int CELLS_X = 100;
    public static final int CELLS_Y = 100;
    public static final int CELL_SIZE = 80;
    public static final int WORLD_WIDTH = CELLS_X * CELL_SIZE;
    public static final int WORLD_HEIGHT = CELLS_Y * CELL_SIZE;

    private Texture textureGrass;
    private Texture textureWalls;
    private TextureRegion[] regions;
    private byte[][] dataLayer;
    private byte[][] typeLayer;

    public Map() {
        dataLayer = new byte[CELLS_X][CELLS_Y];
        typeLayer = new byte[CELLS_X][CELLS_Y];
        for (int i = 0; i < 300; i++) {
            int cellX = MathUtils.random(0, CELLS_X - 1);
            int cellY = MathUtils.random(0, CELLS_Y - 1);
            dataLayer[cellX][cellY] = 1;
            typeLayer[cellX][cellY] = (byte)MathUtils.random(0, 3);
        }
        textureGrass = new Texture("Grass.png");
        textureWalls = new Texture("Walls.png");
        regions = new TextureRegion(textureWalls).split(100, 100)[0];
    }

    public boolean isCellPassable(Vector2 position) {
        if (position.x < 0.0f || position.x > WORLD_WIDTH || position.y < 0.0f || position.y > WORLD_HEIGHT) {
            return false;
        }
        int cellX = (int) (position.x / CELL_SIZE);
        int cellY = (int) (position.y / CELL_SIZE);

        // Корректная обработка отрицательных индексов (ключевое исправление)
        cellX = (cellX % Map.CELLS_X + Map.CELLS_X) % Map.CELLS_X;
        cellY = (cellY % Map.CELLS_Y + Map.CELLS_Y) % Map.CELLS_Y;
        return dataLayer[cellX][cellY] == 0;
    }

    public void render(SpriteBatch batch, Camera camera) {
        int camX = (int) camera.getOffsetX() / CELL_SIZE;
        int camY = (int) camera.getOffsetY() / CELL_SIZE;
        int camW = (int) camera.getWidth() / CELL_SIZE + 2;
        int camH = (int) camera.getHeight() / CELL_SIZE + 2;

        for (int i = camX; i < camX + camW; i++) {
            for (int j = camY; j < camY + camH; j++) {
                if (i >= 0 && i < CELLS_X && j >= 0 && j < CELLS_Y) {
                    batch.draw(textureGrass, i * 80 - camera.getOffsetX(), j * 80 - camera.getOffsetY());
                    if (dataLayer[i][j] == 1) {
                        batch.draw(regions[typeLayer[i][j]], i * 80 - camera.getOffsetX() - 10, j * 80 - camera.getOffsetY() - 10);
                    }
                }
            }
        }
    }
}
