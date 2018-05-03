/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class MapGenStructure
extends MapGenBase {
    private MapGenStructureData field_143029_e;
    protected Map structureMap = new HashMap();
    private static final String __OBFID = "CL_00000505";

    public abstract String func_143025_a();

    @Override
    protected final void func_151538_a(World p_151538_1_, final int p_151538_2_, final int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_) {
        this.func_143027_a(p_151538_1_);
        if (!this.structureMap.containsKey(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_))) {
            this.rand.nextInt();
            try {
                if (this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_)) {
                    StructureStart var7 = this.getStructureStart(p_151538_2_, p_151538_3_);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_), var7);
                    this.func_143026_a(p_151538_2_, p_151538_3_, var7);
                }
            }
            catch (Throwable var10) {
                CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception preparing structure feature");
                CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
                var9.addCrashSectionCallable("Is feature chunk", new Callable(){
                    private static final String __OBFID = "CL_00000506";

                    public String call() {
                        return MapGenStructure.this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_) ? "True" : "False";
                    }
                });
                var9.addCrashSection("Chunk location", String.format("%d,%d", p_151538_2_, p_151538_3_));
                var9.addCrashSectionCallable("Chunk pos hash", new Callable(){
                    private static final String __OBFID = "CL_00000507";

                    public String call() {
                        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_));
                    }
                });
                var9.addCrashSectionCallable("Structure type", new Callable(){
                    private static final String __OBFID = "CL_00000508";

                    public String call() {
                        return MapGenStructure.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(var8);
            }
        }
    }

    public boolean generateStructuresInChunk(World par1World, Random par2Random, int par3, int par4) {
        this.func_143027_a(par1World);
        int var5 = (par3 << 4) + 8;
        int var6 = (par4 << 4) + 8;
        boolean var7 = false;
        for (StructureStart var9 : this.structureMap.values()) {
            if (!var9.isSizeableStructure() || !var9.getBoundingBox().intersectsWith(var5, var6, var5 + 15, var6 + 15)) continue;
            var9.generateStructure(par1World, par2Random, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
            var7 = true;
            this.func_143026_a(var9.func_143019_e(), var9.func_143018_f(), var9);
        }
        return var7;
    }

    public boolean hasStructureAt(int par1, int par2, int par3) {
        this.func_143027_a(this.worldObj);
        if (this.func_143028_c(par1, par2, par3) != null) {
            return true;
        }
        return false;
    }

    protected StructureStart func_143028_c(int par1, int par2, int par3) {
        for (StructureStart var5 : this.structureMap.values()) {
            if (!var5.isSizeableStructure() || !var5.getBoundingBox().intersectsWith(par1, par3, par1, par3)) continue;
            for (StructureComponent var7 : var5.getComponents()) {
                if (!var7.getBoundingBox().isVecInside(par1, par2, par3)) continue;
                return var5;
            }
        }
        return null;
    }

    public boolean func_142038_b(int par1, int par2, int par3) {
        StructureStart var5;
        this.func_143027_a(this.worldObj);
        Iterator<V> var4 = this.structureMap.values().iterator();
        do {
            if (var4.hasNext()) continue;
            return false;
        } while (!(var5 = (StructureStart)var4.next()).isSizeableStructure());
        return var5.getBoundingBox().intersectsWith(par1, par3, par1, par3);
    }

    public ChunkPosition func_151545_a(World p_151545_1_, int p_151545_2_, int p_151545_3_, int p_151545_4_) {
        int var21;
        int var20;
        double var23;
        int var22;
        this.worldObj = p_151545_1_;
        this.func_143027_a(p_151545_1_);
        this.rand.setSeed(p_151545_1_.getSeed());
        long var5 = this.rand.nextLong();
        long var7 = this.rand.nextLong();
        long var9 = (long)(p_151545_2_ >> 4) * var5;
        long var11 = (long)(p_151545_4_ >> 4) * var7;
        this.rand.setSeed(var9 ^ var11 ^ p_151545_1_.getSeed());
        this.func_151538_a(p_151545_1_, p_151545_2_ >> 4, p_151545_4_ >> 4, 0, 0, null);
        double var13 = Double.MAX_VALUE;
        ChunkPosition var15 = null;
        for (StructureStart var17 : this.structureMap.values()) {
            if (!var17.isSizeableStructure()) continue;
            StructureComponent var18 = (StructureComponent)var17.getComponents().get(0);
            ChunkPosition var19 = var18.func_151553_a();
            var20 = var19.field_151329_a - p_151545_2_;
            var21 = var19.field_151327_b - p_151545_3_;
            var22 = var19.field_151328_c - p_151545_4_;
            var23 = var20 * var20 + var21 * var21 + var22 * var22;
            if (var23 >= var13) continue;
            var13 = var23;
            var15 = var19;
        }
        if (var15 != null) {
            return var15;
        }
        List var25 = this.getCoordList();
        if (var25 != null) {
            ChunkPosition var26 = null;
            for (ChunkPosition var19 : var25) {
                var20 = var19.field_151329_a - p_151545_2_;
                var21 = var19.field_151327_b - p_151545_3_;
                var22 = var19.field_151328_c - p_151545_4_;
                var23 = var20 * var20 + var21 * var21 + var22 * var22;
                if (var23 >= var13) continue;
                var13 = var23;
                var26 = var19;
            }
            return var26;
        }
        return null;
    }

    protected List getCoordList() {
        return null;
    }

    private void func_143027_a(World par1World) {
        if (this.field_143029_e == null) {
            this.field_143029_e = (MapGenStructureData)par1World.loadItemData(MapGenStructureData.class, this.func_143025_a());
            if (this.field_143029_e == null) {
                this.field_143029_e = new MapGenStructureData(this.func_143025_a());
                par1World.setItemData(this.func_143025_a(), this.field_143029_e);
            } else {
                NBTTagCompound var2 = this.field_143029_e.func_143041_a();
                for (String var4 : var2.func_150296_c()) {
                    NBTTagCompound var6;
                    NBTBase var5 = var2.getTag(var4);
                    if (var5.getId() != 10 || !(var6 = (NBTTagCompound)var5).hasKey("ChunkX") || !var6.hasKey("ChunkZ")) continue;
                    int var7 = var6.getInteger("ChunkX");
                    int var8 = var6.getInteger("ChunkZ");
                    StructureStart var9 = MapGenStructureIO.func_143035_a(var6, par1World);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(var7, var8), var9);
                }
            }
        }
    }

    private void func_143026_a(int par1, int par2, StructureStart par3StructureStart) {
        this.field_143029_e.func_143043_a(par3StructureStart.func_143021_a(par1, par2), par1, par2);
        this.field_143029_e.markDirty();
    }

    protected abstract boolean canSpawnStructureAtCoords(int var1, int var2);

    protected abstract StructureStart getStructureStart(int var1, int var2);

}

