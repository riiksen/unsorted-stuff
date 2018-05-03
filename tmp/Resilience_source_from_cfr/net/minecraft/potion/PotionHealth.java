/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.potion;

import net.minecraft.potion.Potion;

public class PotionHealth
extends Potion {
    private static final String __OBFID = "CL_00001527";

    public PotionHealth(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public boolean isReady(int par1, int par2) {
        if (par1 >= 1) {
            return true;
        }
        return false;
    }
}

