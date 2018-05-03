/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySquid
extends EntityWaterMob {
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    public float squidRotation;
    public float prevSquidRotation;
    public float tentacleAngle;
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    private float rotationVelocity;
    private float field_70871_bB;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;
    private static final String __OBFID = "CL_00001651";

    public EntitySquid(World par1World) {
        super(par1World);
        this.setSize(0.95f, 0.95f);
        this.rotationVelocity = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected Item func_146068_u() {
        return Item.getItemById(0);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(3 + par2) + 1;
        int var4 = 0;
        while (var4 < var3) {
            this.entityDropItem(new ItemStack(Items.dye, 1, 0), 0.0f);
            ++var4;
        }
    }

    @Override
    public boolean isInWater() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.6000000238418579, 0.0), Material.water, this);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;
        if (this.squidRotation > 6.2831855f) {
            this.squidRotation -= 6.2831855f;
            if (this.rand.nextInt(10) == 0) {
                this.rotationVelocity = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
            }
        }
        if (this.isInWater()) {
            float var1;
            if (this.squidRotation < 3.1415927f) {
                var1 = this.squidRotation / 3.1415927f;
                this.tentacleAngle = MathHelper.sin(var1 * var1 * 3.1415927f) * 3.1415927f * 0.25f;
                if ((double)var1 > 0.75) {
                    this.randomMotionSpeed = 1.0f;
                    this.field_70871_bB = 1.0f;
                } else {
                    this.field_70871_bB *= 0.8f;
                }
            } else {
                this.tentacleAngle = 0.0f;
                this.randomMotionSpeed *= 0.9f;
                this.field_70871_bB *= 0.99f;
            }
            if (!this.worldObj.isClient) {
                this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
                this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
                this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
            }
            var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += ((- (float)Math.atan2(this.motionX, this.motionZ)) * 180.0f / 3.1415927f - this.renderYawOffset) * 0.1f;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw += 3.1415927f * this.field_70871_bB * 1.5f;
            this.squidPitch += ((- (float)Math.atan2(var1, this.motionY)) * 180.0f / 3.1415927f - this.squidPitch) * 0.1f;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927f * 0.25f;
            if (!this.worldObj.isClient) {
                this.motionX = 0.0;
                this.motionY -= 0.08;
                this.motionY *= 0.9800000190734863;
                this.motionZ = 0.0;
            }
            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0f - this.squidPitch) * 0.02);
        }
    }

    @Override
    public void moveEntityWithHeading(float par1, float par2) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    protected void updateEntityActionState() {
        ++this.entityAge;
        if (this.entityAge > 100) {
            this.randomMotionVecZ = 0.0f;
            this.randomMotionVecY = 0.0f;
            this.randomMotionVecX = 0.0f;
        } else if (this.rand.nextInt(50) == 0 || !this.inWater || this.randomMotionVecX == 0.0f && this.randomMotionVecY == 0.0f && this.randomMotionVecZ == 0.0f) {
            float var1 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            this.randomMotionVecX = MathHelper.cos(var1) * 0.2f;
            this.randomMotionVecY = -0.1f + this.rand.nextFloat() * 0.2f;
            this.randomMotionVecZ = MathHelper.sin(var1) * 0.2f;
        }
        this.despawnEntity();
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY > 45.0 && this.posY < 63.0 && super.getCanSpawnHere()) {
            return true;
        }
        return false;
    }
}

