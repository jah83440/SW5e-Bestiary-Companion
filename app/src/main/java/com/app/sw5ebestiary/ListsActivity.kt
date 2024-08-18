package com.app.sw5ebestiary

import android.content.Intent
import android.os.Bundle
//import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityListsBinding

class ListsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListsBinding
    private lateinit var adapter: CustomListAdapter
    private lateinit var makeListLauncher: ActivityResultLauncher<Intent>
    private lateinit var customLists : MutableList<CreatureList>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        customLists = getCustomLists(this).toMutableList()
        adapter = CustomListAdapter(customLists, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        makeListLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->

        }
        binding.newList.setOnClickListener {
            val intent = Intent(this, MakeListActivity::class.java)
            makeListLauncher.launch(intent)        }
    }

    override fun onResume() {
        super.onResume()
        customLists = getCustomLists(this).toMutableList()
        adapter.updateList(customLists)
    }
}