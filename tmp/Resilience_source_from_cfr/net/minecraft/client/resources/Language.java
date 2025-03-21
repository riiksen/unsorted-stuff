/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.resources;

public class Language
implements Comparable {
    private final String languageCode;
    private final String region;
    private final String name;
    private final boolean bidirectional;
    private static final String __OBFID = "CL_00001095";

    public Language(String par1Str, String par2Str, String par3Str, boolean par4) {
        this.languageCode = par1Str;
        this.region = par2Str;
        this.name = par3Str;
        this.bidirectional = par4;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public boolean isBidirectional() {
        return this.bidirectional;
    }

    public String toString() {
        return String.format("%s (%s)", this.name, this.region);
    }

    public boolean equals(Object par1Obj) {
        return this == par1Obj ? true : (!(par1Obj instanceof Language) ? false : this.languageCode.equals(((Language)par1Obj).languageCode));
    }

    public int hashCode() {
        return this.languageCode.hashCode();
    }

    public int compareTo(Language par1Language) {
        return this.languageCode.compareTo(par1Language.languageCode);
    }

    public int compareTo(Object par1Obj) {
        return this.compareTo((Language)par1Obj);
    }
}

