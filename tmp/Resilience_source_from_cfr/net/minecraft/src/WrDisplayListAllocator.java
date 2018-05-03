/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.WrDisplayListBlock;

public class WrDisplayListAllocator {
    private List<WrDisplayListBlock> listBlocks = new ArrayList<WrDisplayListBlock>();
    private WrDisplayListBlock currentBlock = null;
    private int blockIndex = -1;

    public int allocateDisplayLists(int len) {
        if (len > 0 && len <= 16384) {
            if (this.currentBlock == null || !this.currentBlock.canAllocate(len)) {
                if (this.blockIndex + 1 < this.listBlocks.size()) {
                    ++this.blockIndex;
                    this.currentBlock = this.listBlocks.get(this.blockIndex);
                } else {
                    this.currentBlock = new WrDisplayListBlock();
                    this.blockIndex = this.listBlocks.size();
                    this.listBlocks.add(this.currentBlock);
                }
                if (!this.currentBlock.canAllocate(len)) {
                    throw new IllegalArgumentException("Can not allocate: " + len);
                }
            }
            return this.currentBlock.allocate(len);
        }
        throw new IllegalArgumentException("Invalid display list length: " + len);
    }

    public void resetAllocatedLists() {
        this.currentBlock = null;
        this.blockIndex = -1;
        int i = 0;
        while (i < this.listBlocks.size()) {
            WrDisplayListBlock block = this.listBlocks.get(i);
            block.reset();
            ++i;
        }
    }

    public void deleteDisplayLists() {
        int i = 0;
        while (i < this.listBlocks.size()) {
            WrDisplayListBlock block = this.listBlocks.get(i);
            block.deleteDisplayLists();
            ++i;
        }
        this.listBlocks.clear();
        this.currentBlock = null;
        this.blockIndex = -1;
    }
}

