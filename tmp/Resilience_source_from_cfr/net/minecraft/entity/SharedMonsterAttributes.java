/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity;

import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes {
    private static final Logger logger = LogManager.getLogger();
    public static final IAttribute maxHealth = new RangedAttribute("generic.maxHealth", 20.0, 0.0, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true);
    public static final IAttribute followRange = new RangedAttribute("generic.followRange", 32.0, 0.0, 2048.0).setDescription("Follow Range");
    public static final IAttribute knockbackResistance = new RangedAttribute("generic.knockbackResistance", 0.0, 0.0, 1.0).setDescription("Knockback Resistance");
    public static final IAttribute movementSpeed = new RangedAttribute("generic.movementSpeed", 0.699999988079071, 0.0, Double.MAX_VALUE).setDescription("Movement Speed").setShouldWatch(true);
    public static final IAttribute attackDamage = new RangedAttribute("generic.attackDamage", 2.0, 0.0, Double.MAX_VALUE);
    private static final String __OBFID = "CL_00001695";

    public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap par0BaseAttributeMap) {
        NBTTagList var1 = new NBTTagList();
        for (IAttributeInstance var3 : par0BaseAttributeMap.getAllAttributes()) {
            var1.appendTag(SharedMonsterAttributes.writeAttributeInstanceToNBT(var3));
        }
        return var1;
    }

    private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance par0AttributeInstance) {
        NBTTagCompound var1 = new NBTTagCompound();
        IAttribute var2 = par0AttributeInstance.getAttribute();
        var1.setString("Name", var2.getAttributeUnlocalizedName());
        var1.setDouble("Base", par0AttributeInstance.getBaseValue());
        Collection var3 = par0AttributeInstance.func_111122_c();
        if (var3 != null && !var3.isEmpty()) {
            NBTTagList var4 = new NBTTagList();
            for (AttributeModifier var6 : var3) {
                if (!var6.isSaved()) continue;
                var4.appendTag(SharedMonsterAttributes.writeAttributeModifierToNBT(var6));
            }
            var1.setTag("Modifiers", var4);
        }
        return var1;
    }

    private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier par0AttributeModifier) {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("Name", par0AttributeModifier.getName());
        var1.setDouble("Amount", par0AttributeModifier.getAmount());
        var1.setInteger("Operation", par0AttributeModifier.getOperation());
        var1.setLong("UUIDMost", par0AttributeModifier.getID().getMostSignificantBits());
        var1.setLong("UUIDLeast", par0AttributeModifier.getID().getLeastSignificantBits());
        return var1;
    }

    public static void func_151475_a(BaseAttributeMap p_151475_0_, NBTTagList p_151475_1_) {
        int var2 = 0;
        while (var2 < p_151475_1_.tagCount()) {
            NBTTagCompound var3 = p_151475_1_.getCompoundTagAt(var2);
            IAttributeInstance var4 = p_151475_0_.getAttributeInstanceByName(var3.getString("Name"));
            if (var4 != null) {
                SharedMonsterAttributes.applyModifiersToAttributeInstance(var4, var3);
            } else {
                logger.warn("Ignoring unknown attribute '" + var3.getString("Name") + "'");
            }
            ++var2;
        }
    }

    private static void applyModifiersToAttributeInstance(IAttributeInstance par0AttributeInstance, NBTTagCompound par1NBTTagCompound) {
        par0AttributeInstance.setBaseValue(par1NBTTagCompound.getDouble("Base"));
        if (par1NBTTagCompound.func_150297_b("Modifiers", 9)) {
            NBTTagList var2 = par1NBTTagCompound.getTagList("Modifiers", 10);
            int var3 = 0;
            while (var3 < var2.tagCount()) {
                AttributeModifier var4 = SharedMonsterAttributes.readAttributeModifierFromNBT(var2.getCompoundTagAt(var3));
                AttributeModifier var5 = par0AttributeInstance.getModifier(var4.getID());
                if (var5 != null) {
                    par0AttributeInstance.removeModifier(var5);
                }
                par0AttributeInstance.applyModifier(var4);
                ++var3;
            }
        }
    }

    public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound par0NBTTagCompound) {
        UUID var1 = new UUID(par0NBTTagCompound.getLong("UUIDMost"), par0NBTTagCompound.getLong("UUIDLeast"));
        return new AttributeModifier(var1, par0NBTTagCompound.getString("Name"), par0NBTTagCompound.getDouble("Amount"), par0NBTTagCompound.getInteger("Operation"));
    }
}

