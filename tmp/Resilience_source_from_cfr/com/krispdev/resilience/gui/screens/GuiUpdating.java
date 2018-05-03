/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.file.ThreadUpdateGame;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;

public class GuiUpdating
extends GuiScreen {
    private int count = 0;
    private ThreadUpdateGame downloadFile;
    public static boolean isDone = false;
    public static boolean isDownloading = false;
    public static boolean isExtracting = false;
    public static boolean isDeleting = false;

    @Override
    public void initGui() {
        try {
            Resilience.getInstance().getValues().getClass();
            URL obj = new URL("http://krispdev.com/updateDownloadIncrement.php?username=" + this.mc.session.getUsername() + "&update=" + 28);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.getResponseCode();
            con.disconnect();
            Resilience.getInstance().getFileManager().downloadFile(new File("Resilience.zip"), new URL("http://resilience.krispdev.com/Resilience.zip"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiUpdating.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -14671840);
        Utils.drawRect(50.0f, Resilience.getInstance().getInvoker().getHeight() - 40, Resilience.getInstance().getInvoker().getWidth() - 50, Resilience.getInstance().getInvoker().getHeight() - 15, -12369085);
        float onePixel = (float)(Resilience.getInstance().getInvoker().getWidth() - 100) / 100.0f;
        Utils.drawRect(50.0f, Resilience.getInstance().getInvoker().getHeight() - 40, onePixel * ThreadUpdateGame.getPercentDone() + 50.0f, Resilience.getInstance().getInvoker().getHeight() - 15, -13421569);
        super.drawScreen(i, j, f);
        Resilience.getInstance().getPanelTitleFont().drawCenteredString(isDone ? "Done. Please restart your game for changes to take effect." : (isDownloading ? "Downloading - " + Math.round(ThreadUpdateGame.getPercentDone()) + "%" : (isExtracting ? "Extracting - " + Math.round(ThreadUpdateGame.getPercentDone()) + "%" : (isDeleting ? "Deleting residue zip files..." : "Starting..."))), Resilience.getInstance().getInvoker().getWidth() / 2, 4.0f, -1);
    }
}

