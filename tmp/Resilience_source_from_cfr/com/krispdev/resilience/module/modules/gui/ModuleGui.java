/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

public class ModuleGui
extends DefaultModule {
    public ModuleGui() {
        super("Gui", 54);
        this.setCategory(ModuleCategory.GUI);
        this.setDescription("The user interface for the client");
    }

    @Override
    public void onEnable() {
        if (Resilience.getInstance().getWrapper().getWorld() == null || Resilience.getInstance().getWrapper().getPlayer() == null) {
            return;
        }
        this.invoker.displayScreen(Resilience.getInstance().getClickGui());
    }

    @Override
    public void onDisable() {
    }
}

