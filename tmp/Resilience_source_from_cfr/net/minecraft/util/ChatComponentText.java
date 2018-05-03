/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.List;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ChatComponentText
extends ChatComponentStyle {
    private final String text;
    private static final String __OBFID = "CL_00001269";

    public ChatComponentText(String p_i45159_1_) {
        this.text = p_i45159_1_;
    }

    public String getChatComponentText_TextValue() {
        return this.text;
    }

    @Override
    public String getUnformattedTextForChat() {
        return this.text;
    }

    @Override
    public ChatComponentText createCopy() {
        ChatComponentText var1 = new ChatComponentText(this.text);
        var1.setChatStyle(this.getChatStyle().createShallowCopy());
        for (IChatComponent var3 : this.getSiblings()) {
            var1.appendSibling(var3.createCopy());
        }
        return var1;
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (!(par1Obj instanceof ChatComponentText)) {
            return false;
        }
        ChatComponentText var2 = (ChatComponentText)par1Obj;
        if (this.text.equals(var2.getChatComponentText_TextValue()) && super.equals(par1Obj)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
}

