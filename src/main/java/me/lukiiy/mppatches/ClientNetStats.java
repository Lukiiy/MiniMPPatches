package me.lukiiy.mppatches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ClientNetStats {
    public static final AtomicLong packetsSent = new AtomicLong();
    public static final AtomicLong packetsReceived = new AtomicLong();
    public static volatile long lastPingReceived;
    public static volatile long ping;

    private static volatile List<String> debugLines = Collections.emptyList();

    public static void updateDebugCache() {
        List<String> lines = new ArrayList<>();
        lines.add("Packets Sent: " + packetsSent.get());
        lines.add("Packets Received: " + packetsReceived.get());
        lines.add("Ping: " + ping);

        debugLines = Collections.unmodifiableList(lines);
    }

    public static List<String> getDebugLines() {
        return debugLines;
    }
}
