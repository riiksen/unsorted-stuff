/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.utilities.game;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
    private MethodInvoker in = Resilience.getInstance().getInvoker();

    public int getSlotOfHotbarItem(int itemId) {
        int i = 0;
        while (i < 9) {
            ItemStack is = this.in.getItemAtSlotHotbar(i);
            if (is != null && this.in.getIdFromItem(is.getItem()) == itemId) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public int getSlotOfInvItem(int itemId) {
        int i = 9;
        while (i < 36) {
            ItemStack is = this.in.getItemAtSlot(i);
            if (is != null && this.in.getIdFromItem(is.getItem()) == itemId) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public int getFreeSlotInHotbar(int itemId) {
        int i = 0;
        while (i < 9) {
            if (this.in.getItemAtSlot(i) == null) {
                return i;
            }
            if (this.in.getItemAtSlot(i) != null) {
                ItemStack itemAtSlot = this.in.getItemAtSlotHotbar(itemId);
                Item item = this.in.getItemById(itemId);
                int idInSlot = this.in.getIdFromItem(itemAtSlot.getItem());
                if (itemAtSlot != null && itemAtSlot != null && idInSlot == itemId && itemAtSlot.stackSize < item.getItemStackLimit()) {
                    return i;
                }
            } else {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public int getFreeSlotInInv(int itemId) {
        int i = 36;
        while (i < 45) {
            if (this.in.getItemAtSlot(i) == null) {
                return i;
            }
            if (this.in.getItemAtSlot(i) != null) {
                ItemStack itemAtSlot = null;
                itemAtSlot = this.in.getItemAtSlot(i);
                if (itemAtSlot != null) {
                    Item item = this.in.getItemById(itemId);
                    int idInSlot = this.in.getIdFromItem(itemAtSlot.getItem());
                    if (itemAtSlot != null && item != null && idInSlot == itemId && itemAtSlot.stackSize < item.getItemStackLimit()) {
                        return i;
                    }
                }
            }
            ++i;
        }
        return -1;
    }

    public void click(int slot, int mode) {
        this.in.clickWindow(slot, mode, 0, Resilience.getInstance().getWrapper().getPlayer());
    }

    public void sendItemUse(ItemStack itemStack) {
        this.in.sendUseItem(itemStack, Resilience.getInstance().getWrapper().getPlayer());
    }
}

