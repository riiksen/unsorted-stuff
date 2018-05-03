/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.BoolValue;

public class ModuleAnimals
extends DefaultModule {
    public ModuleAnimals() {
        super("Target Animals", 0);
        this.setCategory(ModuleCategory.COMBAT_EXTENSION);
        this.setDescription("Do you want combat mods to target animals?");
        this.setVisible(false);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().animals.setState(true);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().animals.setState(false);
    }
}

