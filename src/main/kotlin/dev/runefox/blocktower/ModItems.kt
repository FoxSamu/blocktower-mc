@file:Suppress("unused")

package dev.runefox.blocktower

import dev.runefox.blocktower.util.*
import net.minecraft.core.registries.BuiltInRegistries

import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

object ModItems {
    val BEER = roleIcon("beer", "Jug of Beer")
    val BELL_JAR = roleIcon("bell_jar", "Bell Jar")
    val BIG_SHOVEL = roleIcon("big_shovel", "Big Shovel")
    val CHEF_HAT = roleIcon("chef_hat", "Chef's Hat")
    val CLOTHESLINE = roleIcon("clothesline", "Clothesline")
    val COURTHOUSE = roleIcon("courthouse", "Courthouse")
    val CROSS = roleIcon("cross", "Cross")
    val EMPATHY = roleIcon("empathy", "Empathy")
    val GAS_LAMP = roleIcon("gas_lamp", "Gas Lamp")
    val GLASS_ORB = roleIcon("glass_orb", "Glass Orb")
    val IMP_TRIDENT = roleIcon("imp_trident", "Imp's Trident")
    val KISS = roleIcon("kiss", "Kiss")
    val MAGNIFYING_GLASS = roleIcon("magnifying_glass", "Magnifying Glass")
    val MASK = roleIcon("mask", "Mask")
    val POISON_BOTTLE = roleIcon("poison_bottle", "Poison Bottle")
    val RAVEN = roleIcon("raven", "Raven")
    val RING = roleIcon("ring", "Ring")
    val SAINT_WINGS = roleIcon("saint_wings", "Saint's Wings")
    val TOP_HAT = roleIcon("top_hat", "Top Hat")

    private fun roleIcon(name: String, defaultTranslation: String): Item {
        val key = blocktower(name) of Registries.ITEM

        val item = item(::Item) {
            setId(key)
        }

        BuiltInRegistries.ITEM.register(key, item)

        Datagen.ROLE_ITEMS.add(item)
        Datagen.TRANSLATIONS[item.descriptionId] = defaultTranslation

        return item
    }
}
