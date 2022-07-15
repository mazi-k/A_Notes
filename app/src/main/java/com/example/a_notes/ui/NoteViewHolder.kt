package com.example.a_notes.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.a_notes.R
import android.widget.TextView
import com.example.a_notes.domain.NoteEntity

class NoteViewHolder(parent: ViewGroup, clickListener: OnItemClickListener) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    ) {

    private val titleTextView = itemView.findViewById<TextView>(R.id.item_title_text_view)
    private val contentTextView = itemView.findViewById<TextView>(R.id.item_content_text_view)
    private val dateTextView = itemView.findViewById<TextView>(R.id.item_date_text_view)
    private var note: NoteEntity? = null
    private val clickListener: OnItemClickListener

    fun bind(note: NoteEntity) {
        this.note = note
        titleTextView.text = note.title
        contentTextView.text = note.content
        dateTextView.text = note.date
        itemView.setOnClickListener { v: View? -> clickListener.onItemClick(note) }
    }

    init {
        itemView.setOnClickListener { v: View? -> clickListener.onItemClick(note) }
        this.clickListener = clickListener
    }
}