/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.village;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.village.MerchantRecipe;

public class MerchantRecipeList
extends ArrayList {
    private static final String __OBFID = "CL_00000127";

    public MerchantRecipeList() {
    }

    public MerchantRecipeList(NBTTagCompound par1NBTTagCompound) {
        this.readRecipiesFromTags(par1NBTTagCompound);
    }

    public MerchantRecipe canRecipeBeUsed(ItemStack par1ItemStack, ItemStack par2ItemStack, int par3) {
        if (par3 > 0 && par3 < this.size()) {
            MerchantRecipe var6 = (MerchantRecipe)this.get(par3);
            return par1ItemStack.getItem() == var6.getItemToBuy().getItem() && (par2ItemStack == null && !var6.hasSecondItemToBuy() || var6.hasSecondItemToBuy() && par2ItemStack != null && var6.getSecondItemToBuy().getItem() == par2ItemStack.getItem()) && par1ItemStack.stackSize >= var6.getItemToBuy().stackSize && (!var6.hasSecondItemToBuy() || par2ItemStack.stackSize >= var6.getSecondItemToBuy().stackSize) ? var6 : null;
        }
        int var4 = 0;
        while (var4 < this.size()) {
            MerchantRecipe var5 = (MerchantRecipe)this.get(var4);
            if (par1ItemStack.getItem() == var5.getItemToBuy().getItem() && par1ItemStack.stackSize >= var5.getItemToBuy().stackSize && (!var5.hasSecondItemToBuy() && par2ItemStack == null || var5.hasSecondItemToBuy() && par2ItemStack != null && var5.getSecondItemToBuy().getItem() == par2ItemStack.getItem() && par2ItemStack.stackSize >= var5.getSecondItemToBuy().stackSize)) {
                return var5;
            }
            ++var4;
        }
        return null;
    }

    public void addToListWithCheck(MerchantRecipe par1MerchantRecipe) {
        int var2 = 0;
        while (var2 < this.size()) {
            MerchantRecipe var3 = (MerchantRecipe)this.get(var2);
            if (par1MerchantRecipe.hasSameIDsAs(var3)) {
                if (par1MerchantRecipe.hasSameItemsAs(var3)) {
                    this.set(var2, par1MerchantRecipe);
                }
                return;
            }
            ++var2;
        }
        this.add(par1MerchantRecipe);
    }

    public void func_151391_a(PacketBuffer p_151391_1_) throws IOException {
        p_151391_1_.writeByte((byte)(this.size() & 255));
        int var2 = 0;
        while (var2 < this.size()) {
            MerchantRecipe var3 = (MerchantRecipe)this.get(var2);
            p_151391_1_.writeItemStackToBuffer(var3.getItemToBuy());
            p_151391_1_.writeItemStackToBuffer(var3.getItemToSell());
            ItemStack var4 = var3.getSecondItemToBuy();
            p_151391_1_.writeBoolean(var4 != null);
            if (var4 != null) {
                p_151391_1_.writeItemStackToBuffer(var4);
            }
            p_151391_1_.writeBoolean(var3.isRecipeDisabled());
            ++var2;
        }
    }

    public static MerchantRecipeList func_151390_b(PacketBuffer p_151390_0_) throws IOException {
        MerchantRecipeList var1 = new MerchantRecipeList();
        int var2 = p_151390_0_.readByte() & 255;
        int var3 = 0;
        while (var3 < var2) {
            ItemStack var4 = p_151390_0_.readItemStackFromBuffer();
            ItemStack var5 = p_151390_0_.readItemStackFromBuffer();
            ItemStack var6 = null;
            if (p_151390_0_.readBoolean()) {
                var6 = p_151390_0_.readItemStackFromBuffer();
            }
            boolean var7 = p_151390_0_.readBoolean();
            MerchantRecipe var8 = new MerchantRecipe(var4, var6, var5);
            if (var7) {
                var8.func_82785_h();
            }
            var1.add(var8);
            ++var3;
        }
        return var1;
    }

    public void readRecipiesFromTags(NBTTagCompound par1NBTTagCompound) {
        NBTTagList var2 = par1NBTTagCompound.getTagList("Recipes", 10);
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            this.add(new MerchantRecipe(var4));
            ++var3;
        }
    }

    public NBTTagCompound getRecipiesAsTags() {
        NBTTagCompound var1 = new NBTTagCompound();
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.size()) {
            MerchantRecipe var4 = (MerchantRecipe)this.get(var3);
            var2.appendTag(var4.writeToTags());
            ++var3;
        }
        var1.setTag("Recipes", var2);
        return var1;
    }
}

