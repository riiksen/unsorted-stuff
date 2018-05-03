/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf
extends EntityTameable {
    private float field_70926_e;
    private float field_70924_f;
    private boolean isShaking;
    private boolean field_70928_h;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private static final String __OBFID = "CL_00001654";

    public EntityWolf(World par1World) {
        super(par1World);
        this.setSize(0.6f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 2.0f));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
        this.setTamed(false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase) {
        super.setAttackTarget(par1EntityLivingBase);
        if (par1EntityLivingBase == null) {
            this.setAngry(false);
        } else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }

    @Override
    protected void updateAITick() {
        this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte(0));
        this.dataWatcher.addObject(20, new Byte((byte)BlockColored.func_150032_b(1)));
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.wolf.step", 0.15f, 1.0f);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
        par1NBTTagCompound.setByte("CollarColor", (byte)this.getCollarColor());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
        if (par1NBTTagCompound.func_150297_b("CollarColor", 99)) {
            this.setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
        }
    }

    @Override
    protected String getLivingSound() {
        return this.isAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) == 0 ? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0f ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected Item func_146068_u() {
        return Item.getItemById(-1);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isClient && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.worldObj.setEntityState(this, 8);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;
        this.field_70926_e = this.func_70922_bv() ? (this.field_70926_e += (1.0f - this.field_70926_e) * 0.4f) : (this.field_70926_e += (0.0f - this.field_70926_e) * 0.4f);
        if (this.func_70922_bv()) {
            this.numTicksToChaseTarget = 10;
        }
        if (this.isWet()) {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        } else if ((this.isShaking || this.field_70928_h) && this.field_70928_h) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.prevTimeWolfIsShaking >= 2.0f) {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                float var1 = (float)this.boundingBox.minY;
                int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * 3.1415927f) * 7.0f);
                int var3 = 0;
                while (var3 < var2) {
                    float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.worldObj.spawnParticle("splash", this.posX + (double)var4, var1 + 0.8f, this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
                    ++var3;
                }
            }
        }
    }

    public boolean getWolfShaking() {
        return this.isShaking;
    }

    public float getShadingWhileShaking(float par1) {
        return 0.75f + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1) / 2.0f * 0.25f;
    }

    public float getShakeAngle(float par1, float par2) {
        float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1 + par2) / 1.8f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        } else if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return MathHelper.sin(var3 * 3.1415927f) * MathHelper.sin(var3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }

    public float getInterestedAngle(float par1) {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15f * 3.1415927f;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }

    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        Entity var3 = par1DamageSource.getEntity();
        this.aiSit.setSitting(false);
        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
            par2 = (par2 + 1.0f) / 2.0f;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        int var2 = this.isTamed() ? 4 : 2;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    @Override
    public void setTamed(boolean par1) {
        super.setTamed(par1);
        if (par1) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (var2 != null) {
                int var4;
                if (var2.getItem() instanceof ItemFood) {
                    ItemFood var3 = (ItemFood)var2.getItem();
                    if (var3.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0f) {
                        if (!par1EntityPlayer.capabilities.isCreativeMode) {
                            --var2.stackSize;
                        }
                        this.heal(var3.func_150905_g(var2));
                        if (var2.stackSize <= 0) {
                            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                        }
                        return true;
                    }
                } else if (var2.getItem() == Items.dye && (var4 = BlockColored.func_150032_b(var2.getItemDamage())) != this.getCollarColor()) {
                    this.setCollarColor(var4);
                    if (!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                    }
                    return true;
                }
            }
            if (par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isClient && !this.isBreedingItem(var2)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity(null);
                this.setTarget(null);
                this.setAttackTarget(null);
            }
        } else if (var2 != null && var2.getItem() == Items.bone && !this.isAngry()) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                --var2.stackSize;
            }
            if (var2.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isClient) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setPathToEntity(null);
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0f);
                    this.setOwner(par1EntityPlayer.getCommandSenderName());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, 7);
                } else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, 6);
                }
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 8) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    public float getTailRotation() {
        return this.isAngry() ? 1.5393804f : (this.isTamed() ? (0.55f - (20.0f - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02f) * 3.1415927f : 0.62831855f);
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack == null ? false : (!(par1ItemStack.getItem() instanceof ItemFood) ? false : ((ItemFood)par1ItemStack.getItem()).isWolfsFavoriteMeat());
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    public boolean isAngry() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 2) != 0) {
            return true;
        }
        return false;
    }

    public void setAngry(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
        }
    }

    public int getCollarColor() {
        return this.dataWatcher.getWatchableObjectByte(20) & 15;
    }

    public void setCollarColor(int par1) {
        this.dataWatcher.updateObject(20, Byte.valueOf((byte)(par1 & 15)));
    }

    @Override
    public EntityWolf createChild(EntityAgeable par1EntityAgeable) {
        EntityWolf var2 = new EntityWolf(this.worldObj);
        String var3 = this.getOwnerName();
        if (var3 != null && var3.trim().length() > 0) {
            var2.setOwner(var3);
            var2.setTamed(true);
        }
        return var2;
    }

    public void func_70918_i(boolean par1) {
        if (par1) {
            this.dataWatcher.updateObject(19, Byte.valueOf(1));
        } else {
            this.dataWatcher.updateObject(19, Byte.valueOf(0));
        }
    }

    @Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal) {
        if (par1EntityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(par1EntityAnimal instanceof EntityWolf)) {
            return false;
        }
        EntityWolf var2 = (EntityWolf)par1EntityAnimal;
        return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
    }

    public boolean func_70922_bv() {
        if (this.dataWatcher.getWatchableObjectByte(19) == 1) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean canDespawn() {
        if (!this.isTamed() && this.ticksExisted > 2400) {
            return true;
        }
        return false;
    }

    @Override
    public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase) {
        if (!(par1EntityLivingBase instanceof EntityCreeper) && !(par1EntityLivingBase instanceof EntityGhast)) {
            EntityWolf var3;
            if (par1EntityLivingBase instanceof EntityWolf && (var3 = (EntityWolf)par1EntityLivingBase).isTamed() && var3.getOwner() == par2EntityLivingBase) {
                return false;
            }
            return par1EntityLivingBase instanceof EntityPlayer && par2EntityLivingBase instanceof EntityPlayer && !((EntityPlayer)par2EntityLivingBase).canAttackPlayer((EntityPlayer)par1EntityLivingBase) ? false : !(par1EntityLivingBase instanceof EntityHorse) || !((EntityHorse)par1EntityLivingBase).isTame();
        }
        return false;
    }
}

