/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.integrated.IntegratedServer;

public class GuiSnooper
extends GuiScreen {
    private final GuiScreen field_146608_a;
    private final GameSettings field_146603_f;
    private final java.util.List field_146604_g = new ArrayList();
    private final java.util.List field_146609_h = new ArrayList();
    private String field_146610_i;
    private String[] field_146607_r;
    private List field_146606_s;
    private GuiButton field_146605_t;
    private static final String __OBFID = "CL_00000714";

    public GuiSnooper(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
        this.field_146608_a = par1GuiScreen;
        this.field_146603_f = par2GameSettings;
    }

    @Override
    public void initGui() {
        this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
        String var1 = I18n.format("options.snooper.desc", new Object[0]);
        ArrayList<String> var2 = new ArrayList<String>();
        for (String var4 : this.fontRendererObj.listFormattedStringToWidth(var1, this.width - 30)) {
            var2.add(var4);
        }
        this.field_146607_r = var2.toArray(new String[0]);
        this.field_146604_g.clear();
        this.field_146609_h.clear();
        this.field_146605_t = new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.field_146603_f.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED));
        this.buttonList.add(this.field_146605_t);
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
        boolean var6 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
        for (Map.Entry var5 : new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_146604_g.add(String.valueOf(var6 ? "C " : "") + (String)var5.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var5.getValue(), this.width - 220));
        }
        if (var6) {
            for (Map.Entry var5 : new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
                this.field_146604_g.add("S " + (String)var5.getKey());
                this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var5.getValue(), this.width - 220));
            }
        }
        this.field_146606_s = new List();
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 2) {
                this.field_146603_f.saveOptions();
                this.field_146603_f.saveOptions();
                this.mc.displayGuiScreen(this.field_146608_a);
            }
            if (p_146284_1_.id == 1) {
                this.field_146603_f.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
                this.field_146605_t.displayString = this.field_146603_f.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.field_146606_s.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / 2, 8, 16777215);
        int var4 = 22;
        String[] var5 = this.field_146607_r;
        int var6 = var5.length;
        int var7 = 0;
        while (var7 < var6) {
            String var8 = var5[var7];
            this.drawCenteredString(this.fontRendererObj, var8, this.width / 2, var4, 8421504);
            var4 += this.fontRendererObj.FONT_HEIGHT;
            ++var7;
        }
        super.drawScreen(par1, par2, par3);
    }

    class List
    extends GuiSlot {
        private static final String __OBFID = "CL_00000715";

        public List() {
            super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
        }

        @Override
        protected int getSize() {
            return GuiSnooper.this.field_146604_g.size();
        }

        @Override
        protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
        }

        @Override
        protected boolean isSelected(int p_148131_1_) {
            return false;
        }

        @Override
        protected void drawBackground() {
        }

        @Override
        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            GuiSnooper.this.fontRendererObj.drawString((String)GuiSnooper.this.field_146604_g.get(p_148126_1_), 10.0f, p_148126_3_, 16777215);
            GuiSnooper.this.fontRendererObj.drawString((String)GuiSnooper.this.field_146609_h.get(p_148126_1_), 230.0f, p_148126_3_, 16777215);
        }

        @Override
        protected int func_148137_d() {
            return this.field_148155_a - 10;
        }
    }

}

