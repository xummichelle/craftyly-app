package com.craftylyteam.craftylyapp1.main.prompt;

import com.google.firebase.Timestamp;

//pojo for prompt
public class Prompt {
    private String description;
    private boolean accepted;
    private Timestamp timestamp;


    public Prompt(String description, boolean accepted, Timestamp timestamp) {
        this.description = description;
        this.accepted = accepted;
        this.timestamp = timestamp;
    }

    public Prompt() {
//        empty constructor needed for firebase
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
