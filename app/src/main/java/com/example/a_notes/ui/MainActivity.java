package com.example.a_notes.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a_notes.R;
import com.example.a_notes.domain.App;
import com.example.a_notes.domain.NoteEntity;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {

    private boolean isLandscape = false;
    private final String TAG = "@@@ Main";
    private boolean isFirstLaunch = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstLaunch)
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
                Log.d(TAG, "createListFragment() called");
            }
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_list);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_list, new NotesListFragment())
                        .commit();
                Log.d(TAG, "createListFragment() land called");
            }
        }
        isFirstLaunch = false;
    }

    @Override
    public void showNote(NoteEntity noteEntity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_content, NoteEditFragment.newInstance(noteEntity))
                    .commit();
            Log.d(TAG, "showNote() land called with: noteEntity = [" + noteEntity.getId() + "]");
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NoteEditFragment.newInstance(noteEntity))
                    .addToBackStack(null)
                    .commit();
            Log.d(TAG, "showNote() called with: noteEntity = [" + noteEntity.getId() + "]");
        }
    }

    @Override
    public void saveNote(NoteEntity noteEntity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_list, NotesListFragment.newInstance(noteEntity))
                    .commit();
            Log.d(TAG, "saveNote() land called with: noteEntity = [" + noteEntity.getId() + "]");
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NotesListFragment.newInstance(noteEntity))
                    .commit();
            Log.d(TAG, "saveNote() called with: noteEntity = [" + noteEntity.getId() + "]");
        }
    }
}
