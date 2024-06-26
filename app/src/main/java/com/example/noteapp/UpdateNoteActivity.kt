package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set content view to the root view of the binding

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updatetitleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.content)

        binding.updatesaveButton.setOnClickListener {
            val newTitle = binding.updatetitleEditText.text.toString().trim()
            val newContent = binding.updatecontentEditText.text.toString().trim()
            if (newTitle.isEmpty() || newContent.isEmpty()) {
                Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (newTitle == note.title && newContent == note.content) {
                Toast.makeText(this, "No changes to save", Toast.LENGTH_SHORT).show()
            } else {
                val updateNote = Note(noteId, newTitle, newContent)
                db.updateNote(updateNote)
                finish()
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            }
        }

    }

}