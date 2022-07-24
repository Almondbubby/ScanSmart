package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DisplayCal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cal);
        Button okButton = (Button) findViewById(R.id.okButton);
        TextView text = findViewById(R.id.textCal);
        double bmr, cal;
        if(Globals.gender == 0) bmr = 66.5 + (13.75*Globals.weight) + (5.003 * Globals.height) - (6.75 * Globals.age);
        else bmr = 655.1 + (9.563 * Globals.weight) + (1.85*Globals.height)-(4.676*Globals.age);

        if(Globals.activityLevel == 0) cal = bmr*1.2;
        else if(Globals.activityLevel == 1) cal = bmr*1.375;
        else if(Globals.activityLevel == 2) cal = bmr*1.55;
        else if(Globals.activityLevel == 3) cal = bmr*1.725;
        else cal = bmr*1.9;

        if(Globals.goal == 0) cal *= 0.8;
        else if(Globals.goal == 1) cal *= 1.2;
        String out = "You should eat " + (int)cal + " calories a day";
        text.setText(out);
        Globals.cal = (int)cal;
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans = "0" + "\n" + Globals.cal + "\n" + Globals.gender + "\n" + Globals.weight + "\n" + Globals.height + "\n" + Globals.age + "\n" + Globals.activityLevel + "\n" + Globals.goal;
                writeToFile("userSettings.txt", ans);
                switchActivities();
            }
        });
    }

    public void writeToFile(String filename, String content){
        File path = getApplicationContext().getFilesDir();
        try{
            FileOutputStream writer = new FileOutputStream(new File(path, filename));
            writer.write(content.getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Home.class);
        startActivity(switchActivityIntent);
    }
}