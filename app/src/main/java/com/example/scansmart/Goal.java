package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Goal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Button loseButton = (Button) findViewById(R.id.loseButton);
        loseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.goal = 0;
                switchActivities();
            }
        });

        Button gainButton = (Button) findViewById(R.id.gainButton);
        gainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.goal = 1;
                switchActivities();
            }
        });
        Button maintainButton = (Button) findViewById(R.id.maintainButton);
        maintainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.goal = 2;
                switchActivities();
            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, DisplayCal.class);
        startActivity(switchActivityIntent);
    }
}