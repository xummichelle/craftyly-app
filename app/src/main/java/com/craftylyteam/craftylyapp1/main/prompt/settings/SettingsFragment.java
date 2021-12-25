package com.craftylyteam.craftylyapp1.main.prompt.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.auth.AuthActivity;
import com.craftylyteam.craftylyapp1.auth.User;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends PreferenceFragmentCompat {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection(Constants.USERS);
    private DocumentReference currentUserRef;



    Preference signOutPreference;
    private ImageView greenBlob;
    private ImageView pinkBlob;
    private ImageView blueBlob;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        signOutPreference = findPreference("signOut");
        //        get current user
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser.isAnonymous())
            signOutPreference.setTitle("Sign In");
        else signOutPreference.setTitle("Sign Out");

        signOutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                signOut();
                return true;
            }
        });

        blueBlob = getActivity().findViewById(R.id.iv_blob_settings_blue);
        greenBlob = getActivity().findViewById(R.id.iv_blob_settings_green);
        pinkBlob = getActivity().findViewById(R.id.iv_blob_settings_pink);
        blueBlob.setVisibility(View.VISIBLE);
        greenBlob.setVisibility(View.VISIBLE);
        pinkBlob.setVisibility(View.VISIBLE);


    }

//    on sign out clicked, clear shared preferences and sign out
    private void signOut() {
        getActivity().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE).edit().clear().apply();

//      delete the account if the user's acc is Anonymous, otherwise just sign out
        if (firebaseUser.isAnonymous()){
//        reference to current user document
            currentUserRef = usersRef.document(firebaseUser.getUid());
            currentUserRef.delete();
            AuthUI.getInstance()
                .delete(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("SettingsFragment", "deleted account");

                        }
                    });
        } else {
            AuthUI.getInstance()
                .signOut(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("SettingsFragment", "signed out of account");

                        }
                    });

        }



    }


}