/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeEndDecorator
extends BiomeDecorator {
    protected WorldGenerator spikeGen = new WorldGenSpikes(Blocks.end_stone);
    private static final String __OBFID = "CL_00000188";

    @Override
    protected void func_150513_a(BiomeGenBase p_150513_1_) {
        this.generateOres();
        if (this.randomGenerator.nextInt(5) == 0) {
            int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int var4 = this.currentWorld.getTopSolidOrLiquidBlock(var2, var3);
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, var2, var4, var3);
        }
        if (this.chunk_X == 0 && this.chunk_Z == 0) {
            EntityDragon var5 = new EntityDragon(this.currentWorld);
            var5.setLocationAndAngles(0.0, 128.0, 0.0, this.randomGenerator.nextFloat() * 360.0f, 0.0f);
            this.currentWorld.spawnEntityInWorld(var5);
        }
    }
}

