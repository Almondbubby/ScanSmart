package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.testing);

        String content = readFromFile("userSettings.txt");
        if(content.charAt(0) == '0'){
            String[] lines = content.split(System.lineSeparator());
            Globals.cal = Integer.parseInt(lines[1]);
            Globals.gender = Integer.parseInt(lines[2]);
            Globals.weight = Double.parseDouble(lines[3]);
            Globals.height = Integer.parseInt(lines[4]);
            Globals.age = Integer.parseInt(lines[5]);
            Globals.activityLevel = Integer.parseInt(lines[6]);
            Globals.goal = Integer.parseInt(lines[7]);
            Intent switchActivityIntent = new Intent(this, Home.class);
            startActivity(switchActivityIntent);
        }

        Button maleButton = (Button) findViewById(R.id.maleButton);
        maleButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Globals.gender = 0;
                switchActivities();
            }
        });
        Button femaleButton = (Button) findViewById(R.id.femaleButton);
        femaleButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Globals.gender = 1;
                switchActivities();
            }
        });

    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Weight.class);
        startActivity(switchActivityIntent);
    }

    public String readFromFile(String fileName){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];

        try{
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}