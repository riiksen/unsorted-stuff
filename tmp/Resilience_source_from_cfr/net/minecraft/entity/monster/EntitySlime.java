/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;

public class EntitySlime
extends EntityLiving
implements IMob {
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private int slimeJumpDelay;
    private static final String __OBFID = "CL_00001698";

    public EntitySlime(World par1World) {
        super(par1World);
        int var2 = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0f;
        this.slimeJumpDelay = this.rand.nextInt(20) + 10;
        this.setSlimeSize(var2);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(1));
    }

    protected void setSlimeSize(int par1) {
        this.dataWatcher.updateObject(16, new Byte((byte)par1));
        this.setSize(0.6f * (float)par1, 0.6f * (float)par1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(par1 * par1);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = par1;
    }

    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Size", this.getSlimeSize() - 1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSlimeSize(par1NBTTagCompound.getInteger("Size") + 1);
    }

    protected String getSlimeParticle() {
        return "slime";
    }

    protected String getJumpSound() {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    @Override
    public void onUpdate() {
        int var2;
        if (!this.worldObj.isClient && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getSlimeSize() > 0) {
            this.isDead = true;
        }
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5f;
        this.prevSquishFactor = this.squishFactor;
        boolean var1 = this.onGround;
        super.onUpdate();
        if (this.onGround && !var1) {
            var2 = this.getSlimeSize();
            int var3 = 0;
            while (var3 < var2 * 8) {
                float var4 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                float var5 = this.rand.nextFloat() * 0.5f + 0.5f;
                float var6 = MathHelper.sin(var4) * (float)var2 * 0.5f * var5;
                float var7 = MathHelper.cos(var4) * (float)var2 * 0.5f * var5;
                this.worldObj.spawnParticle(this.getSlimeParticle(), this.posX + (double)var6, this.boundingBox.minY, this.posZ + (double)var7, 0.0, 0.0, 0.0);
                ++var3;
            }
            if (this.makesSoundOnLand()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.squishAmount = -0.5f;
        } else if (!this.onGround && var1) {
            this.squishAmount = 1.0f;
        }
        this.alterSquishAmount();
        if (this.worldObj.isClient) {
            var2 = this.getSlimeSize();
            this.setSize(0.6f * (float)var2, 0.6f * (float)var2);
        }
    }

    @Override
    protected void updateEntityActionState() {
        this.despawnEntity();
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        if (var1 != null) {
            this.faceEntity(var1, 10.0f, 20.0f);
        }
        if (this.onGround && this.slimeJumpDelay-- <= 0) {
            this.slimeJumpDelay = this.getJumpDelay();
            if (var1 != null) {
                this.slimeJumpDelay /= 3;
            }
            this.isJumping = true;
            if (this.makesSoundOnJump()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.moveStrafing = 1.0f - this.rand.nextFloat() * 2.0f;
            this.moveForward = 1 * this.getSlimeSize();
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveForward = 0.0f;
                this.moveStrafing = 0.0f;
            }
        }
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }

    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }

    protected EntitySlime createInstance() {
        return new EntitySlime(this.worldObj);
    }

    @Override
    public void setDead() {
        int var1 = this.getSlimeSize();
        if (!this.worldObj.isClient && var1 > 1 && this.getHealth() <= 0.0f) {
            int var2 = 2 + this.rand.nextInt(3);
            int var3 = 0;
            while (var3 < var2) {
                float var4 = ((float)(var3 % 2) - 0.5f) * (float)var1 / 4.0f;
                float var5 = ((float)(var3 / 2) - 0.5f) * (float)var1 / 4.0f;
                EntitySlime var6 = this.createInstance();
                var6.setSlimeSize(var1 / 2);
                var6.setLocationAndAngles(this.posX + (double)var4, this.posY + 0.5, this.posZ + (double)var5, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(var6);
                ++var3;
            }
        }
        super.setDead();
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (this.canDamagePlayer()) {
            int var2 = this.getSlimeSize();
            if (this.canEntityBeSeen(par1EntityPlayer) && this.getDistanceSqToEntity(par1EntityPlayer) < 0.6 * (double)var2 * 0.6 * (double)var2 && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength())) {
                this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }

    protected boolean canDamagePlayer() {
        if (this.getSlimeSize() > 1) {
            return true;
        }
        return false;
    }

    protected int getAttackStrength() {
        return this.getSlimeSize();
    }

    @Override
    protected String getHurtSound() {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    @Override
    protected String getDeathSound() {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    @Override
    protected Item func_146068_u() {
        return this.getSlimeSize() == 1 ? Items.slime_ball : Item.getItemById(0);
    }

    @Override
    public boolean getCanSpawnHere() {
        Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1) {
            return false;
        }
        if (this.getSlimeSize() == 1 || this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
            BiomeGenBase var2 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if (var2 == BiomeGenBase.swampland && this.posY > 50.0 && this.posY < 70.0 && this.rand.nextFloat() < 0.5f && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }
            if (this.rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911).nextInt(10) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f * (float)this.getSlimeSize();
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }

    protected boolean makesSoundOnJump() {
        if (this.getSlimeSize() > 0) {
            return true;
        }
        return false;
    }

    protected boolean makesSoundOnLand() {
        if (this.getSlimeSize() > 2) {
            return true;
        }
        return false;
    }
}

