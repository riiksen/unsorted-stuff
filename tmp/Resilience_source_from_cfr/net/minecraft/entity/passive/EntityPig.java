/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.world.World;

public class EntityPig
extends EntityAnimal {
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    private static final String __OBFID = "CL_00001647";

    public EntityPig(World par1World) {
        super(par1World);
        this.setSize(0.9f, 0.9f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3f);
        this.tasks.addTask(2, this.aiControlledByPlayer);
        this.tasks.addTask(3, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot_on_a_stick, false));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public boolean canBeSteered() {
        ItemStack var1 = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        if (var1 != null && var1.getItem() == Items.carrot_on_a_stick) {
            return true;
        }
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf(0));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Saddle", this.getSaddled());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSaddled(par1NBTTagCompound.getBoolean("Saddle"));
    }

    @Override
    protected String getLivingSound() {
        return "mob.pig.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.pig.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.pig.death";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.pig.step", 0.15f, 1.0f);
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        if (super.interact(par1EntityPlayer)) {
            return true;
        }
        if (this.getSaddled() && !this.worldObj.isClient && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer)) {
            par1EntityPlayer.mountEntity(this);
            return true;
        }
        return false;
    }

    @Override
    protected Item func_146068_u() {
        return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);
        int var4 = 0;
        while (var4 < var3) {
            if (this.isBurning()) {
                this.func_145779_a(Items.cooked_porkchop, 1);
            } else {
                this.func_145779_a(Items.porkchop, 1);
            }
            ++var4;
        }
        if (this.getSaddled()) {
            this.func_145779_a(Items.saddle, 1);
        }
    }

    public boolean getSaddled() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    public void setSaddled(boolean par1) {
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf(1));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf(0));
        }
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
        if (!this.worldObj.isClient) {
            EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
            var2.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(var2);
            this.setDead();
        }
    }

    @Override
    protected void fall(float par1) {
        super.fall(par1);
        if (par1 > 5.0f && this.riddenByEntity instanceof EntityPlayer) {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }

    @Override
    public EntityPig createChild(EntityAgeable par1EntityAgeable) {
        return new EntityPig(this.worldObj);
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack) {
        if (par1ItemStack != null && par1ItemStack.getItem() == Items.carrot) {
            return true;
        }
        return false;
    }

    public EntityAIControlledByPlayer getAIControlledByPlayer() {
        return this.aiControlledByPlayer;
    }
}

