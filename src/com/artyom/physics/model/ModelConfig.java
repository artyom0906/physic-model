package com.artyom.physics.model;

public class ModelConfig {
    private int width;
    private int height;
    private int points_count;
    private float vx;
    private float vy;
    private boolean random_pos;
    private BoundaryType boundaryType;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPoints_count() {
        return points_count;
    }

    public void setPoints_count(int points_count) {
        this.points_count = points_count;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public boolean isRandom_pos() {
        return random_pos;
    }

    public void setRandom_pos(boolean random_pos) {
        this.random_pos = random_pos;
    }

    public BoundaryType getBoundaryType() {
        return boundaryType;
    }

    public void setBoundaryType(BoundaryType boundaryType) {
        this.boundaryType = boundaryType;
    }
}
