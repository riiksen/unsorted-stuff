/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.utilities;

import com.krispdev.resilience.utilities.XrayBlock;
import java.util.ArrayList;

public class XrayUtils {
    public ArrayList<XrayBlock> xrayBlocks = new ArrayList();
    public boolean xrayEnabled = false;

    public boolean shouldRenderBlock(XrayBlock block) {
        for (XrayBlock xBlock : this.xrayBlocks) {
            if (xBlock.getId() != block.getId()) continue;
            return true;
        }
        return false;
    }

    public boolean shouldRenderBlock(int block) {
        for (XrayBlock xBlock : this.xrayBlocks) {
            if (xBlock.getId() != block) continue;
            return true;
        }
        return false;
    }

    public boolean removeBlock(int block) {
        for (XrayBlock xBlock : this.xrayBlocks) {
            if (xBlock.getId() != block) continue;
            this.xrayBlocks.remove(this.xrayBlocks.indexOf(xBlock));
            return true;
        }
        return false;
    }

    public boolean addBlock(int block) {
        for (XrayBlock xBlock : this.xrayBlocks) {
            if (xBlock.getId() != block) continue;
            return false;
        }
        this.xrayBlocks.add(new XrayBlock(block));
        return true;
    }
}

