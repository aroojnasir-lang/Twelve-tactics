package com.example.twelvetactics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Loginscreen extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnCreateProfile, btnGuest;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginscreen);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom);

                    return insets;
                });

        // Connect XML views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);
        btnGuest = findViewById(R.id.btnGuest);

        // SharedPreferences
        preferences = getSharedPreferences(
                "TwelveTacticsData", MODE_PRIVATE);

        // CHECK SAVED LOGIN
        boolean isLoggedIn = preferences.getBoolean(
                "isLoggedIn", false);

        if (isLoggedIn) {

            String savedUsername = preferences.getString(
                    "loggedInUsername", "Guest");

            openhomescreen(savedUsername);
            return;
        }

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {

            String username = etUsername.getText()
                    .toString().trim();

            String password = etPassword.getText()
                    .toString().trim();

            String savedUsername = preferences.getString(
                    "username", "arooj");

            String savedPassword = preferences.getString(
                    "password", "123");

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(this,
                        "Please enter username and password",
                        Toast.LENGTH_SHORT).show();

            } else if (username.equals(savedUsername)
                    && password.equals(savedPassword)) {

                // Save login session
                preferences.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("loggedInUsername", username)
                        .apply();

                Toast.makeText(this,
                        "Login successful",
                        Toast.LENGTH_SHORT).show();

                openhomescreen(username);

            } else {

                Toast.makeText(this,
                        "Incorrect username or password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // CREATE PROFILE BUTTON
        btnCreateProfile.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Loginscreen.this, CreateAccount.class);
            startActivity(intent);
            finish();
        });
        // GUEST BUTTON
        btnGuest.setOnClickListener(v -> {

            preferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("loggedInUsername", "Guest")
                    .apply();

            Toast.makeText(this,
                    "Playing as a Guest",
                    Toast.LENGTH_SHORT).show();

            openhomescreen("Guest");
            finish();
        });
    }

    // OPEN HOME SCREEN
    private void openhomescreen(String username) {

        Intent intent = new Intent(
                Loginscreen.this, HomeScreen.class);

        intent.putExtra("username", username);

        startActivity(intent);
        finish();
    }
}
