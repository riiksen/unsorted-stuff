/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpider
extends EntityMob {
    private static final String __OBFID = "CL_00001699";

    public EntitySpider(World par1World) {
        super(par1World);
        this.setSize(1.4f, 0.9f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929);
    }

    @Override
    protected Entity findPlayerToAttack() {
        float var1 = this.getBrightness(1.0f);
        if (var1 < 0.5f) {
            double var2 = 16.0;
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
        }
        return null;
    }

    @Override
    protected String getLivingSound() {
        return "mob.spider.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.spider.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.spider.death";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }

    @Override
    protected void attackEntity(Entity par1Entity, float par2) {
        float var3 = this.getBrightness(1.0f);
        if (var3 > 0.5f && this.rand.nextInt(100) == 0) {
            this.entityToAttack = null;
        } else if (par2 > 2.0f && par2 < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double var4 = par1Entity.posX - this.posX;
                double var6 = par1Entity.posZ - this.posZ;
                float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
                this.motionX = var4 / (double)var8 * 0.5 * 0.800000011920929 + this.motionX * 0.20000000298023224;
                this.motionZ = var6 / (double)var8 * 0.5 * 0.800000011920929 + this.motionZ * 0.20000000298023224;
                this.motionY = 0.4000000059604645;
            }
        } else {
            super.attackEntity(par1Entity, par2);
        }
    }

    @Override
    protected Item func_146068_u() {
        return Items.string;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        super.dropFewItems(par1, par2);
        if (par1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + par2) > 0)) {
            this.func_145779_a(Items.spider_eye, 1);
        }
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    public void setInWeb() {
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
        return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
    }

    public boolean isBesideClimbableBlock() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    public void setBesideClimbableBlock(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        var2 = par1 ? (byte)(var2 | 1) : (byte)(var2 & -2);
        this.dataWatcher.updateObject(16, Byte.valueOf(var2));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        int var4;
        IEntityLivingData par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
        if (this.worldObj.rand.nextInt(100) == 0) {
            EntitySkeleton var2 = new EntitySkeleton(this.worldObj);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            var2.onSpawnWithEgg(null);
            this.worldObj.spawnEntityInWorld(var2);
            var2.mountEntity(this);
        }
        if (par1EntityLivingData1 == null) {
            par1EntityLivingData1 = new GroupData();
            if (this.worldObj.difficultySetting == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1f * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ)) {
                ((GroupData)par1EntityLivingData1).func_111104_a(this.worldObj.rand);
            }
        }
        if (par1EntityLivingData1 instanceof GroupData && (var4 = ((GroupData)par1EntityLivingData1).field_111105_a) > 0 && Potion.potionTypes[var4] != null) {
            this.addPotionEffect(new PotionEffect(var4, Integer.MAX_VALUE));
        }
        return par1EntityLivingData1;
    }

    public static class GroupData
    implements IEntityLivingData {
        public int field_111105_a;
        private static final String __OBFID = "CL_00001700";

        public void func_111104_a(Random par1Random) {
            int var2 = par1Random.nextInt(5);
            if (var2 <= 1) {
                this.field_111105_a = Potion.moveSpeed.id;
            } else if (var2 <= 2) {
                this.field_111105_a = Potion.damageBoost.id;
            } else if (var2 <= 3) {
                this.field_111105_a = Potion.regeneration.id;
            } else if (var2 <= 4) {
                this.field_111105_a = Potion.invisibility.id;
            }
        }
    }

}

