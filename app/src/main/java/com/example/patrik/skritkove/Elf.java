package com.example.patrik.skritkove;

import android.graphics.Bitmap;

/**
 * Created by Patrik on 13.11.2017.
 */

public class Elf {

    public int x;
    public int y;
    public int sizeX;
    public int sizeY;
    public ElfState state;
    public boolean left;
    public Bitmap[] pictures;
    public int floor;
    public int wait;
    public Elf(int x, int y, int sizeX, int sizeY,  ElfState state, boolean left, Bitmap[] pictures)
    {
        this.x = x;
        this.y = y;
        this.state = state;
        this.left = left;
        this.pictures = pictures;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        floor = 0;
        wait = 0;
    }

    public Bitmap getPicture()
    {
        if(state.equals(ElfState.CLIMBING) || state.equals(ElfState.WAITING))
            return pictures[0];
        else if(state.equals(ElfState.BENTING) && left == true)
            return pictures[1];
        else if (state.equals(ElfState.BENTING) && left == false)
            return pictures[2];
        else if (left == true)
            return pictures[3];
        else
            return  pictures[4];
    }
}
