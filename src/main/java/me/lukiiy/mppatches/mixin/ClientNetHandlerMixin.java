package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.MPPatches;
import net.minecraft.class_340;
import net.minecraft.client.network.ClientNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientNetworkHandler.class)
public class ClientNetHandlerMixin {
    @Inject(method = "method_1431", at = @At("TAIL"))
    private void mpPatch$logChat(class_340 par1, CallbackInfo ci) {
        MPPatches.CHAT_LOGGER.info("[CHAT] {}", par1.field_1270.replaceAll("§.", ""));
    }
}
