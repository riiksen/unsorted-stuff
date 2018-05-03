/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemReed
extends Item {
    private Block field_150935_a;
    private static final String __OBFID = "CL_00001773";

    public ItemReed(Block p_i45329_1_) {
        this.field_150935_a = p_i45329_1_;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        int var12;
        Block var11 = par3World.getBlock(par4, par5, par6);
        if (var11 == Blocks.snow_layer && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
            par7 = 1;
        } else if (var11 != Blocks.vine && var11 != Blocks.tallgrass && var11 != Blocks.deadbush) {
            if (par7 == 0) {
                --par5;
            }
            if (par7 == 1) {
                ++par5;
            }
            if (par7 == 2) {
                --par6;
            }
            if (par7 == 3) {
                ++par6;
            }
            if (par7 == 4) {
                --par4;
            }
            if (par7 == 5) {
                ++par4;
            }
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (par1ItemStack.stackSize == 0) {
            return false;
        }
        if (par3World.canPlaceEntityOnSide(this.field_150935_a, par4, par5, par6, false, par7, null, par1ItemStack) && par3World.setBlock(par4, par5, par6, this.field_150935_a, var12 = this.field_150935_a.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0), 3)) {
            if (par3World.getBlock(par4, par5, par6) == this.field_150935_a) {
                this.field_150935_a.onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
                this.field_150935_a.onPostBlockPlaced(par3World, par4, par5, par6, var12);
            }
            par3World.playSoundEffect((float)par4 + 0.5f, (float)par5 + 0.5f, (float)par6 + 0.5f, this.field_150935_a.stepSound.func_150496_b(), (this.field_150935_a.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150935_a.stepSound.func_150494_d() * 0.8f);
            --par1ItemStack.stackSize;
        }
        return true;
    }
}

