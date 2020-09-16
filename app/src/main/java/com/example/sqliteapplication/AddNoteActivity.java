package com.example.sqliteapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    EditText name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
    }

    public void saveNote(View view) {

        if (name.getText().toString().isEmpty()) {
            name.setError("name should not be empty");
            return;
        } else {

            name.setError(null);
            SqliteHelper helper = new SqliteHelper(this);
            String noteName = name.getText().toString();
            String noteDescription = description.getText().toString();
            Note note = new Note(1, noteName, noteDescription);
            helper.insetNote(note);
            finish();
        }
    }
}
