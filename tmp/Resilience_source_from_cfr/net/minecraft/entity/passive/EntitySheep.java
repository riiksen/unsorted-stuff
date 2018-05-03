/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIEatGrass;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySheep
extends EntityAnimal {
    private final InventoryCrafting field_90016_e;
    public static final float[][] fleeceColorTable = new float[][]{{1.0f, 1.0f, 1.0f}, {0.85f, 0.5f, 0.2f}, {0.7f, 0.3f, 0.85f}, {0.4f, 0.6f, 0.85f}, {0.9f, 0.9f, 0.2f}, {0.5f, 0.8f, 0.1f}, {0.95f, 0.5f, 0.65f}, {0.3f, 0.3f, 0.3f}, {0.6f, 0.6f, 0.6f}, {0.3f, 0.5f, 0.6f}, {0.5f, 0.25f, 0.7f}, {0.2f, 0.3f, 0.7f}, {0.4f, 0.3f, 0.2f}, {0.4f, 0.5f, 0.2f}, {0.6f, 0.2f, 0.2f}, {0.1f, 0.1f, 0.1f}};
    private int sheepTimer;
    private EntityAIEatGrass field_146087_bs;
    private static final String __OBFID = "CL_00001648";

    public EntitySheep(World par1World) {
        super(par1World);
        this.field_90016_e = new InventoryCrafting(new Container(){
            private static final String __OBFID = "CL_00001649";

            @Override
            public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
                return false;
            }
        }, 2, 1);
        this.field_146087_bs = new EntityAIEatGrass(this);
        this.setSize(0.9f, 1.3f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, this.field_146087_bs);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.field_90016_e.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.field_90016_e.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.field_146087_bs.func_151499_f();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isClient) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        if (!this.getSheared()) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor()), 0.0f);
        }
    }

    @Override
    protected Item func_146068_u() {
        return Item.getItemFromBlock(Blocks.wool);
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    public float func_70894_j(float par1) {
        return this.sheepTimer <= 0 ? 0.0f : (this.sheepTimer >= 4 && this.sheepTimer <= 36 ? 1.0f : (this.sheepTimer < 4 ? ((float)this.sheepTimer - par1) / 4.0f : (- (float)(this.sheepTimer - 40) - par1) / 4.0f));
    }

    public float func_70890_k(float par1) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float var2 = ((float)(this.sheepTimer - 4) - par1) / 32.0f;
            return 0.62831855f + 0.2199115f * MathHelper.sin(var2 * 28.7f);
        }
        return this.sheepTimer > 0 ? 0.62831855f : this.rotationPitch / 57.295776f;
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.shears && !this.getSheared() && !this.isChild()) {
            if (!this.worldObj.isClient) {
                this.setSheared(true);
                int var3 = 1 + this.rand.nextInt(3);
                int var4 = 0;
                while (var4 < var3) {
                    EntityItem var5 = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor()), 1.0f);
                    var5.motionY += (double)(this.rand.nextFloat() * 0.05f);
                    var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                    var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                    ++var4;
                }
            }
            var2.damageItem(1, par1EntityPlayer);
            this.playSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.interact(par1EntityPlayer);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
        par1NBTTagCompound.setByte("Color", (byte)this.getFleeceColor());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
    }

    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.sheep.step", 0.15f, 1.0f);
    }

    public int getFleeceColor() {
        return this.dataWatcher.getWatchableObjectByte(16) & 15;
    }

    public void setFleeceColor(int par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | par1 & 15)));
    }

    public boolean getSheared() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 16) != 0) {
            return true;
        }
        return false;
    }

    public void setSheared(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 16)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -17)));
        }
    }

    public static int getRandomFleeceColor(Random par0Random) {
        int var1 = par0Random.nextInt(100);
        return var1 < 5 ? 15 : (var1 < 10 ? 7 : (var1 < 15 ? 8 : (var1 < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
    }

    @Override
    public EntitySheep createChild(EntityAgeable par1EntityAgeable) {
        EntitySheep var2 = (EntitySheep)par1EntityAgeable;
        EntitySheep var3 = new EntitySheep(this.worldObj);
        int var4 = this.func_90014_a(this, var2);
        var3.setFleeceColor(15 - var4);
        return var3;
    }

    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.setFleeceColor(EntitySheep.getRandomFleeceColor(this.worldObj.rand));
        return par1EntityLivingData;
    }

    private int func_90014_a(EntityAnimal par1EntityAnimal, EntityAnimal par2EntityAnimal) {
        int var3 = this.func_90013_b(par1EntityAnimal);
        int var4 = this.func_90013_b(par2EntityAnimal);
        this.field_90016_e.getStackInSlot(0).setItemDamage(var3);
        this.field_90016_e.getStackInSlot(1).setItemDamage(var4);
        ItemStack var5 = CraftingManager.getInstance().findMatchingRecipe(this.field_90016_e, ((EntitySheep)par1EntityAnimal).worldObj);
        int var6 = var5 != null && var5.getItem() == Items.dye ? var5.getItemDamage() : (this.worldObj.rand.nextBoolean() ? var3 : var4);
        return var6;
    }

    private int func_90013_b(EntityAnimal par1EntityAnimal) {
        return 15 - ((EntitySheep)par1EntityAnimal).getFleeceColor();
    }

}

