package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.stucom.isainandriod.model.MyToken;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Context context = PlayActivity.this;

        Toast.makeText(PlayActivity.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();
    }
}
