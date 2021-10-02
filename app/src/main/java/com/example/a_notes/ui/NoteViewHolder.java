package com.example.a_notes.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a_notes.R;
import com.example.a_notes.domain.NoteEntity;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleTextView = itemView.findViewById(R.id.item_title_text_view);
    private final TextView contentTextView = itemView.findViewById(R.id.item_content_text_view);
    private final TextView dateTextView = itemView.findViewById(R.id.item_date_text_view);

    private NoteEntity note;


    public NoteViewHolder(@NonNull ViewGroup parent, NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        itemView.setOnClickListener(v -> clickListener.onItemClick(note));
    }


    public void bind(NoteEntity note) {
        this.note = note;
        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());
        dateTextView.setText(note.getDate());
    }
}
