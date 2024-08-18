package com.app.sw5ebestiary

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityAdvancedSearchBinding

class AdvancedSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdvancedSearchBinding
    private lateinit var adapter : CharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvancedSearchBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.searchInput.requestFocus()
        showKeyboard(binding.searchInput)
        adapter = CharacterAdapter(creatures, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                val filteredList = creatures.filter {
                    it.name.contains(query, ignoreCase = true) || it.classification.contains(query, ignoreCase = true) || it.traitsSectionOne?.last()?.substring(
                        it.traitsSectionOne.last().indexOf(" ").plus(1), it.traitsSectionOne.last().indexOf("(")
                    )?.trim()
                        ?.equals(query, ignoreCase = true) ?: false
                }
                adapter.updateList(filteredList)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun showKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}