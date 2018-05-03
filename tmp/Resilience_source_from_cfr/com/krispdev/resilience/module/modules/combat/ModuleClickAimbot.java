/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;

public class ModuleClickAimbot
extends DefaultModule {
    private Entity selectedEntity;
    private EntityUtils utils = new EntityUtils();

    public ModuleClickAimbot() {
        super("Click Aimbot", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically aims at the closest entity when you click");
    }

    @Override
    public void onClick(EventOnClick event) {
        if (event.getButton() == 0) {
            this.selectedEntity = this.utils.getClosestEntity(this.invoker.getWrapper().getPlayer(), Resilience.getInstance().getValues().players.getState(), Resilience.getInstance().getValues().mobs.getState(), Resilience.getInstance().getValues().animals.getState(), Resilience.getInstance().getValues().invisibles.getState(), Resilience.getInstance().getValues().propBlocks.getState());
            if (this.selectedEntity != null && this.utils.isWithinRange(4.0f, this.selectedEntity) && this.utils.canHit(this.selectedEntity) && !this.utils.isEntityFriend(this.selectedEntity)) {
                this.utils.faceEntity(this.selectedEntity);
                this.utils.hitEntity(this.selectedEntity);
            }
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

