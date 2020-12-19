package com.example.fightcorona;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestedNegative extends AppCompatActivity {
    EditText temp, oxygen;
    CheckBox cough, pain, congestion, headache, loss, relative;
    Button submit;
    FirebaseDatabase database;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negative);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        temp = findViewById(R.id.temperatur);
        relative = findViewById(R.id.relative);
        oxygen = findViewById(R.id.oxyge);
        cough = findViewById(R.id.coug);
        pain = findViewById(R.id.pai);
        congestion = findViewById(R.id.nos);
        headache = findViewById(R.id.headach);
        loss = findViewById(R.id.los);
        submit = findViewById(R.id.ente);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("Symptoms_notPositive");
                myRef.child(user.getUid()).child("Temperature").setValue(Double.parseDouble(temp.getText().toString()));
                myRef.child(user.getUid()).child("Oxygen").setValue(Integer.parseInt(oxygen.getText().toString()));
                myRef.child(user.getUid()).child("cough").setValue(cough.isChecked());
                myRef.child(user.getUid()).child("pain").setValue(pain.isChecked());
                myRef.child(user.getUid()).child("Congestion").setValue(congestion.isChecked());
                myRef.child(user.getUid()).child("Headache").setValue(headache.isChecked());
                myRef.child(user.getUid()).child("Loss of smell and taste").setValue(loss.isChecked());
                myRef.child(user.getUid()).child("Close Relative").setValue(relative.isChecked());
                Intent analysis = new Intent(TestedNegative.this, Analysis.class);
                startActivity(analysis);

            }
        });

    }
}
