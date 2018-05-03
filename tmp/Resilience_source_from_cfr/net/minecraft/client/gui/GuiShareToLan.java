/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookGuiIngame;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class GuiShareToLan
extends GuiScreen {
    private final GuiScreen field_146598_a;
    private GuiButton field_146596_f;
    private GuiButton field_146597_g;
    private String field_146599_h = "survival";
    private boolean field_146600_i;
    private static final String __OBFID = "CL_00000713";

    public GuiShareToLan(GuiScreen par1GuiScreen) {
        this.field_146598_a = par1GuiScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
        this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146597_g = new GuiButton(104, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0]));
        this.buttonList.add(this.field_146597_g);
        this.field_146596_f = new GuiButton(103, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0]));
        this.buttonList.add(this.field_146596_f);
        this.func_146595_g();
    }

    private void func_146595_g() {
        this.field_146597_g.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + " " + I18n.format(new StringBuilder("selectWorld.gameMode.").append(this.field_146599_h).toString(), new Object[0]);
        this.field_146596_f.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
        this.field_146596_f.displayString = this.field_146600_i ? String.valueOf(this.field_146596_f.displayString) + I18n.format("options.on", new Object[0]) : String.valueOf(this.field_146596_f.displayString) + I18n.format("options.off", new Object[0]);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 102) {
            this.mc.displayGuiScreen(this.field_146598_a);
        } else if (p_146284_1_.id == 104) {
            this.field_146599_h = this.field_146599_h.equals("survival") ? "creative" : (this.field_146599_h.equals("creative") ? "adventure" : "survival");
            this.func_146595_g();
        } else if (p_146284_1_.id == 103) {
            this.field_146600_i = !this.field_146600_i;
            this.func_146595_g();
        } else if (p_146284_1_.id == 101) {
            this.mc.displayGuiScreen(null);
            String var2 = this.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
            ChatComponentStyle var3 = var2 != null ? new ChatComponentTranslation("commands.publish.started", var2) : new ChatComponentText("commands.publish.failed");
            this.mc.ingameGUI.getChatGUI().func_146227_a(var3);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), this.width / 2, 50, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), this.width / 2, 82, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}

