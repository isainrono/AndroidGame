package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stucom.isainandriod.model.MyToken;

public class firstMain extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);




        Button btnPlay = findViewById(R.id.btnPlay);

        Context context = firstMain.this;

        if(MyToken.getInstance(context) != null) {
            Toast.makeText(firstMain.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(firstMain.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(firstMain.this, SettingActivity.class);
            startActivity(intent);
        }


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstMain.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        Button btnRanking = findViewById(R.id.btnRanquing);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstMain.this, RankingActivity.class);
                startActivity(intent);
            }
        });


        Button btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstMain.this, SettingActivity.class);
                startActivity(intent);
            }
        });


        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstMain.this, AboutActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
