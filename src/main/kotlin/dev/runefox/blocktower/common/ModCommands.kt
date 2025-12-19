package dev.runefox.blocktower.common

import com.mojang.brigadier.CommandDispatcher
import dev.runefox.blocktower.common.command.town
import dev.runefox.blocktower.common.util.command
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack

object ModCommands {
    init {
        CommandRegistrationCallback.EVENT.register { disp, regs, _ ->
            build(disp, regs)
        }
    }

    private fun build(disp: CommandDispatcher<CommandSourceStack>, regs: CommandBuildContext) {
        val base = disp.command("blocktower") {
            town()
        }

        disp.command("bt") {
            redirect(base)
        }
    }
}
