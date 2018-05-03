/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.render;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ModulePlayerESP
extends DefaultModule {
    public ModulePlayerESP() {
        super("PlayerESP", 25);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws a box around players");
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Object o : this.invoker.getEntityList()) {
            if (!(o instanceof EntityPlayer)) continue;
            RenderUtils.drawPlayerESP((EntityPlayer)o);
        }
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

