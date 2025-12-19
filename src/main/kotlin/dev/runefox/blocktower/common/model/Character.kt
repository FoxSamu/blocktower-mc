package dev.runefox.blocktower.common.model

import net.minecraft.world.item.ItemStack

interface Character : Described {
    val icon: ItemStack

    val style: CharacterStyle
}

interface StorytellerCharacter : Character {
    val rules: List<Rule>
}
