package com.example.patrik.skritkove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.PrintWriter;

public class MainActivity extends Activity {

    public Button MainButtonHelp;
    public Button MainButtonSetting;
    public Button MainButtonHighscore;
    public Button MainButtonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createHighscore();
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

    private void createHighscore()
    {
        File file1 = new File(getFilesDir() + "/" + "score1.csv");
        File file2 = new File(getFilesDir() + "/" + "score2.csv");
        File file3 = new File(getFilesDir() + "/" + "score3.csv");
        File options = new File(getFilesDir() + "/" + "options.csv");
        if(!file1.exists())
        {
            try (PrintWriter pw = new PrintWriter(getFilesDir() + "/" + "score1.csv")) {
                for(int i = 0; i < 10; i++)
                    pw.println("---;0");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(!file2.exists())
        {
            try (PrintWriter pw = new PrintWriter(getFilesDir() + "/" + "score2.csv")) {
                for(int i = 0; i < 10; i++)
                    pw.println("---;0");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(!file3.exists())
        {
            try (PrintWriter pw = new PrintWriter(getFilesDir() + "/" + "score3.csv")) {
                for(int i = 0; i < 10; i++)
                    pw.println("---;0");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(!options.exists())
        {
            try (PrintWriter pw = new PrintWriter(getFilesDir() + "/" + "options.csv")) {
                    pw.println("1;1;1;1;");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
