package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Height extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        Button submitHeight = (Button) findViewById(R.id.submitHeight);
        EditText feet = (EditText) findViewById(R.id.feetEdit);
        EditText inch = (EditText) findViewById(R.id.inchEdit);
        submitHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.height = (int) (((Integer.parseInt(feet.getText().toString())*12) + Integer.parseInt(inch.getText().toString()))*2.54);
                switchActivities();
            }
        });
    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Age.class);
        startActivity(switchActivityIntent);
    }
}