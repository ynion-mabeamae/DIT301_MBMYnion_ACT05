package com.example.notekeeperapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
  private var notes: MutableList<Note>,
  private val onNoteClicked: (Note) -> Unit,
  private val onNoteLongClicked: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

  class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    val tvContent: TextView = view.findViewById(R.id.tvContent)
    val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    return NoteViewHolder(v)
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    val note = notes[position]
    holder.tvTitle.text = note.title
    holder.tvContent.text = note.content
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    holder.tvTimestamp.text = sdf.format(Date(note.timestamp))
    holder.itemView.setOnClickListener { onNoteClicked(note) }
    holder.itemView.setOnLongClickListener {
      onNoteLongClicked(note)
      true
    }
  }

  override fun getItemCount(): Int = notes.size

  fun setNotes(newNotes: List<Note>) {
    notes.clear()
    notes.addAll(newNotes)
    notifyDataSetChanged()
  }
}
