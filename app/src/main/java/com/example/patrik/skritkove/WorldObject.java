package com.example.patrik.skritkove;

import android.graphics.Bitmap;

/**
 * Created by Patrik on 12.11.2017.
 */

public class WorldObject {
    public WorldObject(Bitmap picture, int x, int y, int sizeX, int sizeY)
    {
        this.picture = picture;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Bitmap picture;
    public int x;
    public int y;
    public int sizeX;
    public int sizeY;
}
