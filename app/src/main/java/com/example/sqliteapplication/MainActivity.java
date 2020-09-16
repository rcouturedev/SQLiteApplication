package com.example.sqliteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvNotes);

        sqliteHelper = new SqliteHelper(this);

        getNotes();
    }

    private void getNotes() {

        List<Note> notesList = sqliteHelper.getNotes();

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);

        NotesAdapter notesAdapter = new NotesAdapter(this, notesList);

        recyclerView.setAdapter(notesAdapter);
    }

    public void addNote(View view) {

        Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getNotes();
    }
}
