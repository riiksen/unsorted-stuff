/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.feature;

import java.io.PrintStream;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDungeons
extends WorldGenerator {
    private static final WeightedRandomChestContent[] field_111189_a = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 10), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 10), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)};
    private static final String __OBFID = "CL_00000425";

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var11;
        int var12;
        int var6 = 3;
        int var7 = par2Random.nextInt(2) + 2;
        int var8 = par2Random.nextInt(2) + 2;
        int var9 = 0;
        int var10 = par3 - var7 - 1;
        while (var10 <= par3 + var7 + 1) {
            var11 = par4 - 1;
            while (var11 <= par4 + var6 + 1) {
                var12 = par5 - var8 - 1;
                while (var12 <= par5 + var8 + 1) {
                    Material var13 = par1World.getBlock(var10, var11, var12).getMaterial();
                    if (var11 == par4 - 1 && !var13.isSolid()) {
                        return false;
                    }
                    if (var11 == par4 + var6 + 1 && !var13.isSolid()) {
                        return false;
                    }
                    if ((var10 == par3 - var7 - 1 || var10 == par3 + var7 + 1 || var12 == par5 - var8 - 1 || var12 == par5 + var8 + 1) && var11 == par4 && par1World.isAirBlock(var10, var11, var12) && par1World.isAirBlock(var10, var11 + 1, var12)) {
                        ++var9;
                    }
                    ++var12;
                }
                ++var11;
            }
            ++var10;
        }
        if (var9 >= 1 && var9 <= 5) {
            var10 = par3 - var7 - 1;
            while (var10 <= par3 + var7 + 1) {
                var11 = par4 + var6;
                while (var11 >= par4 - 1) {
                    var12 = par5 - var8 - 1;
                    while (var12 <= par5 + var8 + 1) {
                        if (var10 != par3 - var7 - 1 && var11 != par4 - 1 && var12 != par5 - var8 - 1 && var10 != par3 + var7 + 1 && var11 != par4 + var6 + 1 && var12 != par5 + var8 + 1) {
                            par1World.setBlockToAir(var10, var11, var12);
                        } else if (var11 >= 0 && !par1World.getBlock(var10, var11 - 1, var12).getMaterial().isSolid()) {
                            par1World.setBlockToAir(var10, var11, var12);
                        } else if (par1World.getBlock(var10, var11, var12).getMaterial().isSolid()) {
                            if (var11 == par4 - 1 && par2Random.nextInt(4) != 0) {
                                par1World.setBlock(var10, var11, var12, Blocks.mossy_cobblestone, 0, 2);
                            } else {
                                par1World.setBlock(var10, var11, var12, Blocks.cobblestone, 0, 2);
                            }
                        }
                        ++var12;
                    }
                    --var11;
                }
                ++var10;
            }
            var10 = 0;
            while (var10 < 2) {
                for (var11 = 0; var11 < 3; ++var11) {
                    int var14;
                    var12 = par3 + par2Random.nextInt(var7 * 2 + 1) - var7;
                    if (!par1World.isAirBlock(var12, par4, var14 = par5 + par2Random.nextInt(var8 * 2 + 1) - var8)) continue;
                    int var15 = 0;
                    if (par1World.getBlock(var12 - 1, par4, var14).getMaterial().isSolid()) {
                        ++var15;
                    }
                    if (par1World.getBlock(var12 + 1, par4, var14).getMaterial().isSolid()) {
                        ++var15;
                    }
                    if (par1World.getBlock(var12, par4, var14 - 1).getMaterial().isSolid()) {
                        ++var15;
                    }
                    if (par1World.getBlock(var12, par4, var14 + 1).getMaterial().isSolid()) {
                        ++var15;
                    }
                    if (var15 != 1) continue;
                    par1World.setBlock(var12, par4, var14, Blocks.chest, 0, 2);
                    WeightedRandomChestContent[] var16 = WeightedRandomChestContent.func_92080_a(field_111189_a, Items.enchanted_book.func_92114_b(par2Random));
                    TileEntityChest var17 = (TileEntityChest)par1World.getTileEntity(var12, par4, var14);
                    if (var17 == null) break;
                    WeightedRandomChestContent.generateChestContents(par2Random, var16, var17, 8);
                    break;
                }
                ++var10;
            }
            par1World.setBlock(par3, par4, par5, Blocks.mob_spawner, 0, 2);
            TileEntityMobSpawner var18 = (TileEntityMobSpawner)par1World.getTileEntity(par3, par4, par5);
            if (var18 != null) {
                var18.func_145881_a().setMobID(this.pickMobSpawner(par2Random));
            } else {
                System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
            }
            return true;
        }
        return false;
    }

    private String pickMobSpawner(Random par1Random) {
        int var2 = par1Random.nextInt(4);
        return var2 == 0 ? "Skeleton" : (var2 == 1 ? "Zombie" : (var2 == 2 ? "Zombie" : (var2 == 3 ? "Spider" : "")));
    }
}

