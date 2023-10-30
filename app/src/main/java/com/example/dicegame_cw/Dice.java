package com.example.dicegame_cw;

import java.util.Random;

public class Dice {
    private Random random = new Random(); // Create a Random object for rolling dice

    // Roll the dice and return a random number between 1 and 6
    public int roll() {
        return random.nextInt(6) + 1; // Random number between 1 and 6
    }
}
