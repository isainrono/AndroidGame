package com.stucom.isainandriod.AccessApi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.stucom.isainandriod.Message;
import com.stucom.isainandriod.MyVolley;
import com.stucom.isainandriod.model.APIResponse;
import com.stucom.isainandriod.model.MyToken;
import com.stucom.isainandriod.model.Player;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class apiRequest extends AppCompatActivity {

    private final String URI = "https://api.flx.cat/dam2game";

    Context context1 = apiRequest.this;

    public apiRequest(){

    }


    public void showUserInformation(final Context context) {

        //final String URL = String.format("https://api.flx.cat/dam2game/user/"+ playerId +"?token=" + MyToken.getInstance(context).getAuthToken());
        final String URL = String.format("https://api.flx.cat/dam2game/user?token=11f40fa26aff478fd52c0ede8b890e818c589397cbc12303fb0d328760653baaa79e0ae7ddc97c955c258539ed8881f23eed11e97b690bdbb5b64df48c1d19ca");

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
                        //Picasso.get().load(selectedPlayer.getImage()).into(ImagePlayer);

                        MyToken.setPlayerInformation(selectedPlayer);
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
                params.put("token", MyToken.getInstance(context).getAuthToken());
                return params;
            }

        };

        MyVolley.getInstance(context1).add(request);
    }
}
