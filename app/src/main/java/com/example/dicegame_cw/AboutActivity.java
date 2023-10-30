package com.example.dicegame_cw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Find the "About" button by its ID
        Button aboutButton = findViewById(R.id.aboutButton);

        // Set a click listener for the "About" button
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and display the AboutFragment
                AboutFragment aboutFragment = new AboutFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, aboutFragment) // Replace the fragment container with the AboutFragment
                        .addToBackStack(null) // Add the transaction to the back stack
                        .commit(); // Commit the transaction
            }
        });
    }
}
