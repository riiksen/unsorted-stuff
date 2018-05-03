/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class ItemEditableBook
extends Item {
    private static final String __OBFID = "CL_00000077";

    public ItemEditableBook() {
        this.setMaxStackSize(1);
    }

    public static boolean validBookTagContents(NBTTagCompound par0NBTTagCompound) {
        if (!ItemWritableBook.func_150930_a(par0NBTTagCompound)) {
            return false;
        }
        if (!par0NBTTagCompound.func_150297_b("title", 8)) {
            return false;
        }
        String var1 = par0NBTTagCompound.getString("title");
        return var1 != null && var1.length() <= 16 ? par0NBTTagCompound.func_150297_b("author", 8) : false;
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String var3;
        NBTTagCompound var2;
        if (par1ItemStack.hasTagCompound() && !StringUtils.isNullOrEmpty(var3 = (var2 = par1ItemStack.getTagCompound()).getString("title"))) {
            return var3;
        }
        return super.getItemStackDisplayName(par1ItemStack);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        String var6;
        NBTTagCompound var5;
        if (par1ItemStack.hasTagCompound() && !StringUtils.isNullOrEmpty(var6 = (var5 = par1ItemStack.getTagCompound()).getString("author"))) {
            par3List.add((Object)((Object)EnumChatFormatting.GRAY) + StatCollector.translateToLocalFormatted("book.byAuthor", var6));
        }
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

    @Override
    public boolean hasEffect(ItemStack par1ItemStack) {
        return true;
    }
}

