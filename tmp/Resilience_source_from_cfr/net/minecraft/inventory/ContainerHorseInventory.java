/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory
extends Container {
    private IInventory field_111243_a;
    private EntityHorse theHorse;
    private static final String __OBFID = "CL_00001751";

    public ContainerHorseInventory(IInventory par1IInventory, IInventory par2IInventory, final EntityHorse par3EntityHorse) {
        int var6;
        int var7;
        this.field_111243_a = par2IInventory;
        this.theHorse = par3EntityHorse;
        int var4 = 3;
        par2IInventory.openInventory();
        int var5 = (var4 - 4) * 18;
        this.addSlotToContainer(new Slot(par2IInventory, 0, 8, 18){
            private static final String __OBFID = "CL_00001752";

            @Override
            public boolean isItemValid(ItemStack par1ItemStack) {
                if (super.isItemValid(par1ItemStack) && par1ItemStack.getItem() == Items.saddle && !this.getHasStack()) {
                    return true;
                }
                return false;
            }
        });
        this.addSlotToContainer(new Slot(par2IInventory, 1, 8, 36){
            private static final String __OBFID = "CL_00001753";

            @Override
            public boolean isItemValid(ItemStack par1ItemStack) {
                if (super.isItemValid(par1ItemStack) && par3EntityHorse.func_110259_cr() && EntityHorse.func_146085_a(par1ItemStack.getItem())) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean func_111238_b() {
                return par3EntityHorse.func_110259_cr();
            }
        });
        if (par3EntityHorse.isChested()) {
            var6 = 0;
            while (var6 < var4) {
                var7 = 0;
                while (var7 < 5) {
                    this.addSlotToContainer(new Slot(par2IInventory, 2 + var7 + var6 * 5, 80 + var7 * 18, 18 + var6 * 18));
                    ++var7;
                }
                ++var6;
            }
        }
        var6 = 0;
        while (var6 < 3) {
            var7 = 0;
            while (var7 < 9) {
                this.addSlotToContainer(new Slot(par1IInventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 102 + var6 * 18 + var5));
                ++var7;
            }
            ++var6;
        }
        var6 = 0;
        while (var6 < 9) {
            this.addSlotToContainer(new Slot(par1IInventory, var6, 8 + var6 * 18, 160 + var5));
            ++var6;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        if (this.field_111243_a.isUseableByPlayer(par1EntityPlayer) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(par1EntityPlayer) < 8.0f) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 < this.field_111243_a.getSizeInventory() ? !this.mergeItemStack(var5, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true) : (this.getSlot(1).isItemValid(var5) && !this.getSlot(1).getHasStack() ? !this.mergeItemStack(var5, 1, 2, false) : (this.getSlot(0).isItemValid(var5) ? !this.mergeItemStack(var5, 0, 1, false) : this.field_111243_a.getSizeInventory() <= 2 || !this.mergeItemStack(var5, 2, this.field_111243_a.getSizeInventory(), false)))) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
        }
        return var3;
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.field_111243_a.closeInventory();
    }

}

