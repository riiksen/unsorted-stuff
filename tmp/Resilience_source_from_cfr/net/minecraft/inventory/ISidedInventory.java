/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ISidedInventory
extends IInventory {
    public int[] getAccessibleSlotsFromSide(int var1);

    public boolean canInsertItem(int var1, ItemStack var2, int var3);

    public boolean canExtractItem(int var1, ItemStack var2, int var3);
}

