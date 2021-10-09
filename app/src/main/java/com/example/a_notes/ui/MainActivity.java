package com.example.a_notes.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {

    private boolean isLandscape = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        createListFragment();
    }

    private void createListFragment() {
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
        if (!isLandscape) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new NotesListFragment())
                        .commit();
            }
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_list);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_list, new NotesListFragment())
                        .commit();
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void showNote(NoteEntity noteEntity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_content, NoteEditFragment.newInstance(noteEntity))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NoteEditFragment.newInstance(noteEntity))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void saveNote(NoteEntity noteEntity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_list, NotesListFragment.newInstance(noteEntity))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NotesListFragment.newInstance(noteEntity))
                    .commit();
        }

    }
}
