package com.example.a_notes.ui

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.a_notes.R
import com.example.a_notes.domain.NoteEntity
import com.example.a_notes.ui.NoteEditFragment
import java.lang.IllegalStateException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NoteEditFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var saveNoteButton: Button
    private var controller: Controller? = null
    var myContext: FragmentManager? = null
    private var sendingId: Int? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller =
            if (context is Controller) context else throw IllegalStateException(
                "Activity IllegalStateException"
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myContext = Objects.requireNonNull(activity)?.supportFragmentManager
        initViews(view)
        note
        onSaveButtonClick()
    }

    private fun initViews(view: View) {
        titleEditText = view.findViewById(R.id.note_title_edit_text)
        contentEditText = view.findViewById(R.id.note_content_edit_text)
        saveNoteButton = view.findViewById(R.id.note_button_save_note)
        dateTextView = view.findViewById(R.id.note_date_text_view)
        dateTextView.setText(currentDate)
    }

    private val note: Unit
        private get() {
            var noteEntity: NoteEntity? = null
            if (arguments != null) {
                noteEntity = arguments!!.getParcelable(MESSAGE_KEY)
                sendingId = noteEntity!!.id
            }
            noteEntity?.let { fillNote(it) }
        }

    private fun fillNote(note: NoteEntity) {
        titleEditText!!.setText(note.title)
        contentEditText!!.setText(note.content)
        dateTextView!!.text = currentDate
    }

    private fun onSaveButtonClick() {
        saveNoteButton!!.setOnClickListener { v: View? ->
            val note = NoteEntity(
                sendingId,
                titleEditText!!.text.toString(),
                contentEditText!!.text.toString(),
                dateTextView!!.text.toString()
            )
            controller!!.saveNote(note)
        }
    }

    private val currentDate: String
        private get() {
            val currentDate = Date()
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return dateFormat.format(currentDate)
        }

    interface Controller {
        fun saveNote(noteEntity: NoteEntity?)
    }

    companion object {
        private const val MESSAGE_KEY = "MESSAGE_KEY"
    }

    object ObjectWithStatic{
        @JvmStatic
        fun newInstance(noteEntity: NoteEntity?): NoteEditFragment {
            val noteEditFragment = NoteEditFragment()
            val bundle = Bundle()
            bundle.putParcelable(MESSAGE_KEY, noteEntity)
            noteEditFragment.arguments = bundle
            return noteEditFragment
        }
    }
}