/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap {
    private transient Entry[] slots = new Entry[16];
    private transient int count;
    private int threshold = 12;
    private final float growFactor = 0.75f;
    private volatile transient int versionStamp;
    private Set keySet = new HashSet();
    private static final String __OBFID = "CL_00001490";

    private static int computeHash(int par0) {
        par0 ^= par0 >>> 20 ^ par0 >>> 12;
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }

    private static int getSlotIndex(int par0, int par1) {
        return par0 & par1 - 1;
    }

    public Object lookup(int par1) {
        int var2 = IntHashMap.computeHash(par1);
        Entry var3 = this.slots[IntHashMap.getSlotIndex(var2, this.slots.length)];
        while (var3 != null) {
            if (var3.hashEntry == par1) {
                return var3.valueEntry;
            }
            var3 = var3.nextEntry;
        }
        return null;
    }

    public boolean containsItem(int par1) {
        if (this.lookupEntry(par1) != null) {
            return true;
        }
        return false;
    }

    final Entry lookupEntry(int par1) {
        int var2 = IntHashMap.computeHash(par1);
        Entry var3 = this.slots[IntHashMap.getSlotIndex(var2, this.slots.length)];
        while (var3 != null) {
            if (var3.hashEntry == par1) {
                return var3;
            }
            var3 = var3.nextEntry;
        }
        return null;
    }

    public void addKey(int par1, Object par2Obj) {
        this.keySet.add(par1);
        int var3 = IntHashMap.computeHash(par1);
        int var4 = IntHashMap.getSlotIndex(var3, this.slots.length);
        Entry var5 = this.slots[var4];
        while (var5 != null) {
            if (var5.hashEntry == par1) {
                var5.valueEntry = par2Obj;
                return;
            }
            var5 = var5.nextEntry;
        }
        ++this.versionStamp;
        this.insert(var3, par1, par2Obj, var4);
    }

    private void grow(int par1) {
        Entry[] var2 = this.slots;
        int var3 = var2.length;
        if (var3 == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
        } else {
            Entry[] var4 = new Entry[par1];
            this.copyTo(var4);
            this.slots = var4;
            this.threshold = (int)((float)par1 * 0.75f);
        }
    }

    private void copyTo(Entry[] par1ArrayOfIntHashMapEntry) {
        Entry[] var2 = this.slots;
        int var3 = par1ArrayOfIntHashMapEntry.length;
        int var4 = 0;
        while (var4 < var2.length) {
            Entry var5 = var2[var4];
            if (var5 != null) {
                Entry var6;
                var2[var4] = null;
                do {
                    var6 = var5.nextEntry;
                    int var7 = IntHashMap.getSlotIndex(var5.slotHash, var3);
                    var5.nextEntry = par1ArrayOfIntHashMapEntry[var7];
                    par1ArrayOfIntHashMapEntry[var7] = var5;
                    var5 = var6;
                } while (var6 != null);
            }
            ++var4;
        }
    }

    public Object removeObject(int par1) {
        this.keySet.remove(par1);
        Entry var2 = this.removeEntry(par1);
        return var2 == null ? null : var2.valueEntry;
    }

    final Entry removeEntry(int par1) {
        Entry var4;
        int var2 = IntHashMap.computeHash(par1);
        int var3 = IntHashMap.getSlotIndex(var2, this.slots.length);
        Entry var5 = var4 = this.slots[var3];
        while (var5 != null) {
            Entry var6 = var5.nextEntry;
            if (var5.hashEntry == par1) {
                ++this.versionStamp;
                --this.count;
                if (var4 == var5) {
                    this.slots[var3] = var6;
                } else {
                    var4.nextEntry = var6;
                }
                return var5;
            }
            var4 = var5;
            var5 = var6;
        }
        return var5;
    }

    public void clearMap() {
        ++this.versionStamp;
        Entry[] var1 = this.slots;
        int var2 = 0;
        while (var2 < var1.length) {
            var1[var2] = null;
            ++var2;
        }
        this.count = 0;
    }

    private void insert(int par1, int par2, Object par3Obj, int par4) {
        Entry var5 = this.slots[par4];
        this.slots[par4] = new Entry(par1, par2, par3Obj, var5);
        if (this.count++ >= this.threshold) {
            this.grow(2 * this.slots.length);
        }
    }

    static class Entry {
        final int hashEntry;
        Object valueEntry;
        Entry nextEntry;
        final int slotHash;
        private static final String __OBFID = "CL_00001491";

        Entry(int par1, int par2, Object par3Obj, Entry par4IntHashMapEntry) {
            this.valueEntry = par3Obj;
            this.nextEntry = par4IntHashMapEntry;
            this.hashEntry = par2;
            this.slotHash = par1;
        }

        public final int getHash() {
            return this.hashEntry;
        }

        public final Object getValue() {
            return this.valueEntry;
        }

        public final boolean equals(Object par1Obj) {
            Integer var4;
            Object var5;
            Object var6;
            if (!(par1Obj instanceof Entry)) {
                return false;
            }
            Entry var2 = (Entry)par1Obj;
            Integer var3 = this.getHash();
            if ((var3 == (var4 = Integer.valueOf(var2.getHash())) || var3 != null && var3.equals(var4)) && ((var5 = this.getValue()) == (var6 = var2.getValue()) || var5 != null && var5.equals(var6))) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return IntHashMap.computeHash(this.hashEntry);
        }

        public final String toString() {
            return String.valueOf(this.getHash()) + "=" + this.getValue();
        }
    }

}

