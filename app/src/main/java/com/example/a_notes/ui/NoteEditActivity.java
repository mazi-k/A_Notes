package com.example.a_notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteEditActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private TextView dateTextView;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        initViews();

        NoteEntity noteEntity = getIntent().getExtras().getParcelable(NotesListActivity.EDIT_NOTE_KEY);

        fillNote(noteEntity);
        onSaveButtonClick();
    }

    private void initViews(){
        titleEditText = findViewById(R.id.note_title_edit_text);
        contentEditText = findViewById(R.id.note_content_edit_text);
        saveNoteButton = findViewById(R.id.note_button_save_note);

        dateTextView = findViewById(R.id.note_date_text_view);
        dateTextView.setText(getCurrentDate());
    }

    private String getCurrentDate(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    private void fillNote(NoteEntity note){
        titleEditText.setText(note.getTitle());
        contentEditText.setText(note.getContent());
        dateTextView.setText(note.getDate());
    }

    private void onSaveButtonClick(){
        saveNoteButton.setOnClickListener(v -> {
            NoteEntity note = new NoteEntity(
                    titleEditText.getText().toString(),
                    contentEditText.getText().toString(),
                    dateTextView.getText().toString()
            );
            //
            Intent intentResult = new Intent();
            intentResult.putExtra(NotesListActivity.NEW_NOTE_KEY, note);
            setResult(RESULT_OK, intentResult);
            finish();
        });
    }
}
