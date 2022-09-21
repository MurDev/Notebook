package com.example.blocnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateNoteActivity extends AppCompatActivity {

    EditText titleEditText, descEditText;
    ScrollView noteView;

    private Note alreadyAvailableNote;
    private boolean needRefresh;
    String colorNote2 = "#FFEBFAEB";
    String colorNote1 = "#FFFFECD4";
    String colorNote3 = "#FFEBF6FA";

    String[] colorList = {colorNote1, colorNote2, colorNote3};
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView imageBack = findViewById(R.id.imageBack);
        ImageView imageSave = findViewById(R.id.imageSave);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        initWidgets();

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)){
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdate();
        }

    }

    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.inputNoteTitle);
        descEditText = findViewById(R.id.inputNote);
        noteView = findViewById(R.id.createNoteView);
    }

    private void setViewOrUpdate(){
        titleEditText.setText(alreadyAvailableNote.getTitle());
        descEditText.setText(alreadyAvailableNote.getDescription());
        noteView.setBackgroundColor(Color.parseColor(alreadyAvailableNote.getBackgroundColorNote()));
    }

    public void saveNote(){
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());

        int id = Note.noteArrayList.size();
        Note note = new Note(id, title, desc);

        DBHelper dbHelper = new DBHelper(this);

        if (alreadyAvailableNote != null){
            Log.i("!!!!!!", "Modify");
            note.setId(alreadyAvailableNote.getId());
            note.setTitle(title);
            note.setDescription(desc);

            for(int i=0; i < Note.noteArrayList.size(); i++){
                if (Note.noteArrayList.get(i).getId() == note.getId()){
                    note.setBackgroundColorNote(Note.noteArrayList.get(i).getBackgroundColorNote());
                    Note.noteArrayList.set(i, note);
                }
            }
            Log.i("!!!!! Note modified ", ""+note.getTitle());
//            Note.noteArrayList.add(note);
            dbHelper.updateNoteData(note);
        }else{
            Log.i("!!!!!!!", "Insert");
            note.setBackgroundColorNote(colorList[random.nextInt(3)]);
            Note.noteArrayList.add(note);
            dbHelper.insertNoteData(note);
        }
        this.needRefresh = true;
        finish();
    }

}