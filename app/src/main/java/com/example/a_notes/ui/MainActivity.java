package com.example.a_notes.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteEditFragment.Controller {

    private boolean isLandscape = false;
    private final String TAG = "@@@ Main";
    private boolean isFirstLaunch = true;

    private BottomNavigationView bottomNavigationView;
    private Map<Integer, Fragment> fragmentMap = fillFragments();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavigationView();

        if (isFirstLaunch){
            createListFragment();
        }
    }

    private Map<Integer, Fragment> fillFragments() {
        Map<Integer, Fragment> fragments = new HashMap<>();
        fragments.put(R.id.menu_item_list, new NotesListFragment());
        fragments.put(R.id.menu_item_about, new AboutFragment());
        fragments.put(R.id.menu_item_settings, new SettingsFragment());
        return  fragments;
    }

    private void initNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, Objects.requireNonNull(fragmentMap.get(item.getItemId())))
                    .commit();
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_item_list);
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
