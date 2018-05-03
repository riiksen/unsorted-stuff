/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.BoolValue;

public class ModuleMobs
extends DefaultModule {
    public ModuleMobs() {
        super("Target Mobs", 0);
        this.setCategory(ModuleCategory.COMBAT_EXTENSION);
        this.setDescription("Do you want combat mods to attack mobs?");
        this.setVisible(false);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().mobs.setState(true);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().mobs.setState(false);
    }
}

