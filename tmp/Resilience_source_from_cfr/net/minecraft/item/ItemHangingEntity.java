/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemHangingEntity
extends Item {
    private final Class hangingEntityClass;
    private static final String __OBFID = "CL_00000038";

    public ItemHangingEntity(Class p_i45342_1_) {
        this.hangingEntityClass = p_i45342_1_;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par7 == 0) {
            return false;
        }
        if (par7 == 1) {
            return false;
        }
        int var11 = Direction.facingToDirection[par7];
        EntityHanging var12 = this.createHangingEntity(par3World, par4, par5, par6, var11);
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (var12 != null && var12.onValidSurface()) {
            if (!par3World.isClient) {
                par3World.spawnEntityInWorld(var12);
            }
            --par1ItemStack.stackSize;
        }
        return true;
    }

    private EntityHanging createHangingEntity(World par1World, int par2, int par3, int par4, int par5) {
        return this.hangingEntityClass == EntityPainting.class ? new EntityPainting(par1World, par2, par3, par4, par5) : (this.hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(par1World, par2, par3, par4, par5) : null);
    }
}

