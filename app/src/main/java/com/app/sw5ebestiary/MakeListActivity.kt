package com.app.sw5ebestiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
//import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityMakeListBinding

class MakeListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeListBinding
    private lateinit var adapter: AddCreatureAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeListBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.listName.requestFocus()
        showKeyboard(binding.listName)
        val selectedCreatures : MutableList<CreatureItem> = mutableListOf()
        adapter = AddCreatureAdapter(creatureItemList) {
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
            val newList = CreatureList(binding.listName.text.toString(), mutableListOf())
            selectedCreatures.forEach{
                newList.creatures.add(it.creature)
            }
            saveCustomList(this, newList)
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
    private fun showKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}