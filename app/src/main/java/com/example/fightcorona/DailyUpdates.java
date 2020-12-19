package com.example.fightcorona;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DailyUpdates extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseUser user;
    TextView day;
    String d;
    EditText temp, oxygen;
    CheckBox cough, pain, congestion, headache, loss, diabetes, bp, asthama;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        DatabaseReference date = database.getReference("Date");
        day = findViewById(R.id.day);
        date.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            private static final String TAG = "";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                d = dataSnapshot.getValue(String.class);
                try {
                    day.setText("Today is your day " + getDifference());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
// Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        temp = findViewById(R.id.temperature);
        oxygen = findViewById(R.id.oxygen);
        cough = findViewById(R.id.cough);
        pain = findViewById(R.id.pain);
        congestion = findViewById(R.id.nose);
        headache = findViewById(R.id.headache);
        loss = findViewById(R.id.loss);
        diabetes = findViewById(R.id.diabetes);
        bp = findViewById(R.id.bp);
        asthama = findViewById(R.id.asthama);
        submit = findViewById(R.id.enter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("Symptoms");
                myRef.child(user.getUid()).child("Temperature").setValue(Double.parseDouble(temp.getText().toString()));
                myRef.child(user.getUid()).child("Oxygen").setValue(Integer.parseInt(oxygen.getText().toString()));
                myRef.child(user.getUid()).child("cough").setValue(cough.isChecked());
                myRef.child(user.getUid()).child("Congestion").setValue(congestion.isChecked());
                myRef.child(user.getUid()).child("Headache").setValue(headache.isChecked());
                myRef.child(user.getUid()).child("Loss of smell and taste").setValue(loss.isChecked());
                myRef.child(user.getUid()).child("diabetes").setValue(diabetes.isChecked());
                myRef.child(user.getUid()).child("bp").setValue(bp.isChecked());
                myRef.child(user.getUid()).child("asthama").setValue(asthama.isChecked());
                Intent analysis = new Intent(DailyUpdates.this, DetailedAnalysis.class);
                startActivity(analysis);



            }
        });




    }
    public int getDifference() throws ParseException {
        Calendar c = Calendar.getInstance();
        int da = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String x = String.valueOf(da) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = myFormat.parse(d);
        Date date2 = myFormat.parse(x);
        long diff = date2.getTime() - date1.getTime();
        if ((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1 < 0){
            return 0;
        }
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
    }


}
