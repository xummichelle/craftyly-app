package com.craftylyteam.craftylyapp1.main.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNoteFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditNoteActivity editNoteActivity;

    //    firebase references
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection(Constants.USERS);
    private DocumentReference currentUserRef;
    private CollectionReference notesRef;
    private DocumentReference currentNoteRef;
    private FirebaseUser firebaseUser;

    private TextView noteHeaderEditText;
    private ImageView imageViewNoteLightBulbChoice;
    private EditText noteDescriptionEditText;
    private MaterialButton saveNoteButton;
    private MaterialButton cancelNoteButton;
    private String bulbTag;
    private Boolean isBulbSet;

    private String lightbulb;



    public EditNoteFragment() {
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
        view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        editNoteActivity = (EditNoteActivity) getActivity();
        isBulbSet = false;


        firebaseUser = firebaseAuth.getCurrentUser();
        currentUserRef = usersRef.document(firebaseUser.getUid());
        notesRef = currentUserRef.collection(Constants.NOTES);
        currentNoteRef = editNoteActivity.getCurrentNoteRef();



        noteHeaderEditText = (TextView) view.findViewById(R.id.note_header_edit_text);
        noteDescriptionEditText = (EditText) view.findViewById(R.id.note_description_edit_text);

        imageViewNoteLightBulbChoice = (ImageView) view.findViewById(R.id.image_view_note_lightbulb_choice);
        imageViewNoteLightBulbChoice.setOnClickListener(this);
        saveNoteButton = (MaterialButton) view.findViewById(R.id.save_note_button);
        saveNoteButton.setOnClickListener(this);
        cancelNoteButton = (MaterialButton) view.findViewById(R.id.cancel_note_button);
        cancelNoteButton.setOnClickListener(this);

        themeUtils.onEditNoteFragmentCreateSetTheme(getActivity(), view);


        if (getArguments() != null) {
            Log.d("editNoteFragGetArg", "onCreateView: get arguments is not null");
            Bundle receivedBulbTagBundle = this.getArguments();
            bulbTag = receivedBulbTagBundle.getString(Constants.SECOND_BULB_TAG_KEY);
            noteHeaderEditText.setText(receivedBulbTagBundle.getString(Constants.SECOND_NOTE_TITLE_KEY));
            noteDescriptionEditText.setText(receivedBulbTagBundle.getString(Constants.SECOND_NOTE_DESC_KEY));
            imageViewNoteLightBulbChoice.setTag(bulbTag);
            getIntLightBulbChoice();
            isBulbSet = true;
        } else {
            imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_craftyly_bulb_icon);
            lightbulb = Constants.CRAFTYLY_BULB;
        }

        if (!editNoteActivity.isNewNote()) {
            currentNoteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String docTitle = documentSnapshot.getString(Constants.NOTE_TITLE);
                        String docDescription = documentSnapshot.getString(Constants.NOTE_DESCRIPTION);
                        String docLightbulb = documentSnapshot.getString(Constants.NOTE_LIGHTBULB);
                        Log.d("dreamynight", "onSuccess: " + docLightbulb + "title:" + docTitle + "desc: " + docDescription);
                        noteHeaderEditText.setText(docTitle);
                        noteDescriptionEditText.setText(docDescription);
                        imageViewNoteLightBulbChoice.setTag(docLightbulb);
                        if (!isBulbSet) {
                            getIntLightBulbChoice();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Note does not exist", Toast.LENGTH_SHORT).show();
                        Log.d("EditNoteFragment", "note to be edited's document does not exist");
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("EditNoteFragment", e.toString());
                        }
                    });
        }



        return view;
    }

//    set drawable resource in each case
    private void getIntLightBulbChoice() {
        switch (imageViewNoteLightBulbChoice.getTag().toString()) {
            case Constants.CALICO_BULB:
                imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_calico_bulb_icon);
                lightbulb = Constants.CALICO_BULB;
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_craftyly_calico_icon);
                lightbulb = Constants.CRAFTYLY_CALICO_BULB;
                break;
            case Constants.LEMON_BULB:
                imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_lemon_bulb_icon);
                lightbulb = Constants.LEMON_BULB;
                break;
            case Constants.SUNSET_BULB:
                imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_sunset_bulb_icon);
                lightbulb = Constants.SUNSET_BULB;
                break;
            case Constants.CAMO_BULB:
                imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_camo_bulb_icon);
                lightbulb = Constants.CAMO_BULB;
                break;
            default:
                Log.d("dreamynightdefaultedhuhh", "getIntLightBulbChoice: " + imageViewNoteLightBulbChoice.getTag().toString());
                imageViewNoteLightBulbChoice.setImageResource(R.drawable.ic_craftyly_bulb_icon);
                lightbulb = Constants.CRAFTYLY_BULB;
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.save_note_button:
                saveNote();
                break;
            case R.id.cancel_note_button:
                getActivity().finish();
                break;
            case R.id.image_view_note_lightbulb_choice:
                EditBulbFragment editBulbFragment = new EditBulbFragment();
                Bundle newBulbTagBundle = new Bundle();
                newBulbTagBundle.putString(Constants.FIRST_BULB_TAG_KEY, lightbulb);
                newBulbTagBundle.putString(Constants.FIRST_NOTE_TITLE_KEY, noteHeaderEditText.getText().toString());
                newBulbTagBundle.putString(Constants.FIRST_NOTE_DESC_KEY, noteDescriptionEditText.getText().toString());
                editBulbFragment.setArguments(newBulbTagBundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), editBulbFragment, "")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
    }

    private void saveNote() {
        String mTitle = "New Note";
        if (!TextUtils.isEmpty(noteHeaderEditText.getText())){
            mTitle = noteHeaderEditText.getText().toString().trim();
        }
        String mDescription = noteDescriptionEditText.getText().toString();
        String mLightbulb = lightbulb;
        Timestamp mTimestamp = Timestamp.now();
        if (editNoteActivity.isNewNote()) {
            Log.d("dreamynightisnewnote", "onSuccess: " + mLightbulb + "title:" + mTitle + "desc: " + mDescription);
            notesRef.add(new Note(mTitle, mDescription, mLightbulb, mTimestamp));
            Toast.makeText(getActivity(), "Note saved!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            currentNoteRef.update(Constants.NOTE_TITLE, mTitle);
            currentNoteRef.update(Constants.NOTE_DESCRIPTION, mDescription);
            currentNoteRef.update(Constants.NOTE_LIGHTBULB, mLightbulb);
            currentNoteRef.update(Constants.NOTE_TIMESTAMP, mTimestamp);

            Log.d("dreamynightiselse", "onSuccess: " + mLightbulb + "title:" + mTitle + "desc: " + mDescription);
            Toast.makeText(getActivity(), "Note saved!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }
}