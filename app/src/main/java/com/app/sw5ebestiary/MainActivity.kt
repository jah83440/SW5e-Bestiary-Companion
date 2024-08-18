package com.app.sw5ebestiary

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.sw5ebestiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.basicSearch.setOnClickListener {
            val intent = Intent(this, BasicSearchActivity::class.java)
            startActivity(intent)
        }
        binding.advSearch.setOnClickListener {
            val intent = Intent(this, AdvancedSearchActivity::class.java)
            startActivity(intent)
        }
        // remove this line when lists work
        binding.lists.visibility = INVISIBLE
        binding.lists.setOnClickListener {
            val intent = Intent(this, ListsActivity::class.java)
            startActivity(intent)
        }
    }
}