package com.example.patrik.skritkove;

import android.graphics.Bitmap;

/**
 * Created by Patrik on 16.11.2017.
 */

public class Ball {
    public int x;
    public int y;
    public int sizeX;
    public int sizeY;
    public boolean down;
    public boolean left;
    public Bitmap picture;
    public int floor;

    public Ball(int x, int y, int sizeX, int sizeY,  boolean down, boolean left, Bitmap picture, int floor)
    {
        this.x = x;
        this.y = y;
        this.down = down;
        this.left = left;
        this.picture = picture;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.floor = floor;
    }
}
