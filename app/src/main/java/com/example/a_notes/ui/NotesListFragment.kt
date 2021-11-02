package com.example.a_notes.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a_notes.R
import com.example.a_notes.databinding.FragmentNotesListBinding
import com.example.a_notes.domain.App
import com.example.a_notes.domain.NoteEntity
import com.example.a_notes.domain.NotesRepository

class NotesListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesRepository: NotesRepository

    private lateinit var binding: FragmentNotesListBinding

    private val adapter = NotesAdapter { note: NoteEntity -> showNoteContextMenu(note) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notesRepository = app.notesRepository
        if (context is Controller) controller =
            context else throw IllegalStateException("Activity bla bla bla")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        updatedNote
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotesListBinding.bind(view)

        initRecycleView()
        initNewNoteButton()
    }

    private fun initRecycleView() {
        recyclerView = binding.recyclerViewNoteList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setOnCreateContextMenuListener { menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo? ->
            activity!!.menuInflater.inflate(
                R.menu.context_menu,
                menu
            )
        }
        recyclerView.adapter = adapter
        adapter.setData(notesRepository.notes)
    }

    private fun initNewNoteButton() {
        binding.newNoteButton.setOnClickListener { createNewNote() }
    }

    private fun updateSelectedNote(note: NoteEntity) {
        NOTE_ACTION = NOTE_ACTION_UPDATE
        noteIdToChanging = note.id
        if (controller != null) {
            controller!!.showNote(note)
        }
    }

    private val updatedNote: Unit
        get() {
            var noteEntity: NoteEntity? = null
            if (arguments != null) {
                noteEntity = arguments!!.getParcelable(UPDATE_NOTE_KEY)
            }
            if (noteEntity != null) {
                when (NOTE_ACTION) {
                    NOTE_ACTION_CREATE -> {
                        notesRepository.createNote(noteEntity)
                        adapter.setData(notesRepository.notes)
                    }
                    NOTE_ACTION_UPDATE -> {
                        notesRepository.updateNote(noteIdToChanging, noteEntity)
                        adapter.setData(notesRepository.notes)
                    }
                }
            }
        }

    private fun showNoteContextMenu(note: NoteEntity) {
        recyclerView.setOnCreateContextMenuListener { menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo? ->
            activity!!.menuInflater.inflate(R.menu.context_menu, menu)
            menu.findItem(R.id.action_delete).setOnMenuItemClickListener {
                showAlertForDeletingNote(note)
                true
            }
            menu.findItem(R.id.action_update).setOnMenuItemClickListener {
                updateSelectedNote(note)
                true
            }
        }
        recyclerView.showContextMenu()
    }

    private fun deleteNote(note: NoteEntity) {
        notesRepository.deleteNote(note.id)
        adapter.setData(notesRepository.notes)
        adapter.notifyDataSetChanged()
    }

    private fun showAlertForDeletingNote(note: NoteEntity) {
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.alert)
            .setMessage(R.string.message)
            .setPositiveButton(R.string.answer_yes) { _: DialogInterface?, _: Int ->
                deleteNote(
                    note
                )
            }
            .setNegativeButton(R.string.answer_no) { _: DialogInterface?, _: Int ->
                Toast.makeText(
                    activity, "Okay:)", Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private val app: App
        get() = requireActivity().application as App

    internal interface Controller {
        fun showNote(noteEntity: NoteEntity?)
    }

    companion object {
        private var controller: Controller? = null
        const val UPDATE_NOTE_KEY = "UPDATE_NOTE_KEY"
        var NOTE_ACTION: String? = null
        const val NOTE_ACTION_CREATE = "CREATE"
        const val NOTE_ACTION_UPDATE = "UPDATE"
        private var noteIdToChanging = 0


        fun createNewNote() {
            NOTE_ACTION = NOTE_ACTION_CREATE
            val note = NoteEntity(0, "", "", "")
            if (controller != null) {
                controller!!.showNote(note)
            }
        }


    }

    object ObjectWithStatic {
        @JvmStatic
        fun newInstance(noteEntity: NoteEntity?): NotesListFragment {
            val notesListFragment = NotesListFragment()
            val bundle = Bundle()
            bundle.putParcelable(UPDATE_NOTE_KEY, noteEntity)
            notesListFragment.arguments = bundle
            return notesListFragment
        }
    }

}