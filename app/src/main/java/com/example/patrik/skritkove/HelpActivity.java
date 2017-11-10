package com.example.patrik.skritkove;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends Activity {

    View vMain;
    Button back;
    Button next;
    int picture;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
         vMain = (View) findViewById(R.id.ActivityHelp);
        picture = 1;
        vMain.setBackgroundResource(R.drawable.pravidla1);
        //Log.i("Click", "Create");
        back = (Button)findViewById(R.id.HelpButtonBack);
        next = (Button)findViewById(R.id.HelpButtonNext);
        back.setBackgroundResource(R.drawable.menu_leva);
        next.setBackgroundResource(R.drawable.dalsi_prava);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    return false;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if(picture == 1)
                {
                    finish();
                    return true;
                }
                else
                {
                    picture--;
                    int id = getBaseContext().getResources().getIdentifier("pravidla" + picture, "drawable", getBaseContext().getPackageName());
                    vMain.setBackgroundResource(id);
                    if(picture == 1)
                        back.setBackgroundResource(R.drawable.menu_leva);
                    if(picture < 6)
                        next.setBackgroundResource(R.drawable.dalsi_prava);
                    return true;
                }
            }
        });
        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    return false;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if(picture == 6)
                {
                    finish();
                    return true;
                }
                else
                {
                    picture++;
                    int id = getBaseContext().getResources().getIdentifier("pravidla" + picture, "drawable", getBaseContext().getPackageName());
                    vMain.setBackgroundResource(id);
                    if(picture == 6)
                        next.setBackgroundResource(R.drawable.menu_prava);
                    if(picture > 1)
                        back.setBackgroundResource(R.drawable.zpet_leva);
                    return true;
                }
            }
        });
    }
}
