/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer
extends EntityMinecart
implements IInventory {
    private ItemStack[] minecartContainerItems = new ItemStack[36];
    private boolean dropContentsWhenDead = true;
    private static final String __OBFID = "CL_00001674";

    public EntityMinecartContainer(World par1World) {
        super(par1World);
    }

    public EntityMinecartContainer(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    public void killMinecart(DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        int var2 = 0;
        while (var2 < this.getSizeInventory()) {
            ItemStack var3 = this.getStackInSlot(var2);
            if (var3 != null) {
                float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                float var6 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (var3.stackSize > 0) {
                    int var7 = this.rand.nextInt(21) + 10;
                    if (var7 > var3.stackSize) {
                        var7 = var3.stackSize;
                    }
                    var3.stackSize -= var7;
                    EntityItem var8 = new EntityItem(this.worldObj, this.posX + (double)var4, this.posY + (double)var5, this.posZ + (double)var6, new ItemStack(var3.getItem(), var7, var3.getItemDamage()));
                    float var9 = 0.05f;
                    var8.motionX = (float)this.rand.nextGaussian() * var9;
                    var8.motionY = (float)this.rand.nextGaussian() * var9 + 0.2f;
                    var8.motionZ = (float)this.rand.nextGaussian() * var9;
                    this.worldObj.spawnEntityInWorld(var8);
                }
            }
            ++var2;
        }
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.minecartContainerItems[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.minecartContainerItems[par1] != null) {
            if (this.minecartContainerItems[par1].stackSize <= par2) {
                ItemStack var3 = this.minecartContainerItems[par1];
                this.minecartContainerItems[par1] = null;
                return var3;
            }
            ItemStack var3 = this.minecartContainerItems[par1].splitStack(par2);
            if (this.minecartContainerItems[par1].stackSize == 0) {
                this.minecartContainerItems[par1] = null;
            }
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.minecartContainerItems[par1] != null) {
            ItemStack var2 = this.minecartContainerItems[par1];
            this.minecartContainerItems[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.minecartContainerItems[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.isDead ? false : par1EntityPlayer.getDistanceSqToEntity(this) <= 64.0;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.func_95999_t() : "container.minecart";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void travelToDimension(int par1) {
        this.dropContentsWhenDead = false;
        super.travelToDimension(par1);
    }

    @Override
    public void setDead() {
        if (this.dropContentsWhenDead) {
            int var1 = 0;
            while (var1 < this.getSizeInventory()) {
                ItemStack var2 = this.getStackInSlot(var1);
                if (var2 != null) {
                    float var3 = this.rand.nextFloat() * 0.8f + 0.1f;
                    float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                    float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                    while (var2.stackSize > 0) {
                        int var6 = this.rand.nextInt(21) + 10;
                        if (var6 > var2.stackSize) {
                            var6 = var2.stackSize;
                        }
                        var2.stackSize -= var6;
                        EntityItem var7 = new EntityItem(this.worldObj, this.posX + (double)var3, this.posY + (double)var4, this.posZ + (double)var5, new ItemStack(var2.getItem(), var6, var2.getItemDamage()));
                        if (var2.hasTagCompound()) {
                            var7.getEntityItem().setTagCompound((NBTTagCompound)var2.getTagCompound().copy());
                        }
                        float var8 = 0.05f;
                        var7.motionX = (float)this.rand.nextGaussian() * var8;
                        var7.motionY = (float)this.rand.nextGaussian() * var8 + 0.2f;
                        var7.motionZ = (float)this.rand.nextGaussian() * var8;
                        this.worldObj.spawnEntityInWorld(var7);
                    }
                }
                ++var1;
            }
        }
        super.setDead();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.minecartContainerItems.length) {
            if (this.minecartContainerItems[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.minecartContainerItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
            ++var3;
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 >= 0 && var5 < this.minecartContainerItems.length) {
                this.minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
            ++var3;
        }
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isClient) {
            par1EntityPlayer.displayGUIChest(this);
        }
        return true;
    }

    @Override
    protected void applyDrag() {
        int var1 = 15 - Container.calcRedstoneFromInventory(this);
        float var2 = 0.98f + (float)var1 * 0.001f;
        this.motionX *= (double)var2;
        this.motionY *= 0.0;
        this.motionZ *= (double)var2;
    }
}

