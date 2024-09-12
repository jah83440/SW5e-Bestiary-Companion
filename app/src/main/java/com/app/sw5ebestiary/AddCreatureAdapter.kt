package com.app.sw5ebestiary


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddCreatureAdapter(
    private var creatures: List<CreatureItem>,
    private val onCreatureSelected: (CreatureItem) -> Unit
) : RecyclerView.Adapter<AddCreatureAdapter.CreatureViewHolder>() {
    private var dpValue : Int = 0
    private var dpScale : Float = 0.0f
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_creature, parent, false)
        dpValue = (parent.context.resources.displayMetrics.densityDpi)
        dpScale = parent.context.resources.displayMetrics.density

        return CreatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatureViewHolder, position: Int) {
        val creatureItem = creatures[position]
        holder.bind(creatureItem, onCreatureSelected)
    }

    override fun getItemCount(): Int = creatures.size
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(filteredList: List<CreatureItem>) {
        creatures = filteredList
        notifyDataSetChanged()
    }

    inner class CreatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.creature_name)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(creatureItem: CreatureItem, onCreatureSelected: (CreatureItem) -> Unit) {
            checkBox.setOnCheckedChangeListener(null)
            nameTextView.text = creatureItem.creature.name
            val pixels = (dpValue * 0.54f * dpScale).toInt()
            checkBox.isChecked = creatureItem.isSelected
            nameTextView.layoutParams.width = pixels
            Log.i("Density Value", "$dpValue")
            Log.i("Adjusted Width", "$pixels")

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                creatureItem.isSelected = isChecked
                onCreatureSelected(creatureItem)
            }

        }
    }
}