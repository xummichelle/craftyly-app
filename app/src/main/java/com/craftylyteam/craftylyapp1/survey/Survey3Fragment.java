package com.craftylyteam.craftylyapp1.survey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.User;
import com.craftylyteam.craftylyapp1.main.MainActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;

import java.util.Set;


public class Survey3Fragment extends Fragment {
    View view;
    private CheckBox surveyCheckBoxCharacters;
    private CheckBox surveyCheckBoxFeelings;
    private CheckBox surveyCheckBoxEnvironments;
    private Button btnSubmit;
    private SharedPreferences sharedPreferences;

    private Set<String> filtersSet;
    private User user;


    public Survey3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_survey3, container, false);
        setUpViews();
        return view;
    }

    private User getUserFromIntent() {
        return (User) getActivity().getIntent().getSerializableExtra(Constants.EXTRA_USER);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        user = getUserFromIntent();
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivity(intent);
        getActivity().finish();
    }

    private void setUpViews(){
        surveyCheckBoxCharacters = (CheckBox) view.findViewById(R.id.survey_check_box_characters);
        surveyCheckBoxFeelings = (CheckBox) view.findViewById(R.id.survey_check_box_feelings);
        surveyCheckBoxEnvironments = (CheckBox) view.findViewById(R.id.survey_check_box_environments);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> goToMainActivity());
    }
}