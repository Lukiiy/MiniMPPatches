package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.ClientNetStats;
import me.lukiiy.mppatches.MPPatches;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow private int ticksPlayed;

    @Inject(method = "init", at = @At("TAIL"))
    private void mpPatch$get(CallbackInfo ci) {
        MPPatches.minecraft = (Minecraft) (Object) this;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void mpPatch$updateDebug(CallbackInfo ci) {
        if ((ticksPlayed % 20) == 0) ClientNetStats.updateDebugCache();

        ClientNetStats.packetsSent.lazySet(0);
        ClientNetStats.packetsReceived.lazySet(0);
    }
}
