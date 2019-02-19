package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.stucom.isainandriod.model.APIResponse;
import com.stucom.isainandriod.model.MyToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PlayActivity extends AppCompatActivity {

    TextView level;
    TextView score;
    Button bChange;
    Button bShow;
    Context context = PlayActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Context context = PlayActivity.this;
        level = findViewById(R.id.level);
        score = findViewById(R.id.score);
        bChange = findViewById(R.id.btnChangeDatas);

        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDatas();
            }
        });

        Toast.makeText(PlayActivity.this, "token"+ MyToken.getInstance(context).getAuthToken(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void changeDatas(){
        final String URL = String.format("https://api.flx.cat/dam2game/user/score");

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String json = response.toString();
                        Gson gson = new Gson();

                        Log.d("isain", "llega");
                        Type typeToken = new TypeToken<APIResponse<String>>() {}.getType();
                        APIResponse <String> apiResponse = gson.fromJson(json, typeToken);

                        if(apiResponse.getErrorCode() == 0) {
                            Toast.makeText(PlayActivity.this, "ok Cambiado score level", Toast.LENGTH_LONG).show();
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
                params.put("level", level.getText().toString());
                params.put("score", score.getText().toString());
                return params;
            }
        };
        MyVolley.getInstance(this).add(request);

    }

}
