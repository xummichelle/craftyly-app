package com.craftylyteam.craftylyapp1.main.prompt.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.craftylyteam.craftylyapp1.R;


//fragment displaying fluorescent pack information
public class FluorescentShopFragment extends Fragment {
    private View view;
    private ImageView greenBlob;
    private ImageView pinkBlob;
    private ImageView blueBlob;




    public FluorescentShopFragment() {
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
        view = inflater.inflate(R.layout.fragment_fluorescent_shop, container, false);

        blueBlob = getActivity().findViewById(R.id.iv_blob_settings_blue);
        greenBlob = getActivity().findViewById(R.id.iv_blob_settings_green);
        pinkBlob = getActivity().findViewById(R.id.iv_blob_settings_pink);
        blueBlob.setVisibility(View.INVISIBLE);
        greenBlob.setVisibility(View.INVISIBLE);
        pinkBlob.setVisibility(View.INVISIBLE);
        return view;
    }
}