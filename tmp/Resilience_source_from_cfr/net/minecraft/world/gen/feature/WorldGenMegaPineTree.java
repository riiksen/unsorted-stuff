/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

public class WorldGenMegaPineTree
extends WorldGenHugeTrees {
    private boolean field_150542_e;
    private static final String __OBFID = "CL_00000421";

    public WorldGenMegaPineTree(boolean p_i45457_1_, boolean p_i45457_2_) {
        super(p_i45457_1_, 13, 15, 1, 1);
        this.field_150542_e = p_i45457_2_;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = this.func_150533_a(par2Random);
        if (!this.func_150537_a(par1World, par2Random, par3, par4, par5, var6)) {
            return false;
        }
        this.func_150541_c(par1World, par3, par5, par4 + var6, 0, par2Random);
        int var7 = 0;
        while (var7 < var6) {
            Block var8 = par1World.getBlock(par3, par4 + var7, par5);
            if (var8.getMaterial() == Material.air || var8.getMaterial() == Material.leaves) {
                this.func_150516_a(par1World, par3, par4 + var7, par5, Blocks.log, this.woodMetadata);
            }
            if (var7 < var6 - 1) {
                var8 = par1World.getBlock(par3 + 1, par4 + var7, par5);
                if (var8.getMaterial() == Material.air || var8.getMaterial() == Material.leaves) {
                    this.func_150516_a(par1World, par3 + 1, par4 + var7, par5, Blocks.log, this.woodMetadata);
                }
                if ((var8 = par1World.getBlock(par3 + 1, par4 + var7, par5 + 1)).getMaterial() == Material.air || var8.getMaterial() == Material.leaves) {
                    this.func_150516_a(par1World, par3 + 1, par4 + var7, par5 + 1, Blocks.log, this.woodMetadata);
                }
                if ((var8 = par1World.getBlock(par3, par4 + var7, par5 + 1)).getMaterial() == Material.air || var8.getMaterial() == Material.leaves) {
                    this.func_150516_a(par1World, par3, par4 + var7, par5 + 1, Blocks.log, this.woodMetadata);
                }
            }
            ++var7;
        }
        return true;
    }

    private void func_150541_c(World p_150541_1_, int p_150541_2_, int p_150541_3_, int p_150541_4_, int p_150541_5_, Random p_150541_6_) {
        int var7 = p_150541_6_.nextInt(5);
        var7 = this.field_150542_e ? (var7 += this.baseHeight) : (var7 += 3);
        int var8 = 0;
        int var9 = p_150541_4_ - var7;
        while (var9 <= p_150541_4_) {
            int var10 = p_150541_4_ - var9;
            int var11 = p_150541_5_ + MathHelper.floor_float((float)var10 / (float)var7 * 3.5f);
            this.func_150535_a(p_150541_1_, p_150541_2_, var9, p_150541_3_, var11 + (var10 > 0 && var11 == var8 && (var9 & 1) == 0 ? 1 : 0), p_150541_6_);
            var8 = var11;
            ++var9;
        }
    }

    @Override
    public void func_150524_b(World p_150524_1_, Random p_150524_2_, int p_150524_3_, int p_150524_4_, int p_150524_5_) {
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 1, p_150524_4_, p_150524_5_ - 1);
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ + 2, p_150524_4_, p_150524_5_ - 1);
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 1, p_150524_4_, p_150524_5_ + 2);
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ + 2, p_150524_4_, p_150524_5_ + 2);
        int var6 = 0;
        while (var6 < 5) {
            int var7 = p_150524_2_.nextInt(64);
            int var8 = var7 % 8;
            int var9 = var7 / 8;
            if (var8 == 0 || var8 == 7 || var9 == 0 || var9 == 7) {
                this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 3 + var8, p_150524_4_, p_150524_5_ - 3 + var9);
            }
            ++var6;
        }
    }

    private void func_150539_c(World p_150539_1_, Random p_150539_2_, int p_150539_3_, int p_150539_4_, int p_150539_5_) {
        int var6 = -2;
        while (var6 <= 2) {
            int var7 = -2;
            while (var7 <= 2) {
                if (Math.abs(var6) != 2 || Math.abs(var7) != 2) {
                    this.func_150540_a(p_150539_1_, p_150539_3_ + var6, p_150539_4_, p_150539_5_ + var7);
                }
                ++var7;
            }
            ++var6;
        }
    }

    private void func_150540_a(World p_150540_1_, int p_150540_2_, int p_150540_3_, int p_150540_4_) {
        int var5 = p_150540_3_ + 2;
        while (var5 >= p_150540_3_ - 3) {
            Block var6 = p_150540_1_.getBlock(p_150540_2_, var5, p_150540_4_);
            if (var6 == Blocks.grass || var6 == Blocks.dirt) {
                this.func_150516_a(p_150540_1_, p_150540_2_, var5, p_150540_4_, Blocks.dirt, 2);
                break;
            }
            if (var6.getMaterial() != Material.air && var5 < p_150540_3_) break;
            --var5;
        }
    }
}

