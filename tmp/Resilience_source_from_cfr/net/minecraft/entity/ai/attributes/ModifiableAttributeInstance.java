/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class ModifiableAttributeInstance
implements IAttributeInstance {
    private final BaseAttributeMap attributeMap;
    private final IAttribute genericAttribute;
    private final Map mapByOperation = Maps.newHashMap();
    private final Map mapByName = Maps.newHashMap();
    private final Map mapByUUID = Maps.newHashMap();
    private double baseValue;
    private boolean needsUpdate = true;
    private double cachedValue;
    private static final String __OBFID = "CL_00001567";

    public ModifiableAttributeInstance(BaseAttributeMap par1BaseAttributeMap, IAttribute par2Attribute) {
        this.attributeMap = par1BaseAttributeMap;
        this.genericAttribute = par2Attribute;
        this.baseValue = par2Attribute.getDefaultValue();
        int var3 = 0;
        while (var3 < 3) {
            this.mapByOperation.put(var3, new HashSet());
            ++var3;
        }
    }

    @Override
    public IAttribute getAttribute() {
        return this.genericAttribute;
    }

    @Override
    public double getBaseValue() {
        return this.baseValue;
    }

    @Override
    public void setBaseValue(double par1) {
        if (par1 != this.getBaseValue()) {
            this.baseValue = par1;
            this.flagForUpdate();
        }
    }

    public Collection getModifiersByOperation(int par1) {
        return (Collection)this.mapByOperation.get(par1);
    }

    @Override
    public Collection func_111122_c() {
        HashSet var1 = new HashSet();
        int var2 = 0;
        while (var2 < 3) {
            var1.addAll(this.getModifiersByOperation(var2));
            ++var2;
        }
        return var1;
    }

    @Override
    public AttributeModifier getModifier(UUID par1UUID) {
        return (AttributeModifier)this.mapByUUID.get(par1UUID);
    }

    @Override
    public void applyModifier(AttributeModifier par1AttributeModifier) {
        if (this.getModifier(par1AttributeModifier.getID()) != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        HashSet var2 = (HashSet)this.mapByName.get(par1AttributeModifier.getName());
        if (var2 == null) {
            var2 = new HashSet();
            this.mapByName.put(par1AttributeModifier.getName(), var2);
        }
        ((Set)this.mapByOperation.get(par1AttributeModifier.getOperation())).add(par1AttributeModifier);
        ((Set)var2).add(par1AttributeModifier);
        this.mapByUUID.put(par1AttributeModifier.getID(), par1AttributeModifier);
        this.flagForUpdate();
    }

    private void flagForUpdate() {
        this.needsUpdate = true;
        this.attributeMap.addAttributeInstance(this);
    }

    @Override
    public void removeModifier(AttributeModifier par1AttributeModifier) {
        int var2 = 0;
        while (var2 < 3) {
            Set var3 = (Set)this.mapByOperation.get(var2);
            var3.remove(par1AttributeModifier);
            ++var2;
        }
        Set var4 = (Set)this.mapByName.get(par1AttributeModifier.getName());
        if (var4 != null) {
            var4.remove(par1AttributeModifier);
            if (var4.isEmpty()) {
                this.mapByName.remove(par1AttributeModifier.getName());
            }
        }
        this.mapByUUID.remove(par1AttributeModifier.getID());
        this.flagForUpdate();
    }

    @Override
    public void removeAllModifiers() {
        Collection var1 = this.func_111122_c();
        if (var1 != null) {
            ArrayList var4 = new ArrayList(var1);
            for (AttributeModifier var3 : var4) {
                this.removeModifier(var3);
            }
        }
    }

    @Override
    public double getAttributeValue() {
        if (this.needsUpdate) {
            this.cachedValue = this.computeValue();
            this.needsUpdate = false;
        }
        return this.cachedValue;
    }

    private double computeValue() {
        double var1 = this.getBaseValue();
        for (AttributeModifier var4 : this.getModifiersByOperation(0)) {
            var1 += var4.getAmount();
        }
        double var7 = var1;
        for (AttributeModifier var6 : this.getModifiersByOperation(1)) {
            var7 += var1 * var6.getAmount();
        }
        for (AttributeModifier var6 : this.getModifiersByOperation(2)) {
            var7 *= 1.0 + var6.getAmount();
        }
        return this.genericAttribute.clampValue(var7);
    }
}

