package com.app.sw5ebestiary

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityEditListBinding
import kotlin.math.abs

class EditListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditListBinding
    private lateinit var adapter: AddCreatureAdapter
    private lateinit var gestureDetector: GestureDetector
    private var searchMode : String = "Name"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditListBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
        var selectedCreatures : MutableList<CreatureItem> = creatureItemList.toMutableList()
        selectedCreatures.forEach{
            it.isSelected = currentList?.creatures!!.contains(it.creature)
        }
        selectedCreatures = selectedCreatures.sortedWith(compareByDescending<CreatureItem> {it.isSelected}.thenBy {it.creature.name}).toMutableList()
        adapter = AddCreatureAdapter(selectedCreatures) {
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
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                val filteredList = if(query != ""){
                    creatureItemList.filter {
                        it.isSelected
                    } + creatureItemList.filter {
                        when (searchMode) {
                            "Name" -> {
                                it.creature.name.contains(query, ignoreCase = true)
                            }
                            "Type"-> {
                                val spaceIndex = it.creature.classification.indexOf(" ")
                                val commaIndex = it.creature.classification.indexOf(",")
                                it.creature.classification.substring(spaceIndex, commaIndex).trim().contains(query, ignoreCase = true)
                            }
                            "Alignment"-> {
                                it.creature.classification.substringAfter(", ").contains(query, ignoreCase = true)
                            }
                            "Size" -> {
                                it.creature.classification.substringBefore(" ").contains(query, ignoreCase = true)
                            }
                            "CR" -> {
                                it.creature.traitsSectionOne?.last()?.substring(
                                    it.creature.traitsSectionOne.last().indexOf(" ").plus(1), it.creature.traitsSectionOne.last().indexOf("(")
                                )?.trim()
                                    ?.equals(query, ignoreCase = true) ?: false
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