/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryBasic
implements IInventory {
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_70480_d;
    private boolean field_94051_e;
    private static final String __OBFID = "CL_00001514";

    public InventoryBasic(String par1Str, boolean par2, int par3) {
        this.inventoryTitle = par1Str;
        this.field_94051_e = par2;
        this.slotsCount = par3;
        this.inventoryContents = new ItemStack[par3];
    }

    public void func_110134_a(IInvBasic par1IInvBasic) {
        if (this.field_70480_d == null) {
            this.field_70480_d = new ArrayList();
        }
        this.field_70480_d.add(par1IInvBasic);
    }

    public void func_110132_b(IInvBasic par1IInvBasic) {
        this.field_70480_d.remove(par1IInvBasic);
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.inventoryContents[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.inventoryContents[par1] != null) {
            if (this.inventoryContents[par1].stackSize <= par2) {
                ItemStack var3 = this.inventoryContents[par1];
                this.inventoryContents[par1] = null;
                this.onInventoryChanged();
                return var3;
            }
            ItemStack var3 = this.inventoryContents[par1].splitStack(par2);
            if (this.inventoryContents[par1].stackSize == 0) {
                this.inventoryContents[par1] = null;
            }
            this.onInventoryChanged();
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.inventoryContents[par1] != null) {
            ItemStack var2 = this.inventoryContents[par1];
            this.inventoryContents[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.inventoryContents[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }

    @Override
    public String getInventoryName() {
        return this.inventoryTitle;
    }

    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_94051_e;
    }

    public void func_110133_a(String par1Str) {
        this.field_94051_e = true;
        this.inventoryTitle = par1Str;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
        if (this.field_70480_d != null) {
            int var1 = 0;
            while (var1 < this.field_70480_d.size()) {
                ((IInvBasic)this.field_70480_d.get(var1)).onInventoryChanged(this);
                ++var1;
            }
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
}

