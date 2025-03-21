/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes {
    private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();
    private static final String __OBFID = "CL_00000085";

    public static FurnaceRecipes smelting() {
        return smeltingBase;
    }

    private FurnaceRecipes() {
        this.func_151393_a(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7f);
        this.func_151393_a(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0f);
        this.func_151393_a(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0f);
        this.func_151393_a(Blocks.sand, new ItemStack(Blocks.glass), 0.1f);
        this.func_151396_a(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35f);
        this.func_151396_a(Items.beef, new ItemStack(Items.cooked_beef), 0.35f);
        this.func_151396_a(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35f);
        this.func_151393_a(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1f);
        this.func_151396_a(Items.clay_ball, new ItemStack(Items.brick), 0.3f);
        this.func_151393_a(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35f);
        this.func_151393_a(Blocks.cactus, new ItemStack(Items.dye, 1, 2), 0.2f);
        this.func_151393_a(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15f);
        this.func_151393_a(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15f);
        this.func_151393_a(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0f);
        this.func_151396_a(Items.potato, new ItemStack(Items.baked_potato), 0.35f);
        this.func_151393_a(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1f);
        ItemFishFood.FishType[] var1 = ItemFishFood.FishType.values();
        int var2 = var1.length;
        int var3 = 0;
        while (var3 < var2) {
            ItemFishFood.FishType var4 = var1[var3];
            if (var4.func_150973_i()) {
                this.func_151394_a(new ItemStack(Items.fish, 1, var4.func_150976_a()), new ItemStack(Items.cooked_fished, 1, var4.func_150976_a()), 0.35f);
            }
            ++var3;
        }
        this.func_151393_a(Blocks.coal_ore, new ItemStack(Items.coal), 0.1f);
        this.func_151393_a(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7f);
        this.func_151393_a(Blocks.lapis_ore, new ItemStack(Items.dye, 1, 4), 0.2f);
        this.func_151393_a(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2f);
    }

    public void func_151393_a(Block p_151393_1_, ItemStack p_151393_2_, float p_151393_3_) {
        this.func_151396_a(Item.getItemFromBlock(p_151393_1_), p_151393_2_, p_151393_3_);
    }

    public void func_151396_a(Item p_151396_1_, ItemStack p_151396_2_, float p_151396_3_) {
        this.func_151394_a(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
    }

    public void func_151394_a(ItemStack p_151394_1_, ItemStack p_151394_2_, float p_151394_3_) {
        this.smeltingList.put(p_151394_1_, p_151394_2_);
        this.experienceList.put(p_151394_2_, Float.valueOf(p_151394_3_));
    }

    public ItemStack func_151395_a(ItemStack p_151395_1_) {
        Map.Entry var3;
        Iterator var2 = this.smeltingList.entrySet().iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while (!this.func_151397_a(p_151395_1_, (ItemStack)(var3 = var2.next()).getKey()));
        return (ItemStack)var3.getValue();
    }

    private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_) {
        if (p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage())) {
            return true;
        }
        return false;
    }

    public Map getSmeltingList() {
        return this.smeltingList;
    }

    public float func_151398_b(ItemStack p_151398_1_) {
        Map.Entry var3;
        Iterator var2 = this.experienceList.entrySet().iterator();
        do {
            if (var2.hasNext()) continue;
            return 0.0f;
        } while (!this.func_151397_a(p_151398_1_, (ItemStack)(var3 = var2.next()).getKey()));
        return ((Float)var3.getValue()).floatValue();
    }
}

