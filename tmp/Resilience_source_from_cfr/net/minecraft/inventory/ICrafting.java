/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public interface ICrafting {
    public void sendContainerAndContentsToPlayer(Container var1, List var2);

    public void sendSlotContents(Container var1, int var2, ItemStack var3);

    public void sendProgressBarUpdate(Container var1, int var2, int var3);
}

