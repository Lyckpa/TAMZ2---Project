package com.example.patrik.skritkove;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Patrik on 12.11.2017.
 */

public class World {

    int level;
    private Resources r;
    private List<WorldObject> world;
    private Random random;
    public int floors[] = null;
    public boolean canCreateElf= true;
    public boolean[] canCreateBall = null;
    public World(int level, Resources r)
    {
        this.level = level;
        this.r = r;
        random = new Random();

        world = new ArrayList<WorldObject>();
        world.add(new WorldObject(new BitmapFactory().decodeResource(r, R.drawable.herni_plocha), 0,0, 1280, 720));
        Bitmap bmp = new BitmapFactory().decodeResource(r, R.drawable.podlaha);
        Bitmap bmp2 = new BitmapFactory().decodeResource(r, R.drawable.zebrik);
        if(level == 1) {
            for (int i = 0; i < 9; i++) {
                world.add(new WorldObject(bmp, (i + 1) * 128, 720 - 42, 128, 42));
                world.add(new WorldObject(bmp, (i + 1) * 128, 300, 128, 42));
            }
            for (int i = 2; i < 11; i++) {
                world.add(new WorldObject(bmp2, 128, 720 - i * 42, 128, 42));
            }
            world.add(new WorldObject(new BitmapFactory().decodeResource(r, R.drawable.cil_pravy), 1000, 300 - 150, 280, 150));
            floors = new int[]{678,300};
            canCreateElf = true;
            canCreateBall = new boolean[]{true, true};
        } else if (level == 2)
        {
            for (int i = 0; i < 9; i++) {
                world.add(new WorldObject(bmp, (i + 1) * 128, 720 - 42, 128, 42));
                world.add(new WorldObject(bmp, i * 128, 174, 128, 42));
                world.add(new WorldObject(bmp, i * 128, 426, 128, 42));
            }
            for (int i = 2; i < 8; i++) {
                world.add(new WorldObject(bmp2, 128, 720 - i * 42, 128, 42));
                world.add(new WorldObject(bmp2, 640, 720 - i * 42, 128, 42));
                world.add(new WorldObject(bmp2, 1024, 720 - (i + 6) * 42, 128, 42));
            }
            world.add(new WorldObject(new BitmapFactory().decodeResource(r, R.drawable.cil_levy), 0, 24, 280, 150));
            floors = new int[]{678,426, 174};
            canCreateElf = true;
            canCreateBall = new boolean[]{true, true, true};
        } else if (level == 3)
        {
            for (int i = 0; i < 9; i++) {
                world.add(new WorldObject(bmp, (i + 1) * 128, 720 - 42, 128, 42));
                world.add(new WorldObject(bmp, i * 128, 174, 128, 42));
                world.add(new WorldObject(bmp, i * 128, 426, 128, 42));
            }
            for (int i = 2; i < 8; i++) {
                world.add(new WorldObject(bmp2, 128, 720 - i * 42, 128, 42));
                world.add(new WorldObject(bmp2, 1024, 720 - (i + 6) * 42, 128, 42));
            }
            world.add(new WorldObject(new BitmapFactory().decodeResource(r, R.drawable.cil_levy), 0, 24, 280, 150));
            floors = new int[]{678,426, 174};
            canCreateElf = true;
            canCreateBall = new boolean[]{true, true, true};
        }
    }

    public List<WorldObject> getWorld()
    {
        return  world;
    }

    public Elf createElf()
    {
        if(!canCreateElf)
            return null;
        int rnd = random.nextInt(4);
        Bitmap[] bmp = null;
        switch (rnd)
        {
            case 0 : {bmp = new Bitmap[] {new BitmapFactory().decodeResource(r, R.drawable.figurka_cerveny_n), new BitmapFactory().decodeResource(r, R.drawable.figurka_cerveny_dl), new BitmapFactory().decodeResource(r, R.drawable.figurka_cerveny_dp), new BitmapFactory().decodeResource(r, R.drawable.figurka_cerveny_l), new BitmapFactory().decodeResource(r, R.drawable.figurka_cerveny_p)};  break;}
            case 1 : {bmp = new Bitmap[] {new BitmapFactory().decodeResource(r, R.drawable.figurka_modry_n), new BitmapFactory().decodeResource(r, R.drawable.figurka_modry_dl), new BitmapFactory().decodeResource(r, R.drawable.figurka_modry_dp), new BitmapFactory().decodeResource(r, R.drawable.figurka_modry_l), new BitmapFactory().decodeResource(r, R.drawable.figurka_modry_p)};  break;}
            case 2 : {bmp = new Bitmap[] {new BitmapFactory().decodeResource(r, R.drawable.figurka_zeleny_n), new BitmapFactory().decodeResource(r, R.drawable.figurka_zeleny_dl), new BitmapFactory().decodeResource(r, R.drawable.figurka_zeleny_dp), new BitmapFactory().decodeResource(r, R.drawable.figurka_zeleny_l), new BitmapFactory().decodeResource(r, R.drawable.figurka_zeleny_p)};  break;}
            case 3 : {bmp = new Bitmap[] {new BitmapFactory().decodeResource(r, R.drawable.figurka_zluty_n), new BitmapFactory().decodeResource(r, R.drawable.figurka_zluty_dl), new BitmapFactory().decodeResource(r, R.drawable.figurka_zluty_dp), new BitmapFactory().decodeResource(r, R.drawable.figurka_zluty_l), new BitmapFactory().decodeResource(r, R.drawable.figurka_zluty_p)};  break;}
        }
        return new Elf(1280, 586, 59, 92, ElfState.STANDING, true, bmp);
    }

