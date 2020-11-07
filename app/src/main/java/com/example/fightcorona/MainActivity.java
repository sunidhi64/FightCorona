package com.example.fightcorona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    ViewFlipper view_f;

    Firebase myfirebase;
    FirebaseAuth myfirebaseAuth;
    EditText email, password;
    Button register;
    String myemail, mypassword;

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
        myfirebase = new Firebase("https://fightcorona-f189e.firebaseio.com");

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

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        myfirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myemail = email.getText().toString();
                mypassword = password.getText().toString();
                CreatNewUser();

            }
        });

    }

    private void CreatNewUser() {
        myfirebaseAuth.createUserWithEmailAndPassword(myemail, mypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent symptoms = new Intent(MainActivity.this, SymptomTracker.class);
                    startActivity(symptoms);
                }
                else {
                    Toast.makeText(MainActivity.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                }
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