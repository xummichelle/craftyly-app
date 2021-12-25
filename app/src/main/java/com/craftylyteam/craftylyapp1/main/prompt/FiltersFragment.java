package com.craftylyteam.craftylyapp1.main.prompt;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class FiltersFragment extends Fragment {
    View view;
    private ImageButton filtersArrowNavButton;
    private CheckBox checkBoxCharacters;
    private CheckBox checkBoxFeelings;
    private CheckBox checkBoxEnvironments;
    private SharedPreferences sharedPreferences;

    private BottomNavigationView bottomAppBar;
    private ImageView whiteGradient;

    private Set<String> filtersSet;

    public FiltersFragment() {
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
        view = inflater.inflate(R.layout.fragment_filters, container, false);
        bottomAppBar = getActivity().findViewById(R.id.bottom_app_bar);
        whiteGradient = getActivity().findViewById(R.id.white_gradient);
        bottomAppBar.setVisibility(View.GONE);
        whiteGradient.setVisibility(View.GONE);

        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

        filtersArrowNavButton = (ImageButton) view.findViewById(R.id.filters_arrow_nav_button);
        filtersArrowNavButton.setOnClickListener(v -> saveFilters());

//        checkboxes
        checkBoxCharacters = (CheckBox) view.findViewById(R.id.check_box_characters);
        checkBoxFeelings = (CheckBox) view.findViewById(R.id.check_box_feelings);
        checkBoxEnvironments = (CheckBox) view.findViewById(R.id.check_box_environments);
        themeUtils.onFiltersFragmentCreateSetTheme(getActivity(), view);
        loadFilters();

        return view;
    }

//    set checkboxes as checked or unchecked depending on filters set in shared preferences
    private void loadFilters() {
        filtersSet = new HashSet<String>(sharedPreferences.getStringSet(Constants.FILTERS_UNCHECKED, new HashSet<String>()));
        if (!filtersSet.isEmpty()){
            if (filtersSet.contains(Constants.FILTERS_CHARACTERS)) {
                checkBoxCharacters.setChecked(false);
            }
            if (filtersSet.contains(Constants.FILTERS_FEELINGS)) {
                checkBoxFeelings.setChecked(false);
            }
            if (filtersSet.contains(Constants.FILTERS_ENVIRONMENTS)) {
                checkBoxEnvironments.setChecked(false);
            }
        }
    }

//    add new stringset of filters chosen to shared preferences
    private void saveFilters() {
        Set<String> newChosenFilters = new HashSet<>();

        if (!checkBoxCharacters.isChecked()) {
            newChosenFilters.add(Constants.FILTERS_CHARACTERS);
        }
        if (!checkBoxFeelings.isChecked()) {
            newChosenFilters.add(Constants.FILTERS_FEELINGS);
        }
        if (!checkBoxEnvironments.isChecked()) {
            newChosenFilters.add(Constants.FILTERS_ENVIRONMENTS);
        }
        if (newChosenFilters.size() == Constants.ALL_FILTERS_ARRAY.length) {
            Log.d("sharedPrefChosenLength", String.valueOf(newChosenFilters) + Constants.ALL_FILTERS_ARRAY);
            Toast.makeText(getActivity(), "Please pick a filter.", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(Constants.FILTERS_UNCHECKED, newChosenFilters);
        editor.apply();
        Toast.makeText(getActivity(), "Filters saved!", Toast.LENGTH_SHORT).show();
        Log.d("sharedPrefFilter", String.valueOf(newChosenFilters));
        bottomAppBar.setVisibility(View.VISIBLE);
        whiteGradient.setVisibility(View.VISIBLE);
        openPromptsFragment();
    }

    private void openPromptsFragment(){
        PromptFragment promptFragment= new PromptFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), promptFragment, "")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}