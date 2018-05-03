/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  org.apache.commons.lang3.ObjectUtils
 */
package net.minecraft.entity;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ReportedException;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher {
    private final Entity field_151511_a;
    private boolean isBlank = true;
    private static final HashMap dataTypes = new HashMap();
    private final Map watchedObjects = new HashMap();
    private boolean objectChanged;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final String __OBFID = "CL_00001559";

    static {
        dataTypes.put(Byte.class, 0);
        dataTypes.put(Short.class, 1);
        dataTypes.put(Integer.class, 2);
        dataTypes.put(Float.class, 3);
        dataTypes.put(String.class, 4);
        dataTypes.put(ItemStack.class, 5);
        dataTypes.put(ChunkCoordinates.class, 6);
    }

    public DataWatcher(Entity p_i45313_1_) {
        this.field_151511_a = p_i45313_1_;
    }

    public void addObject(int par1, Object par2Obj) {
        Integer var3 = (Integer)dataTypes.get(par2Obj.getClass());
        if (var3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + par2Obj.getClass());
        }
        if (par1 > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + par1 + "! (Max is " + 31 + ")");
        }
        if (this.watchedObjects.containsKey(par1)) {
            throw new IllegalArgumentException("Duplicate id value for " + par1 + "!");
        }
        WatchableObject var4 = new WatchableObject(var3, par1, par2Obj);
        this.lock.writeLock().lock();
        this.watchedObjects.put(par1, var4);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }

    public void addObjectByDataType(int par1, int par2) {
        WatchableObject var3 = new WatchableObject(par2, par1, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(par1, var3);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }

    public byte getWatchableObjectByte(int par1) {
        return ((Byte)this.getWatchedObject(par1).getObject()).byteValue();
    }

    public short getWatchableObjectShort(int par1) {
        return (Short)this.getWatchedObject(par1).getObject();
    }

    public int getWatchableObjectInt(int par1) {
        return (Integer)this.getWatchedObject(par1).getObject();
    }

    public float getWatchableObjectFloat(int par1) {
        return ((Float)this.getWatchedObject(par1).getObject()).floatValue();
    }

    public String getWatchableObjectString(int par1) {
        return (String)this.getWatchedObject(par1).getObject();
    }

    public ItemStack getWatchableObjectItemStack(int par1) {
        return (ItemStack)this.getWatchedObject(par1).getObject();
    }

    private WatchableObject getWatchedObject(int par1) {
        WatchableObject var2;
        this.lock.readLock().lock();
        try {
            var2 = (WatchableObject)this.watchedObjects.get(par1);
        }
        catch (Throwable var6) {
            CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
            CrashReportCategory var5 = var4.makeCategory("Synched entity data");
            var5.addCrashSection("Data ID", par1);
            throw new ReportedException(var4);
        }
        this.lock.readLock().unlock();
        return var2;
    }

    public void updateObject(int par1, Object par2Obj) {
        WatchableObject var3 = this.getWatchedObject(par1);
        if (ObjectUtils.notEqual((Object)par2Obj, (Object)var3.getObject())) {
            var3.setObject(par2Obj);
            this.field_151511_a.func_145781_i(par1);
            var3.setWatched(true);
            this.objectChanged = true;
        }
    }

    public void setObjectWatched(int par1) {
        WatchableObject.access$0(this.getWatchedObject(par1), true);
        this.objectChanged = true;
    }

    public boolean hasChanges() {
        return this.objectChanged;
    }

    public static void writeWatchedListToPacketBuffer(List p_151507_0_, PacketBuffer p_151507_1_) throws IOException {
        if (p_151507_0_ != null) {
            for (WatchableObject var3 : p_151507_0_) {
                DataWatcher.writeWatchableObjectToPacketBuffer(p_151507_1_, var3);
            }
        }
        p_151507_1_.writeByte(127);
    }

    public List getChanged() {
        ArrayList<WatchableObject> var1 = null;
        if (this.objectChanged) {
            this.lock.readLock().lock();
            for (WatchableObject var3 : this.watchedObjects.values()) {
                if (!var3.isWatched()) continue;
                var3.setWatched(false);
                if (var1 == null) {
                    var1 = new ArrayList<WatchableObject>();
                }
                var1.add(var3);
            }
            this.lock.readLock().unlock();
        }
        this.objectChanged = false;
        return var1;
    }

    public void func_151509_a(PacketBuffer p_151509_1_) throws IOException {
        this.lock.readLock().lock();
        for (WatchableObject var3 : this.watchedObjects.values()) {
            DataWatcher.writeWatchableObjectToPacketBuffer(p_151509_1_, var3);
        }
        this.lock.readLock().unlock();
        p_151509_1_.writeByte(127);
    }

    public List getAllWatched() {
        ArrayList<WatchableObject> var1 = null;
        this.lock.readLock().lock();
        for (WatchableObject var3 : this.watchedObjects.values()) {
            if (var1 == null) {
                var1 = new ArrayList<WatchableObject>();
            }
            var1.add(var3);
        }
        this.lock.readLock().unlock();
        return var1;
    }

    private static void writeWatchableObjectToPacketBuffer(PacketBuffer p_151510_0_, WatchableObject p_151510_1_) throws IOException {
        int var2 = (p_151510_1_.getObjectType() << 5 | p_151510_1_.getDataValueId() & 31) & 255;
        p_151510_0_.writeByte(var2);
        switch (p_151510_1_.getObjectType()) {
            case 0: {
                p_151510_0_.writeByte(((Byte)p_151510_1_.getObject()).byteValue());
                break;
            }
            case 1: {
                p_151510_0_.writeShort(((Short)p_151510_1_.getObject()).shortValue());
                break;
            }
            case 2: {
                p_151510_0_.writeInt((Integer)p_151510_1_.getObject());
                break;
            }
            case 3: {
                p_151510_0_.writeFloat(((Float)p_151510_1_.getObject()).floatValue());
                break;
            }
            case 4: {
                p_151510_0_.writeStringToBuffer((String)p_151510_1_.getObject());
                break;
            }
            case 5: {
                ItemStack var4 = (ItemStack)p_151510_1_.getObject();
                p_151510_0_.writeItemStackToBuffer(var4);
                break;
            }
            case 6: {
                ChunkCoordinates var3 = (ChunkCoordinates)p_151510_1_.getObject();
                p_151510_0_.writeInt(var3.posX);
                p_151510_0_.writeInt(var3.posY);
                p_151510_0_.writeInt(var3.posZ);
            }
        }
    }

    public static List readWatchedListFromPacketBuffer(PacketBuffer p_151508_0_) throws IOException {
        ArrayList<Object> var1 = null;
        byte var2 = p_151508_0_.readByte();
        while (var2 != 127) {
            if (var1 == null) {
                var1 = new ArrayList<Object>();
            }
            int var3 = (var2 & 224) >> 5;
            int var4 = var2 & 31;
            WatchableObject var5 = null;
            switch (var3) {
                case 0: {
                    var5 = new WatchableObject(var3, var4, Byte.valueOf(p_151508_0_.readByte()));
                    break;
                }
                case 1: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readShort());
                    break;
                }
                case 2: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readInt());
                    break;
                }
                case 3: {
                    var5 = new WatchableObject(var3, var4, Float.valueOf(p_151508_0_.readFloat()));
                    break;
                }
                case 4: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readStringFromBuffer(32767));
                    break;
                }
                case 5: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readItemStackFromBuffer());
                    break;
                }
                case 6: {
                    int var6 = p_151508_0_.readInt();
                    int var7 = p_151508_0_.readInt();
                    int var8 = p_151508_0_.readInt();
                    var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
                }
            }
            var1.add(var5);
            var2 = p_151508_0_.readByte();
        }
        return var1;
    }

    public void updateWatchedObjectsFromList(List par1List) {
        this.lock.writeLock().lock();
        Iterator var2 = par1List.iterator();
        while (var2.hasNext()) {
            try {
                WatchableObject var3 = (WatchableObject)var2.next();
                WatchableObject var4 = (WatchableObject)this.watchedObjects.get(var3.getDataValueId());
                if (var4 == null) continue;
                var4.setObject(var3.getObject());
                this.field_151511_a.func_145781_i(var3.getDataValueId());
            }
            catch (Exception var3) {
                // empty catch block
            }
        }
        this.lock.writeLock().unlock();
        this.objectChanged = true;
    }

    public boolean getIsBlank() {
        return this.isBlank;
    }

    public void func_111144_e() {
        this.objectChanged = false;
    }

    public static class WatchableObject {
        private final int objectType;
        private final int dataValueId;
        private Object watchedObject;
        private boolean watched;
        private static final String __OBFID = "CL_00001560";

        public WatchableObject(int par1, int par2, Object par3Obj) {
            this.dataValueId = par2;
            this.watchedObject = par3Obj;
            this.objectType = par1;
            this.watched = true;
        }

        public int getDataValueId() {
            return this.dataValueId;
        }

        public void setObject(Object par1Obj) {
            this.watchedObject = par1Obj;
        }

        public Object getObject() {
            return this.watchedObject;
        }

        public int getObjectType() {
            return this.objectType;
        }

        public boolean isWatched() {
            return this.watched;
        }

        public void setWatched(boolean par1) {
            this.watched = par1;
        }

        static /* synthetic */ void access$0(WatchableObject watchableObject, boolean bl) {
            watchableObject.watched = bl;
        }
    }

}

