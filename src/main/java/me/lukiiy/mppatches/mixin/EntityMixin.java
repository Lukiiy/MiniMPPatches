package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.MPPatches;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;x:D", opcode = Opcodes.PUTFIELD))
    private void mpPatch$fixX(Entity entity, double value) {
        if (mpPatch$allowWrite(entity)) entity.x = value;
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;y:D", opcode = Opcodes.PUTFIELD))
    private void mpPatch$fixY(Entity entity, double value) {
        if (mpPatch$allowWrite(entity)) entity.y = value;
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;z:D", opcode = Opcodes.PUTFIELD))
    private void mpPatch$fixZ(Entity entity, double value) {
        if (mpPatch$allowWrite(entity)) entity.z = value;
    }

    @Unique
    private static boolean mpPatch$allowWrite(Entity entity) {
        return !MPPatches.minecraft.isWorldRemote() || entity instanceof PlayerEntity || !(entity instanceof LivingEntity);
    }
}
