/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob
extends EntityCreature
implements IMob {
    private static final String __OBFID = "CL_00001692";

    public EntityMob(World par1World) {
        super(par1World);
        this.experienceValue = 5;
    }

    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        float var1 = this.getBrightness(1.0f);
        if (var1 > 0.5f) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }

    @Override
    protected String getSwimSound() {
        return "game.hostile.swim";
    }

    @Override
    protected String getSplashSound() {
        return "game.hostile.swim.splash";
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (super.attackEntityFrom(par1DamageSource, par2)) {
            Entity var3 = par1DamageSource.getEntity();
            if (this.riddenByEntity != var3 && this.ridingEntity != var3) {
                if (var3 != this) {
                    this.entityToAttack = var3;
                }
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    protected String getHurtSound() {
        return "game.hostile.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "game.hostile.die";
    }

    @Override
    protected String func_146067_o(int p_146067_1_) {
        return p_146067_1_ > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4;
        float var2 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int var3 = 0;
        if (par1Entity instanceof EntityLivingBase) {
            var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
            var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
        }
        if (var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2)) {
            int var5;
            if (var3 > 0) {
                par1Entity.addVelocity((- MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)) * (float)var3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * (float)var3 * 0.5f);
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }
            if ((var5 = EnchantmentHelper.getFireAspectModifier(this)) > 0) {
                par1Entity.setFire(var5 * 4);
            }
            if (par1Entity instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase)par1Entity, this);
            }
            EnchantmentHelper.func_151385_b(this, par1Entity);
        }
        return var4;
    }

    @Override
    protected void attackEntity(Entity par1Entity, float par2) {
        if (this.attackTime <= 0 && par2 < 2.0f && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
    }

    @Override
    public float getBlockPathWeight(int par1, int par2, int par3) {
        return 0.5f - this.worldObj.getLightBrightness(par1, par2, par3);
    }

    protected boolean isValidLightLevel() {
        int var3;
        int var2;
        int var1 = MathHelper.floor_double(this.posX);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2 = MathHelper.floor_double(this.boundingBox.minY), var3 = MathHelper.floor_double(this.posZ)) > this.rand.nextInt(32)) {
            return false;
        }
        int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
        if (this.worldObj.isThundering()) {
            int var5 = this.worldObj.skylightSubtracted;
            this.worldObj.skylightSubtracted = 10;
            var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
            this.worldObj.skylightSubtracted = var5;
        }
        if (var4 <= this.rand.nextInt(8)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere()) {
            return true;
        }
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }

    @Override
    protected boolean func_146066_aG() {
        return true;
    }
}

