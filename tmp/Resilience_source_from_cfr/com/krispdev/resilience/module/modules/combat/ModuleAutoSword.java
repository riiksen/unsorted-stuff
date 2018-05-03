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
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;

public class ModuleAutoSword
extends DefaultModule {
    public ModuleAutoSword() {
        super("AutoSword", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically switches to your swords when you hit an entity");
    }

    @Override
    public void onClick(EventOnClick event) {
        if (event.getButton() == 0 && Resilience.getInstance().getWrapper().getPlayer() != null && this.invoker.getObjectMouseOver() != null && this.invoker.getObjectMouseOver().typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            int i = 0;
            while (i < 9) {
                Item item;
                if (this.invoker.getItemAtSlotHotbar(i) != null && (item = this.invoker.getItemAtSlotHotbar(i).getItem()) != null && item instanceof ItemSword) {
                    this.invoker.setInvSlot(i);
                }
                ++i;
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

