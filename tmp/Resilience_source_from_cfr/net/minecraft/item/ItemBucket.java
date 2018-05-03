/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class ItemBucket
extends Item {
    private Block isFull;
    private static final String __OBFID = "CL_00000000";

    public ItemBucket(Block p_i45331_1_) {
        this.maxStackSize = 1;
        this.isFull = p_i45331_1_;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        boolean var4 = this.isFull == Blocks.air;
        MovingObjectPosition var5 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var4);
        if (var5 == null) {
            return par1ItemStack;
        }
        if (var5.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            int var6 = var5.blockX;
            int var7 = var5.blockY;
            int var8 = var5.blockZ;
            if (!par2World.canMineBlock(par3EntityPlayer, var6, var7, var8)) {
                return par1ItemStack;
            }
            if (var4) {
                if (!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack)) {
                    return par1ItemStack;
                }
                Material var9 = par2World.getBlock(var6, var7, var8).getMaterial();
                int var10 = par2World.getBlockMetadata(var6, var7, var8);
                if (var9 == Material.water && var10 == 0) {
                    par2World.setBlockToAir(var6, var7, var8);
                    return this.func_150910_a(par1ItemStack, par3EntityPlayer, Items.water_bucket);
                }
                if (var9 == Material.lava && var10 == 0) {
                    par2World.setBlockToAir(var6, var7, var8);
                    return this.func_150910_a(par1ItemStack, par3EntityPlayer, Items.lava_bucket);
                }
            } else {
                if (this.isFull == Blocks.air) {
                    return new ItemStack(Items.bucket);
                }
                if (var5.sideHit == 0) {
                    --var7;
                }
                if (var5.sideHit == 1) {
                    ++var7;
                }
                if (var5.sideHit == 2) {
                    --var8;
                }
                if (var5.sideHit == 3) {
                    ++var8;
                }
                if (var5.sideHit == 4) {
                    --var6;
                }
                if (var5.sideHit == 5) {
                    ++var6;
                }
                if (!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack)) {
                    return par1ItemStack;
                }
                if (this.tryPlaceContainedLiquid(par2World, var6, var7, var8) && !par3EntityPlayer.capabilities.isCreativeMode) {
                    return new ItemStack(Items.bucket);
                }
            }
        }
        return par1ItemStack;
    }

    private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_) {
        if (p_150910_2_.capabilities.isCreativeMode) {
            return p_150910_1_;
        }
        if (--p_150910_1_.stackSize <= 0) {
            return new ItemStack(p_150910_3_);
        }
        if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(p_150910_3_))) {
            p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
        }
        return p_150910_1_;
    }

    public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3, int par4) {
        boolean var6;
        if (this.isFull == Blocks.air) {
            return false;
        }
        Material var5 = par1World.getBlock(par2, par3, par4).getMaterial();
        boolean bl = var6 = !var5.isSolid();
        if (!par1World.isAirBlock(par2, par3, par4) && !var6) {
            return false;
        }
        if (par1World.provider.isHellWorld && this.isFull == Blocks.flowing_water) {
            par1World.playSoundEffect((float)par2 + 0.5f, (float)par3 + 0.5f, (float)par4 + 0.5f, "random.fizz", 0.5f, 2.6f + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8f);
            int var7 = 0;
            while (var7 < 8) {
                par1World.spawnParticle("largesmoke", (double)par2 + Math.random(), (double)par3 + Math.random(), (double)par4 + Math.random(), 0.0, 0.0, 0.0);
                ++var7;
            }
        } else {
            if (!par1World.isClient && var6 && !var5.isLiquid()) {
                par1World.func_147480_a(par2, par3, par4, true);
            }
            par1World.setBlock(par2, par3, par4, this.isFull, 0, 3);
        }
        return true;
    }
}

