package com.example.a_notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<NoteEntity> data = new ArrayList<>();
    private OnItemClickListener clickListener = null;

    public void setData(List<NoteEntity> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity note = getItem(position);
        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(note));
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());
        holder.dateTextView.setText(note.getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private NoteEntity getItem(int position){
        return data.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    interface OnItemClickListener{
        void onItemClick(NoteEntity item);
    }
}
