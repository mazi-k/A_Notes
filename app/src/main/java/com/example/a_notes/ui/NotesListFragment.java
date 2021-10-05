package com.example.a_notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;
import com.example.a_notes.domain.NotesRepository;
import com.example.a_notes.impl.NotesRepositoryImpl;

public class NotesListFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotesRepository notesRepository = new NotesRepositoryImpl();
    private NotesAdapter adapter = new NotesAdapter();

    private static Controller controller;

    public static final String NEW_NOTE_KEY = "NEW_NOTE_KEY";
    private static final int NEW_NOTE_CODE = 12;
    public static final String UPDATE_NOTE_KEY = "UPDATE_NOTE_KEY";
    private static final int UPDATE_NOTE_CODE = 32;

    public static String EDIT_NOTE_KEY;
    public static String TAG = "&&&";

    private int noteIdToChanging;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        generateRepository();
        if (context instanceof Controller)
            controller = (Controller) context;
        else
            throw new IllegalStateException("Activity bla bla bla");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecycleView(view);
        initSaveButton(view);
    }

    private void initRecycleView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recycler_view_note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void initSaveButton(View view) {
        view.findViewById(R.id.new_note_button).setOnClickListener(v -> {
            createNewNote();
        });
    }

    private void onItemClick(NoteEntity note) {
        updateSelectedNote(note);
    }

    private void generateRepository() {
        notesRepository.createNote(new NoteEntity("Title1", "some text", "20.20.20"));
    }

    public static void createNewNote() {
        NoteEntity note = new NoteEntity("", "", "");
        if(controller != null){
            controller.showNote(note);
        }
//        Intent intent = new Intent(this, NoteEditFragment.class);
//        EDIT_NOTE_KEY = NEW_NOTE_KEY;
//        intent.putExtra(EDIT_NOTE_KEY, note);
//        startActivityForResult(intent, NEW_NOTE_CODE);

    }


    private void updateSelectedNote(NoteEntity note) {
        if(controller != null){
            controller.showNote(note);
        }
//        Intent intent = new Intent(this, NoteEditFragment.class);
//        EDIT_NOTE_KEY = UPDATE_NOTE_KEY;
//        intent.putExtra(EDIT_NOTE_KEY, note);
//        noteIdToChanging = note.getId();
//        startActivityForResult(intent, UPDATE_NOTE_CODE);
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode != NEW_NOTE_CODE) {
//            super.onActivityResult(requestCode, resultCode, data);
//            NoteEntity note = data.getParcelableExtra(EDIT_NOTE_KEY);
//            notesRepository.updateNote(noteIdToChanging, note);
//            adapter.setData(notesRepository.getNotes());
//            return;
//        }
//
//        if (resultCode == RESULT_OK) {
//            NoteEntity note = data.getParcelableExtra(EDIT_NOTE_KEY);
//            notesRepository.createNote(new NoteEntity(note.getTitle(), note.getContent(), note.getDate()));
//            adapter.setData(notesRepository.getNotes());
//        }
//    }

    interface Controller {
        void showNote(NoteEntity noteEntity);
    }


}