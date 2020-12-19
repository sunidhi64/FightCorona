package com.example.fightcorona;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestedPositive extends AppCompatActivity {
    TextView exactDate, notify;
    DatePickerDialog datepicker;
    EditText getDate;
    CheckBox home, hospital, therm, oxymeter, masks;
    int day,month,year;
    FirebaseDatabase database;
    FirebaseUser user;
    Button submit;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covidpatient);
        Firebase.setAndroidContext(TestedPositive.this);
        getDate = (EditText) findViewById(R.id.tested_date);
        getDate.setInputType(InputType.TYPE_NULL);
        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datepicker = new DatePickerDialog(TestedPositive.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                getDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();

            }
        });









        home = (CheckBox) findViewById(R.id.home);
        hospital = (CheckBox) findViewById(R.id.hospital);
        home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hospital.setChecked(false);
                    TextView alert = (TextView) findViewById(R.id.alert);
                    alert.setText(R.string.alertmsg);


                }
            }
        });

        hospital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    home.setChecked(false);
                    TextView alert = (TextView) findViewById(R.id.alert);
                    alert.setText(R.string.alertMsg1);


                }
            }
        });

        therm = findViewById(R.id.therm);
        oxymeter = findViewById(R.id.oxy);
        masks = findViewById(R.id.mask);
        notify = (TextView) findViewById(R.id.txt);

        notify.setText("Always keep a set of thermometer, oxymeter and couple of masks");

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Date");
                myRef.child(user.getUid()).setValue(getDate.getText().toString());
                DatabaseReference isHome = database.getReference("homeQuarantined");
                isHome.child((user.getUid())).setValue(home.isChecked());
                DatabaseReference isHospi = database.getReference("hospitalized");
                isHospi.child((user.getUid())).setValue(hospital.isChecked());
                DatabaseReference istherm = database.getReference("Thermometer");
                istherm.child((user.getUid())).setValue(therm.isChecked());
                DatabaseReference isOxy = database.getReference("Oxymeter");
                isOxy.child((user.getUid())).setValue(oxymeter.isChecked());
                DatabaseReference hasMask = database.getReference("Mask");
                hasMask.child((user.getUid())).setValue(masks.isChecked());
                Intent dailyUpdate = new Intent(TestedPositive.this, DailyUpdates.class);
                startActivity(dailyUpdate);



            }
        });


    }









}
