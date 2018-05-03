/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 */
package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTool
extends Item {
    private Set field_150914_c;
    protected float efficiencyOnProperMaterial = 4.0f;
    private float damageVsEntity;
    protected Item.ToolMaterial toolMaterial;
    private static final String __OBFID = "CL_00000019";

    protected ItemTool(float p_i45333_1_, Item.ToolMaterial p_i45333_2_, Set p_i45333_3_) {
        this.toolMaterial = p_i45333_2_;
        this.field_150914_c = p_i45333_3_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45333_2_.getMaxUses());
        this.efficiencyOnProperMaterial = p_i45333_2_.getEfficiencyOnProperMaterial();
        this.damageVsEntity = p_i45333_1_ + p_i45333_2_.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_) {
        return this.field_150914_c.contains(p_150893_2_) ? this.efficiencyOnProperMaterial : 1.0f;
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        par1ItemStack.damageItem(2, par3EntityLivingBase);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0) {
            p_150894_1_.damageItem(1, p_150894_7_);
        }
        return true;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    public Item.ToolMaterial func_150913_i() {
        return this.toolMaterial;
    }

    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }

    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return this.toolMaterial.func_150995_f() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap var1 = super.getItemAttributeModifiers();
        var1.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
        return var1;
    }
}

