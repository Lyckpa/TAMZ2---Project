package com.example.patrik.skritkove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

import java.io.BufferedReader;
import java.io.FileReader;

public class GameActivity extends Activity {


    public GameEngine gameEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameEngine = (GameEngine)findViewById(R.id.gameEngine);
        Intent intent = getIntent();
        gameEngine.level = intent.getIntExtra("level", 0);
        gameEngine.playerName = intent.getStringExtra("name");
        gameEngine.filePath = getFilesDir().toString();
        try(BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + "options.csv"))) {
            String[] tmp = br.readLine().split(";");
            if(tmp[0].equals("1"))
            {
                gameEngine.vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            }
            if(tmp[1].equals("1"))
            {
                gameEngine.jumpSound = MediaPlayer.create(this, R.raw.sound_jump);
                gameEngine.downSound = MediaPlayer.create(this, R.raw.sound_down);
                gameEngine.endSound = MediaPlayer.create(this, R.raw.sound_end);
                gameEngine.finishSound = MediaPlayer.create(this, R.raw.sound_finish);
            }
            if(tmp[2].equals("1"))
            {
                gameEngine.music = MediaPlayer.create(this, R.raw.sound_background);
            }
            gameEngine.difficulty = Integer.parseInt(tmp[3]);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
        gameEngine.pause();
    }

}
