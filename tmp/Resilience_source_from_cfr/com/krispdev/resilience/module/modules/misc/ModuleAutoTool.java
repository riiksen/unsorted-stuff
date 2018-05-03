/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ModuleAutoTool
extends DefaultModule {
    public ModuleAutoTool() {
        super("AutoTool", 65);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Automatically switches to the best tool on click");
    }

    @Override
    public void onBlockClicked(EventBlockClick event) {
        float compare = 1.0f;
        int slot = -1;
        int i = 0;
        while (i < 9) {
            try {
                float strength;
                ItemStack item = this.invoker.getItemAtSlotHotbar(i);
                if (item != null && (strength = this.invoker.getStrVsBlock(item, event.getBlock())) > compare) {
                    compare = strength;
                    slot = i;
                }
            }
            catch (Exception item) {
                // empty catch block
            }
            ++i;
        }
        if (slot != -1) {
            this.invoker.setInvSlot(slot);
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

