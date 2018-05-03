/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSeedFood
extends ItemFood {
    private Block field_150908_b;
    private Block soilId;
    private static final String __OBFID = "CL_00000060";

    public ItemSeedFood(int p_i45351_1_, float p_i45351_2_, Block p_i45351_3_, Block p_i45351_4_) {
        super(p_i45351_1_, p_i45351_2_, false);
        this.field_150908_b = p_i45351_3_;
        this.soilId = p_i45351_4_;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par7 != 1) {
            return false;
        }
        if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
            if (par3World.getBlock(par4, par5, par6) == this.soilId && par3World.isAirBlock(par4, par5 + 1, par6)) {
                par3World.setBlock(par4, par5 + 1, par6, this.field_150908_b);
                --par1ItemStack.stackSize;
                return true;
            }
            return false;
        }
        return false;
    }
}

