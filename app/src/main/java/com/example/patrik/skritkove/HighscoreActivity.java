package com.example.patrik.skritkove;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HighscoreActivity extends Activity {

    ListView highscore;
    Button level1;
    Button level2;
    Button level3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        highscore = (ListView)findViewById(R.id.highscoreList);
        level1 = (Button)findViewById(R.id.HighscoreButtonLevel1) ;
        level2 = (Button)findViewById(R.id.HighscoreButtonLevel2) ;
        level3 = (Button)findViewById(R.id.HighscoreButtonLevel3) ;

        String[] from = new String[] {"name", "score"};
        int[] to = new int[] {R.id.highscoreItemName, R.id.highscoreItemScore};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < 10; i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("name", "Level 1 Name" + i);
            map.put("score", "0");
            fillMaps.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.item_highscore, from, to);
        highscore.setAdapter(adapter);

        level1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String[] from = new String[] {"name", "score"};
                int[] to = new int[] {R.id.highscoreItemName, R.id.highscoreItemScore};
                List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
                for(int i = 0; i < 10; i++)
                {
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("name", "Level 1 Name" + i);
                    map.put("score", "0");
                    fillMaps.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), fillMaps, R.layout.item_highscore, from, to);
                highscore.setAdapter(adapter);
                return true;
            }
        });
        level2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String[] from = new String[] {"name", "score"};
                int[] to = new int[] {R.id.highscoreItemName, R.id.highscoreItemScore};
                List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
                for(int i = 0; i < 10; i++)
                {
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("name", "Level 2 Name" + i);
                    map.put("score", "0");
                    fillMaps.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), fillMaps, R.layout.item_highscore, from, to);
                highscore.setAdapter(adapter);
                return true;
            }
        });
        level3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String[] from = new String[] {"name", "score"};
                int[] to = new int[] {R.id.highscoreItemName, R.id.highscoreItemScore};
                List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
                for(int i = 0; i < 10; i++)
                {
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("name", "Level 3 Name" + i);
                    map.put("score", "0");
                    fillMaps.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), fillMaps, R.layout.item_highscore, from, to);
                highscore.setAdapter(adapter);
                return true;
            }
        });
    }
}
