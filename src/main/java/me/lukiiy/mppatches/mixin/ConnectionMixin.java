package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.ClientNetStats;
import net.minecraft.network.Connection;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Mixin(Connection.class)
public class ConnectionMixin {
    @Shadow private List field_1286; // readPackets
    @Shadow private List field_1287; // dataPackets
    @Shadow private List field_1288; // chunkPackets

    @Shadow private Thread field_1291; // write thread
    @Shadow private Thread field_1292; // read thread

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mpPatch$init(Socket string, String arg, NetworkHandler par3, CallbackInfo ci) {
        try {
            string.setTcpNoDelay(true);
        } catch (SocketException ignored) {}

        this.field_1286 = Collections.synchronizedList(new LinkedList<>());
        this.field_1287 = Collections.synchronizedList(new LinkedList<>());
        this.field_1288 = Collections.synchronizedList(new LinkedList<>());
    }

    @Inject(method = "sendPacket", at = @At("TAIL"))
    private void mpPatch$wakeWriter(Packet packet, CallbackInfo ci) {
        if (this.field_1291 != null) this.field_1291.interrupt();
    }

    @Inject(method = "method_1129", at = @At("TAIL"))
    private void mpPatch$wakeReader(CallbackInfo ci) {
        if (this.field_1292 != null) this.field_1292.interrupt();
    }

    @Inject(method = "sendPacket", at = @At("HEAD"))
    private void mpPatch$countSent(Packet packet, CallbackInfo ci) {
        ClientNetStats.packetsSent.incrementAndGet();
    }

    @Inject(method = "method_1129", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;handle(Lnet/minecraft/network/NetworkHandler;)V"))
    private void mpPatch$countReceived(CallbackInfo ci) {
        ClientNetStats.packetsReceived.incrementAndGet();
    }

    @Inject(method = "method_1129", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;handle(Lnet/minecraft/network/NetworkHandler;)V"))
    private void mpPatch$markPingReceive(CallbackInfo ci) {
        long now = System.currentTimeMillis();
        long sent = ClientNetStats.lastPingReceived;

        ClientNetStats.lastPingReceived = now;
        ClientNetStats.ping = now - sent;
    }
}
