/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.server.management.LowerStringMap;

public class ServersideAttributeMap
extends BaseAttributeMap {
    private final Set attributeInstanceSet = Sets.newHashSet();
    protected final Map descriptionToAttributeInstanceMap = new LowerStringMap();
    private static final String __OBFID = "CL_00001569";

    @Override
    public ModifiableAttributeInstance getAttributeInstance(IAttribute par1Attribute) {
        return (ModifiableAttributeInstance)super.getAttributeInstance(par1Attribute);
    }

    @Override
    public ModifiableAttributeInstance getAttributeInstanceByName(String par1Str) {
        IAttributeInstance var2 = super.getAttributeInstanceByName(par1Str);
        if (var2 == null) {
            var2 = (IAttributeInstance)this.descriptionToAttributeInstanceMap.get(par1Str);
        }
        return (ModifiableAttributeInstance)var2;
    }

    @Override
    public IAttributeInstance registerAttribute(IAttribute par1Attribute) {
        if (this.attributesByName.containsKey(par1Attribute.getAttributeUnlocalizedName())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        }
        ModifiableAttributeInstance var2 = new ModifiableAttributeInstance(this, par1Attribute);
        this.attributesByName.put(par1Attribute.getAttributeUnlocalizedName(), var2);
        if (par1Attribute instanceof RangedAttribute && ((RangedAttribute)par1Attribute).getDescription() != null) {
            this.descriptionToAttributeInstanceMap.put(((RangedAttribute)par1Attribute).getDescription(), var2);
        }
        this.attributes.put(par1Attribute, var2);
        return var2;
    }

    @Override
    public void addAttributeInstance(ModifiableAttributeInstance par1ModifiableAttributeInstance) {
        if (par1ModifiableAttributeInstance.getAttribute().getShouldWatch()) {
            this.attributeInstanceSet.add(par1ModifiableAttributeInstance);
        }
    }

    public Set getAttributeInstanceSet() {
        return this.attributeInstanceSet;
    }

    public Collection getWatchedAttributes() {
        HashSet var1 = Sets.newHashSet();
        for (IAttributeInstance var3 : this.getAllAttributes()) {
            if (!var3.getAttribute().getShouldWatch()) continue;
            var1.add(var3);
        }
        return var1;
    }
}

