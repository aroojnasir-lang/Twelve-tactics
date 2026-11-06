package com.example.twelvetactics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeScreen extends AppCompatActivity {

    Button btnLogout;
    ImageView btnShare;
    LinearLayout btnwithcomputer, btnwithfriends,
            btnlocal, btnonline;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        // Shared Preferences
        preferences = getSharedPreferences(
                "TwelveTacticsData", MODE_PRIVATE);

        // Find Views
        btnLogout = findViewById(R.id.btnLogout);
        btnShare = findViewById(R.id.btnShare);
        btnwithcomputer = findViewById(R.id.btnPlayComputer);
        btnonline = findViewById(R.id.btnPlayOnline);
        btnlocal = findViewById(R.id.btnLocalMultiplayer);
        btnwithfriends = findViewById(R.id.btnPlayFriends);

        // Player vs Computer
        btnwithcomputer.setOnClickListener(v ->
                openGameBoard("AI"));

        // Online Multiplayer
        btnonline.setOnClickListener(v ->
                openGameBoard("ONLINE"));

        // Local Multiplayer
        btnlocal.setOnClickListener(v ->
                openGameBoard("LOCAL"));

        // Play with Friends
        btnwithfriends.setOnClickListener(v ->
                openGameBoard("FRIENDS"));

        // Logout
        btnLogout.setOnClickListener(v -> {

            preferences.edit()
                    .putBoolean("isLoggedIn", false)
                    .remove("loggedInUsername")
                    .apply();

            Toast.makeText(
                    HomeScreen.this,
                    "Logged out successfully",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    HomeScreen.this, Loginscreen.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });

        // Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });
    }

    // Open Game Board
    private void openGameBoard(String mode) {
        Intent intent = new Intent(
                HomeScreen.this, gameboard.class);

        intent.putExtra("GAME_MODE", mode);
        startActivity(intent);
    }



}



