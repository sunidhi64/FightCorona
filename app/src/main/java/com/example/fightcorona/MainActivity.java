package com.example.fightcorona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.client.Firebase;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    ViewFlipper view_f;

    Firebase myfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int images[] = {R.drawable.corona, R.drawable.corona1, R.drawable.corona2};
        view_f = findViewById(R.id.view_flipper);

        for (int image: images) {
            flipperImage(image);
        }

        Firebase.setAndroidContext(this);

        TextView awareness = (TextView) findViewById(R.id.awareness);
        awareness.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent awarenessIntent = new Intent(MainActivity.this, Awareness.class);
                startActivity(awarenessIntent);
            }
        });

        TextView mythBuster = (TextView) findViewById(R.id.myth);
        mythBuster.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mythIntent = new Intent(MainActivity.this, Myth.class);
                startActivity(mythIntent);
            }

        });
    }

    public void flipperImage(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        view_f.addView(imageView);
        view_f.setFlipInterval(4000);
        view_f.setAutoStart(true);

        view_f.setInAnimation(this, android.R.anim.slide_in_left);
        view_f.setOutAnimation(this, android.R.anim.slide_out_right);

    }
}