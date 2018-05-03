/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 *  org.lwjgl.input.Mouse
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.GuiAccountScreen;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiDonateCredits;
import com.krispdev.resilience.gui.screens.GuiInfo;
import com.krispdev.resilience.gui.screens.GuiManagerSelect;
import com.krispdev.resilience.gui.screens.GuiResilienceOptions;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.gui.GuiFriendManager;
import com.krispdev.resilience.online.gui.GuiLogin;
import com.krispdev.resilience.online.gui.GuiRegister;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

public class GuiResilienceMain
extends GuiScreen {
    public static GuiResilienceMain screen = new GuiResilienceMain(new HookGuiMainMenu());
    private ArrayList<String> updateData = new ArrayList();
    private int count = 0;
    private GuiScreen parentScreen;
    private ArrayList<Object[]> links = new ArrayList();
    private int scroll = 0;
    private int maxScroll = 0;
    private String loading = "";

    public GuiResilienceMain(GuiScreen parent) {
        this.parentScreen = parent;
        this.getUpdateData();
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        CheckBox.checkBoxList.clear();
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(6, 4.0f, 50.5f, 132.0f, 20.0f, "\u00a7bOnline"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, 4.0f, 170.5f, 132.0f, 20.0f, "Info"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(2, 4.0f, 74.5f, 132.0f, 20.0f, "Options"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(3, 4.0f, 98.5f, 132.0f, 20.0f, "Accounts"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(4, 4.0f, 122.5f, 132.0f, 20.0f, "Donate!"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(5, 4.0f, 146.5f, 132.0f, 20.0f, "Managers"));
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 4.0f, 194.5f, 132.0f, 20.0f, "Back"));
    }

    public void getUpdateData() {
        new Thread(){

            @Override
            public void run() {
                try {
                    String text;
                    GuiResilienceMain.this.updateData.add("Loading...");
                    URL url = new URL("http://krispdev.com/Resilience-Update-Info");
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((text = in.readLine()) != null) {
                        GuiResilienceMain.this.updateData.add(text);
                    }
                    GuiResilienceMain.this.updateData.remove(GuiResilienceMain.this.updateData.indexOf("Loading..."));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (Resilience.getInstance().getInvoker().getId(btn) == 0) {
            Resilience.getInstance().getInvoker().displayScreen(this.parentScreen);
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 1) {
            Resilience.getInstance().getInvoker().displayScreen(new GuiInfo(this));
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 2) {
            Resilience.getInstance().getInvoker().displayScreen(new GuiResilienceOptions(this));
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 3) {
            Resilience.getInstance().getInvoker().displayScreen(GuiAccountScreen.guiScreen);
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 4) {
            Resilience.getInstance().getInvoker().displayScreen(GuiDonateCredits.guiDonate);
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 5) {
            Resilience.getInstance().getInvoker().displayScreen(new GuiManagerSelect(this));
        } else if (Resilience.getInstance().getInvoker().getId(btn) == 6) {
            new Thread(){

                @Override
                public void run() {
                    if (FileUtils.containsString(Resilience.getInstance().getFileManager().online, Resilience.getInstance().getInvoker().getSessionUsername(), 0)) {
                        GuiResilienceMain.access$1(GuiResilienceMain.this, "Loading...");
                        String result = Utils.sendGetRequest("http://resilience.krispdev.com/updateOnline.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&online=true&channel=" + Resilience.getInstance().getValues().userChannel);
                        if (result.equals("")) {
                            Resilience.getInstance().getInvoker().displayScreen(new GuiFriendManager(GuiResilienceMain.screen));
                        } else {
                            Resilience.getInstance().getInvoker().displayScreen(new GuiLogin(GuiResilienceMain.screen));
                        }
                        GuiResilienceMain.access$1(GuiResilienceMain.this, "");
                    } else {
                        Resilience.getInstance().getInvoker().displayScreen(new GuiRegister(GuiResilienceMain.screen, true));
                    }
                }
            }.start();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.scroll += Mouse.getDWheel() / 12;
        if (this.scroll < this.maxScroll) {
            this.scroll = this.maxScroll;
        }
        if (this.scroll > 0) {
            this.scroll = 0;
        }
        this.links.clear();
        GuiResilienceMain.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -16777216);
        GuiResilienceMain.drawRect(140, 50, Resilience.getInstance().getInvoker().getWidth() - 8, Resilience.getInstance().getInvoker().getHeight() - 8, -15724528);
        Resilience.getInstance().getLargeFont().drawString("Change Logs:", 148.0f, 54.0f, -16711681);
        Resilience.getInstance().getLargeFont().drawCenteredString(this.loading, Resilience.getInstance().getInvoker().getWidth() / 2, 8.0f, -16711681);
        Resilience.getInstance().getXLargeFont().drawString(Resilience.getInstance().getName(), 4.0f, 1.0f, -16763905);
        Utils.drawSmallHL(140.0f, 74.0f, Resilience.getInstance().getInvoker().getWidth() - 8, -1);
        try {
            for (String s : this.updateData) {
                if (78 + this.count + this.scroll < Resilience.getInstance().getInvoker().getHeight() - 8 - 10 && 78 + this.count + this.scroll > 70) {
                    Resilience.getInstance().getStandardFont().drawString(s.replaceAll("&", "\u00a7"), 148.0f, 78 + this.count + this.scroll, -1);
                    if (s.contains("http://")) {
                        this.links.add(new Object[]{new Rectangle(148, 78 + this.count - 4 + this.scroll, (int)Resilience.getInstance().getStandardFont().getWidth(StringUtils.stripControlCodes(s)) + 148, this.count + 4 + this.scroll), s});
                    }
                }
                this.count += 12;
            }
        }
        catch (Exception s) {
            // empty catch block
        }
        this.maxScroll = - 78 + this.count + 4 - (Resilience.getInstance().getInvoker().getHeight() - 8);
        this.count = 0;
        super.drawScreen(i, j, f);
    }

    @Override
    public void mouseClicked(int x, int y, int btn) {
        for (Object[] o : this.links) {
            Rectangle r = (Rectangle)o[0];
            if ((double)x < r.getX() || (double)x > r.getMaxX() || (double)y < r.getY() || (double)y > r.getMaxY()) continue;
            Sys.openURL((String)((String)o[1]));
            break;
        }
        super.mouseClicked(x, y, btn);
    }

    static /* synthetic */ void access$1(GuiResilienceMain guiResilienceMain, String string) {
        guiResilienceMain.loading = string;
    }

}

