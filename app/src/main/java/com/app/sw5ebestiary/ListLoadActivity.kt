package com.app.sw5ebestiary

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityListLoadBinding

class ListLoadActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListLoadBinding
    private lateinit var adapter : CharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLoadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        adapter = CharacterAdapter(currentList?.creatures!!.toList(), this)
        binding.textView.text = currentList?.name
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}