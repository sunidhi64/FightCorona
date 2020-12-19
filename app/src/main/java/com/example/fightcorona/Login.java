package com.example.fightcorona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth authLogin;
    EditText email, password;
    Button login;
    String em, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);
        authLogin = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email_id);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em = email.getText().toString();
                pass = password.getText().toString();
                loginUser();

            }
        });

    }


    public void loginUser() {
        authLogin.signInWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent symptoms = new Intent(Login.this, SymptomTracker.class);
                    startActivity(symptoms);
                    finish();
                }
                else {
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }


}
