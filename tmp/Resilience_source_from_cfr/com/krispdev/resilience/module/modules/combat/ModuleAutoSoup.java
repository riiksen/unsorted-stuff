/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.game.InventoryUtils;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.item.ItemStack;

public class ModuleAutoSoup
extends DefaultModule {
    private boolean shouldSoup = false;
    private MethodInvoker invo = Resilience.getInstance().getInvoker();
    private InventoryUtils invUtils = new InventoryUtils();
    private int prevSlot = -1;
    private int soupId = 282;
    private int bowlId = 281;

    public ModuleAutoSoup() {
        super("AutoSoup", 24);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically eats soup when health is low. (For KitPvP)");
    }

    @Override
    public void onHealthUpdate(EventHealthUpdate e) {
        this.shouldSoup = e.getHealth() < Resilience.getInstance().getValues().autoSoupHealth.getValue();
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.shouldSoup) {
            int slotHotbar;
            if (this.prevSlot == -1) {
                this.prevSlot = this.invo.getCurInvSlot();
            }
            if ((slotHotbar = this.invUtils.getSlotOfHotbarItem(this.soupId)) != -1) {
                this.invo.setInvSlot(slotHotbar);
                this.invUtils.sendItemUse(this.invo.getItemAtSlot(slotHotbar));
            } else {
                int invSlot = this.invUtils.getSlotOfInvItem(this.soupId);
                if (invSlot != -1) {
                    int freeSlot = this.invUtils.getFreeSlotInInv(this.bowlId);
                    int freeHotbarSlot = this.invUtils.getFreeSlotInHotbar(0);
                    if (freeHotbarSlot != -1) {
                        this.invUtils.click(freeSlot, 1);
                        this.invUtils.click(invSlot, 1);
                    } else {
                        int hotBarSlotBad = this.invUtils.getSlotOfHotbarItem(this.bowlId);
                        if (hotBarSlotBad != -1) {
                            this.invo.dropItemStack(hotBarSlotBad);
                            this.invUtils.click(invSlot, 1);
                            this.invUtils.sendItemUse(this.invo.getItemAtSlot(invSlot));
                        }
                        this.invUtils.click(invSlot, 1);
                        this.invUtils.click(freeSlot, 1);
                    }
                }
            }
        } else if (this.prevSlot != -1 && this.invo.getCurInvSlot() != this.prevSlot) {
            this.invo.setInvSlot(this.prevSlot);
            this.prevSlot = -1;
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

