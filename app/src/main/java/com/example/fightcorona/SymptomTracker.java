package com.example.fightcorona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SymptomTracker  extends AppCompatActivity {
    Button positive, negative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        positive = (Button) findViewById(R.id.positive);
        negative = (Button) findViewById(R.id.negative);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent covid = new Intent(SymptomTracker.this, TestedPositive.class);
                startActivity(covid);

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent negative = new Intent(SymptomTracker.this, TestedNegative.class);
                startActivity(negative);

            }
        });



    }
}
