/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap
extends ItemMapBase {
    private static final String __OBFID = "CL_00000024";

    protected ItemEmptyMap() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        ItemStack var4 = new ItemStack(Items.filled_map, 1, par2World.getUniqueDataId("map"));
        String var5 = "map_" + var4.getItemDamage();
        MapData var6 = new MapData(var5);
        par2World.setItemData(var5, var6);
        var6.scale = 0;
        int var7 = 128 * (1 << var6.scale);
        var6.xCenter = (int)(Math.round(par3EntityPlayer.posX / (double)var7) * (long)var7);
        var6.zCenter = (int)(Math.round(par3EntityPlayer.posZ / (double)var7) * (long)var7);
        var6.dimension = (byte)par2World.provider.dimensionId;
        var6.markDirty();
        --par1ItemStack.stackSize;
        if (par1ItemStack.stackSize <= 0) {
            return var4;
        }
        if (!par3EntityPlayer.inventory.addItemStackToInventory(var4.copy())) {
            par3EntityPlayer.dropPlayerItemWithRandomChoice(var4, false);
        }
        return par1ItemStack;
    }
}

