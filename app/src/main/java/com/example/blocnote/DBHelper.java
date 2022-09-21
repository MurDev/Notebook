package com.example.blocnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Note_Manager";

    // Table Name
    public static final String TABLE_NOTE = "Note";

    public static final String COLUMN_NOTE_ID = "Note_id";
    public static final String COLUMN_NOTE_TITLE = "Note_title";
    public static final String COLUMN_NOTE_CONTENT = "Note_content";
    public static final String COLUMN_NOTE_COLOR = "Note_color";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");

        String req = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_CONTENT + " TEXT,"
                + COLUMN_NOTE_COLOR + " TEXT" + ")";

        sqLiteDatabase.execSQL(req);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        String req = "DROP TABLE IF EXISTS " + TABLE_NOTE;
        sqLiteDatabase.execSQL(req);

        // Create table again
        onCreate(sqLiteDatabase);
    }

    public void insertNoteData(Note note){
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getTitle());

        // Opening Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOTE_ID, note.getId());
        contentValues.put(COLUMN_NOTE_TITLE, note.getTitle());
        contentValues.put(COLUMN_NOTE_CONTENT, note.getDescription());
        contentValues.put(COLUMN_NOTE_COLOR, note.getBackgroundColorNote());

        // Insert row
        sqLiteDatabase.insert(TABLE_NOTE, null, contentValues);

        // Closing database connection
        sqLiteDatabase.close();
    }

    public void updateNoteData(Note note){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getTitle());
        // Opening Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        contentValues.put(COLUMN_NOTE_ID, note.getId());
        contentValues.put(COLUMN_NOTE_TITLE, note.getTitle());
        contentValues.put(COLUMN_NOTE_CONTENT, note.getDescription());
        contentValues.put(COLUMN_NOTE_COLOR, note.getBackgroundColorNote());

        // Insert row
        sqLiteDatabase.update(TABLE_NOTE, contentValues, COLUMN_NOTE_ID + "=?", new String[]{String.valueOf(note.getId())});

        // Closing database connection
        sqLiteDatabase.close();
    }

    public Note getNote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[] { COLUMN_NOTE_ID,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT, COLUMN_NOTE_COLOR }, COLUMN_NOTE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        note.setBackgroundColorNote(cursor.getString(3));
        // return note
        return note;
    }

    public List<Note> getAllNotes(){
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Note> noteList = new ArrayList<Note>();

        // Select all notes
        String selectQuery = "SELECT * FROM " + TABLE_NOTE;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note.setBackgroundColorNote(cursor.getString(3));
                // Adding to list
                noteList.add(note);
            }while(cursor.moveToNext());
        }
        return noteList;
    }

    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
}
