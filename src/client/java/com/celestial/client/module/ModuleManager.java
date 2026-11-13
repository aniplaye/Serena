package com.celestial.client.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.celestial.client.module.modules.FullBrightModule;
import com.celestial.client.module.modules.UtilityModule;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class ModuleManager {
    private static final List<Module> MODULES = new ArrayList<>();
    private ModuleManager() {}

    public static void init() {
        MODULES.clear();
        MODULES.add(new UtilityModule("fps", "FPS Counter", "Displays your current frame rate.", Category.PERFORMANCE, GLFW.GLFW_KEY_UNKNOWN));
        MODULES.add(new UtilityModule("ping", "Ping Counter", "Displays your connection latency.", Category.HUD, GLFW.GLFW_KEY_UNKNOWN));
        MODULES.add(new UtilityModule("cps", "CPS Counter", "Counts recent left and right mouse clicks.", Category.HUD, GLFW.GLFW_KEY_UNKNOWN));
        MODULES.add(new FullBrightModule());
        MODULES.add(new UtilityModule("keystrokes", "Keystrokes", "Shows movement keys and their pressed state.", Category.INTERFACE, GLFW.GLFW_KEY_UNKNOWN));
        MODULES.add(new UtilityModule("armorhud", "Armor HUD", "Displays your current armor value.", Category.HUD, GLFW.GLFW_KEY_UNKNOWN));
        byId("fps").setEnabled(true, false);
        byId("ping").setEnabled(true, false);
        byId("cps").setEnabled(true, false);
        byId("fullbright").setEnabled(false, false);
        byId("keystrokes").setEnabled(false, false);
        byId("armorhud").setEnabled(false, false);
    }

    public static List<Module> getModules() { return Collections.unmodifiableList(MODULES); }
    public static Module byId(String id) {
        for (Module module : MODULES) if (module.getId().equals(id)) return module;
        return null;
    }
    public static void tick(Minecraft mc) {
        if (mc.player == null || mc.level == null) return;
        for (Module module : MODULES) {
            module.handleKey();
            if (module.isEnabled()) module.onTick(mc);
        }
    }
}
