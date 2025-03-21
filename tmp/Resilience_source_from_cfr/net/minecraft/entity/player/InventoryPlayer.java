/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.player;

import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public class InventoryPlayer
implements IInventory {
    public ItemStack[] mainInventory = new ItemStack[36];
    public ItemStack[] armorInventory = new ItemStack[4];
    public int currentItem;
    private ItemStack currentItemStack;
    public EntityPlayer player;
    private ItemStack itemStack;
    public boolean inventoryChanged;
    private static final String __OBFID = "CL_00001709";

    public InventoryPlayer(EntityPlayer par1EntityPlayer) {
        this.player = par1EntityPlayer;
    }

    public ItemStack getCurrentItem() {
        return this.currentItem < 9 && this.currentItem >= 0 ? this.mainInventory[this.currentItem] : null;
    }

    public static int getHotbarSize() {
        return 9;
    }

    private int func_146029_c(Item p_146029_1_) {
        int var2 = 0;
        while (var2 < this.mainInventory.length) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].getItem() == p_146029_1_) {
                return var2;
            }
            ++var2;
        }
        return -1;
    }

    private int func_146024_c(Item p_146024_1_, int p_146024_2_) {
        int var3 = 0;
        while (var3 < this.mainInventory.length) {
            if (this.mainInventory[var3] != null && this.mainInventory[var3].getItem() == p_146024_1_ && this.mainInventory[var3].getItemDamage() == p_146024_2_) {
                return var3;
            }
            ++var3;
        }
        return -1;
    }

    private int storeItemStack(ItemStack par1ItemStack) {
        int var2 = 0;
        while (var2 < this.mainInventory.length) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].getItem() == par1ItemStack.getItem() && this.mainInventory[var2].isStackable() && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[var2].getHasSubtypes() || this.mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[var2], par1ItemStack)) {
                return var2;
            }
            ++var2;
        }
        return -1;
    }

    public int getFirstEmptyStack() {
        int var1 = 0;
        while (var1 < this.mainInventory.length) {
            if (this.mainInventory[var1] == null) {
                return var1;
            }
            ++var1;
        }
        return -1;
    }

    public void func_146030_a(Item p_146030_1_, int p_146030_2_, boolean p_146030_3_, boolean p_146030_4_) {
        boolean var5 = true;
        this.currentItemStack = this.getCurrentItem();
        int var7 = p_146030_3_ ? this.func_146024_c(p_146030_1_, p_146030_2_) : this.func_146029_c(p_146030_1_);
        if (var7 >= 0 && var7 < 9) {
            this.currentItem = var7;
        } else if (p_146030_4_ && p_146030_1_ != null) {
            int var6 = this.getFirstEmptyStack();
            if (var6 >= 0 && var6 < 9) {
                this.currentItem = var6;
            }
            this.func_70439_a(p_146030_1_, p_146030_2_);
        }
    }

    public void changeCurrentItem(int par1) {
        if (par1 > 0) {
            par1 = 1;
        }
        if (par1 < 0) {
            par1 = -1;
        }
        this.currentItem -= par1;
        while (this.currentItem < 0) {
            this.currentItem += 9;
        }
        while (this.currentItem >= 9) {
            this.currentItem -= 9;
        }
    }

    public int clearInventory(Item p_146027_1_, int p_146027_2_) {
        ItemStack var5;
        int var3 = 0;
        int var4 = 0;
        while (var4 < this.mainInventory.length) {
            var5 = this.mainInventory[var4];
            if (!(var5 == null || p_146027_1_ != null && var5.getItem() != p_146027_1_ || p_146027_2_ > -1 && var5.getItemDamage() != p_146027_2_)) {
                var3 += var5.stackSize;
                this.mainInventory[var4] = null;
            }
            ++var4;
        }
        var4 = 0;
        while (var4 < this.armorInventory.length) {
            var5 = this.armorInventory[var4];
            if (!(var5 == null || p_146027_1_ != null && var5.getItem() != p_146027_1_ || p_146027_2_ > -1 && var5.getItemDamage() != p_146027_2_)) {
                var3 += var5.stackSize;
                this.armorInventory[var4] = null;
            }
            ++var4;
        }
        if (this.itemStack != null) {
            if (p_146027_1_ != null && this.itemStack.getItem() != p_146027_1_) {
                return var3;
            }
            if (p_146027_2_ > -1 && this.itemStack.getItemDamage() != p_146027_2_) {
                return var3;
            }
            var3 += this.itemStack.stackSize;
            this.setItemStack(null);
        }
        return var3;
    }

    public void func_70439_a(Item par1Item, int par2) {
        if (par1Item != null) {
            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.func_146024_c(this.currentItemStack.getItem(), this.currentItemStack.getItemDamageForDisplay()) == this.currentItem) {
                return;
            }
            int var3 = this.func_146024_c(par1Item, par2);
            if (var3 >= 0) {
                int var4 = this.mainInventory[var3].stackSize;
                this.mainInventory[var3] = this.mainInventory[this.currentItem];
                this.mainInventory[this.currentItem] = new ItemStack(par1Item, var4, par2);
            } else {
                this.mainInventory[this.currentItem] = new ItemStack(par1Item, 1, par2);
            }
        }
    }

    private int storePartialItemStack(ItemStack par1ItemStack) {
        Item var2 = par1ItemStack.getItem();
        int var3 = par1ItemStack.stackSize;
        if (par1ItemStack.getMaxStackSize() == 1) {
            int var4 = this.getFirstEmptyStack();
            if (var4 < 0) {
                return var3;
            }
            if (this.mainInventory[var4] == null) {
                this.mainInventory[var4] = ItemStack.copyItemStack(par1ItemStack);
            }
            return 0;
        }
        int var4 = this.storeItemStack(par1ItemStack);
        if (var4 < 0) {
            var4 = this.getFirstEmptyStack();
        }
        if (var4 < 0) {
            return var3;
        }
        if (this.mainInventory[var4] == null) {
            this.mainInventory[var4] = new ItemStack(var2, 0, par1ItemStack.getItemDamage());
            if (par1ItemStack.hasTagCompound()) {
                this.mainInventory[var4].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
            }
        }
        int var5 = var3;
        if (var3 > this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize) {
            var5 = this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize;
        }
        if (var5 > this.getInventoryStackLimit() - this.mainInventory[var4].stackSize) {
            var5 = this.getInventoryStackLimit() - this.mainInventory[var4].stackSize;
        }
        if (var5 == 0) {
            return var3;
        }
        this.mainInventory[var4].stackSize += var5;
        this.mainInventory[var4].animationsToGo = 5;
        return var3 -= var5;
    }

    public void decrementAnimations() {
        int var1 = 0;
        while (var1 < this.mainInventory.length) {
            if (this.mainInventory[var1] != null) {
                this.mainInventory[var1].updateAnimation(this.player.worldObj, this.player, var1, this.currentItem == var1);
            }
            ++var1;
        }
    }

    public boolean consumeInventoryItem(Item p_146026_1_) {
        int var2 = this.func_146029_c(p_146026_1_);
        if (var2 < 0) {
            return false;
        }
        if (--this.mainInventory[var2].stackSize <= 0) {
            this.mainInventory[var2] = null;
        }
        return true;
    }

    public boolean hasItem(Item p_146028_1_) {
        int var2 = this.func_146029_c(p_146028_1_);
        if (var2 >= 0) {
            return true;
        }
        return false;
    }

    public boolean addItemStackToInventory(final ItemStack par1ItemStack) {
        if (par1ItemStack != null && par1ItemStack.stackSize != 0 && par1ItemStack.getItem() != null) {
            int var2;
            block11 : {
                block9 : {
                    block10 : {
                        if (!par1ItemStack.isItemDamaged()) break block9;
                        var2 = this.getFirstEmptyStack();
                        if (var2 < 0) break block10;
                        this.mainInventory[var2] = ItemStack.copyItemStack(par1ItemStack);
                        this.mainInventory[var2].animationsToGo = 5;
                        par1ItemStack.stackSize = 0;
                        return true;
                    }
                    if (this.player.capabilities.isCreativeMode) {
                        par1ItemStack.stackSize = 0;
                        return true;
                    }
                    return false;
                }
                try {
                    do {
                        var2 = par1ItemStack.stackSize;
                        par1ItemStack.stackSize = this.storePartialItemStack(par1ItemStack);
                    } while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < var2);
                    if (par1ItemStack.stackSize != var2 || !this.player.capabilities.isCreativeMode) break block11;
                    par1ItemStack.stackSize = 0;
                    return true;
                }
                catch (Throwable var5) {
                    CrashReport var3 = CrashReport.makeCrashReport(var5, "Adding item to inventory");
                    CrashReportCategory var4 = var3.makeCategory("Item being added");
                    var4.addCrashSection("Item ID", Item.getIdFromItem(par1ItemStack.getItem()));
                    var4.addCrashSection("Item data", par1ItemStack.getItemDamage());
                    var4.addCrashSectionCallable("Item name", new Callable(){
                        private static final String __OBFID = "CL_00001710";

                        public String call() {
                            return par1ItemStack.getDisplayName();
                        }
                    });
                    throw new ReportedException(var3);
                }
            }
            if (par1ItemStack.stackSize < var2) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        ItemStack[] var3 = this.mainInventory;
        if (par1 >= this.mainInventory.length) {
            var3 = this.armorInventory;
            par1 -= this.mainInventory.length;
        }
        if (var3[par1] != null) {
            if (var3[par1].stackSize <= par2) {
                ItemStack var4 = var3[par1];
                var3[par1] = null;
                return var4;
            }
            ItemStack var4 = var3[par1].splitStack(par2);
            if (var3[par1].stackSize == 0) {
                var3[par1] = null;
            }
            return var4;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        ItemStack[] var2 = this.mainInventory;
        if (par1 >= this.mainInventory.length) {
            var2 = this.armorInventory;
            par1 -= this.mainInventory.length;
        }
        if (var2[par1] != null) {
            ItemStack var3 = var2[par1];
            var2[par1] = null;
            return var3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        ItemStack[] var3 = this.mainInventory;
        if (par1 >= var3.length) {
            par1 -= var3.length;
            var3 = this.armorInventory;
        }
        var3[par1] = par2ItemStack;
    }

    public float func_146023_a(Block p_146023_1_) {
        float var2 = 1.0f;
        if (this.mainInventory[this.currentItem] != null) {
            var2 *= this.mainInventory[this.currentItem].func_150997_a(p_146023_1_);
        }
        return var2;
    }

    public NBTTagList writeToNBT(NBTTagList par1NBTTagList) {
        NBTTagCompound var3;
        int var2 = 0;
        while (var2 < this.mainInventory.length) {
            if (this.mainInventory[var2] != null) {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)var2);
                this.mainInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
            ++var2;
        }
        var2 = 0;
        while (var2 < this.armorInventory.length) {
            if (this.armorInventory[var2] != null) {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)(var2 + 100));
                this.armorInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
            ++var2;
        }
        return par1NBTTagList;
    }

    public void readFromNBT(NBTTagList par1NBTTagList) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        int var2 = 0;
        while (var2 < par1NBTTagList.tagCount()) {
            NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
            int var4 = var3.getByte("Slot") & 255;
            ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
            if (var5 != null) {
                if (var4 >= 0 && var4 < this.mainInventory.length) {
                    this.mainInventory[var4] = var5;
                }
                if (var4 >= 100 && var4 < this.armorInventory.length + 100) {
                    this.armorInventory[var4 - 100] = var5;
                }
            }
            ++var2;
        }
    }

    @Override
    public int getSizeInventory() {
        return this.mainInventory.length + 4;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        ItemStack[] var2 = this.mainInventory;
        if (par1 >= var2.length) {
            par1 -= var2.length;
            var2 = this.armorInventory;
        }
        return var2[par1];
    }

    @Override
    public String getInventoryName() {
        return "container.inventory";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean func_146025_b(Block p_146025_1_) {
        if (p_146025_1_.getMaterial().isToolNotRequired()) {
            return true;
        }
        ItemStack var2 = this.getStackInSlot(this.currentItem);
        return var2 != null ? var2.func_150998_b(p_146025_1_) : false;
    }

    public ItemStack armorItemInSlot(int par1) {
        return this.armorInventory[par1];
    }

    public int getTotalArmorValue() {
        int var1 = 0;
        int var2 = 0;
        while (var2 < this.armorInventory.length) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
                int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
                var1 += var3;
            }
            ++var2;
        }
        return var1;
    }

    public void damageArmor(float par1) {
        if ((par1 /= 4.0f) < 1.0f) {
            par1 = 1.0f;
        }
        int var2 = 0;
        while (var2 < this.armorInventory.length) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
                this.armorInventory[var2].damageItem((int)par1, this.player);
                if (this.armorInventory[var2].stackSize == 0) {
                    this.armorInventory[var2] = null;
                }
            }
            ++var2;
        }
    }

    public void dropAllItems() {
        int var1 = 0;
        while (var1 < this.mainInventory.length) {
            if (this.mainInventory[var1] != null) {
                this.player.func_146097_a(this.mainInventory[var1], true, false);
                this.mainInventory[var1] = null;
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.armorInventory.length) {
            if (this.armorInventory[var1] != null) {
                this.player.func_146097_a(this.armorInventory[var1], true, false);
                this.armorInventory[var1] = null;
            }
            ++var1;
        }
    }

    @Override
    public void onInventoryChanged() {
        this.inventoryChanged = true;
    }

    public void setItemStack(ItemStack par1ItemStack) {
        this.itemStack = par1ItemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.player.isDead ? false : par1EntityPlayer.getDistanceSqToEntity(this.player) <= 64.0;
    }

    public boolean hasItemStack(ItemStack par1ItemStack) {
        int var2 = 0;
        while (var2 < this.armorInventory.length) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].isItemEqual(par1ItemStack)) {
                return true;
            }
            ++var2;
        }
        var2 = 0;
        while (var2 < this.mainInventory.length) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].isItemEqual(par1ItemStack)) {
                return true;
            }
            ++var2;
        }
        return false;
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

    public void copyInventory(InventoryPlayer par1InventoryPlayer) {
        int var2 = 0;
        while (var2 < this.mainInventory.length) {
            this.mainInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[var2]);
            ++var2;
        }
        var2 = 0;
        while (var2 < this.armorInventory.length) {
            this.armorInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[var2]);
            ++var2;
        }
        this.currentItem = par1InventoryPlayer.currentItem;
    }

}

