package com.example.blocnote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.blocnote.listeners.NoteListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteListener {
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;

    private final List<Note> noteList = new ArrayList<Note>();

    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BlocNote);
        setContentView(R.layout.activity_main);


        Button buttonAddMain = findViewById(R.id.buttonAddMain);
        buttonAddMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();

            }
        });

        noteRecyclerView = findViewById(R.id.notesRecyclerView);
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        getNotes();

    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent,
                REQUEST_CODE_UPDATE_NOTE);
    }

    private void getNotes() {
        DBHelper dbHelper = new DBHelper(this);

        String c = String.valueOf(dbHelper.getNotesCount());
        Log.i("Count data database ", c);

        if (dbHelper.getNotesCount() > 0) {
            Note nt = dbHelper.getNote(0);
            List<Note> lst = dbHelper.getAllNotes();
            this.noteList.addAll(lst);
//            Log.i("Show data counted", String.valueOf(lstnot.size()));
            Log.i("Show data title ", "" + nt.getTitle());
            Log.i("Show data title from c ", "" + lst.get(0).getTitle());

            Note.noteArrayList.clear();
            Note.noteArrayList.addAll(this.noteList);
        }
        noteAdapter = new NoteAdapter(Note.noteArrayList, this);
        noteRecyclerView.setAdapter(noteAdapter);
    }

    public void newNote(){
        Intent newNoteIntent = new Intent(this, CreateNoteActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            if (needRefresh) {
                Log.i("&&&&&& Need refresh ", "ok");
                this.noteList.clear();
                DBHelper dbHelper = new DBHelper(this);
                List<Note> list = dbHelper.getAllNotes();
                this.noteList.addAll(list);

                this.noteAdapter.notifyDataSetChanged();
            }
        }

    }
}