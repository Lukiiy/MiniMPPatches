package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.MPPatches;
import net.minecraft.network.packet.ChatMessagePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.handler.ClientNetworkHandler.class)
public class ClientNetHandlerMixin {
    @Inject(method = "handleChatMessage", at = @At("TAIL"))
    private void mpPatch$logChat(ChatMessagePacket packet, CallbackInfo ci) {
        MPPatches.CHAT_LOGGER.info("[CHAT] {}", packet.message.replaceAll("§.", ""));
    }
}
