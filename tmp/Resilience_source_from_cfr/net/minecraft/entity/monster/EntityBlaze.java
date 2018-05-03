/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlaze
extends EntityMob {
    private float heightOffset = 0.5f;
    private int heightOffsetUpdateTime;
    private int field_70846_g;
    private static final String __OBFID = "CL_00001682";

    public EntityBlaze(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
        this.experienceValue = 10;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }

    @Override
    protected String getHurtSound() {
        return "mob.blaze.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.blaze.death";
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    @Override
    public float getBrightness(float par1) {
        return 1.0f;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isClient) {
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1.0f);
            }
            --this.heightOffsetUpdateTime;
            if (this.heightOffsetUpdateTime <= 0) {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
            }
            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset) {
                this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            }
        }
        if (this.rand.nextInt(24) == 0) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.fire", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f);
        }
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        int var1 = 0;
        while (var1 < 2) {
            this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5) * (double)this.width, 0.0, 0.0, 0.0);
            ++var1;
        }
        super.onLivingUpdate();
    }

    @Override
    protected void attackEntity(Entity par1Entity, float par2) {
        if (this.attackTime <= 0 && par2 < 2.0f && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        } else if (par2 < 30.0f) {
            double var3 = par1Entity.posX - this.posX;
            double var5 = par1Entity.boundingBox.minY + (double)(par1Entity.height / 2.0f) - (this.posY + (double)(this.height / 2.0f));
            double var7 = par1Entity.posZ - this.posZ;
            if (this.attackTime == 0) {
                ++this.field_70846_g;
                if (this.field_70846_g == 1) {
                    this.attackTime = 60;
                    this.func_70844_e(true);
                } else if (this.field_70846_g <= 4) {
                    this.attackTime = 6;
                } else {
                    this.attackTime = 100;
                    this.field_70846_g = 0;
                    this.func_70844_e(false);
                }
                if (this.field_70846_g > 1) {
                    float var9 = MathHelper.sqrt_float(par2) * 0.5f;
                    this.worldObj.playAuxSFXAtEntity(null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    int var10 = 0;
                    while (var10 < 1) {
                        EntitySmallFireball var11 = new EntitySmallFireball(this.worldObj, this, var3 + this.rand.nextGaussian() * (double)var9, var5, var7 + this.rand.nextGaussian() * (double)var9);
                        var11.posY = this.posY + (double)(this.height / 2.0f) + 0.5;
                        this.worldObj.spawnEntityInWorld(var11);
                        ++var10;
                    }
                }
            }
            this.rotationYaw = (float)(Math.atan2(var7, var3) * 180.0 / 3.141592653589793) - 90.0f;
            this.hasAttacked = true;
        }
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    protected Item func_146068_u() {
        return Items.blaze_rod;
    }

    @Override
    public boolean isBurning() {
        return this.func_70845_n();
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        if (par1) {
            int var3 = this.rand.nextInt(2 + par2);
            int var4 = 0;
            while (var4 < var3) {
                this.func_145779_a(Items.blaze_rod, 1);
                ++var4;
            }
        }
    }

    public boolean func_70845_n() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    public void func_70844_e(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        var2 = par1 ? (byte)(var2 | 1) : (byte)(var2 & -2);
        this.dataWatcher.updateObject(16, Byte.valueOf(var2));
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
}

