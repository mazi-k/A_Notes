package com.example.a_notes.ui;

import android.content.Context;
import android.os.Bundle;
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
    FragmentManager fragmentManager;

    public static final String UPDATE_NOTE_KEY = "UPDATE_NOTE_KEY";

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = requireActivity().getSupportFragmentManager();
        getUpdatedNote();
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
    }

    private void initSaveButton(View view) {
        view.findViewById(R.id.new_note_button).setOnClickListener(v -> createNewNote());
    }

    private void onItemClick(NoteEntity note) {
        updateSelectedNote(note);
    }

    public static void createNewNote() {
        NoteEntity note = new NoteEntity("", "", "");
        if (controller != null) {
            controller.showNote(note);
        }
    }

    private void updateSelectedNote(NoteEntity note) {
        noteIdToChanging = note.getId();
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
        NoteEntity noteEntity = null;
        if (getArguments() != null) {
            noteEntity = getArguments().getParcelable(UPDATE_NOTE_KEY);
        }
        if (noteEntity != null) {
            notesRepository.updateNote(noteIdToChanging, noteEntity);
            adapter.setData(notesRepository.getNotes());
        }
    }

    interface Controller {
        void showNote(NoteEntity noteEntity);
    }
}