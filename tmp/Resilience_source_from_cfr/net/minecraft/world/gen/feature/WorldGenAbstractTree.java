/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGenAbstractTree
extends WorldGenerator {
    private static final String __OBFID = "CL_00000399";

    public WorldGenAbstractTree(boolean p_i45448_1_) {
        super(p_i45448_1_);
    }

    protected boolean func_150523_a(Block p_150523_1_) {
        if (p_150523_1_.getMaterial() != Material.air && p_150523_1_.getMaterial() != Material.leaves && p_150523_1_ != Blocks.grass && p_150523_1_ != Blocks.dirt && p_150523_1_ != Blocks.log && p_150523_1_ != Blocks.log2 && p_150523_1_ != Blocks.sapling && p_150523_1_ != Blocks.vine) {
            return false;
        }
        return true;
    }

    public void func_150524_b(World p_150524_1_, Random p_150524_2_, int p_150524_3_, int p_150524_4_, int p_150524_5_) {
    }
}