    public Ball createBall()
    {
        boolean created = false;
        int rnd = random.nextInt(floors.length);
        for(int i = 0; i < canCreateBall.length; i++)
        {
            if(canCreateBall[rnd] == true)
            {
                created = true;
                break;
            }
            rnd++;
            rnd = rnd % floors.length;
        }
        if(!created)
            return null;
        int y = floors[rnd] - 92;
        int x = 1280;
        boolean left = true;
        if(rnd == 0 || rnd == 2) {
            x = -26;
            left = false;
        }
        int position = random.nextInt(2);
        if(position == 0)
        {
            return new Ball(x, y, 26, 92, true, left, new BitmapFactory().decodeResource(r, R.drawable.koule_dole), rnd);
        }
        else
        {
            return new Ball(x, y, 26, 92, false, left, new BitmapFactory().decodeResource(r, R.drawable.koule_nahore), rnd);
        }
    }

    public void clearAllLocks()
    {
        canCreateElf = true;
        for(int i = 0; i < canCreateBall.length; i++)
            canCreateBall[i] = true;
    }

    public void setLockElf(int x, int floor)
    {
        if(floor == 0 && x > 960)
            canCreateElf = false;
    }

    public void setLockBall(int x, int floor)
    {
        if(floor == 0 && x < 320)
            canCreateBall[floor] = false;
        if(floor == 1 && x > 960)
            canCreateBall[floor] = false;
        if(floor == 2 && x < 320)
            canCreateBall[floor] = false;
    }

    public boolean isLadder(int x, int y)
    {
        if(level == 1)
        {
            //Log.i("pp", x + " "  + y + " " + Math.abs(128 + 64 - x) + " " + Math.abs(636 - y));
            if(Math.abs(128 + 64 - x - 29) < 64 && Math.abs(636 - y) < 64)
            {
                return  true;
            }
            return false;
        }
        else if (level == 2)
        {
            if(Math.abs(128 + 64 - x - 29) < 64 && Math.abs(636 - y) < 64)
            {
                return  true;
            }
            if(Math.abs(640 + 64 - x - 29) < 64 && Math.abs(636 - y) < 64)
            {
                return  true;
            }
            if(Math.abs(1024 + 64 - x - 29) < 64 && Math.abs(384 - y) < 64)
            {
                return  true;
            }
            return false;
        }
        else if (level == 3)
        {
            if(Math.abs(128 + 64 - x - 29) < 64 && Math.abs(636 - y) < 64)
            {
                return  true;
            }
            if(Math.abs(1024 + 64 - x - 29) < 64 && Math.abs(384 - y) < 64)
            {
                return  true;
            }
            return false;
        }
        return false;
    }

    public boolean isFinish(int x, int y)
    {
        if(level == 1)
        {
            if(Math.abs(1300 - x) < 140 && Math.abs(150 + 75 - y) < 75)
            {
                return  true;
            }
            return false;
        }
        else if (level == 2)
        {
            if(Math.abs(0 - x - 29) < 64 && Math.abs(24 + 75 - y) < 64)
            {
                return  true;
            }
            return false;
        }
        else if (level == 3)
        {
            if(Math.abs(0 - x - 29) < 64 && Math.abs(24 + 75 - y) < 64)
            {
                return  true;
            }
            return false;
        }
        return false;
    }

    public boolean isFloor(int x, int floor)
    {
        if(level == 1)
        {
            if(floor == 0 && (x + 30) > 128)
            {
                return  true;
            }
            if(floor == 1)
            {
                return  true;
            }
            return false;
        }
        else if (level == 2)
        {
            if(floor == 0 && (x + 30) > 128)
            {
                return  true;
            }
            if(floor == 1 && (x + 30) < 1152)
            {
                return  true;
            }
            if(floor == 2)
            {
                return true;
            }
            return false;
        }
        else if (level == 3)
        {
            if(floor == 0 && (x + 30) > 128)
            {
                return  true;
            }
            if(floor == 1 && (x + 30) < 1152)
            {
                return  true;
            }
            if(floor == 2)
            {
                return true;
            }
            return false;
        }
        return false;
    }
}
