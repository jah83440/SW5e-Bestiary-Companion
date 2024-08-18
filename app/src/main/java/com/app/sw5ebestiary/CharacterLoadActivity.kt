package com.app.sw5ebestiary

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View.GONE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.sw5ebestiary.databinding.ActivityCharacterLoadBinding
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.text.Typography.bullet
class CharacterLoadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterLoadBinding
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterLoadBinding.inflate(layoutInflater)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
        populateTextViews(Color.parseColor("#AFC6D6"))
        enableEdgeToEdge()
        setContentView(binding.root)
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


    private fun populateTextViews(color: Int) {
        val creatureName = currentCreature
        val nameTextView: TextView = binding.nameTextView
        val classificationTextView : TextView = binding.classificationTextView
        val acTextView : TextView = binding.acTextView
        val hpTextView : TextView = binding.hpTextView
        val speedTextView : TextView = binding.speedTextView
        val strTextView : TextView = binding.STRvalue
        val dexTextView : TextView = binding.DEXvalue
        val conTextView : TextView = binding.CONvalue
        val intTextView : TextView = binding.INTvalue
        val wisTextView : TextView = binding.WISvalue
        val chaTextView : TextView = binding.CHAvalue
        val traitsOneTextView : TextView = binding.traitsOneTextView
        val traitsTwoTextView : TextView = binding.traitsTwoTextView
        val actionsTextView : TextView = binding.actionsTextView
        val reactionsTextView : TextView = binding.reactionsTextView
        val legendaryTextView : TextView = binding.legendaryTextView
        val scroll = binding.resultScroll
        val linearLayout = binding.line
        scroll.setBackgroundColor(color)
        linearLayout.setBackgroundColor(color)
        val creature = creatures.find {
            it.name == creatureName
        }
        if(creature != null){
            // setup formatting
            val acSpan = SpannableString(creature.ac)
            val hpSpan = SpannableString(creature.hp)
            val speedSpan = SpannableString(creature.speed)
            val bold = StyleSpan(Typeface.BOLD)
            acSpan.setSpan(bold, 0, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            hpSpan.setSpan(bold, 0, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            speedSpan.setSpan(bold, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


            nameTextView.text = creature.name.uppercase()
            nameTextView.setBackgroundColor(color)
            classificationTextView.text = creature.classification
            classificationTextView.setBackgroundColor(color)


            acTextView.text = acSpan
            acTextView.setBackgroundColor(color)
            hpTextView.text = hpSpan
            hpTextView.setBackgroundColor(color)
            speedTextView.text = speedSpan
            speedTextView.setBackgroundColor(color)


            val str = creature.scores[0].toString() + " (" + getModifier(creature.scores[0]) + ")"
            val dex = creature.scores[1].toString() + " (" + getModifier(creature.scores[1]) + ")"
            val con = creature.scores[2].toString() + " (" + getModifier(creature.scores[2]) + ")"
            val int = creature.scores[3].toString() + " (" + getModifier(creature.scores[3]) + ")"
            val wis = creature.scores[4].toString() + " (" + getModifier(creature.scores[4]) + ")"
            val cha = creature.scores[5].toString() + " (" + getModifier(creature.scores[5]) + ")"
            strTextView.text = str
            strTextView.setBackgroundColor(color)
            dexTextView.text = dex
            dexTextView.setBackgroundColor(color)
            conTextView.text = con
            conTextView.setBackgroundColor(color)
            intTextView.text = int
            intTextView.setBackgroundColor(color)
            wisTextView.text = wis
            wisTextView.setBackgroundColor(color)
            chaTextView.text = cha
            chaTextView.setBackgroundColor(color)

            traitsOneTextView.text = if(!creature.traitsSectionOne.isNullOrEmpty()) formatTraits(formatList(creature.traitsSectionOne), "firstSection") else ""
            traitsOneTextView.setBackgroundColor(color)


            traitsTwoTextView.text = if(!creature.traitsSectionTwo.isNullOrEmpty()) formatTraits(formatList(creature.traitsSectionTwo), "secondSection") else ""
            traitsTwoTextView.setBackgroundColor(color)
            actionsTextView.text = if(creature.actions.isNotEmpty()) formatTraits(formatList(creature.actions),"actions") else ""
            actionsTextView.setBackgroundColor(color)
            reactionsTextView.text = if(!creature.reactions.isNullOrEmpty()) formatTraits(formatList(creature.reactions),"actions") else ""
            reactionsTextView.setBackgroundColor(color)
            legendaryTextView.text = if(!creature.legendaryActions.isNullOrEmpty()) formatTraits(formatList(creature.legendaryActions), "legendary") else ""
            legendaryTextView.setBackgroundColor(color)
            if(creature.legendaryActions == null){
                binding.legendaryHeader.visibility = GONE
                binding.legendaryDivider.visibility = GONE
                binding.legendaryTextView.visibility = GONE
            }
            if(creature.reactions == null){
                binding.reactionsHeader.visibility = GONE
                binding.reactionsDivider.visibility = GONE
                binding.reactionsTextView.visibility = GONE
            }
        }
    }
    private fun formatList(unformattedList : List<String>) : String{
        var result = ""
        unformattedList.forEach {
            result += it
        }
        return result
    }

    private fun formatTraits(unformatted : String, flag : String) : SpannableString{
        val builder = SpannableStringBuilder()

        //val italic = StyleSpan(Typeface.ITALIC)

        // handle according to passed in flag

        when (flag) {
            "firstSection" -> {
                val result = SpannableString(unformatted)
                val traitsOneFlags = listOf("Saving Throws", "Skills", "Damage Vulnerabilities","Damage Resistances", "Damage Immunities", "Condition Immunities", "Senses", "Languages", "Challenge")
                traitsOneFlags.forEach{
                    if(unformatted.contains(it)){
                        val start = unformatted.indexOf(it)
                        val end = start + it.length
                        val bold = StyleSpan(Typeface.BOLD)
                        result.setSpan(bold, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    }
                }
                builder.append(result)
            }
            "secondSection" -> {
                val traitsTwoFlags = unformatted.split("\n").filter {
                    it!=""
                }
                // handle Innate Forcecasting if present
                if(traitsTwoFlags[0].contains("Innate Forcecasting")){
                    val prelude = SpannableString(traitsTwoFlags[0].substringBefore(":") + ":\n")
                    val boldItalic = StyleSpan(Typeface.BOLD_ITALIC)
                    val start = 0
                    val end = prelude.indexOf(".")
                    prelude.setSpan(boldItalic, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    builder.append(prelude)
                    val powers = traitsTwoFlags[0].substringAfter(":").split("*")
                    powers.forEach{
                        val currentSpan = SpannableString(it)
                        val italic = StyleSpan(Typeface.ITALIC)
                        val startIndex = it.indexOf(":") + 1
                        val endIndex = it.length
                        currentSpan.setSpan(italic, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        builder.append(currentSpan)
                        builder.append("\n")
                    }
                    builder.append("\n")
                }
                traitsTwoFlags.forEachIndexed {index, it ->
                    if(it.contains(".") && !it.contains("Innate Forcecasting") && !it.contains("Tech Casting")){
                        val currentSpan = SpannableString(it)
                        val bold = StyleSpan(Typeface.BOLD_ITALIC)
                        val start = 0
                        val end = it.indexOf(".")
                        currentSpan.setSpan(bold, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        builder.append(currentSpan)
                        if(index < traitsTwoFlags.size - 1)
                            builder.append("\n\n")
                    } else if(it.contains(bullet)){
                        val currentSpan = SpannableString(it)
                        val italic = StyleSpan(Typeface.ITALIC)
                        val start = it.indexOf(":") + 1
                        val end = it.length
                        currentSpan.setSpan(italic, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        builder.append(currentSpan)
                        builder.append("\n")
                    }
                }
                // handle tech casting if present
                if(traitsTwoFlags.last().contains("Tech Casting")){
                    val prelude = SpannableString(traitsTwoFlags.last().substringBefore(":") + ":\n")
                    val boldItalic = StyleSpan(Typeface.BOLD_ITALIC)
                    val start = 0
                    val end = prelude.indexOf(".")
                    prelude.setSpan(boldItalic, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    builder.append(prelude)
                    val powers = traitsTwoFlags.last().substringAfter(":").split("*")
                    powers.forEach{
                        val currentSpan = SpannableString(it)
                        val italic = StyleSpan(Typeface.ITALIC)
                        val startIndex = it.indexOf(":") + 1
                        val endIndex = it.length
                        currentSpan.setSpan(italic, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        builder.append(currentSpan)
                        builder.append("\n")
                    }
                }

            }
            "actions" -> {
                val actionsFlags = unformatted.split("\n").filter{it!=""}
                actionsFlags.forEachIndexed {index, it ->
                    if(it.contains(".")){
                        val currentSpan = SpannableString(it)
                        val boldItalic = StyleSpan(Typeface.BOLD_ITALIC)
                        val start = 0
                        val end = it.indexOf(".", 2)
                        currentSpan.setSpan(boldItalic, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        if(it.contains("Attack:")){
                            val italic = StyleSpan(Typeface.ITALIC)
                            val italicEnd = it.indexOf(":", end + 1)
                            currentSpan.setSpan(italic, end + 1, italicEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                            val hitItalic = StyleSpan(Typeface.ITALIC)
                            currentSpan.setSpan(hitItalic, it.indexOf("Hit:"),it.indexOf("Hit:") + 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        }
                        builder.append(currentSpan)
                        if(index < actionsFlags.size - 1)
                            builder.append("\n\n")
                    }
                }
            }
            "reactions" -> {
                val reactionsFlags = unformatted.split("\n").filter{it!=""}
                reactionsFlags.forEachIndexed {index, it ->
                    if(it.contains(".")){
                        val currentSpan = SpannableString(it)
                        val boldItalic = StyleSpan(Typeface.BOLD_ITALIC)
                        val start = 0
                        val end = it.indexOf(".", 2)
                        currentSpan.setSpan(boldItalic, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        builder.append(currentSpan)
                        if(index < reactionsFlags.size - 1)
                            builder.append("\n\n")
                    }
                }
            }
            "legendary" -> {
                val legendaryFlags = unformatted.split("\n").filter{it!=""}
                builder.append(legendaryFlags[0] + "\n\n")
                legendaryFlags.forEachIndexed { index, s ->
                    if(index != 0 && s.contains(".")){
                        val currentSpan = SpannableString(s)
                        val boldItalic = StyleSpan(Typeface.BOLD_ITALIC)
                        val start = 0
                        val end = s.indexOf(".")
                        currentSpan.setSpan(boldItalic, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                        builder.append(currentSpan)
                        builder.append("\n\n")
                    }
                }
            }
        }
        return SpannableString(builder)
    }

    private fun getModifier(score : Int) : String{
        return if(score >= 10){
            "+" + ((score - 10) / 2).toString()
        }
        else{
            "-" + ceil((10 - score) / 2.0).toInt().toString()
        }
    }
}