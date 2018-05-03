/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package com.krispdev.resilience.file;

import com.krispdev.resilience.gui.screens.GuiUpdating;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.lwjgl.Sys;

public class ThreadUpdateGame
extends Thread {
    private URL url;
    private File location;
    private static float percent = 0.0f;
    private float sizeDone;

    public ThreadUpdateGame(URL url, File location) {
        this.url = url;
        this.location = location;
    }

    @Override
    public void run() {
        block31 : {
            BufferedInputStream in;
            FileOutputStream fout;
            in = null;
            fout = null;
            try {
                try {
                    int count;
                    GuiUpdating.isDownloading = true;
                    in = new BufferedInputStream(this.url.openStream());
                    fout = new FileOutputStream(this.location);
                    HttpURLConnection con = (HttpURLConnection)this.url.openConnection();
                    byte[] data = new byte[1024];
                    while ((count = in.read(data, 0, 1024)) != -1) {
                        Thread.sleep(1);
                        fout.write(data, 0, count);
                        this.sizeDone = this.location.length();
                        percent = this.sizeDone / (float)con.getContentLength() * 100.0f;
                    }
                    con.disconnect();
                }
                catch (Exception e) {
                    Sys.openURL((String)"http://resilience.krispdev.com/downloads");
                    e.printStackTrace();
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (fout != null) {
                        try {
                            fout.close();
                        }
                        catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    percent = 0.0f;
                    break block31;
                }
            }
            catch (Throwable throwable) {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fout != null) {
                    try {
                        fout.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                percent = 0.0f;
                throw throwable;
            }
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            percent = 0.0f;
        }
        GuiUpdating.isExtracting = true;
        GuiUpdating.isDownloading = false;
        String version = "Resilience";
        try {
            File dir = new File("versions" + File.separator + version);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File jar = new File("versions" + File.separator + "Resilience" + File.separator + version + ".jar");
            File zip = new File("Resilience.zip");
            FileOutputStream out1 = new FileOutputStream("versions" + File.separator + "Resilience" + File.separator + version + ".jar");
            FileOutputStream out2 = new FileOutputStream("versions" + File.separator + "Resilience" + File.separator + version + ".json");
            ZipInputStream in2 = new ZipInputStream(new BufferedInputStream(new FileInputStream("Resilience.zip")));
            ZipEntry entry = null;
            while ((entry = in2.getNextEntry()) != null) {
                byte[] buffer;
                int len;
                if (entry.getName().equals("Resilience.jar")) {
                    buffer = new byte[8192];
                    while ((len = in2.read(buffer)) != -1) {
                        out1.write(buffer, 0, len);
                        if (percent >= 100.0f) continue;
                        percent = (float)jar.length() / (float)zip.length() * 100.0f;
                    }
                    out1.close();
                    continue;
                }
                if (!entry.getName().equals("Resilience.json")) continue;
                buffer = new byte[8192];
                while ((len = in2.read(buffer)) != -1) {
                    out2.write(buffer, 0, len);
                }
                out2.close();
            }
            in2.close();
            GuiUpdating.isExtracting = false;
            GuiUpdating.isDeleting = true;
            this.location.delete();
            GuiUpdating.isDone = true;
        }
        catch (Exception e) {
            Sys.openURL((String)"http://resilience.krispdev.com/downloads");
            e.printStackTrace();
        }
    }

    public static float getPercentDone() {
        return percent;
    }
}

