/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb
extends Entity {
    public int xpColor;
    public int xpOrbAge;
    public int field_70532_c;
    private int xpOrbHealth = 5;
    private int xpValue;
    private EntityPlayer closestPlayer;
    private int xpTargetColor;
    private static final String __OBFID = "CL_00001544";

    public EntityXPOrb(World par1World, double par2, double par4, double par6, int par8) {
        super(par1World);
        this.setSize(0.5f, 0.5f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.motionY = (float)(Math.random() * 0.2) * 2.0f;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.xpValue = par8;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityXPOrb(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public int getBrightnessForRender(float par1) {
        float var2 = 0.5f;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 255;
        int var5 = var3 >> 16 & 255;
        if ((var4 += (int)(var2 * 15.0f * 16.0f)) > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }

    @Override
    public void onUpdate() {
        double var9;
        double var5;
        double var11;
        double var3;
        double var7;
        super.onUpdate();
        if (this.field_70532_c > 0) {
            --this.field_70532_c;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746;
        if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        double var1 = 8.0;
        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > var1 * var1) {
                this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, var1);
            }
            this.xpTargetColor = this.xpColor;
        }
        if (this.closestPlayer != null && (var11 = 1.0 - (var9 = Math.sqrt((var3 = (this.closestPlayer.posX - this.posX) / var1) * var3 + (var5 = (this.closestPlayer.posY + (double)this.closestPlayer.getEyeHeight() - this.posY) / var1) * var5 + (var7 = (this.closestPlayer.posZ - this.posZ) / var1) * var7))) > 0.0) {
            var11 *= var11;
            this.motionX += var3 / var9 * var11 * 0.1;
            this.motionY += var5 / var9 * var11 * 0.1;
            this.motionZ += var7 / var9 * var11 * 0.1;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float var13 = 0.98f;
        if (this.onGround) {
            var13 = this.worldObj.getBlock((int)MathHelper.floor_double((double)this.posX), (int)(MathHelper.floor_double((double)this.boundingBox.minY) - 1), (int)MathHelper.floor_double((double)this.posZ)).slipperiness * 0.98f;
        }
        this.motionX *= (double)var13;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= (double)var13;
        if (this.onGround) {
            this.motionY *= -0.8999999761581421;
        }
        ++this.xpColor;
        ++this.xpOrbAge;
        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
    }

    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    @Override
    protected void dealFireDamage(int par1) {
        this.attackEntityFrom(DamageSource.inFire, par1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        this.xpOrbHealth = (int)((float)this.xpOrbHealth - par2);
        if (this.xpOrbHealth <= 0) {
            this.setDead();
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.xpOrbHealth);
        par1NBTTagCompound.setShort("Age", (short)this.xpOrbAge);
        par1NBTTagCompound.setShort("Value", (short)this.xpValue);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.xpOrbHealth = par1NBTTagCompound.getShort("Health") & 255;
        this.xpOrbAge = par1NBTTagCompound.getShort("Age");
        this.xpValue = par1NBTTagCompound.getShort("Value");
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isClient && this.field_70532_c == 0 && par1EntityPlayer.xpCooldown == 0) {
            par1EntityPlayer.xpCooldown = 2;
            this.worldObj.playSoundAtEntity(par1EntityPlayer, "random.orb", 0.1f, 0.5f * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.8f));
            par1EntityPlayer.onItemPickup(this, 1);
            par1EntityPlayer.addExperience(this.xpValue);
            this.setDead();
        }
    }

    public int getXpValue() {
        return this.xpValue;
    }

    public int getTextureByXP() {
        return this.xpValue >= 2477 ? 10 : (this.xpValue >= 1237 ? 9 : (this.xpValue >= 617 ? 8 : (this.xpValue >= 307 ? 7 : (this.xpValue >= 149 ? 6 : (this.xpValue >= 73 ? 5 : (this.xpValue >= 37 ? 4 : (this.xpValue >= 17 ? 3 : (this.xpValue >= 7 ? 2 : (this.xpValue >= 3 ? 1 : 0)))))))));
    }

    public static int getXPSplit(int par0) {
        return par0 >= 2477 ? 2477 : (par0 >= 1237 ? 1237 : (par0 >= 617 ? 617 : (par0 >= 307 ? 307 : (par0 >= 149 ? 149 : (par0 >= 73 ? 73 : (par0 >= 37 ? 37 : (par0 >= 17 ? 17 : (par0 >= 7 ? 7 : (par0 >= 3 ? 3 : 1)))))))));
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}

