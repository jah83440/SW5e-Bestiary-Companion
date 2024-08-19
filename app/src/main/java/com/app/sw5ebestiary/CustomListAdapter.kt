package com.app.sw5ebestiary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomListAdapter (private var lists : List<CreatureList>, private val context: Context) : RecyclerView.Adapter<CustomListAdapter.CustomListViewHolder>(){
    private var isDeletionMode: Boolean = false
    private val selectedItems: MutableSet<Int> = mutableSetOf()
    class CustomListViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameTextView: TextView = view.findViewById(R.id.listNameTextView)
        val checkbox : CheckBox = view.findViewById(R.id.checkbox)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomListViewHolder(view)
    }
    override fun getItemCount(): Int = lists.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CustomListViewHolder, position: Int) {
        val list = lists[position]
        holder.nameTextView.text = list.name
        // Show checkbox only in deletion mode
        holder.checkbox.visibility = if (isDeletionMode) VISIBLE else GONE
        holder.checkbox.isChecked = selectedItems.contains(position)

        // Handle checkbox selection
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add(position)
            } else {
                selectedItems.remove(position)
            }
        }

        // Handle long press to enable deletion mode
        holder.itemView.setOnLongClickListener {
            isDeletionMode = true
            (context as ListsActivity).binding.deleteButton.visibility = VISIBLE
            context.binding.cancelButton.visibility = VISIBLE
            notifyDataSetChanged() // Refresh the list to show checkboxes
            true
        }
        holder.nameTextView.setOnClickListener {
            if(!isDeletionMode)
            {
                currentList = list
                val intent = Intent(context, ListLoadActivity::class.java)
                context.startActivity(intent)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(customLists: MutableList<CreatureList>) {
        lists = customLists
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectedItems() {
        isDeletionMode = false
        selectedItems.forEach{
            val current = lists[it]
            deleteCustomList(context, current.id)
        }
        notifyDataSetChanged()
    }
    fun cancelDelete(){
        isDeletionMode = false
    }
}
