/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.item;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityItem
extends Entity {
    private static final Logger logger = LogManager.getLogger();
    public int age;
    public int delayBeforeCanPickup;
    private int health = 5;
    private String field_145801_f;
    private String field_145802_g;
    public float hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
    private static final String __OBFID = "CL_00001669";

    public EntityItem(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }

    public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
        this(par1World, par2, par4, par6);
        this.setEntityItemStack(par8ItemStack);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityItem(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }

    @Override
    public void onUpdate() {
        if (this.getEntityItem() == null) {
            this.setDead();
        } else {
            boolean var1;
            super.onUpdate();
            if (this.delayBeforeCanPickup > 0) {
                --this.delayBeforeCanPickup;
            }
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            this.noClip = this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            boolean bl = var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;
            if (var1 || this.ticksExisted % 25 == 0) {
                if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
                    this.motionY = 0.20000000298023224;
                    this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
                }
                if (!this.worldObj.isClient) {
                    this.searchForOtherItemsNearby();
                }
            }
            float var2 = 0.98f;
            if (this.onGround) {
                var2 = this.worldObj.getBlock((int)MathHelper.floor_double((double)this.posX), (int)(MathHelper.floor_double((double)this.boundingBox.minY) - 1), (int)MathHelper.floor_double((double)this.posZ)).slipperiness * 0.98f;
            }
            this.motionX *= (double)var2;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= (double)var2;
            if (this.onGround) {
                this.motionY *= -0.5;
            }
            ++this.age;
            if (!this.worldObj.isClient && this.age >= 6000) {
                this.setDead();
            }
        }
    }

    private void searchForOtherItemsNearby() {
        for (EntityItem var2 : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5, 0.0, 0.5))) {
            this.combineItems(var2);
        }
    }

    public boolean combineItems(EntityItem par1EntityItem) {
        if (par1EntityItem == this) {
            return false;
        }
        if (par1EntityItem.isEntityAlive() && this.isEntityAlive()) {
            ItemStack var2 = this.getEntityItem();
            ItemStack var3 = par1EntityItem.getEntityItem();
            if (var3.getItem() != var2.getItem()) {
                return false;
            }
            if (var3.hasTagCompound() ^ var2.hasTagCompound()) {
                return false;
            }
            if (var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) {
                return false;
            }
            if (var3.getItem() == null) {
                return false;
            }
            if (var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage()) {
                return false;
            }
            if (var3.stackSize < var2.stackSize) {
                return par1EntityItem.combineItems(this);
            }
            if (var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
                return false;
            }
            var3.stackSize += var2.stackSize;
            par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
            par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
            par1EntityItem.setEntityItemStack(var3);
            this.setDead();
            return true;
        }
        return false;
    }

    public void setAgeToCreativeDespawnTime() {
        this.age = 4800;
    }

    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    @Override
    protected void dealFireDamage(int par1) {
        this.attackEntityFrom(DamageSource.inFire, par1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.getEntityItem() != null && this.getEntityItem().getItem() == Items.nether_star && par1DamageSource.isExplosion()) {
            return false;
        }
        this.setBeenAttacked();
        this.health = (int)((float)this.health - par2);
        if (this.health <= 0) {
            this.setDead();
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.health);
        par1NBTTagCompound.setShort("Age", (short)this.age);
        if (this.func_145800_j() != null) {
            par1NBTTagCompound.setString("Thrower", this.field_145801_f);
        }
        if (this.func_145798_i() != null) {
            par1NBTTagCompound.setString("Owner", this.field_145802_g);
        }
        if (this.getEntityItem() != null) {
            par1NBTTagCompound.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.health = par1NBTTagCompound.getShort("Health") & 255;
        this.age = par1NBTTagCompound.getShort("Age");
        if (par1NBTTagCompound.hasKey("Owner")) {
            this.field_145802_g = par1NBTTagCompound.getString("Owner");
        }
        if (par1NBTTagCompound.hasKey("Thrower")) {
            this.field_145801_f = par1NBTTagCompound.getString("Thrower");
        }
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
        if (this.getEntityItem() == null) {
            this.setDead();
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isClient) {
            ItemStack var2 = this.getEntityItem();
            int var3 = var2.stackSize;
            if (this.delayBeforeCanPickup == 0 && (this.field_145802_g == null || 6000 - this.age <= 200 || this.field_145802_g.equals(par1EntityPlayer.getCommandSenderName())) && par1EntityPlayer.inventory.addItemStackToInventory(var2)) {
                EntityPlayer var4;
                if (var2.getItem() == Item.getItemFromBlock(Blocks.log)) {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (var2.getItem() == Item.getItemFromBlock(Blocks.log2)) {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (var2.getItem() == Items.leather) {
                    par1EntityPlayer.triggerAchievement(AchievementList.killCow);
                }
                if (var2.getItem() == Items.diamond) {
                    par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
                }
                if (var2.getItem() == Items.blaze_rod) {
                    par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
                }
                if (var2.getItem() == Items.diamond && this.func_145800_j() != null && (var4 = this.worldObj.getPlayerEntityByName(this.func_145800_j())) != null && var4 != par1EntityPlayer) {
                    var4.triggerAchievement(AchievementList.field_150966_x);
                }
                this.worldObj.playSoundAtEntity(par1EntityPlayer, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                par1EntityPlayer.onItemPickup(this, var3);
                if (var2.stackSize <= 0) {
                    this.setDead();
                }
            }
        }
    }

    @Override
    public String getCommandSenderName() {
        return StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    public void travelToDimension(int par1) {
        super.travelToDimension(par1);
        if (!this.worldObj.isClient) {
            this.searchForOtherItemsNearby();
        }
    }

    public ItemStack getEntityItem() {
        ItemStack var1 = this.getDataWatcher().getWatchableObjectItemStack(10);
        if (var1 == null) {
            if (this.worldObj != null) {
                logger.error("Item entity " + this.getEntityId() + " has no item?!");
            }
            return new ItemStack(Blocks.stone);
        }
        return var1;
    }

    public void setEntityItemStack(ItemStack par1ItemStack) {
        this.getDataWatcher().updateObject(10, par1ItemStack);
        this.getDataWatcher().setObjectWatched(10);
    }

    public String func_145798_i() {
        return this.field_145802_g;
    }

    public void func_145797_a(String p_145797_1_) {
        this.field_145802_g = p_145797_1_;
    }

    public String func_145800_j() {
        return this.field_145801_f;
    }

    public void func_145799_b(String p_145799_1_) {
        this.field_145801_f = p_145799_1_;
    }
}

