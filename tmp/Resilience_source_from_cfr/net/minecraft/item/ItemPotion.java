/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 */
package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPotion
extends Item {
    private HashMap effectCache = new HashMap();
    private static final Map field_77835_b = new LinkedHashMap();
    private IIcon field_94591_c;
    private IIcon field_94590_d;
    private IIcon field_94592_ct;
    private static final String __OBFID = "CL_00000055";

    public ItemPotion() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }

    public List getEffects(ItemStack par1ItemStack) {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().func_150297_b("CustomPotionEffects", 9)) {
            ArrayList<PotionEffect> var7 = new ArrayList<PotionEffect>();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
            int var4 = 0;
            while (var4 < var3.tagCount()) {
                NBTTagCompound var5 = var3.getCompoundTagAt(var4);
                PotionEffect var6 = PotionEffect.readCustomPotionEffectFromNBT(var5);
                if (var6 != null) {
                    var7.add(var6);
                }
                ++var4;
            }
            return var7;
        }
        List var2 = (List)this.effectCache.get(par1ItemStack.getItemDamage());
        if (var2 == null) {
            var2 = PotionHelper.getPotionEffects(par1ItemStack.getItemDamage(), false);
            this.effectCache.put(par1ItemStack.getItemDamage(), var2);
        }
        return var2;
    }

    public List getEffects(int par1) {
        List var2 = (List)this.effectCache.get(par1);
        if (var2 == null) {
            var2 = PotionHelper.getPotionEffects(par1, false);
            this.effectCache.put(par1, var2);
        }
        return var2;
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        List var4;
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        if (!par2World.isClient && (var4 = this.getEffects(par1ItemStack)) != null) {
            for (PotionEffect var6 : var4) {
                par3EntityPlayer.addPotionEffect(new PotionEffect(var6));
            }
        }
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            if (par1ItemStack.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }
            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (ItemPotion.isSplash(par1ItemStack.getItemDamage())) {
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!par2World.isClient) {
                par2World.spawnEntityInWorld(new EntityPotion(par2World, (EntityLivingBase)par3EntityPlayer, par1ItemStack));
            }
            return par1ItemStack;
        }
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        return false;
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return ItemPotion.isSplash(par1) ? this.field_94591_c : this.field_94590_d;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return par2 == 0 ? this.field_94592_ct : super.getIconFromDamageForRenderPass(par1, par2);
    }

    public static boolean isSplash(int par0) {
        if ((par0 & 16384) != 0) {
            return true;
        }
        return false;
    }

    public int getColorFromDamage(int par1) {
        return PotionHelper.func_77915_a(par1, false);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return par2 > 0 ? 16777215 : this.getColorFromDamage(par1ItemStack.getItemDamage());
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public boolean isEffectInstant(int par1) {
        List var2 = this.getEffects(par1);
        if (var2 != null && !var2.isEmpty()) {
            PotionEffect var4;
            Iterator var3 = var2.iterator();
            do {
                if (var3.hasNext()) continue;
                return false;
            } while (!Potion.potionTypes[(var4 = (PotionEffect)var3.next()).getPotionID()].isInstant());
            return true;
        }
        return false;
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        List var3;
        if (par1ItemStack.getItemDamage() == 0) {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }
        String var2 = "";
        if (ItemPotion.isSplash(par1ItemStack.getItemDamage())) {
            var2 = String.valueOf(StatCollector.translateToLocal("potion.prefix.grenade").trim()) + " ";
        }
        if ((var3 = Items.potionitem.getEffects(par1ItemStack)) != null && !var3.isEmpty()) {
            String var4 = ((PotionEffect)var3.get(0)).getEffectName();
            var4 = String.valueOf(var4) + ".postfix";
            return String.valueOf(var2) + StatCollector.translateToLocal(var4).trim();
        }
        String var4 = PotionHelper.func_77905_c(par1ItemStack.getItemDamage());
        return String.valueOf(StatCollector.translateToLocal(var4).trim()) + " " + super.getItemStackDisplayName(par1ItemStack);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (par1ItemStack.getItemDamage() != 0) {
            List var5 = Items.potionitem.getEffects(par1ItemStack);
            HashMultimap var6 = HashMultimap.create();
            if (var5 != null && !var5.isEmpty()) {
                for (PotionEffect var8 : var5) {
                    String var9 = StatCollector.translateToLocal(var8.getEffectName()).trim();
                    Potion var10 = Potion.potionTypes[var8.getPotionID()];
                    Map var11 = var10.func_111186_k();
                    if (var11 != null && var11.size() > 0) {
                        for (Map.Entry var13 : var11.entrySet()) {
                            AttributeModifier var14 = (AttributeModifier)var13.getValue();
                            AttributeModifier var15 = new AttributeModifier(var14.getName(), var10.func_111183_a(var8.getAmplifier(), var14), var14.getOperation());
                            var6.put((Object)((IAttribute)var13.getKey()).getAttributeUnlocalizedName(), (Object)var15);
                        }
                    }
                    if (var8.getAmplifier() > 0) {
                        var9 = String.valueOf(var9) + " " + StatCollector.translateToLocal(new StringBuilder("potion.potency.").append(var8.getAmplifier()).toString()).trim();
                    }
                    if (var8.getDuration() > 20) {
                        var9 = String.valueOf(var9) + " (" + Potion.getDurationString(var8) + ")";
                    }
                    if (var10.isBadEffect()) {
                        par3List.add((Object)((Object)EnumChatFormatting.RED) + var9);
                        continue;
                    }
                    par3List.add((Object)((Object)EnumChatFormatting.GRAY) + var9);
                }
            } else {
                String var7 = StatCollector.translateToLocal("potion.empty").trim();
                par3List.add((Object)((Object)EnumChatFormatting.GRAY) + var7);
            }
            if (!var6.isEmpty()) {
                par3List.add("");
                par3List.add((Object)((Object)EnumChatFormatting.DARK_PURPLE) + StatCollector.translateToLocal("potion.effects.whenDrank"));
                for (Map.Entry var17 : var6.entries()) {
                    AttributeModifier var18 = (AttributeModifier)var17.getValue();
                    double var19 = var18.getAmount();
                    double var20 = var18.getOperation() != 1 && var18.getOperation() != 2 ? var18.getAmount() : var18.getAmount() * 100.0;
                    if (var19 > 0.0) {
                        par3List.add((Object)((Object)EnumChatFormatting.BLUE) + StatCollector.translateToLocalFormatted(new StringBuilder("attribute.modifier.plus.").append(var18.getOperation()).toString(), ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal(new StringBuilder("attribute.name.").append((String)var17.getKey()).toString())));
                        continue;
                    }
                    if (var19 >= 0.0) continue;
                    par3List.add((Object)((Object)EnumChatFormatting.RED) + StatCollector.translateToLocalFormatted(new StringBuilder("attribute.modifier.take.").append(var18.getOperation()).toString(), ItemStack.field_111284_a.format(var20 *= -1.0), StatCollector.translateToLocal(new StringBuilder("attribute.name.").append((String)var17.getKey()).toString())));
                }
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack) {
        List var2 = this.getEffects(par1ItemStack);
        if (var2 != null && !var2.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        int var5;
        super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
        if (field_77835_b.isEmpty()) {
            int var4 = 0;
            while (var4 <= 15) {
                var5 = 0;
                while (var5 <= 1) {
                    int var6 = var5 == 0 ? var4 | 8192 : var4 | 16384;
                    int var7 = 0;
                    while (var7 <= 2) {
                        List var9;
                        int var8 = var6;
                        if (var7 != 0) {
                            if (var7 == 1) {
                                var8 = var6 | 32;
                            } else if (var7 == 2) {
                                var8 = var6 | 64;
                            }
                        }
                        if ((var9 = PotionHelper.getPotionEffects(var8, false)) != null && !var9.isEmpty()) {
                            field_77835_b.put(var9, var8);
                        }
                        ++var7;
                    }
                    ++var5;
                }
                ++var4;
            }
        }
        Iterator var10 = field_77835_b.values().iterator();
        while (var10.hasNext()) {
            var5 = (Integer)var10.next();
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5));
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.field_94590_d = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_" + "bottle_drinkable");
        this.field_94591_c = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_" + "bottle_splash");
        this.field_94592_ct = par1IconRegister.registerIcon(String.valueOf(this.getIconString()) + "_" + "overlay");
    }

    public static IIcon func_94589_d(String par0Str) {
        return par0Str.equals("bottle_drinkable") ? Items.potionitem.field_94590_d : (par0Str.equals("bottle_splash") ? Items.potionitem.field_94591_c : (par0Str.equals("overlay") ? Items.potionitem.field_94592_ct : null));
    }
}

