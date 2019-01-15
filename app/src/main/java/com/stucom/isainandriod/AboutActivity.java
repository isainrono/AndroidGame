package com.stucom.isainandriod;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.stucom.isainandriod.model.MyToken;

import static android.content.Intent.ACTION_DIAL;

public class AboutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Context context = AboutActivity.this;

        if (MyToken.getInstance(context).getAuthToken() != null) {

            ImageButton map = findViewById(R.id.btnMap);

            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("geo:41.3857089,2.1651619");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

        } else {
            Intent intent = new Intent(AboutActivity.this, SettingActivity.class);
            startActivity(intent);
        }





    }


}
