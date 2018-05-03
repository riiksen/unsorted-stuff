/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.BoolValue;

public class ModuleSafeMode
extends DefaultModule {
    public ModuleSafeMode() {
        super("Safe Mode", 0);
        this.setCategory(ModuleCategory.COMBAT_EXTENSION);
        this.setDescription("Makes KillAura only attack people in ");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().safeMode.setState(true);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().safeMode.setState(false);
    }
}

