/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCarrotOnAStick
extends Item {
    private static final String __OBFID = "CL_00000001";

    public ItemCarrotOnAStick() {
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        EntityPig var4;
        if (par3EntityPlayer.isRiding() && par3EntityPlayer.ridingEntity instanceof EntityPig && (var4 = (EntityPig)par3EntityPlayer.ridingEntity).getAIControlledByPlayer().isControlledByPlayer() && par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() >= 7) {
            var4.getAIControlledByPlayer().boostSpeed();
            par1ItemStack.damageItem(7, par3EntityPlayer);
            if (par1ItemStack.stackSize == 0) {
                ItemStack var5 = new ItemStack(Items.fishing_rod);
                var5.setTagCompound(par1ItemStack.stackTagCompound);
                return var5;
            }
        }
        return par1ItemStack;
    }
}

