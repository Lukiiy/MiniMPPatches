package me.lukiiy.mppatches.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net.minecraft.network.Connection$1")
public class PacketWriteThreadMixin {
    @ModifyConstant(method = "run", constant = @Constant(longValue = 100L), remap = false)
    private long mpPatch$reduce(long constant) {
        return 5L;
    }
}
