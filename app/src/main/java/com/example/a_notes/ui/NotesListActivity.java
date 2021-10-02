package com.example.a_notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;
import com.example.a_notes.domain.NotesRepository;
import com.example.a_notes.impl.NotesRepositoryImpl;

public class NotesListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private NotesRepository notesRepository = new NotesRepositoryImpl();
    private NotesAdapter adapter = new NotesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        generateRepository();

        initToolbar();
        initRecycleView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_note_menu:
                openNoteEditScreen(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openNoteEditScreen(@Nullable NoteEntity item) {
        Intent intent = new Intent(this, NoteEditActivity.class);
        //start for result
        startActivity(intent);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.recycler_view_note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(NoteEntity note) {
        openNoteEditScreen(note);
    }

    private void generateRepository() {
        notesRepository.createNote(new NoteEntity("Title1", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title2", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title3", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title4", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title5", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title6", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title7", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title8", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title9", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title10", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title11", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title12", "some text", "20.20.20"));
        notesRepository.createNote(new NoteEntity("Title13", "some text", "20.20.20"));

    }
}