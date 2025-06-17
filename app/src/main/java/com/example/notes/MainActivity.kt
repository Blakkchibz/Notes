package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var notesList = mutableListOf<Note>()
    private lateinit var adapter: NotesAdapter

    companion object {
        const val REQUEST_CODE_EDIT_NOTE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NotesAdapter(
            notesList,
            onItemClick = { note ->
                val intent = Intent(this, EditNoteActivity::class.java).apply {
                    putExtra("noteId", note.id)
                    putExtra("noteTitle", note.title)
                    putExtra("noteDesc", note.desciption)
                }
                startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE)
            },
            onDeleteClick = {note ->
                notesList.remove(note)
                adapter.notifyDataSetChanged()
            }
        )

        binding.notesRecycleView.layoutManager = LinearLayoutManager(this)
        binding.notesRecycleView.adapter = adapter

        binding.addNoteBtn.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_EDIT_NOTE && resultCode == Activity.RESULT_OK && data != null) {
            val id = data.getIntExtra("noteId", -1)
            val title = data.getStringExtra("noteTitle") ?: ""
            val desc = data.getStringExtra("noteDesc") ?: ""

            if (id >= 0) {
                // Editing an existing note
                val index = notesList.indexOfFirst { it.id == id }
                if (index != -1) {
                    notesList[index].title = title
                    notesList[index].desciption = desc
                    adapter.notifyItemChanged(index)
                }
            } else {
                // Adding a new note
                val newNote = Note(
                    id = if (notesList.isEmpty()) 0 else notesList.maxOf { it.id } + 1,
                    title = title,
                    desciption = desc
                )
                notesList.add(newNote)
                adapter.notifyItemInserted(notesList.lastIndex)
            }
        }
    }


}