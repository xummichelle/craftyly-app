package com.craftylyteam.craftylyapp1.auth;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

//POJO class for user
public class User implements Serializable {
    private String uid;
    private String name;
    private String email;

    @Exclude
    public boolean isAuthenticated;
    @Exclude
    boolean isNew, isCreated;

    //public no-arg constructor needed for firebase
    public User() {}

    //    storing 5 fields: uid, name, email
    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }



    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
