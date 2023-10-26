package com.example.dicegame_cw;

import java.util.Random;

public class Dice {
    private Random random = new Random();

    public int roll() {
        return random.nextInt(6) + 1; // Random number between 1 and 6
    }
}
