package me.lukiiy.mppatches;

import me.lukiiy.f3mod.DebugEntry;
import me.lukiiy.f3mod.F3Mod;
import me.lukiiy.f3mod.F3ModAPI;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class F3ModHook implements F3ModAPI {
    @Override
    public Collection<DebugEntry> getEntries() {
        return Collections.singleton(new DebugEntry() {
            @Override
            public F3Mod.Section getSection() {
                return F3Mod.Section.RIGHT;
            }

            @Override
            public List<String> provide(ClientPlayerEntity clientPlayerEntity, World world) {
                if (!world.isRemote) return Collections.emptyList();
                return ClientNetStats.getDebugLines();
            }
        });
    }
}
