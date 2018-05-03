/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGeneratorBonusChest
extends WorldGenerator {
    private final WeightedRandomChestContent[] theBonusChestGenerator;
    private final int itemsToGenerateInBonusChest;
    private static final String __OBFID = "CL_00000403";

    public WorldGeneratorBonusChest(WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, int par2) {
        this.theBonusChestGenerator = par1ArrayOfWeightedRandomChestContent;
        this.itemsToGenerateInBonusChest = par2;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        Block var6;
        while (((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air || var6.getMaterial() == Material.leaves) && par4 > 1) {
            --par4;
        }
        if (par4 < 1) {
            return false;
        }
        ++par4;
        int var7 = 0;
        while (var7 < 4) {
            int var9;
            int var10;
            int var8 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            if (par1World.isAirBlock(var8, var9 = par4 + par2Random.nextInt(3) - par2Random.nextInt(3), var10 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4)) && World.doesBlockHaveSolidTopSurface(par1World, var8, var9 - 1, var10)) {
                par1World.setBlock(var8, var9, var10, Blocks.chest, 0, 2);
                TileEntityChest var11 = (TileEntityChest)par1World.getTileEntity(var8, var9, var10);
                if (var11 != null && var11 != null) {
                    WeightedRandomChestContent.generateChestContents(par2Random, this.theBonusChestGenerator, var11, this.itemsToGenerateInBonusChest);
                }
                if (par1World.isAirBlock(var8 - 1, var9, var10) && World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10)) {
                    par1World.setBlock(var8 - 1, var9, var10, Blocks.torch, 0, 2);
                }
                if (par1World.isAirBlock(var8 + 1, var9, var10) && World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10)) {
                    par1World.setBlock(var8 + 1, var9, var10, Blocks.torch, 0, 2);
                }
                if (par1World.isAirBlock(var8, var9, var10 - 1) && World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10)) {
                    par1World.setBlock(var8, var9, var10 - 1, Blocks.torch, 0, 2);
                }
                if (par1World.isAirBlock(var8, var9, var10 + 1) && World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10)) {
                    par1World.setBlock(var8, var9, var10 + 1, Blocks.torch, 0, 2);
                }
                return true;
            }
            ++var7;
        }
        return false;
    }
}

