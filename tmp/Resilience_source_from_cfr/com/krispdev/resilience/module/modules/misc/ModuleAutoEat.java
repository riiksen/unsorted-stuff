/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.InventoryUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ModuleAutoEat
extends DefaultModule {
    private InventoryUtils utils = new InventoryUtils();
    private boolean goOnce = false;
    private int prevSlot = -1;
    private boolean finished = false;

    public ModuleAutoEat() {
        super("AutoEat", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Automatically eats food when you're hungry");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.prevSlot != -1 && this.finished && this.goOnce) {
            this.invoker.setInvSlot(this.prevSlot);
            this.invoker.setUseItemKeyPressed(false);
            this.goOnce = false;
        }
        if (this.invoker.getFoodLevel() < 18) {
            int i = 0;
            while (i < 9) {
                ItemStack item = this.invoker.getItemAtSlotHotbar(i);
                if (item != null && item.getItem() instanceof ItemFood) {
                    this.prevSlot = this.invoker.getCurInvSlot();
                    this.invoker.setInvSlot(i);
                    this.invoker.setUseItemKeyPressed(true);
                    if (this.invoker.getFoodLevel() > 16) {
                        this.goOnce = true;
                        this.finished = true;
                    }
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

