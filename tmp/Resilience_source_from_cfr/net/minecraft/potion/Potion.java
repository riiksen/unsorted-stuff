/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionAbsoption;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHealth;
import net.minecraft.potion.PotionHealthBoost;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class Potion {
    public static final Potion[] potionTypes = new Potion[32];
    public static final Potion field_76423_b = null;
    public static final Potion moveSpeed = new Potion(1, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224, 2);
    public static final Potion moveSlowdown = new Potion(2, true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, 2);
    public static final Potion digSpeed = new Potion(3, false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5);
    public static final Potion digSlowdown = new Potion(4, true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
    public static final Potion damageBoost = new PotionAttackDamage(5, false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0, 2);
    public static final Potion heal = new PotionHealth(6, false, 16262179).setPotionName("potion.heal");
    public static final Potion harm = new PotionHealth(7, true, 4393481).setPotionName("potion.harm");
    public static final Potion jump = new Potion(8, false, 7889559).setPotionName("potion.jump").setIconIndex(2, 1);
    public static final Potion confusion = new Potion(9, true, 5578058).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25);
    public static final Potion regeneration = new Potion(10, false, 13458603).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25);
    public static final Potion resistance = new Potion(11, false, 10044730).setPotionName("potion.resistance").setIconIndex(6, 1);
    public static final Potion fireResistance = new Potion(12, false, 14981690).setPotionName("potion.fireResistance").setIconIndex(7, 1);
    public static final Potion waterBreathing = new Potion(13, false, 3035801).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
    public static final Potion invisibility = new Potion(14, false, 8356754).setPotionName("potion.invisibility").setIconIndex(0, 1);
    public static final Potion blindness = new Potion(15, true, 2039587).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25);
    public static final Potion nightVision = new Potion(16, false, 2039713).setPotionName("potion.nightVision").setIconIndex(4, 1);
    public static final Potion hunger = new Potion(17, true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
    public static final Potion weakness = new PotionAttackDamage(18, true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
    public static final Potion poison = new Potion(19, true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25);
    public static final Potion wither = new Potion(20, true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25);
    public static final Potion field_76434_w = new PotionHealthBoost(21, false, 16284963).setPotionName("potion.healthBoost").setIconIndex(2, 2).func_111184_a(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
    public static final Potion field_76444_x = new PotionAbsoption(22, false, 2445989).setPotionName("potion.absorption").setIconIndex(2, 2);
    public static final Potion field_76443_y = new PotionHealth(23, false, 16262179).setPotionName("potion.saturation");
    public static final Potion field_76442_z = null;
    public static final Potion field_76409_A = null;
    public static final Potion field_76410_B = null;
    public static final Potion field_76411_C = null;
    public static final Potion field_76405_D = null;
    public static final Potion field_76406_E = null;
    public static final Potion field_76407_F = null;
    public static final Potion field_76408_G = null;
    public final int id;
    private final Map field_111188_I = Maps.newHashMap();
    private final boolean isBadEffect;
    private final int liquidColor;
    private String name = "";
    private int statusIconIndex = -1;
    private double effectiveness;
    private boolean usable;
    private static final String __OBFID = "CL_00001528";

    protected Potion(int par1, boolean par2, int par3) {
        this.id = par1;
        Potion.potionTypes[par1] = this;
        this.isBadEffect = par2;
        this.effectiveness = par2 ? 0.5 : 1.0;
        this.liquidColor = par3;
    }

    protected Potion setIconIndex(int par1, int par2) {
        this.statusIconIndex = par1 + par2 * 8;
        return this;
    }

    public int getId() {
        return this.id;
    }

    public void performEffect(EntityLivingBase par1EntityLivingBase, int par2) {
        if (this.id == Potion.regeneration.id) {
            if (par1EntityLivingBase.getHealth() < par1EntityLivingBase.getMaxHealth()) {
                par1EntityLivingBase.heal(1.0f);
            }
        } else if (this.id == Potion.poison.id) {
            if (par1EntityLivingBase.getHealth() > 1.0f) {
                par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 1.0f);
            }
        } else if (this.id == Potion.wither.id) {
            par1EntityLivingBase.attackEntityFrom(DamageSource.wither, 1.0f);
        } else if (this.id == Potion.hunger.id && par1EntityLivingBase instanceof EntityPlayer) {
            ((EntityPlayer)par1EntityLivingBase).addExhaustion(0.025f * (float)(par2 + 1));
        } else if (this.id == Potion.field_76443_y.id && par1EntityLivingBase instanceof EntityPlayer) {
            if (!par1EntityLivingBase.worldObj.isClient) {
                ((EntityPlayer)par1EntityLivingBase).getFoodStats().addStats(par2 + 1, 1.0f);
            }
        } else if (!(this.id == Potion.heal.id && !par1EntityLivingBase.isEntityUndead() || this.id == Potion.harm.id && par1EntityLivingBase.isEntityUndead())) {
            if (this.id == Potion.harm.id && !par1EntityLivingBase.isEntityUndead() || this.id == Potion.heal.id && par1EntityLivingBase.isEntityUndead()) {
                par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 6 << par2);
            }
        } else {
            par1EntityLivingBase.heal(Math.max(4 << par2, 0));
        }
    }

    public void affectEntity(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase, int par3, double par4) {
        if (!(this.id == Potion.heal.id && !par2EntityLivingBase.isEntityUndead() || this.id == Potion.harm.id && par2EntityLivingBase.isEntityUndead())) {
            if (this.id == Potion.harm.id && !par2EntityLivingBase.isEntityUndead() || this.id == Potion.heal.id && par2EntityLivingBase.isEntityUndead()) {
                int var6 = (int)(par4 * (double)(6 << par3) + 0.5);
                if (par1EntityLivingBase == null) {
                    par2EntityLivingBase.attackEntityFrom(DamageSource.magic, var6);
                } else {
                    par2EntityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par2EntityLivingBase, par1EntityLivingBase), var6);
                }
            }
        } else {
            int var6 = (int)(par4 * (double)(4 << par3) + 0.5);
            par2EntityLivingBase.heal(var6);
        }
    }

    public boolean isInstant() {
        return false;
    }

    public boolean isReady(int par1, int par2) {
        if (this.id == Potion.regeneration.id) {
            int var3 = 50 >> par2;
            return var3 > 0 ? par1 % var3 == 0 : true;
        }
        if (this.id == Potion.poison.id) {
            int var3 = 25 >> par2;
            return var3 > 0 ? par1 % var3 == 0 : true;
        }
        if (this.id == Potion.wither.id) {
            int var3 = 40 >> par2;
            return var3 > 0 ? par1 % var3 == 0 : true;
        }
        if (this.id == Potion.hunger.id) {
            return true;
        }
        return false;
    }

    public Potion setPotionName(String par1Str) {
        this.name = par1Str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public boolean hasStatusIcon() {
        if (this.statusIconIndex >= 0) {
            return true;
        }
        return false;
    }

    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }

    public boolean isBadEffect() {
        return this.isBadEffect;
    }

    public static String getDurationString(PotionEffect par0PotionEffect) {
        if (par0PotionEffect.getIsPotionDurationMax()) {
            return "**:**";
        }
        int var1 = par0PotionEffect.getDuration();
        return StringUtils.ticksToElapsedTime(var1);
    }

    protected Potion setEffectiveness(double par1) {
        this.effectiveness = par1;
        return this;
    }

    public double getEffectiveness() {
        return this.effectiveness;
    }

    public boolean isUsable() {
        return this.usable;
    }

    public int getLiquidColor() {
        return this.liquidColor;
    }

    public Potion func_111184_a(IAttribute par1Attribute, String par2Str, double par3, int par5) {
        AttributeModifier var6 = new AttributeModifier(UUID.fromString(par2Str), this.getName(), par3, par5);
        this.field_111188_I.put(par1Attribute, var6);
        return this;
    }

    public Map func_111186_k() {
        return this.field_111188_I;
    }

    public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        for (Map.Entry var5 : this.field_111188_I.entrySet()) {
            IAttributeInstance var6 = par2BaseAttributeMap.getAttributeInstance((IAttribute)var5.getKey());
            if (var6 == null) continue;
            var6.removeModifier((AttributeModifier)var5.getValue());
        }
    }

    public void applyAttributesModifiersToEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        for (Map.Entry var5 : this.field_111188_I.entrySet()) {
            IAttributeInstance var6 = par2BaseAttributeMap.getAttributeInstance((IAttribute)var5.getKey());
            if (var6 == null) continue;
            AttributeModifier var7 = (AttributeModifier)var5.getValue();
            var6.removeModifier(var7);
            var6.applyModifier(new AttributeModifier(var7.getID(), String.valueOf(this.getName()) + " " + par3, this.func_111183_a(par3, var7), var7.getOperation()));
        }
    }

    public double func_111183_a(int par1, AttributeModifier par2AttributeModifier) {
        return par2AttributeModifier.getAmount() * (double)(par1 + 1);
    }
}

