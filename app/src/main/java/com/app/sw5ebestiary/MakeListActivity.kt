package com.app.sw5ebestiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
//import android.util.Log
import android.view.inputmethod.InputMethodManager
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