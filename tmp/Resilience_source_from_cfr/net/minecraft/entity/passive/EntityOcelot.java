/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityOcelot
extends EntityTameable {
    private EntityAITempt aiTempt;
    private static final String __OBFID = "CL_00001646";

    public EntityOcelot(World par1World) {
        super(par1World);
        this.setSize(0.6f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, true);
        this.tasks.addTask(3, this.aiTempt);
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0f, 0.8, 1.33));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 5.0f));
        this.tasks.addTask(6, new EntityAIOcelotSit(this, 1.33));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3f));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.8));
        this.tasks.addTask(10, new EntityAIWander(this, 0.8));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 750, false));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, Byte.valueOf(0));
    }

    @Override
    public void updateAITick() {
        if (this.getMoveHelper().isUpdating()) {
            double var1 = this.getMoveHelper().getSpeed();
            if (var1 == 0.6) {
                this.setSneaking(true);
                this.setSprinting(false);
            } else if (var1 == 1.33) {
                this.setSneaking(false);
                this.setSprinting(true);
            } else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        } else {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }

    @Override
    protected boolean canDespawn() {
        if (!this.isTamed() && this.ticksExisted > 2400) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CatType", this.getTameSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setTameSkin(par1NBTTagCompound.getInteger("CatType"));
    }

    @Override
    protected String getLivingSound() {
        return this.isTamed() ? (this.isInLove() ? "mob.cat.purr" : (this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }

    @Override
    protected String getHurtSound() {
        return "mob.cat.hitt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.cat.hitt";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected Item func_146068_u() {
        return Items.leather;
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.aiSit.setSitting(false);
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isClient && !this.isBreedingItem(var2)) {
                this.aiSit.setSitting(!this.isSitting());
            }
        } else if (this.aiTempt.isRunning() && var2 != null && var2.getItem() == Items.fish && par1EntityPlayer.getDistanceSqToEntity(this) < 9.0) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                --var2.stackSize;
            }
            if (var2.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isClient) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
                    this.setOwner(par1EntityPlayer.getCommandSenderName());
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
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
    public EntityOcelot createChild(EntityAgeable par1EntityAgeable) {
        EntityOcelot var2 = new EntityOcelot(this.worldObj);
        if (this.isTamed()) {
            var2.setOwner(this.getOwnerName());
            var2.setTamed(true);
            var2.setTameSkin(this.getTameSkin());
        }
        return var2;
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack) {
        if (par1ItemStack != null && par1ItemStack.getItem() == Items.fish) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal) {
        if (par1EntityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(par1EntityAnimal instanceof EntityOcelot)) {
            return false;
        }
        EntityOcelot var2 = (EntityOcelot)par1EntityAnimal;
        return !var2.isTamed() ? false : this.isInLove() && var2.isInLove();
    }

    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    public void setTameSkin(int par1) {
        this.dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.rand.nextInt(3) == 0) {
            return false;
        }
        if (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.boundingBox.minY);
            int var3 = MathHelper.floor_double(this.posZ);
            if (var2 < 63) {
                return false;
            }
            Block var4 = this.worldObj.getBlock(var1, var2 - 1, var3);
            if (var4 == Blocks.grass || var4.getMaterial() == Material.leaves) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCommandSenderName() {
        return this.hasCustomNameTag() ? this.getCustomNameTag() : (this.isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getCommandSenderName());
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        if (this.worldObj.rand.nextInt(7) == 0) {
            int var2 = 0;
            while (var2 < 2) {
                EntityOcelot var3 = new EntityOcelot(this.worldObj);
                var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                var3.setGrowingAge(-24000);
                this.worldObj.spawnEntityInWorld(var3);
                ++var2;
            }
        }
        return par1EntityLivingData;
    }
}

