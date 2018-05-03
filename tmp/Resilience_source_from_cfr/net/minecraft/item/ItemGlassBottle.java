/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemGlassBottle
extends Item {
    private static final String __OBFID = "CL_00001776";

    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return Items.potionitem.getIconFromDamage(0);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        if (var4 == null) {
            return par1ItemStack;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            int var5 = var4.blockX;
            int var6 = var4.blockY;
            int var7 = var4.blockZ;
            if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
                return par1ItemStack;
            }
            if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
                return par1ItemStack;
            }
            if (par2World.getBlock(var5, var6, var7).getMaterial() == Material.water) {
                --par1ItemStack.stackSize;
                if (par1ItemStack.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }
                if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                    par3EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
                }
            }
        }
        return par1ItemStack;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
    }
}

