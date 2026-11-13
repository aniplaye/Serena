package com.celestial.client.module;

/** Utility-client module groups. */
public enum Category {
    HUD("HUD"), INTERFACE("Interface"), WORLD("World"), PERFORMANCE("Performance");

    private final String label;
    Category(String label) { this.label = label; }
    public String getLabel() { return label; }
}
