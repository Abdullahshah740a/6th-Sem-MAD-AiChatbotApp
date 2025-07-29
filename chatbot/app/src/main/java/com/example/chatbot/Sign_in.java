package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_in extends AppCompatActivity {

    EditText email, password;
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        signup = findViewById(R.id.signup_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Sign_in.this, REGISTER.class);
                startActivity(loginIntent);
            }
        });
    }

    private void validateInputs() {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString();

        if (emailInput.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (passwordInput.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.createLoginSession(emailInput);

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

        Intent loginIntent = new Intent(Sign_in.this, MainActivity.class);
        startActivity(loginIntent);
    }
}
