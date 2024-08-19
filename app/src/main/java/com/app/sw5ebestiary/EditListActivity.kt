package com.app.sw5ebestiary

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityEditListBinding

class EditListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditListBinding
    private lateinit var adapter: AddCreatureAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditListBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val selectedCreatures : MutableList<CreatureItem> = creatureItemList.toMutableList()
        selectedCreatures.forEach{
            it.isSelected = currentList?.creatures!!.contains(it.creature)
        }
        adapter = AddCreatureAdapter(selectedCreatures) {
            if(it.isSelected){
                selectedCreatures.add(it)
            }
            else{
                selectedCreatures.remove(it)
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.saveList.setOnClickListener {
            deleteCustomList(this, currentList!!.id)
            val newList = CreatureList(currentList!!.name, mutableListOf(), currentList!!.id)
            selectedCreatures.forEach{
                if(it.isSelected)
                    newList.creatures.add(it.creature)
            }

            newList.creatures = newList.creatures.filterIndexed { index, item ->
                newList.creatures.indexOf(item) == index
            }.toMutableList()
            currentList?.creatures = newList.creatures
            saveCustomList(this, newList)
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}