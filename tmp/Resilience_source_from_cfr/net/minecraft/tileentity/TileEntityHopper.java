/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityHopper
extends TileEntity
implements IHopper {
    private ItemStack[] field_145900_a = new ItemStack[5];
    private String field_145902_i;
    private int field_145901_j = -1;
    private static final String __OBFID = "CL_00000359";

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145900_a = new ItemStack[this.getSizeInventory()];
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145902_i = p_145839_1_.getString("CustomName");
        }
        this.field_145901_j = p_145839_1_.getInteger("TransferCooldown");
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.field_145900_a.length) {
                this.field_145900_a[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
            ++var3;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.field_145900_a.length) {
            if (this.field_145900_a[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145900_a[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
            ++var3;
        }
        p_145841_1_.setTag("Items", var2);
        p_145841_1_.setInteger("TransferCooldown", this.field_145901_j);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145902_i);
        }
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
    }

    @Override
    public int getSizeInventory() {
        return this.field_145900_a.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.field_145900_a[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.field_145900_a[par1] != null) {
            if (this.field_145900_a[par1].stackSize <= par2) {
                ItemStack var3 = this.field_145900_a[par1];
                this.field_145900_a[par1] = null;
                return var3;
            }
            ItemStack var3 = this.field_145900_a[par1].splitStack(par2);
            if (this.field_145900_a[par1].stackSize == 0) {
                this.field_145900_a[par1] = null;
            }
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.field_145900_a[par1] != null) {
            ItemStack var2 = this.field_145900_a[par1];
            this.field_145900_a[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.field_145900_a[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145902_i : "container.hopper";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        if (this.field_145902_i != null && this.field_145902_i.length() > 0) {
            return true;
        }
        return false;
    }

    public void func_145886_a(String p_145886_1_) {
        this.field_145902_i = p_145886_1_;
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
    public void updateEntity() {
        if (this.worldObj != null && !this.worldObj.isClient) {
            --this.field_145901_j;
            if (!this.func_145888_j()) {
                this.func_145896_c(0);
                this.func_145887_i();
            }
        }
    }

    public boolean func_145887_i() {
        if (this.worldObj != null && !this.worldObj.isClient) {
            if (!this.func_145888_j() && BlockHopper.func_149917_c(this.getBlockMetadata())) {
                boolean var1 = this.func_145883_k();
                boolean bl = var1 = TileEntityHopper.func_145891_a(this) || var1;
                if (var1) {
                    this.func_145896_c(8);
                    this.onInventoryChanged();
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean func_145883_k() {
        IInventory var1 = this.func_145895_l();
        if (var1 == null) {
            return false;
        }
        int var2 = 0;
        while (var2 < this.getSizeInventory()) {
            if (this.getStackInSlot(var2) != null) {
                ItemStack var3 = this.getStackInSlot(var2).copy();
                ItemStack var4 = TileEntityHopper.func_145889_a(var1, this.decrStackSize(var2, 1), Facing.oppositeSide[BlockHopper.func_149918_b(this.getBlockMetadata())]);
                if (var4 == null || var4.stackSize == 0) {
                    var1.onInventoryChanged();
                    return true;
                }
                this.setInventorySlotContents(var2, var3);
            }
            ++var2;
        }
        return false;
    }

    public static boolean func_145891_a(IHopper p_145891_0_) {
        IInventory var1 = TileEntityHopper.func_145884_b(p_145891_0_);
        if (var1 != null) {
            int var2 = 0;
            if (var1 instanceof ISidedInventory && var2 > -1) {
                ISidedInventory var7 = (ISidedInventory)var1;
                int[] var8 = var7.getAccessibleSlotsFromSide(var2);
                int var5 = 0;
                while (var5 < var8.length) {
                    if (TileEntityHopper.func_145892_a(p_145891_0_, var1, var8[var5], var2)) {
                        return true;
                    }
                    ++var5;
                }
            } else {
                int var3 = var1.getSizeInventory();
                int var4 = 0;
                while (var4 < var3) {
                    if (TileEntityHopper.func_145892_a(p_145891_0_, var1, var4, var2)) {
                        return true;
                    }
                    ++var4;
                }
            }
        } else {
            EntityItem var6 = TileEntityHopper.func_145897_a(p_145891_0_.getWorldObj(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0, p_145891_0_.getZPos());
            if (var6 != null) {
                return TileEntityHopper.func_145898_a(p_145891_0_, var6);
            }
        }
        return false;
    }

    private static boolean func_145892_a(IHopper p_145892_0_, IInventory p_145892_1_, int p_145892_2_, int p_145892_3_) {
        ItemStack var4 = p_145892_1_.getStackInSlot(p_145892_2_);
        if (var4 != null && TileEntityHopper.func_145890_b(p_145892_1_, var4, p_145892_2_, p_145892_3_)) {
            ItemStack var5 = var4.copy();
            ItemStack var6 = TileEntityHopper.func_145889_a(p_145892_0_, p_145892_1_.decrStackSize(p_145892_2_, 1), -1);
            if (var6 == null || var6.stackSize == 0) {
                p_145892_1_.onInventoryChanged();
                return true;
            }
            p_145892_1_.setInventorySlotContents(p_145892_2_, var5);
        }
        return false;
    }

    public static boolean func_145898_a(IInventory p_145898_0_, EntityItem p_145898_1_) {
        boolean var2 = false;
        if (p_145898_1_ == null) {
            return false;
        }
        ItemStack var3 = p_145898_1_.getEntityItem().copy();
        ItemStack var4 = TileEntityHopper.func_145889_a(p_145898_0_, var3, -1);
        if (var4 != null && var4.stackSize != 0) {
            p_145898_1_.setEntityItemStack(var4);
        } else {
            var2 = true;
            p_145898_1_.setDead();
        }
        return var2;
    }

    public static ItemStack func_145889_a(IInventory p_145889_0_, ItemStack p_145889_1_, int p_145889_2_) {
        if (p_145889_0_ instanceof ISidedInventory && p_145889_2_ > -1) {
            ISidedInventory var6 = (ISidedInventory)p_145889_0_;
            int[] var7 = var6.getAccessibleSlotsFromSide(p_145889_2_);
            int var5 = 0;
            while (var5 < var7.length && p_145889_1_ != null && p_145889_1_.stackSize > 0) {
                p_145889_1_ = TileEntityHopper.func_145899_c(p_145889_0_, p_145889_1_, var7[var5], p_145889_2_);
                ++var5;
            }
        } else {
            int var3 = p_145889_0_.getSizeInventory();
            int var4 = 0;
            while (var4 < var3 && p_145889_1_ != null && p_145889_1_.stackSize > 0) {
                p_145889_1_ = TileEntityHopper.func_145899_c(p_145889_0_, p_145889_1_, var4, p_145889_2_);
                ++var4;
            }
        }
        if (p_145889_1_ != null && p_145889_1_.stackSize == 0) {
            p_145889_1_ = null;
        }
        return p_145889_1_;
    }

    private static boolean func_145885_a(IInventory p_145885_0_, ItemStack p_145885_1_, int p_145885_2_, int p_145885_3_) {
        return !p_145885_0_.isItemValidForSlot(p_145885_2_, p_145885_1_) ? false : !(p_145885_0_ instanceof ISidedInventory) || ((ISidedInventory)p_145885_0_).canInsertItem(p_145885_2_, p_145885_1_, p_145885_3_);
    }

    private static boolean func_145890_b(IInventory p_145890_0_, ItemStack p_145890_1_, int p_145890_2_, int p_145890_3_) {
        if (p_145890_0_ instanceof ISidedInventory && !((ISidedInventory)p_145890_0_).canExtractItem(p_145890_2_, p_145890_1_, p_145890_3_)) {
            return false;
        }
        return true;
    }

    private static ItemStack func_145899_c(IInventory p_145899_0_, ItemStack p_145899_1_, int p_145899_2_, int p_145899_3_) {
        ItemStack var4 = p_145899_0_.getStackInSlot(p_145899_2_);
        if (TileEntityHopper.func_145885_a(p_145899_0_, p_145899_1_, p_145899_2_, p_145899_3_)) {
            boolean var5 = false;
            if (var4 == null) {
                p_145899_0_.setInventorySlotContents(p_145899_2_, p_145899_1_);
                p_145899_1_ = null;
                var5 = true;
            } else if (TileEntityHopper.func_145894_a(var4, p_145899_1_)) {
                int var6 = p_145899_1_.getMaxStackSize() - var4.stackSize;
                int var7 = Math.min(p_145899_1_.stackSize, var6);
                p_145899_1_.stackSize -= var7;
                var4.stackSize += var7;
                boolean bl = var5 = var7 > 0;
            }
            if (var5) {
                if (p_145899_0_ instanceof TileEntityHopper) {
                    ((TileEntityHopper)p_145899_0_).func_145896_c(8);
                    p_145899_0_.onInventoryChanged();
                }
                p_145899_0_.onInventoryChanged();
            }
        }
        return p_145899_1_;
    }

    private IInventory func_145895_l() {
        int var1 = BlockHopper.func_149918_b(this.getBlockMetadata());
        return TileEntityHopper.func_145893_b(this.getWorldObj(), this.field_145851_c + Facing.offsetsXForSide[var1], this.field_145848_d + Facing.offsetsYForSide[var1], this.field_145849_e + Facing.offsetsZForSide[var1]);
    }

    public static IInventory func_145884_b(IHopper p_145884_0_) {
        return TileEntityHopper.func_145893_b(p_145884_0_.getWorldObj(), p_145884_0_.getXPos(), p_145884_0_.getYPos() + 1.0, p_145884_0_.getZPos());
    }

    public static EntityItem func_145897_a(World p_145897_0_, double p_145897_1_, double p_145897_3_, double p_145897_5_) {
        List var7 = p_145897_0_.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(p_145897_1_, p_145897_3_, p_145897_5_, p_145897_1_ + 1.0, p_145897_3_ + 1.0, p_145897_5_ + 1.0), IEntitySelector.selectAnything);
        return var7.size() > 0 ? (EntityItem)var7.get(0) : null;
    }

    public static IInventory func_145893_b(World p_145893_0_, double p_145893_1_, double p_145893_3_, double p_145893_5_) {
        int var10;
        int var9;
        Block var12;
        List var13;
        IInventory var7 = null;
        int var8 = MathHelper.floor_double(p_145893_1_);
        TileEntity var11 = p_145893_0_.getTileEntity(var8, var9 = MathHelper.floor_double(p_145893_3_), var10 = MathHelper.floor_double(p_145893_5_));
        if (var11 != null && var11 instanceof IInventory && (var7 = (IInventory)((Object)var11)) instanceof TileEntityChest && (var12 = p_145893_0_.getBlock(var8, var9, var10)) instanceof BlockChest) {
            var7 = ((BlockChest)var12).func_149951_m(p_145893_0_, var8, var9, var10);
        }
        if (var7 == null && (var13 = p_145893_0_.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB(p_145893_1_, p_145893_3_, p_145893_5_, p_145893_1_ + 1.0, p_145893_3_ + 1.0, p_145893_5_ + 1.0), IEntitySelector.selectInventories)) != null && var13.size() > 0) {
            var7 = (IInventory)var13.get(p_145893_0_.rand.nextInt(var13.size()));
        }
        return var7;
    }

    private static boolean func_145894_a(ItemStack p_145894_0_, ItemStack p_145894_1_) {
        return p_145894_0_.getItem() != p_145894_1_.getItem() ? false : (p_145894_0_.getItemDamage() != p_145894_1_.getItemDamage() ? false : (p_145894_0_.stackSize > p_145894_0_.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(p_145894_0_, p_145894_1_)));
    }

    @Override
    public double getXPos() {
        return this.field_145851_c;
    }

    @Override
    public double getYPos() {
        return this.field_145848_d;
    }

    @Override
    public double getZPos() {
        return this.field_145849_e;
    }

    public void func_145896_c(int p_145896_1_) {
        this.field_145901_j = p_145896_1_;
    }

    public boolean func_145888_j() {
        if (this.field_145901_j > 0) {
            return true;
        }
        return false;
    }
}

