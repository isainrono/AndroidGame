package com.stucom.isainandriod;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stucom.isainandriod.model.APIResponse;
import com.stucom.isainandriod.model.MyToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    EditText edName;
    EditText  edEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "name"+ edName.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SettingActivity.this, "email :" + edEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.d("llega","aqui");
                registro();

            }
        });
    }




    public void registro() {

        final String URL = "https://api.flx.cat/dam2game/register";

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {
                        String json  = response.toString();
                        Gson gson = new Gson();

                        Type typeToken = new TypeToken<APIResponse<Integer>>() {}.getType();
                        APIResponse <Integer> apiResponse = gson.fromJson(json, typeToken);



                        if(apiResponse.getErrorCode() == 0) {
                            final EditText editText = new EditText(SettingActivity.this);
                            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                            builder.setMessage("Ingresa aqui el token que se ha enviado a tu email!!!")
                                    .setTitle("Validacion de Usuario")
                                    .setView(editText)
                                    .setCancelable(false)
                                    .setNegativeButton("Cancelar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            })
                                    .setPositiveButton("Continuar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    String mailtoken = editText.getText().toString();
                                                    String email = edEmail.getText().toString();
                                                    Toast.makeText(SettingActivity.this,"email" + email, Toast.LENGTH_LONG).show();
                                                    //Toast.makeText(SettingActivity.this, "token"+ mailtoken, Toast.LENGTH_LONG).show();
                                                    confirmarRegistro(mailtoken, email);
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                        } else {

                            Toast.makeText(SettingActivity.this, "Error con el mail", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                String message = error.toString();
                NetworkResponse response = error.networkResponse;
                if(response != null){
                    message = response.statusCode + " " + message;
                }

                Toast.makeText(SettingActivity.this, "ERROR" + message, Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", edEmail.getText().toString());

                return params;
            }
        };
        MyVolley.getInstance(this).add(request);
        // finish

    }


    public void confirmarRegistro(final String tokenMail, final String email) {
        final String URL = "https://api.flx.cat/dam2game/register";

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override public void onResponse(String response) {
                        String json  = response.toString();
                        Gson gson = new Gson();

                        Type typeToken = new TypeToken<APIResponse<String>>() {}.getType();
                        APIResponse <String> apiResponse = gson.fromJson(json, typeToken);



                        if(apiResponse.getErrorCode() == 0) {
                            Toast.makeText(SettingActivity.this, "token" + apiResponse.getData(), Toast.LENGTH_LONG).show();
                            apiResponse.getData();

                            Context context = SettingActivity.this;
                            MyToken.getInstance(context).setAuthToken(apiResponse.getData());


                        } else {

                            Toast.makeText(SettingActivity.this, "Error con el mail", Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                String message = error.toString();
                NetworkResponse response = error.networkResponse;
                if(response != null){
                    message = response.statusCode + " " + message;
                }

                Toast.makeText(SettingActivity.this, "ERROR" + message, Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("verify", tokenMail);
                return params;
            }
        };
        MyVolley.getInstance(this).add(request);
        // finish
    }






    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        edName.setText(name);
        edEmail.setText(email);


    }

    @Override
    protected void onPause(){
        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", name);
        edit.putString("email", email);
        edit.apply();

        super.onPause();
    }


}
