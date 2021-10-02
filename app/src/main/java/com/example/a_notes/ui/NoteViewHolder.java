package com.example.a_notes.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a_notes.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    public TextView contentTextView = itemView.findViewById(R.id.content_text_view);
    public TextView dateTextView = itemView.findViewById(R.id.date_text_view);
}
