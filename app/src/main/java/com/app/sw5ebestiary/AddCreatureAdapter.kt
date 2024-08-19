package com.app.sw5ebestiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddCreatureAdapter(
    private val creatures: List<CreatureItem>,
    private val onCreatureSelected: (CreatureItem) -> Unit
) : RecyclerView.Adapter<AddCreatureAdapter.CreatureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_creature, parent, false)
        return CreatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatureViewHolder, position: Int) {
        val creatureItem = creatures[position]
        holder.bind(creatureItem, onCreatureSelected)
    }

    override fun getItemCount(): Int = creatures.size

    class CreatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.creature_name)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(creatureItem: CreatureItem, onCreatureSelected: (CreatureItem) -> Unit) {
            nameTextView.text = creatureItem.creature.name
            checkBox.isChecked = creatureItem.isSelected

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                creatureItem.isSelected = isChecked
                onCreatureSelected(creatureItem)
            }
        }
    }
}