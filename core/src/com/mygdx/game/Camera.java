package com.mygdx.game;

public class Camera {
    private float x, y;
    private int width, height;
    private float lerpSpeed = 0.3f;
    private float deadZoneX, deadZoneY;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        deadZoneX = width * 0.2f;
        deadZoneY = height * 0.2f;
    }

        public float getOffsetX () {
            return x;
        }

        public float getOffsetY () {
            return y;
        }

        public void setOffsetX ( float x){
            this.x = x;
        }

        public void setOffsetY ( float y){
            this.y = y;
        }

        public int getWidth () {
            return width;
        }

        public int getHeight () {
            return height;
        }

        public void update ( float heroX, float heroY){
            float targetX = heroX - width / 2f;
            float targetY = heroY - height / 2f;

            if (Math.abs(targetX - x) > deadZoneX) {
                x += (targetX - x) * lerpSpeed;
            }
            if (Math.abs(targetY - y) > deadZoneY) {
                y += (targetY - y) * lerpSpeed;
            }

            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            if (x > Map.WORLD_WIDTH - width) {
                x = Map.WORLD_WIDTH - width;
            }
            if (y > Map.WORLD_HEIGHT - height) {
                y = Map.WORLD_HEIGHT - height;
            }
        }
}
