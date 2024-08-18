package com.app.sw5ebestiary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomListAdapter (private var lists : List<CreatureList>, private val context: Context) : RecyclerView.Adapter<CustomListAdapter.CustomListViewHolder>(){
    class CustomListViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameTextView: TextView = view.findViewById(R.id.listNameTextView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomListViewHolder(view)
    }
    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: CustomListViewHolder, position: Int) {
        val list = lists[position]
        holder.nameTextView.text = list.name
        holder.nameTextView.setOnClickListener {
            currentList = list
            val intent = Intent(context, ListLoadActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun updateList(customLists: MutableList<CreatureList>) {
        lists = customLists
        notifyDataSetChanged()
    }
}
