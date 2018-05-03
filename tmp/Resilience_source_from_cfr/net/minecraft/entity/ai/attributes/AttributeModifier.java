/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.entity.ai.attributes;

import java.util.UUID;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
    private final double amount;
    private final int operation;
    private final String name;
    private final UUID id;
    private boolean isSaved = true;
    private static final String __OBFID = "CL_00001564";

    public AttributeModifier(String par1Str, double par2, int par4) {
        this(UUID.randomUUID(), par1Str, par2, par4);
    }

    public AttributeModifier(UUID par1UUID, String par2Str, double par3, int par5) {
        this.id = par1UUID;
        this.name = par2Str;
        this.amount = par3;
        this.operation = par5;
        Validate.notEmpty((CharSequence)par2Str, (String)"Modifier name cannot be empty", (Object[])new Object[0]);
        Validate.inclusiveBetween((Object)0, (Object)2, (Comparable)Integer.valueOf(par5), (String)"Invalid operation", (Object[])new Object[0]);
    }

    public UUID getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getOperation() {
        return this.operation;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isSaved() {
        return this.isSaved;
    }

    public AttributeModifier setSaved(boolean par1) {
        this.isSaved = par1;
        return this;
    }

    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (par1Obj != null && this.getClass() == par1Obj.getClass()) {
            AttributeModifier var2 = (AttributeModifier)par1Obj;
            if (this.id != null ? !this.id.equals(var2.id) : var2.id != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    public String toString() {
        return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
    }
}

