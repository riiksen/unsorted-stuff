/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMonsterPlacer
extends Item {
    private IIcon theIcon;
    private static final String __OBFID = "CL_00000070";

    public ItemMonsterPlacer() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String var2 = StatCollector.translateToLocal(new StringBuilder(String.valueOf(this.getUnlocalizedName())).append(".name").toString()).trim();
        String var3 = EntityList.getStringFromID(par1ItemStack.getItemDamage());
        if (var3 != null) {
            var2 = String.valueOf(var2) + " " + StatCollector.translateToLocal(new StringBuilder("entity.").append(var3).append(".name").toString());
        }
        return var2;
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        EntityList.EntityEggInfo var3 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(par1ItemStack.getItemDamage());
        return var3 != null ? (par2 == 0 ? var3.primaryColor : var3.secondaryColor) : 16777215;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        Entity var14;
        if (par3World.isClient) {
            return true;
        }
        Block var11 = par3World.getBlock(par4, par5, par6);
        par4 += Facing.offsetsXForSide[par7];
        par5 += Facing.offsetsYForSide[par7];
        par6 += Facing.offsetsZForSide[par7];
        double var12 = 0.0;
        if (par7 == 1 && var11.getRenderType() == 11) {
            var12 = 0.5;
        }
        if ((var14 = ItemMonsterPlacer.spawnCreature(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5, (double)par5 + var12, (double)par6 + 0.5)) != null) {
            if (var14 instanceof EntityLivingBase && par1ItemStack.hasDisplayName()) {
                ((EntityLiving)var14).setCustomNameTag(par1ItemStack.getDisplayName());
            }
            if (!par2EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par2World.isClient) {
            return par1ItemStack;
        }
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        if (var4 == null) {
            return par1ItemStack;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Entity var8;
            int var5 = var4.blockX;
            int var6 = var4.blockY;
            int var7 = var4.blockZ;
            if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
                return par1ItemStack;
            }
            if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
                return par1ItemStack;
            }
            if (par2World.getBlock(var5, var6, var7) instanceof BlockLiquid && (var8 = ItemMonsterPlacer.spawnCreature(par2World, par1ItemStack.getItemDamage(), var5, var6, var7)) != null) {
                if (var8 instanceof EntityLivingBase && par1ItemStack.hasDisplayName()) {
                    ((EntityLiving)var8).setCustomNameTag(par1ItemStack.getDisplayName());
                }
                if (!par3EntityPlayer.capabilities.isCreativeMode) {
                    --par1ItemStack.stackSize;
                }
            }
        }
        return par1ItemStack;
    }

    public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6) {
        if (!EntityList.entityEggs.containsKey(par1)) {
            return null;
        }
        Entity var8 = null;
        int var9 = 0;
        while (var9 < 1) {
            var8 = EntityList.createEntityByID(par1, par0World);
            if (var8 != null && var8 instanceof EntityLivingBase) {
                EntityLiving var10 = (EntityLiving)var8;
                var8.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0f), 0.0f);
                var10.rotationYawHead = var10.rotationYaw;
                var10.renderYawOffset = var10.rotationYaw;
                var10.onSpawnWithEgg(null);
                par0World.spawnEntityInWorld(var8);
                var10.playLivingSound();
            }
            ++var9;
        }
        return var8;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        for (EntityList.EntityEggInfo var5 : EntityList.entityEggs.values()) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5.spawnedID));
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_overlay");
    }
}

