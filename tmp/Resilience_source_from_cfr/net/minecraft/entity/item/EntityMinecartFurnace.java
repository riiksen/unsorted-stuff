/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace
extends EntityMinecart {
    private int fuel;
    public double pushX;
    public double pushZ;
    private static final String __OBFID = "CL_00001675";

    public EntityMinecartFurnace(World par1World) {
        super(par1World);
    }

    public EntityMinecartFurnace(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    public int getMinecartType() {
        return 2;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            --this.fuel;
        }
        if (this.fuel <= 0) {
            this.pushZ = 0.0;
            this.pushX = 0.0;
        }
        this.setMinecartPowered(this.fuel > 0);
        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void killMinecart(DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        if (!par1DamageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0f);
        }
    }

    @Override
    protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_) {
        super.func_145821_a(p_145821_1_, p_145821_2_, p_145821_3_, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
        double var10 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var10 > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            var10 = MathHelper.sqrt_double(var10);
            this.pushX /= var10;
            this.pushZ /= var10;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
            } else {
                this.pushX = this.motionX;
                this.pushZ = this.motionZ;
            }
        }
    }

    @Override
    protected void applyDrag() {
        double var1 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var1 > 1.0E-4) {
            var1 = MathHelper.sqrt_double(var1);
            this.pushX /= var1;
            this.pushZ /= var1;
            double var3 = 0.05;
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.0;
            this.motionZ *= 0.800000011920929;
            this.motionX += this.pushX * var3;
            this.motionZ += this.pushZ * var3;
        } else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.0;
            this.motionZ *= 0.9800000190734863;
        }
        super.applyDrag();
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.coal) {
            if (!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize == 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            this.fuel += 3600;
        }
        this.pushX = this.posX - par1EntityPlayer.posX;
        this.pushZ = this.posZ - par1EntityPlayer.posZ;
        return true;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setDouble("PushX", this.pushX);
        par1NBTTagCompound.setDouble("PushZ", this.pushZ);
        par1NBTTagCompound.setShort("Fuel", (short)this.fuel);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.pushX = par1NBTTagCompound.getDouble("PushX");
        this.pushZ = par1NBTTagCompound.getDouble("PushZ");
        this.fuel = par1NBTTagCompound.getShort("Fuel");
    }

    protected boolean isMinecartPowered() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    protected void setMinecartPowered(boolean par1) {
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) | 1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) & -2)));
        }
    }

    @Override
    public Block func_145817_o() {
        return Blocks.lit_furnace;
    }

    @Override
    public int getDefaultDisplayTileData() {
        return 2;
    }
}

