/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.render;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.gui.objects.buttons.ModOptionBox;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.tileentity.TileEntityChest;

public class ModuleChestESP
extends DefaultModule {
    private ModOptionBox drawTracers;

    public ModuleChestESP() {
        super("ChestESP", 64);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws a crossed box around chests");
        this.drawTracers = new ModOptionBox("Draw Tracers", 0.0f, 0.0f, false);
        this.guiExtras.add(this.drawTracers);
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Object o : this.invoker.getTileEntityList()) {
            if (!(o instanceof TileEntityChest)) continue;
            TileEntityChest chest = (TileEntityChest)o;
            RenderUtils.drawESP(true, (double)this.invoker.getChestX(chest) - this.invoker.getRenderPosX(), (double)this.invoker.getChestY(chest) - this.invoker.getRenderPosY(), (double)this.invoker.getChestZ(chest) - this.invoker.getRenderPosZ(), (double)(this.invoker.getChestX(chest) + 1) - this.invoker.getRenderPosX(), (double)(this.invoker.getChestY(chest) + 1) - this.invoker.getRenderPosY(), (double)(this.invoker.getChestZ(chest) + 1) - this.invoker.getRenderPosZ(), 0.3, 0.48, 1.0, 0.183, 0.3, 0.48, 1.0, 1.0);
            if (!this.drawTracers.isChecked()) continue;
            RenderUtils.drawTracer(0.0, 0.0, 0.0, (double)this.invoker.getChestX(chest) + 0.5 - this.invoker.getRenderPosX(), (double)this.invoker.getChestY(chest) + 0.5 - this.invoker.getRenderPosY(), (double)this.invoker.getChestZ(chest) + 0.5 - this.invoker.getRenderPosZ(), 0.3, 0.48, 1.0, 1.0);
        }
    }
}

