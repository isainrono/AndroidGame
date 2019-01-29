package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class Message extends AppCompatActivity {

    TextView name;
    ImageView ImagePlayer;
    Context context = Message.this;
    int playerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        name = findViewById(R.id.playerName);
        ImagePlayer = findViewById(R.id.playerImage);

        Intent getDatas = getIntent();

        String playerName = getDatas.getStringExtra("playerName");
        playerId = getDatas.getIntExtra("playerId", -1  );
        Log.d("isain", "idUser=" + playerId);
        Log.d("isain", "Position=" + MyToken.getInstance(context).getAuthToken());
        Toast.makeText(Message.this, "player"+ MyToken.getInstance(this.context).getAuthToken(), Toast.LENGTH_SHORT).show();
        name.setText(playerName);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void downloadDatas() {
        final String URL = String.format("https://api.flx.cat/dam2game/user/"+ this.playerId +"?token=" + MyToken.getInstance(this.context).getAuthToken());

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String json  = response.toString();
                        Gson gson = new Gson();

                        Type typeToken = new TypeToken<APIResponse<Player>>() {}.getType();
                        APIResponse <Player> apiResponse = gson.fromJson(json, typeToken);

                        Player selectedPlayer = apiResponse.getData();
                        Log.d("isain", "idUser=" + selectedPlayer.getId());
                        Log.d("isain", "imagen=" + selectedPlayer.getImage());

                        // Preguntar como hacer una adapter para el jugador
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
                Context context = Message.this;
                params.put("token", MyToken.getInstance(context).getAuthToken());
                return params;
            }

        };
        MyVolley.getInstance(this).add(request);
    }





}
