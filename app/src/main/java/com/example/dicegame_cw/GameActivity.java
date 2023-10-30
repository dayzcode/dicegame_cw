package com.example.dicegame_cw;

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
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GameActivity extends AppCompatActivity {

    private Button btnBackToMain;
    private Button btnThrow;
    private Button btnScore;
    private ImageView diceImage1;
    private ImageView diceImage2;
    private ImageView diceImage3;
    private ImageView diceImage4;
    private ImageView diceImage5;
    private ImageView computerDiceImage1;
    private ImageView computerDiceImage2;
    private ImageView computerDiceImage3;
    private ImageView computerDiceImage4;
    private ImageView computerDiceImage5;
    private TextView tvScore;
    private TextView tvWins;
    private EditText etTargetScore;
    private Dice dice = new Dice();
    private int humanScore = 0;
    private int computerScore = 0;
    private int targetScore = 101;
    private int humanWins = 0;
    private int computerWins = 0;
    private int rollsRemaining = 3;
    private boolean gameActive = true;
    private int[] currentDiceValues = new int[5];
    private int[] computerDiceValues = new int[5];
    private boolean computerReroll = false;
    private boolean[] computerDiceToKeep = new boolean[5];
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);

        // Initialize UI elements
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnThrow = findViewById(R.id.btnThrow);
        btnScore = findViewById(R.id.btnScore);
        diceImage1 = findViewById(R.id.diceImage1);
        diceImage2 = findViewById(R.id.diceImage2);
        diceImage3 = findViewById(R.id.diceImage3);
        diceImage4 = findViewById(R.id.diceImage4);
        diceImage5 = findViewById(R.id.diceImage5);
        computerDiceImage1 = findViewById(R.id.computerDiceImage1);
        computerDiceImage2 = findViewById(R.id.computerDiceImage2);
        computerDiceImage3 = findViewById(R.id.computerDiceImage3);
        computerDiceImage4 = findViewById(R.id.computerDiceImage4);
        computerDiceImage5 = findViewById(R.id.computerDiceImage5);
        tvScore = findViewById(R.id.tvScore);
        etTargetScore = findViewById(R.id.etTargetScore);
        tvWins = findViewById(R.id.tvWins);

        initializeGame(); // Call this to set target score
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

    public void onBackToMainClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateDiceImage(int index, int diceValue, boolean isComputer) {
        // Depending on the diceValue, update the corresponding ImageView
        ImageView imageView;

        if (isComputer) {
            // Set the appropriate computer dice image based on the index
            imageView = getComputerDiceImageView(index);
        } else {
            // Set the appropriate human dice image based on the index
            imageView = getHumanDiceImageView(index);
        }

        // Set the appropriate image for the diceValue
        setDiceImageResource(imageView, diceValue);
    }

    private ImageView getComputerDiceImageView(int index) {
        switch (index) {
            case 0:
                return computerDiceImage1;
            case 1:
                return computerDiceImage2;
            case 2:
                return computerDiceImage3;
            case 3:
                return computerDiceImage4;
            case 4:
                return computerDiceImage5;
            default:
                return computerDiceImage1;
        }
    }

    private ImageView getHumanDiceImageView(int index) {
        switch (index) {
            case 0:
                return diceImage1;
            case 1:
                return diceImage2;
            case 2:
                return diceImage3;
            case 3:
                return diceImage4;
            case 4:
                return diceImage5;
            default:
                return diceImage1;
        }
    }

    private void setDiceImageResource(ImageView imageView, int diceValue) {
        switch (diceValue) {
            case 1:
                imageView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.dice6);
                break;
            default:
                imageView.setImageResource(R.drawable.dice1);
                break;
        }
    }

    public void onThrowClick(View view) {
        if (gameActive && rollsRemaining > 0) {
            Log.d("YourTag", "Throw button clicked");
            rollsRemaining--;

            // Simulate rolling 5 dice for the human player and the computer
            for (int i = 0; i < 5; i++) {
                currentDiceValues[i] = rollDice();
                computerDiceValues[i] = rollDice();
                updateDiceImage(i, currentDiceValues[i], false);
                updateDiceImage(i, computerDiceValues[i], true);
            }

            if (rollsRemaining == 0) {
                // Automatically update the score after the maximum of 3 rolls
                // This is where the score update logic should go
                int currentRoundScore = calculateScore(currentDiceValues);
                humanScore += currentRoundScore;

                for (int i = 0; i < 5; i++) {
                    currentDiceValues[i] = rollDice();
                    updateDiceImage(i, currentDiceValues[i], false);
                }

                computerTurn();

                updateScore();

                rollsRemaining = 3;
                Arrays.fill(currentDiceValues, 0);

                if (humanScore >= targetScore || computerScore >= targetScore) {
                    if (humanScore >= targetScore && computerScore >= targetScore) {
                        resolveTie();
                    } else if (humanScore >= targetScore) {
                        showGameResult(true);
                    } else {
                        showGameResult(false);
                    }

                    gameActive = false; // Set gameActive to false to end the game
                }
            }
        }
    }

    public void onScoreClick(View view) {
        if (gameActive) {
            int currentRoundScore = calculateScore(currentDiceValues);
            humanScore += currentRoundScore;

            for (int i = 0; i < 5; i++) {
                currentDiceValues[i] = rollDice();
                updateDiceImage(i, currentDiceValues[i], false);
            }

            computerTurn();

            updateScore();

            rollsRemaining = 3;
            Arrays.fill(currentDiceValues, 0);

            if (humanScore >= targetScore || computerScore >= targetScore) {
                if (humanScore >= targetScore && computerScore >= targetScore) {
                    resolveTie();
                } else if (humanScore >= targetScore) {
                    showGameResult(true);
                } else {
                    showGameResult(false);
                }

                gameActive = false; // Set gameActive to false to end the game
            }
        }
    }
    private void resolveTie() {
        while (humanScore == computerScore) {
            // Simulate another round for both players without rerolls
            int humanRoundScore = calculateScore(currentDiceValues);
            humanScore += humanRoundScore;
            int computerRoundScore = calculateScore(computerDiceValues);
            computerScore += computerRoundScore;
            updateScore();

            // If there's still a tie, keep looping to the next round
        }

        // Determine the winner
        if (humanScore > computerScore) {
            showGameResult(true);
        } else {
            showGameResult(false);
        }
    }


    public void onRerollClick(View view) {
        // Implement optional rerolls for the human player
        if (gameActive && rollsRemaining < 3) {
            rollsRemaining++;

            for (int i = 0; i < 5; i++) {
                if (!computerDiceToKeep[i]) {
                    currentDiceValues[i] = rollDice();
                    updateDiceImage(i, currentDiceValues[i], false);
                }
            }
        }
    }

    private void computerTurn() {
        if (gameActive) {
            rollsRemaining = 3;
            computerReroll = true;

            for (int roll = 0; roll < 3 && computerReroll; roll++) {
                computerReroll = false;

                // Simulate rolling 5 dice for the computer
                for (int i = 0; i < 5; i++) {
                    computerDiceValues[i] = rollDice();
                    updateDiceImage(i, computerDiceValues[i], true); // Update computer's dice image
                }

                int computerRoundScore = calculateScore(computerDiceValues);
                computerScore += computerRoundScore;
                updateScore();

                if (computerScore >= targetScore) {
                    showGameResult(false);
                    return; // End the computer's turn
                }

                // Implement a basic reroll strategy:
                // The computer rerolls any dice with a value less than 4.
                for (int i = 0; i < 5; i++) {
                    if (computerDiceValues[i] < 4) {
                        computerDiceValues[i] = rollDice(); // Reroll the dice
                        updateDiceImage(i, computerDiceValues[i], true);
                        computerReroll = true;
                    }
                }
            }

            if (computerScore >= targetScore) {
                showGameResult(false);
            }
        }
    }

    private boolean newGameButtonEnabled = true; // Add this variable to your class

    private void showGameResult(boolean isHumanWinner) {
        if (!gameActive || !newGameButtonEnabled) {
            return; // Exit if the game is not active or the dialog has already been shown
        }

        newGameButtonEnabled = false; // Disable the "New Game" button

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over!");

        if (isHumanWinner) {
            builder.setMessage("You win!");
            builder.setIcon(ContextCompat.getDrawable(this, R.drawable.green_background));
            humanWins++;
        } else {
            builder.setMessage("You lose.");
            builder.setIcon(ContextCompat.getDrawable(this, R.drawable.red_background));
            computerWins++;
        }

        builder.setPositiveButton("New Game", (dialog, which) -> {
            resetGame(); // Reset the game state
            newGameButtonEnabled = true; // Re-enable the "New Game" button
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void resetGame() {
        humanScore = 0;
        computerScore = 0;
        rollsRemaining = 3;
        Arrays.fill(currentDiceValues, 0);
        Arrays.fill(computerDiceValues, 0);
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
        return random.nextInt(6) + 1;
    }
}
