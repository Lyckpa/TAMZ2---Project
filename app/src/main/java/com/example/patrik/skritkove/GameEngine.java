package com.example.patrik.skritkove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Patrik on 10.11.2017.
 */

public class GameEngine extends SurfaceView implements Runnable {

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playing;
    Canvas canvas;
    Paint paint;
    long timeRefresh = 30;
    private long timeThisFrame;
    public int difficulty = 1;
    private int speed = 2;
    private int timeOutElf = 50;
    private int timeOutBall = 50;
    private int score = 0;
    private Random random;
    private Elf selectedElf = null;
    Bitmap bitmapWorld;
    private Bitmap sipka;
    private int endGame = 30;
    public String filePath;
    List<Elf> elfs;
    List<Ball> balls;
    //int bobXPosition = 10;
    //int bobYPosition = 10;

    public boolean sound;

    public Vibrator vibrator = null;
    public MediaPlayer music = null;
    public  MediaPlayer jumpSound = null;
    public MediaPlayer downSound = null;
    public MediaPlayer endSound = null;
    public MediaPlayer finishSound = null;

    private int x1;
    private int y1;

    World world;
    public int level = 1;
    public String playerName = "";

    public GameEngine(Context context) {
        super(context);
        init();
    }

    public GameEngine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameEngine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init()
    {
        ourHolder = getHolder();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        elfs = new ArrayList<Elf>();
        balls = new ArrayList<Ball>();
        random = new Random();
        sipka = BitmapFactory.decodeResource(this.getResources(), R.drawable.sipka);
        //bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ikona);
        init(level);
        playing = true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN :
            {
                x1 = (int)motionEvent.getX();
                y1 = (int)motionEvent.getY();
                break;
            }
            case MotionEvent.ACTION_UP :
            {
                int x2 =  (int)motionEvent.getX();
                int y2 =  (int)motionEvent.getY();

                int difX = x1 - x2;
                int difY = y1 - y2;
                if(Math.abs(difX) < 50 && Math.abs(difY) < 50)//tap
                {
                    if(selectedElf != null) {
                        if (Math.abs(selectedElf.x + selectedElf.sizeX / 2 - x2) < 40 && Math.abs(selectedElf.y + selectedElf.sizeY / 2 - y2) < 60) {
                            if(selectedElf.state.equals(ElfState.CLIMBING))
                            {
                                selectedElf.state = ElfState.WAITING;
                                selectedElf.wait = (180/speed)/difficulty;
                            }
                            else if (world.isLadder(selectedElf.x, selectedElf.y))
                                selectedElf.state = ElfState.CLIMBING;
                            break;
                        }
                    }
                    for(Elf e : elfs)
                    {
                        if(Math.abs(e.x + e.sizeX/2 - x2) < 40 && Math.abs(e.y + e.sizeY/2 - y2) < 60)
                        {
                            selectedElf = e;
                            break;
                        }
                    }
                }
                else if(difY > 0 && Math.abs(difX) < 500)//up
                {
                    if(selectedElf != null) {
                        if(selectedElf.state.equals(ElfState.STANDING))
                        {
                            selectedElf.state = ElfState.JUMPING;
                            //selectedElf.wait = (100/speed)/difficulty;
                            selectedElf.wait = 50;
                            if(jumpSound != null)
                                jumpSound.start();
                        }
                    }
                }
                else if(difY < 0 && Math.abs(difX) < 500)//down
                {
                    if(selectedElf != null) {
                        if(selectedElf.state.equals(ElfState.STANDING))
                        {
                            selectedElf.state = ElfState.BENTING;
                            selectedElf.wait = (100/speed)/difficulty;
                            if(downSound != null)
                                downSound.start();
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            if(music != null && music.isPlaying() == false)
            {
                music.start();
                music.setLooping(true);
            }
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            Log.i("sleep", Long.toString(timeRefresh - timeThisFrame));
            if (timeThisFrame < timeRefresh) {

                try {
                    Thread.sleep(timeRefresh - timeThisFrame);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    //int offset = 15;
    public void update() {
        world.clearAllLocks();
        Iterator<Elf> i = elfs.iterator();
      while(i.hasNext())
      {
          Elf e = i.next();
          if(e.state.equals(ElfState.CLIMBING) || e.state.equals(ElfState.WAITING))
          {}
          else
            world.setLockBall(e.x, e.floor);
          if(e.state.equals(ElfState.STANDING) || e.state.equals(ElfState.BENTING)) {
              if (e.left == true)
                  e.x = e.x - speed * difficulty;
              else if (e.left == false)
                  e.x = e.x + speed * difficulty;
              if(e.state.equals(ElfState.STANDING))
              {
                  for(Ball ball : balls)
                  {
                      if(ball.floor == e.floor)
                      {
                          if(Math.abs(ball.x + ball.sizeX/2 - (e.x + e.sizeX/2)) < 42)
                          {
                            endGame = 0;
                          }
                      }
                  }
              }
              if(e.state.equals(ElfState.BENTING))
              {
                  e.wait--;
                  if(e.wait <= 0)
                      e.state = ElfState.STANDING;
                  for(Ball ball : balls)
                  {
                      if(ball.floor == e.floor && ball.down == true)
                      {
                          if(Math.abs(ball.x + ball.sizeX/2 - (e.x + e.sizeX/2)) < 42)
                          {
                              endGame = 0;
                          }
                      }
                  }
              }
          }
          if(e.state.equals(ElfState.CLIMBING)) {
              e.y = e.y - speed*difficulty;
              if(world.floors[e.floor+1] - e.sizeY > e.y)
              {
                  e.floor++;
                  e.y = world.floors[e.floor] - e.sizeY;
                  e.left = !e.left;
                  e.state = ElfState.STANDING;
              }
          }
          if(e.state.equals(ElfState.WAITING))
          {
              e.wait--;
              if(e.wait <= 0)
                  e.state = ElfState.CLIMBING;
          }
          if(e.state.equals(ElfState.JUMPING))
          {
              if (e.left == true)
                  e.x = e.x - (speed * difficulty)/2/(speed/2);
                    //e.x--;
              else if (e.left == false)
                  e.x = e.x + (speed * difficulty)/2/(speed/2);
                   //   e.x++;
              //e.y = e.y - (e.wait/3 - ((25*speed*difficulty)/(2*3)));
              int offset = (e.wait/3 - ((50)/(6)));
              if(Math.abs(offset) <= 2)
                  offset = 0;
              else
              {
                  if(offset > 0)
                      offset = offset - 2;
                  else
                      offset = offset + 2;
              }
              e.y = e.y - offset;
              e.wait--;
              if(e.wait <= 0) {
                  e.state = ElfState.STANDING;
                  e.y = world.floors[e.floor] - e.sizeY;
              }
              for(Ball ball : balls)
              {
                  if(ball.floor == e.floor && ball.down == false)
                  {
                      if(Math.abs(ball.x + ball.sizeX/2 - (e.x + e.sizeX/2)) < 42)
                      {
                          endGame = 0;
                      }
                  }
              }
          }
          if(e.state.equals(ElfState.FALLING))
          {
              if (e.left == true)
                  e.x = e.x - (speed * difficulty)/2/(speed/2);
              else if (e.left == false)
                  e.x = e.x + (speed * difficulty)/2/(speed/2);
              e.y = e.y + 5;
              endGame--;
          }

          if(!world.isFloor(e.x, e.floor))
          {
              e.state = ElfState.FALLING;
          }

        if(world.isFinish(e.x, e.y))
        {
            if(selectedElf.equals(e))
                selectedElf = null;
            i.remove();
            score++;
            if(score % 5 == 0)
                speed++;
            if(finishSound != null)
                finishSound.start();
        }
      }
        Iterator<Ball> i2 = balls.iterator();
        while(i2.hasNext())
        {
            Ball b = i2.next();
            world.setLockElf(b.x, b.floor);
            if (b.left == true)
                b.x = b.x - speed * difficulty;
            else
                b.x = b.x + speed * difficulty;
            if(b.x > 1300 || b.x < -30)
                i2.remove();
        }

        if(endGame <= 0) {
            playing = false;
            if(vibrator != null)
                vibrator.vibrate(500);
            if(endSound != null)
                endSound.start();
            saveHighscore();
        }

        timeOutElf--;
        if(timeOutElf < 0)
        {
            Elf tmp = world.createElf();
            if(tmp != null) {
                timeOutElf = random.nextInt(300) + 700 - difficulty * 200 - speed * 50;
                elfs.add(tmp);
                if(vibrator != null)
                    vibrator.vibrate(500);
            }
        }

        timeOutBall--;
        if(timeOutBall < 0)
        {
            Ball tmp = world.createBall();
            if(tmp != null) {
                timeOutBall = random.nextInt(300) + 600 - difficulty * 200 - speed * 50;
                balls.add(tmp);
            }
        }
    }

    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            //Log.i("size", "H" + Integer.toString(canvas.getHeight()) + " W " + Integer.toString(canvas.getWidth()));
            //canvas.drawColor(Color.argb(255,  26, 128, 182));
            //canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);
            //canvas.drawBitmap(bitmapBob, null, new Rect(bobXPosition, bobYPosition, bobXPosition + 200, bobYPosition + 200), paint);
            /*for(WorldObject o : world.getWorld()) {
                canvas.drawBitmap(o.picture, null, new Rect(o.x, o.y, o.x + o.sizeX, o.y + o.sizeY), paint);
            }*/
            canvas.drawBitmap(bitmapWorld, null, new Rect(0,0,1280,720), paint);
            for(Elf e : elfs)
            {
                canvas.drawBitmap(e.getPicture(), null, new Rect(e.x, e.y, e.x + e.sizeX, e.y + e.sizeY), paint);
            }
            if(selectedElf != null)
            {
                canvas.drawBitmap(sipka, null, new Rect(selectedElf.x, selectedElf.y - 50, selectedElf.x + 59, selectedElf.y + 36 - 50), paint);
            }
            for(Ball b : balls)
            {
                canvas.drawBitmap(b.picture, null, new Rect(b.x, b.y, b.x + b.sizeX, b.y + b.sizeY), paint);
            }
            canvas.drawText("Skóre: " + score, 20,40, paint);
            if(endGame == 0)
            {
                paint.setTextSize(200);
                canvas.drawText("Konec hry!", 200,400, paint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }//
    }

    public void pause() {
        playing = false;
        if(music != null)
            music.pause();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        init(level);
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void  init(int level)
    {
        world = new World(level, this.getResources());
        Bitmap bmp = Bitmap.createBitmap(1280,720, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        for(WorldObject o : world.getWorld()) {
            c.drawBitmap(o.picture, null, new Rect(o.x, o.y, o.x + o.sizeX, o.y + o.sizeY), paint);
        }
        bitmapWorld = bmp;
       /* if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            for(WorldObject o : world.getWorld()) {
                canvas.drawBitmap(o.picture, 0, 0, paint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }*/
    }

    public void saveHighscore()
    {
        String[][] hihgscore = new String[10][2];
        try(BufferedReader br = new BufferedReader(new FileReader(filePath + "/" + "score" + level + ".csv"))) {
            for (int i = 0; i < 10; i++) {
                String[] tmp = br.readLine().split(";");
                hihgscore[i][0] = tmp[0];
                hihgscore[i][1] = tmp[1];
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(filePath + "/" + "score" + level +".csv")) {
            int k = 0;
            boolean isWrite = false;
            for(int i = 0; i < 10; i++)
            {
                if(Integer.parseInt(hihgscore[k][1]) >= score || isWrite == true)
                {
                    pw.println(hihgscore[k][0] + ";" + hihgscore[k][1]);
                    k++;
                }
                else
                {
                    pw.println(playerName + ";" + score);
                    isWrite = true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
