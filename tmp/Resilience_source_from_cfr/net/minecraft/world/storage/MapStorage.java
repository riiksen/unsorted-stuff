/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.ISaveHandler;

public class MapStorage {
    private ISaveHandler saveHandler;
    private Map loadedDataMap = new HashMap();
    private List loadedDataList = new ArrayList();
    private Map idCounts = new HashMap();
    private static final String __OBFID = "CL_00000604";

    public MapStorage(ISaveHandler par1ISaveHandler) {
        this.saveHandler = par1ISaveHandler;
        this.loadIdCounts();
    }

    public WorldSavedData loadData(Class par1Class, String par2Str) {
        WorldSavedData var3;
        block7 : {
            var3 = (WorldSavedData)this.loadedDataMap.get(par2Str);
            if (var3 != null) {
                return var3;
            }
            if (this.saveHandler != null) {
                try {
                    File var4 = this.saveHandler.getMapFileFromName(par2Str);
                    if (var4 == null || !var4.exists()) break block7;
                    try {
                        var3 = (WorldSavedData)par1Class.getConstructor(String.class).newInstance(par2Str);
                    }
                    catch (Exception var7) {
                        throw new RuntimeException("Failed to instantiate " + par1Class.toString(), var7);
                    }
                    FileInputStream var5 = new FileInputStream(var4);
                    NBTTagCompound var6 = CompressedStreamTools.readCompressed(var5);
                    var5.close();
                    var3.readFromNBT(var6.getCompoundTag("data"));
                }
                catch (Exception var8) {
                    var8.printStackTrace();
                }
            }
        }
        if (var3 != null) {
            this.loadedDataMap.put(par2Str, var3);
            this.loadedDataList.add(var3);
        }
        return var3;
    }

    public void setData(String par1Str, WorldSavedData par2WorldSavedData) {
        if (par2WorldSavedData == null) {
            throw new RuntimeException("Can't set null data");
        }
        if (this.loadedDataMap.containsKey(par1Str)) {
            this.loadedDataList.remove(this.loadedDataMap.remove(par1Str));
        }
        this.loadedDataMap.put(par1Str, par2WorldSavedData);
        this.loadedDataList.add(par2WorldSavedData);
    }

    public void saveAllData() {
        int var1 = 0;
        while (var1 < this.loadedDataList.size()) {
            WorldSavedData var2 = (WorldSavedData)this.loadedDataList.get(var1);
            if (var2.isDirty()) {
                this.saveData(var2);
                var2.setDirty(false);
            }
            ++var1;
        }
    }

    private void saveData(WorldSavedData par1WorldSavedData) {
        if (this.saveHandler != null) {
            try {
                File var2 = this.saveHandler.getMapFileFromName(par1WorldSavedData.mapName);
                if (var2 != null) {
                    NBTTagCompound var3 = new NBTTagCompound();
                    par1WorldSavedData.writeToNBT(var3);
                    NBTTagCompound var4 = new NBTTagCompound();
                    var4.setTag("data", var3);
                    FileOutputStream var5 = new FileOutputStream(var2);
                    CompressedStreamTools.writeCompressed(var4, var5);
                    var5.close();
                }
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
    }

    private void loadIdCounts() {
        try {
            this.idCounts.clear();
            if (this.saveHandler == null) {
                return;
            }
            File var1 = this.saveHandler.getMapFileFromName("idcounts");
            if (var1 != null && var1.exists()) {
                DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
                NBTTagCompound var3 = CompressedStreamTools.read(var2);
                var2.close();
                for (String var5 : var3.func_150296_c()) {
                    NBTBase var6 = var3.getTag(var5);
                    if (!(var6 instanceof NBTTagShort)) continue;
                    NBTTagShort var7 = (NBTTagShort)var6;
                    short var9 = var7.func_150289_e();
                    this.idCounts.put(var5, var9);
                }
            }
        }
        catch (Exception var10) {
            var10.printStackTrace();
        }
    }

    public int getUniqueDataId(String par1Str) {
        Short var2 = (Short)this.idCounts.get(par1Str);
        var2 = var2 == null ? Short.valueOf(0) : Short.valueOf((short)(var2 + 1));
        this.idCounts.put(par1Str, var2);
        if (this.saveHandler == null) {
            return var2.shortValue();
        }
        try {
            File var3 = this.saveHandler.getMapFileFromName("idcounts");
            if (var3 != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                for (String var6 : this.idCounts.keySet()) {
                    short var7 = (Short)this.idCounts.get(var6);
                    var4.setShort(var6, var7);
                }
                DataOutputStream var9 = new DataOutputStream(new FileOutputStream(var3));
                CompressedStreamTools.write(var4, var9);
                var9.close();
            }
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        return var2.shortValue();
    }
}

