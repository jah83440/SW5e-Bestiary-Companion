package com.app.sw5ebestiary

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityListLoadBinding
import kotlin.math.abs

class ListLoadActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListLoadBinding
    private lateinit var adapter : CharacterAdapter
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLoadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
        adapter = CharacterAdapter(currentList?.creatures!!.toList(), this)
        binding.textView.text = currentList?.name
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
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