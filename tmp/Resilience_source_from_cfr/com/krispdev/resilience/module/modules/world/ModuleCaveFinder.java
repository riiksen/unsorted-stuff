/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.world;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleCaveFinder
extends DefaultModule {
    public ModuleCaveFinder() {
        super("CaveFinder", 0);
        this.setCategory(ModuleCategory.WORLD);
        this.setDescription("Shows where caves are");
        this.setSave(false);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getInvoker().loadRenderers();
        Resilience.getInstance().getValues().caveFinderEnabled = true;
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getInvoker().loadRenderers();
        Resilience.getInstance().getValues().caveFinderEnabled = false;
    }
}

