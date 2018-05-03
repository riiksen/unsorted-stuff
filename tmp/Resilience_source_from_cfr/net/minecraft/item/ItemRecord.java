/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRecord
extends Item {
    private static final Map field_150928_b = new HashMap();
    public final String field_150929_a;
    private static final String __OBFID = "CL_00000057";

    protected ItemRecord(String p_i45350_1_) {
        this.field_150929_a = p_i45350_1_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        field_150928_b.put(p_i45350_1_, this);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return this.itemIcon;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par3World.getBlock(par4, par5, par6) == Blocks.jukebox && par3World.getBlockMetadata(par4, par5, par6) == 0) {
            if (par3World.isClient) {
                return true;
            }
            ((BlockJukebox)Blocks.jukebox).func_149926_b(par3World, par4, par5, par6, par1ItemStack);
            par3World.playAuxSFXAtEntity(null, 1005, par4, par5, par6, Item.getIdFromItem(this));
            --par1ItemStack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(this.func_150927_i());
    }

    public String func_150927_i() {
        return StatCollector.translateToLocal("item.record." + this.field_150929_a + ".desc");
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    public static ItemRecord func_150926_b(String p_150926_0_) {
        return (ItemRecord)field_150928_b.get(p_150926_0_);
    }
}

