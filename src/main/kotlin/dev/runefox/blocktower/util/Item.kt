package dev.runefox.blocktower.util

import net.minecraft.world.item.Item

inline fun itemProperties(block: Item.Properties.() -> Unit): Item.Properties {
    return Item.Properties().apply(block)
}

inline fun <I : Item> item(ctor: (Item.Properties) -> I, block: Item.Properties.() -> Unit): I {
    return ctor(itemProperties(block))
}
