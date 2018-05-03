/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFirework
extends Item {
    private static final String __OBFID = "CL_00000031";

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (!par3World.isClient) {
            EntityFireworkRocket var11 = new EntityFireworkRocket(par3World, (float)par4 + par8, (float)par5 + par9, (float)par6 + par10, par1ItemStack);
            par3World.spawnEntityInWorld(var11);
            if (!par2EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        NBTTagCompound var5;
        if (par1ItemStack.hasTagCompound() && (var5 = par1ItemStack.getTagCompound().getCompoundTag("Fireworks")) != null) {
            NBTTagList var6;
            if (var5.func_150297_b("Flight", 99)) {
                par3List.add(String.valueOf(StatCollector.translateToLocal("item.fireworks.flight")) + " " + var5.getByte("Flight"));
            }
            if ((var6 = var5.getTagList("Explosions", 10)) != null && var6.tagCount() > 0) {
                int var7 = 0;
                while (var7 < var6.tagCount()) {
                    NBTTagCompound var8 = var6.getCompoundTagAt(var7);
                    ArrayList<String> var9 = new ArrayList<String>();
                    ItemFireworkCharge.func_150902_a(var8, var9);
                    if (var9.size() > 0) {
                        int var10 = 1;
                        while (var10 < var9.size()) {
                            var9.set(var10, "  " + (String)var9.get(var10));
                            ++var10;
                        }
                        par3List.addAll(var9);
                    }
                    ++var7;
                }
            }
        }
    }
}

