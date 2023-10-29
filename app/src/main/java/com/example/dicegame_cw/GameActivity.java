package com.example.dicegame_cw;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private ImageView diceImage;
    private Button btnBackToMain;
    private Button btnThrow;
    private Button btnScore;
    private ImageView diceImage1;
    private ImageView diceImage2;
    private ImageView diceImage3;
    private ImageView diceImage4;
    private ImageView diceImage5;
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
        setContentView(R.layout.activity_newgame);

        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnThrow = findViewById(R.id.btnThrow);
        btnScore = findViewById(R.id.btnScore);
        diceImage1 = findViewById(R.id.diceImage1);
        diceImage2 = findViewById(R.id.diceImage2);
        diceImage3 = findViewById(R.id.diceImage3);
        diceImage4 = findViewById(R.id.diceImage4);
        diceImage5 = findViewById(R.id.diceImage5);
        tvScore = findViewById(R.id.tvScore);
        etTargetScore = findViewById(R.id.etTargetScore);
        tvWins = findViewById(R.id.tvWins);

        dice = new Dice();
    }

    public void onBackToMainClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initializeGame() {
        // Retrieve the custom target score
        String targetScoreText = etTargetScore.getText().toString();
        if (!targetScoreText.isEmpty()) {
            targetScore = Integer.parseInt(targetScoreText);
        } else {
            // Use the default target score (101 in this case)
            targetScore = 101;
        }
    }

    public void onThrowClick(View view) {
        if (gameActive && rollsRemaining > 0) {
            Log.d("YourTag", "Throw button clicked");
            rollsRemaining--;
            for (int i = 0; i < 5; i++) {
                currentDiceValues[i] = rollDice(); // Simulate rolling a die
                updateDiceImage(i, currentDiceValues[i]);
            }
            if (rollsRemaining == 0) {
                // Automatically update score after the maximum of 3 rolls
                onScoreClick(view);
            }
        }
    }

    public void onScoreClick(View view) {
        if (gameActive) {
            int currentRoundScore = calculateScore(currentDiceValues);
            humanScore += currentRoundScore;
            updateScore();

            rollsRemaining = 3;
            Arrays.fill(currentDiceValues, 0);

            if (humanScore >= targetScore) {
                showGameResult(true);
            } else {
                computerTurn();
            }
        }
    }

    private void updateDiceImage(int diceValue, int currentDiceValue) {
        String resourceName = "dice" + currentDiceValue;
        int resID = getResources().getIdentifier(resourceName, "drawable", getPackageName());
        switch (diceValue) {
            case 0:
                diceImage1.setImageResource(resID);
                break;
            case 1:
                diceImage2.setImageResource(resID);
                break;
            case 2:
                diceImage3.setImageResource(resID);
                break;
            case 3:
                diceImage4.setImageResource(resID);
                break;
            case 4:
                diceImage5.setImageResource(resID);
                break;
        }
    }

    private void computerTurn() {
        if (gameActive) {
            rollsRemaining = 3;
            int[] computerDiceValues = new int[5];
            for (int i = 0; i < 5; i++) {
                computerDiceValues[i] = rollDice(); // Simulate rolling a die for the computer
                updateDiceImage(i, computerDiceValues[i]);
            }

            int computerRoundScore = calculateScore(computerDiceValues);
            computerScore += computerRoundScore;
            updateScore();

            if (computerScore >= targetScore) {
                showGameResult(false);
            }
        }
    }

    private void showGameResult(boolean isHumanWinner) {
        gameActive = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over!");
        if (isHumanWinner) {
            builder.setMessage("You win!");
            humanWins++;
        } else {
            builder.setMessage("You lose.");
            computerWins++;
        }
        builder.setPositiveButton("New Game", (dialog, which) -> resetGame());
        builder.setCancelable(false);
        builder.show();
    }

    private void resetGame() {
        humanScore = 0;
        computerScore = 0;
        rollsRemaining = 3;
        Arrays.fill(currentDiceValues, 0);
        gameActive = true;
        updateScore();
    }

    private void updateScore() {
        String scoreText = "H:" + humanWins + "/C:" + computerWins;
        tvScore.setText("Score: " + humanScore);
        tvWins.setText(scoreText);
    }

    private int calculateScore(int[] diceValues) {
        int score = 0;
        for (int value : diceValues) {
            score += value;
        }
        return score;
    }

    private int rollDice() {
        // Simulate rolling a die and returning a random value between 1 and 6
        return (int) (Math.random() * 6) + 1;
    }
}
