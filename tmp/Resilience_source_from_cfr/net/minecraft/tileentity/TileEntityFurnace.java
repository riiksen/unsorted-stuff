/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFurnace
extends TileEntity
implements ISidedInventory {
    private static final int[] field_145962_k = new int[1];
    private static final int[] field_145959_l = new int[]{2, 1};
    private static final int[] field_145960_m = new int[]{1};
    private ItemStack[] field_145957_n = new ItemStack[3];
    public int field_145956_a;
    public int field_145963_i;
    public int field_145961_j;
    private String field_145958_o;
    private static final String __OBFID = "CL_00000357";

    @Override
    public int getSizeInventory() {
        return this.field_145957_n.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.field_145957_n[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.field_145957_n[par1] != null) {
            if (this.field_145957_n[par1].stackSize <= par2) {
                ItemStack var3 = this.field_145957_n[par1];
                this.field_145957_n[par1] = null;
                return var3;
            }
            ItemStack var3 = this.field_145957_n[par1].splitStack(par2);
            if (this.field_145957_n[par1].stackSize == 0) {
                this.field_145957_n[par1] = null;
            }
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.field_145957_n[par1] != null) {
            ItemStack var2 = this.field_145957_n[par1];
            this.field_145957_n[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.field_145957_n[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145958_o : "container.furnace";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        if (this.field_145958_o != null && this.field_145958_o.length() > 0) {
            return true;
        }
        return false;
    }

    public void func_145951_a(String p_145951_1_) {
        this.field_145958_o = p_145951_1_;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145957_n = new ItemStack[this.getSizeInventory()];
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.field_145957_n.length) {
                this.field_145957_n[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
            ++var3;
        }
        this.field_145956_a = p_145839_1_.getShort("BurnTime");
        this.field_145961_j = p_145839_1_.getShort("CookTime");
        this.field_145963_i = TileEntityFurnace.func_145952_a(this.field_145957_n[1]);
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145958_o = p_145839_1_.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BurnTime", (short)this.field_145956_a);
        p_145841_1_.setShort("CookTime", (short)this.field_145961_j);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.field_145957_n.length) {
            if (this.field_145957_n[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145957_n[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
            ++var3;
        }
        p_145841_1_.setTag("Items", var2);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145958_o);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public int func_145953_d(int p_145953_1_) {
        return this.field_145961_j * p_145953_1_ / 200;
    }

    public int func_145955_e(int p_145955_1_) {
        if (this.field_145963_i == 0) {
            this.field_145963_i = 200;
        }
        return this.field_145956_a * p_145955_1_ / this.field_145963_i;
    }

    public boolean func_145950_i() {
        if (this.field_145956_a > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        boolean var1 = this.field_145956_a > 0;
        boolean var2 = false;
        if (this.field_145956_a > 0) {
            --this.field_145956_a;
        }
        if (!this.worldObj.isClient) {
            if (this.field_145956_a == 0 && this.func_145948_k()) {
                this.field_145963_i = this.field_145956_a = TileEntityFurnace.func_145952_a(this.field_145957_n[1]);
                if (this.field_145956_a > 0) {
                    var2 = true;
                    if (this.field_145957_n[1] != null) {
                        --this.field_145957_n[1].stackSize;
                        if (this.field_145957_n[1].stackSize == 0) {
                            Item var3 = this.field_145957_n[1].getItem().getContainerItem();
                            ItemStack itemStack = this.field_145957_n[1] = var3 != null ? new ItemStack(var3) : null;
                        }
                    }
                }
            }
            if (this.func_145950_i() && this.func_145948_k()) {
                ++this.field_145961_j;
                if (this.field_145961_j == 200) {
                    this.field_145961_j = 0;
                    this.func_145949_j();
                    var2 = true;
                }
            } else {
                this.field_145961_j = 0;
            }
            if (var1 != this.field_145956_a > 0) {
                var2 = true;
                BlockFurnace.func_149931_a(this.field_145956_a > 0, this.worldObj, this.field_145851_c, this.field_145848_d, this.field_145849_e);
            }
        }
        if (var2) {
            this.onInventoryChanged();
        }
    }

    private boolean func_145948_k() {
        if (this.field_145957_n[0] == null) {
            return false;
        }
        ItemStack var1 = FurnaceRecipes.smelting().func_151395_a(this.field_145957_n[0]);
        return var1 == null ? false : (this.field_145957_n[2] == null ? true : (!this.field_145957_n[2].isItemEqual(var1) ? false : (this.field_145957_n[2].stackSize < this.getInventoryStackLimit() && this.field_145957_n[2].stackSize < this.field_145957_n[2].getMaxStackSize() ? true : this.field_145957_n[2].stackSize < var1.getMaxStackSize())));
    }

    public void func_145949_j() {
        if (this.func_145948_k()) {
            ItemStack var1 = FurnaceRecipes.smelting().func_151395_a(this.field_145957_n[0]);
            if (this.field_145957_n[2] == null) {
                this.field_145957_n[2] = var1.copy();
            } else if (this.field_145957_n[2].getItem() == var1.getItem()) {
                ++this.field_145957_n[2].stackSize;
            }
            --this.field_145957_n[0].stackSize;
            if (this.field_145957_n[0].stackSize <= 0) {
                this.field_145957_n[0] = null;
            }
        }
    }

    public static int func_145952_a(ItemStack p_145952_0_) {
        if (p_145952_0_ == null) {
            return 0;
        }
        Item var1 = p_145952_0_.getItem();
        if (var1 instanceof ItemBlock && Block.getBlockFromItem(var1) != Blocks.air) {
            Block var2 = Block.getBlockFromItem(var1);
            if (var2 == Blocks.wooden_slab) {
                return 150;
            }
            if (var2.getMaterial() == Material.wood) {
                return 300;
            }
            if (var2 == Blocks.coal_block) {
                return 16000;
            }
        }
        return var1 instanceof ItemTool && ((ItemTool)var1).getToolMaterialName().equals("WOOD") ? 200 : (var1 instanceof ItemSword && ((ItemSword)var1).func_150932_j().equals("WOOD") ? 200 : (var1 instanceof ItemHoe && ((ItemHoe)var1).getMaterialName().equals("WOOD") ? 200 : (var1 == Items.stick ? 100 : (var1 == Items.coal ? 1600 : (var1 == Items.lava_bucket ? 20000 : (var1 == Item.getItemFromBlock(Blocks.sapling) ? 100 : (var1 == Items.blaze_rod ? 2400 : 0)))))));
    }

    public static boolean func_145954_b(ItemStack p_145954_0_) {
        if (TileEntityFurnace.func_145952_a(p_145954_0_) > 0) {
            return true;
        }
        return false;
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
        return par1 == 2 ? false : (par1 == 1 ? TileEntityFurnace.func_145954_b(par2ItemStack) : true);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1) {
        return par1 == 0 ? field_145959_l : (par1 == 1 ? field_145962_k : field_145960_m);
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
        if (par3 == 0 && par1 == 1 && par2ItemStack.getItem() != Items.bucket) {
            return false;
        }
        return true;
    }
}

