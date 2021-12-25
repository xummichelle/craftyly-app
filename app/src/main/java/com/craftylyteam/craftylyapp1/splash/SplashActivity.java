package com.craftylyteam.craftylyapp1.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.craftylyteam.craftylyapp1.auth.AuthActivity;
import com.craftylyteam.craftylyapp1.auth.User;
import com.craftylyteam.craftylyapp1.intro.IntroActivity;
import  com.craftylyteam.craftylyapp1.main.MainActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;


public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        based on the selected theme, show the proper splash screen background
        themeUtils.onSplashActivityCreateSetTheme(this);
        initSplashViewModel();

        checkIfUserIsAuthenticated();
    }

    private void initSplashViewModel() {
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }


    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedLiveData.observe(this, user -> {
            if (!user.isAuthenticated) {
//                if user is not authenticated

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
//                    if it is the first run
                    Log.d("firstrun", "checkIfUserIsAuthenticated: true");
                    goToIntroActivity();
                } else {
//                    if it is not the first run
                    goToAuthInActivity();
                }
                finish();
            } else {
                getUserFromDatabase(user.getUid());
            }
        });
    }

    private void goToIntroActivity() {
        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
        startActivity(intent);
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userLiveData.observe(this, user -> {
            goToMainActivity(user);
            finish();
        });
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);
    }
}