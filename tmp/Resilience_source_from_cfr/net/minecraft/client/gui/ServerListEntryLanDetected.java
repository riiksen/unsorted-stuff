/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ServerListEntryLanDetected
implements GuiListExtended.IGuiListEntry {
    private final GuiMultiplayer field_148292_c;
    protected final Minecraft field_148293_a;
    protected final LanServerDetector.LanServer field_148291_b;
    private long field_148290_d = 0;
    private static final String __OBFID = "CL_00000816";

    protected ServerListEntryLanDetected(GuiMultiplayer p_i45046_1_, LanServerDetector.LanServer p_i45046_2_) {
        this.field_148292_c = p_i45046_1_;
        this.field_148291_b = p_i45046_2_;
        this.field_148293_a = Minecraft.getMinecraft();
    }

    @Override
    public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_) {
        this.field_148293_a.fontRenderer.drawString(I18n.format("lanServer.title", new Object[0]), p_148279_2_ + 32 + 3, p_148279_3_ + 1, 16777215);
        this.field_148293_a.fontRenderer.drawString(this.field_148291_b.getServerMotd(), p_148279_2_ + 32 + 3, p_148279_3_ + 12, 8421504);
        if (this.field_148293_a.gameSettings.hideServerAddress) {
            this.field_148293_a.fontRenderer.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + 11, 3158064);
        } else {
            this.field_148293_a.fontRenderer.drawString(this.field_148291_b.getServerIpPort(), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + 11, 3158064);
        }
    }

    @Override
    public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        this.field_148292_c.func_146790_a(p_148278_1_);
        if (Minecraft.getSystemTime() - this.field_148290_d < 250) {
            this.field_148292_c.func_146796_h();
        }
        this.field_148290_d = Minecraft.getSystemTime();
        return false;
    }

    @Override
    public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
    }

    public LanServerDetector.LanServer func_148289_a() {
        return this.field_148291_b;
    }
}

