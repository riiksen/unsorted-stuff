/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.BoolValue;

public class ModulePropBlocks
extends DefaultModule {
    public ModulePropBlocks() {
        super("Target PropBlocks", 0);
        this.setCategory(ModuleCategory.COMBAT_EXTENSION);
        this.setDescription("Do you want combat mods to target Prophunt blocks?");
        this.setVisible(false);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().propBlocks.setState(true);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().propBlocks.setState(false);
    }
}

