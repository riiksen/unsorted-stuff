/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.gui.TaskOnlineConnect;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenReamlsTOS
extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private final GuiScreen field_146770_f;
    private final McoServer field_146771_g;
    private GuiButton field_146774_h;
    private boolean field_146775_i = false;
    private String field_146772_r = "https://minecraft.net/realms/terms";
    private static final String __OBFID = "CL_00000809";

    public GuiScreenReamlsTOS(GuiScreen p_i45045_1_, McoServer p_i45045_2_) {
        this.field_146770_f = p_i45045_1_;
        this.field_146771_g = p_i45045_2_;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        int var1 = this.width / 4;
        int var2 = this.width / 4 - 2;
        int var3 = this.width / 2 + 4;
        this.field_146774_h = new GuiButton(1, var1, this.height / 5 + 96 + 22, var2, 20, I18n.format("mco.terms.buttons.agree", new Object[0]));
        this.buttonList.add(this.field_146774_h);
        this.buttonList.add(new GuiButton(2, var3, this.height / 5 + 96 + 22, var2, 20, I18n.format("mco.terms.buttons.disagree", new Object[0])));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 2) {
                this.mc.displayGuiScreen(this.field_146770_f);
            } else if (p_146284_1_.id == 1) {
                this.func_146768_g();
            }
        }
    }

    private void func_146768_g() {
        Session var1 = this.mc.getSession();
        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        try {
            var2.func_148714_h();
            GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.mc, this, new TaskOnlineConnect(this, this.field_146771_g));
            var3.func_146902_g();
            this.mc.displayGuiScreen(var3);
        }
        catch (ExceptionMcoService var4) {
            logger.error("Couldn't agree to TOS");
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if (this.field_146775_i) {
            Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
            var4.setContents(new StringSelection(this.field_146772_r), null);
            this.func_146769_a(this.field_146772_r);
        }
    }

    private void func_146769_a(String p_146769_1_) {
        try {
            URI var2 = new URI(p_146769_1_);
            Class var3 = Class.forName("java.awt.Desktop");
            Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            var3.getMethod("browse", URI.class).invoke(var4, var2);
        }
        catch (Throwable var5) {
            logger.error("Couldn't open link");
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.terms.title", new Object[0]), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.terms.sentence.1", new Object[0]), this.width / 2 - 120, 87, 16777215);
        int var4 = this.fontRendererObj.getStringWidth(I18n.format("mco.terms.sentence.1", new Object[0]));
        int var5 = 3368635;
        int var6 = 7107012;
        int var7 = this.width / 2 - 121 + var4;
        int var8 = 86;
        int var9 = var7 + this.fontRendererObj.getStringWidth("mco.terms.sentence.2") + 1;
        int var10 = 87 + this.fontRendererObj.FONT_HEIGHT;
        if (var7 <= par1 && par1 <= var9 && var8 <= par2 && par2 <= var10) {
            this.field_146775_i = true;
            this.drawString(this.fontRendererObj, " " + I18n.format("mco.terms.sentence.2", new Object[0]), this.width / 2 - 120 + var4, 87, var6);
        } else {
            this.field_146775_i = false;
            this.drawString(this.fontRendererObj, " " + I18n.format("mco.terms.sentence.2", new Object[0]), this.width / 2 - 120 + var4, 87, var5);
        }
        super.drawScreen(par1, par2, par3);
    }
}

