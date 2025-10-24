package com.example.notekeeperapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

  companion object {
    private const val DATABASE_NAME = "notes.db"
    private const val DATABASE_VERSION = 1

    const val TABLE = "notes"
    const val COL_ID = "id"
    const val COL_TITLE = "title"
    const val COL_CONTENT = "content"
    const val COL_TIMESTAMP = "timestamp"
  }

  override fun onCreate(db: SQLiteDatabase) {
    val create = """
            CREATE TABLE $TABLE (
              $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
              $COL_TITLE TEXT,
              $COL_CONTENT TEXT,
              $COL_TIMESTAMP INTEGER
            )
        """.trimIndent()
    db.execSQL(create)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("DROP TABLE IF EXISTS $TABLE")
    onCreate(db)
  }

  fun insertNote(title: String, content: String, timestamp: Long): Long {
    val db = writableDatabase
    val cv = ContentValues().apply {
      put(COL_TITLE, title)
      put(COL_CONTENT, content)
      put(COL_TIMESTAMP, timestamp)
    }
    return db.insert(TABLE, null, cv)
  }

  fun updateNote(id: Int, title: String, content: String, timestamp: Long): Int {
    val db = writableDatabase
    val cv = ContentValues().apply {
      put(COL_TITLE, title)
      put(COL_CONTENT, content)
      put(COL_TIMESTAMP, timestamp)
    }
    return db.update(TABLE, cv, "$COL_ID=?", arrayOf(id.toString()))
  }

  fun deleteNote(id: Int): Int {
    val db = writableDatabase
    return db.delete(TABLE, "$COL_ID=?", arrayOf(id.toString()))
  }

  fun getAllNotes(): List<Note> {
    val notes = mutableListOf<Note>()
    val db = readableDatabase
    val cursor: Cursor = db.query(TABLE, null, null, null, null, null, "$COL_TIMESTAMP DESC")
    cursor.use {
      if (it.moveToFirst()) {
        do {
          val id = it.getInt(it.getColumnIndexOrThrow(COL_ID))
          val title = it.getString(it.getColumnIndexOrThrow(COL_TITLE))
          val content = it.getString(it.getColumnIndexOrThrow(COL_CONTENT))
          val timestamp = it.getLong(it.getColumnIndexOrThrow(COL_TIMESTAMP))
          notes.add(Note(id, title, content, timestamp))
        } while (it.moveToNext())
      }
    }
    return notes
  }

  fun getNoteById(id: Int): Note? {
    val db = readableDatabase
    val cursor = db.query(TABLE, null, "$COL_ID=?", arrayOf(id.toString()), null, null, null)
    cursor.use {
      if (it.moveToFirst()) {
        val title = it.getString(it.getColumnIndexOrThrow(COL_TITLE))
        val content = it.getString(it.getColumnIndexOrThrow(COL_CONTENT))
        val timestamp = it.getLong(it.getColumnIndexOrThrow(COL_TIMESTAMP))
        return Note(id, title, content, timestamp)
      }
    }
    return null
  }
}