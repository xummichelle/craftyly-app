package com.craftylyteam.craftylyapp1.main.prompt.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.notes.AcceptedListFragment;
import com.google.android.material.button.MaterialButton;


//fragment displaying subscription back information, and buttons to see more about the packs
public class ShopFragment extends Fragment {
    private View view;
    private MaterialButton incandescentButton;
    private MaterialButton fluorescentButton;
    private MaterialButton ledButton;
    private ImageView greenBlob;
    private ImageView pinkBlob;
    private ImageView blueBlob;



    public ShopFragment() {
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
        view = inflater.inflate(R.layout.fragment_shop, container, false);

        blueBlob = getActivity().findViewById(R.id.iv_blob_settings_blue);
        greenBlob = getActivity().findViewById(R.id.iv_blob_settings_green);
        pinkBlob = getActivity().findViewById(R.id.iv_blob_settings_pink);
        blueBlob.setVisibility(View.INVISIBLE);
        greenBlob.setVisibility(View.INVISIBLE);
        pinkBlob.setVisibility(View.INVISIBLE);

        incandescentButton = view.findViewById(R.id.button_incandescent_info);
        fluorescentButton = view.findViewById(R.id.button_fluorescent_info);
        ledButton = view.findViewById(R.id.button_led_info);
        incandescentButton.setOnClickListener(v -> openFragment(new IncanShopFragment()));
        fluorescentButton.setOnClickListener(v -> openFragment(new FluorescentShopFragment()));
        ledButton.setOnClickListener(v -> openFragment(new LEDShopFragment()));
        return view;
    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), fragment, "")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}