package com.stucom.isainandriod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerActivity extends AppCompatActivity {

    TextView apName;
    TextView apId;
    TextView apLevel;
    TextView apScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        apName = findViewById(R.id.apName);
        apId = findViewById(R.id.apId);
        apLevel = findViewById(R.id.apLevel);
        apScore = findViewById(R.id.apScore);

        Intent getDatas = getIntent();

        String tapName = getDatas.getStringExtra("playerName");
        /*int tapId =  getDatas.getIntExtra("totalScore", -1);
        int tapLevel = getDatas.getIntExtra("totalScore", -1);
        int tapScore = getDatas.getIntExtra("lastLevel",-1);

        */apName.setText(tapName);/*
        apId.setText(tapId);
        apLevel.setText(tapLevel);
        apScore.setText(tapScore);*/

    }
}
