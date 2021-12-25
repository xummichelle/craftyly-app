package com.craftylyteam.craftylyapp1.main.prompt.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;

public class ThemesFragment extends Fragment {
    private View view;
    private RecyclerView themesRecyclerView;
    private String[] themesList = Constants.ALL_BULBS_ARRAY;
    private ThemesAdapter themesAdapter;
    private LinearLayoutManager themesLayoutManager;
    private ImageView greenBlob;
    private ImageView pinkBlob;
    private ImageView blueBlob;


    public ThemesFragment() {
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
        view = inflater.inflate(R.layout.fragment_themes, container, false);

        blueBlob = getActivity().findViewById(R.id.iv_blob_settings_blue);
        greenBlob = getActivity().findViewById(R.id.iv_blob_settings_green);
        pinkBlob = getActivity().findViewById(R.id.iv_blob_settings_pink);
        blueBlob.setVisibility(View.INVISIBLE);
        greenBlob.setVisibility(View.INVISIBLE);
        pinkBlob.setVisibility(View.INVISIBLE);
        setUpThemesRecyclerView();

        return view;
    }



    private void setUpThemesRecyclerView(){
        themesAdapter = new ThemesAdapter(themesList);

        themesRecyclerView = (RecyclerView) view.findViewById(R.id.themes_recycler_view);
        themesLayoutManager = new LinearLayoutManager(getActivity());
        themesRecyclerView.setLayoutManager(themesLayoutManager);
        themesRecyclerView.setAdapter(themesAdapter);
        themesRecyclerView.setHasFixedSize(true);


    }


}