package com.craftylyteam.craftylyapp1.auth;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    LiveData<User> authenticatedUserLiveData;
    LiveData<User> createdUserLiveData;
    LiveData<User> anonymousUserLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
    }

    void signInWithFirebaseUI(int resultCode) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithFirebaseUI(resultCode);
    }

    void mySignInAnonymously() {
        anonymousUserLiveData = authRepository.firebaseSignInAnonymously();
    }


    void createUser(User authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }
}