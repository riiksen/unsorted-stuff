/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.effect;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityLightningBolt
extends EntityWeatherEffect {
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;
    private static final String __OBFID = "CL_00001666";

    public EntityLightningBolt(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.setLocationAndAngles(par2, par4, par6, 0.0f, 0.0f);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        if (!par1World.isClient && par1World.getGameRules().getGameRuleBooleanValue("doFireTick") && (par1World.difficultySetting == EnumDifficulty.NORMAL || par1World.difficultySetting == EnumDifficulty.HARD) && par1World.doChunksNearChunkExist(MathHelper.floor_double(par2), MathHelper.floor_double(par4), MathHelper.floor_double(par6), 10)) {
            int var10;
            int var9;
            int var8 = MathHelper.floor_double(par2);
            if (par1World.getBlock(var8, var9 = MathHelper.floor_double(par4), var10 = MathHelper.floor_double(par6)).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(par1World, var8, var9, var10)) {
                par1World.setBlock(var8, var9, var10, Blocks.fire);
            }
            var8 = 0;
            while (var8 < 4) {
                int var11;
                var9 = MathHelper.floor_double(par2) + this.rand.nextInt(3) - 1;
                if (par1World.getBlock(var9, var10 = MathHelper.floor_double(par4) + this.rand.nextInt(3) - 1, var11 = MathHelper.floor_double(par6) + this.rand.nextInt(3) - 1).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(par1World, var9, var10, var11)) {
                    par1World.setBlock(var9, var10, var11, Blocks.fire);
                }
                ++var8;
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.lightningState == 2) {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0f, 0.8f + this.rand.nextFloat() * 0.2f);
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0f, 0.5f + this.rand.nextFloat() * 0.2f);
        }
        --this.lightningState;
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
            } else if (this.lightningState < - this.rand.nextInt(10)) {
                int var1;
                int var2;
                int var3;
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
                if (!this.worldObj.isClient && this.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick") && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10) && this.worldObj.getBlock(var1 = MathHelper.floor_double(this.posX), var2 = MathHelper.floor_double(this.posY), var3 = MathHelper.floor_double(this.posZ)).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, var1, var2, var3)) {
                    this.worldObj.setBlock(var1, var2, var3, Blocks.fire);
                }
            }
        }
        if (this.lightningState >= 0) {
            if (this.worldObj.isClient) {
                this.worldObj.lastLightningBolt = 2;
            } else {
                double var6 = 3.0;
                List var7 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(this.posX - var6, this.posY - var6, this.posZ - var6, this.posX + var6, this.posY + 6.0 + var6, this.posZ + var6));
                int var4 = 0;
                while (var4 < var7.size()) {
                    Entity var5 = (Entity)var7.get(var4);
                    var5.onStruckByLightning(this);
                    ++var4;
                }
            }
        }
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }
}

