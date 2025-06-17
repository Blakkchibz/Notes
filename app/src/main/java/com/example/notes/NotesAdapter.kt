package com.example.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.NoteItemBinding

class NotesAdapter(
    private val notesList: List<Note>,
    private val onItemClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.binding.titleTextView.text = note.title
        holder.binding.descTextView.text = note.desciption
        holder.itemView.setOnClickListener { onItemClick(note) }
        holder.binding.editIcon.setOnClickListener { onItemClick(note) }
        holder.binding.deleteIcon.setOnClickListener { onDeleteClick(note) }
    }

    override fun getItemCount() = notesList.size
}