/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class MapGenBase {
    protected int range = 8;
    protected Random rand = new Random();
    protected World worldObj;
    private static final String __OBFID = "CL_00000394";

    public void func_151539_a(IChunkProvider p_151539_1_, World p_151539_2_, int p_151539_3_, int p_151539_4_, Block[] p_151539_5_) {
        int var6 = this.range;
        this.worldObj = p_151539_2_;
        this.rand.setSeed(p_151539_2_.getSeed());
        long var7 = this.rand.nextLong();
        long var9 = this.rand.nextLong();
        int var11 = p_151539_3_ - var6;
        while (var11 <= p_151539_3_ + var6) {
            int var12 = p_151539_4_ - var6;
            while (var12 <= p_151539_4_ + var6) {
                long var13 = (long)var11 * var7;
                long var15 = (long)var12 * var9;
                this.rand.setSeed(var13 ^ var15 ^ p_151539_2_.getSeed());
                this.func_151538_a(p_151539_2_, var11, var12, p_151539_3_, p_151539_4_, p_151539_5_);
                ++var12;
            }
            ++var11;
        }
    }

    protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_) {
    }
}

