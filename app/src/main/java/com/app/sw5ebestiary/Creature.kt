package com.app.sw5ebestiary

import kotlin.text.Typography.bullet

data class Creature(val name : String, val classification : String, val ac : String, val hp : String, val speed : String, val scores : List<Int>, val traitsSectionOne : List<String>?, val traitsSectionTwo : List<String>?, val actions : List<String>, val legendaryActions : List<String>?)

var currentCreature : String = ""
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
            "Innate Forcecasting. Abeloth's forcecasting ability is Charisma (force save DC 23). Abeloth can innately cast the following powers:\nAt will: coerce mind, sense force\n3/day each: fear, force suppression, telekinesis\n1/day each: telekinetic wave, force lightning cone",
            "Legendary Resistance (3/day). If Abeloth fails a saving throw, she can choose to succeed instead.\n",
            "Force Resistance. Abeloth has advantage on saving throws against force powers and effects.\n",
            "Enhanced Being. Abeloth's weapon attacks are enhanced.\n",
            "One with the Force. Abeloth has advantage on saving throws against being blinded, deafened, stunned, or knocked unconscious.\n"
        ),
        // actions
        listOf(
            "Multiattack. Abeloth makes two tentacle attacks.\n",
            "Tentacles. Melee Weapon Attack: +17 to hit, reach 5ft., one target. Hit: 28 (3d12 + 9) kinetic damage. If the target is a creature, it must succeed on a DC 23 Constitution saving throw or its hit point maximum is reduced by an amount equal to the damage taken. This reduction lasts until the target finishes a long rest. If the target's hit point maximum is reduced to 0, they die and their essence is absorbed into Abeloth.\n",
            "Will. Abeloth turns her will upon one creature she can see within 120 feet. That target must make a DC 23 Wisdom saving throw. Unless the target is incapacitated, it can avert it's attention to automatically succeed on the save. If the target does so, it can't see Abeloth until the start of its next turn. If the target again focuses on Abeloth in the meantime, it must immediately make the save. If the target fails the save, the target suffers one of the following effects of Abeloth's choice or at random:\n1. Beguiling Will. The target i stunned until the start of Abeloth's next turn.\n2. Hypnotic Will. The target is charmed by Abeloth until the start of Abeloth's next turn. Abeloth chooses how the charmed target uses its actions, reactions, and movement. Because this effect requires Abeloth's strong effort, she can't use her Maddening Will legendary action until the start of her next turn.\n3. Insane Will. The target suffers the effect of the confusion power without making a saving throw. The effect lasts until the start of Abeloth's next turn. Abeloth doesn't need to concentrate on the power.\n"
        ),
        // legendary actions
        listOf(
            "Abeloth can take 3 Legendary Actions, choosing from the options below. One legendary action can be used at a time, and only at the end of another's turn. Spent Legendary Actions are regained at the start of each turn.\n",
            "Draining Lightning. Ranged Power Attack: +17 to hit, range 15 ft., one target. Hit: 20 (2d10 + 9) lightning damage plus 11 (2d10) necrotic damage.\n",
            "Maddening Will. Abeloth uses her Will action, and must choose either the Beguiling Will or the Insane Will effect."
        )
    )
)