package com.app.sw5ebestiary

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Creature(val name : String, val classification : String, val ac : String, val hp : String, val speed : String, val scores : List<Int>, val traitsSectionOne : List<String>, val traitsSectionTwo : List<String>? = null, val actions : List<String>, val reactions : List<String>? = null, val legendaryActions : List<String>? = null, val id : Int = getNextId())
{
    companion object{
        private var currentId = 0
        private fun getNextId() : Int{
            currentId+=1
            return currentId
        }
    }
}

data class CreatureItem(
    val creature : Creature,
    var isSelected : Boolean = false
)
data class CreatureList(
    val name : String,
    var creatures : MutableList<Creature>,
    val id : Int = getNextId()
)
{
    companion object{
        private var currentId = 0
        private fun getNextId() : Int{
            currentId+=1
            return currentId
        }
    }
}


// Function for making selectable creatureItems
fun makeCreatureItems(creatures: List<Creature>) : List<CreatureItem>{
    val result : MutableList<CreatureItem> = mutableListOf()
    creatures.forEach {
        result.add(CreatureItem(it))
    }
    return result
}

// Function to save custom list to SharedPreferences
fun saveCustomList(context: Context, customList: CreatureList) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("creature_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val gson = Gson()
    val json = gson.toJson(customList)

    editor.putString("custom_list_${customList.id}", json)
    editor.apply()
}

// Function to delete custom lists
fun deleteCustomList(context: Context, customListId: Int) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("creature_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Remove the list by its unique key
    editor.remove("custom_list_$customListId")
    editor.apply()
}

// Function to retrieve custom lists
fun getCustomLists(context: Context): List<CreatureList> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("creature_prefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val customLists = mutableListOf<CreatureList>()

    // Loop through all stored entries
    sharedPreferences.all.forEach { entry ->
        val json = entry.value as String
        val type = object : TypeToken<CreatureList>() {}.type
        val customList: CreatureList = gson.fromJson(json, type)
        customLists.add(customList)
    }
    return customLists
}

fun importJson(context : Context) : List<Creature>{
    val json = Json {
        prettyPrint = true
    }
    val assetManager = context.assets
    val inputStream = assetManager.open("creatures.json")
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val list : List<Creature> = json.decodeFromString(jsonString)
    return list.sortedBy {it.name}
}
var currentCreature : String = ""
var currentList : CreatureList? = null
var creatures : List<Creature> = listOf()

var creatureItemList : List<CreatureItem> = listOf()
// everything from 46 to 189 still needs doing
