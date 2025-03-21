/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IRegistry;
import net.minecraft.world.World;

public class ItemMinecart
extends Item {
    private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem(){
        private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
        private static final String __OBFID = "CL_00000050";

        @Override
        public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
            double var15;
            int var12;
            int var13;
            EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
            World var4 = par1IBlockSource.getWorld();
            double var5 = par1IBlockSource.getX() + (double)((float)var3.getFrontOffsetX() * 1.125f);
            double var7 = par1IBlockSource.getY() + (double)((float)var3.getFrontOffsetY() * 1.125f);
            double var9 = par1IBlockSource.getZ() + (double)((float)var3.getFrontOffsetZ() * 1.125f);
            int var11 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
            Block var14 = var4.getBlock(var11, var12 = par1IBlockSource.getYInt() + var3.getFrontOffsetY(), var13 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ());
            if (BlockRailBase.func_150051_a(var14)) {
                var15 = 0.0;
            } else {
                if (var14.getMaterial() != Material.air || !BlockRailBase.func_150051_a(var4.getBlock(var11, var12 - 1, var13))) {
                    return this.behaviourDefaultDispenseItem.dispense(par1IBlockSource, par2ItemStack);
                }
                var15 = -1.0;
            }
            EntityMinecart var17 = EntityMinecart.createMinecart(var4, var5, var7 + var15, var9, ((ItemMinecart)par2ItemStack.getItem()).minecartType);
            if (par2ItemStack.hasDisplayName()) {
                var17.setMinecartName(par2ItemStack.getDisplayName());
            }
            var4.spawnEntityInWorld(var17);
            par2ItemStack.splitStack(1);
            return par2ItemStack;
        }

        @Override
        protected void playDispenseSound(IBlockSource par1IBlockSource) {
            par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
        }
    };
    public int minecartType;
    private static final String __OBFID = "CL_00000049";

    public ItemMinecart(int p_i45345_1_) {
        this.maxStackSize = 1;
        this.minecartType = p_i45345_1_;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (BlockRailBase.func_150051_a(par3World.getBlock(par4, par5, par6))) {
            if (!par3World.isClient) {
                EntityMinecart var11 = EntityMinecart.createMinecart(par3World, (float)par4 + 0.5f, (float)par5 + 0.5f, (float)par6 + 0.5f, this.minecartType);
                if (par1ItemStack.hasDisplayName()) {
                    var11.setMinecartName(par1ItemStack.getDisplayName());
                }
                par3World.spawnEntityInWorld(var11);
            }
            --par1ItemStack.stackSize;
            return true;
        }
        return false;
    }

}

