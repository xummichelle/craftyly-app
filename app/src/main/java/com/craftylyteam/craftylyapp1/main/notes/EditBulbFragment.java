package com.craftylyteam.craftylyapp1.main.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class EditBulbFragment extends Fragment implements View.OnClickListener, BulbAdapter.OnBulbListener {
    private View view;
    private MaterialButton doneBulbButton;
    private ImageView imageViewEditBulbDisplay;
    private String bulbTag;
    private String mNoteTitle;
    private String mNoteDesc;

//    recycler view stuff
    private RecyclerView recyclerViewLightBulb;
    private BulbAdapter bulbAdapter;
    private List<Bulb> bulbsList;

    public EditBulbFragment() {
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
        view = inflater.inflate(R.layout.fragment_edit_bulb, container, false);
//        get bulb tag from bundle
        bulbTag = Constants.CRAFTYLY_BULB;
        if (getArguments() != null) {
            Bundle receivedBulbTagBundle = this.getArguments();
            bulbTag = receivedBulbTagBundle.getString(Constants.FIRST_BULB_TAG_KEY);
            mNoteTitle = receivedBulbTagBundle.getString(Constants.FIRST_NOTE_TITLE_KEY);
            mNoteDesc = receivedBulbTagBundle.getString(Constants.FIRST_NOTE_DESC_KEY);
            setUpBulbDisplay(bulbTag);
        }
        setUpBulbRecyclerView();

        doneBulbButton = (MaterialButton) view.findViewById(R.id.done_bulb_button);
        doneBulbButton.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.done_bulb_button:
                EditNoteFragment editNoteFragment = new EditNoteFragment();
                Bundle newBulbTagBundle = new Bundle();
                String currentBulbTag = imageViewEditBulbDisplay.getTag().toString();
                newBulbTagBundle.putString(Constants.SECOND_BULB_TAG_KEY, currentBulbTag);
                newBulbTagBundle.putString(Constants.SECOND_NOTE_TITLE_KEY, mNoteTitle);
                newBulbTagBundle.putString(Constants.SECOND_NOTE_DESC_KEY, mNoteDesc);
                editNoteFragment.setArguments(newBulbTagBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), editNoteFragment, "")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    private void setUpBulbDisplay(String myBulbTag){
        imageViewEditBulbDisplay = view.findViewById(R.id.image_view_edit_bulb_display);
//        set tag of imageview to mybulbtag
        imageViewEditBulbDisplay.setTag(myBulbTag);
//        depending on tag, set imageview to bulb corresponding to tag

        switch (myBulbTag) {
            case Constants.CALICO_BULB:
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_calico_bulb_icon);
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_craftyly_calico_icon);
                break;
            case Constants.LEMON_BULB:
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_lemon_bulb_icon);
                break;
            case Constants.SUNSET_BULB:
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_sunset_bulb_icon);
                break;
            case Constants.CAMO_BULB:
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_camo_bulb_icon);
                break;
            default:
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_craftyly_bulb_icon);
                break;
        }

    }

    private void setUpBulbRecyclerView(){
        bulbsList = new ArrayList<>();
        bulbsList.add(new Bulb(Constants.CRAFTYLY_BULB));
        bulbsList.add(new Bulb(Constants.CALICO_BULB));
        bulbsList.add(new Bulb(Constants.CRAFTYLY_CALICO_BULB));
        bulbsList.add(new Bulb(Constants.LEMON_BULB));
        bulbsList.add(new Bulb(Constants.SUNSET_BULB));
        bulbsList.add(new Bulb(Constants.CAMO_BULB));
        recyclerViewLightBulb = view.findViewById(R.id.recycler_view_lightbulb);
        GridLayoutManager bulbsLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerViewLightBulb.setLayoutManager(bulbsLayoutManager);
        recyclerViewLightBulb.setHasFixedSize(true);

        bulbAdapter = new BulbAdapter(bulbsList, this);
        recyclerViewLightBulb.setAdapter(bulbAdapter);
    }

    @Override
    public void onBulbClick(int position) {
        Log.d("onbulbclick", String.valueOf(position));
        switch (position){
            default:
                imageViewEditBulbDisplay.setTag(Constants.CRAFTYLY_BULB);
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_craftyly_bulb_icon);
                break;
            case 1:
                imageViewEditBulbDisplay.setTag(Constants.CALICO_BULB);
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_calico_bulb_icon);
                break;
            case 2:
                imageViewEditBulbDisplay.setTag(Constants.CRAFTYLY_CALICO_BULB);
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_craftyly_calico_icon);
                break;
            case 3:
                imageViewEditBulbDisplay.setTag(Constants.LEMON_BULB);
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_lemon_bulb_icon);
                break;
            case 4:
                imageViewEditBulbDisplay.setTag(Constants.SUNSET_BULB);
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_sunset_bulb_icon);
                break;
            case 5:
                imageViewEditBulbDisplay.setTag(Constants.CAMO_BULB);
                imageViewEditBulbDisplay.setImageResource(R.drawable.ic_camo_bulb_icon);
                break;

        }
    }
}