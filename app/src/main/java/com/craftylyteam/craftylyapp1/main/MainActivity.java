package com.craftylyteam.craftylyapp1.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.craftylyteam.craftylyapp1.LightbulbMessageDialog;
import com.craftylyteam.craftylyapp1.main.notes.NotesListFragment;
import com.craftylyteam.craftylyapp1.main.prompt.PromptFragment;
import com.craftylyteam.craftylyapp1.survey.SurveyActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.User;
import com.craftylyteam.craftylyapp1.auth.AuthActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;
    private TextView messageTextView;
    private BottomNavigationView bottomAppBar;
    private ImageView whiteGradient;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = getUserFromIntent();
        initGoogleSignInClient();

        bottomAppBar = (BottomNavigationView) findViewById(R.id.bottom_app_bar);
        bottomAppBar.setOnNavigationItemSelectedListener(navListener);
        themeUtils.onMainActivityCreateSetTheme(this);
        bottomAppBar.setItemIconTintList(null);
        whiteGradient = (ImageView) findViewById(R.id.white_gradient);

        //set fragment prompt to show
        if (savedInstanceState == null) {
            changeFragment(new PromptFragment());
            bottomAppBar.setSelectedItemId(R.id.app_bar_home);
        }


    }



    private User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(Constants.EXTRA_USER);
    }

    public User getUser() {
        return user;
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        Boolean newTheme = sharedPreferences.getBoolean(Constants.THEME_NEW, true);
        if (newTheme) {
            recreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return true;
    }


//    bottom nav bar on select listener
private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_home:
                changeFragment(new PromptFragment());
                break;
            case R.id.app_bar_notes:
                changeFragment(new NotesListFragment());

                break;
            default:
                break;
        }
        return true;
    }
};

//    change fragment when click bott nav bar buttons
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }


}