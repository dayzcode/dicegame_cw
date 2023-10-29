package com.example.dicegame_cw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnThrow;
    private Button btnScore;
    private ImageView diceImage;
    private TextView tvScore;
    private TextView tvWins;
    private Dice dice;
    private int humanScore = 0;
    private int computerScore = 0;
    private int targetScore = 101;
    private int humanWins = 0;
    private int computerWins = 0;
    private int rollsRemaining = 3;
    private boolean gameActive = true;
    private EditText etTargetScore;
    private int[] currentDiceValues = new int[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the "About" button by its ID
        Button buttonAbout = findViewById(R.id.buttonAbout);

        // Set a click listener for the "About" button
        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the "About" activity or show an About dialog
                // Example: Replace AboutActivity.class with your About activity or dialog
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        // Find the "New Game" button by its ID
        Button buttonNewGame = findViewById(R.id.buttonNewGame);

        // Set a click listener for the "New Game" button
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new game or navigate to the game activity
                // Example: Replace NewGameActivity.class with your game activity
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
