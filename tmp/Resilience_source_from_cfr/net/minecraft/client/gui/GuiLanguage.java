/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

public class GuiLanguage
extends GuiScreen {
    protected GuiScreen field_146453_a;
    private List field_146450_f;
    private final GameSettings field_146451_g;
    private final LanguageManager field_146454_h;
    private GuiOptionButton field_146455_i;
    private GuiOptionButton field_146452_r;
    private static final String __OBFID = "CL_00000698";

    public GuiLanguage(GuiScreen par1GuiScreen, GameSettings par2GameSettings, LanguageManager par3LanguageManager) {
        this.field_146453_a = par1GuiScreen;
        this.field_146451_g = par2GameSettings;
        this.field_146454_h = par3LanguageManager;
    }

    @Override
    public void initGui() {
        this.field_146455_i = new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.field_146451_g.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT));
        this.buttonList.add(this.field_146455_i);
        this.field_146452_r = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done", new Object[0]));
        this.buttonList.add(this.field_146452_r);
        this.field_146450_f = new List();
        this.field_146450_f.func_148134_d(7, 8);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            switch (p_146284_1_.id) {
                case 5: {
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.field_146453_a);
                    break;
                }
                case 100: {
                    if (!(p_146284_1_ instanceof GuiOptionButton)) break;
                    this.field_146451_g.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
                    p_146284_1_.displayString = this.field_146451_g.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                    break;
                }
                default: {
                    this.field_146450_f.func_148147_a(p_146284_1_);
                }
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.field_146450_f.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", this.width / 2, this.height - 56, 8421504);
        super.drawScreen(par1, par2, par3);
    }

    static /* synthetic */ GuiOptionButton access$2(GuiLanguage guiLanguage) {
        return guiLanguage.field_146452_r;
    }

    static /* synthetic */ GuiOptionButton access$3(GuiLanguage guiLanguage) {
        return guiLanguage.field_146455_i;
    }

    class List
    extends GuiSlot {
        private final java.util.List field_148176_l;
        private final Map field_148177_m;
        private static final String __OBFID = "CL_00000699";

        public List() {
            super(GuiLanguage.this.mc, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
            this.field_148176_l = Lists.newArrayList();
            this.field_148177_m = Maps.newHashMap();
            for (Language var3 : GuiLanguage.this.field_146454_h.getLanguages()) {
                this.field_148177_m.put(var3.getLanguageCode(), var3);
                this.field_148176_l.add(var3.getLanguageCode());
            }
        }

        @Override
        protected int getSize() {
            return this.field_148176_l.size();
        }

        @Override
        protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
            Language var5 = (Language)this.field_148177_m.get(this.field_148176_l.get(p_148144_1_));
            GuiLanguage.this.field_146454_h.setCurrentLanguage(var5);
            GuiLanguage.access$1((GuiLanguage)GuiLanguage.this).language = var5.getLanguageCode();
            GuiLanguage.this.mc.refreshResources();
            GuiLanguage.this.fontRendererObj.setUnicodeFlag(GuiLanguage.this.field_146454_h.isCurrentLocaleUnicode() || GuiLanguage.access$1((GuiLanguage)GuiLanguage.this).forceUnicodeFont);
            GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.field_146454_h.isCurrentLanguageBidirectional());
            GuiLanguage.access$2((GuiLanguage)GuiLanguage.this).displayString = I18n.format("gui.done", new Object[0]);
            GuiLanguage.access$3((GuiLanguage)GuiLanguage.this).displayString = GuiLanguage.this.field_146451_g.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiLanguage.this.field_146451_g.saveOptions();
        }

        @Override
        protected boolean isSelected(int p_148131_1_) {
            return ((String)this.field_148176_l.get(p_148131_1_)).equals(GuiLanguage.this.field_146454_h.getCurrentLanguage().getLanguageCode());
        }

        @Override
        protected int func_148138_e() {
            return this.getSize() * 18;
        }

        @Override
        protected void drawBackground() {
            GuiLanguage.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            GuiLanguage.this.fontRendererObj.setBidiFlag(true);
            GuiLanguage.this.drawCenteredString(GuiLanguage.this.fontRendererObj, ((Language)this.field_148177_m.get(this.field_148176_l.get(p_148126_1_))).toString(), this.field_148155_a / 2, p_148126_3_ + 1, 16777215);
            GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.field_146454_h.getCurrentLanguage().isBidirectional());
        }
    }

}

