package com.craftylyteam.craftylyapp1.utils;

import android.app.Activity;
import android.content.Context;
import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.craftylyteam.craftylyapp1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

//public methods that update views depending on chosen theme
public class themeUtils {
    private static String myTheme;


    public static void changeToThemeByContext(Context context, String theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        myTheme = theme;
        editor.putString(Constants.CURRENT_THEME, myTheme);
        editor.putBoolean(Constants.THEME_NEW, true);
        editor.apply();
        Log.d("themeutils", myTheme);

    }

    public static void onSplashActivityCreateSetTheme(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        switch (myTheme) {
            case Constants.CALICO_BULB:
                activity.setTheme(R.style.SplashActivityCalicoStyle);
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                activity.setTheme(R.style.SplashActivityCraftylyCalicoStyle);
                break;
            case Constants.LEMON_BULB:
                activity.setTheme(R.style.SplashActivityLemonStyle);
                break;
            case Constants.SUNSET_BULB:
                activity.setTheme(R.style.SplashActivitySunsetStyle);
                break;
            case Constants.CAMO_BULB:
                activity.setTheme(R.style.SplashActivityCamoStyle);
                break;
            default:
                activity.setTheme(R.style.SplashActivityStyle);
                break;
    }
    }

    public static void onMainActivityCreateSetTheme(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_app_bar);


