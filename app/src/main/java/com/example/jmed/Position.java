package com.example.jmed;

import android.view.View;

public class Position {
    private float x;
    private float y;
    private int width;
    private int height;

    public Position(float x, float y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public Position(float x,float y) {
        this(x,y,0,0);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(int w) { this.width = w; }
    public void setHeight(int h) { this.height = h; }
}



