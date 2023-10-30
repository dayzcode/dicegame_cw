package com.example.dicegame_cw;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        // Find TextViews in the layout to display student information and message
        TextView studentIdTextView = rootView.findViewById(R.id.studentIdTextView);
        TextView studentNameTextView = rootView.findViewById(R.id.studentNameTextView);
        TextView messageTextView = rootView.findViewById(R.id.messageTextView);

        // Set the student information and message
        studentIdTextView.setText("Student ID: B00749916");
        studentNameTextView.setText("Student Name: Matthew Devine");
        messageTextView.setText("I declare that this is all my own work. Any material I have referred to has been accurately" +
                "referenced and any contribution of Artificial Intelligence technology has been fully acknowledged. " +
                "I have read the University’s policy on academic misconduct and understand the different forms of " +
                "academic misconduct. If it is shown that material has been falsified, plagiarised, or I have otherwise " +
                "attempted to obtain an unfair advantage for myself or others, I understand that I may face " +
                "sanctions in accordance with the policies and procedures of the University. A mark of zero may be " +
                "awarded, and the reason for that mark will be recorded on my file");

        return rootView;
    }

    public void onBackToMainClick(View view) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
