package com.stucom.isainandriod.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.stucom.isainandriod.SettingActivity;

import static android.content.Context.MODE_PRIVATE;

public class MyToken {

    private static MyToken instance;
    public String authToken;
    public Context context;


    public MyToken(Context context) {
        this.context = context;
    }

    public String getAuthToken() {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("token", authToken);
        edit.apply();
    }

    public static MyToken getInstance(Context context) {
        if (instance == null) {
            instance = new MyToken(context.getApplicationContext());
        }
        return instance;
    }

}
