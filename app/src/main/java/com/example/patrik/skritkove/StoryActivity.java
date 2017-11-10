package com.example.patrik.skritkove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StoryActivity extends Activity {

    public Button StoryButtonLevel1;
    public Button StoryButtonLevel2;
    public Button StoryButtonLevel3;
    public EditText StoryTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        StoryButtonLevel1 = (Button)findViewById(R.id.StoryButtonLevel1);
        StoryButtonLevel2 = (Button)findViewById(R.id.StoryButtonLevel2);
        StoryButtonLevel3 = (Button)findViewById(R.id.StoryButtonLevel3);
        StoryTextName = (EditText)findViewById(R.id.StoryTextName);
        StoryButtonLevel1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(StoryActivity.this, GameActivity.class);
                intent.putExtra("name", StoryTextName.getText().toString());
                intent.putExtra("level", 1);
                startActivity(intent);
                return true;
            }
        });
        StoryButtonLevel2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(StoryActivity.this, GameActivity.class);
                intent.putExtra("name", StoryTextName.getText().toString());
                intent.putExtra("level", 2);
                startActivity(intent);
                return true;
            }
        });
        StoryButtonLevel3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(StoryActivity.this, GameActivity.class);
                intent.putExtra("name", StoryTextName.getText().toString());
                intent.putExtra("level", 3);
                startActivity(intent);
                return true;
            }
        });
    }
}
