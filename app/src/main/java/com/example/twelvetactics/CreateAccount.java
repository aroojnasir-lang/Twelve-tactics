package com.example.twelvetactics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccount extends AppCompatActivity {

    EditText etCreateUsername, etCreateEmail;
    EditText etCreatePassword, etConfirmPassword;

    Button btnCreateAccount, btnback;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Connect XML views
        etCreateUsername = findViewById(R.id.etCreateUsername);
        etCreateEmail = findViewById(R.id.etCreateEmail);
        etCreatePassword = findViewById(R.id.etCreatePassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnback = findViewById(R.id.btn_back);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // SharedPreferences
        preferences = getSharedPreferences(
                "TwelveTacticsData", MODE_PRIVATE);

        // CREATE ACCOUNT
        btnCreateAccount.setOnClickListener(v -> {

            String username = etCreateUsername.getText()
                    .toString().trim();

            String email = etCreateEmail.getText()
                    .toString().trim();

            String password = etCreatePassword.getText()
                    .toString();

            String confirmPassword = etConfirmPassword.getText()
                    .toString();

            // Check empty fields
            if (username.isEmpty() || email.isEmpty()
                    || password.isEmpty()
                    || confirmPassword.isEmpty()) {

                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                return;
            }

            // Validate email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                etCreateEmail.setError("Enter a valid email");
                etCreateEmail.requestFocus();
                return;
            }


            // Strong password validation
            String passwordPattern =
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s])\\S{7,}$";

            if (!password.matches(passwordPattern)) {

                etCreatePassword.setError(
                        "Password must contain 6+ characters, uppercase, lowercase, number and special character");

                etCreatePassword.requestFocus();
                return;
            }

            // Confirm password
            if (!password.equals(confirmPassword)) {

                etConfirmPassword.setError(
                        "Passwords do not match");

                etConfirmPassword.requestFocus();
                return;
            }

            // Save account information
            preferences.edit()
                    .putString("username", username)
                    .putString("email", email)
                    .putString("password", password)
                    .putBoolean("isLoggedIn", true)
                    .putString("loggedInUsername", username)
                    .apply();

            Toast.makeText(this,
                    "Account created successfully!",
                    Toast.LENGTH_SHORT).show();

            // Open Home Screen
            Intent intent = new Intent(
                    CreateAccount.this,
                    HomeScreen.class);

            intent.putExtra("username", username);

            startActivity(intent);
            finish();
        });
        btnback.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, Loginscreen.class);
            startActivity(intent);

        });


    }
}