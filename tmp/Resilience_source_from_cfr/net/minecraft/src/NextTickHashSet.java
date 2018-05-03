/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.NextTickListEntry;

public class NextTickHashSet
extends AbstractSet {
    private LongHashMap longHashMap = new LongHashMap();
    private int size = 0;
    private HashSet emptySet = new HashSet();

    public NextTickHashSet(Set oldSet) {
        this.addAll(oldSet);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        NextTickListEntry entry = (NextTickListEntry)obj;
        long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        return set == null ? false : set.contains(entry);
    }

    @Override
    public boolean add(Object obj) {
        boolean added;
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        NextTickListEntry entry = (NextTickListEntry)obj;
        long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
        HashSet<NextTickListEntry> set = (HashSet<NextTickListEntry>)this.longHashMap.getValueByKey(key);
        if (set == null) {
            set = new HashSet<NextTickListEntry>();
            this.longHashMap.add(key, set);
        }
        if (added = set.add(entry)) {
            ++this.size;
        }
        return added;
    }

    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        NextTickListEntry entry = (NextTickListEntry)obj;
        long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        if (set == null) {
            return false;
        }
        boolean removed = set.remove(entry);
        if (removed) {
            --this.size;
        }
        return removed;
    }

    public Iterator getNextTickEntries(int chunkX, int chunkZ) {
        HashSet set = this.getNextTickEntriesSet(chunkX, chunkZ);
        return set.iterator();
    }

    public HashSet getNextTickEntriesSet(int chunkX, int chunkZ) {
        long key = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        if (set == null) {
            set = this.emptySet;
        }
        return set;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not implemented");
    }
}

