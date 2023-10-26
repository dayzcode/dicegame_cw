package com.example.dicegame_cw;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;

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

        btnThrow = findViewById(R.id.btnThrow);
        btnScore = findViewById(R.id.btnScore);
        diceImage = findViewById(R.id.diceImage);
        tvScore = findViewById(R.id.tvScore);
        etTargetScore = findViewById(R.id.etTargetScore);
        tvWins = findViewById(R.id.tvWins);

        dice = new Dice();
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
            rollsRemaining--;
            for (int i = 0; i < 5; i++) {
                currentDiceValues[i] = dice.roll();
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
        diceImage.setImageResource(resID);
    }

    private void computerTurn() {
        if (gameActive) {
            rollsRemaining = 3;
            int[] computerDiceValues = new int[5];
            for (int i = 0; i < 5; i++) {
                computerDiceValues[i] = dice.roll();
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
        // Implement your scoring logic here
        // For example, sum of diceValues
        int score = 0;
        for (int value : diceValues) {
            score += value;
        }
        return score;
    }
}
