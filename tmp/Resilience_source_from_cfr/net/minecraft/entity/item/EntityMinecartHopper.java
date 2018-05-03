/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartHopper
extends EntityMinecartContainer
implements IHopper {
    private boolean isBlocked = true;
    private int transferTicker = -1;
    private static final String __OBFID = "CL_00001676";

    public EntityMinecartHopper(World par1World) {
        super(par1World);
    }

    public EntityMinecartHopper(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    public int getMinecartType() {
        return 5;
    }

    @Override
    public Block func_145817_o() {
        return Blocks.hopper;
    }

    @Override
    public int getDefaultDisplayTileOffset() {
        return 1;
    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isClient) {
            par1EntityPlayer.displayGUIHopperMinecart(this);
        }
        return true;
    }

    @Override
    public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {
        boolean var5;
        boolean bl = var5 = !par4;
        if (var5 != this.getBlocked()) {
            this.setBlocked(var5);
        }
    }

    public boolean getBlocked() {
        return this.isBlocked;
    }

    public void setBlocked(boolean par1) {
        this.isBlocked = par1;
    }

    @Override
    public World getWorldObj() {
        return this.worldObj;
    }

    @Override
    public double getXPos() {
        return this.posX;
    }

    @Override
    public double getYPos() {
        return this.posY;
    }

    @Override
    public double getZPos() {
        return this.posZ;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient && this.isEntityAlive() && this.getBlocked()) {
            --this.transferTicker;
            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.func_96112_aD()) {
                    this.setTransferTicker(4);
                    this.onInventoryChanged();
                }
            }
        }
    }

    public boolean func_96112_aD() {
        if (TileEntityHopper.func_145891_a(this)) {
            return true;
        }
        List var1 = this.worldObj.selectEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.25, 0.0, 0.25), IEntitySelector.selectAnything);
        if (var1.size() > 0) {
            TileEntityHopper.func_145898_a(this, (EntityItem)var1.get(0));
        }
        return false;
    }

    @Override
    public void killMinecart(DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        this.func_145778_a(Item.getItemFromBlock(Blocks.hopper), 1, 0.0f);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("TransferCooldown", this.transferTicker);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.transferTicker = par1NBTTagCompound.getInteger("TransferCooldown");
    }

    public void setTransferTicker(int par1) {
        this.transferTicker = par1;
    }

    public boolean canTransfer() {
        if (this.transferTicker > 0) {
            return true;
        }
        return false;
    }
}

