/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiResourcePackList;
import net.minecraft.client.resources.I18n;

public class GuiResourcePackAvailable
extends GuiResourcePackList {
    private static final String __OBFID = "CL_00000824";

    public GuiResourcePackAvailable(Minecraft p_i45054_1_, int p_i45054_2_, int p_i45054_3_, List p_i45054_4_) {
        super(p_i45054_1_, p_i45054_2_, p_i45054_3_, p_i45054_4_);
    }

    @Override
    protected String func_148202_k() {
        return I18n.format("resourcePack.available.title", new Object[0]);
    }
}

