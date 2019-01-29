package com.stucom.isainandriod;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.stucom.isainandriod.model.APIResponse;
import com.stucom.isainandriod.model.Player;
import com.stucom.isainandriod.model.MyToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context = RankingActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        if (MyToken.getInstance(context).getAuthToken() != null) {
            Log.d("isain", "Position=" + MyToken.getInstance(context).getAuthToken());
            Toast.makeText(RankingActivity.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();

            textView = findViewById(R.id.namePlayer);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    downloadDatas();
                }
            });


        } else {
            Intent intent = new Intent(RankingActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadDatas();
    }



    public void downloadDatas() {
        final String URL = String.format("https://api.flx.cat/dam2game/ranking/?token=" + MyToken.getInstance(this.context).getAuthToken());

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<APIResponse<List<Player>>>() {}.getType();
                        APIResponse<List<Player>> apiResponse = gson.fromJson(json, typeToken);

                        List<Player> players = apiResponse.getData();
                        PlayerAdapter adapter = new PlayerAdapter(players);
                        recyclerView.setAdapter(adapter);

                        swipeRefreshLayout.setRefreshing(false);
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
                        textView.setText("ERROR" + message);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Context context = RankingActivity.this;
                params.put("token", MyToken.getInstance(context).getAuthToken());
                return params;
            }

        };
        MyVolley.getInstance(this).add(request);
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView namePlayer;
        TextView DataPlayer;
        ImageView ImagePlayer;

        PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            namePlayer = itemView.findViewById(R.id.namePlayer);
            ImagePlayer = itemView.findViewById(R.id.imagePlayer);
            DataPlayer = itemView.findViewById(R.id.datas);
        }
    }


    class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

        private List<Player> players;

        PlayerAdapter(List<Player> players) {
            super();
            // Collections.sort(Players, new Player.comparator);
            this.players = players;
        }

        @NonNull @Override
        public  PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
            return new PlayerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerViewHolder viewHolder, int position) {
            // agregar el setonclip listerner en el adapter y despues hacer metodo de alertDialog
            Log.d("isain", "Position=" + position);

            final Player player = players.get(position);
            Log.d("isain", "id=" + player.getId());
            // Falta ordenar por orden
            if(!player.getName().equalsIgnoreCase("User")){
                viewHolder.namePlayer.setText(player.getName());
                String totalScore2 = String.valueOf(player.getLastScore());
                viewHolder.DataPlayer.setText(totalScore2);
                Picasso.get().load(player.getImage()).into(viewHolder.ImagePlayer);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RankingActivity.this, Message.class);
                    intent.putExtra("playerId", player.getId());
                    intent.putExtra("playerName", player.getName());
                    intent.putExtra("playerImage", player.getImage());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }
}
