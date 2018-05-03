/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;

public class ModuleAimbot
extends DefaultModule {
    private Entity target;
    private EntityUtils entityUtils = new EntityUtils();

    public ModuleAimbot() {
        super("Aimbot", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically aims at the closest target");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.target = this.entityUtils.getClosestEntity(Resilience.getInstance().getWrapper().getPlayer(), Resilience.getInstance().getValues().players.getState(), Resilience.getInstance().getValues().mobs.getState(), Resilience.getInstance().getValues().animals.getState(), Resilience.getInstance().getValues().invisibles.getState(), Resilience.getInstance().getValues().propBlocks.getState());
        try {
            if (this.target != null && this.entityUtils.isWithinRange(Resilience.getInstance().getValues().range.getValue(), this.target)) {
                this.entityUtils.faceEntity(this.target);
            }
        }
        catch (Exception exception) {
            // empty catch block
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

