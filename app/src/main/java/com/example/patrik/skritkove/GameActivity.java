package com.example.patrik.skritkove;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    public GameEngine gameEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameEngine = (GameEngine)findViewById(R.id.gameEngine);
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
