/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityCreeper
extends EntityMob {
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;
    private static final String __OBFID = "CL_00001684";

    public EntityCreeper(World par1World) {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public int getMaxSafePointTries() {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0f);
    }

    @Override
    protected void fall(float par1) {
        super.fall(par1);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5f);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf(-1));
        this.dataWatcher.addObject(17, Byte.valueOf(0));
        this.dataWatcher.addObject(18, Byte.valueOf(0));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            par1NBTTagCompound.setBoolean("powered", true);
        }
        par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        par1NBTTagCompound.setBoolean("ignited", this.func_146078_ca());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf(par1NBTTagCompound.getBoolean("powered") ? 1 : 0));
        if (par1NBTTagCompound.func_150297_b("Fuse", 99)) {
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");
        }
        if (par1NBTTagCompound.func_150297_b("ExplosionRadius", 99)) {
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
        }
        if (par1NBTTagCompound.getBoolean("ignited")) {
            this.func_146079_cb();
        }
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            int var1;
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.func_146078_ca()) {
                this.setCreeperState(1);
            }
            if ((var1 = this.getCreeperState()) > 0 && this.timeSinceIgnited == 0) {
                this.playSound("creeper.primed", 1.0f, 0.5f);
            }
            this.timeSinceIgnited += var1;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.func_146077_cc();
            }
        }
        super.onUpdate();
    }

    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (par1DamageSource.getEntity() instanceof EntitySkeleton) {
            int var2 = Item.getIdFromItem(Items.record_13);
            int var3 = Item.getIdFromItem(Items.record_wait);
            int var4 = var2 + this.rand.nextInt(var3 - var2 + 1);
            this.func_145779_a(Item.getItemById(var4), 1);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        return true;
    }

    public boolean getPowered() {
        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            return true;
        }
        return false;
    }

    public float getCreeperFlashIntensity(float par1) {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / (float)(this.fuseTime - 2);
    }

    @Override
    protected Item func_146068_u() {
        return Items.gunpowder;
    }

    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setCreeperState(int par1) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
        super.onStruckByLightning(par1EntityLightningBolt);
        this.dataWatcher.updateObject(17, Byte.valueOf(1));
    }

    @Override
    protected boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.flint_and_steel) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.ignite", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            par1EntityPlayer.swingItem();
            if (!this.worldObj.isClient) {
                this.func_146079_cb();
                var2.damageItem(1, par1EntityPlayer);
                return true;
            }
        }
        return super.interact(par1EntityPlayer);
    }

    private void func_146077_cc() {
        if (!this.worldObj.isClient) {
            boolean var1 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            if (this.getPowered()) {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * 2, var1);
            } else {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius, var1);
            }
            this.setDead();
        }
    }

    public boolean func_146078_ca() {
        if (this.dataWatcher.getWatchableObjectByte(18) != 0) {
            return true;
        }
        return false;
    }

    public void func_146079_cb() {
        this.dataWatcher.updateObject(18, Byte.valueOf(1));
    }
}

