package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class Home extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView text = findViewById(R.id.testing);
        String titleText = Globals.cal - Globals.calEaten + " Calories Left";
        text.setText(titleText);

        reloadList();

        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });

        Button manualButton = (Button) findViewById(R.id.manualButton);
        EditText addName = findViewById(R.id.addName);
        EditText addCal = findViewById(R.id.addCal);
        Button addSubmit = findViewById(R.id.submitAdd);
        addName.setVisibility(View.INVISIBLE);
        addCal.setVisibility(View.INVISIBLE);
        addSubmit.setVisibility(View.INVISIBLE);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addName.getVisibility() == View.INVISIBLE) {
                    addName.setVisibility(View.VISIBLE);
                    addCal.setVisibility(View.VISIBLE);
                    addSubmit.setVisibility(View.VISIBLE);
                }
                else{
                    addName.setVisibility(View.INVISIBLE);
                    addCal.setVisibility(View.INVISIBLE);
                    addSubmit.setVisibility(View.INVISIBLE);
                }
            }
        });
        addSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.foodNames.add(addName.getText().toString());
                Globals.calorieList.add(Integer.parseInt(addCal.getText().toString()));
                Globals.calEaten += Integer.parseInt(addCal.getText().toString());
                text.setText(Globals.cal-Globals.calEaten + " Calories Left");
                reloadList();
                addName.setVisibility(View.INVISIBLE);
                addCal.setVisibility(View.INVISIBLE);
                addSubmit.setVisibility(View.INVISIBLE);
            }
        });

        Button detailsButton = (Button) findViewById(R.id.detailsButton);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailSwitch();
            }
        });

    }

    private void detailSwitch(){
        Intent switchActivityIntent = new Intent(this, FoodDetails.class);
        startActivity(switchActivityIntent);
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Scan.class);
        startActivity(switchActivityIntent);
    }

    @SuppressLint("SetTextI18n")
    private void reloadList(){
        StringBuilder nameSum = new StringBuilder();
        for(int i = 0; i < Globals.foodNames.size(); i++){
            nameSum.append(Globals.foodNames.get(i)).append("\n\n");
        }

        StringBuilder calSum = new StringBuilder();
        for(int i = 0; i < Globals.calorieList.size(); i++){
            calSum.append(Globals.calorieList.get(i)).append("\n\n");
            if(Globals.foodNames.get(i).length() > 25) calSum.append("\n");
        }

        TextView nameView = findViewById(R.id.foodNameView);
        TextView calView = findViewById(R.id.calView);

        nameView.setText("Food Name:\n\n" + nameSum);
        calView.setText("Cal:\n\n" + calSum);
    }

}