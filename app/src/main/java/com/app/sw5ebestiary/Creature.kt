package com.app.sw5ebestiary

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.text.Typography.bullet

data class Creature(val name : String, val classification : String, val ac : String, val hp : String, val speed : String, val scores : List<Int>, val traitsSectionOne : List<String>?, val traitsSectionTwo : List<String>?, val actions : List<String>, val reactions : List<String>? = null, val legendaryActions : List<String>? = null, val id : Int = getNextId())
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

var currentCreature : String = ""
var currentList : CreatureList? = null
val creatures = listOf(
    Creature("Avatar of Abeloth",
        "Medium undead, neutral dark side",
        "Armor Class 17 (force shield)",
        "Hit Points 153 (18d6 + 90)",
        "Speed 30 ft. walk",
        // scores
        listOf(11, 16, 20, 16, 14, 20),
        // traits 1
        listOf(
            "Saving Throws Con +12, Wis +9, Cha +1\n",
            "Skills Lore +10, Perception +9, Insight +9, Deception +19, Persuasion +12\n",
            "Damage Resistances cold, lightning, necrotic\n",
            "Damage Immunities poison; kinetic, ion, and energy from unenhanced weapons\n",
            "Condition Immunities charmed, exhaustion, frightened, paralyzed, poisoned\n",
            "Senses truesight 120ft., passive Perception 19\n",
            "Languages all, telepathy 120 ft.\n",
            "Challenge 21 (33,000 XP)\n"
        ),
        // traits 2
        listOf(
            "Legendary Resistance (3/day). If Abeloth fails a saving throw, she can choose to succeed instead.\n",
            "\nForce Resistance. Abeloth has advantage on saving throws against Force Powers.\n",
            "\nFont of Power. When Abeloth rolls damage for a power, she can spend 1 additional force point to reroll a number of the damage dice up to her Charisma modifier (5). She must use the new rolls.\n",
            "\nPool of Knowledge. When you cast a power that has a casting time of 1 action, you can spend 2 additional force points to change the casting time to 1 bonus action for this casting.\n",
            "\nForcecasting. Abeloth is an 18th-level forcecaster. Her Forcecasting Ability is Charisma (force save DC 20, +12 to hit with force powers). Abeloth has 77 Force Points and knows the following powers:\n$bullet At will: Burst, Feedback, Force Push/Pull, Give Life, Saber Reflect, Shock\n$bullet 1st level: Cloud Mind, Force Mask, Force Throw, Project\n$bullet 2nd level: Coerce Mind, Hallucination\n$bullet 3rd level: Force Lightning, Force Repulse, Knight Speed, Telekinetic Storm\n$bullet 4th level: Drain Life, Mind Tap\n$bullet 5th level: Dominate Mind, Greater Feedback, Siphon Life\n$bullet 6th level: Mass Coerce Mind, Telekinetic Burst\n$bullet 7th level: Force Project, Whirlwind\n$bullet 8th level: Death Field, Earthquake, Telekinetic Wave\n$bullet 9th level: Master Feedback"
        ),
        // actions
        listOf(
            "Shotosaber. Melee Weapon Attack: +10 to hit, reach 5 ft., one creature. Hit: 10 (2d6 +3) energy damage.\n",
            "Teleport. Abeloth teleports up to 120 feet into an unoccupied space she can see."
        ),
        reactions = null,
        // legendary actions
        listOf(
            "Abeloth can take 3 Legendary Actions, choosing from the options below. One legendary action can be used at a time, and only at the end of another's turn. Spent Legendary Actions are regained at the start of each turn.\n\n",
            "At Will Power. Abeloth casts an at will power.\n",
            "Attack. Abeloth attacks once with Shotosaber.\n",
            "Teleport. Abeloth uses her teleport action.\n"
        )
    ),
    Creature(
        "Manifestation of Abeloth",
        "Medium undead, neutral dark side",
        "Armor Class 22",
        "Hit Points 406 (28d12 + 224)",
    "Speed 30 ft. walk",
        // scores
        listOf(29, 21, 26, 20, 17, 25),
        // traits 1
        listOf(
            "Saving Throws Dex +13, Con +16, Wis +11, Cha +15\n",
            "Skills Deception +15, Insight +11, Perception +19\n",
            "Damage Resistances cold, fire, lightning\n",
            "Damage Immunities poison; kinetic, ion and energy from unenhanced weapons.\n",
            "Condition Immunities charmed, exhaustion, frightened, poisoned\n",
            "Senses truesight 120 ft., passive Perception 29\n",
            "Languages all, telepathy 120 ft.\n",
            "Challenge 26 (90,000 XP)\n"
        ),
        // traits 2
        listOf(
            "Innate Forcecasting. Abeloth's forcecasting ability is Charisma (force save DC 23). Abeloth can innately cast the following powers:At will: coerce mind, sense force*3/day each: fear, force suppression, telekinesis*1/day each: telekinetic wave, force lightning cone\n",
            "Legendary Resistance (3/day). If Abeloth fails a saving throw, she can choose to succeed instead.\n",
            "Force Resistance. Abeloth has advantage on saving throws against force powers and effects.\n",
            "Enhanced Being. Abeloth's weapon attacks are enhanced.\n",
            "One with the Force. Abeloth has advantage on saving throws against being blinded, deafened, stunned, or knocked unconscious.\n"
        ),
        // actions
        listOf(
            "Multiattack. Abeloth makes two tentacle attacks.\n",
            "Tentacles. Melee Weapon Attack: +17 to hit, reach 5ft., one target. Hit: 28 (3d12 + 9) kinetic damage. If the target is a creature, it must succeed on a DC 23 Constitution saving throw or its hit point maximum is reduced by an amount equal to the damage taken. This reduction lasts until the target finishes a long rest. If the target's hit point maximum is reduced to 0, they die and their essence is absorbed into Abeloth.\n",
            "Will. Abeloth turns her will upon one creature she can see within 120 feet. That target must make a DC 23 Wisdom saving throw. Unless the target is incapacitated, it can avert it's attention to automatically succeed on the save. If the target does so, it can't see Abeloth until the start of its next turn. If the target again focuses on Abeloth in the meantime, it must immediately make the save. If the target fails the save, the target suffers one of the following effects of Abeloth's choice or at random:\n1. Beguiling Will. The target is stunned until the start of Abeloth's next turn.\n2. Hypnotic Will. The target is charmed by Abeloth until the start of Abeloth's next turn. Abeloth chooses how the charmed target uses its actions, reactions, and movement. Because this effect requires Abeloth's strong effort, she can't use her Maddening Will legendary action until the start of her next turn.\n3. Insane Will. The target suffers the effect of the confusion power without making a saving throw. The effect lasts until the start of Abeloth's next turn. Abeloth doesn't need to concentrate on the power.\n"
        ),
        reactions = null,
        // legendary actions
        listOf(
            "Abeloth can take 3 Legendary Actions, choosing from the options below. One legendary action can be used at a time, and only at the end of another's turn. Spent Legendary Actions are regained at the start of each turn.\n",
            "Draining Lightning. Ranged Power Attack: +17 to hit, range 15 ft., one target. Hit: 20 (2d10 + 9) lightning damage plus 11 (2d10) necrotic damage.\n",
            "Maddening Will. Abeloth uses her Will action, and must choose either the Beguiling Will or the Insane Will effect."
        )
    ),
    Creature(
        "Acklay, Adolescent",
        "Huge beast, unaligned",
        "Armor Class 13 (natural armor)",
        "Hit Points 95 (10d12 + 30)",
        "Speed 40 ft.",
        //scores
        listOf(22, 9, 17, 3, 11, 5),
        //traits section 1
        listOf(
            "Senses passive Perception 10\n",
            "Languages \u2014\n",
            "Challenge 5 (1,800 XP)\n"
        ),
        //traits section 2
        listOf(
            "Trampling Charge. If the acklay moves at least 20 feet straight toward a creature and then hits it with a bite attack on the same turn, that target must succeed on a DC 16 Strength saving throw or be knocked prone. If the target is prone, the acklay can make one claw attack against it a a bonus action.\n"
        ),
        //actions
        listOf(
            "Bite. Melee Weapon Attack: +9 to hit, reach 10 ft., one target. Hit: 24 (4d8 + 6) kinetic damage.\n",
            "Claw. Melee Weapon Attack: +9 to hit, reach 10 ft., one target. Hit: 21 (3d10 + 6) kinetic damage."
        ),
        reactions = null,
        legendaryActions = null
    ),
    Creature(
        "Acklay, Adult",
        "Huge beast, unaligned",
        "Armor Class 14 (natural armor)",
        "Hit Points 149 (13d12 + 65)",
        "Speed 40 ft.",
        //scores
        listOf(25, 10, 20, 3, 11, 5),
        //traits section 1
        listOf(
            "Senses passive Perception 10\n",
            "Languages \u2014\n",
            "Challenge 8 (3,900 XP)\n"
        ),
        //traits section 2
        listOf(
            "Trampling Charge. If the acklay moves at least 20 feet straight toward a creature and then hits it with a bite attack on the same turn, that target must succeed on a DC 19 Strength saving throw or be knocked prone. If the target is prone, the acklay can make one claw attack against it a a bonus action.\n"
        ),
        //actions
        listOf(
            "Bite. Melee Weapon Attack: +11 to hit, reach 10 ft., one target. Hit: 25 (4d8 + 7) kinetic damage.\n",
            "Claw. Melee Weapon Attack: +11 to hit, reach 10 ft., one target. Hit: 29 (4d10 + 7) kinetic damage."
        ),
        reactions = null,
        legendaryActions = null
    ),
    Creature(
        "Aiwha",
        "Huge beast, unaligned",
        "Armor Class 12",
        "Hit Points 82 (11d12 + 11)",
        "Speed 10 ft., fly 80 ft., swim 60 ft.",
        listOf(19, 14, 13, 3, 12, 5),
        listOf(
            "Skills Perception +3\n",
            "Senses blindsight 120ft., passive Perception 13\n",
            "Languages \u2014\n",
            "Challenge 3 (700 XP)\n"
        ),
        listOf(
            "Dive Attack. If the aiwha is flying and dives and dives at least 30 ft. straight toward a target and then hits it with a bite attack, the attack deals an extra 10 (3d6) damage to the target.\n",
            "Echolocation. The aiwha can't use its blindsight while deafened.\n",
            "Hold Breath. The aiwha can hold its breath for 30 minutes.\n"
        ),
        listOf(
            "Bite. Melee Weapon Attack: +6 to hit, reach 5 ft., one target. Hit: 14 (3d6 + 4) kinetic damage."
        ),
        reactions = null,
        legendaryActions = null
    ),
    Creature(
        "Anooba",
        "Medium beast, unaligned",
        "Armor Class 14 (natural armor)",
        "Hit Points 36 (5d10 + 9)",
        "Speed 50 ft.",
        listOf(16, 15, 15, 3, 12, 7),
        listOf(
            "Skills Perception +3, Stealth +4\n",
            "Senses passive Perception 13\n",
            "Challenge 1 (100 XP)\n"
        ),
        listOf(
            "Keen Hearing and Smell. The anooba has advantage on Wisdom (Perception) checks that rely on hearing or smell.\n",
            "Pack Tactics. The anooba has advantage on an attack roll against a creature if at least one of the anooba allies is within 5 ft. of the creature and the ally isn't incapacitated.\n"
        ),
        listOf(
            "Bite. Melee Weapon Attack: +5 to hit, reach 5 ft., one target. Hit: (2d6 + 3) piercing damage. If the target is a creature, it must succeed on a DC 13 Strength saving throw or be knocked prone."
        ),
        reactions = null,
        legendaryActions = null
    ),
    Creature(
        "BT-1 Assassin Droid",
        "Small droid, unaligned",
        "Armor Class 17 (armor plating)",
        "Hit Points 90 (12d8 + 36)",
        "Speed 25 ft.",
        listOf(8, 15, 17, 18, 13, 8),
        listOf(
            "Skills Stealth +6, Perception +5, Survival +5, Intimidation +3\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 15\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 9 (5,000 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Legendary Resistance (3/day). If the assassin droid fails a saving throw, it can choose to succeed instead.\n"
        ),
        listOf(
            "Multiattack. The assassin droid makes three Blaster Cannon attacks.\n",
            "Blaster Cannon. Each creature in a 10 foot cube centered on a point the droid can see within 100 ft. must make a DC 16 Dexterity saving throw. A creature takes 9 (1d10 + 4) energy damage on a failed save.\n",
            "Flamethrower (2/day). Each creature in a 30-foot cone must make a DC 16 Dexterity saving throw. A creature takes 36 (8d8) fire damage on a failed save, or half as much on a successful one. The fire ignites any flammable objects in the area that aren't being worn or carried.\n",
            "Rocket Barrage (2/day). Each creature in a 20-foot-radius sphere centered on a point the droid can see within 150 feet must make a DC 16 Dexterity saving throw. A target takes 14 (4d6) kinetic damage and 14 (4d6) fire damage on a failed save, or half as much damage on a successful one.\n"
        ),
        reactions = null,
        listOf(
            "The BT-1 Assassin Droid can take 3 legendary actions, choosing from the options below. Only one legendary action option can be used at a time and only at the end of another creature's turn. The assassin droid regains spent legendary actions at the start of its turn.\n",
            "Attack. The assassin droid makes one Blaster Cannon attack.\n",
            "Detect. The assassin droid makes a Wisdom (Perception) check.\n",
            "Rocket Boost. The assassin droid leaps up to 40 feet in any direction. This movement does not provoke opportunity attacks."
        )
    ),
    Creature(
        "HK-47 Assassin Droid",
        "Medium droid, unaligned",
        "Armor Class 18 (armor plating)",
        "Hit Points 75 (10d8 + 30)",
        "Speed 40 ft.",
        listOf(14, 20, 17, 15, 16, 15),
        listOf(
            "Skills Athletics +6, Acrobatics +9, Insight\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 17\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 8 (3,900 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Droid Synergy. Once per turn, the assassin droid can deal an extra 14 (4d6) damage to a creature it hits with a weapon attack if that creature is within 5 feet of an ally of the assassin droid that isn't incapacitated.\n",
            "Legendary Resistance (3/day). If the assassin droid fails a saving throw, it can choose to succeed instead.\n"
        ),
        listOf(
            "Multiattack. The assassin droid makes three weapon attacks.\n",
            "Blaster Rifle. Ranged Weapon Attack: +9 to hit, range 100/400 ft., one target. Hit: 9 (1d8 + 5) energy damage.\n",
            "Stock Strike. Melee Weapon Attack: +2 to hit, reach 5 ft., one target. Hit: 4 (1d4 + 2) kinetic damage.\n"
        ),
        listOf(
            "Reactive Shield (1/day). Until the start of its next turn, the assassin droid has a +5 bonus to AC. This includes the triggering attack.\n"
        ),
        listOf(
            "The HK-47 Assassin Droid can take 3 legendary actions, choosing from the options below. Only one legendary action option can be used at a time and only at the end of another creature's turn. The assassin droid regains spent legendary actions at the start of its turn.\n",
            "Attack. The assassin droid makes one weapon attack.\n",
            "Detect. The assassin droid makes a Wisdom (Perception) check.\n",
            "Rocket Boost. The assassin droid leaps up to 40 feet in any direction. This movement does not provoke opportunity attacks."
        )
    ),
    Creature(
        "IG-86 Assassin Droid",
        "Medium droid, unaligned",
        "Armor Class 16 (armor plating)",
        "Hit Points 58 (9d8 + 18)",
        "Speed 30 ft.",
        listOf(14, 17, 15, 14, 13, 7),
        listOf(
            "Skills Perception +3, Stealth +5, Survival +3\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 13\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 3 (700 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Sneak Attack (1/turn). The assassin droid deals an extra 7 (2d6) damage when it hits a target with a weapon attack and has advantage on the attack roll, or when the target is within 5 feet of an ally of the assassin droid that isn't incapacitated and the assassin doesn't have disadvantage on the roll.\n"
        ),
        listOf(
            "Sniper Rifle. Ranged Weapon Attack: +5 to hit, range 150/600 ft., one target. Hit: 9 (1d12 + 3) energy damage.\n",
            "Stock Strike. Melee Weapon Attack: +2 to hit, reach 5 ft., one target. Hit: 4 (1d4 + 2) kinetic damage."
        )
    ),
    Creature(
        "IG-88 Assassin Droid",
        "Medium droid, unaligned",
        "Armor Class 17 (armor plating)",
        "Hit Points 112 (15d8 + 45)",
        "Speed 30 ft.",
        listOf(14, 19, 16, 14, 15, 7),
        listOf(
            "Saving Throws Dex +9, Int +7, Wis +7\n",
            "Skills Insight +7, Perception +7, Stealth +9, Survival +7\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 17\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 10 (5,900 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Legendary Resistance (3/day). If IG-88 fails a saving throw, it can choose to succeed instead.\n",
            "Sneak Attack (1/turn). IG-88 deals an extra 28 (8d6) damage when it hits a target with a weapon attack and has advantage on the attack roll, or when the target is within 5 feet of an ally of IG-88 that isn't incapacitated and IG-88 doesn't have disadvantage on the roll.\n"
        ),
        listOf(
            "Multiattack. IG-88 makes three weapon attacks.\n",
            "Sniper Rifle. Ranged Weapon Attack: +9 to hit, range 150/600 ft., one target. Hit: 10 (1d12 + 4) energy damage.\n",
            "Stock Strike. Melee Weapon Attack: +7 to hit, reach 5 ft., one target. Hit: 4 (1d4 + 2) kinetic damage.\n"
        ),
        legendaryActions = listOf(
            "IG-88 can take 3 legendary actions, choosing from the options below. Only one legendary action option can be used at a time and only at the end of another creature's turn. The assassin droid regains spent legendary actions at the start of its turn.\n",
            "Attack. IG-88 makes one weapon attack.\n",
            "Detect. IG-88 makes a Wisdom (Perception) check."
        )
    ),
    Creature(
        "BB Series Astromech Droid",
        "Small droid, unaligned",
        "Armor Class 13 (armor plating)",
        "Hit Points 14 (4d6)",
        "Speed 25 ft.",
        listOf(5, 14, 11, 17, 12, 9),
        listOf(
            "Skills Perception +3, Technology +5, Piloting +5\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 30 ft., passive Perception 13\n",
            "Languages Speaks Binary, understands Galactic Basic\n",
            "Challenge 1/8 (25 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that deal ion or lightning damage.\n",
            "Keen Hearing and Sight. The astromech has advantage on Wisdom (Perception) checks that rely on hearing or sight.\n",
            "Tech Casting. The astromech is a 3rd level tech caster (tech save DC 13, +5 to hit with power attacks, 15 tech points). The astromech knows the following powers:At Will: jet of flame, mending, minor hologram, on/off*1st Level: decryption program, expeditious retreat, hologram, repair droid, target lock*2nd Level: translocate, motivator boost\n"
        ),
        listOf(
            "Shockprod. Melee Weapon Attack: +3 to hit, reach 5 ft., one target. Hit: 3 (1d4 + 1) lightning damage."
        )
    ),
    Creature(
        "C1 Series Astromech Droid",
        "Small droid, unaligned",
        "Armor Class 12 (armor plating)",
        "Hit Points 7 (2d6)",
        "Speed 25 ft.",
        listOf(6, 13, 11, 16, 12, 5),
        listOf(
            "Skills Perception +3, Technology +5, Piloting +5\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 13\n",
            "Languages Speaks Binary, understands Galactic Basic\n",
            "Challenge 1/8 (25 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that deal ion or lightning damage.\n",
            "Keen Hearing and Sight. The astromech droid has advantage on Wisdom (Perception) checks that rely on hearing or sight.\n",
            "Rocket Boost (1/day). The droid leaps up to 40 feet in any direction.\n"
        ),
        listOf(
            "Shockprod. Melee Weapon Attack: +3 to hit, reach 5 ft., one target. Hit: 3 (1d4 + 1) lightning damage."
        )
    ),
    Creature(
        "R2 Series Astromech Droid",
        "Small droid, unaligned",
        "Armor Class 12 (armor plating)",
        "Hit Points 13 (3d6 + 3)",
        "Speed 25 ft.",
        listOf(6, 13, 12, 17, 12, 7),
        listOf(
            "Skills Perception +3, Technology +5, Piloting +5\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 13\n",
            "Languages Speaks Binary, understands Galactic Basic\n",
            "Challenge 1/8 (25 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that deal ion or lightning damage.\n",
            "Keen Hearing and Sight. The astromech droid has advantage on Wisdom (Perception) checks that rely on hearing or sight.\n",
            "Rocket Boost (1/day). The droid leaps up to 40 feet in any direction.\n",
            "Tech Casting. The astromech is a 3rd level tech caster (tech save DC 13, +5 to hit with power attacks, 15 tech points). The astromech knows the following powers:At Will: light, mending, minor hologram, on/off*1st Level: decryption program, oil slick, repair droid, smoke cloud, target lock*2nd Level: lock, release\n"
        ),
        listOf(
            "Shockprod. Melee Weapon Attack: +3 to hit, reach 5 ft., one target. Hit: 3 (1d4 + 1) lightning damage."
        )
    ),
    Creature(
        "Awakened Shrub",
        "Small plant, unaligned",
        "Armor Class 9",
        "Hit Points 10 (3d6)",
        "Speed 20 ft.",
        listOf(3, 8, 11, 10, 10, 6),
        listOf(
            "Damage Vulnerabilities fire\n",
            "Damage Resistances kinetic\n",
            "Condition Immunities blinded, charmed, deafened, prone\n",
            "Senses Tremorsense 120 ft. (blind beyond this radius), passive Perception 10\n",
            "Challenge 0 (10 XP)\n"
        ),
        listOf(
            "False Appearance. While the shrub remains motionless, it is indistinguishable from a normal shrub.\n"
        ),
        listOf(
            "Rake. Melee Weapon Attack: +1 to hit, reach 5 ft., one target. Hit: 1 (1d4 - 1) kinetic damage."
        )
    ),
    Creature(
        "Awakened Tree",
        "Large plant, unaligned",
        "Armor Class 13 (natural armor)",
        "Hit Points 73 (7d10 + 28)",
        "Speed 20 ft.",
        listOf(21, 6, 18, 10, 10, 7),
        listOf(
            "Damage Vulnerabilities fire\n",
            "Damage Resistances kinetic\n",
            "Condition Immunities blinded, charmed, deafened, prone\n",
            "Senses Tremorsense 120 ft. (blind beyond this radius), passive Perception 10\n",
            "Challenge 2 (450 XP)\n"
        ),
        listOf(
            "False Appearance. While the tree remains motionless, it is indistinguishable from a normal tree.\n"
        ),
        listOf(
            "Slam. Melee Weapon Attack: +7 to hit, reach 10 ft., one target. Hit: 23 (4d8 + 5) kinetic damage."
        )
    ),
    Creature(
        "Awakened Carnivorous Plant",
        "Small plant, unaligned",
        "Armor Class 8",
        "Hit Points 24 (4d6 + 12)",
        "Speed 10 ft., climb 10 ft.",
        listOf(12, 11, 16, 10, 10, 2),
        listOf(
            "Damage Vulnerabilities fire\n",
            "Damage Resistances acid\n",
            "Condition Immunities blinded, charmed, deafened, prone\n",
            "Senses blindsight 60 ft. (blind beyond this radius), passive Perception 10\n",
            "Challenge 1 (100 XP)\n"
        ),
        listOf(
            "Corrode Metal. Any unenhanced weapon made of metal that hits the plant corrodes. After dealing damage, the weapon takes a permanent and cumulative -1 penalty to damage rolls. If its penalty drops to -5, the weapon is destroyed. The plant can eat through 2-inch-thick, unenhanced metal in 1 round.\n",
            "False Appearance. While the plant remains motionless, it is indistinguishable from a normal plant.\n"
        ),
        listOf(
            "Bite. Melee Weapon Attack: +2 to hit, reach 5 ft., one target. Hit: 4 (1d6 + 1) kinetic damage plus 7 (2d6) acid damage."
        )
    ),
    Creature(
        "Awakened Vines",
        "Large plant, unaligned",
        "Armor Class 12",
        "Hit Points 84 (12d10 + 24)",
        "Speed 20 ft., climb 15 ft.",
        listOf(18, 14, 12, 10, 7, 4),
        listOf(
            "Damage Vulnerabilities fire\n",
            "Condition Immunities blinded, charmed, deafened, prone\n",
            "Senses blindsight 60 ft. (blind beyond this radius), passive Perception 8\n",
            "Challenge 2 (450 XP)\n"
        ),
        listOf(
            "Constrict. Creatures grappled by the vines take 4 (1d8) kinetic damage at the start of every round.\n",
            "False Appearance. While the vines remain motionless, they are indistinguishable from normal vines.\n"
        ),
        listOf(
            "Vines. Melee Weapon Attack: +6 to hit, reach 15 ft., one target. Hit: 22 (4d8 + 4) kinetic damage, and the target is Grappled (escape DC 14). Until this grapple ends, the creature is restrained, and the vines can't constrict another target."
        )
    ),
    Creature(
        "Bantha, Adolescent",
        "Huge beast, unaligned",
        "Armor Class 12 (natural armor)",
        "Hit Points 76 (8d10 + 32)",
        "Speed 40 ft.",
        listOf(22, 9, 17, 3, 11, 6),
        listOf(
            "Senses passive Perception 10\n",
            "Languages \u2014\n",
            "Challenge 4 (1,100 XP)\n"
        ),
        listOf(
            "Trampling Charge. If the bantha moves at least 20 feet straight toward a creature and then hits it with a ram attack on the same turn, that target must succeed on a DC 14 Strength saving throw or be knocked prone. If the target is prone, the bantha can make one stomp attack against it as a bonus action.\n"
        ),
        listOf(
            "Ram. Melee Weapon Attack: +8 to hit, reach 10 ft., one target. Hit: 18 (3d8 + 6) kinetic damage.\n",
            "Stomp. Melee Weapon Attack: +8 to hit, reach 5 ft., one target. Hit: 21 (3d10 + 6) kinetic damage."
        )
    ),
    Creature(
        "Bantha, Adult",
        "Huge beast, unaligned",
        "Armor Class 13 (natural armor)",
        "Hit Points 126 (11d12 + 55)",
        "Speed 40 ft.",
        listOf(24, 9, 21, 3, 11, 6),
        listOf(
            "Senses passive Perception 10\n",
            "Languages \u2014\n",
            "Challenge 6 (2,300 XP)\n"
        ),
        listOf(
            "Trampling Charge. If the bantha moves at least 20 feet straight toward a creature and then hits it with a ram attack on the same turn, that target must succeed on a DC 18 Strength saving throw or be knocked prone. If the target is prone, the bantha can make one stomp attack against it as a bonus action.\n"
        ),
        listOf(
            "Ram. Melee Weapon Attack: +10 to hit, reach 10 ft., one target. Hit: 25 (4d8 + 7) kinetic damage.\n",
            "Stomp. Melee Weapon Attack: +10 to hit, reach 5 ft., one target. Hit: 29 (4d10 + 7) kinetic damage."
        )
    ),
    Creature(
        "B1 Battle Droid",
        "Medium droid, unaligned",
        "Armor Class 14 (armor plating)",
        "Hit Points 7 (2d8 - 2)",
        "Speed 30 ft.",
        listOf(9, 14, 9, 13, 10, 7),
        listOf(
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 10\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 1/8 (25 XP)\n"
        ),
        listOf(
            "Battle Droid Swarm. When an ally of the battle droid hits a hostile creature that it can see with a weapon attack, the battle droid can use its reaction to make one weapon attack against that creature.\n",
            "Circuitry. The droid has disadvantage on saving throws against effects that deal ion or lightning damage.\n"
        ),
        listOf(
            "Blaster Rifle. Ranged Weapon Attack: +4 to hit, range 100/400 ft., one target. Hit: 6 (1d8 + 2) energy damage.\n",
            "Stock Strike. Melee Weapon Attack: +1 to hit, reach 5 ft., one target. Hit: 1 (1d4 - 1) kinetic damage."
        )
    ),
    Creature(
        "B1-X Battle Droid",
        "Medium droid, unaligned",
        "Armor Class 15 (armor plating)",
        "Hit Points 16 (3d8 + 3)",
        "Speed 30 ft.",
        listOf(11, 16, 12, 14, 10, 9),
        listOf(
            "Skills Perception +2\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 12\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 1/2 (100 XP)\n"
        ),
        listOf(
            "Battle Droid Swarm. When an ally of the battle droid hits a hostile creature that it can see with a weapon attack, the battle droid can use its reaction to make one weapon attack against that creature.\n",
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n"
        ),
        listOf(
            "Blaster Pistol. Ranged Weapon Attack: +5 to hit, range 40/160 ft., one target. Hit: 6 (1d6 + 3) energy damage.\n",
            "Stock Strike. Melee Weapon Attack: +2 to hit, reach 5 ft., one target. Hit: 2 (1d4) kinetic damage.\n"
        ),
        reactions = listOf(
            "Redirect Attack. When a creature the battle droid can see targets it with an attack, the battle droid chooses another battle droid within 5 feet of it. The two battle droids swap places, and the chosen battle droid becomes the target instead."
        )
    ),
    Creature(
        "B2 Super Battle Droid",
        "Medium droid, unaligned",
        "Armor Class 16 (armor plating)",
        "Hit Points 26 (4d8 + 8)",
        "Speed 30 ft.",
        listOf(13, 16, 14, 14, 12, 11),
        listOf(
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 11\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 1 (200 XP)\n"
        ),
        listOf("Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n"),
        listOf(
            "Multiattack. The super battle droid makes two weapon attacks with its strike or wrist blaster.\n",
            "Strike. Melee Weapon Attack: +3 to hit, reach 5 ft., one target. Hit: 3 (1d4 + 1) kinetic damage.\n",
            "Wrist Blaster. Ranged Weapon Attack: +5 to hit, range 60/240 ft., one target. Hit: 6 (1d6 + 3) energy damage.\n",
            "Wrist Blaster Volley (Recharge 6). The super battle droid sprays a 10-foot-cube area within normal range with shots. Each creature in the area must make a DC 13 Dexterity saving throw, taking normal weapon damage on a failed save."
        )
    ),
    Creature(
        "B2-HA Super Battle Droid",
        "Medium droid, unaligned",
        "Armor Class 17 (armor plating)",
        "Hit Points 84 (14d8 + 28)",
        "Speed 30 ft.",
        listOf(15, 16, 14, 14, 15, 11),
        listOf(
            "Skills Perception +4\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 14\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 4 (1,100 XP)\n"
        ),
        listOf("Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n"),
        listOf(
            "Multiattack. The super battle droid makes two weapon attacks with its strike or wrist blaster.\n",
            "Strike. Melee Weapon Attack: +4 to hit, reach 5 ft., one target. Hit: 4 (1d4 + 2) kinetic damage.\n",
            "Wrist Blaster. Ranged Weapon Attack: +5 to hit, range 60/240 ft., one target. Hit: 6 (1d6 + 3) energy damage.\n",
            "Wrist Blaster Volley (Recharge 5-6). The super battle droid sprays a 10-foot-cube area within normal range with shots. Each creature in the area must make a DC 13 Dexterity saving throw, taking normal weapon damage on a failed save.\n",
            "Rocket Launcher (1/day). Each creature in a 20-foot-radius sphere centered on a point the battle droid can see within 150 feet must make a DC 13 Dexterity saving throw. A target takes 8d6 fire damage on a failed save, or half as much damage on a successful one."
        )
    ),
    Creature(
        "BX Commando Droid",
        "Medium droid, unaligned",
        "Armor Class 15 (armor plating)",
        "Hit Points 49 (9d8 + 9)",
        "Speed 30 ft.",
        listOf(12, 17, 13, 15, 13, 7),
        listOf(
            "Skills Perception +3, Stealth +5\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 12\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 2 (450 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Droid Synergy. Once per turn, the commando droid can deal an extra 3 (1d6) damage to a creature it hits with a weapon attack if that creature is within 5 feet of an ally of the commando droid that isn't incapacitated.\n"
        ),
        listOf(
            "Blaster Carbine. Ranged Weapon Attack: +5 to hit, range 60/240 ft., one target. Hit: 6 (1d6 + 3) energy damage.\n",
            "Techblade. Melee Weapon Attack: +5 to hit, reach 5 ft., one target. Hit: 6 (1d6 + 3) kinetic damage.\n",
            "Stealth Field Generator. The droid becomes invisible. Anything the droid is carrying is invisible as long as it is on the droid. The droid may end the invisibility as a free action. The invisibility ends if the droid attacks."
        )
    ),
    Creature(
        "BX-5C Commando Droid",
        "Medium droid, unaligned",
        "Armor Class 16 (armor plating)",
        "Hit Points 96 (15d8 + 30)",
        "Speed 30 ft.",
        listOf(13, 19, 14, 15, 13, 7),
        listOf(
            "Saving Throws Dex +7\n",
            "Skills Perception +4, Stealth +7\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 12\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 5 (1,800 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Droid Synergy. Once per turn, the commando droid can deal an extra 7 (2d6) damage to a creature it hits with a weapon attack if that creature is within 5 feet of an ally of the commando droid that isn't incapacitated.\n"
        ),
        listOf(
            "Multiattack. The supercommando droid makes two weapon attacks.\n",
            "Blaster Carbine. Ranged Weapon Attack: +7 to hit, range 60/240 ft., one target. Hit: 6 (1d6 + 3) energy damage.\n",
            "Techblade. Melee Weapon Attack: +7 to hit, reach 5 ft., one target. Hit: 6 (1d6 + 3) kinetic damage.\n",
            "Stealth Field Generator. The droid becomes invisible. Anything the droid is carrying is invisible as long as it is on the droid. The droid may end the invisibility as a free action. The invisibility ends if the droid attacks."
        )
    ),
    Creature(
        "Destroyer Droid",
        "Large droid, unaligned",
        "Armor Class 17 (armor plating)",
        "Hit Points 142 (15d10 + 60)",
        "Speed 15 ft. (attack form) or 60 ft. (ball form)",
        listOf(14, 18, 18, 13, 15, 7),
        listOf(
            "Skills Perception +5\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses darkvision 60 ft., passive Perception 15\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 7 (2,900 XP)\n"
        ),
        listOf(
            "Circuitry. The droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Ball Form. As a bonus action, the destroyer droid can transform into or out of ball form. While in ball form, the destroyer droid has increased speed, but it can not attack.\n"
            ),
        listOf(
            "Multiattack. The destroyer droid makes two weapon attacks.\n",
            "Blaster Cannons. Ranged Weapon Attack: +7 to hit, range 100/400 ft., one target. Hit: 11 (2d6 + 4) energy damage.\n",
            "Blaster Volley. The destroyer droid sprays a 10-foot-cube area within normal range of its blaster cannons with shots. Each creature in the area must make a DC 15 Dexterity saving throw, taking normal weapon damage on a failed save.\n"
        ),
        reactions = listOf(
            "Deflector Shield. The destroyer droid adds 3 to its AC against one attack that would hit it. To do so, the destroyer droid must see the attacker."
        )
    ),
    Creature(
        "DSD1 Dwarf Spider Droid",
        "Large droid, unaligned",
        "Armor Class 16 (armor plating)",
        "Hit Points 32 (5d10 + 5)",
        "Speed 40 ft., climb 40 ft.",
        listOf(15, 16, 12, 10, 10, 9),
        listOf(
            "Skills Perception +4\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic\n",
            "Condition Immunities poison, disease\n",
            "Senses truesight 120 ft., passive Perception 14\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 2 (450 XP)\n"
        ),
        listOf(
            "Circuitry. The spider droid has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Spider Climb. The spider droid can climb difficult surfaces, including upside down on ceilings, without needing to make an ability check.\n"
        ),
        listOf(
            "Blaster Cannon. Ranged Weapon Attack: +5 to hit, range 100/400 ft., one target. Hit: 9 (1d12 + 3) energy damage.\n",
            "Burst. The spider droid sprays a 10-foot-cube area within normal range with shots. Each creature in the area must make a DC 13 Dexterity saving throw, taking 9 (1d12 + 3) energy damage on a failed save, or half as much on a successful one.\n",
            "Claw. Melee Weapon Attack: +4 to hit, reach 5 ft., one target. Hit: 5 (1d5 + 2) kinetic damage.\n",
            "Self-Destruct. When the spider droid is reduced to half of its hit point maximum and is within 20 feet of a hostile creature, it attempts to self-destruct. Each creature within 20 feet of it must make a DC 13 Dexterity saving throw, taking 6d6 fire damage on a failed save, or half as much on a successful one."
        )
    ),
    Creature(
        "MagnaGuard Sentinel",
        "Medium droid, unaligned",
        "Armor Class 16 (armor plating)",
        "Hit Points 58 (9d8 + 18)",
        "Speed 30 ft., climb 30 ft.",
        listOf(13, 19, 14, 15, 13, 9),
        listOf(
            "Saving Throws Dex +6\n",
            "Skills Perception +3, Stealth +6, Intimidation +1\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances necrotic, poison, psychic; energy from melee weapon attacks\n",
            "Condition Immunities poison, disease\n",
            "Senses truesight 120 ft., passive Perception 13\n",
            "Languages Binary, Galactic Basic\n",
            "Challenge 4 (1,100 XP)\n"
        ),
        listOf(
            "Circuitry. The MagnaGuard has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Droid Synergy. Once per turn, the MagnaGuard can deal an extra 3 (1d6) damage to a creature it hits with a weapon attack if that creature is within 5 feet of a MagnaGuard ally that isn't incapacitated.\n",
            "Leap. The MagnaGuard can expend all its movement on its turn to jump up to its current movement speed in any direction.\n",
            "Mag-lock Limbs. The MagnaGuard can climb metal surfaces, including upside down on ceilings, without needing to make an ability check.\n"
        ),
        listOf(
            "Multiattack. The MagnaGuard makes two electrostaff attacks.\n",
            "Electrostaff. Melee Weapon Attack: +6 to hit, reach 5 ft., one target. Hit: 9 (2d4 + 4) kinetic damage.\n",
            "Blaster Pistol. Ranged Weapon Attack: +6 to hit, range 40/160 ft., one target. Hit: 7 (1d6 + 4) energy damage.\n"
        ),
        reactions = listOf(
            "Parry. The droid adds 3 to its AC against one melee attack that would hit it. To do so, the droid must see the attacker and be wielding a melee weapon."
        )
    ),
    Creature(
        "Battle Hydra",
        "Huge aberration, neutral dark",
        "Armor Class 14 (natural armor)",
        "Hit Points 126 (11d12 + 55)",
        "Speed 30 ft., fly 60 ft.",
        listOf(20, 12, 20, 2, 12, 7),
        listOf(
            "Skills Perception +7\n",
            "Damage Resistances energy; ion and kinetic from unenhanced weapons\n",
            "Senses darkvision 60 ft., passive Perception 17\n",
            "Languages \u2014\n",
            "Challenge 9 (5,000 XP)\n"
        ),
        listOf(
            "Reactive Heads. The hydra gets an extra reaction that can be used only for opportunity attacks.\n",
            "Two Heads. The hydra has advantage on Wisdom (Perception) checks and on saving throws against being blinded, charmed, deafened, frightened, stunned, and knocked unconscious.\n",
            "Wakeful. When one of the hydra's heads is asleep, its other head is awake.\n"
        ),
        listOf(
            "Multiattack. The hydra can make three attacks: two with its bite and one with its claws.\n",
            "Bite. Melee Weapon Attack: +8 to hit, reach 10 ft., one target. Hit: 10 (1d10 + 5) kinetic damage.\n",
            "Claws. Melee Weapon Attack: +8 to hit, reach 5 ft., one target. Hit: 12 (2d6 + 5) kinetic damage.\n",
            "Stinger. Melee Weapon Attack: +8 to hit, reach 10 ft., one target. Hit: 9 (1d8 + 5) kinetic damage. The target must make a DC 15 Constitution saving throw, taking 24 (7d6) poison damage on a failed save, or half as much damage on a successful one."
        )
    ),
    Creature(
        "Blurrg",
        "Large beast, unaligned",
        "Armor Class 11 (natural armor)",
        "Hit Points 67 (9d10 + 18)",
        "Speed 30 ft.",
        listOf(20, 6, 15, 1, 7, 5),
        listOf(
            "Skills Athletics +6\n",
            "Senses passive Perception 8\n",
            "Languages \u2014\n",
            "Challenge 2 (450 XP)\n"
        ),
        listOf(
            "Siege Monster. The blurrg deals double damage to objects and structures.\n",
            "Sure-Footed. The blurrg has advantage on Strength and Dexterity saving throws made against effects that would shove it or knock it prone.\n",
            "Beast of Burden. The blurrg is considered to be a Huge animal for the purpose of determining its carrying capacity.\n"
        ),
        listOf(
            "Bite. Melee Weapon Attack: +7 to hit, reach 5 ft., one target. Hit: 11 (1d12 + 5) kinetic damage."
        )
    ),
    Creature(
        "Bogwing, Lesser",
        "Tiny beast, unaligned",
        "Armor Class 12",
        "Hit Points 4 (3d4 - 3)",
        "Speed 10 ft., fly 30 ft.",
        listOf(5, 14, 9, 3, 13, 5),
        listOf(
            "Skills Perception +3\n",
            "Senses passive Perception 13\n",
            "Languages \u2014\n",
            "Challenge 1/8 (25 XP)\n"
        ),
        listOf(
            "Beast of Burden. The bogwing is considered to be a Medium animal for the purposes of determining its carrying capacity.\n"
        ),
        listOf(
            "Claws. Melee Weapon Attack: +4 to hit, reach 5 ft., one target. Hit: 4 (1d4 + 2) kinetic damage."
        )
    ),
    Creature(
        "Bogwing, Greater",
        "Small beast, unaligned",
        "Armor Class 13",
        "Hit Points 14 (4d6)",
        "Speed 10 ft., fly 40 ft.",
        listOf(6, 16, 11, 3, 13, 5),
        listOf(
            "Skills Perception +3\n",
            "Senses passive Perception 13\n",
            "Languages \u2014\n",
            "Challenge 1 (200 XP)\n"
        ),
        listOf(
            "Beast of Burden. The bogwing is considered to be a Large animal for the purposes of determining its carrying capacity.\n"
        ),
        listOf(
            "Multiattack. The bogwing can make two claw attacks.\n",
            "Claws. Melee Weapon Attack: +5 to hit, reach 5 ft., one target. Hit: 6 (1d6 + 3) kinetic damage."
        )
    ),
    Creature(
        "B'omarr Brain Walker",
        "Medium construct, any dark",
        "Armor Class 14 (armor plating)",
        "Hit Points 54 (8d8 + 16)",
        "Speed 30 ft.",
        listOf(1, 11, 15, 16, 12, 16),
        listOf(
            "Saving Throws Int +6, Wis +4, Cha +6\n",
            "Skills Lore +6, Deception +6, Perception +4, Persuasion +6\n",
            "Damage Vulnerabilities ion\n",
            "Damage Resistances psychic\n",
            "Condition Immunities charmed, frightened, paralyzed\n",
            "Senses blindsight 60 ft., passive Perception 14\n",
            "Languages Galactic Basic, Huttese\n",
            "Challenge 3 (700 XP)\n"
        ),
        listOf(
            "Circuitry. The B'omarr Brain Walker has disadvantage on saving throws against effects that would deal ion or lightning damage.\n",
            "Innate Forcecasting. The B'omarr Brain Walker's forcecasting ability is Charisma (Force save DC 13). The B'omarr Brain Walker can innately cast the following force powers:At will: affect mind, force push/pull*1/day each: greater feedback, mind trap\n"
        ),
        listOf(
            "Multiattack. The B'omarr Brain Walker makes two Mind Thrust attacks.\n",
            "Mind Thrust. One creature within 60 feet of the B'omarr Brain Walker must make a DC 13 Wisdom saving throw, taking 13 (4d6) psychic damage on a failed save, or half as much on a successful one."
        )
    ),
    Creature(
        "B'omarr Monk Initiate",
        "Medium humanoid, any neutral",
        "Armor Class 12 (combat suit)",
        "Hit Points 9 (2d8)",
        "Speed 30 ft.",
        listOf(8, 8, 8, 10, 14, 9),
        listOf(
            "Skills Medicine +4, Deception +2\n",
            "Senses passive Perception 10\n",
            "Languages Galactic Basic, Huttese\n",
            "Challenge 1/8 (25 XP)\n"
        ),
        listOf(
            "Unearthly Focus. The initiate has advantage on saving throws against being charmed or frightened.\n"
        ),
        listOf(
            "Hold-Out Blaster. Ranged Weapon Attack: +2 to hit, range 30/120 ft., one target. Hit: 4 (1d4 + 2) energy damage."
        )
    ),
    Creature(
        "Bor Gullet",
        "Large beast, unaligned",
        "Armor Class 12 (natural armor)",
        "Hit Points 180 (19d10 + 76)",
        "Speed 10 ft., swim 10 ft.",
        listOf(16, 8, 18, 20, 18, 17),
        listOf(
            "Saving Throws Con +9, Int +10, Wis +9\n",
            "Skills Lore +10, Perception +9, Insight +14, Deception +8, Persuasion +8\n",
            "Damage Resistances psychic\n",
            "Damage Immunities poison\n",
            "Condition Immunities poisoned\n",
            "Senses blindsight 120 ft., passive Perception 19\n",
            "Languages understands Galactic Basic but can't speak, telepathy 120 ft.\n",
            "Challenge 13 (10,000 XP)\n"
        ),
        listOf(
            "Creature Sense. The bor gullet is aware of the presence of creatures within 1 mile of it that have an Intelligence score of 4 or higher. It knows the distance and direction to each such creature, as well as each such creature's Intelligence score, but it can't sense anything else about it. Droids and creatures protected by a mind blank power or other similar power cannot be perceived in this manner.\n",
            "Fortified Mind. The bor gullet has advantage on saving throws against being charmed and frightened, and it can't be put to sleep.\n",
            "Legendary Resistance (3/day). If the bor gullet fails a saving throw, it can choose to succeed instead.\n",
            "Psychic Link. The bor gullet can establish a psychic link with any creature that it successfully grapples with its tentacles. Until the psychic link ends, the bor gullet can perceive everything the target senses. The target is aware that its mind is linked, and the bor gullet can terminate the link at any time (no action required). The target can use an action on its turn to attempt to break the psychic link, doing so with a successful DC 18 Intelligence saving throw. On a successful save, the target takes 10 (3d6) psychic damage. The psychic link also ends if the target and the bor gullet are more than 1 mile apart, with no consequences to the target. The bor gullet can form psychic links with up to ten creatures at a time.\n"
        ),
        listOf(
            "Multiattack. The bor gullet makes three tentacle attacks.\n",
            "Tentacle. Melee Weapon Attack: +8 to hit, reach 20 ft., one target. Hit: 6 (1d6 + 3) kinetic damage, and the target is grappled (escape DC 16) if it is Large or smaller. Until the grapple ends, the target takes 9 (1d8 + 5) psychic damage at the start of each of its turns, and bor gullet can't use this tentacle on another target. The bor gullet has six tentacles.\n",
            "Mind Blast (Recharge 5-6). The bor gullet emits psychic energy. Creatures of the bor gullet's choice within 60 feet of it must succeed on a DC 18 Intelligence saving throw or take 32 (5d10 + 5) psychic damage and be stunned for 1 minute. A target can repeat the saving throw at the end of each of its turns, ending the effect on itself on a success.\n",
            "Sense Thoughts. The bor gullet targets a creature with which it has a psychic link. The bor gullet gains insight into the target's emotional state and any thoughts that loom large in its mind (including worries, loves, and hates). The bor gullet can also make a Charisma (Deception) check with advantage to deceive the target's mind into thinking it believes one idea or feels a particular emotion. The target contests this attempt with a Wisdom (Insight) check. If the bor gullet succeeds, the mind believes the deception for 1 hour or until evidence of the lie is presented to the target.\n"
        ),
        legendaryActions = listOf(
            "The bor gullet can take 3 legendary actions, choosing from the options below. It can take only one legendary action at a time and only at the end of another creature's turn. The bor gullet regains spent legendary actions at the start of its turn.\n",
            "Tentacle. The bor gullet makes a tentacle attack.\n",
            "Break Concentration. The bor gullet targets a creature within 120 feet of it with which it has a psychic link. The bor gullet breaks the creature's concentration on a power it is using. The creature also takes 1d4 psychic damage per level of the power.\n",
            "Psychic Pulse. The bor gullet targets a creature within 120 feet of it with which it has a psychic link. Enemies of the bor gullet within 10 feet of that creature take 10 (3d6) psychic damage.\n",
            "Sever Psychic Link. The bor gullet targets a creature within 120 feet of it with which it has a psychic link. The bor gullet ends the link, causing the creature to have disadvantage on all ability checks, attack rolls, and saving throws until the end of the creature's next turn."
        )
    ),
    Creature(
        "Brain Worm",
        "Tiny beast, lawful dark",
        "Armor Class 12",
        "Hit Points 21 (6d4 + 6)",
        "Speed 30 ft.",
        listOf(6, 14, 13, 12, 11, 10),
        listOf(
            "Skills Perception +2, Stealth +4\n",
            "Damage Vulnerabilities cold\n",
            "Damage Resistances energy and kinetic from unenhanced weapons\n",
            "Condition Immunities blinded\n",
            "Senses blindsight 60 ft. (blind beyond this radius), passive Perception 12\n",
            "Languages Geonosian (understands but can't speak)\n",
            "Challenge 2 (450 XP)\n"
        ),
        listOf(
            "Detect Sentience. The brain worm can sense the presence and location of any creature within 300 feet of it that has an Intelligence of 3 or higher, regardless of interposing barriers, unless the creature is protected by a mind blank force power.\n"
        ),
        listOf(
            "Multiattack. The brain worm makes one Enter Orifice attack and uses Consume Mind.\n",
            "Enter Orifice. Melee Weapon Attack: +4 to hit, reach 5 ft., one target. Hit: 7 (2d4 + 2) kinetic damage.\n",
            "Consume Mind. The brain worm targets one creature it can see within 10 feet of it that has a brain. The target must succeed on a DC 12 Intelligence saving throw against this magic or take 11 (2d10) psychic damage. Also on a failure, roll 3d6: if the total equals or exceeds the target's Intelligence score, that score is reduced to 0. The target is stunned until it regains at least one point of Intelligence.\n",
            "Body Thief. The brain worm initiates an Intelligence contest with an incapacitated humanoid with 5 feet of it. If it wins the contest, th brain worm burrows into the target's brain and takes control of the target's body. While inside a creature, the brain worm has total cover against attacks and other effects originating outside its host. The brain worm retains its Intelligence, Wisdom, and Charisma scores, as well as its understanding of Geonosian, its telepathy, and its traits. It otherwise adopts the target's statistics. It knows everything the creature knew, including force powers, tech powers, and languages.newlineIf the host body takes 20 points of cold damage in a single round, the brain worm must make a DC 15 Constitution saving throw using its own Constitution score, not the host's. On a failure, the brain worm leaves the body, slithering out to a space within 5 ft. of the host. After exiting the host, it is paralyzed until the start of its next turn.newlineIf the host body dies, the brain worm must leave it. The brain worm is also forced out if the target regains its devoured brain by means of a master heal force power. By spending 5 feet of its movement, the brain worm can voluntarily leave the body, slithering out to the nearest unoccupied space within 5 feet of it."
        )
    ),
    Creature(
        "Cantina Brawl",
        "Huge swarm of Medium humanoids, unaligned",
        "Armor Class 12 (combat suit)",
        "Hit Points 67 (9d12 + 9)",
        "Speed 30 ft.",
        listOf(14, 12, 13, 11, 10, 9),
        listOf(
            "Damage Resistances kinetic\n",
            "Condition Immunities charmed, frightened, paralyzed, petrified, prone, restrained, stunned\n",
            "Senses passive Perception 10\n",
            "Languages Galactic Basic and any one other language\n",
            "Challenge 3 (700 XP)\n"
        ),
        listOf(
            "Liquid Courage (Recharge 5-6). As a bonus action, the cantina brawl imbibes nearby alcohol to gain access to a hidden reservoir of audacity and grit. The cantina brawl gains 7 (2d6) temporary hp for 1 minute.\n",
            "Swarm. The cantina brawl can occupy another creature's space and vice versa, and the cantina brawl can move through any opening large enough for a medium humanoid. Except for Liquid Courage, the cantina brawl can't regain hp or gain temporary hp.\n"
        ),
        listOf(
            "Multiattack. The cantina brawl makes two melee attacks or two darts attacks.\n",
            "Stool. Melee Weapon Attack: +4 to hit, range 0 ft., one target in the cantina brawl's space. Hit: 14 (4d4 + 4) kinetic damage, or 7 (2d4 + 2) if the cantina brawl has half its hit points or fewer.\n",
            "Broken Bottles. Melee Weapon Attack: +3 to hit, range 0 ft., one target in the cantina brawl's space. Hit: 17 (6d4 + 2) kinetic damage, or 8 (3d4 + 1) if the cantina brawl has half its hit points or fewer.\n",
            "Darts. Ranged Weapon Attack: +3 to hit, range 20/40 ft., one target. Hit: 10 (4d4) kinetic damage, or 5 (2d4) if the cantina brawl has half its hit points or fewer."
        )
    ),
    //page 34
).sortedBy {
    it.name
}

val creatureItemList : List<CreatureItem> = makeCreatureItems(creatures)
