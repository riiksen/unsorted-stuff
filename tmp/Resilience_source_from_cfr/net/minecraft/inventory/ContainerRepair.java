/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.inventory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair
extends Container {
    private static final Logger logger = LogManager.getLogger();
    private IInventory outputSlot = new InventoryCraftResult();
    private IInventory inputSlots;
    private World theWorld;
    private int field_82861_i;
    private int field_82858_j;
    private int field_82859_k;
    public int maximumCost;
    private int stackSizeToBeUsedInRepair;
    private String repairedItemName;
    private final EntityPlayer thePlayer;
    private static final String __OBFID = "CL_00001732";

    public ContainerRepair(InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5, EntityPlayer par6EntityPlayer) {
        this.inputSlots = new InventoryBasic("Repair", true, 2){
            private static final String __OBFID = "CL_00001733";

            @Override
            public void onInventoryChanged() {
                super.onInventoryChanged();
                ContainerRepair.this.onCraftMatrixChanged(this);
            }
        };
        this.theWorld = par2World;
        this.field_82861_i = par3;
        this.field_82858_j = par4;
        this.field_82859_k = par5;
        this.thePlayer = par6EntityPlayer;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47){
            private static final String __OBFID = "CL_00001734";

            @Override
            public boolean isItemValid(ItemStack par1ItemStack) {
                return false;
            }

            @Override
            public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
                if ((par1EntityPlayer.capabilities.isCreativeMode || par1EntityPlayer.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && this.getHasStack()) {
                    return true;
                }
                return false;
            }

            @Override
            public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    par1EntityPlayer.addExperienceLevel(- ContainerRepair.this.maximumCost);
                }
                ContainerRepair.this.inputSlots.setInventorySlotContents(0, null);
                if (ContainerRepair.this.stackSizeToBeUsedInRepair > 0) {
                    ItemStack var3 = ContainerRepair.this.inputSlots.getStackInSlot(1);
                    if (var3 != null && var3.stackSize > ContainerRepair.this.stackSizeToBeUsedInRepair) {
                        var3.stackSize -= ContainerRepair.this.stackSizeToBeUsedInRepair;
                        ContainerRepair.this.inputSlots.setInventorySlotContents(1, var3);
                    } else {
                        ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
                    }
                } else {
                    ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
                }
                ContainerRepair.this.maximumCost = 0;
                if (!par1EntityPlayer.capabilities.isCreativeMode && !par2World.isClient && par2World.getBlock(par3, par4, par5) == Blocks.anvil && par1EntityPlayer.getRNG().nextFloat() < 0.12f) {
                    int var6 = par2World.getBlockMetadata(par3, par4, par5);
                    int var4 = var6 & 3;
                    int var5 = var6 >> 2;
                    if (++var5 > 2) {
                        par2World.setBlockToAir(par3, par4, par5);
                        par2World.playAuxSFX(1020, par3, par4, par5, 0);
                    } else {
                        par2World.setBlockMetadataWithNotify(par3, par4, par5, var4 | var5 << 2, 2);
                        par2World.playAuxSFX(1021, par3, par4, par5, 0);
                    }
                } else if (!par2World.isClient) {
                    par2World.playAuxSFX(1021, par3, par4, par5, 0);
                }
            }
        });
        int var7 = 0;
        while (var7 < 3) {
            int var8 = 0;
            while (var8 < 9) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
                ++var8;
            }
            ++var7;
        }
        var7 = 0;
        while (var7 < 9) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var7, 8 + var7 * 18, 142));
            ++var7;
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        super.onCraftMatrixChanged(par1IInventory);
        if (par1IInventory == this.inputSlots) {
            this.updateRepairOutput();
        }
    }

    public void updateRepairOutput() {
        ItemStack var1 = this.inputSlots.getStackInSlot(0);
        this.maximumCost = 0;
        int var2 = 0;
        int var3 = 0;
        int var4 = 0;
        if (var1 == null) {
            this.outputSlot.setInventorySlotContents(0, null);
            this.maximumCost = 0;
        } else {
            int var9;
            int var13;
            Iterator var21;
            int var11;
            int var10;
            int var14;
            Enchantment var22;
            ItemStack var5 = var1.copy();
            ItemStack var6 = this.inputSlots.getStackInSlot(1);
            Map var7 = EnchantmentHelper.getEnchantments(var5);
            boolean var8 = false;
            int var19 = var3 + var1.getRepairCost() + (var6 == null ? 0 : var6.getRepairCost());
            this.stackSizeToBeUsedInRepair = 0;
            if (var6 != null) {
                boolean bl = var8 = var6.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(var6).tagCount() > 0;
                if (var5.isItemStackDamageable() && var5.getItem().getIsRepairable(var1, var6)) {
                    var9 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4);
                    if (var9 <= 0) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    var10 = 0;
                    while (var9 > 0 && var10 < var6.stackSize) {
                        var11 = var5.getItemDamageForDisplay() - var9;
                        var5.setItemDamage(var11);
                        var2 += Math.max(1, var9 / 100) + var7.size();
                        var9 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4);
                        ++var10;
                    }
                    this.stackSizeToBeUsedInRepair = var10;
                } else {
                    if (!(var8 || var5.getItem() == var6.getItem() && var5.isItemStackDamageable())) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    if (var5.isItemStackDamageable() && !var8) {
                        var9 = var1.getMaxDamage() - var1.getItemDamageForDisplay();
                        var10 = var6.getMaxDamage() - var6.getItemDamageForDisplay();
                        var11 = var10 + var5.getMaxDamage() * 12 / 100;
                        int var12 = var9 + var11;
                        var13 = var5.getMaxDamage() - var12;
                        if (var13 < 0) {
                            var13 = 0;
                        }
                        if (var13 < var5.getItemDamage()) {
                            var5.setItemDamage(var13);
                            var2 += Math.max(1, var11 / 100);
                        }
                    }
                    Map var20 = EnchantmentHelper.getEnchantments(var6);
                    var21 = var20.keySet().iterator();
                    while (var21.hasNext()) {
                        var11 = (Integer)var21.next();
                        var22 = Enchantment.enchantmentsList[var11];
                        var13 = var7.containsKey(var11) ? (Integer)var7.get(var11) : 0;
                        var14 = (Integer)var20.get(var11);
                        int var10000 = var13 == var14 ? ++var14 : Math.max(var14, var13);
                        var14 = var10000;
                        int var15 = var14 - var13;
                        boolean var16 = var22.canApply(var1);
                        if (this.thePlayer.capabilities.isCreativeMode || var1.getItem() == Items.enchanted_book) {
                            var16 = true;
                        }
                        Iterator var17 = var7.keySet().iterator();
                        while (var17.hasNext()) {
                            int var18 = (Integer)var17.next();
                            if (var18 == var11 || var22.canApplyTogether(Enchantment.enchantmentsList[var18])) continue;
                            var16 = false;
                            var2 += var15;
                        }
                        if (!var16) continue;
                        if (var14 > var22.getMaxLevel()) {
                            var14 = var22.getMaxLevel();
                        }
                        var7.put(var11, var14);
                        int var23 = 0;
                        switch (var22.getWeight()) {
                            case 1: {
                                var23 = 8;
                                break;
                            }
                            case 2: {
                                var23 = 4;
                            }
                            default: {
                                break;
                            }
                            case 5: {
                                var23 = 2;
                                break;
                            }
                            case 10: {
                                var23 = 1;
                            }
                        }
                        if (var8) {
                            var23 = Math.max(1, var23 / 2);
                        }
                        var2 += var23 * var15;
                    }
                }
            }
            if (StringUtils.isBlank((CharSequence)this.repairedItemName)) {
                if (var1.hasDisplayName()) {
                    var4 = var1.isItemStackDamageable() ? 7 : var1.stackSize * 5;
                    var2 += var4;
                    var5.func_135074_t();
                }
            } else if (!this.repairedItemName.equals(var1.getDisplayName())) {
                var4 = var1.isItemStackDamageable() ? 7 : var1.stackSize * 5;
                var2 += var4;
                if (var1.hasDisplayName()) {
                    var19 += var4 / 2;
                }
                var5.setStackDisplayName(this.repairedItemName);
            }
            var9 = 0;
            var21 = var7.keySet().iterator();
            while (var21.hasNext()) {
                var11 = (Integer)var21.next();
                var22 = Enchantment.enchantmentsList[var11];
                var13 = (Integer)var7.get(var11);
                var14 = 0;
                ++var9;
                switch (var22.getWeight()) {
                    case 1: {
                        var14 = 8;
                        break;
                    }
                    case 2: {
                        var14 = 4;
                    }
                    default: {
                        break;
                    }
                    case 5: {
                        var14 = 2;
                        break;
                    }
                    case 10: {
                        var14 = 1;
                    }
                }
                if (var8) {
                    var14 = Math.max(1, var14 / 2);
                }
                var19 += var9 + var13 * var14;
            }
            if (var8) {
                var19 = Math.max(1, var19 / 2);
            }
            this.maximumCost = var19 + var2;
            if (var2 <= 0) {
                var5 = null;
            }
            if (var4 == var2 && var4 > 0 && this.maximumCost >= 40) {
                this.maximumCost = 39;
            }
            if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
                var5 = null;
            }
            if (var5 != null) {
                var10 = var5.getRepairCost();
                if (var6 != null && var10 < var6.getRepairCost()) {
                    var10 = var6.getRepairCost();
                }
                if (var5.hasDisplayName()) {
                    var10 -= 9;
                }
                if (var10 < 0) {
                    var10 = 0;
                }
                var5.setRepairCost(var10 += 2);
                EnchantmentHelper.setEnchantments(var7, var5);
            }
            this.outputSlot.setInventorySlotContents(0, var5);
            this.detectAndSendChanges();
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.maximumCost);
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.maximumCost = par2;
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        if (!this.theWorld.isClient) {
            int var2 = 0;
            while (var2 < this.inputSlots.getSizeInventory()) {
                ItemStack var3 = this.inputSlots.getStackInSlotOnClosing(var2);
                if (var3 != null) {
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
                }
                ++var2;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.theWorld.getBlock(this.field_82861_i, this.field_82858_j, this.field_82859_k) != Blocks.anvil ? false : par1EntityPlayer.getDistanceSq((double)this.field_82861_i + 0.5, (double)this.field_82858_j + 0.5, (double)this.field_82859_k + 0.5) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 2) {
                if (!this.mergeItemStack(var5, 3, 39, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            } else if (par2 != 0 && par2 != 1 ? par2 >= 3 && par2 < 39 && !this.mergeItemStack(var5, 0, 2, false) : !this.mergeItemStack(var5, 3, 39, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }
        return var3;
    }

    public void updateItemName(String par1Str) {
        this.repairedItemName = par1Str;
        if (this.getSlot(2).getHasStack()) {
            ItemStack var2 = this.getSlot(2).getStack();
            if (StringUtils.isBlank((CharSequence)par1Str)) {
                var2.func_135074_t();
            } else {
                var2.setStackDisplayName(this.repairedItemName);
            }
        }
        this.updateRepairOutput();
    }

}

