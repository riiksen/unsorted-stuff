/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.hooks;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiResilienceMain;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HookGuiMainMenu
extends GuiMainMenu {
    private final ResourceLocation backgroundImage = new ResourceLocation("assets/minecraft/textures/gui/title/resilience-background.jpg");
    private final ResourceLocation titleImage = new ResourceLocation("assets/minecraft/textures/gui/title/resilience-title.png");
    private static String version = "1.7.6";
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private static boolean hasAsked = false;
    private static boolean hasClickedRes = false;

    @Override
    public void initGui() {
        new Thread(){

            @Override
            public void run() {
                Utils.sendGetRequest("http://resilience.krispdev.com/updateStatus.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&status=Not playing multiplayer");
            }
        }.start();
        this.mc.gameSettings.guiScale = 2;
        if (Resilience.getInstance().isEnabled()) {
            this.invoker.addButton(this, new ResilienceButton(1, 1.0f, 1.0f, this.invoker.getWidth() / 3 + 1, 20.0f, I18n.format("menu.singleplayer", new Object[0])));
            this.invoker.addButton(this, new ResilienceButton(69, this.invoker.getWidth() / 3 + 3, 1.0f, this.invoker.getWidth() / 3 + 3, 41.0f, Resilience.getInstance().getName()));
            this.invoker.addButton(this, new ResilienceButton(2, this.invoker.getWidth() / 3 * 2 + 7, 1.0f, this.invoker.getWidth() / 3 - 7, 20.0f, I18n.format("menu.multiplayer", new Object[0])));
            this.invoker.addButton(this, new ResilienceButton(199, this.invoker.getWidth() / 2 - 50, this.invoker.getHeight() - 93, 100.0f, 20.0f, "Change to 1.7.9"));
            this.invoker.addButton(this, new ResilienceButton(0, 1.0f, 22.0f, this.invoker.getWidth() / 3 + 1, 20.0f, I18n.format("menu.options", new Object[0])));
            this.invoker.addButton(this, new ResilienceButton(4, this.invoker.getWidth() / 3 * 2 + 7, 22.0f, this.invoker.getWidth() / 3 - 7, 20.0f, I18n.format("menu.quit", new Object[0])));
            this.invoker.addButton(this, new ResilienceButton(70, this.invoker.getWidth() / 2 - 50, (float)this.invoker.getHeight() - 24.5f, 100.0f, 20.0f, "Suggest"));
        } else {
            super.initGui();
        }
    }

    @Override
    protected void addSingleplayerMultiplayerButtons(int par1, int par2) {
        super.addSingleplayerMultiplayerButtons(par1, par2);
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        if (this.invoker.getId(btn) == 69) {
            this.mc.displayGuiScreen(GuiResilienceMain.screen);
            hasClickedRes = true;
        } else if (this.invoker.getId(btn) == 70) {
            Sys.openURL((String)"http://resilience.krispdev.com/suggest");
        } else if (this.invoker.getId(btn) == 199) {
            btn.displayString = "Change to " + version;
            if (version.equals("1.7.9")) {
                version = "1.7.6";
                Resilience.getInstance().getValues().version = 4;
            } else if (version.equals("1.7.6")) {
                version = "1.7.9";
                Resilience.getInstance().getValues().version = 5;
            }
        }
        super.actionPerformed(btn);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (Resilience.getInstance().isEnabled()) {
            Image img;
            this.drawGradientRect(0, 0, this.invoker.getWidth(), this.invoker.getHeight(), -1776412, -13421569);
            try {
                img = new Image(this.backgroundImage.getResourcePath());
                img.draw(0.0f, 0.0f, (float)this.invoker.getWidth(), this.invoker.getHeight());
            }
            catch (SlickException e) {
                e.printStackTrace();
            }
            try {
                img = new Image(this.titleImage.getResourcePath());
                GL11.glEnable((int)3042);
                img.draw((float)(this.invoker.getWidth() / 2 - 90), (float)(this.invoker.getHeight() / 2 - 60), 200.0f, 80.0f);
            }
            catch (SlickException e) {
                e.printStackTrace();
            }
            Utils.drawBetterRect(this.invoker.getWidth() / 2 - 150, this.invoker.getHeight() - 68, this.invoker.getWidth() / 2 + 150, this.invoker.getHeight() - 29, -1996488705, 1711276032);
            Resilience.getInstance().getStandardFont().drawCenteredString("Version: \u00a77" + version, this.invoker.getWidth() / 2, this.invoker.getHeight() - 65, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString("Username: \u00a77" + this.mc.session.getUsername(), this.invoker.getWidth() / 2, (float)this.invoker.getHeight() - 54.5f, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString("Account status: " + (Donator.isDonator(this.mc.session.getUsername(), 5.0f) ? "\u00a7bUpgraded" : "\u00a78Not Upgraded"), this.invoker.getWidth() / 2, this.invoker.getHeight() - 44, -1);
            String title = "\u00a73" + Resilience.getInstance().getName() + "\u00a7f v" + Resilience.getInstance().getVersion();
            Resilience.getInstance().getStandardFont().drawStringWithShadow(title, 4.0f, this.invoker.getHeight() - 14, -1);
            Resilience.getInstance().getStandardFont().drawStringWithShadow("by Krisp", (float)(this.invoker.getWidth() - 4) - Resilience.getInstance().getStandardFont().getWidth("by Krisp"), this.invoker.getHeight() - 14, -13421569);
            int var4 = 0;
            while (var4 < this.buttonList.size()) {
                ((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, par1, par2);
                ++var4;
            }
            var4 = 0;
            while (var4 < this.labelList.size()) {
                ((GuiLabel)this.labelList.get(var4)).func_146159_a(this.mc, par1, par2);
                ++var4;
            }
            if (!hasClickedRes && Resilience.getInstance().isFirstTimeOnline()) {
                Utils.drawLine(this.invoker.getWidth() / 2, 50.0f, this.invoker.getWidth() / 2 + 130, 60.0f, 2.0f, -16776961);
                Resilience.getInstance().getStandardFont().drawString("To use \u00a7bResilience Online\u00a7f, click on Resilience!", this.invoker.getWidth() / 2 + 34, 62.0f, -1);
            }
        } else {
            super.drawScreen(par1, par2, par3);
        }
    }

}

