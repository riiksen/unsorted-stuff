/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.inventory;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.world.World;

public class SlotCrafting
extends Slot {
    private final IInventory craftMatrix;
    private EntityPlayer thePlayer;
    private int amountCrafted;
    private static final String __OBFID = "CL_00001761";

    public SlotCrafting(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6) {
        super(par3IInventory, par4, par5, par6);
        this.thePlayer = par1EntityPlayer;
        this.craftMatrix = par2IInventory;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(par1, this.getStack().stackSize);
        }
        return super.decrStackSize(par1);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2) {
        this.amountCrafted += par2;
        this.onCrafting(par1ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack) {
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        this.amountCrafted = 0;
        if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
            this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
        }
        if (par1ItemStack.getItem() instanceof ItemPickaxe) {
            this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
        }
        if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
            this.thePlayer.addStat(AchievementList.buildFurnace, 1);
        }
        if (par1ItemStack.getItem() instanceof ItemHoe) {
            this.thePlayer.addStat(AchievementList.buildHoe, 1);
        }
        if (par1ItemStack.getItem() == Items.bread) {
            this.thePlayer.addStat(AchievementList.makeBread, 1);
        }
        if (par1ItemStack.getItem() == Items.cake) {
            this.thePlayer.addStat(AchievementList.bakeCake, 1);
        }
        if (par1ItemStack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)par1ItemStack.getItem()).func_150913_i() != Item.ToolMaterial.WOOD) {
            this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
        }
        if (par1ItemStack.getItem() instanceof ItemSword) {
            this.thePlayer.addStat(AchievementList.buildSword, 1);
        }
        if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
            this.thePlayer.addStat(AchievementList.enchantments, 1);
        }
        if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
            this.thePlayer.addStat(AchievementList.bookcase, 1);
        }
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        this.onCrafting(par2ItemStack);
        int var3 = 0;
        while (var3 < this.craftMatrix.getSizeInventory()) {
            ItemStack var4 = this.craftMatrix.getStackInSlot(var3);
            if (var4 != null) {
                this.craftMatrix.decrStackSize(var3, 1);
                if (var4.getItem().hasContainerItem()) {
                    ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
                    if (!var4.getItem().doesContainerItemLeaveCraftingGrid(var4) || !this.thePlayer.inventory.addItemStackToInventory(var5)) {
                        if (this.craftMatrix.getStackInSlot(var3) == null) {
                            this.craftMatrix.setInventorySlotContents(var3, var5);
                        } else {
                            this.thePlayer.dropPlayerItemWithRandomChoice(var5, false);
                        }
                    }
                }
            }
            ++var3;
        }
    }
}

