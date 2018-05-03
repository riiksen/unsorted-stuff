/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemLeaves
extends ItemBlock {
    private final BlockLeaves field_150940_b;
    private static final String __OBFID = "CL_00000046";

    public ItemLeaves(BlockLeaves p_i45344_1_) {
        super(p_i45344_1_);
        this.field_150940_b = p_i45344_1_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int par1) {
        return par1 | 4;
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return this.field_150940_b.getIcon(0, par1);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return this.field_150940_b.getRenderColor(par1ItemStack.getItemDamage());
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        int var2 = par1ItemStack.getItemDamage();
        if (var2 < 0 || var2 >= this.field_150940_b.func_150125_e().length) {
            var2 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + this.field_150940_b.func_150125_e()[var2];
    }
}

