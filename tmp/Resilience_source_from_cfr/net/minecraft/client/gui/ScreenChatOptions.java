/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ScreenChatOptions
extends GuiScreen {
    private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[]{GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH};
    private static final GameSettings.Options[] field_146395_f = new GameSettings.Options[]{GameSettings.Options.SHOW_CAPE};
    private final GuiScreen field_146396_g;
    private final GameSettings field_146400_h;
    private String field_146401_i;
    private String field_146398_r;
    private int field_146397_s;
    private static final String __OBFID = "CL_00000681";

    public ScreenChatOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
        this.field_146396_g = par1GuiScreen;
        this.field_146400_h = par2GameSettings;
    }

    @Override
    public void initGui() {
        GameSettings.Options var5;
        int var1 = 0;
        this.field_146401_i = I18n.format("options.chat.title", new Object[0]);
        this.field_146398_r = I18n.format("options.multiplayer.title", new Object[0]);
        GameSettings.Options[] var2 = field_146399_a;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            var5 = var2[var4];
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5));
            } else {
                this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5, this.field_146400_h.getKeyBinding(var5)));
            }
            ++var1;
            ++var4;
        }
        if (var1 % 2 == 1) {
            ++var1;
        }
        this.field_146397_s = this.height / 6 + 24 * (var1 >> 1);
        var1 += 2;
        var2 = field_146395_f;
        var3 = var2.length;
        var4 = 0;
        while (var4 < var3) {
            var5 = var2[var4];
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5));
            } else {
                this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5, this.field_146400_h.getKeyBinding(var5)));
            }
            ++var1;
            ++var4;
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id < 100 && p_146284_1_ instanceof GuiOptionButton) {
                this.field_146400_h.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
                p_146284_1_.displayString = this.field_146400_h.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.id));
            }
            if (p_146284_1_.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146396_g);
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_146398_r, this.width / 2, this.field_146397_s + 7, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}

