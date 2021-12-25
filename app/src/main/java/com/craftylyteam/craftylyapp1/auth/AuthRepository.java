package com.craftylyteam.craftylyapp1.auth;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.craftylyteam.craftylyapp1.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.USER;

@SuppressWarnings("ConstantConditions")
public class AuthRepository {
    private static final String TAG = "AuthRepository";

    //    firebaseauth object
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //    reference to database root
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    //    reference to users collection
    private CollectionReference usersRef = rootRef.collection(Constants.USERS);

    MutableLiveData<User> firebaseSignInWithFirebaseUI(int resultCode) {
        MutableLiveData<User>authenticatedUserMutableLiveData = new MutableLiveData<>();
        if (resultCode == RESULT_OK) {

            // Successfully signed in
            boolean isNewUser;
            FirebaseUserMetadata metadata = firebaseAuth.getCurrentUser().getMetadata();
            if (metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
                isNewUser = true;
                Log.d("isNewUserRepo", "user is new");
            } else {
                isNewUser = false;
                Log.d("isNewUserRepo", "user is not new");
            }
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                String uid = firebaseUser.getUid();
                String name = firebaseUser.getDisplayName();
                String email = firebaseUser.getEmail();
//                    create new user object
                User user = new User(uid, name, email);
//                    set isNew to true or false (i think)
                user.isNew = isNewUser;
                authenticatedUserMutableLiveData.setValue(user);
//                    sets authenticated user livedata to new user object
            } else {
                Log.d(TAG, "sign in failed");
            }

        }
        return authenticatedUserMutableLiveData;
    }

    MutableLiveData<User> firebaseSignInAnonymously() {
        MutableLiveData<User> anonymousUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(authTask -> {
                    if (authTask.isSuccessful()) {
                            // Sign in success, update user object with the signed-in user's information
                            boolean isNewUser = true;
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();
                                String name = "Anonymous";
                                String email = firebaseUser.getEmail();
//                    create new user object
                                User user = new User(uid, name, email);
//                    set isNew
                                user.isNew = isNewUser;
                                anonymousUserMutableLiveData.setValue(user);

//                    sets authenticated user livedata to new user object
                            }
                        } else {
                            Log.w(TAG, "signInAnonymously:failure", authTask.getException());
                        }

                });

        return anonymousUserMutableLiveData;
    }

    //    called by authviewmodel createuser()
    MutableLiveData<User> createUserInFirestoreIfNotExists(User authenticatedUser) {
        MutableLiveData<User> newUserMutableLiveData = new MutableLiveData<>();
//        reference to user document
        DocumentReference uidRef = usersRef.document(authenticatedUser.getUid());
        uidRef.get().addOnCompleteListener(uidTask -> {
            if (uidTask.isSuccessful()) {
                DocumentSnapshot document = uidTask.getResult();
                if (!document.exists()) {
//                    set the user to the data in authenticatedUser
                    uidRef.set(authenticatedUser).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {
//                            set iscreated to true and add the authenticated user object to newUserMutableLiveData
                            authenticatedUser.isCreated = true;
                            newUserMutableLiveData.setValue(authenticatedUser);
                        } else {
                            Log.d(TAG, userCreationTask.getException().getMessage());
                        }
                    });
                } else {
                    newUserMutableLiveData.setValue(authenticatedUser);
                }
            } else {
                Log.d(TAG, uidTask.getException().getMessage());
            }
        });
//        this becomes createdUserLiveData in the ViewModel
        return newUserMutableLiveData;
    }

}
