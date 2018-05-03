/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;

public class EntitySkeleton
extends EntityMob
implements IRangedAttackMob {
    private EntityAIArrowAttack aiArrowAttack;
    private EntityAIAttackOnCollide aiAttackOnCollide;
    private static final String __OBFID = "CL_00001697";

    public EntitySkeleton(World par1World) {
        super(par1World);
        this.aiArrowAttack = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0f);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, false);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        if (par1World != null && !par1World.isClient) {
            this.setCombatTask();
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte(0));
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.skeleton.step", 0.15f, 1.0f);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (this.getSkeletonType() == 1 && par1Entity instanceof EntityLivingBase) {
                ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }
            return true;
        }
        return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public void onLivingUpdate() {
        float var1;
        if (this.worldObj.isDaytime() && !this.worldObj.isClient && (var1 = this.getBrightness(1.0f)) > 0.5f && this.rand.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
            boolean var2 = true;
            ItemStack var3 = this.getEquipmentInSlot(4);
            if (var3 != null) {
                if (var3.isItemStackDamageable()) {
                    var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));
                    if (var3.getItemDamageForDisplay() >= var3.getMaxDamage()) {
                        this.renderBrokenItemStack(var3);
                        this.setCurrentItemOrArmor(4, null);
                    }
                }
                var2 = false;
            }
            if (var2) {
                this.setFire(8);
            }
        }
        if (this.worldObj.isClient && this.getSkeletonType() == 1) {
            this.setSize(0.72f, 2.34f);
        }
        super.onLivingUpdate();
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        if (this.ridingEntity instanceof EntityCreature) {
            EntityCreature var1 = (EntityCreature)this.ridingEntity;
            this.renderYawOffset = var1.renderYawOffset;
        }
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (par1DamageSource.getSourceOfDamage() instanceof EntityArrow && par1DamageSource.getEntity() instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)par1DamageSource.getEntity();
            double var3 = var2.posX - this.posX;
            double var5 = var2.posZ - this.posZ;
            if (var3 * var3 + var5 * var5 >= 2500.0) {
                var2.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }

    @Override
    protected Item func_146068_u() {
        return Items.arrow;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3;
        int var4;
        if (this.getSkeletonType() == 1) {
            var3 = this.rand.nextInt(3 + par2) - 1;
            var4 = 0;
            while (var4 < var3) {
                this.func_145779_a(Items.coal, 1);
                ++var4;
            }
        } else {
            var3 = this.rand.nextInt(3 + par2);
            var4 = 0;
            while (var4 < var3) {
                this.func_145779_a(Items.arrow, 1);
                ++var4;
            }
        }
        var3 = this.rand.nextInt(3 + par2);
        var4 = 0;
        while (var4 < var3) {
            this.func_145779_a(Items.bone, 1);
            ++var4;
        }
    }

    @Override
    protected void dropRareDrop(int par1) {
        if (this.getSkeletonType() == 1) {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0f);
        }
    }

    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        Calendar var2;
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0) {
            this.tasks.addTask(4, this.aiAttackOnCollide);
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
        } else {
            this.tasks.addTask(4, this.aiArrowAttack);
            this.addRandomArmor();
            this.enchantEquipment();
        }
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));
        if (this.getEquipmentInSlot(4) == null && (var2 = this.worldObj.getCurrentDate()).get(2) + 1 == 10 && var2.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
            this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1f ? Blocks.lit_pumpkin : Blocks.pumpkin));
            this.equipmentDropChances[4] = 0.0f;
        }
        return par1EntityLivingData;
    }

    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        ItemStack var1 = this.getHeldItem();
        if (var1 != null && var1.getItem() == Items.bow) {
            this.tasks.addTask(4, this.aiArrowAttack);
        } else {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
        EntityArrow var3 = new EntityArrow(this.worldObj, this, par1EntityLivingBase, 1.6f, 14 - this.worldObj.difficultySetting.getDifficultyId() * 4);
        int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var3.setDamage((double)(par2 * 2.0f) + this.rand.nextGaussian() * 0.25 + (double)((float)this.worldObj.difficultySetting.getDifficultyId() * 0.11f));
        if (var4 > 0) {
            var3.setDamage(var3.getDamage() + (double)var4 * 0.5 + 0.5);
        }
        if (var5 > 0) {
            var3.setKnockbackStrength(var5);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1) {
            var3.setFire(100);
        }
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(var3);
    }

    public int getSkeletonType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }

    public void setSkeletonType(int par1) {
        this.dataWatcher.updateObject(13, Byte.valueOf((byte)par1));
        boolean bl = this.isImmuneToFire = par1 == 1;
        if (par1 == 1) {
            this.setSize(0.72f, 2.34f);
        } else {
            this.setSize(0.6f, 1.8f);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.func_150297_b("SkeletonType", 99)) {
            byte var2 = par1NBTTagCompound.getByte("SkeletonType");
            this.setSkeletonType(var2);
        }
        this.setCombatTask();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }

    @Override
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
        super.setCurrentItemOrArmor(par1, par2ItemStack);
        if (!this.worldObj.isClient && par1 == 0) {
            this.setCombatTask();
        }
    }

    @Override
    public double getYOffset() {
        return super.getYOffset() - 0.5;
    }
}

