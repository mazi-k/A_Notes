package com.example.a_notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private final NotesRepository notesRepository = new NotesRepositoryImpl();
    private final NotesAdapter adapter = new NotesAdapter();

    private static Controller controller;

    public static final String UPDATE_NOTE_KEY = "UPDATE_NOTE_KEY";
    private final String TAG = "@@@";

    public static String NOTE_ACTION = null;
    public static final String NOTE_ACTION_CREATE = "CREATE";
    public static final String NOTE_ACTION_UPDATE = "UPDATE";

    private int noteIdToChanging;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        generateRepository();
        getUpdatedNote();
        if (context instanceof Controller)
            controller = (Controller) context;
        else
            throw new IllegalStateException("Activity bla bla bla");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    private void generateRepository() {
        notesRepository.createNote(new NoteEntity("Title1", "some text", "20.20.20"));
        Log.d(TAG, "generateRepository() repository: " + notesRepository.getNotes().size());
    }

    private void initSaveButton(View view) {
        view.findViewById(R.id.new_note_button).setOnClickListener(v -> createNewNote());
    }

    private void onItemClick(NoteEntity note) {
        updateSelectedNote(note);
    }

    public static void createNewNote() {
        NOTE_ACTION = NOTE_ACTION_CREATE;
        NoteEntity note = new NoteEntity("", "", "");
        if (controller != null) {
            controller.showNote(note);
        }
    }

    private void updateSelectedNote(NoteEntity note) {
        NOTE_ACTION = NOTE_ACTION_UPDATE;
        noteIdToChanging = note.getId();
        Log.d(TAG, "noteIdToChanging = [" + noteIdToChanging + "] (" + note.getId() + ")");
        if (controller != null) {
            controller.showNote(note);
        }
    }

    public static NotesListFragment newInstance(NoteEntity noteEntity) {
        NotesListFragment notesListFragment = new NotesListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(UPDATE_NOTE_KEY, noteEntity);
        notesListFragment.setArguments(bundle);
        return notesListFragment;
    }

    private void getUpdatedNote() {
        Log.d(TAG, "NOTE_ACTION::::: " + NOTE_ACTION);
        NoteEntity noteEntity = null;
        if (getArguments() != null) {
            noteEntity = getArguments().getParcelable(UPDATE_NOTE_KEY);
        }
        if (noteEntity != null) {
            switch (NOTE_ACTION){
                case NOTE_ACTION_CREATE:
                    notesRepository.createNote(noteEntity);
                    adapter.setData(notesRepository.getNotes());
                    break;
                case NOTE_ACTION_UPDATE:
                    notesRepository.updateNote(noteIdToChanging, noteEntity);
                    adapter.setData(notesRepository.getNotes());
                    break;
            }
        }
        Log.d(TAG, "getUpdatedNote() repository: " + notesRepository.getNotes().size());
    }

    interface Controller {
        void showNote(NoteEntity noteEntity);
    }
}