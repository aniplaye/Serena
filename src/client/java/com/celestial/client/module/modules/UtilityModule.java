package com.celestial.client.module.modules;

import com.celestial.client.module.Category;
import com.celestial.client.module.Module;

/** Simple toggle-only utility module; rendering and behavior are handled by the client HUD. */
public final class UtilityModule extends Module {
    public UtilityModule(String id, String name, String description, Category category, int key) {
        super(id, name, description, category, key);
    }
}
