package dev.runefox.blocktower.common.util

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

inline fun itemProperties(block: Item.Properties.() -> Unit): Item.Properties {
    return Item.Properties().apply(block)
}

inline fun <I : Item> item(ctor: (Item.Properties) -> I, block: Item.Properties.() -> Unit): I {
    return ctor(itemProperties(block))
}

operator fun Item.get(size: Int): ItemStack {
    return ItemStack(this, size)
}
