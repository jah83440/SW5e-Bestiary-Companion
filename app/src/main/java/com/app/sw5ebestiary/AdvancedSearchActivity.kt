package com.app.sw5ebestiary

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityAdvancedSearchBinding
import kotlin.math.abs

class AdvancedSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdvancedSearchBinding
    private lateinit var adapter : CharacterAdapter
    private lateinit var gestureDetector: GestureDetector
    private var searchMode : String = "Name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvancedSearchBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.searchInput.requestFocus()
        showKeyboard(binding.searchInput)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
        adapter = CharacterAdapter(creatures, this)
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
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                val filteredList = creatures.filter {
                    when (searchMode) {
                        "Name" -> {
                            it.name.contains(query, ignoreCase = true)
                        }
                        "Type"-> {
                            val spaceIndex = it.classification.indexOf(" ")
                            val commaIndex = it.classification.indexOf(",")
                            it.classification.substring(spaceIndex, commaIndex).trim().contains(query, ignoreCase = true)
                        }
                        "Alignment"-> {
                            it.classification.substringAfter(", ").contains(query, ignoreCase = true)
                        }
                        "Size" -> {
                            it.classification.substringBefore(" ").contains(query, ignoreCase = true)
                        }
                        "CR" -> {
                            it.traitsSectionOne?.last()?.substring(
                                it.traitsSectionOne.last().indexOf(" ").plus(1), it.traitsSectionOne.last().indexOf("(")
                            )?.trim()
                                ?.equals(query, ignoreCase = true) ?: false
                        }
                        else -> {
                            true
                        }
                    }
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
}