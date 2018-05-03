/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;

public class ModuleAutoChestSteal
extends DefaultModule {
    public ModuleAutoChestSteal() {
        super("Auto Chest Steal", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Automatically steals when you open a chest");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().autoChestStealEnabled = true;
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().autoChestStealEnabled = false;
    }
}

