/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.IRegistry;
import net.minecraft.world.World;

public class ItemArmor
extends Item {
    private static final int[] maxDamageArray = new int[]{11, 16, 15, 13};
    private static final String[] CLOTH_OVERLAY_NAMES = new String[]{"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
    public static final String[] EMPTY_SLOT_NAMES = new String[]{"empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots"};
    private static final IBehaviorDispenseItem dispenserBehavior = new BehaviorDefaultDispenseItem(){
        private static final String __OBFID = "CL_00001767";

        @Override
        protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
            EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
            int var4 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
            int var5 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
            int var6 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
            AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().getAABB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
            List var8 = par1IBlockSource.getWorld().selectEntitiesWithinAABB(EntityLivingBase.class, var7, new IEntitySelector.ArmoredMob(par2ItemStack));
            if (var8.size() > 0) {
                EntityLivingBase var9 = (EntityLivingBase)var8.get(0);
                int var10 = var9 instanceof EntityPlayer ? 1 : 0;
                int var11 = EntityLiving.getArmorPosition(par2ItemStack);
                ItemStack var12 = par2ItemStack.copy();
                var12.stackSize = 1;
                var9.setCurrentItemOrArmor(var11 - var10, var12);
                if (var9 instanceof EntityLiving) {
                    ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0f);
                }
                --par2ItemStack.stackSize;
                return par2ItemStack;
            }
            return super.dispenseStack(par1IBlockSource, par2ItemStack);
        }
    };
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final ArmorMaterial material;
    private IIcon overlayIcon;
    private IIcon emptySlotIcon;
    private static final String __OBFID = "CL_00001766";

    public ItemArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
        this.material = p_i45325_1_;
        this.armorType = p_i45325_3_;
        this.renderIndex = p_i45325_2_;
        this.damageReduceAmount = p_i45325_1_.getDamageReductionAmount(p_i45325_3_);
        this.setMaxDamage(p_i45325_1_.getDurability(p_i45325_3_));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        if (par2 > 0) {
            return 16777215;
        }
        int var3 = this.getColor(par1ItemStack);
        if (var3 < 0) {
            var3 = 16777215;
        }
        return var3;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        if (this.material == ArmorMaterial.CLOTH) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    public boolean hasColor(ItemStack par1ItemStack) {
        return this.material != ArmorMaterial.CLOTH ? false : (!par1ItemStack.hasTagCompound() ? false : (!par1ItemStack.getTagCompound().func_150297_b("display", 10) ? false : par1ItemStack.getTagCompound().getCompoundTag("display").func_150297_b("color", 3)));
    }

    public int getColor(ItemStack par1ItemStack) {
        if (this.material != ArmorMaterial.CLOTH) {
            return -1;
        }
        NBTTagCompound var2 = par1ItemStack.getTagCompound();
        if (var2 == null) {
            return 10511680;
        }
        NBTTagCompound var3 = var2.getCompoundTag("display");
        return var3 == null ? 10511680 : (var3.func_150297_b("color", 3) ? var3.getInteger("color") : 10511680);
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return par2 == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }

    public void removeColor(ItemStack par1ItemStack) {
        NBTTagCompound var2;
        NBTTagCompound var3;
        if (this.material == ArmorMaterial.CLOTH && (var2 = par1ItemStack.getTagCompound()) != null && (var3 = var2.getCompoundTag("display")).hasKey("color")) {
            var3.removeTag("color");
        }
    }

    public void func_82813_b(ItemStack par1ItemStack, int par2) {
        if (this.material != ArmorMaterial.CLOTH) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        NBTTagCompound var3 = par1ItemStack.getTagCompound();
        if (var3 == null) {
            var3 = new NBTTagCompound();
            par1ItemStack.setTagCompound(var3);
        }
        NBTTagCompound var4 = var3.getCompoundTag("display");
        if (!var3.func_150297_b("display", 10)) {
            var3.setTag("display", var4);
        }
        var4.setInteger("color", par2);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return this.material.func_151685_b() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        if (this.material == ArmorMaterial.CLOTH) {
            this.overlayIcon = par1IconRegister.registerIcon(CLOTH_OVERLAY_NAMES[this.armorType]);
        }
        this.emptySlotIcon = par1IconRegister.registerIcon(EMPTY_SLOT_NAMES[this.armorType]);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        int var4 = EntityLiving.getArmorPosition(par1ItemStack) - 1;
        ItemStack var5 = par3EntityPlayer.getCurrentArmor(var4);
        if (var5 == null) {
            par3EntityPlayer.setCurrentItemOrArmor(var4, par1ItemStack.copy());
            par1ItemStack.stackSize = 0;
        }
        return par1ItemStack;
    }

    public static IIcon func_94602_b(int par0) {
        switch (par0) {
            case 0: {
                return Items.diamond_helmet.emptySlotIcon;
            }
            case 1: {
                return Items.diamond_chestplate.emptySlotIcon;
            }
            case 2: {
                return Items.diamond_leggings.emptySlotIcon;
            }
            case 3: {
                return Items.diamond_boots.emptySlotIcon;
            }
        }
        return null;
    }

    public static enum ArmorMaterial {
        CLOTH("CLOTH", 0, 5, new int[]{1, 3, 2, 1}, 15),
        CHAIN("CHAIN", 1, 15, new int[]{2, 5, 4, 1}, 12),
        IRON("IRON", 2, 15, new int[]{2, 6, 5, 2}, 9),
        GOLD("GOLD", 3, 7, new int[]{2, 5, 3, 1}, 25),
        DIAMOND("DIAMOND", 4, 33, new int[]{3, 8, 6, 3}, 10);
        
        private int maxDamageFactor;
        private int[] damageReductionAmountArray;
        private int enchantability;
        private static final ArmorMaterial[] $VALUES;
        private static final String __OBFID = "CL_00001768";

        static {
            $VALUES = new ArmorMaterial[]{CLOTH, CHAIN, IRON, GOLD, DIAMOND};
        }

        private ArmorMaterial(String par1Str, int par2, String par3, int par4ArrayOfInteger, int par5, int[] arrn, int n2) {
            this.maxDamageFactor = par3;
            this.damageReductionAmountArray = par4ArrayOfInteger;
            this.enchantability = par5;
        }

        public int getDurability(int par1) {
            return maxDamageArray[par1] * this.maxDamageFactor;
        }

        public int getDamageReductionAmount(int par1) {
            return this.damageReductionAmountArray[par1];
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public Item func_151685_b() {
            return this == CLOTH ? Items.leather : (this == CHAIN ? Items.iron_ingot : (this == GOLD ? Items.gold_ingot : (this == IRON ? Items.iron_ingot : (this == DIAMOND ? Items.diamond : null))));
        }
    }

}

