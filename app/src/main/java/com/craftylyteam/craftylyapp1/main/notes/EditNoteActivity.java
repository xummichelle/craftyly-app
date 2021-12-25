package com.craftylyteam.craftylyapp1.main.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.prompt.settings.SettingsFragment;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNoteActivity extends AppCompatActivity {
    //    firebase references
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection(Constants.USERS);
    private DocumentReference currentUserRef;
    private CollectionReference notesRef;
    private DocumentReference currentNoteRef;
    private FirebaseUser firebaseUser;

    private Intent addEditIntent;
    private boolean isNewNote;
    private String currentNoteRefID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        firebaseUser = firebaseAuth.getCurrentUser();
        currentUserRef = usersRef.document(firebaseUser.getUid());
        notesRef = currentUserRef.collection(Constants.NOTES);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.edit_note_fragment_container, new EditNoteFragment())
                .commit();

        addEditIntent = getIntent();
        if (addEditIntent.hasExtra(Constants.EXTRA_NOTE_ID)) {
            isNewNote = false;
            currentNoteRefID = addEditIntent.getStringExtra(Constants.EXTRA_NOTE_ID);
            currentNoteRef = notesRef.document(currentNoteRefID);
        } else {
            isNewNote = true;
        }



    }

    public Intent getAddEditIntent() {
        return addEditIntent;
    }

    public DocumentReference getCurrentNoteRef() {
        return currentNoteRef;
    }

    public boolean isNewNote() {
        return isNewNote;
    }

    public String getCurrentNoteRefID() {
        return currentNoteRefID;
    }

    public void setNewNote(boolean newNote) {
        isNewNote = newNote;
    }

    public void setCurrentNoteRef(DocumentReference currentNoteRef) {
        this.currentNoteRef = currentNoteRef;
    }

    public void setCurrentNoteRefID(String currentNoteRefID) {
        this.currentNoteRefID = currentNoteRefID;
    }
}