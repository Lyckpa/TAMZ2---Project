package com.example.patrik.skritkove;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class SettingsActivity extends Activity {

    public CheckBox vibration;
    public CheckBox sound;
    public CheckBox music;
    public RadioGroup dificulty;
    public Button delete;
    public Button save;
    public RadioButton easy;
    public RadioButton medium;
    public RadioButton hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        vibration = (CheckBox)findViewById(R.id.checkBoxVibration);
        sound = (CheckBox)findViewById(R.id.checkBoxSound);
        music = (CheckBox)findViewById(R.id.checkBoxMusic);
        dificulty = (RadioGroup)findViewById(R.id.RadioGroupDifficult);
        delete = (Button)findViewById(R.id.SettingButtonDeleteScore);
        save = (Button)findViewById(R.id.SettingButtonSave);
        easy = (RadioButton)findViewById(R.id.radioButtonEasy);
        medium = (RadioButton)findViewById(R.id.radioButtonMedium);
        hard = (RadioButton)findViewById(R.id.radioButtonHard);
        try(BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/" + "options.csv"))) {
            String[] tmp = br.readLine().split(";");
            if(tmp[0].equals("1"))
                vibration.setChecked(true);
            if(tmp[1].equals("1"))
                sound.setChecked(true);
            if(tmp[2].equals("1"))
                music.setChecked(true);
            if(tmp[3].equals("1"))
            {
                easy.setChecked(true);
            }
            if(tmp[3].equals("2"))
            {
                medium.setChecked(true);
            }
            if(tmp[3].equals("3"))
            {
                hard.setChecked(true);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        delete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                for(int k = 1; k < 4; k++) {
                    try (PrintWriter pw = new PrintWriter(getFilesDir() + "/" + "score" + k +".csv")) {
                        for (int i = 0; i < 10; i++)
                            pw.println("---;0");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
        save.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String[] tmp = new String[4];
                if(vibration.isChecked())
                    tmp[0] = "1";
                else
                    tmp[0] = "0";
                if(sound.isChecked())
                    tmp[1] = "1";
                else
                    tmp[1] = "0";
                if(music.isChecked())
                    tmp[2] = "1";
                else
                    tmp[2] = "0";
                if(easy.isChecked())
                    tmp[3] = "1";
                if(medium.isChecked())
                    tmp[3] = "2";
                if(hard.isChecked())
                    tmp[3] = "3";
                Log.i("options", tmp[0] + ";" + tmp[1] + ";" + tmp[2] + ";" + tmp[3] + ";");
                try (PrintWriter pw = new PrintWriter(getFilesDir() + "/" + "options.csv")) {
                        pw.println(tmp[0] + ";" + tmp[1] + ";" + tmp[2] + ";" + tmp[3] + ";");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            }
        });

    }
}
