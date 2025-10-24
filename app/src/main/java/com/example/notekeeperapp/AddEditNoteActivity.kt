package com.example.notekeeperapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditNoteActivity : AppCompatActivity() {

  companion object {
    const val EXTRA_ID = "extra_id"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_CONTENT = "extra_content"
  }

  private lateinit var db: NotesDbHelper
  private var noteId: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_edit)
    db = NotesDbHelper(this)

    val etTitle = findViewById<EditText>(R.id.etTitle)
    val etContent = findViewById<EditText>(R.id.etContent)
    val btnSave = findViewById<Button>(R.id.btnSave)

    // If editing existing note
    noteId = intent.getIntExtra(EXTRA_ID, 0)
    if (noteId != 0) {
      val note = db.getNoteById(noteId)
      note?.let {
        etTitle.setText(it.title)
        etContent.setText(it.content)
      }
    }

    btnSave.setOnClickListener {
      val title = etTitle.text.toString().trim()
      val content = etContent.text.toString().trim()
      val timestamp = System.currentTimeMillis()

      if (noteId == 0) {
        db.insertNote(title, content, timestamp)
      } else {
        db.updateNote(noteId, title, content, timestamp)
      }

      setResult(Activity.RESULT_OK)
      finish()
    }
  }
}