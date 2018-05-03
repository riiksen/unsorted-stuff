/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityChest
extends TileEntity
implements IInventory {
    private ItemStack[] field_145985_p = new ItemStack[36];
    public boolean field_145984_a;
    public TileEntityChest field_145992_i;
    public TileEntityChest field_145990_j;
    public TileEntityChest field_145991_k;
    public TileEntityChest field_145988_l;
    public float field_145989_m;
    public float field_145986_n;
    public int field_145987_o;
    private int field_145983_q;
    private int field_145982_r;
    private String field_145981_s;
    private static final String __OBFID = "CL_00000346";

    public TileEntityChest() {
        this.field_145982_r = -1;
    }

    public TileEntityChest(int par1) {
        this.field_145982_r = par1;
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.field_145985_p[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.field_145985_p[par1] != null) {
            if (this.field_145985_p[par1].stackSize <= par2) {
                ItemStack var3 = this.field_145985_p[par1];
                this.field_145985_p[par1] = null;
                this.onInventoryChanged();
                return var3;
            }
            ItemStack var3 = this.field_145985_p[par1].splitStack(par2);
            if (this.field_145985_p[par1].stackSize == 0) {
                this.field_145985_p[par1] = null;
            }
            this.onInventoryChanged();
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.field_145985_p[par1] != null) {
            ItemStack var2 = this.field_145985_p[par1];
            this.field_145985_p[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.field_145985_p[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145981_s : "container.chest";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        if (this.field_145981_s != null && this.field_145981_s.length() > 0) {
            return true;
        }
        return false;
    }

    public void func_145976_a(String p_145976_1_) {
        this.field_145981_s = p_145976_1_;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145985_p = new ItemStack[this.getSizeInventory()];
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145981_s = p_145839_1_.getString("CustomName");
        }
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 >= 0 && var5 < this.field_145985_p.length) {
                this.field_145985_p[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
            ++var3;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.field_145985_p.length) {
            if (this.field_145985_p[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145985_p[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
            ++var3;
        }
        p_145841_1_.setTag("Items", var2);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145981_s);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this ? false : par1EntityPlayer.getDistanceSq((double)this.field_145851_c + 0.5, (double)this.field_145848_d + 0.5, (double)this.field_145849_e + 0.5) <= 64.0;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.field_145984_a = false;
    }

    private void func_145978_a(TileEntityChest p_145978_1_, int p_145978_2_) {
        if (p_145978_1_.isInvalid()) {
            this.field_145984_a = false;
        } else if (this.field_145984_a) {
            switch (p_145978_2_) {
                case 0: {
                    if (this.field_145988_l == p_145978_1_) break;
                    this.field_145984_a = false;
                    break;
                }
                case 1: {
                    if (this.field_145991_k == p_145978_1_) break;
                    this.field_145984_a = false;
                    break;
                }
                case 2: {
                    if (this.field_145992_i == p_145978_1_) break;
                    this.field_145984_a = false;
                    break;
                }
                case 3: {
                    if (this.field_145990_j == p_145978_1_) break;
                    this.field_145984_a = false;
                }
            }
        }
    }

    public void func_145979_i() {
        if (!this.field_145984_a) {
            this.field_145984_a = true;
            this.field_145992_i = null;
            this.field_145990_j = null;
            this.field_145991_k = null;
            this.field_145988_l = null;
            if (this.func_145977_a(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e)) {
                this.field_145991_k = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
            }
            if (this.func_145977_a(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e)) {
                this.field_145990_j = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
            }
            if (this.func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1)) {
                this.field_145992_i = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
            }
            if (this.func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1)) {
                this.field_145988_l = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
            }
            if (this.field_145992_i != null) {
                this.field_145992_i.func_145978_a(this, 0);
            }
            if (this.field_145988_l != null) {
                this.field_145988_l.func_145978_a(this, 2);
            }
            if (this.field_145990_j != null) {
                this.field_145990_j.func_145978_a(this, 1);
            }
            if (this.field_145991_k != null) {
                this.field_145991_k.func_145978_a(this, 3);
            }
        }
    }

    private boolean func_145977_a(int p_145977_1_, int p_145977_2_, int p_145977_3_) {
        Block var4 = this.worldObj.getBlock(p_145977_1_, p_145977_2_, p_145977_3_);
        if (var4 instanceof BlockChest && ((BlockChest)var4).field_149956_a == this.func_145980_j()) {
            return true;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        float var1;
        super.updateEntity();
        this.func_145979_i();
        ++this.field_145983_q;
        if (!this.worldObj.isClient && this.field_145987_o != 0 && (this.field_145983_q + this.field_145851_c + this.field_145848_d + this.field_145849_e) % 200 == 0) {
            this.field_145987_o = 0;
            var1 = 5.0f;
            List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((float)this.field_145851_c - var1, (float)this.field_145848_d - var1, (float)this.field_145849_e - var1, (float)(this.field_145851_c + 1) + var1, (float)(this.field_145848_d + 1) + var1, (float)(this.field_145849_e + 1) + var1));
            for (EntityPlayer var4 : var2) {
                IInventory var5;
                if (!(var4.openContainer instanceof ContainerChest) || (var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory()) != this && (!(var5 instanceof InventoryLargeChest) || !((InventoryLargeChest)var5).isPartOfLargeChest(this))) continue;
                ++this.field_145987_o;
            }
        }
        this.field_145986_n = this.field_145989_m;
        var1 = 0.1f;
        if (this.field_145987_o > 0 && this.field_145989_m == 0.0f && this.field_145992_i == null && this.field_145991_k == null) {
            double var8 = (double)this.field_145851_c + 0.5;
            double var11 = (double)this.field_145849_e + 0.5;
            if (this.field_145988_l != null) {
                var11 += 0.5;
            }
            if (this.field_145990_j != null) {
                var8 += 0.5;
            }
            this.worldObj.playSoundEffect(var8, (double)this.field_145848_d + 0.5, var11, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (this.field_145987_o == 0 && this.field_145989_m > 0.0f || this.field_145987_o > 0 && this.field_145989_m < 1.0f) {
            float var10;
            float var9 = this.field_145989_m;
            this.field_145989_m = this.field_145987_o > 0 ? (this.field_145989_m += var1) : (this.field_145989_m -= var1);
            if (this.field_145989_m > 1.0f) {
                this.field_145989_m = 1.0f;
            }
            if (this.field_145989_m < (var10 = 0.5f) && var9 >= var10 && this.field_145992_i == null && this.field_145991_k == null) {
                double var11 = (double)this.field_145851_c + 0.5;
                double var6 = (double)this.field_145849_e + 0.5;
                if (this.field_145988_l != null) {
                    var6 += 0.5;
                }
                if (this.field_145990_j != null) {
                    var11 += 0.5;
                }
                this.worldObj.playSoundEffect(var11, (double)this.field_145848_d + 0.5, var6, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.field_145989_m < 0.0f) {
                this.field_145989_m = 0.0f;
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
        if (p_145842_1_ == 1) {
            this.field_145987_o = p_145842_2_;
            return true;
        }
        return super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    @Override
    public void openInventory() {
        if (this.field_145987_o < 0) {
            this.field_145987_o = 0;
        }
        ++this.field_145987_o;
        this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType(), 1, this.field_145987_o);
        this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.getBlockType());
    }

    @Override
    public void closeInventory() {
        if (this.getBlockType() instanceof BlockChest) {
            --this.field_145987_o;
            this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType(), 1, this.field_145987_o);
            this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.getBlockType());
        }
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.func_145979_i();
    }

    public int func_145980_j() {
        if (this.field_145982_r == -1) {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return 0;
            }
            this.field_145982_r = ((BlockChest)this.getBlockType()).field_149956_a;
        }
        return this.field_145982_r;
    }
}

