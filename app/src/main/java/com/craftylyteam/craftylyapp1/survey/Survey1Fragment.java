package com.craftylyteam.craftylyapp1.survey;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.AuthActivity;
import com.craftylyteam.craftylyapp1.auth.User;
import com.craftylyteam.craftylyapp1.main.MainActivity;
import com.craftylyteam.craftylyapp1.main.notes.AcceptedListFragment;
import com.craftylyteam.craftylyapp1.utils.Constants;


public class Survey1Fragment extends Fragment implements View.OnClickListener{
    private View view;

    private Button btnCircle1;
    private Button btnCircle2;
    private Button btnCircle3;
    private Button btnCircle4;
    private Button btnCircle5;
    private Button btnCircle6;
    private Button btnCircle7;
    private Button btnCircle8;
    private Button btnCircle9;
    private Button btnCircle10;

    private Button btnSubmit;




    public Survey1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_survey1, container, false);

        setUpButtonViews();





        return view;
    }

    private void setUpButtonViews(){
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnCircle1 = view.findViewById(R.id.btn_circle1);
        btnCircle2 = view.findViewById(R.id.btn_circle2);
        btnCircle3 = view.findViewById(R.id.btn_circle3);
        btnCircle4 = view.findViewById(R.id.btn_circle4);
        btnCircle5 = view.findViewById(R.id.btn_circle5);
        btnCircle6 = view.findViewById(R.id.btn_circle6);
        btnCircle7 = view.findViewById(R.id.btn_circle7);
        btnCircle8 = view.findViewById(R.id.btn_circle8);
        btnCircle9 = view.findViewById(R.id.btn_circle9);
        btnCircle10 = view.findViewById(R.id.btn_circle10);
        btnSubmit.setOnClickListener(this);
        btnCircle1.setOnClickListener(this);
        btnCircle2.setOnClickListener(this);
        btnCircle3.setOnClickListener(this);
        btnCircle4.setOnClickListener(this);
        btnCircle5.setOnClickListener(this);
        btnCircle6.setOnClickListener(this);
        btnCircle7.setOnClickListener(this);
        btnCircle8.setOnClickListener(this);
        btnCircle9.setOnClickListener(this);
        btnCircle10.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Survey2Fragment survey2Fragment= new Survey2Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), survey2Fragment, "")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btn_circle1:
                if (btnCircle1.getTag().equals("unselected")) {
                    btnCircle1.setBackgroundResource(R.drawable.green_circle);
                    btnCircle1.setTag("selected");
                } else {
                    btnCircle1.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle1.setTag("unselected");
                }
                break;
            case R.id.btn_circle2:
                if (btnCircle2.getTag().equals("unselected")) {
                    btnCircle2.setBackgroundResource(R.drawable.blue_circle);
                    btnCircle2.setTag("selected");
                } else {
                    btnCircle2.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle2.setTag("unselected");
                }
                break;
            case R.id.btn_circle3:
                if (btnCircle3.getTag().equals("unselected")) {
                    btnCircle3.setBackgroundResource(R.drawable.pink_circle);
                    btnCircle3.setTag("selected");
                } else {
                    btnCircle3.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle3.setTag("unselected");
                }
                break;
            case R.id.btn_circle4:
                if (btnCircle4.getTag().equals("unselected")) {
                    btnCircle4.setBackgroundResource(R.drawable.green_circle);
                    btnCircle4.setTag("selected");
                } else {
                    btnCircle4.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle4.setTag("unselected");
                }
                break;
            case R.id.btn_circle5:
                if (btnCircle5.getTag().equals("unselected")) {
                    btnCircle5.setBackgroundResource(R.drawable.pink_circle);
                    btnCircle5.setTag("selected");
                } else {
                    btnCircle5.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle5.setTag("unselected");
                }
                break;
            case R.id.btn_circle6:
                if (btnCircle6.getTag().equals("unselected")) {
                    btnCircle6.setBackgroundResource(R.drawable.blue_circle);
                    btnCircle6.setTag("selected");
                } else {
                    btnCircle6.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle6.setTag("unselected");
                }
                break;
            case R.id.btn_circle7:
                if (btnCircle7.getTag().equals("unselected")) {
                    btnCircle7.setBackgroundResource(R.drawable.green_circle);
                    btnCircle7.setTag("selected");
                } else {
                    btnCircle7.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle7.setTag("unselected");
                }
                break;
            case R.id.btn_circle8:
                if (btnCircle8.getTag().equals("unselected")) {
                    btnCircle8.setBackgroundResource(R.drawable.pink_circle);
                    btnCircle8.setTag("selected");
                } else {
                    btnCircle8.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle8.setTag("unselected");
                }
                break;
            case R.id.btn_circle9:
                if (btnCircle9.getTag().equals("unselected")) {
                    btnCircle9.setBackgroundResource(R.drawable.blue_circle);
                    btnCircle9.setTag("selected");
                } else {
                    btnCircle9.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle9.setTag("unselected");
                }
                break;
            case R.id.btn_circle10:
                if (btnCircle10.getTag().equals("unselected")) {
                    btnCircle10.setBackgroundResource(R.drawable.blue_circle);
                    btnCircle10.setTag("selected");
                } else {
                    btnCircle10.setBackgroundResource(R.drawable.grey_circle);
                    btnCircle10.setTag("unselected");
                }
                break;
            default:
                break;
        }

    }
}