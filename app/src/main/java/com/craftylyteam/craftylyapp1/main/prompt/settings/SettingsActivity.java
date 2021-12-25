package com.craftylyteam.craftylyapp1.main.prompt.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.AuthActivity;
import com.craftylyteam.craftylyapp1.main.MainActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ImageButton settingsArrowNavButton;
    private TextView settingsHeaderTextView;
    private ImageView greenBlob;
    private ImageView pinkBlob;
    private ImageView blueBlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        blueBlob = findViewById(R.id.iv_blob_settings_blue);
        greenBlob = findViewById(R.id.iv_blob_settings_green);
        pinkBlob = findViewById(R.id.iv_blob_settings_pink);


        settingsArrowNavButton = (ImageButton) findViewById(R.id.settings_arrow_nav_button);
        settingsHeaderTextView = (TextView) findViewById(R.id.settings_header_text_view);
        themeUtils.onSettingsActivityCreateSetTheme(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
//        when the backstack is changed, get the current fragment and update the top bar text and
//        what the back button does.
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = fragmentManager.findFragmentById(R.id.settings_fragment_container);
                String currentFragment = fragment.getClass().getSimpleName();
                setSettingHeaderAndNavButton(currentFragment);
            }
        });

        changeFragment(new SettingsFragment(), Constants.SETTINGS_FRAGMENT);

    }

//when the user signs out, bring them to sign in page
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.d("SettingsActivity", "auth state changed");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goToAuthInActivity();
        }
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SettingsActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    public void changeFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.settings_fragment_container, fragment, tag)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

//    depending on which fragment is shown, update views
    private void setSettingHeaderAndNavButton(String currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fr = currentFragment;
        if (fr!=null) {
            if (fr.equals("SettingsFragment")) {
                settingsHeaderTextView.setText("Settings");
                settingsArrowNavButton.setOnClickListener(v -> finish());

            } else if (fr.equals("ThemesFragment")) {
                settingsHeaderTextView.setText("Themes");
                settingsArrowNavButton.setOnClickListener(v -> popBackStackShowBlobChangeTheme());
            } else if (fr.equals("ShopFragment")) {
                settingsHeaderTextView.setText("Shop");
                settingsArrowNavButton.setOnClickListener(v -> popBackStackShowBlob());
            } else if (fr.equals("AboutFragment")) {
                settingsHeaderTextView.setText("About Craftyly");
                settingsArrowNavButton.setOnClickListener(v -> popBackStackShowBlob());
            } else if (fr.equals("IncanShopFragment")) {
                settingsHeaderTextView.setText("Incandescent \nPack");
                settingsArrowNavButton.setOnClickListener(v -> fragmentManager.popBackStack());
            } else if (fr.equals("FluorescentShopFragment")) {
                settingsHeaderTextView.setText("Fluorescent \nPack");
                settingsArrowNavButton.setOnClickListener(v -> fragmentManager.popBackStack());
            } else if (fr.equals("LEDShopFragment")) {
                settingsHeaderTextView.setText("LED \nPack");
                settingsArrowNavButton.setOnClickListener(v -> fragmentManager.popBackStack());
            }
        }
    }

    public void popBackStackShowBlobChangeTheme(){
        themeUtils.onSettingsActivityCreateSetTheme(this);
        blueBlob.setVisibility(View.VISIBLE);
        greenBlob.setVisibility(View.VISIBLE);
        pinkBlob.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void popBackStackShowBlob() {
        blueBlob.setVisibility(View.VISIBLE);
        greenBlob.setVisibility(View.VISIBLE);
        pinkBlob.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }


}