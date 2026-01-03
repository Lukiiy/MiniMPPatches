package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.MPPatches;
import net.minecraft.class_217;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void mpPatch$deathQuitFix(ButtonWidget par1, CallbackInfo ci) { // based on the pause menu code
        if (par1.id == 2) {
            Minecraft minecraft = MPPatches.minecraft;

            minecraft.field_2773.method_1990(class_217.field_823, 1);
            if (minecraft.isWorldRemote()) minecraft.world.method_293();

            minecraft.setWorld(null);
            minecraft.setScreen(new TitleScreen());
            ci.cancel();
        }
    }
}
