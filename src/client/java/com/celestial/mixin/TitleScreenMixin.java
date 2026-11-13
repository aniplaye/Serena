package com.celestial.mixin;

import com.celestial.client.gui.CelestialMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void celestial$replaceTitleScreen(CallbackInfo ci) {
        Minecraft.getInstance().setScreen(new CelestialMainMenu());
        ci.cancel();
    }
}
