package me.lukiiy.mppatches.mixin;

import me.lukiiy.mppatches.MPPatches;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.game.DeathScreen;
import net.minecraft.client.gui.screen.menu.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void mpPatch$deathQuitFix(ButtonWidget button, CallbackInfo ci) { // based on the pause menu code
        if (button.id != 2) return;

        Minecraft minecraft = MPPatches.minecraft;

        minecraft.stats.increment(Stats.GAMES_LEFT, 1);
        if (minecraft.isMultiplayer()) minecraft.world.disconnect();
        minecraft.setWorld(null);
        minecraft.openScreen(new TitleScreen());
        ci.cancel();
    }
}
