/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWitch
extends EntityMob
implements IRangedAttackMob {
    private static final UUID field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier field_110185_bq = new AttributeModifier(field_110184_bp, "Drinking speed penalty", -0.25, 0).setSaved(false);
    private static final Item[] witchDrops = new Item[]{Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick};
    private int witchAttackTimer;
    private static final String __OBFID = "CL_00001701";

    public EntityWitch(World par1World) {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(21, Byte.valueOf(0));
    }

    @Override
    protected String getLivingSound() {
        return "mob.witch.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.witch.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.witch.death";
    }

    public void setAggressive(boolean par1) {
        this.getDataWatcher().updateObject(21, Byte.valueOf(par1 ? 1 : 0));
    }

    public boolean getAggressive() {
        if (this.getDataWatcher().getWatchableObjectByte(21) == 1) {
            return true;
        }
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isClient) {
            if (this.getAggressive()) {
                if (this.witchAttackTimer-- <= 0) {
                    List var2;
                    this.setAggressive(false);
                    ItemStack var1 = this.getHeldItem();
                    this.setCurrentItemOrArmor(0, null);
                    if (var1 != null && var1.getItem() == Items.potionitem && (var2 = Items.potionitem.getEffects(var1)) != null) {
                        for (PotionEffect var4 : var2) {
                            this.addPotionEffect(new PotionEffect(var4));
                        }
                    }
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(field_110185_bq);
                }
            } else {
                int var5 = -1;
                if (this.rand.nextFloat() < 0.15f && this.isInsideOfMaterial(Material.water) && !this.isPotionActive(Potion.waterBreathing)) {
                    var5 = 8237;
                } else if (this.rand.nextFloat() < 0.15f && this.isBurning() && !this.isPotionActive(Potion.fireResistance)) {
                    var5 = 16307;
                } else if (this.rand.nextFloat() < 0.05f && this.getHealth() < this.getMaxHealth()) {
                    var5 = 16341;
                } else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    var5 = 16274;
                } else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    var5 = 16274;
                }
                if (var5 > -1) {
                    this.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, var5));
                    this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
                    this.setAggressive(true);
                    IAttributeInstance var6 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                    var6.removeModifier(field_110185_bq);
                    var6.applyModifier(field_110185_bq);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.worldObj.setEntityState(this, 15);
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 15) {
            int var2 = 0;
            while (var2 < this.rand.nextInt(35) + 10) {
                this.worldObj.spawnParticle("witchMagic", this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.boundingBox.maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
                ++var2;
            }
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    @Override
    protected float applyPotionDamageCalculations(DamageSource par1DamageSource, float par2) {
        par2 = super.applyPotionDamageCalculations(par1DamageSource, par2);
        if (par1DamageSource.getEntity() == this) {
            par2 = 0.0f;
        }
        if (par1DamageSource.isMagicDamage()) {
            par2 = (float)((double)par2 * 0.15);
        }
        return par2;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(3) + 1;
        int var4 = 0;
        while (var4 < var3) {
            int var5 = this.rand.nextInt(3);
            Item var6 = witchDrops[this.rand.nextInt(witchDrops.length)];
            if (par2 > 0) {
                var5 += this.rand.nextInt(par2 + 1);
            }
            int var7 = 0;
            while (var7 < var5) {
                this.func_145779_a(var6, 1);
                ++var7;
            }
            ++var4;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
        if (!this.getAggressive()) {
            EntityPotion var3 = new EntityPotion(this.worldObj, (EntityLivingBase)this, 32732);
            var3.rotationPitch -= -20.0f;
            double var4 = par1EntityLivingBase.posX + par1EntityLivingBase.motionX - this.posX;
            double var6 = par1EntityLivingBase.posY + (double)par1EntityLivingBase.getEyeHeight() - 1.100000023841858 - this.posY;
            double var8 = par1EntityLivingBase.posZ + par1EntityLivingBase.motionZ - this.posZ;
            float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
            if (var10 >= 8.0f && !par1EntityLivingBase.isPotionActive(Potion.moveSlowdown)) {
                var3.setPotionDamage(32698);
            } else if (par1EntityLivingBase.getHealth() >= 8.0f && !par1EntityLivingBase.isPotionActive(Potion.poison)) {
                var3.setPotionDamage(32660);
            } else if (var10 <= 3.0f && !par1EntityLivingBase.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25f) {
                var3.setPotionDamage(32696);
            }
            var3.setThrowableHeading(var4, var6 + (double)(var10 * 0.2f), var8, 0.75f, 8.0f);
            this.worldObj.spawnEntityInWorld(var3);
        }
    }
}

