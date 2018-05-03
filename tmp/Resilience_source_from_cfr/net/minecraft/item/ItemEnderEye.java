/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ItemEnderEye
extends Item {
    private static final String __OBFID = "CL_00000026";

    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        Block var11 = par3World.getBlock(par4, par5, par6);
        int var12 = par3World.getBlockMetadata(par4, par5, par6);
        if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && var11 == Blocks.end_portal_frame && !BlockEndPortalFrame.func_150020_b(var12)) {
            int var29;
            if (par3World.isClient) {
                return true;
            }
            par3World.setBlockMetadataWithNotify(par4, par5, par6, var12 + 4, 2);
            par3World.func_147453_f(par4, par5, par6, Blocks.end_portal_frame);
            --par1ItemStack.stackSize;
            int var13 = 0;
            while (var13 < 16) {
                double var14 = (float)par4 + (5.0f + itemRand.nextFloat() * 6.0f) / 16.0f;
                double var16 = (float)par5 + 0.8125f;
                double var18 = (float)par6 + (5.0f + itemRand.nextFloat() * 6.0f) / 16.0f;
                double var20 = 0.0;
                double var22 = 0.0;
                double var24 = 0.0;
                par3World.spawnParticle("smoke", var14, var16, var18, var20, var22, var24);
                ++var13;
            }
            var13 = var12 & 3;
            int var26 = 0;
            int var15 = 0;
            boolean var27 = false;
            boolean var17 = true;
            int var28 = Direction.rotateRight[var13];
            int var19 = -2;
            while (var19 <= 2) {
                var29 = par4 + Direction.offsetX[var28] * var19;
                int var21 = par6 + Direction.offsetZ[var28] * var19;
                if (par3World.getBlock(var29, par5, var21) == Blocks.end_portal_frame) {
                    if (!BlockEndPortalFrame.func_150020_b(par3World.getBlockMetadata(var29, par5, var21))) {
                        var17 = false;
                        break;
                    }
                    var15 = var19;
                    if (!var27) {
                        var26 = var19;
                        var27 = true;
                    }
                }
                ++var19;
            }
            if (var17 && var15 == var26 + 2) {
                int var21;
                int var30;
                var19 = var26;
                while (var19 <= var15) {
                    var29 = par4 + Direction.offsetX[var28] * var19;
                    var21 = par6 + Direction.offsetZ[var28] * var19;
                    if (par3World.getBlock(var29 += Direction.offsetX[var13] * 4, par5, var21 += Direction.offsetZ[var13] * 4) != Blocks.end_portal_frame || !BlockEndPortalFrame.func_150020_b(par3World.getBlockMetadata(var29, par5, var21))) {
                        var17 = false;
                        break;
                    }
                    ++var19;
                }
                var19 = var26 - 1;
                while (var19 <= var15 + 1) {
                    var29 = 1;
                    while (var29 <= 3) {
                        var21 = par4 + Direction.offsetX[var28] * var19;
                        var30 = par6 + Direction.offsetZ[var28] * var19;
                        if (par3World.getBlock(var21 += Direction.offsetX[var13] * var29, par5, var30 += Direction.offsetZ[var13] * var29) != Blocks.end_portal_frame || !BlockEndPortalFrame.func_150020_b(par3World.getBlockMetadata(var21, par5, var30))) {
                            var17 = false;
                            break;
                        }
                        ++var29;
                    }
                    var19 += 4;
                }
                if (var17) {
                    var19 = var26;
                    while (var19 <= var15) {
                        var29 = 1;
                        while (var29 <= 3) {
                            var21 = par4 + Direction.offsetX[var28] * var19;
                            var30 = par6 + Direction.offsetZ[var28] * var19;
                            par3World.setBlock(var21 += Direction.offsetX[var13] * var29, par5, var30 += Direction.offsetZ[var13] * var29, Blocks.end_portal, 0, 2);
                            ++var29;
                        }
                        ++var19;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        ChunkPosition var5;
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);
        if (var4 != null && var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && par2World.getBlock(var4.blockX, var4.blockY, var4.blockZ) == Blocks.end_portal_frame) {
            return par1ItemStack;
        }
        if (!par2World.isClient && (var5 = par2World.findClosestStructure("Stronghold", (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ)) != null) {
            EntityEnderEye var6 = new EntityEnderEye(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY + 1.62 - (double)par3EntityPlayer.yOffset, par3EntityPlayer.posZ);
            var6.moveTowards(var5.field_151329_a, var5.field_151327_b, var5.field_151328_c);
            par2World.spawnEntityInWorld(var6);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            par2World.playAuxSFXAtEntity(null, 1002, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ, 0);
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
        }
        return par1ItemStack;
    }
}

