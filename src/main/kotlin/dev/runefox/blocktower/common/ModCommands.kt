package dev.runefox.blocktower.common

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import dev.runefox.blocktower.common.command.seatColor
import dev.runefox.blocktower.common.command.town
import dev.runefox.blocktower.common.util.*
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry.registerArgumentType
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.synchronization.ArgumentTypeInfo
import net.minecraft.commands.synchronization.SingletonArgumentInfo.contextFree
import net.minecraft.resources.Identifier



object ModCommands {
    init {
        CommandRegistrationCallback.EVENT.register { disp, regs, _ ->
            build(disp, regs)
        }

        registerArgumentType(blocktower("seat_color"), contextFree { seatColor() })
    }

    private inline fun <reified A : ArgumentType<*>> registerArgumentType(id: Identifier, serializer: ArgumentTypeInfo<A, *>) {
        registerArgumentType(id, A::class.java, serializer)
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
