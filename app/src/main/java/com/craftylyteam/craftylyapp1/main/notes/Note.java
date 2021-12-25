package com.craftylyteam.craftylyapp1.main.notes;

import com.google.firebase.Timestamp;


public class Note {
    private String title;
    private String description;
    private String bulbTag;
    private Timestamp timestamp;

    public Note(){
//        empty constructor needed for firebase
    }

    public Note(String title, String description, String bulbTag, Timestamp timestamp) {
        this.title = title;
        this.description = description;
        this.bulbTag = bulbTag;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBulbTag() {
        return bulbTag;
    }

    public void setBulbTag(String bulbTag) {
        this.bulbTag = bulbTag;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
