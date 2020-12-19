package com.example.fightcorona;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Analysis extends AppCompatActivity {
    int flag1 = 0, flag2 = 0, flag_cough = 0, flag_taste = 0;
    double temp;
    int oxygen;
    boolean cough;
    FirebaseDatabase database;
    FirebaseUser user;
    TextView fever, oxy, symp, co, loss, pain;
    Button condition, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis1);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        fever = findViewById(R.id.fev);
        oxy = findViewById(R.id.oxyg);
        symp = findViewById(R.id.symptoms);
        co = findViewById(R.id.c);
        loss = findViewById(R.id.taste);
        pain = findViewById(R.id.pai);

        DatabaseReference symptoms = database.getReference("Symptoms_notPositive");
        symptoms.child(user.getUid()).child("Temperature").addValueEventListener(new ValueEventListener() {
            private static final String TAG = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temp = dataSnapshot.getValue(Double.class);
                if (temp >= 99.4) {
                    flag1 = 1;
                    fever.setText("Your temperature is above normal range");
                } else {
                    fever.setText("You don`t have fever");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        symptoms.child(user.getUid()).child("Oxygen").addValueEventListener(new ValueEventListener() {
            private static final String TAG = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                oxygen = dataSnapshot.getValue(Integer.class);
                if (oxygen < 95) {
                    flag2 = 1;
                    oxy.setText("You have hypoxia, consult doctor as soon as possible");


                }
                else {
                    oxy.setText("Your oxygen level is in normal range");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        symptoms.child(user.getUid()).child("cough").addValueEventListener(new ValueEventListener() {
            private static final String TAG = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                symptoms.child(user.getUid()).child("Close Relative").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cough = dataSnapshot.getValue(Boolean.class);

                        if (snapshot.getValue(Boolean.class) && cough) {
                            flag_cough = 1;
                            co.setText("You reported having cough or sore throat, kindly wear mask all the time");
                        }
                        else if (cough) {
                            co.setText("You reported having cough or sore throat, kindly wear mask all the time");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                symptoms.child(user.getUid()).child("pain").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cough = dataSnapshot.getValue(Boolean.class);

                        if (snapshot.getValue(Boolean.class) && cough) {
                            flag_cough = 1;
                            co.setText("You reported having cough or sore throat, kindly wear mask all the time");
                        }
                        else if (cough) {
                            co.setText("You reported having cough or sore throat, kindly wear mask all the time");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                symptoms.child(user.getUid()).child("Headache").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cough = dataSnapshot.getValue(Boolean.class);

                        if (snapshot.getValue(Boolean.class) && cough) {
                            flag_cough = 1;
                            co.setText("You reported having cough or sore throat, kindly wear mask all the time");
                        }
                        else if (cough) {
                            co.setText("You reported having cough or sore throat, kindly wear mask all the time");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        symptoms.child(user.getUid()).child("Loss of smell and taste").addValueEventListener(new ValueEventListener() {
            private static final String TAG = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue(Boolean.class)) {
                    flag_taste = 1;
                    loss.setText("You have loss of taste and smell");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        condition = findViewById(R.id.safe);
        condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag1 == 1 && flag2 == 1 || flag_taste == 1 || flag2 == 1) {
                    symp.setTextColor(Color.parseColor("#FF3D00"));
                    symp.setText("You have Covid19, kindly have a covid test");
                }
                else if (flag1 == 1 || flag_cough == 1) {
                    symp.setTextColor(Color.parseColor("#FFDE03"));
                    symp.setText("You may have Covid19, kindly confirm");
                }
                else {
                    symp.setTextColor(Color.parseColor("#00E676"));
                    symp.setText("You are safe till now");
                }
            }
        });

        logout = findViewById(R.id.logout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ac = new Intent(Analysis.this, MainActivity.class);
                startActivity(ac);
            }
        });


    }
}
