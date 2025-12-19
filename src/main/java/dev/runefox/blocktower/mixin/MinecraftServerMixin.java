package dev.runefox.blocktower.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.MinecraftServer;

import dev.runefox.blocktower.common.map.Town;
import dev.runefox.blocktower.inject.MinecraftServerInj;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerInj {
    @Unique
    private @Nullable Town town = null;

    @Unique
    private boolean loadedTown = false;

    @Unique
    private void loadTown() {
        if (loadedTown) {
            return;
        }

        var t = new Town((MinecraftServer) (Object) this);
        if (t.load()) {
            town = t;
        }

        loadedTown = true;
    }

    @Override
    public @Nullable Town blocktower$getTown() {
        loadTown();
        return town;
    }

    @Override
    public Town blocktower$initTown() {
        town = new Town((MinecraftServer) (Object) this);
        town.save();
        loadedTown = true;
        return town;
    }

    @Override
    public boolean blocktower$deleteTown() {
        loadTown();
        if (town != null) {
            town.delete();
            town = null;
            return true;
        }

        return false;
    }

    @Inject(
        method = "saveEverything",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;saveAllChunks(ZZZ)Z")
    )
    private void onSaveEverything(boolean b1, boolean b2, boolean b3, CallbackInfoReturnable<Boolean> cir) {
        if (town != null) {
            town.save();
        }
    }

    @Inject(
        method = "stopServer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;saveAllChunks(ZZZ)Z")
    )
    private void onStopServer(CallbackInfo cir) {
        if (town != null) {
            town.save();
        }
    }
}
