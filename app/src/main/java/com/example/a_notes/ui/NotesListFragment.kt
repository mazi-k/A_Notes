package com.example.a_notes.ui

import android.content.Context
import android.content.DialogInterface
import androidx.recyclerview.widget.RecyclerView
import com.example.a_notes.domain.NotesRepository
import com.example.a_notes.ui.NotesAdapter
import com.example.a_notes.domain.NoteEntity
import com.example.a_notes.ui.NotesListFragment
import android.os.Bundle
import android.view.*
import com.example.a_notes.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View.OnCreateContextMenuListener
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.a_notes.domain.App
import java.lang.IllegalStateException

class NotesListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesRepository: NotesRepository

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
        initRecycleView(view)
        initNewNoteButton(view)
    }

    private fun initRecycleView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_note_list)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setOnCreateContextMenuListener(OnCreateContextMenuListener { menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo? ->
            activity!!.menuInflater.inflate(
                R.menu.context_menu,
                menu
            )
        })
        recyclerView.setAdapter(adapter)
        adapter.setData(notesRepository!!.notes)
    }

    private fun initNewNoteButton(view: View) {
        view.findViewById<View>(R.id.new_note_button)
            .setOnClickListener { v: View? -> createNewNote() }
    }

    private fun updateSelectedNote(note: NoteEntity) {
        NOTE_ACTION = NOTE_ACTION_UPDATE
        noteIdToChanging = note.id
        if (controller != null) {
            controller!!.showNote(note)
        }
    }

    private val updatedNote: Unit
        private get() {
            var noteEntity: NoteEntity? = null
            if (arguments != null) {
                noteEntity = arguments!!.getParcelable(UPDATE_NOTE_KEY)
            }
            if (noteEntity != null) {
                when (NOTE_ACTION) {
                    NOTE_ACTION_CREATE -> {
                        notesRepository!!.createNote(noteEntity)
                        adapter.setData(notesRepository!!.notes)
                    }
                    NOTE_ACTION_UPDATE -> {
                        notesRepository!!.updateNote(noteIdToChanging, noteEntity)
                        adapter.setData(notesRepository!!.notes)
                    }
                }
            }
        }

    private fun showNoteContextMenu(note: NoteEntity) {
        recyclerView!!.setOnCreateContextMenuListener { menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo? ->
            activity!!.menuInflater.inflate(R.menu.context_menu, menu)
            menu.findItem(R.id.action_delete).setOnMenuItemClickListener { item: MenuItem? ->
                showAlertForDeletingNote(note)
                true
            }
            menu.findItem(R.id.action_update).setOnMenuItemClickListener { item: MenuItem? ->
                updateSelectedNote(note)
                true
            }
        }
        recyclerView!!.showContextMenu()
    }

    private fun deleteNote(note: NoteEntity) {
        notesRepository!!.deleteNote(note.id)
        adapter.setData(notesRepository!!.notes)
        adapter.notifyDataSetChanged()
    }

    private fun showAlertForDeletingNote(note: NoteEntity) {
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.alert)
            .setMessage(R.string.message)
            .setPositiveButton(R.string.answer_yes) { dialog: DialogInterface?, which: Int ->
                deleteNote(
                    note
                )
            }
            .setNegativeButton(R.string.answer_no) { dialog: DialogInterface?, which: Int ->
                Toast.makeText(
                    activity, "Okay:)", Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private val app: App
        private get() = requireActivity().application as App

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

    object ObjectWithStatic{
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