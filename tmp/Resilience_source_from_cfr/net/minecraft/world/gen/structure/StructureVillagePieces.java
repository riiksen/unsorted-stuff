/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class StructureVillagePieces {
    private static final String __OBFID = "CL_00000516";

    public static void func_143016_a() {
        MapGenStructureIO.func_143031_a(House1.class, "ViBH");
        MapGenStructureIO.func_143031_a(Field1.class, "ViDF");
        MapGenStructureIO.func_143031_a(Field2.class, "ViF");
        MapGenStructureIO.func_143031_a(Torch.class, "ViL");
        MapGenStructureIO.func_143031_a(Hall.class, "ViPH");
        MapGenStructureIO.func_143031_a(House4Garden.class, "ViSH");
        MapGenStructureIO.func_143031_a(WoodHut.class, "ViSmH");
        MapGenStructureIO.func_143031_a(Church.class, "ViST");
        MapGenStructureIO.func_143031_a(House2.class, "ViS");
        MapGenStructureIO.func_143031_a(Start.class, "ViStart");
        MapGenStructureIO.func_143031_a(Path.class, "ViSR");
        MapGenStructureIO.func_143031_a(House3.class, "ViTRH");
        MapGenStructureIO.func_143031_a(Well.class, "ViW");
    }

    public static List getStructureVillageWeightedPieceList(Random par0Random, int par1) {
        ArrayList<PieceWeight> var2 = new ArrayList<PieceWeight>();
        var2.add(new PieceWeight(House4Garden.class, 4, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        var2.add(new PieceWeight(Church.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 1 + par1)));
        var2.add(new PieceWeight(House1.class, 20, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        var2.add(new PieceWeight(WoodHut.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 5 + par1 * 3)));
        var2.add(new PieceWeight(Hall.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 2 + par1)));
        var2.add(new PieceWeight(Field1.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 1 + par1, 4 + par1)));
        var2.add(new PieceWeight(Field2.class, 3, MathHelper.getRandomIntegerInRange(par0Random, 2 + par1, 4 + par1 * 2)));
        var2.add(new PieceWeight(House2.class, 15, MathHelper.getRandomIntegerInRange(par0Random, 0, 1 + par1)));
        var2.add(new PieceWeight(House3.class, 8, MathHelper.getRandomIntegerInRange(par0Random, 0 + par1, 3 + par1 * 2)));
        Iterator var3 = var2.iterator();
        while (var3.hasNext()) {
            if (((PieceWeight)var3.next()).villagePiecesLimit != 0) continue;
            var3.remove();
        }
        return var2;
    }

    private static int func_75079_a(List par0List) {
        boolean var1 = false;
        int var2 = 0;
        for (PieceWeight var4 : par0List) {
            if (var4.villagePiecesLimit > 0 && var4.villagePiecesSpawned < var4.villagePiecesLimit) {
                var1 = true;
            }
            var2 += var4.villagePieceWeight;
        }
        return var1 ? var2 : -1;
    }

    private static Village func_75083_a(Start par0ComponentVillageStartPiece, PieceWeight par1StructureVillagePieceWeight, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8) {
        Class var9 = par1StructureVillagePieceWeight.villagePieceClass;
        Village var10 = null;
        if (var9 == House4Garden.class) {
            var10 = House4Garden.func_74912_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == Church.class) {
            var10 = Church.func_74919_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == House1.class) {
            var10 = House1.func_74898_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == WoodHut.class) {
            var10 = WoodHut.func_74908_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == Hall.class) {
            var10 = Hall.func_74906_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == Field1.class) {
            var10 = Field1.func_74900_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == Field2.class) {
            var10 = Field2.func_74902_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == House2.class) {
            var10 = House2.func_74915_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        } else if (var9 == House3.class) {
            var10 = House3.func_74921_a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        return var10;
    }

    private static Village getNextVillageComponent(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        int var8 = StructureVillagePieces.func_75079_a(par0ComponentVillageStartPiece.structureVillageWeightedPieceList);
        if (var8 <= 0) {
            return null;
        }
        int var9 = 0;
        block0 : while (var9 < 5) {
            ++var9;
            int var10 = par2Random.nextInt(var8);
            for (PieceWeight var12 : par0ComponentVillageStartPiece.structureVillageWeightedPieceList) {
                if ((var10 -= var12.villagePieceWeight) >= 0) continue;
                if (!var12.canSpawnMoreVillagePiecesOfType(par7) || var12 == par0ComponentVillageStartPiece.structVillagePieceWeight && par0ComponentVillageStartPiece.structureVillageWeightedPieceList.size() > 1) continue block0;
                Village var13 = StructureVillagePieces.func_75083_a(par0ComponentVillageStartPiece, var12, par1List, par2Random, par3, par4, par5, par6, par7);
                if (var13 == null) continue;
                ++var12.villagePiecesSpawned;
                par0ComponentVillageStartPiece.structVillagePieceWeight = var12;
                if (!var12.canSpawnMoreVillagePieces()) {
                    par0ComponentVillageStartPiece.structureVillageWeightedPieceList.remove(var12);
                }
                return var13;
            }
        }
        StructureBoundingBox var14 = Torch.func_74904_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);
        if (var14 != null) {
            return new Torch(par0ComponentVillageStartPiece, par7, par2Random, var14, par6);
        }
        return null;
    }

    private static StructureComponent getNextVillageStructureComponent(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        if (par7 > 50) {
            return null;
        }
        if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
            Village var8 = StructureVillagePieces.getNextVillageComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);
            if (var8 != null) {
                int var13;
                int var9 = (var8.boundingBox.minX + var8.boundingBox.maxX) / 2;
                int var10 = (var8.boundingBox.minZ + var8.boundingBox.maxZ) / 2;
                int var11 = var8.boundingBox.maxX - var8.boundingBox.minX;
                int var12 = var8.boundingBox.maxZ - var8.boundingBox.minZ;
                int n = var13 = var11 > var12 ? var11 : var12;
                if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(var9, var10, var13 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    par1List.add(var8);
                    par0ComponentVillageStartPiece.field_74932_i.add(var8);
                    return var8;
                }
            }
            return null;
        }
        return null;
    }

    private static StructureComponent getNextComponentVillagePath(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        if (par7 > 3 + par0ComponentVillageStartPiece.terrainType) {
            return null;
        }
        if (Math.abs(par3 - par0ComponentVillageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
            StructureBoundingBox var8 = Path.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);
            if (var8 != null && var8.minY > 10) {
                int var14;
                Path var9 = new Path(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
                int var10 = (var9.boundingBox.minX + var9.boundingBox.maxX) / 2;
                int var11 = (var9.boundingBox.minZ + var9.boundingBox.maxZ) / 2;
                int var12 = var9.boundingBox.maxX - var9.boundingBox.minX;
                int var13 = var9.boundingBox.maxZ - var9.boundingBox.minZ;
                int n = var14 = var12 > var13 ? var12 : var13;
                if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(var10, var11, var14 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    par1List.add(var9);
                    par0ComponentVillageStartPiece.field_74930_j.add(var9);
                    return var9;
                }
            }
            return null;
        }
        return null;
    }

    public static class Church
    extends Village {
        private static final String __OBFID = "CL_00000525";

        public Church() {
        }

        public Church(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
        }

        public static Church func_74919_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 5, 12, 9, par6);
            return Church.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new Church(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 7, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 9, 3, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 3, 0, 8, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 0, 3, 10, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 10, 3, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 10, 3, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 0, 4, 7, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 4, 4, 4, 7, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 8, 3, 4, 8, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 4, 3, 10, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 5, 3, 5, 7, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 9, 0, 4, 9, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 11, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 11, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 2, 11, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 2, 11, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 1, 1, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 1, 1, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 2, 1, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 3, 1, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 3, 1, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 1, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 1, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 3, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 1), 1, 2, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 0), 3, 2, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 3, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 6, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 7, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 6, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 7, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 6, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 7, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 6, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 7, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 3, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 3, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 2, 4, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 1, 4, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 3, 4, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 2, 4, 5, par3StructureBoundingBox);
            int var4 = this.func_151555_a(Blocks.ladder, 4);
            int var5 = 1;
            while (var5 <= 9) {
                this.func_151550_a(par1World, Blocks.ladder, var4, 3, var5, 3, par3StructureBoundingBox);
                ++var5;
            }
            this.func_151550_a(par1World, Blocks.air, 0, 2, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 2, 0, par3StructureBoundingBox);
            this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
            }
            var5 = 0;
            while (var5 < 9) {
                int var6 = 0;
                while (var6 < 5) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var6, 12, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var6, -1, var5, par3StructureBoundingBox);
                    ++var6;
                }
                ++var5;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
            return true;
        }

        @Override
        protected int getVillagerType(int par1) {
            return 2;
        }
    }

    public static class Field1
    extends Village {
        private Block cropTypeA;
        private Block cropTypeB;
        private Block cropTypeC;
        private Block cropTypeD;
        private static final String __OBFID = "CL_00000518";

        public Field1() {
        }

        public Field1(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
            this.cropTypeA = this.func_151559_a(par3Random);
            this.cropTypeB = this.func_151559_a(par3Random);
            this.cropTypeC = this.func_151559_a(par3Random);
            this.cropTypeD = this.func_151559_a(par3Random);
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            par1NBTTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
            par1NBTTagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
            par1NBTTagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.cropTypeA = Block.getBlockById(par1NBTTagCompound.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(par1NBTTagCompound.getInteger("CB"));
            this.cropTypeC = Block.getBlockById(par1NBTTagCompound.getInteger("CC"));
            this.cropTypeD = Block.getBlockById(par1NBTTagCompound.getInteger("CD"));
        }

        private Block func_151559_a(Random p_151559_1_) {
            switch (p_151559_1_.nextInt(5)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
            }
            return Blocks.wheat;
        }

        public static Field1 func_74900_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 13, 4, 9, par6);
            return Field1.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new Field1(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 12, 4, 8, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 1, 8, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 10, 0, 1, 11, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 12, 0, 0, 12, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 11, 0, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 8, 11, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 1, 9, 0, 7, Blocks.water, Blocks.water, false);
            int var4 = 1;
            while (var4 <= 7) {
                this.func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 1, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 2, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 4, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 5, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeC, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 7, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeC, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 8, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeD, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 10, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeD, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 11, 1, var4, par3StructureBoundingBox);
                ++var4;
            }
            var4 = 0;
            while (var4 < 9) {
                int var5 = 0;
                while (var5 < 13) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var5, 4, var4, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.dirt, 0, var5, -1, var4, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Field2
    extends Village {
        private Block cropTypeA;
        private Block cropTypeB;
        private static final String __OBFID = "CL_00000519";

        public Field2() {
        }

        public Field2(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
            this.cropTypeA = this.func_151560_a(par3Random);
            this.cropTypeB = this.func_151560_a(par3Random);
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            par1NBTTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.cropTypeA = Block.getBlockById(par1NBTTagCompound.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(par1NBTTagCompound.getInteger("CB"));
        }

        private Block func_151560_a(Random p_151560_1_) {
            switch (p_151560_1_.nextInt(5)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
            }
            return Blocks.wheat;
        }

        public static Field2 func_74902_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 7, 4, 9, par6);
            return Field2.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new Field2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 6, 4, 8, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 0, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 8, 5, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
            int var4 = 1;
            while (var4 <= 7) {
                this.func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 1, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 2, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 4, 1, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 5, 1, var4, par3StructureBoundingBox);
                ++var4;
            }
            var4 = 0;
            while (var4 < 9) {
                int var5 = 0;
                while (var5 < 7) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var5, 4, var4, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.dirt, 0, var5, -1, var4, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Hall
    extends Village {
        private static final String __OBFID = "CL_00000522";

        public Hall() {
        }

        public Hall(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
        }

        public static Hall func_74906_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 7, 11, par6);
            return Hall.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new Hall(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var7;
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 0, 6, 8, 0, 10, Blocks.dirt, Blocks.dirt, false);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 6, 0, 6, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 6, 2, 1, 10, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 1, 6, 8, 1, 10, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 10, 7, 1, 10, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 1, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 5, 7, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 3, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 4, 8, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(par1World, Blocks.planks, 0, 0, 4, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 0, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 8, 4, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 8, 4, 3, par3StructureBoundingBox);
            int var4 = this.func_151555_a(Blocks.oak_stairs, 3);
            int var5 = this.func_151555_a(Blocks.oak_stairs, 2);
            int var6 = -1;
            while (var6 <= 2) {
                var7 = 0;
                while (var7 <= 8) {
                    this.func_151550_a(par1World, Blocks.oak_stairs, var4, var7, 4 + var6, var6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.oak_stairs, var5, var7, 4 + var6, 5 - var6, par3StructureBoundingBox);
                    ++var7;
                }
                ++var6;
            }
            this.func_151550_a(par1World, Blocks.log, 0, 0, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 0, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 8, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 8, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 3, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 6, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 2, 1, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 2, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 1, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 3), 2, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 1), 1, 1, 3, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
            this.func_151550_a(par1World, Blocks.double_stone_slab, 0, 6, 1, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.double_stone_slab, 0, 6, 1, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 2, 3, 1, par3StructureBoundingBox);
            this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
            }
            this.func_151550_a(par1World, Blocks.air, 0, 6, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 6, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 6, 3, 4, par3StructureBoundingBox);
            this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 6, 1, 5, this.func_151555_a(Blocks.wooden_door, 1));
            var6 = 0;
            while (var6 < 5) {
                var7 = 0;
                while (var7 < 9) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var7, 7, var6, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var7, -1, var6, par3StructureBoundingBox);
                    ++var7;
                }
                ++var6;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
            return true;
        }

        @Override
        protected int getVillagerType(int par1) {
            return par1 == 0 ? 4 : 0;
        }
    }

    public static class House1
    extends Village {
        private static final String __OBFID = "CL_00000517";

        public House1() {
        }

        public House1(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
        }

        public static House1 func_74898_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 9, 6, par6);
            return House1.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new House1(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var7;
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 0, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 8, 5, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 1, 8, 6, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 7, 2, 8, 7, 3, Blocks.cobblestone, Blocks.cobblestone, false);
            int var4 = this.func_151555_a(Blocks.oak_stairs, 3);
            int var5 = this.func_151555_a(Blocks.oak_stairs, 2);
            int var6 = -1;
            while (var6 <= 2) {
                var7 = 0;
                while (var7 <= 8) {
                    this.func_151550_a(par1World, Blocks.oak_stairs, var4, var7, 6 + var6, var6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.oak_stairs, var5, var7, 6 + var6, 5 - var6, par3StructureBoundingBox);
                    ++var7;
                }
                ++var6;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 5, 8, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 1, 0, 8, 1, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 0, 7, 1, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 4, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 4, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 2, 5, 8, 4, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 2, 0, 8, 4, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 4, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 2, 1, 8, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 4, 0, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 6, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 5, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 6, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 3, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 3, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 3, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 3, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 6, 2, 5, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 1, 7, 4, 1, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 4, 7, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 7, 3, 4, Blocks.bookshelf, Blocks.bookshelf, false);
            this.func_151550_a(par1World, Blocks.planks, 0, 7, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 0), 7, 1, 3, par3StructureBoundingBox);
            var6 = this.func_151555_a(Blocks.oak_stairs, 3);
            this.func_151550_a(par1World, Blocks.oak_stairs, var6, 6, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, var6, 5, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, var6, 4, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, var6, 3, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 6, 1, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 6, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 4, 1, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 4, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.crafting_table, 0, 7, 1, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 1, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 1, 2, 0, par3StructureBoundingBox);
            this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(par1World, 1, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, 1, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 1, 0, -1, par3StructureBoundingBox);
            }
            var7 = 0;
            while (var7 < 6) {
                int var8 = 0;
                while (var8 < 9) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var8, 9, var7, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var8, -1, var7, par3StructureBoundingBox);
                    ++var8;
                }
                ++var7;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
            return true;
        }

        @Override
        protected int getVillagerType(int par1) {
            return 1;
        }
    }

    public static class House2
    extends Village {
        private static final WeightedRandomChestContent[] villageBlacksmithChestContents = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)};
        private boolean hasMadeChest;
        private static final String __OBFID = "CL_00000526";

        public House2() {
        }

        public House2(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
        }

        public static House2 func_74915_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 10, 6, 7, par6);
            return House2.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new House2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Chest", this.hasMadeChest);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.hasMadeChest = par1NBTTagCompound.getBoolean("Chest");
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var5;
            int var4;
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 9, 4, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 0, 6, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 0, 9, 4, 6, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 9, 5, 6, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 8, 5, 5, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 4, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 4, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 6, 0, 4, 6, Blocks.log, Blocks.log, false);
            this.func_151550_a(par1World, Blocks.planks, 0, 3, 3, 1, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 3, 2, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 3, 5, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 6, 5, 3, 6, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 0, 5, 3, 0, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 1, 0, 9, 3, 0, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 4, 9, 4, 6, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151550_a(par1World, Blocks.flowing_lava, 0, 7, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.flowing_lava, 0, 8, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_bars, 0, 9, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_bars, 0, 9, 2, 4, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 4, 8, 2, 5, Blocks.air, Blocks.air, false);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 6, 1, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.furnace, 0, 6, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.furnace, 0, 6, 3, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.double_stone_slab, 0, 8, 1, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 2, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, 2, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 1, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 3), 2, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 1), 1, 1, 4, par3StructureBoundingBox);
            if (!this.hasMadeChest) {
                int var6;
                var4 = this.getYWithOffset(1);
                var5 = this.getXWithOffset(5, 5);
                if (par3StructureBoundingBox.isVecInside(var5, var4, var6 = this.getZWithOffset(5, 5))) {
                    this.hasMadeChest = true;
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 5, 1, 5, villageBlacksmithChestContents, 3 + par2Random.nextInt(6));
                }
            }
            var4 = 6;
            while (var4 <= 8) {
                if (this.func_151548_a(par1World, var4, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, var4, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                    this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), var4, 0, -1, par3StructureBoundingBox);
                }
                ++var4;
            }
            var4 = 0;
            while (var4 < 7) {
                var5 = 0;
                while (var5 < 10) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var5, -1, var4, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 7, 1, 1, 1);
            return true;
        }

        @Override
        protected int getVillagerType(int par1) {
            return 3;
        }
    }

    public static class House3
    extends Village {
        private static final String __OBFID = "CL_00000530";

        public House3() {
        }

        public House3(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
        }

        public static House3 func_74921_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 7, 12, par6);
            return House3.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new House3(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var9;
            int var8;
            int var7;
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 0, 5, 8, 0, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 2, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 5, 2, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 0, 6, 2, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 0, 10, 7, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 2, 3, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 4, 3, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(par1World, Blocks.planks, 0, 0, 4, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 0, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 8, 4, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 8, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 8, 4, 4, par3StructureBoundingBox);
            int var4 = this.func_151555_a(Blocks.oak_stairs, 3);
            int var5 = this.func_151555_a(Blocks.oak_stairs, 2);
            int var6 = -1;
            while (var6 <= 2) {
                var7 = 0;
                while (var7 <= 8) {
                    this.func_151550_a(par1World, Blocks.oak_stairs, var4, var7, 4 + var6, var6, par3StructureBoundingBox);
                    if (!(var6 <= -1 && var7 > 1 || var6 <= 0 && var7 > 3 || var6 <= 1 && var7 > 4 && var7 < 6)) {
                        this.func_151550_a(par1World, Blocks.oak_stairs, var5, var7, 4 + var6, 5 - var6, par3StructureBoundingBox);
                    }
                    ++var7;
                }
                ++var6;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 4, 5, 3, 4, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 4, 2, 7, 4, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 4, 4, 5, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 4, 6, 5, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 6, 3, 5, 6, 10, Blocks.planks, Blocks.planks, false);
            var6 = this.func_151555_a(Blocks.oak_stairs, 0);
            var7 = 4;
            while (var7 >= 1) {
                this.func_151550_a(par1World, Blocks.planks, 0, var7, 2 + var7, 7 - var7, par3StructureBoundingBox);
                var8 = 8 - var7;
                while (var8 <= 10) {
                    this.func_151550_a(par1World, Blocks.oak_stairs, var6, var7, 2 + var7, var8, par3StructureBoundingBox);
                    ++var8;
                }
                --var7;
            }
            var7 = this.func_151555_a(Blocks.oak_stairs, 1);
            this.func_151550_a(par1World, Blocks.planks, 0, 6, 6, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 7, 5, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.oak_stairs, var7, 6, 6, 4, par3StructureBoundingBox);
            var8 = 6;
            while (var8 <= 8) {
                var9 = 5;
                while (var9 <= 10) {
                    this.func_151550_a(par1World, Blocks.oak_stairs, var7, var8, 12 - var8, var9, par3StructureBoundingBox);
                    ++var9;
                }
                ++var8;
            }
            this.func_151550_a(par1World, Blocks.log, 0, 0, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 0, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 4, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 5, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 6, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 8, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 8, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 8, 2, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 8, 2, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 8, 2, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 8, 2, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 2, 2, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 7, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 2, 2, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 4, 4, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 5, 4, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 6, 4, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 5, 5, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 2, 3, 1, par3StructureBoundingBox);
            this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, -1, 3, 2, -1, Blocks.air, Blocks.air, false);
            if (this.func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
            }
            var8 = 0;
            while (var8 < 5) {
                var9 = 0;
                while (var9 < 9) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var9, 7, var8, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var9, -1, var8, par3StructureBoundingBox);
                    ++var9;
                }
                ++var8;
            }
            var8 = 5;
            while (var8 < 11) {
                var9 = 2;
                while (var9 < 9) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var9, 7, var8, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var9, -1, var8, par3StructureBoundingBox);
                    ++var9;
                }
                ++var8;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
            return true;
        }
    }

    public static class House4Garden
    extends Village {
        private boolean isRoofAccessible;
        private static final String __OBFID = "CL_00000523";

        public House4Garden() {
        }

        public House4Garden(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
            this.isRoofAccessible = par3Random.nextBoolean();
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Terrace", this.isRoofAccessible);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.isRoofAccessible = par1NBTTagCompound.getBoolean("Terrace");
        }

        public static House4Garden func_74912_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 5, 6, 5, par6);
            return StructureComponent.findIntersecting(par1List, var8) != null ? null : new House4Garden(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var4;
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 1, 3, 4, 3, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 0, 3, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 1, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 3, 4, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 3, 3, 4, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 2, 2, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 4, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 1, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 1, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 1, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 2, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 3, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 3, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.planks, 0, 3, 1, 0, par3StructureBoundingBox);
            if (this.func_151548_a(par1World, 2, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, 2, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, par3StructureBoundingBox);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 3, Blocks.air, Blocks.air, false);
            if (this.isRoofAccessible) {
                this.func_151550_a(par1World, Blocks.fence, 0, 0, 5, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 1, 5, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 2, 5, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 3, 5, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 4, 5, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 0, 5, 4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 1, 5, 4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 2, 5, 4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 3, 5, 4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 4, 5, 4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 4, 5, 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 4, 5, 2, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 4, 5, 3, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 0, 5, 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 0, 5, 2, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 0, 5, 3, par3StructureBoundingBox);
            }
            if (this.isRoofAccessible) {
                var4 = this.func_151555_a(Blocks.ladder, 3);
                this.func_151550_a(par1World, Blocks.ladder, var4, 3, 1, 3, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var4, 3, 2, 3, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var4, 3, 3, 3, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var4, 3, 4, 3, par3StructureBoundingBox);
            }
            this.func_151550_a(par1World, Blocks.torch, 0, 2, 3, 1, par3StructureBoundingBox);
            var4 = 0;
            while (var4 < 5) {
                int var5 = 0;
                while (var5 < 5) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var5, -1, var4, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
            return true;
        }
    }

    public static class Path
    extends Road {
        private int averageGroundLevel;
        private static final String __OBFID = "CL_00000528";

        public Path() {
        }

        public Path(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
            this.averageGroundLevel = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("Length", this.averageGroundLevel);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.averageGroundLevel = par1NBTTagCompound.getInteger("Length");
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            StructureComponent var6;
            boolean var4 = false;
            int var5 = par3Random.nextInt(5);
            while (var5 < this.averageGroundLevel - 8) {
                var6 = this.getNextComponentNN((Start)par1StructureComponent, par2List, par3Random, 0, var5);
                if (var6 != null) {
                    var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                    var4 = true;
                }
                var5 += 2 + par3Random.nextInt(5);
            }
            var5 = par3Random.nextInt(5);
            while (var5 < this.averageGroundLevel - 8) {
                var6 = this.getNextComponentPP((Start)par1StructureComponent, par2List, par3Random, 0, var5);
                if (var6 != null) {
                    var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                    var4 = true;
                }
                var5 += 2 + par3Random.nextInt(5);
            }
            if (var4 && par3Random.nextInt(3) > 0) {
                switch (this.coordBaseMode) {
                    case 0: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                        break;
                    }
                    case 1: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                        break;
                    }
                    case 2: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                        break;
                    }
                    case 3: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    }
                }
            }
            if (var4 && par3Random.nextInt(3) > 0) {
                switch (this.coordBaseMode) {
                    case 0: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                        break;
                    }
                    case 1: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                        break;
                    }
                    case 2: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                        break;
                    }
                    case 3: {
                        StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    }
                }
            }
        }

        public static StructureBoundingBox func_74933_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6) {
            int var7 = 7 * MathHelper.getRandomIntegerInRange(par2Random, 3, 5);
            while (var7 >= 7) {
                StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 3, var7, par6);
                if (StructureComponent.findIntersecting(par1List, var8) == null) {
                    return var8;
                }
                var7 -= 7;
            }
            return null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            Block var4 = this.func_151558_b(Blocks.gravel, 0);
            int var5 = this.boundingBox.minX;
            while (var5 <= this.boundingBox.maxX) {
                int var6 = this.boundingBox.minZ;
                while (var6 <= this.boundingBox.maxZ) {
                    if (par3StructureBoundingBox.isVecInside(var5, 64, var6)) {
                        int var7 = par1World.getTopSolidOrLiquidBlock(var5, var6) - 1;
                        par1World.setBlock(var5, var7, var6, var4, 0, 2);
                    }
                    ++var6;
                }
                ++var5;
            }
            return true;
        }
    }

    public static class PieceWeight {
        public Class villagePieceClass;
        public final int villagePieceWeight;
        public int villagePiecesSpawned;
        public int villagePiecesLimit;
        private static final String __OBFID = "CL_00000521";

        public PieceWeight(Class par1Class, int par2, int par3) {
            this.villagePieceClass = par1Class;
            this.villagePieceWeight = par2;
            this.villagePiecesLimit = par3;
        }

        public boolean canSpawnMoreVillagePiecesOfType(int par1) {
            if (this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit) {
                return false;
            }
            return true;
        }

        public boolean canSpawnMoreVillagePieces() {
            if (this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit) {
                return false;
            }
            return true;
        }
    }

    public static abstract class Road
    extends Village {
        private static final String __OBFID = "CL_00000532";

        public Road() {
        }

        protected Road(Start par1ComponentVillageStartPiece, int par2) {
            super(par1ComponentVillageStartPiece, par2);
        }
    }

    public static class Start
    extends Well {
        public WorldChunkManager worldChunkMngr;
        public boolean inDesert;
        public int terrainType;
        public PieceWeight structVillagePieceWeight;
        public List structureVillageWeightedPieceList;
        public List field_74932_i = new ArrayList();
        public List field_74930_j = new ArrayList();
        private static final String __OBFID = "CL_00000527";

        public Start() {
        }

        public Start(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, List par6List, int par7) {
            super(null, 0, par3Random, par4, par5);
            this.worldChunkMngr = par1WorldChunkManager;
            this.structureVillageWeightedPieceList = par6List;
            this.terrainType = par7;
            BiomeGenBase var8 = par1WorldChunkManager.getBiomeGenAt(par4, par5);
            this.inDesert = var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills;
        }

        public WorldChunkManager getWorldChunkManager() {
            return this.worldChunkMngr;
        }
    }

    public static class Torch
    extends Village {
        private static final String __OBFID = "CL_00000520";

        public Torch() {
        }

        public Torch(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
        }

        public static StructureBoundingBox func_74904_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 4, 2, par6);
            return StructureComponent.findIntersecting(par1List, var7) != null ? null : var7;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 3, 1, Blocks.air, Blocks.air, false);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 0, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 2, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.wool, 15, 1, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 0, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 1, 3, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 2, 3, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.torch, 0, 1, 3, -1, par3StructureBoundingBox);
            return true;
        }
    }

    static abstract class Village
    extends StructureComponent {
        protected int field_143015_k = -1;
        private int villagersSpawned;
        private boolean field_143014_b;
        private static final String __OBFID = "CL_00000531";

        public Village() {
        }

        protected Village(Start par1ComponentVillageStartPiece, int par2) {
            super(par2);
            if (par1ComponentVillageStartPiece != null) {
                this.field_143014_b = par1ComponentVillageStartPiece.inDesert;
            }
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            par1NBTTagCompound.setInteger("HPos", this.field_143015_k);
            par1NBTTagCompound.setInteger("VCount", this.villagersSpawned);
            par1NBTTagCompound.setBoolean("Desert", this.field_143014_b);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            this.field_143015_k = par1NBTTagCompound.getInteger("HPos");
            this.villagersSpawned = par1NBTTagCompound.getInteger("VCount");
            this.field_143014_b = par1NBTTagCompound.getBoolean("Desert");
        }

        protected StructureComponent getNextComponentNN(Start par1ComponentVillageStartPiece, List par2List, Random par3Random, int par4, int par5) {
            switch (this.coordBaseMode) {
                case 0: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
                }
                case 1: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
                case 2: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
                }
                case 3: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentPP(Start par1ComponentVillageStartPiece, List par2List, Random par3Random, int par4, int par5) {
            switch (this.coordBaseMode) {
                case 0: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
                }
                case 1: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
                case 2: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
                }
                case 3: {
                    return StructureVillagePieces.getNextVillageStructureComponent(par1ComponentVillageStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
            }
            return null;
        }

        protected int getAverageGroundLevel(World par1World, StructureBoundingBox par2StructureBoundingBox) {
            int var3 = 0;
            int var4 = 0;
            int var5 = this.boundingBox.minZ;
            while (var5 <= this.boundingBox.maxZ) {
                int var6 = this.boundingBox.minX;
                while (var6 <= this.boundingBox.maxX) {
                    if (par2StructureBoundingBox.isVecInside(var6, 64, var5)) {
                        var3 += Math.max(par1World.getTopSolidOrLiquidBlock(var6, var5), par1World.provider.getAverageGroundLevel());
                        ++var4;
                    }
                    ++var6;
                }
                ++var5;
            }
            if (var4 == 0) {
                return -1;
            }
            return var3 / var4;
        }

        protected static boolean canVillageGoDeeper(StructureBoundingBox par0StructureBoundingBox) {
            if (par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10) {
                return true;
            }
            return false;
        }

        protected void spawnVillagers(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6) {
            if (this.villagersSpawned < par6) {
                int var7 = this.villagersSpawned;
                while (var7 < par6) {
                    int var9;
                    int var10;
                    int var8 = this.getXWithOffset(par3 + var7, par5);
                    if (!par2StructureBoundingBox.isVecInside(var8, var9 = this.getYWithOffset(par4), var10 = this.getZWithOffset(par3 + var7, par5))) break;
                    ++this.villagersSpawned;
                    EntityVillager var11 = new EntityVillager(par1World, this.getVillagerType(var7));
                    var11.setLocationAndAngles((double)var8 + 0.5, var9, (double)var10 + 0.5, 0.0f, 0.0f);
                    par1World.spawnEntityInWorld(var11);
                    ++var7;
                }
            }
        }

        protected int getVillagerType(int par1) {
            return 0;
        }

        protected Block func_151558_b(Block p_151558_1_, int p_151558_2_) {
            if (this.field_143014_b) {
                if (p_151558_1_ == Blocks.log || p_151558_1_ == Blocks.log2) {
                    return Blocks.sandstone;
                }
                if (p_151558_1_ == Blocks.cobblestone) {
                    return Blocks.sandstone;
                }
                if (p_151558_1_ == Blocks.planks) {
                    return Blocks.sandstone;
                }
                if (p_151558_1_ == Blocks.oak_stairs) {
                    return Blocks.sandstone_stairs;
                }
                if (p_151558_1_ == Blocks.stone_stairs) {
                    return Blocks.sandstone_stairs;
                }
                if (p_151558_1_ == Blocks.gravel) {
                    return Blocks.sandstone;
                }
            }
            return p_151558_1_;
        }

        protected int func_151557_c(Block p_151557_1_, int p_151557_2_) {
            if (this.field_143014_b) {
                if (p_151557_1_ == Blocks.log || p_151557_1_ == Blocks.log2) {
                    return 0;
                }
                if (p_151557_1_ == Blocks.cobblestone) {
                    return 0;
                }
                if (p_151557_1_ == Blocks.planks) {
                    return 2;
                }
            }
            return p_151557_2_;
        }

        @Override
        protected void func_151550_a(World p_151550_1_, Block p_151550_2_, int p_151550_3_, int p_151550_4_, int p_151550_5_, int p_151550_6_, StructureBoundingBox p_151550_7_) {
            Block var8 = this.func_151558_b(p_151550_2_, p_151550_3_);
            int var9 = this.func_151557_c(p_151550_2_, p_151550_3_);
            super.func_151550_a(p_151550_1_, var8, var9, p_151550_4_, p_151550_5_, p_151550_6_, p_151550_7_);
        }

        @Override
        protected void func_151549_a(World p_151549_1_, StructureBoundingBox p_151549_2_, int p_151549_3_, int p_151549_4_, int p_151549_5_, int p_151549_6_, int p_151549_7_, int p_151549_8_, Block p_151549_9_, Block p_151549_10_, boolean p_151549_11_) {
            Block var12 = this.func_151558_b(p_151549_9_, 0);
            int var13 = this.func_151557_c(p_151549_9_, 0);
            Block var14 = this.func_151558_b(p_151549_10_, 0);
            int var15 = this.func_151557_c(p_151549_10_, 0);
            super.func_151556_a(p_151549_1_, p_151549_2_, p_151549_3_, p_151549_4_, p_151549_5_, p_151549_6_, p_151549_7_, p_151549_8_, var12, var13, var14, var15, p_151549_11_);
        }

        @Override
        protected void func_151554_b(World p_151554_1_, Block p_151554_2_, int p_151554_3_, int p_151554_4_, int p_151554_5_, int p_151554_6_, StructureBoundingBox p_151554_7_) {
            Block var8 = this.func_151558_b(p_151554_2_, p_151554_3_);
            int var9 = this.func_151557_c(p_151554_2_, p_151554_3_);
            super.func_151554_b(p_151554_1_, var8, var9, p_151554_4_, p_151554_5_, p_151554_6_, p_151554_7_);
        }
    }

    public static class Well
    extends Village {
        private static final String __OBFID = "CL_00000533";

        public Well() {
        }

        public Well(Start par1ComponentVillageStartPiece, int par2, Random par3Random, int par4, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par3Random.nextInt(4);
            switch (this.coordBaseMode) {
                case 0: 
                case 2: {
                    this.boundingBox = new StructureBoundingBox(par4, 64, par5, par4 + 6 - 1, 78, par5 + 6 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(par4, 64, par5, par4 + 6 - 1, 78, par5 + 6 - 1);
                }
            }
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 1, this.getComponentType());
            StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 3, this.getComponentType());
            StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, 2, this.getComponentType());
            StructureVillagePieces.getNextComponentVillagePath((Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 4, 12, 4, Blocks.cobblestone, Blocks.flowing_water, false);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 12, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 3, 12, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 2, 12, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 3, 12, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 13, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 14, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 4, 13, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 4, 14, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 13, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 1, 14, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 4, 13, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.fence, 0, 4, 14, 4, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 15, 1, 4, 15, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            int var4 = 0;
            while (var4 <= 5) {
                int var5 = 0;
                while (var5 <= 5) {
                    if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5) {
                        this.func_151550_a(par1World, Blocks.gravel, 0, var5, 11, var4, par3StructureBoundingBox);
                        this.clearCurrentPositionBlocksUpwards(par1World, var5, 12, var4, par3StructureBoundingBox);
                    }
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class WoodHut
    extends Village {
        private boolean isTallHouse;
        private int tablePosition;
        private static final String __OBFID = "CL_00000524";

        public WoodHut() {
        }

        public WoodHut(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
            super(par1ComponentVillageStartPiece, par2);
            this.coordBaseMode = par5;
            this.boundingBox = par4StructureBoundingBox;
            this.isTallHouse = par3Random.nextBoolean();
            this.tablePosition = par3Random.nextInt(3);
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("T", this.tablePosition);
            par1NBTTagCompound.setBoolean("C", this.isTallHouse);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.tablePosition = par1NBTTagCompound.getInteger("T");
            this.isTallHouse = par1NBTTagCompound.getBoolean("C");
        }

        public static WoodHut func_74908_a(Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 4, 6, 5, par6);
            return WoodHut.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new WoodHut(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 3, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 3, Blocks.dirt, Blocks.dirt, false);
            if (this.isTallHouse) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 1, 2, 4, 3, Blocks.log, Blocks.log, false);
            } else {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 2, 5, 3, Blocks.log, Blocks.log, false);
            }
            this.func_151550_a(par1World, Blocks.log, 0, 1, 4, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 2, 4, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 1, 4, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 2, 4, 4, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 0, 4, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 0, 4, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 0, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 3, 4, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 3, 4, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.log, 0, 3, 4, 3, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 3, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 3, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 4, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 4, 3, 3, 4, Blocks.log, Blocks.log, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 1, 3, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 3, 4, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 0, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.glass_pane, 0, 3, 2, 2, par3StructureBoundingBox);
            if (this.tablePosition > 0) {
                this.func_151550_a(par1World, Blocks.fence, 0, this.tablePosition, 1, 3, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.wooden_pressure_plate, 0, this.tablePosition, 2, 3, par3StructureBoundingBox);
            }
            this.func_151550_a(par1World, Blocks.air, 0, 1, 1, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.air, 0, 1, 2, 0, par3StructureBoundingBox);
            this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(par1World, 1, 0, -1, par3StructureBoundingBox).getMaterial() == Material.air && this.func_151548_a(par1World, 1, -1, -1, par3StructureBoundingBox).getMaterial() != Material.air) {
                this.func_151550_a(par1World, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 1, 0, -1, par3StructureBoundingBox);
            }
            int var4 = 0;
            while (var4 < 5) {
                int var5 = 0;
                while (var5 < 4) {
                    this.clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.cobblestone, 0, var5, -1, var4, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            this.spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
            return true;
        }
    }

}

