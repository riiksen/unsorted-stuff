/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class ItemBoat
extends Item {
    private static final String __OBFID = "CL_00001774";

    public ItemBoat() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        float var17;
        float var20;
        double var21;
        float var16;
        float var4 = 1.0f;
        float var5 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * var4;
        float var6 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * var4;
        double var7 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
        double var9 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62 - (double)par3EntityPlayer.yOffset;
        double var11 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
        Vec3 var13 = par2World.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
        float var14 = MathHelper.cos((- var6) * 0.017453292f - 3.1415927f);
        float var15 = MathHelper.sin((- var6) * 0.017453292f - 3.1415927f);
        float var18 = var15 * (var16 = - MathHelper.cos((- var5) * 0.017453292f));
        Vec3 var23 = var13.addVector((double)var18 * (var21 = 5.0), (double)(var17 = MathHelper.sin((- var5) * 0.017453292f)) * var21, (double)(var20 = var14 * var16) * var21);
        MovingObjectPosition var24 = par2World.rayTraceBlocks(var13, var23, true);
        if (var24 == null) {
            return par1ItemStack;
        }
        Vec3 var25 = par3EntityPlayer.getLook(var4);
        boolean var26 = false;
        float var27 = 1.0f;
        List var28 = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand(var27, var27, var27));
        int var29 = 0;
        while (var29 < var28.size()) {
            float var31;
            AxisAlignedBB var32;
            Entity var30 = (Entity)var28.get(var29);
            if (var30.canBeCollidedWith() && (var32 = var30.boundingBox.expand(var31 = var30.getCollisionBorderSize(), var31, var31)).isVecInside(var13)) {
                var26 = true;
            }
            ++var29;
        }
        if (var26) {
            return par1ItemStack;
        }
        if (var24.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            var29 = var24.blockX;
            int var33 = var24.blockY;
            int var34 = var24.blockZ;
            if (par2World.getBlock(var29, var33, var34) == Blocks.snow_layer) {
                --var33;
            }
            EntityBoat var35 = new EntityBoat(par2World, (float)var29 + 0.5f, (float)var33 + 1.0f, (float)var34 + 0.5f);
            var35.rotationYaw = ((MathHelper.floor_double((double)(par3EntityPlayer.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) - 1) * 90;
            if (!par2World.getCollidingBoundingBoxes(var35, var35.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
                return par1ItemStack;
            }
            if (!par2World.isClient) {
                par2World.spawnEntityInWorld(var35);
            }
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
        }
        return par1ItemStack;
    }
}

