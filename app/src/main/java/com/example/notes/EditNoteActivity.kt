package com.example.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getIntExtra("noteId", -1)
        val noteTitle = intent.getStringExtra("noteTitle")
        val noteDesc = intent.getStringExtra("noteDesc")

        if (noteId != -1){
            binding.titleEditText.setText(noteTitle)
            binding.descEditText.setText(noteDesc)
        }
        binding.saveBtn.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val desc = binding.descEditText.text.toString()
            val resultIntent = Intent().apply {
                putExtra("noteTitle", title)
                putExtra("noteDesc", desc)
                putExtra("noteId", noteId) // optional: if you're editing
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }


}