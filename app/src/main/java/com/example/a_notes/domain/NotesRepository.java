package com.example.a_notes.domain;

import androidx.annotation.Nullable;

import java.util.List;

public interface NotesRepository {

    List <NoteEntity> getNotes();

    @Nullable
     Integer createNote(NoteEntity note);

    boolean deleteNote(int id);

    boolean updateNote(int id, NoteEntity note);
}
