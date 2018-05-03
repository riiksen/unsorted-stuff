/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
    common(EnumChatFormatting.WHITE, "Common"),
    uncommon(EnumChatFormatting.YELLOW, "Uncommon"),
    rare(EnumChatFormatting.AQUA, "Rare"),
    epic(EnumChatFormatting.LIGHT_PURPLE, "Epic");
    
    public final EnumChatFormatting rarityColor;
    public final String rarityName;
    private static final String __OBFID = "CL_00000056";

    private EnumRarity(String p_i45349_3_, int p_i45349_4_, EnumChatFormatting enumChatFormatting, String string2) {
        this.rarityColor = p_i45349_3_;
        this.rarityName = (String)p_i45349_4_;
    }
}

