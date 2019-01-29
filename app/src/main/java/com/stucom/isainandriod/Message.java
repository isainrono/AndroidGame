package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stucom.isainandriod.model.MyToken;

public class Message extends AppCompatActivity {

    TextView name;
    ImageView ImagePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        name = findViewById(R.id.playerName);
        ImagePlayer = findViewById(R.id.playerImage);

        Intent getDatas = getIntent();

        String playerName = getDatas.getStringExtra("playerName");

        Toast.makeText(Message.this, "player"+ playerName, Toast.LENGTH_SHORT).show();
        name.setText(playerName);


    }
}
