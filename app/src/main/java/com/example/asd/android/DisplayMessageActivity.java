package com.example.asd.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);

        // Get SharedPreferences
        SharedPreferences pref = this.getSharedPreferences(getString(R.string.reference_file_key), Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default);
        System.out.println("defaultValue");
        System.out.println(defaultValue);

        long highScore = pref.getInt(getString(R.string.saved_high_score), defaultValue);
        System.out.println("highScore");
        System.out.println(highScore);

        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textView.setText(String.format("%s:%s:%s", message, Integer.toString(defaultValue), Long.toString(highScore)));
    }
}
