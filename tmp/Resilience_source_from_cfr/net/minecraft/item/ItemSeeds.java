/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSeeds
extends Item {
    private Block field_150925_a;
    private Block soilBlockID;
    private static final String __OBFID = "CL_00000061";

    public ItemSeeds(Block p_i45352_1_, Block p_i45352_2_) {
        this.field_150925_a = p_i45352_1_;
        this.soilBlockID = p_i45352_2_;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par7 != 1) {
            return false;
        }
        if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
            if (par3World.getBlock(par4, par5, par6) == this.soilBlockID && par3World.isAirBlock(par4, par5 + 1, par6)) {
                par3World.setBlock(par4, par5 + 1, par6, this.field_150925_a);
                --par1ItemStack.stackSize;
                return true;
            }
            return false;
        }
        return false;
    }
}

