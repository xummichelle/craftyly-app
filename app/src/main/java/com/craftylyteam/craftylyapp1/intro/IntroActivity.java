package com.craftylyteam.craftylyapp1.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.AuthActivity;
import com.craftylyteam.craftylyapp1.splash.SplashActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;

public class IntroActivity extends AppCompatActivity {

    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getStartedButton = (Button) findViewById(R.id.get_started_button);

        getStartedButton.setOnClickListener(v -> openAuthActivity());

    }

    private void openAuthActivity() {
        Intent intent = new Intent(IntroActivity.this, AuthActivity.class);
        startActivity(intent);
    }
}