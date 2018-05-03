/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModuleForceField
extends DefaultModule {
    private EntityUtils entityUtils = new EntityUtils();

    public ModuleForceField() {
        super("ForceField", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("For unprotected servers. Attacks entities.");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
        if (Resilience.getInstance().getWrapper().getPlayer() == null || Resilience.getInstance().getWrapper().getWorld() == null) {
            return;
        }
        Resilience.getInstance().getLogger().warningChat("ForceField does not bypass protection on most servers. Use KillAura instead!");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        try {
            for (Object o : this.invoker.getEntityList()) {
                EntityLivingBase entity = null;
                if (o instanceof EntityLivingBase) {
                    entity = (EntityLivingBase)o;
                }
                if (entity == null || !this.entityUtils.isWithinRange(4.2f, entity) || this.entityUtils.isEntityFriend(entity) || this.entityUtils.isEntityDead(entity) || this.entityUtils.isThePlayer(entity)) continue;
                this.entityUtils.hitEntity(entity, true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

