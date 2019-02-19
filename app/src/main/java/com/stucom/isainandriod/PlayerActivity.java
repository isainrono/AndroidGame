package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stucom.isainandriod.model.MyToken;

public class PlayerActivity extends AppCompatActivity {

    TextView apName;
    TextView apId;
    TextView apLevel;
    TextView apScore;
    ImageView apPlayerImage;
    Context context = PlayerActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        apName = findViewById(R.id.apName);
        apId = findViewById(R.id.apId);
        apLevel = findViewById(R.id.apLevel);
        apScore = findViewById(R.id.apScore);
        apPlayerImage = findViewById(R.id.apPlayerImage);

        Intent getDatas = getIntent();

        String tapName = getDatas.getStringExtra("playerName");
        int tapId =  getDatas.getIntExtra("playerId", -1);
        int tapLevel = getDatas.getIntExtra("totalScore", -1);
        int tapScore = getDatas.getIntExtra("lastLevel",-1);



        apName.setText(tapName);
        apId.setText(String.valueOf(tapId));
        apLevel.setText(String.valueOf(tapLevel));
        apScore.setText(String.valueOf(tapScore));

        Picasso.get().load(MyToken.getPlayer().getImage()).into(apPlayerImage);

    }
}
