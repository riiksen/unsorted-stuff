/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.profiler;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.util.HttpUtil;

public class PlayerUsageSnooper {
    private Map dataMap = new HashMap();
    private final String uniqueID = UUID.randomUUID().toString();
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;
    private final Timer threadTrigger = new Timer("Snooper Timer", true);
    private final Object syncLock = new Object();
    private final long minecraftStartTimeMilis;
    private boolean isRunning;
    private int selfCounter;
    private static final String __OBFID = "CL_00001515";

    public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage, long par3) {
        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + par1Str + "?version=" + 1);
        }
        catch (MalformedURLException var6) {
            throw new IllegalArgumentException();
        }
        this.playerStatsCollector = par2IPlayerUsage;
        this.minecraftStartTimeMilis = par3;
    }

    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.addBaseDataToSnooper();
            this.threadTrigger.schedule(new TimerTask(){
                private static final String __OBFID = "CL_00001516";

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    if (PlayerUsageSnooper.this.playerStatsCollector.isSnooperEnabled()) {
                        HashMap<String, Integer> var1;
                        Object object = PlayerUsageSnooper.this.syncLock;
                        synchronized (object) {
                            var1 = new HashMap<String, Integer>(PlayerUsageSnooper.this.dataMap);
                            var1.put("snooper_count", PlayerUsageSnooper.getSelfCounterFor(PlayerUsageSnooper.this));
                        }
                        HttpUtil.func_151226_a(PlayerUsageSnooper.this.serverUrl, var1, true);
                    }
                }
            }, 0, 900000);
        }
    }

    private void addBaseDataToSnooper() {
        this.addJvmArgsToSnooper();
        this.addData("snooper_token", this.uniqueID);
        this.addData("os_name", System.getProperty("os.name"));
        this.addData("os_version", System.getProperty("os.version"));
        this.addData("os_architecture", System.getProperty("os.arch"));
        this.addData("java_version", System.getProperty("java.version"));
        this.addData("version", "1.7.2");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }

    private void addJvmArgsToSnooper() {
        RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        List<String> var2 = var1.getInputArguments();
        int var3 = 0;
        for (String var5 : var2) {
            if (!var5.startsWith("-X")) continue;
            this.addData("jvm_arg[" + var3++ + "]", var5);
        }
        this.addData("jvm_args", var3);
    }

    public void addMemoryStatsToSnooper() {
        this.addData("memory_total", Runtime.getRuntime().totalMemory());
        this.addData("memory_max", Runtime.getRuntime().maxMemory());
        this.addData("memory_free", Runtime.getRuntime().freeMemory());
        this.addData("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addData(String par1Str, Object par2Obj) {
        Object var3 = this.syncLock;
        Object object = this.syncLock;
        synchronized (object) {
            this.dataMap.put(par1Str, par2Obj);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map getCurrentStats() {
        LinkedHashMap var1 = new LinkedHashMap();
        Object var2 = this.syncLock;
        Object object = this.syncLock;
        synchronized (object) {
            this.addMemoryStatsToSnooper();
            for (Map.Entry var4 : this.dataMap.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            return var1;
        }
    }

    public boolean isSnooperRunning() {
        return this.isRunning;
    }

    public void stopSnooper() {
        this.threadTrigger.cancel();
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }

    static int getSelfCounterFor(PlayerUsageSnooper par0PlayerUsageSnooper) {
        return par0PlayerUsageSnooper.selfCounter++;
    }

}

