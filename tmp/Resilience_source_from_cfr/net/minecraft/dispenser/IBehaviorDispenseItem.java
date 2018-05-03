/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.dispenser;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
    public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem(){
        private static final String __OBFID = "CL_00001200";

        @Override
        public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
            return par2ItemStack;
        }
    };

    public ItemStack dispense(IBlockSource var1, ItemStack var2);

}

