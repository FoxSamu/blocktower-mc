package dev.runefox.blocktower.inject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.runefox.blocktower.common.map.Town;

public interface MinecraftServerInj {
    @Nullable Town blocktower$getTown();
    @NotNull Town blocktower$initTown();
    boolean blocktower$deleteTown();
}
