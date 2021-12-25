package com.craftylyteam.craftylyapp1.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.prompt.settings.SettingsFragment;
import com.craftylyteam.craftylyapp1.utils.Constants;

public class SurveyActivity extends AppCompatActivity {
    private TextView surveyTitleTextView;
    private TextView surveyQuestionTextView;
    private ProgressBar surveyProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        surveyTitleTextView = (TextView) findViewById(R.id.survey_title_text_view);
        surveyQuestionTextView = (TextView) findViewById(R.id.survey_question_text_view);
        surveyProgressIndicator = (ProgressBar) findViewById(R.id.survey_progress_indicator);

        changeFragment(new Survey1Fragment());
        FragmentManager fragmentManager = getSupportFragmentManager();
//        when the backstack is changed, get the current fragment and update heading and progress bar
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = fragmentManager.findFragmentById(R.id.survey_fragment_container);
                String currentFragment = fragment.getClass().getSimpleName();
                setSettingHeaderAndNavButton(currentFragment);
            }
        });

    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.survey_fragment_container, fragment, "")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    //    depending on which fragment is shown, update views
    private void setSettingHeaderAndNavButton(String currentFragment) {
        String fr = currentFragment;
        if (fr != null) {
            if (fr.equals("Survey1Fragment")) {
                surveyTitleTextView.setText("Let's start...");
                surveyQuestionTextView.setText("What medium of art do you enjoy? \n (select all that apply)");
                ObjectAnimator.ofInt(surveyProgressIndicator, "progress", 20)
                        .setDuration(300)
                        .start();
            } else if (fr.equals("Survey2Fragment")) {
                surveyTitleTextView.setText("Tell us about yourself!");
                surveyQuestionTextView.setText("Are you an...");
                ObjectAnimator.ofInt(surveyProgressIndicator, "progress", 40)
                        .setDuration(300)
                        .start();
            }
        }
    }
}