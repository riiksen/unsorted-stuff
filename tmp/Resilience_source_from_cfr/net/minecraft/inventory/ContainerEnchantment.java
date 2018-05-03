/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerEnchantment
extends Container {
    public IInventory tableInventory;
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand;
    public long nameSeed;
    public int[] enchantLevels;
    private static final String __OBFID = "CL_00001745";

    public ContainerEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
        this.tableInventory = new InventoryBasic("Enchant", true, 1){
            private static final String __OBFID = "CL_00001746";

            @Override
            public int getInventoryStackLimit() {
                return 1;
            }

            @Override
            public void onInventoryChanged() {
                super.onInventoryChanged();
                ContainerEnchantment.this.onCraftMatrixChanged(this);
            }
        };
        this.rand = new Random();
        this.enchantLevels = new int[3];
        this.worldPointer = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.addSlotToContainer(new Slot(this.tableInventory, 0, 25, 47){
            private static final String __OBFID = "CL_00001747";

            @Override
            public boolean isItemValid(ItemStack par1ItemStack) {
                return true;
            }
        });
        int var6 = 0;
        while (var6 < 3) {
            int var7 = 0;
            while (var7 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
                ++var7;
            }
            ++var6;
        }
        var6 = 0;
        while (var6 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
            ++var6;
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int var1 = 0;
        while (var1 < this.crafters.size()) {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            var2.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            var2.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            var2.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
            ++var1;
        }
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 >= 0 && par1 <= 2) {
            this.enchantLevels[par1] = par2;
        } else {
            super.updateProgressBar(par1, par2);
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        if (par1IInventory == this.tableInventory) {
            ItemStack var2 = par1IInventory.getStackInSlot(0);
            if (var2 != null && var2.isItemEnchantable()) {
                this.nameSeed = this.rand.nextLong();
                if (!this.worldPointer.isClient) {
                    int var3 = 0;
                    int var4 = -1;
                    while (var4 <= 1) {
                        int var5 = -1;
                        while (var5 <= 1) {
                            if ((var4 != 0 || var5 != 0) && this.worldPointer.isAirBlock(this.posX + var5, this.posY, this.posZ + var4) && this.worldPointer.isAirBlock(this.posX + var5, this.posY + 1, this.posZ + var4)) {
                                if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                    ++var3;
                                }
                                if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY + 1, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                    ++var3;
                                }
                                if (var5 != 0 && var4 != 0) {
                                    if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY, this.posZ + var4) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY + 1, this.posZ + var4) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlock(this.posX + var5, this.posY, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlock(this.posX + var5, this.posY + 1, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                }
                            }
                            ++var5;
                        }
                        ++var4;
                    }
                    var4 = 0;
                    while (var4 < 3) {
                        this.enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var4, var3, var2);
                        ++var4;
                    }
                    this.detectAndSendChanges();
                }
            } else {
                int var3 = 0;
                while (var3 < 3) {
                    this.enchantLevels[var3] = 0;
                    ++var3;
                }
            }
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = this.tableInventory.getStackInSlot(0);
        if (this.enchantLevels[par2] > 0 && var3 != null && (par1EntityPlayer.experienceLevel >= this.enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isClient) {
                boolean var5;
                List var4 = EnchantmentHelper.buildEnchantmentList(this.rand, var3, this.enchantLevels[par2]);
                boolean bl = var5 = var3.getItem() == Items.book;
                if (var4 != null) {
                    par1EntityPlayer.addExperienceLevel(- this.enchantLevels[par2]);
                    if (var5) {
                        var3.func_150996_a(Items.enchanted_book);
                    }
                    int var6 = var5 && var4.size() > 1 ? this.rand.nextInt(var4.size()) : -1;
                    int var7 = 0;
                    while (var7 < var4.size()) {
                        EnchantmentData var8 = (EnchantmentData)var4.get(var7);
                        if (!var5 || var7 != var6) {
                            if (var5) {
                                Items.enchanted_book.addEnchantment(var3, var8);
                            } else {
                                var3.addEnchantment(var8.enchantmentobj, var8.enchantmentLevel);
                            }
                        }
                        ++var7;
                    }
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        ItemStack var2;
        super.onContainerClosed(par1EntityPlayer);
        if (!this.worldPointer.isClient && (var2 = this.tableInventory.getStackInSlotOnClosing(0)) != null) {
            par1EntityPlayer.dropPlayerItemWithRandomChoice(var2, false);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.worldPointer.getBlock(this.posX, this.posY, this.posZ) != Blocks.enchanting_table ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5, (double)this.posY + 0.5, (double)this.posZ + 0.5) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 1, 37, true)) {
                    return null;
                }
            } else {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(var5)) {
                    return null;
                }
                if (var5.hasTagCompound() && var5.stackSize == 1) {
                    ((Slot)this.inventorySlots.get(0)).putStack(var5.copy());
                    var5.stackSize = 0;
                } else if (var5.stackSize >= 1) {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(var5.getItem(), 1, var5.getItemDamage()));
                    --var5.stackSize;
                }
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }
        return var3;
    }

}

