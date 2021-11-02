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
import com.example.a_notes.databinding.FragmentNoteEditBinding
import com.example.a_notes.domain.NoteEntity
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
    private var myContext: FragmentManager? = null

    private var sendingId: Int = 0

    private lateinit var binding: FragmentNoteEditBinding

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

        binding = FragmentNoteEditBinding.bind(view)

        initViews()
        note
        onSaveButtonClick()
    }

    private fun initViews() {
        titleEditText = binding.noteTitleEditText
        contentEditText = binding.noteContentEditText
        saveNoteButton = binding.noteButtonSaveNote
        dateTextView = binding.noteDateTextView
        dateTextView.text = currentDate
    }

    private val note: Unit
        get() {
            var noteEntity: NoteEntity? = null
            if (arguments != null) {
                noteEntity = arguments!!.getParcelable(MESSAGE_KEY)
                sendingId = noteEntity!!.id
            }
            noteEntity?.let { fillNote(it) }
        }

    private fun fillNote(note: NoteEntity) {
        titleEditText.setText(note.title)
        contentEditText.setText(note.content)
        dateTextView.text = currentDate
    }

    private fun onSaveButtonClick() {
        saveNoteButton.setOnClickListener {
            val note = NoteEntity(
                sendingId,
                titleEditText.text.toString(),
                contentEditText.text.toString(),
                dateTextView.text.toString()
            )
            controller!!.saveNote(note)
        }
    }

    private val currentDate: String
        get() {
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