package com.example.patrik.skritkove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StoryActivity extends Activity {

    public Button StoryButtonLevel1;
    public Button StoryButtonLevel2;
    public Button StoryButtonLevel3;
    public Button next;
    public Button back;
    public TextView text;
    public EditText StoryTextName;
    private long mLastClickTime = 0;
    private int picture = 1;
    public View viewStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        viewStory = (View) findViewById(R.id.ActivityStory);
        viewStory.setBackgroundResource(R.drawable.pribeh1);
        StoryButtonLevel1 = (Button)findViewById(R.id.StoryButtonLevel1);
        StoryButtonLevel2 = (Button)findViewById(R.id.StoryButtonLevel2);
        StoryButtonLevel3 = (Button)findViewById(R.id.StoryButtonLevel3);
        StoryTextName = (EditText)findViewById(R.id.StoryTextName);
        next = (Button)findViewById(R.id.StoryButtonNext);
        back = (Button)findViewById(R.id.StoryButtonBack);
        back.setBackgroundResource(R.drawable.menu_leva);
        next.setBackgroundResource(R.drawable.dalsi_prava);
        text = (TextView)findViewById(R.id.StoryText);
        next.setEnabled(true);
        next.setVisibility(View.VISIBLE);
        back.setEnabled(true);
        back.setVisibility(View.VISIBLE);
        text.setVisibility(View.INVISIBLE);
        StoryButtonLevel1.setEnabled(false);
        StoryButtonLevel2.setEnabled(false);
        StoryButtonLevel2.setEnabled(false);
        StoryButtonLevel1.setVisibility(View.INVISIBLE);
        StoryButtonLevel2.setVisibility(View.INVISIBLE);
        StoryButtonLevel3.setVisibility(View.INVISIBLE);
        StoryTextName.setEnabled(false);
        StoryTextName.setVisibility(View.INVISIBLE);
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
        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    return false;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if(picture == 5)
                {
                    int id = getBaseContext().getResources().getIdentifier("pozadi_aktivita", "drawable", getBaseContext().getPackageName());
                    viewStory.setBackgroundResource(id);
                    next.setEnabled(false);
                    next.setVisibility(View.INVISIBLE);
                    back.setEnabled(false);
                    back.setVisibility(View.INVISIBLE);
                    text.setVisibility(View.VISIBLE);
                    StoryButtonLevel1.setEnabled(true);
                    StoryButtonLevel2.setEnabled(true);
                    StoryButtonLevel2.setEnabled(true);
                    StoryButtonLevel1.setVisibility(View.VISIBLE);
                    StoryButtonLevel2.setVisibility(View.VISIBLE);
                    StoryButtonLevel3.setVisibility(View.VISIBLE);
                    StoryTextName.setEnabled(true);
                    StoryTextName.setVisibility(View.VISIBLE);
                    return true;
                }
                else
                {
                    picture++;
                    int id = getBaseContext().getResources().getIdentifier("pribeh" + picture, "drawable", getBaseContext().getPackageName());
                    viewStory.setBackgroundResource(id);
                    if(picture == 5)
                        next.setBackgroundResource(R.drawable.dalsi_prava);
                    if(picture > 1)
                        back.setBackgroundResource(R.drawable.zpet_leva);
                    return true;
                }
            }
        });
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
                    int id = getBaseContext().getResources().getIdentifier("pribeh" + picture, "drawable", getBaseContext().getPackageName());
                    viewStory.setBackgroundResource(id);
                    if(picture == 1)
                        back.setBackgroundResource(R.drawable.menu_leva);
                    if(picture < 5)
                        next.setBackgroundResource(R.drawable.dalsi_prava);
                    return true;
                }
            }
        });
    }
}
