package com.example.a_notes.domain;

import android.app.Application;

import com.example.a_notes.impl.NotesRepositoryImpl;

public class App extends Application {
    private NotesRepository notesRepository = new NotesRepositoryImpl();

    public NotesRepository getNotesRepository(){
        return notesRepository;
    }
}
