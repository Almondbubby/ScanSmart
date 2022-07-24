package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Button noButton = (Button) findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.activityLevel = 0;
                switchActivities();
            }
        });

        Button lightButton = (Button) findViewById(R.id.lightButton);
        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.activityLevel = 1;
                switchActivities();
            }
        });

        Button moderateButton = (Button) findViewById(R.id.moderateButton);
        moderateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.activityLevel = 2;
                switchActivities();
            }
        });

        Button heavyButton = (Button) findViewById(R.id.heavyButton);
        heavyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.activityLevel = 3;
                switchActivities();
            }
        });

        Button veryHeavyButton = (Button) findViewById(R.id.veryHeavyButton);
        veryHeavyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.activityLevel = 4;
                switchActivities();
            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Goal.class);
        startActivity(switchActivityIntent);
    }
}