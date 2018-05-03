/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNameTag
extends Item {
    private static final String __OBFID = "CL_00000052";

    public ItemNameTag() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {
        if (!par1ItemStack.hasDisplayName()) {
            return false;
        }
        if (par3EntityLivingBase instanceof EntityLiving) {
            EntityLiving var4 = (EntityLiving)par3EntityLivingBase;
            var4.setCustomNameTag(par1ItemStack.getDisplayName());
            var4.func_110163_bv();
            --par1ItemStack.stackSize;
            return true;
        }
        return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
    }
}

