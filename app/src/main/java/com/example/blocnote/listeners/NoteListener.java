package com.example.blocnote.listeners;

import com.example.blocnote.Note;

public interface NoteListener {
    void onNoteClicked(Note note, int position);
}
