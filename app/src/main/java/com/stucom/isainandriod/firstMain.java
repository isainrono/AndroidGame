package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.stucom.isainandriod.model.APIResponse;
import com.stucom.isainandriod.model.MyToken;
import com.stucom.isainandriod.model.Player;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class firstMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        Button btnPlay = findViewById(R.id.btnPlay);

        Context context = firstMain.this;
        // Con estas 2 lineas dejo sin valor al sharedPreferences en caso de necesitarlo
        //MyToken.getInstance(context).setAuthToken("");
        //MyToken.getInstance(context).setAuthDatas();


        //Toast.makeText(firstMain.this, "token" + MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_LONG).show();
        Log.d("isain", "d" + MyToken.getInstance(context).getAuthToken());

        if(MyToken.getInstance(context).getAuthToken() != "") {
            downloadDatas();
            //Toast.makeText(firstMain.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();

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


            Button btnAbout = findViewById(R.id.btnAbout);
            btnAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(firstMain.this, AboutActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            //Toast.makeText(firstMain.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(firstMain.this, SettingActivity.class);
            startActivity(intent);
        }


        Button btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstMain.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        Button btnGame = findViewById(R.id.btnGame);
        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstMain.this, GameActivity.class);
                startActivity(intent);
            }
        });



    }

    public void downloadDatas() {
        Log.d("isain", "idUser=legga" );
        Context context = firstMain.this;
        Log.d("isain", "token " + MyToken.getInstance(context).getAuthToken());
        final String URL = String.format("https://api.flx.cat/dam2game/user?token=" + MyToken.getInstance(context).getAuthToken());

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("isain", "idUser=peta" );
                        String json  = response.toString();
                        Gson gson = new Gson();

                        Type typeToken = new TypeToken<APIResponse<Player>>() {}.getType();
                        APIResponse <Player> apiResponse = gson.fromJson(json, typeToken);

                        Player selectedPlayer = apiResponse.getData();

                        if(apiResponse.getErrorCode() == 0){
                            MyToken.setPlayerInformation(selectedPlayer);
                            Log.d("isainaqui", "imagen=" + MyToken.getPlayer().getName());
                        } else {
                            Log.d("isain", "error" + apiResponse.getErrorCode());
                        }


                        //Picasso.get().load(selectedPlayer.getImage()).into(imageView);


                        //sendMessage(String.valueOf(selectedPlayer.getId()));


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = error.toString();
                        NetworkResponse response = error.networkResponse;
                        if(response != null) {
                            message = response.statusCode + " " + message;
                        }

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Context context = firstMain.this;
                params.put("token", MyToken.getInstance(context).getAuthToken());
                return params;
            }

        };
        MyVolley.getInstance(this).add(request);
    }


    @Override
    protected void onResume(){
        super.onResume();
        Context context = firstMain.this;
        Button btnPlay = findViewById(R.id.btnPlay);

        if(MyToken.getInstance(context).getAuthToken() != "") {
            downloadDatas();
            //Toast.makeText(firstMain.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();

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


            Button btnAbout = findViewById(R.id.btnAbout);
            btnAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(firstMain.this, AboutActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            Button btnSetting = findViewById(R.id.btnSetting);
            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(firstMain.this, SettingActivity.class);
                    startActivity(intent);
                }
            });
        }
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
