package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class REGISTER extends AppCompatActivity {
    private EditText etName, etEmail, etPass1, etPass2;
    private Button btnSignUp;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName   = findViewById(R.id.fname);
        etEmail  = findViewById(R.id.email_reg);
        etPass1  = findViewById(R.id.pass1_reg);
        etPass2  = findViewById(R.id.pass2_reg);
        btnSignUp = findViewById(R.id.signup_button);

        SessionManager sessionManager = new SessionManager(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attemptRegister()){
                    sessionManager.createLoginSession(etEmail.getText().toString().trim());
                    Intent i = new Intent(REGISTER.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    private Boolean attemptRegister() {

        String name  = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String p1    = etPass1.getText().toString();
        String p2    = etPass2.getText().toString();

        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        if (p1.length() < 6) {
            etPass1.setError("Password must be ≥6 characters");
            etPass1.requestFocus();
            return false;
        }

        if (!p1.equals(p2)) {
            etPass2.setError("Passwords don't match");
            etPass2.requestFocus();
            return false;
        }

        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

        return true;
    }
}