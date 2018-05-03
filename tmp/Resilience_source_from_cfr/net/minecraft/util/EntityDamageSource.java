/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class EntityDamageSource
extends DamageSource {
    protected Entity damageSourceEntity;
    private static final String __OBFID = "CL_00001522";

    public EntityDamageSource(String par1Str, Entity par2Entity) {
        super(par1Str);
        this.damageSourceEntity = par2Entity;
    }

    @Override
    public Entity getEntity() {
        return this.damageSourceEntity;
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase p_151519_1_) {
        ItemStack var2 = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
        String var3 = "death.attack." + this.damageType;
        String var4 = String.valueOf(var3) + ".item";
        return var2 != null && var2.hasDisplayName() && StatCollector.canTranslate(var4) ? new ChatComponentTranslation(var4, p_151519_1_.func_145748_c_(), this.damageSourceEntity.func_145748_c_(), var2.func_151000_E()) : new ChatComponentTranslation(var3, p_151519_1_.func_145748_c_(), this.damageSourceEntity.func_145748_c_());
    }

    @Override
    public boolean isDifficultyScaled() {
        if (this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer)) {
            return true;
        }
        return false;
    }
}

