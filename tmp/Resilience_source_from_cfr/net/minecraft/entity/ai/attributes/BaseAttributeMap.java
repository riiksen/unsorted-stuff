/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 */
package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {
    protected final Map attributes = new HashMap();
    protected final Map attributesByName = new LowerStringMap();
    private static final String __OBFID = "CL_00001566";

    public IAttributeInstance getAttributeInstance(IAttribute par1Attribute) {
        return (IAttributeInstance)this.attributes.get(par1Attribute);
    }

    public IAttributeInstance getAttributeInstanceByName(String par1Str) {
        return (IAttributeInstance)this.attributesByName.get(par1Str);
    }

    public abstract IAttributeInstance registerAttribute(IAttribute var1);

    public Collection getAllAttributes() {
        return this.attributesByName.values();
    }

    public void addAttributeInstance(ModifiableAttributeInstance par1ModifiableAttributeInstance) {
    }

    public void removeAttributeModifiers(Multimap par1Multimap) {
        for (Map.Entry var3 : par1Multimap.entries()) {
            IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());
            if (var4 == null) continue;
            var4.removeModifier((AttributeModifier)var3.getValue());
        }
    }

    public void applyAttributeModifiers(Multimap par1Multimap) {
        for (Map.Entry var3 : par1Multimap.entries()) {
            IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());
            if (var4 == null) continue;
            var4.removeModifier((AttributeModifier)var3.getValue());
            var4.applyModifier((AttributeModifier)var3.getValue());
        }
    }
}

