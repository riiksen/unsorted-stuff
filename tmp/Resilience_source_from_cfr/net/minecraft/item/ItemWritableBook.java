/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemWritableBook
extends Item {
    private static final String __OBFID = "CL_00000076";

    public ItemWritableBook() {
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.displayGUIBook(par1ItemStack);
        return par1ItemStack;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    public static boolean func_150930_a(NBTTagCompound p_150930_0_) {
        if (p_150930_0_ == null) {
            return false;
        }
        if (!p_150930_0_.func_150297_b("pages", 9)) {
            return false;
        }
        NBTTagList var1 = p_150930_0_.getTagList("pages", 8);
        int var2 = 0;
        while (var2 < var1.tagCount()) {
            String var3 = var1.getStringTagAt(var2);
            if (var3 == null) {
                return false;
            }
            if (var3.length() > 256) {
                return false;
            }
            ++var2;
        }
        return true;
    }
}

