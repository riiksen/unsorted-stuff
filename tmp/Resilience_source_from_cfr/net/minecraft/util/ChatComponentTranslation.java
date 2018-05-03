/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 */
package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslationFormatException;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class ChatComponentTranslation
extends ChatComponentStyle {
    private final String key;
    private final Object[] formatArgs;
    private final Object syncLock = new Object();
    private long lastTranslationUpdateTimeInMilliseconds = -1;
    List children = Lists.newArrayList();
    public static final Pattern stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    private static final String __OBFID = "CL_00001270";

    public /* varargs */ ChatComponentTranslation(String p_i45160_1_, Object ... p_i45160_2_) {
        this.key = p_i45160_1_;
        this.formatArgs = p_i45160_2_;
        Object[] var3 = p_i45160_2_;
        int var4 = p_i45160_2_.length;
        int var5 = 0;
        while (var5 < var4) {
            Object var6 = var3[var5];
            if (var6 instanceof IChatComponent) {
                ((IChatComponent)var6).getChatStyle().setParentStyle(this.getChatStyle());
            }
            ++var5;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized void ensureInitialized() {
        Object var1 = this.syncLock;
        Object object = this.syncLock;
        synchronized (object) {
            long var2 = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
            if (var2 == this.lastTranslationUpdateTimeInMilliseconds) {
                return;
            }
            this.lastTranslationUpdateTimeInMilliseconds = var2;
            this.children.clear();
        }
        try {
            this.initializeFromFormat(StatCollector.translateToLocal(this.key));
        }
        catch (ChatComponentTranslationFormatException var6) {
            this.children.clear();
            try {
                this.initializeFromFormat(StatCollector.translateToFallback(this.key));
            }
            catch (ChatComponentTranslationFormatException var5) {
                throw var6;
            }
        }
    }

    protected void initializeFromFormat(String p_150269_1_) {
        boolean var2 = false;
        Matcher var3 = stringVariablePattern.matcher(p_150269_1_);
        int var4 = 0;
        int var5 = 0;
        try {
            while (var3.find(var5)) {
                int var6 = var3.start();
                int var7 = var3.end();
                if (var6 > var5) {
                    ChatComponentText var8 = new ChatComponentText(String.format(p_150269_1_.substring(var5, var6), new Object[0]));
                    var8.getChatStyle().setParentStyle(this.getChatStyle());
                    this.children.add(var8);
                }
                String var14 = var3.group(2);
                String var9 = p_150269_1_.substring(var6, var7);
                if ("%".equals(var14) && "%%".equals(var9)) {
                    ChatComponentText var15 = new ChatComponentText("%");
                    var15.getChatStyle().setParentStyle(this.getChatStyle());
                    this.children.add(var15);
                } else {
                    if (!"s".equals(var14)) {
                        throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + var9 + "'");
                    }
                    String var10 = var3.group(1);
                    int var11 = var10 != null ? Integer.parseInt(var10) - 1 : var4++;
                    this.children.add(this.getFormatArgumentAsComponent(var11));
                }
                var5 = var7;
            }
            if (var5 < p_150269_1_.length()) {
                ChatComponentText var13 = new ChatComponentText(String.format(p_150269_1_.substring(var5), new Object[0]));
                var13.getChatStyle().setParentStyle(this.getChatStyle());
                this.children.add(var13);
            }
        }
        catch (IllegalFormatException var12) {
            throw new ChatComponentTranslationFormatException(this, var12);
        }
    }

    private IChatComponent getFormatArgumentAsComponent(int p_150272_1_) {
        IChatComponent var3;
        if (p_150272_1_ >= this.formatArgs.length) {
            throw new ChatComponentTranslationFormatException(this, p_150272_1_);
        }
        Object var2 = this.formatArgs[p_150272_1_];
        if (var2 instanceof IChatComponent) {
            var3 = (IChatComponent)var2;
        } else {
            var3 = new ChatComponentText(var2.toString());
            var3.getChatStyle().setParentStyle(this.getChatStyle());
        }
        return var3;
    }

    @Override
    public IChatComponent setChatStyle(ChatStyle p_150255_1_) {
        super.setChatStyle(p_150255_1_);
        Object[] var2 = this.formatArgs;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            Object var5 = var2[var4];
            if (var5 instanceof IChatComponent) {
                ((IChatComponent)var5).getChatStyle().setParentStyle(this.getChatStyle());
            }
            ++var4;
        }
        if (this.lastTranslationUpdateTimeInMilliseconds > -1) {
            for (IChatComponent var7 : this.children) {
                var7.getChatStyle().setParentStyle(p_150255_1_);
            }
        }
        return this;
    }

    @Override
    public Iterator iterator() {
        this.ensureInitialized();
        return Iterators.concat((Iterator)ChatComponentTranslation.createDeepCopyIterator(this.children), (Iterator)ChatComponentTranslation.createDeepCopyIterator(this.siblings));
    }

    @Override
    public String getUnformattedTextForChat() {
        this.ensureInitialized();
        StringBuilder var1 = new StringBuilder();
        for (IChatComponent var3 : this.children) {
            var1.append(var3.getUnformattedTextForChat());
        }
        return var1.toString();
    }

    @Override
    public ChatComponentTranslation createCopy() {
        Object[] var1 = new Object[this.formatArgs.length];
        int var2 = 0;
        while (var2 < this.formatArgs.length) {
            var1[var2] = this.formatArgs[var2] instanceof IChatComponent ? ((IChatComponent)this.formatArgs[var2]).createCopy() : this.formatArgs[var2];
            ++var2;
        }
        ChatComponentTranslation var5 = new ChatComponentTranslation(this.key, var1);
        var5.setChatStyle(this.getChatStyle().createShallowCopy());
        for (IChatComponent var4 : this.getSiblings()) {
            var5.appendSibling(var4.createCopy());
        }
        return var5;
    }

    @Override
    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (!(par1Obj instanceof ChatComponentTranslation)) {
            return false;
        }
        ChatComponentTranslation var2 = (ChatComponentTranslation)par1Obj;
        if (Arrays.equals(this.formatArgs, var2.formatArgs) && this.key.equals(var2.key) && super.equals(par1Obj)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int var1 = super.hashCode();
        var1 = 31 * var1 + this.key.hashCode();
        var1 = 31 * var1 + Arrays.hashCode(this.formatArgs);
        return var1;
    }

    @Override
    public String toString() {
        return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }

    public String getKey() {
        return this.key;
    }

    public Object[] getFormatArgs() {
        return this.formatArgs;
    }
}

