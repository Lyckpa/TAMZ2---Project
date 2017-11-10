package com.example.patrik.skritkove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Patrik on 10.11.2017.
 */

public class GameEngine extends SurfaceView implements Runnable {

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playing;
    Canvas canvas;
    Paint paint;
    long timeRefresh = 20;
    private long timeThisFrame;
    Bitmap bitmapBob;
    int bobXPosition = 10;
    int bobYPosition = 10;

    public GameEngine(Context context) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ikona);
        playing = true;
    }

    public GameEngine(Context context, AttributeSet attrs) {
        super(context, attrs);
        ourHolder = getHolder();
        paint = new Paint();
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ikona);
        playing = true;
    }

    public GameEngine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ourHolder = getHolder();
        paint = new Paint();
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ikona);
        playing = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void run() {
        while (playing) {
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
    int offset = 15;
    public void update() {

        if(bobXPosition < 20)
            offset = 15;
        if(bobXPosition  > 300)
            offset = -15;
        //Log.i("pozice", Integer.toString(bobXPosition));
        bobXPosition = bobXPosition + offset;
    }

    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            Log.i("size", "H" + Integer.toString(canvas.getHeight()) + " W " + Integer.toString(canvas.getWidth()));
            canvas.drawColor(Color.argb(255,  26, 128, 182));
            //canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);
            canvas.drawBitmap(bitmapBob, null, new Rect(bobXPosition, bobYPosition, bobXPosition + 200, bobYPosition + 200), paint);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
