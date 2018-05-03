/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.profiler.Profiler;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLiving
extends EntityLivingBase {
    public int livingSoundTime;
    protected int experienceValue;
    private EntityLookHelper lookHelper;
    private EntityMoveHelper moveHelper;
    private EntityJumpHelper jumpHelper;
    private EntityBodyHelper bodyHelper;
    private PathNavigate navigator;
    protected final EntityAITasks tasks;
    protected final EntityAITasks targetTasks;
    private EntityLivingBase attackTarget;
    private EntitySenses senses;
    private ItemStack[] equipment = new ItemStack[5];
    protected float[] equipmentDropChances = new float[5];
    private boolean canPickUpLoot;
    private boolean persistenceRequired;
    protected float defaultPitch;
    private Entity currentTarget;
    protected int numTicksToChaseTarget;
    private boolean isLeashed;
    private Entity leashedToEntity;
    private NBTTagCompound field_110170_bx;
    private static final String __OBFID = "CL_00001550";

    public EntityLiving(World par1World) {
        super(par1World);
        this.tasks = new EntityAITasks(par1World != null && par1World.theProfiler != null ? par1World.theProfiler : null);
        this.targetTasks = new EntityAITasks(par1World != null && par1World.theProfiler != null ? par1World.theProfiler : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = new PathNavigate(this, par1World);
        this.senses = new EntitySenses(this);
        int var2 = 0;
        while (var2 < this.equipmentDropChances.length) {
            this.equipmentDropChances[var2] = 0.085f;
            ++var2;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
    }

    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }

    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }

    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }

    public PathNavigate getNavigator() {
        return this.navigator;
    }

    public EntitySenses getEntitySenses() {
        return this.senses;
    }

    public EntityLivingBase getAttackTarget() {
        return this.attackTarget;
    }

    public void setAttackTarget(EntityLivingBase par1EntityLivingBase) {
        this.attackTarget = par1EntityLivingBase;
    }

    public boolean canAttackClass(Class par1Class) {
        if (EntityCreeper.class != par1Class && EntityGhast.class != par1Class) {
            return true;
        }
        return false;
    }

    public void eatGrassBonus() {
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(11, Byte.valueOf(0));
        this.dataWatcher.addObject(10, "");
    }

    public int getTalkInterval() {
        return 80;
    }

    public void playLivingSound() {
        String var1 = this.getLivingSound();
        if (var1 != null) {
            this.playSound(var1, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection("mobBaseTick");
        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = - this.getTalkInterval();
            this.playLivingSound();
        }
        this.worldObj.theProfiler.endSection();
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
        if (this.experienceValue > 0) {
            int var2 = this.experienceValue;
            ItemStack[] var3 = this.getLastActiveItems();
            int var4 = 0;
            while (var4 < var3.length) {
                if (var3[var4] != null && this.equipmentDropChances[var4] <= 1.0f) {
                    var2 += 1 + this.rand.nextInt(3);
                }
                ++var4;
            }
            return var2;
        }
        return this.experienceValue;
    }

    public void spawnExplosionParticle() {
        int var1 = 0;
        while (var1 < 20) {
            double var2 = this.rand.nextGaussian() * 0.02;
            double var4 = this.rand.nextGaussian() * 0.02;
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = 10.0;
            this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width - var2 * var8, this.posY + (double)(this.rand.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width - var6 * var8, var2, var4, var6);
            ++var1;
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient) {
            this.updateLeashedState();
        }
    }

    @Override
    protected float func_110146_f(float par1, float par2) {
        if (this.isAIEnabled()) {
            this.bodyHelper.func_75664_a();
            return par2;
        }
        return super.func_110146_f(par1, par2);
    }

    protected String getLivingSound() {
        return null;
    }

    protected Item func_146068_u() {
        return Item.getItemById(0);
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        Item var3 = this.func_146068_u();
        if (var3 != null) {
            int var4 = this.rand.nextInt(3);
            if (par2 > 0) {
                var4 += this.rand.nextInt(par2 + 1);
            }
            int var5 = 0;
            while (var5 < var4) {
                this.func_145779_a(var3, 1);
                ++var5;
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        NBTTagCompound var4;
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        par1NBTTagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.equipment.length) {
            var4 = new NBTTagCompound();
            if (this.equipment[var3] != null) {
                this.equipment[var3].writeToNBT(var4);
            }
            var2.appendTag(var4);
            ++var3;
        }
        par1NBTTagCompound.setTag("Equipment", var2);
        NBTTagList var6 = new NBTTagList();
        int var7 = 0;
        while (var7 < this.equipmentDropChances.length) {
            var6.appendTag(new NBTTagFloat(this.equipmentDropChances[var7]));
            ++var7;
        }
        par1NBTTagCompound.setTag("DropChances", var6);
        par1NBTTagCompound.setString("CustomName", this.getCustomNameTag());
        par1NBTTagCompound.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
        par1NBTTagCompound.setBoolean("Leashed", this.isLeashed);
        if (this.leashedToEntity != null) {
            var4 = new NBTTagCompound();
            if (this.leashedToEntity instanceof EntityLivingBase) {
                var4.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
                var4.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
            } else if (this.leashedToEntity instanceof EntityHanging) {
                EntityHanging var5 = (EntityHanging)this.leashedToEntity;
                var4.setInteger("X", var5.field_146063_b);
                var4.setInteger("Y", var5.field_146064_c);
                var4.setInteger("Z", var5.field_146062_d);
            }
            par1NBTTagCompound.setTag("Leash", var4);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        NBTTagList var2;
        int var3;
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setCanPickUpLoot(par1NBTTagCompound.getBoolean("CanPickUpLoot"));
        this.persistenceRequired = par1NBTTagCompound.getBoolean("PersistenceRequired");
        if (par1NBTTagCompound.func_150297_b("CustomName", 8) && par1NBTTagCompound.getString("CustomName").length() > 0) {
            this.setCustomNameTag(par1NBTTagCompound.getString("CustomName"));
        }
        this.setAlwaysRenderNameTag(par1NBTTagCompound.getBoolean("CustomNameVisible"));
        if (par1NBTTagCompound.func_150297_b("Equipment", 9)) {
            var2 = par1NBTTagCompound.getTagList("Equipment", 10);
            var3 = 0;
            while (var3 < this.equipment.length) {
                this.equipment[var3] = ItemStack.loadItemStackFromNBT(var2.getCompoundTagAt(var3));
                ++var3;
            }
        }
        if (par1NBTTagCompound.func_150297_b("DropChances", 9)) {
            var2 = par1NBTTagCompound.getTagList("DropChances", 5);
            var3 = 0;
            while (var3 < var2.tagCount()) {
                this.equipmentDropChances[var3] = var2.func_150308_e(var3);
                ++var3;
            }
        }
        this.isLeashed = par1NBTTagCompound.getBoolean("Leashed");
        if (this.isLeashed && par1NBTTagCompound.func_150297_b("Leash", 10)) {
            this.field_110170_bx = par1NBTTagCompound.getCompoundTag("Leash");
        }
    }

    public void setMoveForward(float par1) {
        this.moveForward = par1;
    }

    @Override
    public void setAIMoveSpeed(float par1) {
        super.setAIMoveSpeed(par1);
        this.setMoveForward(par1);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.worldObj.theProfiler.startSection("looting");
        if (!this.worldObj.isClient && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            List var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0, 0.0, 1.0));
            for (EntityItem var3 : var1) {
                int var5;
                EntityPlayer var10;
                ItemStack var4;
                if (var3.isDead || var3.getEntityItem() == null || (var5 = EntityLiving.getArmorPosition(var4 = var3.getEntityItem())) <= -1) continue;
                boolean var6 = true;
                ItemStack var7 = this.getEquipmentInSlot(var5);
                if (var7 != null) {
                    if (var5 == 0) {
                        if (var4.getItem() instanceof ItemSword && !(var7.getItem() instanceof ItemSword)) {
                            var6 = true;
                        } else if (var4.getItem() instanceof ItemSword && var7.getItem() instanceof ItemSword) {
                            ItemSword var8 = (ItemSword)var4.getItem();
                            ItemSword var9 = (ItemSword)var7.getItem();
                            var6 = var8.func_150931_i() == var9.func_150931_i() ? var4.getItemDamage() > var7.getItemDamage() || var4.hasTagCompound() && !var7.hasTagCompound() : var8.func_150931_i() > var9.func_150931_i();
                        } else {
                            var6 = false;
                        }
                    } else if (var4.getItem() instanceof ItemArmor && !(var7.getItem() instanceof ItemArmor)) {
                        var6 = true;
                    } else if (var4.getItem() instanceof ItemArmor && var7.getItem() instanceof ItemArmor) {
                        ItemArmor var11 = (ItemArmor)var4.getItem();
                        ItemArmor var12 = (ItemArmor)var7.getItem();
                        var6 = var11.damageReduceAmount == var12.damageReduceAmount ? var4.getItemDamage() > var7.getItemDamage() || var4.hasTagCompound() && !var7.hasTagCompound() : var11.damageReduceAmount > var12.damageReduceAmount;
                    } else {
                        var6 = false;
                    }
                }
                if (!var6) continue;
                if (var7 != null && this.rand.nextFloat() - 0.1f < this.equipmentDropChances[var5]) {
                    this.entityDropItem(var7, 0.0f);
                }
                if (var4.getItem() == Items.diamond && var3.func_145800_j() != null && (var10 = this.worldObj.getPlayerEntityByName(var3.func_145800_j())) != null) {
                    var10.triggerAchievement(AchievementList.field_150966_x);
                }
                this.setCurrentItemOrArmor(var5, var4);
                this.equipmentDropChances[var5] = 2.0f;
                this.persistenceRequired = true;
                this.onItemPickup(var3, 1);
                var3.setDead();
            }
        }
        this.worldObj.theProfiler.endSection();
    }

    @Override
    protected boolean isAIEnabled() {
        return false;
    }

    protected boolean canDespawn() {
        return true;
    }

    public void despawnEntity() {
        if (this.persistenceRequired) {
            this.entityAge = 0;
        } else {
            EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0);
            if (var1 != null) {
                double var2 = var1.posX - this.posX;
                double var4 = var1.posY - this.posY;
                double var6 = var1.posZ - this.posZ;
                double var8 = var2 * var2 + var4 * var4 + var6 * var6;
                if (this.canDespawn() && var8 > 16384.0) {
                    this.setDead();
                }
                if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && var8 > 1024.0 && this.canDespawn()) {
                    this.setDead();
                } else if (var8 < 1024.0) {
                    this.entityAge = 0;
                }
            }
        }
    }

    @Override
    protected void updateAITasks() {
        ++this.entityAge;
        this.worldObj.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("mob tick");
        this.updateAITick();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("controls");
        this.worldObj.theProfiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.worldObj.theProfiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }

    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.moveStrafing = 0.0f;
        this.moveForward = 0.0f;
        this.despawnEntity();
        float var1 = 8.0f;
        if (this.rand.nextFloat() < 0.02f) {
            EntityPlayer var2 = this.worldObj.getClosestPlayerToEntity(this, var1);
            if (var2 != null) {
                this.currentTarget = var2;
                this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
            } else {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.currentTarget != null) {
            this.faceEntity(this.currentTarget, 10.0f, this.getVerticalFaceSpeed());
            if (this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > (double)(var1 * var1)) {
                this.currentTarget = null;
            }
        } else {
            if (this.rand.nextFloat() < 0.05f) {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
            this.rotationYaw += this.randomYawVelocity;
            this.rotationPitch = this.defaultPitch;
        }
        boolean var4 = this.isInWater();
        boolean var3 = this.handleLavaMovement();
        if (var4 || var3) {
            this.isJumping = this.rand.nextFloat() < 0.8f;
        }
    }

    public int getVerticalFaceSpeed() {
        return 40;
    }

    public void faceEntity(Entity par1Entity, float par2, float par3) {
        double var6;
        double var4 = par1Entity.posX - this.posX;
        double var8 = par1Entity.posZ - this.posZ;
        if (par1Entity instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase)par1Entity;
            var6 = var10.posY + (double)var10.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        } else {
            var6 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0 - (this.posY + (double)this.getEyeHeight());
        }
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)(- Math.atan2(var6, var14) * 180.0 / 3.141592653589793);
        this.rotationPitch = this.updateRotation(this.rotationPitch, var13, par3);
        this.rotationYaw = this.updateRotation(this.rotationYaw, var12, par2);
    }

    private float updateRotation(float par1, float par2, float par3) {
        float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
        if (var4 > par3) {
            var4 = par3;
        }
        if (var4 < - par3) {
            var4 = - par3;
        }
        return par1 + var4;
    }

    public boolean getCanSpawnHere() {
        if (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            return true;
        }
        return false;
    }

    public float getRenderSizeModifier() {
        return 1.0f;
    }

    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    public int getMaxSafePointTries() {
        if (this.getAttackTarget() == null) {
            return 3;
        }
        int var1 = (int)(this.getHealth() - this.getMaxHealth() * 0.33f);
        if ((var1 -= (3 - this.worldObj.difficultySetting.getDifficultyId()) * 4) < 0) {
            var1 = 0;
        }
        return var1 + 3;
    }

    @Override
    public ItemStack getHeldItem() {
        return this.equipment[0];
    }

    @Override
    public ItemStack getEquipmentInSlot(int par1) {
        return this.equipment[par1];
    }

    public ItemStack func_130225_q(int par1) {
        return this.equipment[par1 + 1];
    }

    @Override
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
        this.equipment[par1] = par2ItemStack;
    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return this.equipment;
    }

    @Override
    protected void dropEquipment(boolean par1, int par2) {
        int var3 = 0;
        while (var3 < this.getLastActiveItems().length) {
            boolean var5;
            ItemStack var4 = this.getEquipmentInSlot(var3);
            boolean bl = var5 = this.equipmentDropChances[var3] > 1.0f;
            if (var4 != null && (par1 || var5) && this.rand.nextFloat() - (float)par2 * 0.01f < this.equipmentDropChances[var3]) {
                if (!var5 && var4.isItemStackDamageable()) {
                    int var6 = Math.max(var4.getMaxDamage() - 25, 1);
                    int var7 = var4.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var6) + 1);
                    if (var7 > var6) {
                        var7 = var6;
                    }
                    if (var7 < 1) {
                        var7 = 1;
                    }
                    var4.setItemDamage(var7);
                }
                this.entityDropItem(var4, 0.0f);
            }
            ++var3;
        }
    }

    protected void addRandomArmor() {
        if (this.rand.nextFloat() < 0.15f * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ)) {
            float var2;
            int var1 = this.rand.nextInt(2);
            float f = var2 = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.1f : 0.25f;
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            int var3 = 3;
            while (var3 >= 0) {
                Item var5;
                ItemStack var4 = this.func_130225_q(var3);
                if (var3 < 3 && this.rand.nextFloat() < var2) break;
                if (var4 == null && (var5 = EntityLiving.getArmorItemForSlot(var3 + 1, var1)) != null) {
                    this.setCurrentItemOrArmor(var3 + 1, new ItemStack(var5));
                }
                --var3;
            }
        }
    }

    public static int getArmorPosition(ItemStack par0ItemStack) {
        if (par0ItemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && par0ItemStack.getItem() != Items.skull) {
            if (par0ItemStack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)par0ItemStack.getItem()).armorType) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }

    public static Item getArmorItemForSlot(int par0, int par1) {
        switch (par0) {
            case 4: {
                if (par1 == 0) {
                    return Items.leather_helmet;
                }
                if (par1 == 1) {
                    return Items.golden_helmet;
                }
                if (par1 == 2) {
                    return Items.chainmail_helmet;
                }
                if (par1 == 3) {
                    return Items.iron_helmet;
                }
                if (par1 == 4) {
                    return Items.diamond_helmet;
                }
            }
            case 3: {
                if (par1 == 0) {
                    return Items.leather_chestplate;
                }
                if (par1 == 1) {
                    return Items.golden_chestplate;
                }
                if (par1 == 2) {
                    return Items.chainmail_chestplate;
                }
                if (par1 == 3) {
                    return Items.iron_chestplate;
                }
                if (par1 == 4) {
                    return Items.diamond_chestplate;
                }
            }
            case 2: {
                if (par1 == 0) {
                    return Items.leather_leggings;
                }
                if (par1 == 1) {
                    return Items.golden_leggings;
                }
                if (par1 == 2) {
                    return Items.chainmail_leggings;
                }
                if (par1 == 3) {
                    return Items.iron_leggings;
                }
                if (par1 == 4) {
                    return Items.diamond_leggings;
                }
            }
            case 1: {
                if (par1 == 0) {
                    return Items.leather_boots;
                }
                if (par1 == 1) {
                    return Items.golden_boots;
                }
                if (par1 == 2) {
                    return Items.chainmail_boots;
                }
                if (par1 == 3) {
                    return Items.iron_boots;
                }
                if (par1 != 4) break;
                return Items.diamond_boots;
            }
        }
        return null;
    }

    protected void enchantEquipment() {
        float var1 = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25f * var1) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0f + var1 * (float)this.rand.nextInt(18)));
        }
        int var2 = 0;
        while (var2 < 4) {
            ItemStack var3 = this.func_130225_q(var2);
            if (var3 != null && this.rand.nextFloat() < 0.5f * var1) {
                EnchantmentHelper.addRandomEnchantment(this.rand, var3, (int)(5.0f + var1 * (float)this.rand.nextInt(18)));
            }
            ++var2;
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05, 1));
        return par1EntityLivingData;
    }

    public boolean canBeSteered() {
        return false;
    }

    @Override
    public String getCommandSenderName() {
        return this.hasCustomNameTag() ? this.getCustomNameTag() : super.getCommandSenderName();
    }

    public void func_110163_bv() {
        this.persistenceRequired = true;
    }

    public void setCustomNameTag(String par1Str) {
        this.dataWatcher.updateObject(10, par1Str);
    }

    public String getCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString(10);
    }

    public boolean hasCustomNameTag() {
        if (this.dataWatcher.getWatchableObjectString(10).length() > 0) {
            return true;
        }
        return false;
    }

    public void setAlwaysRenderNameTag(boolean par1) {
        this.dataWatcher.updateObject(11, Byte.valueOf(par1 ? 1 : 0));
    }

    public boolean getAlwaysRenderNameTag() {
        if (this.dataWatcher.getWatchableObjectByte(11) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }

    public void setEquipmentDropChance(int par1, float par2) {
        this.equipmentDropChances[par1] = par2;
    }

    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }

    public void setCanPickUpLoot(boolean par1) {
        this.canPickUpLoot = par1;
    }

    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }

    @Override
    public final boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.getLeashed() && this.getLeashedToEntity() == par1EntityPlayer) {
            this.clearLeashed(true, !par1EntityPlayer.capabilities.isCreativeMode);
            return true;
        }
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.lead && this.allowLeashing()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
                this.setLeashedToEntity(par1EntityPlayer, true);
                --var2.stackSize;
                return true;
            }
            if (par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(((EntityTameable)this).getOwnerName())) {
                this.setLeashedToEntity(par1EntityPlayer, true);
                --var2.stackSize;
                return true;
            }
        }
        return this.interact(par1EntityPlayer) ? true : super.interactFirst(par1EntityPlayer);
    }

    protected boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    protected void updateLeashedState() {
        if (this.field_110170_bx != null) {
            this.recreateLeash();
        }
        if (this.isLeashed && (this.leashedToEntity == null || this.leashedToEntity.isDead)) {
            this.clearLeashed(true, true);
        }
    }

    public void clearLeashed(boolean par1, boolean par2) {
        if (this.isLeashed) {
            this.isLeashed = false;
            this.leashedToEntity = null;
            if (!this.worldObj.isClient && par2) {
                this.func_145779_a(Items.lead, 1);
            }
            if (!this.worldObj.isClient && par1 && this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S1BPacketEntityAttach(1, this, null));
            }
        }
    }

    public boolean allowLeashing() {
        if (!this.getLeashed() && !(this instanceof IMob)) {
            return true;
        }
        return false;
    }

    public boolean getLeashed() {
        return this.isLeashed;
    }

    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }

    public void setLeashedToEntity(Entity par1Entity, boolean par2) {
        this.isLeashed = true;
        this.leashedToEntity = par1Entity;
        if (!this.worldObj.isClient && par2 && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
        }
    }

    private void recreateLeash() {
        if (this.isLeashed && this.field_110170_bx != null) {
            if (this.field_110170_bx.func_150297_b("UUIDMost", 4) && this.field_110170_bx.func_150297_b("UUIDLeast", 4)) {
                UUID var5 = new UUID(this.field_110170_bx.getLong("UUIDMost"), this.field_110170_bx.getLong("UUIDLeast"));
                List var6 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 10.0, 10.0));
                for (EntityLivingBase var8 : var6) {
                    if (!var8.getUniqueID().equals(var5)) continue;
                    this.leashedToEntity = var8;
                    break;
                }
            } else if (this.field_110170_bx.func_150297_b("X", 99) && this.field_110170_bx.func_150297_b("Y", 99) && this.field_110170_bx.func_150297_b("Z", 99)) {
                int var3;
                int var2;
                int var1 = this.field_110170_bx.getInteger("X");
                EntityLeashKnot var4 = EntityLeashKnot.getKnotForBlock(this.worldObj, var1, var2 = this.field_110170_bx.getInteger("Y"), var3 = this.field_110170_bx.getInteger("Z"));
                if (var4 == null) {
                    var4 = EntityLeashKnot.func_110129_a(this.worldObj, var1, var2, var3);
                }
                this.leashedToEntity = var4;
            } else {
                this.clearLeashed(false, true);
            }
        }
        this.field_110170_bx = null;
    }
}

