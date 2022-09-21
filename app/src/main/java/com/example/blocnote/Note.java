package com.example.blocnote;

import java.io.Serializable;
import java.util.ArrayList;

public class Note implements Serializable {

    public static ArrayList<Note> noteArrayList = new ArrayList<>();

    private int id;
    private String title;
    private String description;
    private String dateCreated;
    private String dateUpdated;
    private String backgroundColorNote;
    private String textColorNote;

    public Note(){

    }

    public Note(int id, String title, String description, String dateCreated, String dateUpdated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getBackgroundColorNote() {
        return backgroundColorNote;
    }

    public void setBackgroundColorNote(String backgroundColorNote) {
        this.backgroundColorNote = backgroundColorNote;
    }

    public String getTextColorNote() {
        return textColorNote;
    }

    public void setTextColorNote(String textColorNote) {
        this.textColorNote = textColorNote;
    }
}
