/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

public class LongHashMap {
    private transient Entry[] hashArray = new Entry[1024];
    private transient int numHashElements;
    private int capacity = (int)(0.75f * (float)this.hashArray.length);
    private final float percentUseable = 0.75f;
    private volatile transient int modCount;
    private static final String __OBFID = "CL_00001492";

    private static int getHashedKey(long par0) {
        return (int)(par0 ^ par0 >>> 27);
    }

    private static int hash(int par0) {
        par0 ^= par0 >>> 20 ^ par0 >>> 12;
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }

    private static int getHashIndex(int par0, int par1) {
        return par0 & par1 - 1;
    }

    public int getNumHashElements() {
        return this.numHashElements;
    }

    public Object getValueByKey(long par1) {
        int var3 = LongHashMap.getHashedKey(par1);
        Entry var4 = this.hashArray[LongHashMap.getHashIndex(var3, this.hashArray.length)];
        while (var4 != null) {
            if (var4.key == par1) {
                return var4.value;
            }
            var4 = var4.nextEntry;
        }
        return null;
    }

    public boolean containsItem(long par1) {
        if (this.getEntry(par1) != null) {
            return true;
        }
        return false;
    }

    final Entry getEntry(long par1) {
        int var3 = LongHashMap.getHashedKey(par1);
        Entry var4 = this.hashArray[LongHashMap.getHashIndex(var3, this.hashArray.length)];
        while (var4 != null) {
            if (var4.key == par1) {
                return var4;
            }
            var4 = var4.nextEntry;
        }
        return null;
    }

    public void add(long par1, Object par3Obj) {
        int var4 = LongHashMap.getHashedKey(par1);
        int var5 = LongHashMap.getHashIndex(var4, this.hashArray.length);
        Entry var6 = this.hashArray[var5];
        while (var6 != null) {
            if (var6.key == par1) {
                var6.value = par3Obj;
                return;
            }
            var6 = var6.nextEntry;
        }
        ++this.modCount;
        this.createKey(var4, par1, par3Obj, var5);
    }

    private void resizeTable(int par1) {
        Entry[] var2 = this.hashArray;
        int var3 = var2.length;
        if (var3 == 1073741824) {
            this.capacity = Integer.MAX_VALUE;
        } else {
            Entry[] var4 = new Entry[par1];
            this.copyHashTableTo(var4);
            this.hashArray = var4;
            float var10001 = par1;
            this.getClass();
            this.capacity = (int)(var10001 * 0.75f);
        }
    }

    private void copyHashTableTo(Entry[] par1ArrayOfLongHashMapEntry) {
        Entry[] var2 = this.hashArray;
        int var3 = par1ArrayOfLongHashMapEntry.length;
        int var4 = 0;
        while (var4 < var2.length) {
            Entry var5 = var2[var4];
            if (var5 != null) {
                Entry var6;
                var2[var4] = null;
                do {
                    var6 = var5.nextEntry;
                    int var7 = LongHashMap.getHashIndex(var5.hash, var3);
                    var5.nextEntry = par1ArrayOfLongHashMapEntry[var7];
                    par1ArrayOfLongHashMapEntry[var7] = var5;
                    var5 = var6;
                } while (var6 != null);
            }
            ++var4;
        }
    }

    public Object remove(long par1) {
        Entry var3 = this.removeKey(par1);
        return var3 == null ? null : var3.value;
    }

    final Entry removeKey(long par1) {
        Entry var5;
        int var3 = LongHashMap.getHashedKey(par1);
        int var4 = LongHashMap.getHashIndex(var3, this.hashArray.length);
        Entry var6 = var5 = this.hashArray[var4];
        while (var6 != null) {
            Entry var7 = var6.nextEntry;
            if (var6.key == par1) {
                ++this.modCount;
                --this.numHashElements;
                if (var5 == var6) {
                    this.hashArray[var4] = var7;
                } else {
                    var5.nextEntry = var7;
                }
                return var6;
            }
            var5 = var6;
            var6 = var7;
        }
        return var6;
    }

    private void createKey(int par1, long par2, Object par4Obj, int par5) {
        Entry var6 = this.hashArray[par5];
        this.hashArray[par5] = new Entry(par1, par2, par4Obj, var6);
        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }

    public double getKeyDistribution() {
        int countValid = 0;
        int i = 0;
        while (i < this.hashArray.length) {
            if (this.hashArray[i] != null) {
                ++countValid;
            }
            ++i;
        }
        return 1.0 * (double)countValid / (double)this.numHashElements;
    }

    static class Entry {
        final long key;
        Object value;
        Entry nextEntry;
        final int hash;
        private static final String __OBFID = "CL_00001493";

        Entry(int par1, long par2, Object par4Obj, Entry par5LongHashMapEntry) {
            this.value = par4Obj;
            this.nextEntry = par5LongHashMapEntry;
            this.key = par2;
            this.hash = par1;
        }

        public final long getKey() {
            return this.key;
        }

        public final Object getValue() {
            return this.value;
        }

        public final boolean equals(Object par1Obj) {
            Object var5;
            Object var6;
            Long var4;
            if (!(par1Obj instanceof Entry)) {
                return false;
            }
            Entry var2 = (Entry)par1Obj;
            Long var3 = this.getKey();
            if ((var3 == (var4 = Long.valueOf(var2.getKey())) || var3 != null && var3.equals(var4)) && ((var5 = this.getValue()) == (var6 = var2.getValue()) || var5 != null && var5.equals(var6))) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return LongHashMap.getHashedKey(this.key);
        }

        public final String toString() {
            return String.valueOf(this.getKey()) + "=" + this.getValue();
        }
    }

}

