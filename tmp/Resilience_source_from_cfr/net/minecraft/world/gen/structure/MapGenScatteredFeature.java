/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenScatteredFeature
extends MapGenStructure {
    private static List biomelist = Arrays.asList(BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland);
    private List scatteredFeatureSpawnList = new ArrayList();
    private int maxDistanceBetweenScatteredFeatures = 32;
    private int minDistanceBetweenScatteredFeatures = 8;
    private static final String __OBFID = "CL_00000471";

    public MapGenScatteredFeature() {
        this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }

    public MapGenScatteredFeature(Map par1Map) {
        this();
        for (Map.Entry var3 : par1Map.entrySet()) {
            if (!((String)var3.getKey()).equals("distance")) continue;
            this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
        }
    }

    @Override
    public String func_143025_a() {
        return "Temple";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        int var3 = par1;
        int var4 = par2;
        if (par1 < 0) {
            par1 -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        if (par2 < 0) {
            par2 -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        int var5 = par1 / this.maxDistanceBetweenScatteredFeatures;
        int var6 = par2 / this.maxDistanceBetweenScatteredFeatures;
        Random var7 = this.worldObj.setRandomSeed(var5, var6, 14357617);
        var5 *= this.maxDistanceBetweenScatteredFeatures;
        var6 *= this.maxDistanceBetweenScatteredFeatures;
        if (var3 == (var5 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures)) && var4 == (var6 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures))) {
            BiomeGenBase var8 = this.worldObj.getWorldChunkManager().getBiomeGenAt(var3 * 16 + 8, var4 * 16 + 8);
            for (BiomeGenBase var10 : biomelist) {
                if (var8 != var10) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int par1, int par2) {
        return new Start(this.worldObj, this.rand, par1, par2);
    }

    public boolean func_143030_a(int par1, int par2, int par3) {
        StructureStart var4 = this.func_143028_c(par1, par2, par3);
        if (var4 != null && var4 instanceof Start && !var4.components.isEmpty()) {
            StructureComponent var5 = (StructureComponent)var4.components.getFirst();
            return var5 instanceof ComponentScatteredFeaturePieces.SwampHut;
        }
        return false;
    }

    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }

    public static class Start
    extends StructureStart {
        private static final String __OBFID = "CL_00000472";

        public Start() {
        }

        public Start(World par1World, Random par2Random, int par3, int par4) {
            super(par3, par4);
            BiomeGenBase var5 = par1World.getBiomeGenForCoords(par3 * 16 + 8, par4 * 16 + 8);
            if (var5 != BiomeGenBase.jungle && var5 != BiomeGenBase.jungleHills) {
                if (var5 == BiomeGenBase.swampland) {
                    ComponentScatteredFeaturePieces.SwampHut var8 = new ComponentScatteredFeaturePieces.SwampHut(par2Random, par3 * 16, par4 * 16);
                    this.components.add(var8);
                } else {
                    ComponentScatteredFeaturePieces.DesertPyramid var7 = new ComponentScatteredFeaturePieces.DesertPyramid(par2Random, par3 * 16, par4 * 16);
                    this.components.add(var7);
                }
            } else {
                ComponentScatteredFeaturePieces.JunglePyramid var6 = new ComponentScatteredFeaturePieces.JunglePyramid(par2Random, par3 * 16, par4 * 16);
                this.components.add(var6);
            }
            this.updateBoundingBox();
        }
    }

}

