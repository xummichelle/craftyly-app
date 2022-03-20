package com.craftylyteam.craftylyapp1.survey;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.User;
import com.craftylyteam.craftylyapp1.main.MainActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;


public class Survey2Fragment extends Fragment implements View.OnClickListener{
    private View view;

    private Button btnRectStudent;
    private Button btnRectProf;
    private Button btnRectAspiring;
    private Button btnRectHobbyist;
    private Button btnRectOther;
    private Button btnSubmit;

    private User user;



    public Survey2Fragment() {
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
        view = inflater.inflate(R.layout.fragment_survey2, container, false);
        setUpButtonViews();

        return view;
    }

    private void setUpButtonViews(){
        btnRectStudent = (Button) view.findViewById(R.id.btn_rect_student);
        btnRectProf = (Button) view.findViewById(R.id.btn_rect_prof);
        btnRectAspiring = (Button) view.findViewById(R.id.btn_rect_aspiring);
        btnRectHobbyist = (Button) view.findViewById(R.id.btn_rect_hobbyist);
        btnRectOther = (Button) view.findViewById(R.id.btn_rect_other);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnRectStudent.setOnClickListener(this);
        btnRectProf.setOnClickListener(this);
        btnRectAspiring.setOnClickListener(this);
        btnRectHobbyist.setOnClickListener(this);
        btnRectOther.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rect_student:
                if (btnRectStudent.getTag().equals("unselected")) {
                    btnRectStudent.setBackgroundResource(R.drawable.pink_rectangle_select_background);
                    btnRectStudent.setTag("selected");
                } else {
                    btnRectStudent.setBackgroundResource(R.drawable.grey_rectangle_unselect_background);
                    btnRectStudent.setTag("unselected");
                }
                break;
            case R.id.btn_rect_prof:
                if (btnRectProf.getTag().equals("unselected")) {
                    btnRectProf.setBackgroundResource(R.drawable.pink_rectangle_select_background);
                    btnRectProf.setTag("selected");
                } else {
                    btnRectProf.setBackgroundResource(R.drawable.grey_rectangle_unselect_background);
                    btnRectProf.setTag("unselected");
                }
                break;
            case R.id.btn_rect_aspiring:
                if (btnRectAspiring.getTag().equals("unselected")) {
                    btnRectAspiring.setBackgroundResource(R.drawable.pink_rectangle_select_background);
                    btnRectAspiring.setTag("selected");
                } else {
                    btnRectAspiring.setBackgroundResource(R.drawable.grey_rectangle_unselect_background);
                    btnRectAspiring.setTag("unselected");
                }
                break;
            case R.id.btn_rect_hobbyist:
                if (btnRectHobbyist.getTag().equals("unselected")) {
                    btnRectHobbyist.setBackgroundResource(R.drawable.pink_rectangle_select_background);
                    btnRectHobbyist.setTag("selected");
                } else {
                    btnRectHobbyist.setBackgroundResource(R.drawable.grey_rectangle_unselect_background);
                    btnRectHobbyist.setTag("unselected");
                }
                break;
            case R.id.btn_rect_other:
                if (btnRectOther.getTag().equals("unselected")) {
                    btnRectOther.setBackgroundResource(R.drawable.pink_rectangle_select_background);
                    btnRectOther.setTag("selected");
                } else {
                    btnRectOther.setBackgroundResource(R.drawable.grey_rectangle_unselect_background);
                    btnRectOther.setTag("unselected");
                }
                break;
            case R.id.btn_submit:
                Survey3Fragment survey3Fragment= new Survey3Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), survey3Fragment, "")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
    }
}