/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

public abstract class EntityAIBase {
    private int mutexBits;
    private static final String __OBFID = "CL_00001587";

    public abstract boolean shouldExecute();

    public boolean continueExecuting() {
        return this.shouldExecute();
    }

    public boolean isInterruptible() {
        return true;
    }

    public void startExecuting() {
    }

    public void resetTask() {
    }

    public void updateTask() {
    }

    public void setMutexBits(int par1) {
        this.mutexBits = par1;
    }

    public int getMutexBits() {
        return this.mutexBits;
    }
}

