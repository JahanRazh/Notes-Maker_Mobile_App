package com.example.notesapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    // Declare binding variable
    private lateinit var binding: ActivityAddNoteBinding
    // Declare database helper variable
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using data binding
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database helper
        db = NotesDatabaseHelper(this)

        // Set OnClickListener for the save button
        binding.saveButton.setOnClickListener{
            // Retrieve title and content from EditText fields
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            // Create a new Note object
            val note = Note(0, title, content)
            // Insert the note into the database
            db.insertNote(note)
            // Finish the activity
            finish()
            // Display a toast to indicate that the note is saved
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
        }
    }
}
