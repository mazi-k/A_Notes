package com.example.a_notes.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.a_notes.ui.NoteViewHolder
import com.example.a_notes.domain.NoteEntity
import android.view.ViewGroup
import java.util.ArrayList

class NotesAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteViewHolder>() {

    private var data: List<NoteEntity> = ArrayList()
    fun setData(data: List<NoteEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getItem(position: Int): NoteEntity {
        return data[position]
    }
}