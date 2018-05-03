/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;

public class ModuleJesus
extends DefaultModule {
    public ModuleJesus() {
        super("Jesus", 0);
        this.setCategory(ModuleCategory.MOVEMENT);
        this.setDescription("Allows walking on water");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().jesusEnabled = true;
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().jesusEnabled = false;
    }
}

