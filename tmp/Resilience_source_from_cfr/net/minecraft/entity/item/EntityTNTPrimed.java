/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityTNTPrimed
extends Entity {
    public int fuse;
    private EntityLivingBase tntPlacedBy;
    private static final String __OBFID = "CL_00001681";

    public EntityTNTPrimed(World par1World) {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
    }

    public EntityTNTPrimed(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLivingBase) {
        this(par1World);
        this.setPosition(par2, par4, par6);
        float var9 = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.motionX = (- (float)Math.sin(var9)) * 0.02f;
        this.motionY = 0.20000000298023224;
        this.motionZ = (- (float)Math.cos(var9)) * 0.02f;
        this.fuse = 80;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
        this.tntPlacedBy = par8EntityLivingBase;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.setDead();
            if (!this.worldObj.isClient) {
                this.explode();
            }
        } else {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
    }

    private void explode() {
        float var1 = 4.0f;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, var1, true);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.fuse = par1NBTTagCompound.getByte("Fuse");
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public EntityLivingBase getTntPlacedBy() {
        return this.tntPlacedBy;
    }
}

