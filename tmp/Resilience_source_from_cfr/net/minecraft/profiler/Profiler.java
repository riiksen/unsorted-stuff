/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.profiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
    private static final Logger logger = LogManager.getLogger();
    private final List sectionList = new ArrayList();
    private final List timestampList = new ArrayList();
    public boolean profilingEnabled;
    private String profilingSection = "";
    private final Map profilingMap = new HashMap();
    private static final String __OBFID = "CL_00001497";
    public boolean profilerGlobalEnabled;
    private boolean profilerLocalEnabled;
    private long startTickNano;
    public long timeTickNano;
    private long startUpdateChunksNano;
    public long timeUpdateChunksNano;

    public Profiler() {
        this.profilerLocalEnabled = this.profilerGlobalEnabled = true;
        this.startTickNano = 0;
        this.timeTickNano = 0;
        this.startUpdateChunksNano = 0;
        this.timeUpdateChunksNano = 0;
    }

    public void clearProfiling() {
        this.profilingMap.clear();
        this.profilingSection = "";
        this.sectionList.clear();
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
    }

    public void startSection(String par1Str) {
        if (Config.getGameSettings().showDebugInfo) {
            if (this.startTickNano == 0 && par1Str.equals("tick")) {
                this.startTickNano = System.nanoTime();
            }
            if (this.startTickNano != 0 && par1Str.equals("preRenderErrors")) {
                this.timeTickNano = System.nanoTime() - this.startTickNano;
                this.startTickNano = 0;
            }
            if (this.startUpdateChunksNano == 0 && par1Str.equals("updatechunks")) {
                this.startUpdateChunksNano = System.nanoTime();
            }
            if (this.startUpdateChunksNano != 0 && par1Str.equals("terrain")) {
                this.timeUpdateChunksNano = System.nanoTime() - this.startUpdateChunksNano;
                this.startUpdateChunksNano = 0;
            }
        }
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            if (this.profilingSection.length() > 0) {
                this.profilingSection = String.valueOf(this.profilingSection) + ".";
            }
            this.profilingSection = String.valueOf(this.profilingSection) + par1Str;
            this.sectionList.add(this.profilingSection);
            this.timestampList.add(System.nanoTime());
        }
    }

    public void endSection() {
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            long var1 = System.nanoTime();
            long var3 = (Long)this.timestampList.remove(this.timestampList.size() - 1);
            this.sectionList.remove(this.sectionList.size() - 1);
            long var5 = var1 - var3;
            if (this.profilingMap.containsKey(this.profilingSection)) {
                this.profilingMap.put(this.profilingSection, (Long)this.profilingMap.get(this.profilingSection) + var5);
            } else {
                this.profilingMap.put(this.profilingSection, var5);
            }
            if (var5 > 100000000) {
                logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + (double)var5 / 1000000.0 + " ms");
            }
            this.profilingSection = !this.sectionList.isEmpty() ? (String)this.sectionList.get(this.sectionList.size() - 1) : "";
        }
    }

    public List getProfilingData(String par1Str) {
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
        if (!this.profilerLocalEnabled) {
            return new ArrayList<Result>(Arrays.asList(new Result("root", 0.0, 0.0)));
        }
        if (!this.profilingEnabled) {
            return null;
        }
        long var3 = this.profilingMap.containsKey("root") ? (Long)this.profilingMap.get("root") : 0;
        long var5 = this.profilingMap.containsKey(par1Str) ? (Long)this.profilingMap.get(par1Str) : -1;
        ArrayList<Result> var7 = new ArrayList<Result>();
        if (par1Str.length() > 0) {
            par1Str = String.valueOf(par1Str) + ".";
        }
        long var8 = 0;
        for (String var21 : this.profilingMap.keySet()) {
            if (var21.length() <= par1Str.length() || !var21.startsWith(par1Str) || var21.indexOf(".", par1Str.length() + 1) >= 0) continue;
            var8 += ((Long)this.profilingMap.get(var21)).longValue();
        }
        float var211 = var8;
        if (var8 < var5) {
            var8 = var5;
        }
        if (var3 < var8) {
            var3 = var8;
        }
        for (String var12 : this.profilingMap.keySet()) {
            if (var12.length() <= par1Str.length() || !var12.startsWith(par1Str) || var12.indexOf(".", par1Str.length() + 1) >= 0) continue;
            long var13 = (Long)this.profilingMap.get(var12);
            double var15 = (double)var13 * 100.0 / (double)var8;
            double var17 = (double)var13 * 100.0 / (double)var3;
            String var19 = var12.substring(par1Str.length());
            var7.add(new Result(var19, var15, var17));
        }
        for (String var12 : this.profilingMap.keySet()) {
            this.profilingMap.put(var12, (Long)this.profilingMap.get(var12) * 999 / 1000);
        }
        if ((float)var8 > var211) {
            var7.add(new Result("unspecified", (double)((float)var8 - var211) * 100.0 / (double)var8, (double)((float)var8 - var211) * 100.0 / (double)var3));
        }
        Collections.sort(var7);
        var7.add(0, new Result(par1Str, 100.0, (double)var8 * 100.0 / (double)var3));
        return var7;
    }

    public void endStartSection(String par1Str) {
        if (this.profilerLocalEnabled) {
            this.endSection();
            this.startSection(par1Str);
        }
    }

    public String getNameOfLastSection() {
        return this.sectionList.size() == 0 ? "[UNKNOWN]" : (String)this.sectionList.get(this.sectionList.size() - 1);
    }

    public static final class Result
    implements Comparable {
        public double field_76332_a;
        public double field_76330_b;
        public String field_76331_c;
        private static final String __OBFID = "CL_00001498";

        public Result(String par1Str, double par2, double par4) {
            this.field_76331_c = par1Str;
            this.field_76332_a = par2;
            this.field_76330_b = par4;
        }

        public int compareTo(Result par1ProfilerResult) {
            return par1ProfilerResult.field_76332_a < this.field_76332_a ? -1 : (par1ProfilerResult.field_76332_a > this.field_76332_a ? 1 : par1ProfilerResult.field_76331_c.compareTo(this.field_76331_c));
        }

        public int func_76329_a() {
            return (this.field_76331_c.hashCode() & 11184810) + 4473924;
        }

        public int compareTo(Object par1Obj) {
            return this.compareTo((Result)par1Obj);
        }
    }

}

