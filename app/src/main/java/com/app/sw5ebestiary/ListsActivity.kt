package com.app.sw5ebestiary

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
//import android.view.View
import android.view.View.GONE
//import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sw5ebestiary.databinding.ActivityListsBinding
import kotlin.math.abs

class ListsActivity : AppCompatActivity() {
    internal lateinit var binding: ActivityListsBinding
    private lateinit var adapter: CustomListAdapter
    private lateinit var makeListLauncher: ActivityResultLauncher<Intent>
    private lateinit var customLists : MutableList<CreatureList>
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
        customLists = getCustomLists(this).toMutableList()
        adapter = CustomListAdapter(customLists, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        makeListLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->

        }
        binding.newList.setOnClickListener {
            val intent = Intent(this, MakeListActivity::class.java)
            makeListLauncher.launch(intent)
        }

        // Handle the delete button click
        binding.deleteButton.setOnClickListener {
            adapter.deleteSelectedItems()
            binding.deleteButton.visibility = GONE
            binding.cancelButton.visibility = GONE
            onResume()
        }

        binding.cancelButton.setOnClickListener {
            adapter.cancelDelete()
            binding.deleteButton.visibility = GONE
            binding.cancelButton.visibility = GONE
            onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        customLists = getCustomLists(this).toMutableList()
        adapter.updateList(customLists)
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