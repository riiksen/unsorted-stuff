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
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class ModuleTracers
extends DefaultModule {
    private EntityUtils entityUtils = new EntityUtils();

    public ModuleTracers() {
        super("Tracers", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws lines to all players around you");
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Object o : this.invoker.getEntityList()) {
            if (!(o instanceof EntityOtherPlayerMP)) continue;
            EntityOtherPlayerMP player = (EntityOtherPlayerMP)o;
            double posX = this.invoker.getLastTickPosX(player) - RenderManager.renderPosX;
            double posY = this.invoker.getLastTickPosY(player) - RenderManager.renderPosY;
            double posZ = this.invoker.getLastTickPosZ(player) - RenderManager.renderPosZ;
            boolean friend = this.entityUtils.isEntityFriend(player);
            boolean enemy = this.entityUtils.isEntityEnemy(player);
            RenderUtils.drawTracer(0.0, 0.0, 0.0, posX, posY + (double)(this.invoker.getEntityHeight(player) / 2.0f), posZ, (double)(!friend) ? 1 : 0, (double)(!friend && !enemy) ? 1 : 0, (double)(!enemy) ? 1 : 0, 1.0);
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

