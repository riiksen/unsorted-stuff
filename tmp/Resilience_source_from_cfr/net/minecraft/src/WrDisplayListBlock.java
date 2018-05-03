/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import net.minecraft.client.renderer.GLAllocation;

public class WrDisplayListBlock {
    private int start = -1;
    private int used = this.start = GLAllocation.generateDisplayLists(16384);
    private int end = this.start + 16384;
    public static final int BLOCK_LENGTH = 16384;

    public boolean canAllocate(int len) {
        if (this.used + len < this.end) {
            return true;
        }
        return false;
    }

    public int allocate(int len) {
        if (!this.canAllocate(len)) {
            return -1;
        }
        int allocated = this.used;
        this.used += len;
        return allocated;
    }

    public void reset() {
        this.used = this.start;
    }

    public void deleteDisplayLists() {
        GLAllocation.deleteDisplayLists(this.start);
    }

    public int getStart() {
        return this.start;
    }

    public int getUsed() {
        return this.used;
    }

    public int getEnd() {
        return this.end;
    }
}

