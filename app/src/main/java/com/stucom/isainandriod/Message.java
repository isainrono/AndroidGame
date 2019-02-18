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
import android.widget.Button;
import android.widget.EditText;
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
    Button btnSend;
    Context context = Message.this;
    EditText message;
    int playerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_message);



        name = findViewById(R.id.playerName);
        ImagePlayer = findViewById(R.id.playerImage);
        btnSend = findViewById(R.id.send);
        message = findViewById(R.id.playerMessage);

        Intent getDatas = getIntent();

        String playerName = getDatas.getStringExtra("playerName");
        playerId = getDatas.getIntExtra("playerId", -1  );
        Log.d("isain", "idUser=" + playerId);
        Log.d("isain", "Position=" + MyToken.getInstance(context).getAuthToken());
        Log.d("isain", "message" + message.getText().toString() + "hasta");
        // Toast.makeText(Message.this, "player"+ MyToken.getInstance(this.context).getAuthToken(), Toast.LENGTH_SHORT).show();
        name.setText(playerName);

        // Boton que envía el mensaje
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("isain", "entra despues de click en btnSend");
                Log.d("isain", "message" + message.getText().toString() + "hasta");
                sendMessage(String.valueOf(playerId));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        downloadDatas();
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
                        Log.d("isainaqui", "imagen=" + selectedPlayer.getImage());
                        Picasso.get().load(selectedPlayer.getImage()).into(ImagePlayer);


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
                Context context = Message.this;
                params.put("token", MyToken.getInstance(context).getAuthToken());
                return params;
            }

        };
        MyVolley.getInstance(this).add(request);
    }

    public void sendMessage(String playerSelected) {
        Log.d("isain", "este es el id" + playerSelected);
        Log.d("isain", "este es el id sin pasarlo" + playerId);
        final String URL = "https://api.flx.cat/dam2game/message/" + playerId;

        StringRequest request = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String json = response.toString();
                        Gson gson = new Gson();

                        Log.d("isain", "llega");
                        Type typeToken = new TypeToken<APIResponse<String>>() {}.getType();
                        APIResponse <String> apiResponse = gson.fromJson(json, typeToken);

                        if(apiResponse.getErrorCode() == 0) {
                            Toast.makeText(Message.this, "ok HEcho Mensaje", Toast.LENGTH_LONG).show();
                        }
                        

                    }
                }, new Response.ErrorListener() {

                             @Override public void onErrorResponse(VolleyError error) {
                                 Log.d("isain", "entra en error");
                                 String message = error.toString();
                                 NetworkResponse response = error.networkResponse;
                                 if(response != null){
                                     message = response.statusCode + " " + message;
                                 }

                             }
                         }) {
                             @Override protected Map<String, String> getParams() {
                                 Map<String, String> params = new HashMap<>();
                                 params.put("token", MyToken.getInstance(context).getAuthToken());
                                 params.put("text", message.getText().toString());
                                 return params;
                             }
                         };
                         MyVolley.getInstance(this).add(request);
    }







}
