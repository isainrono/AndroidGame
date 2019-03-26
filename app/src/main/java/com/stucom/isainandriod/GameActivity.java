package com.stucom.isainandriod;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

public class GameActivity extends AppCompatActivity implements WormyView.WormyListener, SensorEventListener {

    SoundPool soundPool;
    boolean loaded;
    MediaPlayer mediaPlayer;
    private WormyView wormyView;
    private TextView tvScore;
    Context context = GameActivity.this;
    SensorEvent sensorEvent;
    private SensorManager sensorManager;
    float ax, ay;
    int coin, gameover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d("isain", "d" + MyToken.getInstance(context).getAuthToken());
        wormyView = findViewById(R.id.wormyView);
        Button btnNewGame  = findViewById(R.id.btnNewGame);
        tvScore = findViewById(R.id.tvScore);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvScore.setText("0");
                wormyView.newGame();
            }
        });
        wormyView.setWormyListener(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.music2);
        mediaPlayer.setLooping(true);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(15).build();
        }
        else {
            soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 0);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        coin = soundPool.load(this, R.raw.coin, 1);
        gameover = soundPool.load(this, R.raw.gover, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
        mediaPlayer.pause();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_A: wormyView.update(0, +10); break;
            case KeyEvent.KEYCODE_Q: wormyView.update(0, -10); break;
            case KeyEvent.KEYCODE_O: wormyView.update(-10, 0); break;
            case KeyEvent.KEYCODE_P: wormyView.update(+10, 0); break;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void scoreUpdated(View view, int score) {
        soundPool.play(coin, 1f, 1f, 1, 0, 1f);
        tvScore.setText(String.valueOf(score));
    }

    @Override
    public void gameLost(View view) {
        soundPool.play(gameover, 1f, 1f, 1, 0, 1f);
        Toast.makeText(this, "you Lost", Toast.LENGTH_LONG).show();
        changeDatas();

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Quieres Jugar de nuevo??")
                .setTitle("GAME OVER")
                .setCancelable(false)
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(GameActivity.this, RankingActivity.class);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tvScore.setText("0");
                                wormyView.newGame();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();


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
                            Toast.makeText(GameActivity.this, "ok Cambiado score level", Toast.LENGTH_LONG).show();
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
                params.put("level", "0");
                params.put("score", tvScore.getText().toString());
                return params;
            }
        };
        MyVolley.getInstance(this).add(request);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ax = event.values[0];
        ay = event.values[1];
        wormyView.update(-ax, ay);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
