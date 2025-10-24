package com.example.notekeeperapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

  private lateinit var db: NotesDbHelper
  private lateinit var adapter: NotesAdapter
  private lateinit var rvNotes: RecyclerView

  private val addEditLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    // refresh list on return
    if (it.resultCode == RESULT_OK) loadNotes()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    db = NotesDbHelper(this)
    rvNotes = findViewById(R.id.rvNotes)
    rvNotes.layoutManager = LinearLayoutManager(this)
    adapter = NotesAdapter(mutableListOf(), { note -> openEdit(note) }, { note -> confirmDelete(note) })
    rvNotes.adapter = adapter

    findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
      val intent = Intent(this, AddEditNoteActivity::class.java)
      addEditLauncher.launch(intent)
    }

    loadNotes()
  }

  private fun loadNotes() {
    val notes = db.getAllNotes()
    adapter.setNotes(notes)
  }

  private fun openEdit(note: Note) {
    val intent = Intent(this, AddEditNoteActivity::class.java).apply {
      putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
    }
    addEditLauncher.launch(intent)
  }

  private fun confirmDelete(note: Note) {
    AlertDialog.Builder(this)
      .setTitle("Delete note")
      .setMessage("Are you sure you want to delete this note?")
      .setPositiveButton("Delete") { _, _ ->
        db.deleteNote(note.id)
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        loadNotes()
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  override fun onResume() {
    super.onResume()
    loadNotes()
  }
}