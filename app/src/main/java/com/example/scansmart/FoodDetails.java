package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FoodDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        String out = "Total Calories: " + Globals.calEaten + "\n" + Globals.totalCarbs + " grams\n" + Globals.totalFat + " grams\n" + "Total Saturated Fat: " + Globals.totalSatFat + " grams\n" + "Total Trans Fat: " + Globals.totalTransFat + " grams\n" + "Total Iron: " + Globals.totalIron + " milligrams\n" + "Total Protein: " + Globals.totalProtein + " grams\n" + "Total Sugar: " + Globals.totalSugar + " grams\n" + "Total Sodium: " + Globals.totalSodium + " milligrams";
        TextView detailText = findViewById(R.id.detailText);
        detailText.setText(out);

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Home.class);
        startActivity(switchActivityIntent);
    }
}