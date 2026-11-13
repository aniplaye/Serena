package com.celestial.client.module.modules;

import com.celestial.client.module.Category;
import com.celestial.client.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class FullBrightModule extends Module {
    private double previousGamma = 1.0;
    public FullBrightModule() {
        super("fullbright", "FullBright", "Raises in-game brightness for visibility.", Category.WORLD, GLFW.GLFW_KEY_UNKNOWN);
    }
    @Override protected void onEnable(Minecraft mc) {
        previousGamma = mc.options.gamma().get();
        mc.options.gamma().set(16.0);
    }
    @Override protected void onDisable(Minecraft mc) {
        mc.options.gamma().set(previousGamma);
    }
}
