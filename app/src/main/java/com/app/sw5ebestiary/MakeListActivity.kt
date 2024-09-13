package com.app.sw5ebestiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
//import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityMakeListBinding
import kotlin.math.abs

class MakeListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeListBinding
    private lateinit var adapter: AddCreatureAdapter
    private lateinit var gestureDetector: GestureDetector
    private var searchMode : String = "Name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeListBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
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
        val spinner = binding.advSearchDropdown
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.advSearchOption, R.layout.custom_spinner_item)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                searchMode = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                searchMode = parent?.getItemAtPosition(0).toString()
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
        binding.listName.setOnEditorActionListener {
            v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER){
                binding.searchInput.requestFocus()
                showKeyboard(binding.searchInput)
                true
            } else{
                false
            }
        }
        binding.searchInput.setOnEditorActionListener {
                v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER){
                hideKeyboard(binding.searchInput)
                true
            } else{
                false
            }
        }
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                val filteredList = if(query != ""){
                    creatureItemList.filter {
                        it.isSelected
                    } + creatureItemList.filter {
                        when (searchMode) {
                            "Name" -> {
                                it.creature.name.contains(query, ignoreCase = true) && !selectedCreatures.contains(it)
                            }
                            "Type"-> {
                                val spaceIndex = it.creature.classification.indexOf(" ")
                                val commaIndex = it.creature.classification.indexOf(",")
                                it.creature.classification.substring(spaceIndex, commaIndex).trim().contains(query, ignoreCase = true) && !selectedCreatures.contains(it)
                            }
                            "Alignment"-> {
                                it.creature.classification.substringAfter(", ").contains(query, ignoreCase = true) && !selectedCreatures.contains(it)
                            }
                            "Size" -> {
                                it.creature.classification.substringBefore(" ").contains(query, ignoreCase = true) && !selectedCreatures.contains(it)
                            }
                            "CR" -> {
                                it.creature.traitsSectionOne?.last()?.substring(
                                    it.creature.traitsSectionOne.last().indexOf(" ").plus(1), it.creature.traitsSectionOne.last().indexOf("(")
                                )?.trim()
                                    ?.equals(query, ignoreCase = true) ?: false && !selectedCreatures.contains(it)
                            }
                            else -> {
                                true
                            }
                        }
                    }
                } else{
                    creatureItemList.filter {it.isSelected} + creatureItemList.filter {!it.isSelected}
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

    private fun hideKeyboard(editText: EditText){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
    private inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener(){
        private val swipeThreshold = 100
        private val swipeVelocityThreshold = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if(e1 != null){
                val diffX = e2.x - e1.x
                val diffY = e2.y - e1.y
                if(abs(diffX) > abs(diffY)){
                    if(abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold){
                        if(diffX > 0){
                            onSwipeRight()
                        }
                        return true
                    }
                }
            }
            return false
        }
    }
    private fun onSwipeRight(){
        finish()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            gestureDetector.onTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
        creatureItemList.forEach{
            it.isSelected = false
        }
    }
}