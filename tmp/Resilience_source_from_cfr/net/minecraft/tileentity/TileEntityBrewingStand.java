/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityBrewingStand
extends TileEntity
implements ISidedInventory {
    private static final int[] field_145941_a = new int[]{3};
    private static final int[] field_145947_i;
    private ItemStack[] field_145945_j = new ItemStack[4];
    private int field_145946_k;
    private int field_145943_l;
    private Item field_145944_m;
    private String field_145942_n;
    private static final String __OBFID = "CL_00000345";

    static {
        int[] arrn = new int[3];
        arrn[1] = 1;
        arrn[2] = 2;
        field_145947_i = arrn;
    }

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145942_n : "container.brewing";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        if (this.field_145942_n != null && this.field_145942_n.length() > 0) {
            return true;
        }
        return false;
    }

    public void func_145937_a(String p_145937_1_) {
        this.field_145942_n = p_145937_1_;
    }

    @Override
    public int getSizeInventory() {
        return this.field_145945_j.length;
    }

    @Override
    public void updateEntity() {
        if (this.field_145946_k > 0) {
            --this.field_145946_k;
            if (this.field_145946_k == 0) {
                this.func_145940_l();
                this.onInventoryChanged();
            } else if (!this.func_145934_k()) {
                this.field_145946_k = 0;
                this.onInventoryChanged();
            } else if (this.field_145944_m != this.field_145945_j[3].getItem()) {
                this.field_145946_k = 0;
                this.onInventoryChanged();
            }
        } else if (this.func_145934_k()) {
            this.field_145946_k = 400;
            this.field_145944_m = this.field_145945_j[3].getItem();
        }
        int var1 = this.func_145939_j();
        if (var1 != this.field_145943_l) {
            this.field_145943_l = var1;
            this.worldObj.setBlockMetadataWithNotify(this.field_145851_c, this.field_145848_d, this.field_145849_e, var1, 2);
        }
        super.updateEntity();
    }

    public int func_145935_i() {
        return this.field_145946_k;
    }

    private boolean func_145934_k() {
        if (this.field_145945_j[3] != null && this.field_145945_j[3].stackSize > 0) {
            ItemStack var1 = this.field_145945_j[3];
            if (!var1.getItem().isPotionIngredient(var1)) {
                return false;
            }
            boolean var2 = false;
            int var3 = 0;
            while (var3 < 3) {
                if (this.field_145945_j[var3] != null && this.field_145945_j[var3].getItem() == Items.potionitem) {
                    int var4 = this.field_145945_j[var3].getItemDamage();
                    int var5 = this.func_145936_c(var4, var1);
                    if (!ItemPotion.isSplash(var4) && ItemPotion.isSplash(var5)) {
                        var2 = true;
                        break;
                    }
                    List var6 = Items.potionitem.getEffects(var4);
                    List var7 = Items.potionitem.getEffects(var5);
                    if (!(var4 > 0 && var6 == var7 || var6 != null && (var6.equals(var7) || var7 == null) || var4 == var5)) {
                        var2 = true;
                        break;
                    }
                }
                ++var3;
            }
            return var2;
        }
        return false;
    }

    private void func_145940_l() {
        if (this.func_145934_k()) {
            ItemStack var1 = this.field_145945_j[3];
            int var2 = 0;
            while (var2 < 3) {
                if (this.field_145945_j[var2] != null && this.field_145945_j[var2].getItem() == Items.potionitem) {
                    int var3 = this.field_145945_j[var2].getItemDamage();
                    int var4 = this.func_145936_c(var3, var1);
                    List var5 = Items.potionitem.getEffects(var3);
                    List var6 = Items.potionitem.getEffects(var4);
                    if (!(var3 > 0 && var5 == var6 || var5 != null && (var5.equals(var6) || var6 == null))) {
                        if (var3 != var4) {
                            this.field_145945_j[var2].setItemDamage(var4);
                        }
                    } else if (!ItemPotion.isSplash(var3) && ItemPotion.isSplash(var4)) {
                        this.field_145945_j[var2].setItemDamage(var4);
                    }
                }
                ++var2;
            }
            if (var1.getItem().hasContainerItem()) {
                this.field_145945_j[3] = new ItemStack(var1.getItem().getContainerItem());
            } else {
                --this.field_145945_j[3].stackSize;
                if (this.field_145945_j[3].stackSize <= 0) {
                    this.field_145945_j[3] = null;
                }
            }
        }
    }

    private int func_145936_c(int p_145936_1_, ItemStack p_145936_2_) {
        return p_145936_2_ == null ? p_145936_1_ : (p_145936_2_.getItem().isPotionIngredient(p_145936_2_) ? PotionHelper.applyIngredient(p_145936_1_, p_145936_2_.getItem().getPotionEffect(p_145936_2_)) : p_145936_1_);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145945_j = new ItemStack[this.getSizeInventory()];
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.field_145945_j.length) {
                this.field_145945_j[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
            ++var3;
        }
        this.field_145946_k = p_145839_1_.getShort("BrewTime");
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145942_n = p_145839_1_.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BrewTime", (short)this.field_145946_k);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.field_145945_j.length) {
            if (this.field_145945_j[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145945_j[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
            ++var3;
        }
        p_145841_1_.setTag("Items", var2);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145942_n);
        }
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return par1 >= 0 && par1 < this.field_145945_j.length ? this.field_145945_j[par1] : null;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (par1 >= 0 && par1 < this.field_145945_j.length) {
            ItemStack var3 = this.field_145945_j[par1];
            this.field_145945_j[par1] = null;
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (par1 >= 0 && par1 < this.field_145945_j.length) {
            ItemStack var2 = this.field_145945_j[par1];
            this.field_145945_j[par1] = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        if (par1 >= 0 && par1 < this.field_145945_j.length) {
            this.field_145945_j[par1] = par2ItemStack;
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
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return par1 == 3 ? par2ItemStack.getItem().isPotionIngredient(par2ItemStack) : par2ItemStack.getItem() == Items.potionitem || par2ItemStack.getItem() == Items.glass_bottle;
    }

    public void func_145938_d(int p_145938_1_) {
        this.field_145946_k = p_145938_1_;
    }

    public int func_145939_j() {
        int var1 = 0;
        int var2 = 0;
        while (var2 < 3) {
            if (this.field_145945_j[var2] != null) {
                var1 |= 1 << var2;
            }
            ++var2;
        }
        return var1;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1) {
        return par1 == 1 ? field_145941_a : field_145947_i;
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
        return true;
    }
}

