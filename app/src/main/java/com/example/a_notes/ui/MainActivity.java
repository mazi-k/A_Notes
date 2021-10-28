package com.example.a_notes.ui;

import android.os.Bundle;

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
    private boolean isFirstLaunch = true;

    private BottomNavigationView bottomNavigationView;
    private final Map<Integer, Fragment> fragmentMap = fillFragments();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstLaunch) {
            createListFragment();
        }

        initNavigationView();
    }

    private Map<Integer, Fragment> fillFragments() {
        Map<Integer, Fragment> fragments = new HashMap<>();
        fragments.put(R.id.menu_item_list, new NotesListFragment());
        fragments.put(R.id.menu_item_about, new AboutFragment());
        fragments.put(R.id.menu_item_settings, new SettingsFragment());
        return fragments;
    }

    private void initNavigationView() {
        if (isLandscape) {
            bottomNavigationView = findViewById(R.id.bottom_nav_view);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_content, Objects.requireNonNull(fragmentMap.get(item.getItemId())))
                        .commit();
                return true;
            });
        } else {
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
        isFirstLaunch = false;
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
