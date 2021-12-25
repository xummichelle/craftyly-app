package com.craftylyteam.craftylyapp1.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.MainActivity;

import com.craftylyteam.craftylyapp1.survey.SurveyActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;


public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "AuthActivity";

    private AuthViewModel authViewModel;

    private FirebaseAuth firebaseAuth;
    private List<AuthUI.IdpConfig> providers;

    private SharedPreferences sharedPreferences;

    SignInButton googleSignInButton;
    Button anonymousSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        firebaseAuth = FirebaseAuth.getInstance();
        initSignInButton();
        initAuthViewModel();
        initSignInProviders();
    }

    private void initSignInButton() {
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(v -> signIn());
        anonymousSignInButton = findViewById(R.id.anonymous_sign_in_button);
        anonymousSignInButton.setOnClickListener(v -> signInAnonymously());
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void initSignInProviders() {
//        choose authentication providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

    }


    //    when google sign in button is clicked
    private void signIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(),
                Constants.SIGN_IN_REQUEST);
    }

//    when anonymous sign in button is clicked
    private void signInAnonymously() {
        authViewModel.mySignInAnonymously();
        authViewModel.anonymousUserLiveData.observe(this, authenticatedUser -> {
            createNewUser(authenticatedUser);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SIGN_IN_REQUEST) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            authViewModel.signInWithFirebaseUI(resultCode);
            authViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
                if (authenticatedUser.isNew) {
                    createNewUser(authenticatedUser);
                } else {
                    goToMainActivity(authenticatedUser);
                }

            });
        }
    }



    private void createNewUser(User authenticatedUser) {
//        called if authenticateduser.isnew. then calls the viewmodel method to create user
        authViewModel.createUser(authenticatedUser);
//        when user object is added to livedata, observe() is called
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {

                Log.d("authactivity", "createNewUser: user created");
            }
            goToIntroQuizActivity(user);
        });
    }



    //    redirect to intro quiz
//    TODO actually make an intro quiz activity and go to it
    private void goToIntroQuizActivity(User user) {
        Intent intent = new Intent(AuthActivity.this, SurveyActivity.class);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);
        Log.d("authactivity", "goToMainActivity: intro quizzed");
        finish();
    }

    //    redirect to main activity
    private void goToMainActivity(User user) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);
        finish();
    }
}