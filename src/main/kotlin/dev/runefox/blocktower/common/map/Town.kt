package dev.runefox.blocktower.common.map

import dev.runefox.blocktower.common.Codecs
import dev.runefox.blocktower.common.Mod
import dev.runefox.blocktower.common.util.unwrap
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtIo
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ProblemReporter
import net.minecraft.world.level.storage.TagValueInput
import net.minecraft.world.level.storage.TagValueOutput
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists

class Town(val server: MinecraftServer) {
    // TODO include dimension
    var center = BlockPos(0, 0, 0)

    private val seats = EnumMap<SeatColor, TownSeat>(SeatColor::class.java)

    fun addSeat(color: SeatColor, seat: TownSeat): Int {
        if (color in seats) {
            return 1
        }

        seats[color] = seat
        return 0
    }

    fun addSeat(seat: TownSeat): Int {
        val color = SeatColor.entries.find { it !in seats } ?: return 2
        return addSeat(color, seat)
    }

    fun removeSeat(color: SeatColor): Int {
        return if (seats.remove(color) != null) 0 else 2
    }

    fun moveSeat(from: SeatColor, to: SeatColor): Int {
        val seat = seats.remove(from) ?: return 2
        return addSeat(to, seat)
    }

    private fun store(root: ValueOutput) {
        root.store("center", Codecs.BlockPos, center)

        val seats = root.child("seats")
        for ((color, seat) in this.seats) {
            seats.store(color.name, Codecs.TownSeat, seat)
        }
    }

    private fun load(root: ValueInput) {
        center = root.read("center", Codecs.BlockPos).unwrap() ?: BlockPos(0, 0, 0)

        this.seats.clear()
        val seats = root.childOrEmpty("seats")
        for (key in seats.keys()) {
            val seat = SeatColor[key] ?: continue

            this.seats[seat] = seats.read(key, Codecs.TownSeat).unwrap() ?: continue
        }
    }

    fun delete() {
        val file = server.getFile("blocktower_map.nbt")
        file.deleteIfExists()
    }

    fun save() {
        val file = server.getFile("blocktower_map.nbt")
        file.parent.createDirectories()

        val reporter = ProblemReporter.Collector()
        val out = TagValueOutput.createWithContext(reporter, server.registryAccess())
        store(out)

        if (!reporter.isEmpty) {
            Mod.LOGGER.error("Errors while saving Blocktower map NBT")
            Mod.LOGGER.error(reporter.treeReport)
        }

        NbtIo.write(out.buildResult(), file)
    }

    fun load(): Boolean {
        val file = server.getFile("blocktower_map.nbt")
        val nbt = NbtIo.read(file) ?: return false

        val reporter = ProblemReporter.Collector()
        val input = TagValueInput.create(reporter, server.registryAccess(), nbt)
        load(input)

        if (!reporter.isEmpty) {
            Mod.LOGGER.error("Errors while loading Blocktower map NBT")
            Mod.LOGGER.error(reporter.treeReport)
        }

        return true
    }
}
