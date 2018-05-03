/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecipeFireworks
implements IRecipe {
    private ItemStack field_92102_a;
    private static final String __OBFID = "CL_00000083";

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        this.field_92102_a = null;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        while (var9 < par1InventoryCrafting.getSizeInventory()) {
            ItemStack var10 = par1InventoryCrafting.getStackInSlot(var9);
            if (var10 != null) {
                if (var10.getItem() == Items.gunpowder) {
                    ++var4;
                } else if (var10.getItem() == Items.firework_charge) {
                    ++var6;
                } else if (var10.getItem() == Items.dye) {
                    ++var5;
                } else if (var10.getItem() == Items.paper) {
                    ++var3;
                } else if (var10.getItem() == Items.glowstone_dust) {
                    ++var7;
                } else if (var10.getItem() == Items.diamond) {
                    ++var7;
                } else if (var10.getItem() == Items.fire_charge) {
                    ++var8;
                } else if (var10.getItem() == Items.feather) {
                    ++var8;
                } else if (var10.getItem() == Items.gold_nugget) {
                    ++var8;
                } else {
                    if (var10.getItem() != Items.skull) {
                        return false;
                    }
                    ++var8;
                }
            }
            ++var9;
        }
        var7 += var5 + var8;
        if (var4 <= 3 && var3 <= 1) {
            NBTTagCompound var18;
            if (var4 >= 1 && var3 == 1 && var7 == 0) {
                this.field_92102_a = new ItemStack(Items.fireworks);
                if (var6 > 0) {
                    NBTTagCompound var15 = new NBTTagCompound();
                    var18 = new NBTTagCompound();
                    NBTTagList var25 = new NBTTagList();
                    int var22 = 0;
                    while (var22 < par1InventoryCrafting.getSizeInventory()) {
                        ItemStack var26 = par1InventoryCrafting.getStackInSlot(var22);
                        if (var26 != null && var26.getItem() == Items.firework_charge && var26.hasTagCompound() && var26.getTagCompound().func_150297_b("Explosion", 10)) {
                            var25.appendTag(var26.getTagCompound().getCompoundTag("Explosion"));
                        }
                        ++var22;
                    }
                    var18.setTag("Explosions", var25);
                    var18.setByte("Flight", (byte)var4);
                    var15.setTag("Fireworks", var18);
                    this.field_92102_a.setTagCompound(var15);
                }
                return true;
            }
            if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1) {
                this.field_92102_a = new ItemStack(Items.firework_charge);
                NBTTagCompound var15 = new NBTTagCompound();
                var18 = new NBTTagCompound();
                int var21 = 0;
                ArrayList<Integer> var12 = new ArrayList<Integer>();
                int var13 = 0;
                while (var13 < par1InventoryCrafting.getSizeInventory()) {
                    ItemStack var14 = par1InventoryCrafting.getStackInSlot(var13);
                    if (var14 != null) {
                        if (var14.getItem() == Items.dye) {
                            var12.add(ItemDye.field_150922_c[var14.getItemDamage()]);
                        } else if (var14.getItem() == Items.glowstone_dust) {
                            var18.setBoolean("Flicker", true);
                        } else if (var14.getItem() == Items.diamond) {
                            var18.setBoolean("Trail", true);
                        } else if (var14.getItem() == Items.fire_charge) {
                            var21 = 1;
                        } else if (var14.getItem() == Items.feather) {
                            var21 = 4;
                        } else if (var14.getItem() == Items.gold_nugget) {
                            var21 = 2;
                        } else if (var14.getItem() == Items.skull) {
                            var21 = 3;
                        }
                    }
                    ++var13;
                }
                int[] var24 = new int[var12.size()];
                int var27 = 0;
                while (var27 < var24.length) {
                    var24[var27] = (Integer)var12.get(var27);
                    ++var27;
                }
                var18.setIntArray("Colors", var24);
                var18.setByte("Type", (byte)var21);
                var15.setTag("Explosion", var18);
                this.field_92102_a.setTagCompound(var15);
                return true;
            }
            if (var4 == 0 && var3 == 0 && var6 == 1 && var5 > 0 && var5 == var7) {
                ArrayList<Integer> var16 = new ArrayList<Integer>();
                int var20 = 0;
                while (var20 < par1InventoryCrafting.getSizeInventory()) {
                    ItemStack var11 = par1InventoryCrafting.getStackInSlot(var20);
                    if (var11 != null) {
                        if (var11.getItem() == Items.dye) {
                            var16.add(ItemDye.field_150922_c[var11.getItemDamage()]);
                        } else if (var11.getItem() == Items.firework_charge) {
                            this.field_92102_a = var11.copy();
                            this.field_92102_a.stackSize = 1;
                        }
                    }
                    ++var20;
                }
                int[] var17 = new int[var16.size()];
                int var19 = 0;
                while (var19 < var17.length) {
                    var17[var19] = (Integer)var16.get(var19);
                    ++var19;
                }
                if (this.field_92102_a != null && this.field_92102_a.hasTagCompound()) {
                    NBTTagCompound var23 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
                    if (var23 == null) {
                        return false;
                    }
                    var23.setIntArray("FadeColors", var17);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        return this.field_92102_a.copy();
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.field_92102_a;
    }
}

