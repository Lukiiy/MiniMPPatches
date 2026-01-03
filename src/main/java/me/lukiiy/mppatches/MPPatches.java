package me.lukiiy.mppatches;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MPPatches implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("mppatches");
    public static final Logger CHAT_LOGGER = LoggerFactory.getLogger("mppatches_chat");
    public static Minecraft minecraft;

    @Override
    public void onInitialize() {}
}
