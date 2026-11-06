package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.ClientNetStats;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.packet.Packet;
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
    @Shadow private List<Packet> readQueue;
    @Shadow private List<Packet> sendQueue;
    @Shadow private List<Packet> delayedSendQueue;

    @Shadow private Thread writer;
    @Shadow private Thread reader;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;start()V", ordinal = 0))
    private void mpPatch$init(Socket socket, String name, PacketHandler listener, CallbackInfo ci) {
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException ignored) {}

        readQueue = Collections.synchronizedList(new LinkedList<>());
        sendQueue = Collections.synchronizedList(new LinkedList<>());
        delayedSendQueue = Collections.synchronizedList(new LinkedList<>());
    }

    @Inject(method = "send", at = @At("HEAD"))
    private void mpPatch$countSent(Packet packet, CallbackInfo ci) {
        ClientNetStats.packetsSent.incrementAndGet();
    }

    @Inject(method = "send", at = @At("TAIL"))
    private void mpPatch$wakeWriter(Packet packet, CallbackInfo ci) {
        if (writer != null) writer.interrupt();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void mpPatch$wakeReader(CallbackInfo ci) {
        if (reader != null) reader.interrupt();
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/Packet;handle(Lnet/minecraft/network/PacketHandler;)V"))
    private void mpPatch$countReceived(CallbackInfo ci) {
        ClientNetStats.packetsReceived.incrementAndGet();
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/Packet;handle(Lnet/minecraft/network/PacketHandler;)V"))
    private void mpPatch$markPingReceive(CallbackInfo ci) {
        long now = System.currentTimeMillis();
        long sent = ClientNetStats.lastPingReceived;

        ClientNetStats.lastPingReceived = now;
        ClientNetStats.ping = now - sent;
    }
}