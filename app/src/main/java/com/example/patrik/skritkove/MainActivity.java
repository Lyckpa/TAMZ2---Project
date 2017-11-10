package com.example.patrik.skritkove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public Button MainButtonHelp;
    public Button MainButtonSetting;
    public Button MainButtonHighscore;
    public Button MainButtonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainButtonHelp = (Button) findViewById(R.id.MainButtonHelp);
        MainButtonSetting = (Button) findViewById(R.id.MainButtonOption);
        MainButtonHighscore = (Button) findViewById(R.id.MainButtonScore);
        MainButtonPlay = (Button)findViewById(R.id.MainButtonPlay);
        MainButtonHelp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                return true;
            }
        });//
        MainButtonSetting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });
        MainButtonHighscore.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, HighscoreActivity.class);
                startActivity(intent);
                return true;
            }
        });
        MainButtonPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                startActivity(intent);
                return true;
            }
        });

    }
}
