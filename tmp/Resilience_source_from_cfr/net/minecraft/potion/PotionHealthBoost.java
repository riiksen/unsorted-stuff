/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.potion.Potion;

public class PotionHealthBoost
extends Potion {
    private static final String __OBFID = "CL_00001526";

    public PotionHealthBoost(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        super.removeAttributesModifiersFromEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
        if (par1EntityLivingBase.getHealth() > par1EntityLivingBase.getMaxHealth()) {
            par1EntityLivingBase.setHealth(par1EntityLivingBase.getMaxHealth());
        }
    }
}

