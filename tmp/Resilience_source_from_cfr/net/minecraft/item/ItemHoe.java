/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHoe
extends Item {
    protected Item.ToolMaterial theToolMaterial;
    private static final String __OBFID = "CL_00000039";

    public ItemHoe(Item.ToolMaterial p_i45343_1_) {
        this.theToolMaterial = p_i45343_1_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45343_1_.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        Block var11 = par3World.getBlock(par4, par5, par6);
        if (par7 != 0 && par3World.getBlock(par4, par5 + 1, par6).getMaterial() == Material.air && (var11 == Blocks.grass || var11 == Blocks.dirt)) {
            Block var12 = Blocks.farmland;
            par3World.playSoundEffect((float)par4 + 0.5f, (float)par5 + 0.5f, (float)par6 + 0.5f, var12.stepSound.func_150498_e(), (var12.stepSound.func_150497_c() + 1.0f) / 2.0f, var12.stepSound.func_150494_d() * 0.8f);
            if (par3World.isClient) {
                return true;
            }
            par3World.setBlock(par4, par5, par6, var12);
            par1ItemStack.damageItem(1, par2EntityPlayer);
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
}

