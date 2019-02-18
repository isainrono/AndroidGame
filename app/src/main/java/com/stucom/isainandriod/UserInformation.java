package com.stucom.isainandriod;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stucom.isainandriod.AccessApi.apiRequest;
import com.stucom.isainandriod.model.MyToken;
import com.stucom.isainandriod.model.Player;

public class UserInformation extends AppCompatActivity {

    Context context = UserInformation.this;
    TextView name;
    TextView tScore;
    TextView level;
    TextView lScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        name = findViewById(R.id.userName);
        tScore = findViewById(R.id.userTotalScore);
        level = findViewById(R.id.userLastLevel);
        lScore = findViewById(R.id.userLastScore);

        apiRequest request = new apiRequest();

        request.showUserInformation(context);

        name.setText(MyToken.getPlayer().getName());
        tScore.setText(MyToken.getPlayer().getTotalScore());
        level.setText(MyToken.getPlayer().getLastLevel());
        lScore.setText(MyToken.getPlayer().getLastScore());

    }
}
