/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList
extends GuiListExtended {
    protected final Minecraft field_148205_k;
    protected final List field_148204_l;
    private static final String __OBFID = "CL_00000825";

    public GuiResourcePackList(Minecraft p_i45055_1_, int p_i45055_2_, int p_i45055_3_, List p_i45055_4_) {
        super(p_i45055_1_, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
        this.field_148205_k = p_i45055_1_;
        this.field_148204_l = p_i45055_4_;
        this.field_148163_i = false;
        this.func_148133_a(true, (int)((float)p_i45055_1_.fontRenderer.FONT_HEIGHT * 1.5f));
    }

    @Override
    protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
        String var4 = (Object)((Object)EnumChatFormatting.UNDERLINE) + (Object)((Object)EnumChatFormatting.BOLD) + this.func_148202_k();
        this.field_148205_k.fontRenderer.drawString(var4, p_148129_1_ + this.field_148155_a / 2 - this.field_148205_k.fontRenderer.getStringWidth(var4) / 2, Math.min(this.field_148153_b + 3, p_148129_2_), 16777215);
    }

    protected abstract String func_148202_k();

    public List func_148201_l() {
        return this.field_148204_l;
    }

    @Override
    protected int getSize() {
        return this.func_148201_l().size();
    }

    @Override
    public ResourcePackListEntry func_148180_b(int p_148203_1_) {
        return (ResourcePackListEntry)this.func_148201_l().get(p_148203_1_);
    }

    @Override
    public int func_148139_c() {
        return this.field_148155_a;
    }

    @Override
    protected int func_148137_d() {
        return this.field_148151_d - 6;
    }
}

