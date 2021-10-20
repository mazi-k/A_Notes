package com.example.a_notes.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a_notes.domain.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<NoteEntity> data = new ArrayList<>();
    private OnItemClickListener clickListener;

    public NotesAdapter (OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setData(List<NoteEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private NoteEntity getItem(int position) {
        return data.get(position);
    }
}
