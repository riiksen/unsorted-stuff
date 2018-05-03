/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBow
extends Item {
    public static final String[] bowPullIconNameArray = new String[]{"pulling_0", "pulling_1", "pulling_2"};
    private IIcon[] iconArray;
    private static final String __OBFID = "CL_00001777";

    public ItemBow() {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
        boolean var5;
        boolean bl = var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
        if (var5 || par3EntityPlayer.inventory.hasItem(Items.arrow)) {
            int var9;
            int var10;
            int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
            float var7 = (float)var6 / 20.0f;
            if ((double)(var7 = (var7 * var7 + var7 * 2.0f) / 3.0f) < 0.1) {
                return;
            }
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0f);
            if (var7 == 1.0f) {
                var8.setIsCritical(true);
            }
            if ((var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack)) > 0) {
                var8.setDamage(var8.getDamage() + (double)var9 * 0.5 + 0.5);
            }
            if ((var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack)) > 0) {
                var8.setKnockbackStrength(var10);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
                var8.setFire(100);
            }
            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + var7 * 0.5f);
            if (var5) {
                var8.canBePickedUp = 2;
            } else {
                par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
            }
            if (!par2World.isClient) {
                par2World.spawnEntityInWorld(var8);
            }
        }
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Items.arrow)) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        return par1ItemStack;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_standby");
        this.iconArray = new IIcon[bowPullIconNameArray.length];
        int var2 = 0;
        while (var2 < this.iconArray.length) {
            this.iconArray[var2] = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_" + bowPullIconNameArray[var2]);
            ++var2;
        }
    }

    public IIcon getItemIconForUseDuration(int par1) {
        return this.iconArray[par1];
    }
}