        switch (myTheme) {
            case Constants.CRAFTYLY_BULB:
            case Constants.CRAFTYLY_CALICO_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(bottomNavigationView.getBackground()),
                        ContextCompat.getColor(activity, R.color.tangerine)
                );
                break;
            case Constants.CALICO_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(bottomNavigationView.getBackground()),
                        ContextCompat.getColor(activity, R.color.calico_orange)
                );
                break;
            case Constants.LEMON_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(bottomNavigationView.getBackground()),
                        ContextCompat.getColor(activity, R.color.lemon_light_yellow)
                );
                break;
            case Constants.SUNSET_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(bottomNavigationView.getBackground()),
                        ContextCompat.getColor(activity, R.color.sunset_red)
                );
                break;
            case Constants.CAMO_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(bottomNavigationView.getBackground()),
                        ContextCompat.getColor(activity, R.color.camo_light_green)
                );
                break;
            default:
                break;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.THEME_NEW, false);
        editor.apply();


    }

    public static void onPromptFragmentCreateSetTheme(Context context, View view) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        ImageView welcomeHeaderImageView = view.findViewById(R.id.welcome_header_image_view);
        switch (myTheme) {
            case Constants.CRAFTYLY_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(welcomeHeaderImageView.getDrawable()),
                        ContextCompat.getColor(context, R.color.med_purple)
                );
                welcomeHeaderImageView.setTag(Constants.CRAFTYLY_BULB);
                break;
            case Constants.CALICO_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(welcomeHeaderImageView.getDrawable()),
                        ContextCompat.getColor(context, R.color.calico_light_brown)
                );
                welcomeHeaderImageView.setTag(Constants.CALICO_BULB);
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(welcomeHeaderImageView.getDrawable()),
                        ContextCompat.getColor(context, R.color.med_purple)
                );
                welcomeHeaderImageView.setTag(Constants.CRAFTYLY_CALICO_BULB);
                break;
            case Constants.LEMON_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(welcomeHeaderImageView.getDrawable()),
                        ContextCompat.getColor(context, R.color.lemon_bright_green)
                );
                welcomeHeaderImageView.setTag(Constants.LEMON_BULB);
                break;
            case Constants.SUNSET_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(welcomeHeaderImageView.getDrawable()),
                        ContextCompat.getColor(context, R.color.sunset_deep_purple)
                );
                welcomeHeaderImageView.setTag(Constants.SUNSET_BULB);
                break;
            case Constants.CAMO_BULB:
                DrawableCompat.setTint(
                        DrawableCompat.wrap(welcomeHeaderImageView.getDrawable()),
                        ContextCompat.getColor(context, R.color.camo_light_blue)
                );
                welcomeHeaderImageView.setTag(Constants.CAMO_BULB);
                break;
            default:
                break;
        }


    }

    public static void onFiltersFragmentCreateSetTheme(Context context, View view) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        ImageView blobBlue = view.findViewById(R.id.image_view_blob_filter_blue);
        ImageView blobPink = view.findViewById(R.id.image_view_blob_filter_pink);
        ImageView blobGreen = view.findViewById(R.id.image_view_blob_filter_green);
        switch (myTheme) {
            case Constants.CRAFTYLY_BULB:
            case Constants.CRAFTYLY_CALICO_BULB:
                blobBlue.setImageResource(R.drawable.blob_filter_blue);
                blobPink.setImageResource(R.drawable.blob_filter_pink);
                blobGreen.setImageResource(R.drawable.blob_filter_green);

                break;
            case Constants.CALICO_BULB:
                blobBlue.setImageResource(R.drawable.blob_filter_calico_orange);
                blobPink.setImageResource(R.drawable.blob_filter_calico_white);
                blobGreen.setImageResource(R.drawable.blob_filter_calico_brown);

                break;

            case Constants.LEMON_BULB:
                blobBlue.setImageResource(R.drawable.blob_filter_lemon_dark_green);
                blobPink.setImageResource(R.drawable.blob_filter_lemon_yellow);
                blobGreen.setImageResource(R.drawable.blob_filter_lemon_green);

                break;
            case Constants.SUNSET_BULB:
                blobBlue.setImageResource(R.drawable.blob_filter_sunset_purple);
                blobPink.setImageResource(R.drawable.blob_filter_sunset_red);
                blobGreen.setImageResource(R.drawable.blob_filter_sunset_orange);

                break;
            case Constants.CAMO_BULB:
                blobBlue.setImageResource(R.drawable.blob_filter_camo_brown);
                blobPink.setImageResource(R.drawable.blob_filter_camo_light_green);
                blobGreen.setImageResource(R.drawable.blob_filter_camo_light_blue);

                break;
            default:
                break;
        }
    }

    public static void onSettingsActivityCreateSetTheme(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        ImageView blobBlue = activity.findViewById(R.id.iv_blob_settings_blue);
        ImageView blobPink = activity.findViewById(R.id.iv_blob_settings_pink);
        ImageView blobGreen = activity.findViewById(R.id.iv_blob_settings_green);
        switch (myTheme) {
            case Constants.CRAFTYLY_BULB:
            case Constants.CRAFTYLY_CALICO_BULB:
                blobBlue.setImageResource(R.drawable.blob_settings_blue);
                blobPink.setImageResource(R.drawable.blob_settings_pink);
                blobGreen.setImageResource(R.drawable.blob_settings_green);

                break;
            case Constants.CALICO_BULB:
                blobBlue.setImageResource(R.drawable.blob_settings_calico_orange);
                blobPink.setImageResource(R.drawable.blob_settings_calico_white);
                blobGreen.setImageResource(R.drawable.blob_settings_calico_brown);

                break;

            case Constants.LEMON_BULB:
                blobBlue.setImageResource(R.drawable.blob_settings_lemon_dark_green);
                blobPink.setImageResource(R.drawable.blob_settings_lemon_yellow);
                blobGreen.setImageResource(R.drawable.blob_settings_lemon_bright_green);

                break;
            case Constants.SUNSET_BULB:
                blobBlue.setImageResource(R.drawable.blob_settings_sunset_purple);
                blobPink.setImageResource(R.drawable.blob_settings_sunset_red);
                blobGreen.setImageResource(R.drawable.blob_settings_sunset_orange);

                break;
            case Constants.CAMO_BULB:
                blobBlue.setImageResource(R.drawable.blob_settings_camo_brown);
                blobPink.setImageResource(R.drawable.blob_settings_camo_light_green);
                blobGreen.setImageResource(R.drawable.blob_settings_camo_light_blue);

                break;
            default:
                break;
        }
    }

    public static void onNotesListFragmentCreateSetTheme(Context context, View view) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        MaterialButton newNoteButton =  view.findViewById(R.id.new_note_button);
        switch (myTheme) {
            case Constants.CRAFTYLY_BULB:
            case Constants.CRAFTYLY_CALICO_BULB:
                newNoteButton.setIconTintResource(R.color.med_purple);
                newNoteButton.setTextColor(ContextCompat.getColor(context, R.color.med_purple));
                break;
            case Constants.CALICO_BULB:
                newNoteButton.setIconTintResource(R.color.calico_orange);
                newNoteButton.setTextColor(ContextCompat.getColor(context, R.color.calico_orange));

                break;

            case Constants.LEMON_BULB:
                newNoteButton.setIconTintResource(R.color.lemon_gold);
                newNoteButton.setTextColor(ContextCompat.getColor(context, R.color.lemon_gold));

                break;
            case Constants.SUNSET_BULB:
                newNoteButton.setIconTintResource(R.color.sunset_orange);
                newNoteButton.setTextColor(ContextCompat.getColor(context, R.color.sunset_orange));

                break;
            case Constants.CAMO_BULB:
                newNoteButton.setIconTintResource(R.color.camo_green);
                newNoteButton.setTextColor(ContextCompat.getColor(context, R.color.camo_green));

                break;
            default:
                break;
        }
    }

    public static void onEditNoteFragmentCreateSetTheme(Context context, View view) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        MaterialButton saveNoteButton =  view.findViewById(R.id.save_note_button);
        switch (myTheme) {
            case Constants.CRAFTYLY_BULB:
                saveNoteButton.setIconTintResource(R.color.med_purple);
                saveNoteButton.setTextColor(ContextCompat.getColor(context, R.color.med_purple));
                break;
            case Constants.CALICO_BULB:
                saveNoteButton.setIconTintResource(R.color.calico_orange);
                saveNoteButton.setTextColor(ContextCompat.getColor(context, R.color.calico_orange));
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                saveNoteButton.setIconTintResource(R.color.med_purple);
                saveNoteButton.setTextColor(ContextCompat.getColor(context, R.color.med_purple));
                break;
            case Constants.LEMON_BULB:
                saveNoteButton.setIconTintResource(R.color.lemon_gold);
                saveNoteButton.setTextColor(ContextCompat.getColor(context, R.color.lemon_gold));

                break;
            case Constants.SUNSET_BULB:
                saveNoteButton.setIconTintResource(R.color.sunset_orange);
                saveNoteButton.setTextColor(ContextCompat.getColor(context, R.color.sunset_orange));

                break;
            case Constants.CAMO_BULB:
                saveNoteButton.setIconTintResource(R.color.camo_green);
                saveNoteButton.setTextColor(ContextCompat.getColor(context, R.color.camo_green));

                break;
            default:
                break;
        }
    }

    public static String getCurrentThemeByContext(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        myTheme = sharedPreferences.getString(Constants.CURRENT_THEME, Constants.CRAFTYLY_BULB);
        Log.d("themeutilsgetmethod", myTheme);
        return myTheme;
    }


}
