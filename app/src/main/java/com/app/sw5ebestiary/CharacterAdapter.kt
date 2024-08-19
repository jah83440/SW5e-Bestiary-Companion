package com.app.sw5ebestiary


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(private var creatures : List<Creature>, private val context: Context) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    class CharacterViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = creatures[position]
        holder.nameTextView.text = character.name
        holder.nameTextView.setOnClickListener {
            currentCreature = character.name
            val intent = Intent(context, CharacterLoadActivity::class.java)
            context.startActivity(intent)
        }
    }




    override fun getItemCount() = creatures.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Creature>) {
        creatures = newList
        notifyDataSetChanged()
    }
}