package com.example.sqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";
    private static final String TABLE_NAME = "notes";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, " + DESCRIPTION + " TEXT);";

    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long insetNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME, note.getName());
        values.put(DESCRIPTION, note.getDescription());

        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public List<Note> getNotes() {

        List<Note> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                long id = cursor.getInt(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));


                Note note = new Note(id, name, description);
                notes.add(note);


            } while (cursor.moveToNext());
        }
        return notes;
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public long update(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME, note.getName());
        values.put(DESCRIPTION, note.getDescription());

        long id = db.update(TABLE_NAME, values, ID + " = ?",
                new String[]{String.valueOf(note.getId())});

        db.close();

        return id;
    }

}

