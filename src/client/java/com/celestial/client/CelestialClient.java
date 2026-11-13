package com.celestial.client;

import java.util.ArrayDeque;
import java.util.Deque;

import com.celestial.client.config.Theme;
import com.celestial.client.gui.CelestialGui;
import com.celestial.client.module.Module;
import com.celestial.client.module.ModuleManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class CelestialClient implements ClientModInitializer {
    private static KeyMapping guiKey;
    private static final Deque<Long> LEFT_CLICKS = new ArrayDeque<>();
    private static final Deque<Long> RIGHT_CLICKS = new ArrayDeque<>();
    private static boolean leftWasDown;
    private static boolean rightWasDown;

    @Override
    public void onInitializeClient() {
        Celestial.init();
        guiKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.serena.open_gui", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, KeyMapping.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.consumeClick()) {
                if (client.screen instanceof CelestialGui) client.setScreen(null);
                else if (client.player != null) CelestialGui.open(client);
            }
            trackClicks(client);
            ModuleManager.tick(client);
        });

        HudElementRegistry.addLast(Identifier.withDefaultNamespace("serena_hud"), (graphics, deltaTracker) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;
            int x = Celestial.settings.hudX;
            int y = Celestial.settings.hudY;
            int accent = Theme.current.accent;
            if (enabled("fps")) y = drawLine(graphics, mc, "FPS  " + mc.getFps(), x, y, accent, Celestial.settings.hudScale);
            if (enabled("ping")) y = drawLine(graphics, mc, "PING  " + pingText(mc), x, y, accent, Celestial.settings.hudScale);
            if (enabled("cps")) y = drawLine(graphics, mc, "CPS  " + cps(LEFT_CLICKS) + " | " + cps(RIGHT_CLICKS), x, y, accent, Celestial.settings.hudScale);
            if (enabled("keystrokes")) {
                String keys = keyState(mc.options.keyUp, "W") + "  " + keyState(mc.options.keyLeft, "A") + "  " + keyState(mc.options.keyDown, "S") + "  " + keyState(mc.options.keyRight, "D");
                y = drawLine(graphics, mc, keys, x, y + 4, accent, Celestial.settings.hudScale);
                y = drawLine(graphics, mc, "SPACE " + (mc.options.keyJump.isDown() ? "[ON]" : "[  ]"), x, y, accent, Celestial.settings.hudScale);
            }
            if (enabled("armorhud")) {
                String armor = "ARMOR  " + mc.player.getArmorValue() + "/20";
                int right = mc.getWindow().getGuiScaledWidth() - mc.font.width(armor) - 10;
                drawLine(graphics, mc, armor, right, mc.getWindow().getGuiScaledHeight() - 20, accent, Celestial.settings.hudScale);
            }
        });
    }

    private static boolean enabled(String id) {
        Module module = ModuleManager.byId(id);
        return module != null && module.isEnabled();
    }

    private static int drawLine(net.minecraft.client.gui.GuiGraphicsExtractor graphics, Minecraft mc, String text, int x, int y, int accent, double scale) {
        int width = mc.font.width(text);
        int pad = Math.max(2, (int) Math.round(3 * scale));
        int height = Math.max(11, (int) Math.round(13 * scale));
        graphics.fill(x - pad, y - 2, x + width + pad + 2, y + height - 2, 0x990A0712);
        graphics.fill(x - pad, y - 2, x - pad + 2, y + height - 2, accent);
        graphics.text(mc.font, text, x + 1, y, 0xFFF1EAFB, true);
        return y + Math.max(15, (int) Math.round(15 * scale));
    }

    private static String keyState(KeyMapping key, String label) {
        return key.isDown() ? "[" + label + "]" : " " + label + " ";
    }

    private static void trackClicks(Minecraft mc) {
        if (mc.getWindow() == null) return;
        long window = mc.getWindow().getWindow();
        boolean left = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean right = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        long now = System.currentTimeMillis();
        if (left && !leftWasDown) LEFT_CLICKS.addLast(now);
        if (right && !rightWasDown) RIGHT_CLICKS.addLast(now);
        leftWasDown = left;
        rightWasDown = right;
        prune(LEFT_CLICKS, now);
        prune(RIGHT_CLICKS, now);
    }

    private static void prune(Deque<Long> clicks, long now) {
        while (!clicks.isEmpty() && now - clicks.peekFirst() > 1000) clicks.removeFirst();
    }

    private static int cps(Deque<Long> clicks) { return clicks.size(); }

    private static String pingText(Minecraft mc) {
        try {
            if (mc.getConnection() != null && mc.player != null) {
                PlayerInfo info = mc.getConnection().getPlayerInfo(mc.getGameProfile().getId());
                if (info != null) return info.getLatency() + "ms";
            }
        } catch (Exception ignored) { }
        return "--";
    }
}
