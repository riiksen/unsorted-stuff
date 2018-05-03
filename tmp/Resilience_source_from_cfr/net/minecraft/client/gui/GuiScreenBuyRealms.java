/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui;

import java.net.Proxy;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBuyRealms
extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private GuiScreen field_146817_f;
    private static int field_146818_g = 111;
    private volatile String field_146820_h = "";
    private static final String __OBFID = "CL_00000770";

    public GuiScreenBuyRealms(GuiScreen p_i45035_1_) {
        this.field_146817_f = p_i45035_1_;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        int var1 = 212;
        this.buttonList.add(new GuiButton(field_146818_g, this.width / 2 - var1 / 2, 180, var1, 20, I18n.format("gui.back", new Object[0])));
        this.func_146816_h();
    }

    private void func_146816_h() {
        Session var1 = this.mc.getSession();
        final McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        new Thread(){
            private static final String __OBFID = "CL_00000771";

            @Override
            public void run() {
                try {
                    GuiScreenBuyRealms.access$0(GuiScreenBuyRealms.this, var2.func_148690_i());
                }
                catch (ExceptionMcoService var2x) {
                    logger.error("Could not get stat");
                }
            }
        }.start();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled && p_146284_1_.id == field_146818_g) {
            this.mc.displayGuiScreen(this.field_146817_f);
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.buy.realms.title", new Object[0]), this.width / 2, 11, 16777215);
        String[] var4 = this.field_146820_h.split("\n");
        int var5 = 52;
        String[] var6 = var4;
        int var7 = var4.length;
        int var8 = 0;
        while (var8 < var7) {
            String var9 = var6[var8];
            this.drawCenteredString(this.fontRendererObj, var9, this.width / 2, var5, 10526880);
            var5 += 18;
            ++var8;
        }
        super.drawScreen(par1, par2, par3);
    }

    static /* synthetic */ void access$0(GuiScreenBuyRealms guiScreenBuyRealms, String string) {
        guiScreenBuyRealms.field_146820_h = string;
    }

}

