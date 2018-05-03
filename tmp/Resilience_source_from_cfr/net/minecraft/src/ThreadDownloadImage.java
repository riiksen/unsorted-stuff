/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;

public class ThreadDownloadImage
extends Thread {
    private ThreadDownloadImageData parent;
    private String urlStr;
    private IImageBuffer imageBuffer;

    public ThreadDownloadImage(ThreadDownloadImageData parent, String urlStr, IImageBuffer imageBuffer) {
        this.parent = parent;
        this.urlStr = urlStr;
        this.imageBuffer = imageBuffer;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            URL e = new URL(this.urlStr);
            conn = (HttpURLConnection)e.openConnection(Minecraft.getMinecraft().getProxy());
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            if (conn.getResponseCode() / 100 != 2) {
                return;
            }
            BufferedImage var2 = ImageIO.read(conn.getInputStream());
            if (this.imageBuffer != null) {
                var2 = this.imageBuffer.parseUserSkin(var2);
            }
            this.parent.func_147641_a(var2);
            return;
        }
        catch (Exception var7) {
            System.out.println(String.valueOf(var7.getClass().getName()) + ": " + var7.getMessage());
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}

