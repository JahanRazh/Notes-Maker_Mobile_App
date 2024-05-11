package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notesapp.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager // Import LinearLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var  notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        db = NotesDatabaseHelper(this)

        // Initialize the adapter with the notes from the database
        notesAdapter =  NotesAdapter(db.getAllNotes(),this)

        // Set the layout manager for the RecyclerView
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter for the RecyclerView
        binding.notesRecyclerView.adapter = notesAdapter

        // Set click listener for the add button
        binding.addbutton.setOnClickListener{
            // Start the AddNoteActivity when the button is clicked
            val intent = Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the data in the adapter when the activity resumes
        notesAdapter.refreshData(db.getAllNotes())
    }
}
