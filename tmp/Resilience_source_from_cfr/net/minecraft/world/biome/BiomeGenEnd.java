/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.biome;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenEnd
extends BiomeGenBase {
    private static final String __OBFID = "CL_00000187";

    public BiomeGenEnd(int par1) {
        super(par1);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = Blocks.dirt;
        this.fillerBlock = Blocks.dirt;
        this.theBiomeDecorator = new BiomeEndDecorator();
    }

    @Override
    public int getSkyColorByTemp(float par1) {
        return 0;
    }
}

