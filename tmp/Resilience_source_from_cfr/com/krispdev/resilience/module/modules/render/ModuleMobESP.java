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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

public class ModuleMobESP
extends DefaultModule {
    public ModuleMobESP() {
        super("MobESP", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws colour coded boxes around mobs and animals");
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Object o : this.invoker.getEntityList()) {
            EntityLivingBase entity;
            boolean mob;
            if (o instanceof EntityPlayer || !(o instanceof EntityLivingBase)) continue;
            RenderUtils.drawESP(false, this.invoker.getBoundboxMinX(entity) - this.invoker.getRenderPosX() - 0.1, this.invoker.getBoundboxMinY(entity) - this.invoker.getRenderPosY() - 0.1, this.invoker.getBoundboxMinZ(entity) - this.invoker.getRenderPosZ() - 0.1, this.invoker.getBoundboxMaxX(entity) - this.invoker.getRenderPosX() + 0.1, this.invoker.getBoundboxMaxY(entity) - this.invoker.getRenderPosY() + 0.1, this.invoker.getBoundboxMaxZ(entity) - this.invoker.getRenderPosZ() + 0.1, 1.0, 1.0, 1.0, 0.19, (double)(mob = (entity = (EntityLivingBase)o) instanceof EntityMob) ? 1 : 0, (double)(!mob) ? 1 : 0, (double)(!mob) ? 1 : 0, 1.0);
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

