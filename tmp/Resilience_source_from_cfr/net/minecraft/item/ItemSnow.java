/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemSnow
extends ItemBlockWithMetadata {
    private static final String __OBFID = "CL_00000068";

    public ItemSnow(Block p_i45354_1_, Block p_i45354_2_) {
        super(p_i45354_1_, p_i45354_2_);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        int var13;
        int var12;
        if (par1ItemStack.stackSize == 0) {
            return false;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        Block var11 = par3World.getBlock(par4, par5, par6);
        if (var11 == Blocks.snow_layer && (var13 = (var12 = par3World.getBlockMetadata(par4, par5, par6)) & 7) <= 6 && par3World.checkNoEntityCollision(this.field_150939_a.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockMetadataWithNotify(par4, par5, par6, var13 + 1 | var12 & -8, 2)) {
            par3World.playSoundEffect((float)par4 + 0.5f, (float)par5 + 0.5f, (float)par6 + 0.5f, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150939_a.stepSound.func_150494_d() * 0.8f);
            --par1ItemStack.stackSize;
            return true;
        }
        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
}

