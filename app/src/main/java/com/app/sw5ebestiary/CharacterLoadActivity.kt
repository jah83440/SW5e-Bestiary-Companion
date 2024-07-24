package com.app.sw5ebestiary

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.sw5ebestiary.databinding.ActivityCharacterLoadBinding

class CharacterLoadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterLoadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterLoadBinding.inflate(layoutInflater)
        populateTextViews(Color.parseColor("#AFC6D6"))
        enableEdgeToEdge()
        setContentView(binding.root)
    }


    private fun populateTextViews(color: Int) {
        val creatureName = currentCreature
        val nameTextView: TextView = binding.nameTextView
        val classificationTextView : TextView = binding.classificationTextView
        val acTextView : TextView = binding.acTextView
        val hpTextView : TextView = binding.hpTextView
        val speedTextView : TextView = binding.speedTextView
        val scoresTextView : TextView = binding.scoresTextView
        val traitsOneTextView : TextView = binding.traitsOneTextView
        val traitsTwoTextView : TextView = binding.traitsTwoTextView
        val actionsTextView : TextView = binding.actionsTextView
        val legendaryTextView : TextView = binding.legendaryTextView
        val scroll = binding.resultScroll
        val linearLayout = binding.line
        scroll.setBackgroundColor(color)
        linearLayout.setBackgroundColor(color)
        val creature = creatures.find {
            it.name == creatureName
        }
        if(creature != null){
            nameTextView.text = creature.name
            nameTextView.setBackgroundColor(color)
            classificationTextView.text = creature.classification
            classificationTextView.setBackgroundColor(color)
            acTextView.text = creature.ac
            acTextView.setBackgroundColor(color)
            hpTextView.text = creature.hp
            hpTextView.setBackgroundColor(color)
            speedTextView.text = creature.speed
            speedTextView.setBackgroundColor(color)
            scoresTextView.text = getString(R.string.scoreName) + creature.scores.toString().removePrefix("[").removeSuffix("]").replace(",", "   ")
            scoresTextView.setBackgroundColor(color)
            traitsOneTextView.text = if(!creature.traitsSectionOne.isNullOrEmpty()) formatList(creature.traitsSectionOne) else ""
            traitsOneTextView.setBackgroundColor(color)
            traitsTwoTextView.text = if(!creature.traitsSectionTwo.isNullOrEmpty()) formatList(creature.traitsSectionTwo) else ""
            traitsTwoTextView.setBackgroundColor(color)
            actionsTextView.text = creature.actions.toString().removePrefix("[").removeSuffix("]").replace(", ", "")
            actionsTextView.setBackgroundColor(color)
            legendaryTextView.text = if(!creature.legendaryActions.isNullOrEmpty()) formatList(creature.legendaryActions) else ""
            legendaryTextView.setBackgroundColor(color)
        }
    }
    private fun formatList(unformattedList : List<String>) : String{
        var result = ""
        unformattedList.forEach {
            result += it
        }
        return result
    }
}