package com.celestial.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;

/** Lightweight, custom Serena-themed replacement for the vanilla title screen. */
public final class CelestialMainMenu extends Screen {
    public CelestialMainMenu() { super(Component.literal("Serena Client")); }

    @Override protected void init() {
        int buttonWidth = 190;
        int left = (width - buttonWidth) / 2;
        int top = height / 2 - 2;
        addRenderableWidget(Button.builder(Component.literal("Singleplayer"), b -> minecraft.setScreen(new SelectWorldScreen(this)))
            .bounds(left, top, buttonWidth, 22).build());
        addRenderableWidget(Button.builder(Component.literal("Multiplayer"), b -> minecraft.setScreen(new JoinMultiplayerScreen(this)))
            .bounds(left, top + 28, buttonWidth, 22).build());
        addRenderableWidget(Button.builder(Component.literal("Options"), b -> minecraft.setScreen(new OptionsScreen(this, minecraft.options)))
            .bounds(left, top + 56, buttonWidth, 22).build());
        addRenderableWidget(Button.builder(Component.literal("Quit Game"), b -> minecraft.stop())
            .bounds(left, top + 84, buttonWidth, 22).build());
    }

    @Override public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        drawSpace(graphics);
        int center = width / 2;
        graphics.centeredText(font, "SERENA", center, height / 4, 0xFFFFEAF3);
        graphics.centeredText(font, "A QUIETER WAY TO PLAY", center, height / 4 + 18, 0xFFFF82B8);
        graphics.fill(center - 70, height / 4 + 33, center + 70, height / 4 + 34, 0xFFD43D83);
        graphics.text(font, "FABRIC UTILITY CLIENT", 10, height - 22, 0xFFB88B9F, false);
        graphics.text(font, "Right Shift  •  Client Dashboard", width - 174, height - 22, 0xFFB88B9F, false);
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    private void drawSpace(GuiGraphicsExtractor g) {
        g.fill(0, 0, width, height, 0xFF080408);
        g.fill(0, height / 3, width, height, 0xFF130812);
        int cx = width * 3 / 4;
        int cy = height / 3;
        for (int r = 150; r >= 25; r -= 25) {
            int alpha = Math.max(5, 28 - r / 9);
            g.fill(cx - r, cy - r / 2, cx + r, cy + r / 2, (alpha << 24) | 0x004A1630);
        }
        g.fill(cx - 34, cy - 34, cx + 34, cy + 34, 0xFF160A12);
        g.outline(cx - 36, cy - 36, cx + 36, cy + 36, 0xFFB83270);
        // Deterministic starfield: no textures, particles or per-frame allocations.
        for (int i = 0; i < 90; i++) {
            int sx = Math.floorMod(i * 173 + 41, Math.max(1, width));
            int sy = Math.floorMod(i * 97 + 19, Math.max(1, height));
            int size = i % 13 == 0 ? 2 : 1;
            int color = i % 5 == 0 ? 0xFFFF82B8 : 0xFF806B77;
            g.fill(sx, sy, sx + size, sy + size, color);
        }
        g.fill(0, 0, width, 1, 0xFF4A1D34);
    }

    @Override public boolean isPauseScreen() { return false; }
}
