/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;

public class ModuleAutoBlock
extends DefaultModule {
    public ModuleAutoBlock() {
        super("AutoBlock", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically blocks with your sword in KillAura");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().autoBlockEnabled = true;
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().autoBlockEnabled = false;
    }
}

