package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter
    private var allNotes: List<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        db = NotesDatabaseHelper(this)

        // Initialize the adapter with the notes from the database
        allNotes = db.getAllNotes()
        notesAdapter = NotesAdapter(allNotes, this)

        // Set the layout manager for the RecyclerView
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter for the RecyclerView
        binding.notesRecyclerView.adapter = notesAdapter

        // Set click listener for the add button
        binding.addbutton.setOnClickListener {
            // Start the AddNoteActivity when the button is clicked
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Set up search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // If the search query is empty, show all notes
                    notesAdapter.refreshData(allNotes)
                } else {
                    // Filter notes based on the search query
                    val filteredNotes = allNotes.filter { note ->
                        note.title.contains(newText, true) // Case-insensitive search
                    }
                    notesAdapter.refreshData(filteredNotes)
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Refresh the data in the adapter when the activity resumes
        allNotes = db.getAllNotes()
        notesAdapter.refreshData(allNotes)
    }


}
