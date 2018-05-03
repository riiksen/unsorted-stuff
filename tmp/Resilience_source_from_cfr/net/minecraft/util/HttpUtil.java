/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {
    private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00001485";

    public static String buildPostString(Map par0Map) {
        StringBuilder var1 = new StringBuilder();
        for (Map.Entry var3 : par0Map.entrySet()) {
            if (var1.length() > 0) {
                var1.append('&');
            }
            try {
                var1.append(URLEncoder.encode((String)var3.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
            }
            if (var3.getValue() == null) continue;
            var1.append('=');
            try {
                var1.append(URLEncoder.encode(var3.getValue().toString(), "UTF-8"));
            }
            catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
            }
        }
        return var1.toString();
    }

    public static String func_151226_a(URL p_151226_0_, Map p_151226_1_, boolean p_151226_2_) {
        return HttpUtil.func_151225_a(p_151226_0_, HttpUtil.buildPostString(p_151226_1_), p_151226_2_);
    }

    private static String func_151225_a(URL p_151225_0_, String p_151225_1_, boolean p_151225_2_) {
        try {
            String var7;
            Proxy var3;
            Proxy proxy = var3 = MinecraftServer.getServer() == null ? null : MinecraftServer.getServer().getServerProxy();
            if (var3 == null) {
                var3 = Proxy.NO_PROXY;
            }
            HttpURLConnection var4 = (HttpURLConnection)p_151225_0_.openConnection(var3);
            var4.setRequestMethod("POST");
            var4.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            var4.setRequestProperty("Content-Length", "" + p_151225_1_.getBytes().length);
            var4.setRequestProperty("Content-Language", "en-US");
            var4.setUseCaches(false);
            var4.setDoInput(true);
            var4.setDoOutput(true);
            DataOutputStream var5 = new DataOutputStream(var4.getOutputStream());
            var5.writeBytes(p_151225_1_);
            var5.flush();
            var5.close();
            BufferedReader var6 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
            StringBuffer var8 = new StringBuffer();
            while ((var7 = var6.readLine()) != null) {
                var8.append(var7);
                var8.append('\r');
            }
            var6.close();
            return var8.toString();
        }
        catch (Exception var9) {
            if (!p_151225_2_) {
                logger.error("Could not post to " + p_151225_0_, (Throwable)var9);
            }
            return "";
        }
    }

    public static void func_151223_a(final File p_151223_0_, final String p_151223_1_, final DownloadListener p_151223_2_, final Map p_151223_3_, final int p_151223_4_, IProgressUpdate p_151223_5_, final Proxy p_151223_6_) {
        Thread var7 = new Thread(new Runnable(){
            private static final String __OBFID = "CL_00001486";

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                block39 : {
                    block35 : {
                        block36 : {
                            var1 = null;
                            var2 = null;
                            var3 = null;
                            if (IProgressUpdate.this != null) {
                                IProgressUpdate.this.resetProgressAndMessage("Downloading Texture Pack");
                                IProgressUpdate.this.resetProgresAndWorkingMessage("Making Request...");
                            }
                            var4 = new byte[4096];
                            var5 = new URL(p_151223_1_);
                            var1 = var5.openConnection(p_151223_6_);
                            var6 = 0.0f;
                            var7 = p_151223_3_.entrySet().size();
                            for (Map.Entry<K, V> var9 : p_151223_3_.entrySet()) {
                                var1.setRequestProperty((String)var9.getKey(), (String)var9.getValue());
                                if (IProgressUpdate.this == null) continue;
                                IProgressUpdate.this.setLoadingProgress((int)((var6 += 1.0f) / var7 * 100.0f));
                            }
                            var2 = var1.getInputStream();
                            var7 = var1.getContentLength();
                            var28 = var1.getContentLength();
                            if (IProgressUpdate.this != null) {
                                IProgressUpdate.this.resetProgresAndWorkingMessage(String.format("Downloading file (%.2f MB)...", new Object[]{Float.valueOf(var7 / 1000.0f / 1000.0f)}));
                            }
                            if (!p_151223_0_.exists()) ** GOTO lbl48
                            var29 = p_151223_0_.length();
                            if (var29 != (long)var28) break block35;
                            p_151223_2_.func_148522_a(p_151223_0_);
                            if (IProgressUpdate.this == null) break block36;
                            IProgressUpdate.this.func_146586_a();
                        }
                        try {
                            if (var2 != null) {
                                var2.close();
                            }
                        }
                        catch (IOException var13_14) {
                            // empty catch block
                        }
                        try {
                            if (var3 == null) return;
                            var3.close();
                            return;
                        }
                        catch (IOException var13_15) {
                            // empty catch block
                        }
                        return;
                    }
                    HttpUtil.access$0().warn("Deleting " + p_151223_0_ + " as it does not match what we currently have (" + var28 + " vs our " + var29 + ").");
                    p_151223_0_.delete();
                    break block39;
lbl48: // 1 sources:
                    if (p_151223_0_.getParentFile() != null) {
                        p_151223_0_.getParentFile().mkdirs();
                    }
                }
                var3 = new DataOutputStream(new FileOutputStream(p_151223_0_));
                if (p_151223_4_ > 0 && var7 > (float)p_151223_4_) {
                    if (IProgressUpdate.this == null) throw new IOException("Filesize is bigger than maximum allowed (file is " + var6 + ", limit is " + p_151223_4_ + ")");
                    IProgressUpdate.this.func_146586_a();
                    throw new IOException("Filesize is bigger than maximum allowed (file is " + var6 + ", limit is " + p_151223_4_ + ")");
                }
                var31 = false;
                do {
                    if ((var30 = var2.read(var4)) < 0) {
                        p_151223_2_.func_148522_a(p_151223_0_);
                        if (IProgressUpdate.this == null) return;
                        IProgressUpdate.this.func_146586_a();
                        break;
                    }
                    var6 += (float)var30;
                    if (IProgressUpdate.this != null) {
                        IProgressUpdate.this.setLoadingProgress((int)(var6 / var7 * 100.0f));
                    }
                    if (p_151223_4_ > 0 && var6 > (float)p_151223_4_) {
                        if (IProgressUpdate.this == null) throw new IOException("Filesize was bigger than maximum allowed (got >= " + var6 + ", limit was " + p_151223_4_ + ")");
                        IProgressUpdate.this.func_146586_a();
                        throw new IOException("Filesize was bigger than maximum allowed (got >= " + var6 + ", limit was " + p_151223_4_ + ")");
                    }
                    var3.write(var4, 0, var30);
                } while (true);
                try {
                    if (var2 != null) {
                        var2.close();
                    }
                }
                catch (IOException var13_16) {
                    // empty catch block
                }
                try {
                    if (var3 == null) return;
                    var3.close();
                    return;
                }
                catch (IOException var13_17) {
                    // empty catch block
                }
                return;
                catch (Throwable var26) {
                    try {
                        var26.printStackTrace();
                        return;
                    }
                    catch (Throwable var12_23) {}
                    throw var12_23;
                    finally {
                        try {
                            if (var2 != null) {
                                var2.close();
                            }
                        }
                        catch (IOException var13_20) {}
                        try {
                            if (var3 != null) {
                                var3.close();
                            }
                        }
                        catch (IOException var13_21) {}
                    }
                }
            }
        }, "File Downloader #" + downloadThreadsStarted.incrementAndGet());
        var7.setDaemon(true);
        var7.start();
    }

    public static int func_76181_a() throws IOException {
        int var10;
        ServerSocket var0 = null;
        boolean var1 = true;
        try {
            var0 = new ServerSocket(0);
            var10 = var0.getLocalPort();
        }
        finally {
            try {
                if (var0 != null) {
                    var0.close();
                }
            }
            catch (IOException iOException) {}
        }
        return var10;
    }

    static /* synthetic */ Logger access$0() {
        return logger;
    }

    public static interface DownloadListener {
        public void func_148522_a(File var1);
    }

}

