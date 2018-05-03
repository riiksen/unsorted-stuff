/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFishingRod
extends Item {
    private IIcon theIcon;
    private static final String __OBFID = "CL_00000034";

    public ItemFishingRod() {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
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
        if (par3EntityPlayer.fishEntity != null) {
            int var4 = par3EntityPlayer.fishEntity.func_146034_e();
            par1ItemStack.damageItem(var4, par3EntityPlayer);
            par3EntityPlayer.swingItem();
        } else {
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!par2World.isClient) {
                par2World.spawnEntityInWorld(new EntityFishHook(par2World, par3EntityPlayer));
            }
            par3EntityPlayer.swingItem();
        }
        return par1ItemStack;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_uncast");
        this.theIcon = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_cast");
    }

    public IIcon func_94597_g() {
        return this.theIcon;
    }

    @Override
    public boolean isItemTool(ItemStack par1ItemStack) {
        return super.isItemTool(par1ItemStack);
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}

