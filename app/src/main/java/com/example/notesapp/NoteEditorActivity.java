package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NoteEditorActivity extends AppCompatActivity {
    private int noteId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            String noteText = MainActivity.notes.get(noteId);
            editText.setText(noteText);
        } else {
            noteId = MainActivity.notes.size();
            MainActivity.notes.add("");
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Add your code here if needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                saveNotesToSharedPreferences();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Add your code here if needed
            }
        });
    }

    private void saveNotesToSharedPreferences() {
        Set<String> set = new HashSet<>(MainActivity.notes);
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }
}
