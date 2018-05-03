/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIControlledByPlayer
extends EntityAIBase {
    private final EntityLiving thisEntity;
    private final float maxSpeed;
    private float currentSpeed;
    private boolean speedBoosted;
    private int speedBoostTime;
    private int maxSpeedBoostTime;
    private static final String __OBFID = "CL_00001580";

    public EntityAIControlledByPlayer(EntityLiving par1EntityLiving, float par2) {
        this.thisEntity = par1EntityLiving;
        this.maxSpeed = par2;
        this.setMutexBits(7);
    }

    @Override
    public void startExecuting() {
        this.currentSpeed = 0.0f;
    }

    @Override
    public void resetTask() {
        this.speedBoosted = false;
        this.currentSpeed = 0.0f;
    }

    @Override
    public boolean shouldExecute() {
        if (this.thisEntity.isEntityAlive() && this.thisEntity.riddenByEntity != null && this.thisEntity.riddenByEntity instanceof EntityPlayer && (this.speedBoosted || this.thisEntity.canBeSteered())) {
            return true;
        }
        return false;
    }

    @Override
    public void updateTask() {
        ItemStack var22;
        EntityPlayer var1 = (EntityPlayer)this.thisEntity.riddenByEntity;
        EntityCreature var2 = (EntityCreature)this.thisEntity;
        float var3 = MathHelper.wrapAngleTo180_float(var1.rotationYaw - this.thisEntity.rotationYaw) * 0.5f;
        if (var3 > 5.0f) {
            var3 = 5.0f;
        }
        if (var3 < -5.0f) {
            var3 = -5.0f;
        }
        this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + var3);
        if (this.currentSpeed < this.maxSpeed) {
            this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01f;
        }
        if (this.currentSpeed > this.maxSpeed) {
            this.currentSpeed = this.maxSpeed;
        }
        int var4 = MathHelper.floor_double(this.thisEntity.posX);
        int var5 = MathHelper.floor_double(this.thisEntity.posY);
        int var6 = MathHelper.floor_double(this.thisEntity.posZ);
        float var7 = this.currentSpeed;
        if (this.speedBoosted) {
            if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
                this.speedBoosted = false;
            }
            var7 += var7 * 1.15f * MathHelper.sin((float)this.speedBoostTime / (float)this.maxSpeedBoostTime * 3.1415927f);
        }
        float var8 = 0.91f;
        if (this.thisEntity.onGround) {
            var8 = this.thisEntity.worldObj.getBlock((int)MathHelper.floor_float((float)((float)var4)), (int)(MathHelper.floor_float((float)((float)var5)) - 1), (int)MathHelper.floor_float((float)((float)var6))).slipperiness * 0.91f;
        }
        float var9 = 0.16277136f / (var8 * var8 * var8);
        float var10 = MathHelper.sin(var2.rotationYaw * 3.1415927f / 180.0f);
        float var11 = MathHelper.cos(var2.rotationYaw * 3.1415927f / 180.0f);
        float var12 = var2.getAIMoveSpeed() * var9;
        float var13 = Math.max(var7, 1.0f);
        var13 = var12 / var13;
        float var14 = var7 * var13;
        float var15 = - var14 * var10;
        float var16 = var14 * var11;
        if (MathHelper.abs(var15) > MathHelper.abs(var16)) {
            if (var15 < 0.0f) {
                var15 -= this.thisEntity.width / 2.0f;
            }
            if (var15 > 0.0f) {
                var15 += this.thisEntity.width / 2.0f;
            }
            var16 = 0.0f;
        } else {
            var15 = 0.0f;
            if (var16 < 0.0f) {
                var16 -= this.thisEntity.width / 2.0f;
            }
            if (var16 > 0.0f) {
                var16 += this.thisEntity.width / 2.0f;
            }
        }
        int var17 = MathHelper.floor_double(this.thisEntity.posX + (double)var15);
        int var18 = MathHelper.floor_double(this.thisEntity.posZ + (double)var16);
        PathPoint var19 = new PathPoint(MathHelper.floor_float(this.thisEntity.width + 1.0f), MathHelper.floor_float(this.thisEntity.height + var1.height + 1.0f), MathHelper.floor_float(this.thisEntity.width + 1.0f));
        if (var4 != var17 || var6 != var18) {
            boolean var21;
            Block var20 = this.thisEntity.worldObj.getBlock(var4, var5, var6);
            boolean bl = var21 = !this.func_151498_a(var20) && (var20.getMaterial() != Material.air || !this.func_151498_a(this.thisEntity.worldObj.getBlock(var4, var5 - 1, var6)));
            if (var21 && PathFinder.func_82565_a(this.thisEntity, var17, var5, var18, var19, false, false, true) == 0 && PathFinder.func_82565_a(this.thisEntity, var4, var5 + 1, var6, var19, false, false, true) == 1 && PathFinder.func_82565_a(this.thisEntity, var17, var5 + 1, var18, var19, false, false, true) == 1) {
                var2.getJumpHelper().setJumping();
            }
        }
        if (!var1.capabilities.isCreativeMode && this.currentSpeed >= this.maxSpeed * 0.5f && this.thisEntity.getRNG().nextFloat() < 0.006f && !this.speedBoosted && (var22 = var1.getHeldItem()) != null && var22.getItem() == Items.carrot_on_a_stick) {
            var22.damageItem(1, var1);
            if (var22.stackSize == 0) {
                ItemStack var23 = new ItemStack(Items.fishing_rod);
                var23.setTagCompound(var22.stackTagCompound);
                var1.inventory.mainInventory[var1.inventory.currentItem] = var23;
            }
        }
        this.thisEntity.moveEntityWithHeading(0.0f, var7);
    }

    private boolean func_151498_a(Block p_151498_1_) {
        if (p_151498_1_.getRenderType() != 10 && !(p_151498_1_ instanceof BlockSlab)) {
            return false;
        }
        return true;
    }

    public boolean isSpeedBoosted() {
        return this.speedBoosted;
    }

    public void boostSpeed() {
        this.speedBoosted = true;
        this.speedBoostTime = 0;
        this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
    }

    public boolean isControlledByPlayer() {
        if (!this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3f) {
            return true;
        }
        return false;
    }
}

