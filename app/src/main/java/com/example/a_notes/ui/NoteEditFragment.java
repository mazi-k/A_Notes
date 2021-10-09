package com.example.a_notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteEditFragment extends Fragment {

    private EditText titleEditText;
    private EditText contentEditText;
    private TextView dateTextView;
    private Button saveNoteButton;

    private static final String MESSAGE_KEY = "MESSAGE_KEY";
    public static final String UPDATE_NOTE_KEY = "UPDATE_NOTE_KEY";
    public static String TAG = "&&&";

    Controller controller;
    FragmentManager fragmentManager;

    public NoteEditFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NotesListFragment.Controller)
            controller = (NoteEditFragment.Controller) context;
        else
            throw new IllegalStateException("Activity bla bla bla");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        getNote();
        onSaveButtonClick();
    }


    private void initViews(@NonNull View view){
        titleEditText = view.findViewById(R.id.note_title_edit_text);
        contentEditText = view.findViewById(R.id.note_content_edit_text);
        saveNoteButton = view.findViewById(R.id.note_button_save_note);

        dateTextView = view.findViewById(R.id.note_date_text_view);
        dateTextView.setText(getCurrentDate());
    }

    private void getNote(){
        NoteEntity noteEntity = getArguments().getParcelable(MESSAGE_KEY);
        fillNote(noteEntity);
    }

    private void fillNote(NoteEntity note){
        titleEditText.setText(note.getTitle());
        contentEditText.setText(note.getContent());
        dateTextView.setText(getCurrentDate());
    }

    private void onSaveButtonClick(){
        saveNoteButton.setOnClickListener(v -> {
            NoteEntity note = new NoteEntity(
                    titleEditText.getText().toString(),
                    contentEditText.getText().toString(),
                    dateTextView.getText().toString()
            );
            controller.saveNote(note);
            Log.d(TAG, "onSaveButtonClick() called " + note.getTitle());
        });
    }

    public static NoteEditFragment newInstance(NoteEntity noteEntity){
        NoteEditFragment noteEditFragment = new NoteEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MESSAGE_KEY, noteEntity);
        noteEditFragment.setArguments(bundle);
        return noteEditFragment;
    }


    private String getCurrentDate(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    interface Controller {
        void saveNote(NoteEntity noteEntity);
    }
}
