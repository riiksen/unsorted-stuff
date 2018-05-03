/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class StructureStrongholdPieces {
    private static final PieceWeight[] pieceWeightArray = new PieceWeight[]{new PieceWeight(Straight.class, 40, 0), new PieceWeight(Prison.class, 5, 5), new PieceWeight(LeftTurn.class, 20, 0), new PieceWeight(RightTurn.class, 20, 0), new PieceWeight(RoomCrossing.class, 10, 6), new PieceWeight(StairsStraight.class, 5, 5), new PieceWeight(Stairs.class, 5, 5), new PieceWeight(Crossing.class, 5, 4), new PieceWeight(ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2){
        private static final String __OBFID = "CL_00000484";

        @Override
        public boolean canSpawnMoreStructuresOfType(int par1) {
            if (super.canSpawnMoreStructuresOfType(par1) && par1 > 4) {
                return true;
            }
            return false;
        }
    }, new PieceWeight(PortalRoom.class, 20, 1){
        private static final String __OBFID = "CL_00000485";

        @Override
        public boolean canSpawnMoreStructuresOfType(int par1) {
            if (super.canSpawnMoreStructuresOfType(par1) && par1 > 5) {
                return true;
            }
            return false;
        }
    }};
    private static List structurePieceList;
    private static Class strongComponentType;
    static int totalWeight;
    private static final Stones strongholdStones;
    private static final String __OBFID = "CL_00000483";

    static {
        strongholdStones = new Stones(null);
    }

    public static void func_143046_a() {
        MapGenStructureIO.func_143031_a(ChestCorridor.class, "SHCC");
        MapGenStructureIO.func_143031_a(Corridor.class, "SHFC");
        MapGenStructureIO.func_143031_a(Crossing.class, "SH5C");
        MapGenStructureIO.func_143031_a(LeftTurn.class, "SHLT");
        MapGenStructureIO.func_143031_a(Library.class, "SHLi");
        MapGenStructureIO.func_143031_a(PortalRoom.class, "SHPR");
        MapGenStructureIO.func_143031_a(Prison.class, "SHPH");
        MapGenStructureIO.func_143031_a(RightTurn.class, "SHRT");
        MapGenStructureIO.func_143031_a(RoomCrossing.class, "SHRC");
        MapGenStructureIO.func_143031_a(Stairs.class, "SHSD");
        MapGenStructureIO.func_143031_a(Stairs2.class, "SHStart");
        MapGenStructureIO.func_143031_a(Straight.class, "SHS");
        MapGenStructureIO.func_143031_a(StairsStraight.class, "SHSSD");
    }

    public static void prepareStructurePieces() {
        structurePieceList = new ArrayList();
        PieceWeight[] var0 = pieceWeightArray;
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            PieceWeight var3 = var0[var2];
            var3.instancesSpawned = 0;
            structurePieceList.add(var3);
            ++var2;
        }
        strongComponentType = null;
    }

    private static boolean canAddStructurePieces() {
        boolean var0 = false;
        totalWeight = 0;
        for (PieceWeight var2 : structurePieceList) {
            if (var2.instancesLimit > 0 && var2.instancesSpawned < var2.instancesLimit) {
                var0 = true;
            }
            totalWeight += var2.pieceWeight;
        }
        return var0;
    }

    private static Stronghold getStrongholdComponentFromWeightedPiece(Class par0Class, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        Stronghold var8 = null;
        if (par0Class == Straight.class) {
            var8 = Straight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == Prison.class) {
            var8 = Prison.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == LeftTurn.class) {
            var8 = LeftTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == RightTurn.class) {
            var8 = RightTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == RoomCrossing.class) {
            var8 = RoomCrossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == StairsStraight.class) {
            var8 = StairsStraight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == Stairs.class) {
            var8 = Stairs.getStrongholdStairsComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == Crossing.class) {
            var8 = Crossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == ChestCorridor.class) {
            var8 = ChestCorridor.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == Library.class) {
            var8 = Library.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (par0Class == PortalRoom.class) {
            var8 = PortalRoom.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        return var8;
    }

    private static Stronghold getNextComponent(Stairs2 par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        if (!StructureStrongholdPieces.canAddStructurePieces()) {
            return null;
        }
        if (strongComponentType != null) {
            Stronghold var8 = StructureStrongholdPieces.getStrongholdComponentFromWeightedPiece(strongComponentType, par1List, par2Random, par3, par4, par5, par6, par7);
            strongComponentType = null;
            if (var8 != null) {
                return var8;
            }
        }
        int var13 = 0;
        block0 : while (var13 < 5) {
            ++var13;
            int var9 = par2Random.nextInt(totalWeight);
            for (PieceWeight var11 : structurePieceList) {
                if ((var9 -= var11.pieceWeight) >= 0) continue;
                if (!var11.canSpawnMoreStructuresOfType(par7) || var11 == par0ComponentStrongholdStairs2.strongholdPieceWeight) continue block0;
                Stronghold var12 = StructureStrongholdPieces.getStrongholdComponentFromWeightedPiece(var11.pieceClass, par1List, par2Random, par3, par4, par5, par6, par7);
                if (var12 == null) continue;
                ++var11.instancesSpawned;
                par0ComponentStrongholdStairs2.strongholdPieceWeight = var11;
                if (!var11.canSpawnMoreStructures()) {
                    structurePieceList.remove(var11);
                }
                return var12;
            }
        }
        StructureBoundingBox var14 = Corridor.func_74992_a(par1List, par2Random, par3, par4, par5, par6);
        if (var14 != null && var14.minY > 1) {
            return new Corridor(par7, par2Random, var14, par6);
        }
        return null;
    }

    private static StructureComponent getNextValidComponent(Stairs2 par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        if (par7 > 50) {
            return null;
        }
        if (Math.abs(par3 - par0ComponentStrongholdStairs2.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentStrongholdStairs2.getBoundingBox().minZ) <= 112) {
            Stronghold var8 = StructureStrongholdPieces.getNextComponent(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7 + 1);
            if (var8 != null) {
                par1List.add(var8);
                par0ComponentStrongholdStairs2.field_75026_c.add(var8);
            }
            return var8;
        }
        return null;
    }

    static /* synthetic */ Class access$0() {
        return strongComponentType;
    }

    static /* synthetic */ void access$1(Class class_) {
        strongComponentType = class_;
    }

    public static class ChestCorridor
    extends Stronghold {
        private static final WeightedRandomChestContent[] strongholdChestContents = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)};
        private boolean hasMadeChest;
        private static final String __OBFID = "CL_00000487";

        public ChestCorridor() {
        }

        public ChestCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
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
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
        }

        public static ChestCorridor findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
            return ChestCorridor.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new ChestCorridor(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, Stronghold.Door.OPENING, 1, 1, 6);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.stonebrick, Blocks.stonebrick, false);
            this.func_151550_a(par1World, Blocks.stone_slab, 5, 3, 1, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 5, 3, 1, 5, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 5, 3, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 5, 3, 2, 4, par3StructureBoundingBox);
            int var4 = 2;
            while (var4 <= 4) {
                this.func_151550_a(par1World, Blocks.stone_slab, 5, 2, 1, var4, par3StructureBoundingBox);
                ++var4;
            }
            if (!this.hasMadeChest) {
                int var6;
                var4 = this.getYWithOffset(2);
                int var5 = this.getXWithOffset(3, 3);
                if (par3StructureBoundingBox.isVecInside(var5, var4, var6 = this.getZWithOffset(3, 3))) {
                    this.hasMadeChest = true;
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, WeightedRandomChestContent.func_92080_a(strongholdChestContents, Items.enchanted_book.func_92114_b(par2Random)), 2 + par2Random.nextInt(2));
                }
            }
            return true;
        }
    }

    public static class Corridor
    extends Stronghold {
        private int field_74993_a;
        private static final String __OBFID = "CL_00000488";

        public Corridor() {
        }

        public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.field_74993_a = par4 != 2 && par4 != 0 ? par3StructureBoundingBox.getXSize() : par3StructureBoundingBox.getZSize();
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("Steps", this.field_74993_a);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.field_74993_a = par1NBTTagCompound.getInteger("Steps");
        }

        public static StructureBoundingBox func_74992_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5) {
            boolean var6 = true;
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
            StructureComponent var8 = StructureComponent.findIntersecting(par0List, var7);
            if (var8 == null) {
                return null;
            }
            if (var8.getBoundingBox().minY == var7.minY) {
                int var9 = 3;
                while (var9 >= 1) {
                    var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9 - 1, par5);
                    if (!var8.getBoundingBox().intersectsWith(var7)) {
                        return StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9, par5);
                    }
                    --var9;
                }
            }
            return null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            int var4 = 0;
            while (var4 < this.field_74993_a) {
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 0, 0, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 0, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 2, 0, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 0, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 4, 0, var4, par3StructureBoundingBox);
                int var5 = 1;
                while (var5 <= 3) {
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 0, var5, var4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.air, 0, 1, var5, var4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.air, 0, 2, var5, var4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.air, 0, 3, var5, var4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 4, var5, var4, par3StructureBoundingBox);
                    ++var5;
                }
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 0, 4, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 4, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 2, 4, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 4, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stonebrick, 0, 4, 4, var4, par3StructureBoundingBox);
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing
    extends Stronghold {
        private boolean field_74996_b;
        private boolean field_74997_c;
        private boolean field_74995_d;
        private boolean field_74999_h;
        private static final String __OBFID = "CL_00000489";

        public Crossing() {
        }

        public Crossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
            this.field_74996_b = par2Random.nextBoolean();
            this.field_74997_c = par2Random.nextBoolean();
            this.field_74995_d = par2Random.nextBoolean();
            this.field_74999_h = par2Random.nextInt(3) > 0;
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("leftLow", this.field_74996_b);
            par1NBTTagCompound.setBoolean("leftHigh", this.field_74997_c);
            par1NBTTagCompound.setBoolean("rightLow", this.field_74995_d);
            par1NBTTagCompound.setBoolean("rightHigh", this.field_74999_h);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.field_74996_b = par1NBTTagCompound.getBoolean("leftLow");
            this.field_74997_c = par1NBTTagCompound.getBoolean("leftHigh");
            this.field_74995_d = par1NBTTagCompound.getBoolean("rightLow");
            this.field_74999_h = par1NBTTagCompound.getBoolean("rightHigh");
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            int var4 = 3;
            int var5 = 5;
            if (this.coordBaseMode == 1 || this.coordBaseMode == 2) {
                var4 = 8 - var4;
                var5 = 8 - var5;
            }
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 5, 1);
            if (this.field_74996_b) {
                this.getNextComponentX((Stairs2)par1StructureComponent, par2List, par3Random, var4, 1);
            }
            if (this.field_74997_c) {
                this.getNextComponentX((Stairs2)par1StructureComponent, par2List, par3Random, var5, 7);
            }
            if (this.field_74995_d) {
                this.getNextComponentZ((Stairs2)par1StructureComponent, par2List, par3Random, var4, 1);
            }
            if (this.field_74999_h) {
                this.getNextComponentZ((Stairs2)par1StructureComponent, par2List, par3Random, var5, 7);
            }
        }

        public static Crossing findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -3, 0, 10, 9, 11, par5);
            return Crossing.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Crossing(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 8, 10, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 4, 3, 0);
            if (this.field_74996_b) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 5, 3, Blocks.air, Blocks.air, false);
            }
            if (this.field_74995_d) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 9, 3, 1, 9, 5, 3, Blocks.air, Blocks.air, false);
            }
            if (this.field_74997_c) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 7, 0, 7, 9, Blocks.air, Blocks.air, false);
            }
            if (this.field_74999_h) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 7, 9, 7, 9, Blocks.air, Blocks.air, false);
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 10, 7, 3, 10, Blocks.air, Blocks.air, false);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 2, 1, 8, 2, 6, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 4, 4, 9, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 5, 8, 4, 9, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 4, 7, 3, 4, 9, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 5, 3, 3, 6, false, par2Random, strongholdStones);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 3, 3, 4, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 6, 3, 4, 6, Blocks.stone_slab, Blocks.stone_slab, false);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 1, 7, 7, 1, 8, false, par2Random, strongholdStones);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 9, 7, 1, 9, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 7, 7, 2, 7, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 7, 4, 5, 9, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 7, 8, 5, 9, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
            this.func_151550_a(par1World, Blocks.torch, 0, 6, 5, 6, par3StructureBoundingBox);
            return true;
        }
    }

    public static class LeftTurn
    extends Stronghold {
        private static final String __OBFID = "CL_00000490";

        public LeftTurn() {
        }

        public LeftTurn(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.getNextComponentZ((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
            } else {
                this.getNextComponentX((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
            }
        }

        public static LeftTurn findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 5, par5);
            return LeftTurn.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new LeftTurn(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
            } else {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }

    public static class Library
    extends Stronghold {
        private static final WeightedRandomChestContent[] strongholdLibraryChestContents = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent(Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1)};
        private boolean isLargeRoom;
        private static final String __OBFID = "CL_00000491";

        public Library() {
        }

        public Library(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
            this.isLargeRoom = par3StructureBoundingBox.getYSize() > 6;
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Tall", this.isLargeRoom);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.isLargeRoom = par1NBTTagCompound.getBoolean("Tall");
        }

        public static Library findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 11, 15, par5);
            if (!(Library.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null || Library.canStrongholdGoDeeper(var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 6, 15, par5)) && StructureComponent.findIntersecting(par0List, var7) == null)) {
                return null;
            }
            return new Library(par6, par1Random, var7, par5);
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            int var4 = 11;
            if (!this.isLargeRoom) {
                var4 = 6;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 13, var4 - 1, 14, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 4, 1, 0);
            this.func_151551_a(par1World, par3StructureBoundingBox, par2Random, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.web, Blocks.web, false);
            boolean var5 = true;
            boolean var6 = true;
            int var7 = 1;
            while (var7 <= 13) {
                if ((var7 - 1) % 4 == 0) {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, var7, 1, 4, var7, Blocks.planks, Blocks.planks, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, 12, 1, var7, 12, 4, var7, Blocks.planks, Blocks.planks, false);
                    this.func_151550_a(par1World, Blocks.torch, 0, 2, 3, var7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.torch, 0, 11, 3, var7, par3StructureBoundingBox);
                    if (this.isLargeRoom) {
                        this.func_151549_a(par1World, par3StructureBoundingBox, 1, 6, var7, 1, 9, var7, Blocks.planks, Blocks.planks, false);
                        this.func_151549_a(par1World, par3StructureBoundingBox, 12, 6, var7, 12, 9, var7, Blocks.planks, Blocks.planks, false);
                    }
                } else {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, var7, 1, 4, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, 12, 1, var7, 12, 4, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                    if (this.isLargeRoom) {
                        this.func_151549_a(par1World, par3StructureBoundingBox, 1, 6, var7, 1, 9, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                        this.func_151549_a(par1World, par3StructureBoundingBox, 12, 6, var7, 12, 9, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                    }
                }
                ++var7;
            }
            var7 = 3;
            while (var7 < 12) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 3, 1, var7, 4, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 6, 1, var7, 7, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 9, 1, var7, 10, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                var7 += 2;
            }
            if (this.isLargeRoom) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 5, 13, Blocks.planks, Blocks.planks, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 10, 5, 1, 12, 5, 13, Blocks.planks, Blocks.planks, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 1, 9, 5, 2, Blocks.planks, Blocks.planks, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 12, 9, 5, 13, Blocks.planks, Blocks.planks, false);
                this.func_151550_a(par1World, Blocks.planks, 0, 9, 5, 11, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.planks, 0, 8, 5, 11, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.planks, 0, 9, 5, 10, par3StructureBoundingBox);
                this.func_151549_a(par1World, par3StructureBoundingBox, 3, 6, 2, 3, 6, 12, Blocks.fence, Blocks.fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 10, 6, 2, 10, 6, 10, Blocks.fence, Blocks.fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 6, 2, 9, 6, 2, Blocks.fence, Blocks.fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 6, 12, 8, 6, 12, Blocks.fence, Blocks.fence, false);
                this.func_151550_a(par1World, Blocks.fence, 0, 9, 6, 11, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 8, 6, 11, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, 9, 6, 10, par3StructureBoundingBox);
                var7 = this.func_151555_a(Blocks.ladder, 3);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 1, 13, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 2, 13, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 3, 13, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 4, 13, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 5, 13, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 6, 13, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.ladder, var7, 10, 7, 13, par3StructureBoundingBox);
                int var8 = 7;
                int var9 = 7;
                this.func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 9, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8, 9, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 8, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8, 8, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 7, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8, 7, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8 - 2, 7, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8 + 1, 7, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 7, var9 - 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8 - 1, 7, var9 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8, 7, var9 - 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.fence, 0, var8, 7, var9 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.torch, 0, var8 - 2, 8, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.torch, 0, var8 + 1, 8, var9, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.torch, 0, var8 - 1, 8, var9 - 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.torch, 0, var8 - 1, 8, var9 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.torch, 0, var8, 8, var9 - 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.torch, 0, var8, 8, var9 + 1, par3StructureBoundingBox);
            }
            this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 3, 5, WeightedRandomChestContent.func_92080_a(strongholdLibraryChestContents, Items.enchanted_book.func_92112_a(par2Random, 1, 5, 2)), 1 + par2Random.nextInt(4));
            if (this.isLargeRoom) {
                this.func_151550_a(par1World, Blocks.air, 0, 12, 9, 1, par3StructureBoundingBox);
                this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 12, 8, 1, WeightedRandomChestContent.func_92080_a(strongholdLibraryChestContents, Items.enchanted_book.func_92112_a(par2Random, 1, 5, 2)), 1 + par2Random.nextInt(4));
            }
            return true;
        }
    }

    static class PieceWeight {
        public Class pieceClass;
        public final int pieceWeight;
        public int instancesSpawned;
        public int instancesLimit;
        private static final String __OBFID = "CL_00000492";

        public PieceWeight(Class par1Class, int par2, int par3) {
            this.pieceClass = par1Class;
            this.pieceWeight = par2;
            this.instancesLimit = par3;
        }

        public boolean canSpawnMoreStructuresOfType(int par1) {
            if (this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit) {
                return false;
            }
            return true;
        }

        public boolean canSpawnMoreStructures() {
            if (this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit) {
                return false;
            }
            return true;
        }
    }

    public static class PortalRoom
    extends Stronghold {
        private boolean hasSpawner;
        private static final String __OBFID = "CL_00000493";

        public PortalRoom() {
        }

        public PortalRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Mob", this.hasSpawner);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.hasSpawner = par1NBTTagCompound.getBoolean("Mob");
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            if (par1StructureComponent != null) {
                ((Stairs2)par1StructureComponent).strongholdPortalRoom = this;
            }
        }

        public static PortalRoom findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 8, 16, par5);
            return PortalRoom.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new PortalRoom(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 7, 15, false, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, Stronghold.Door.GRATES, 4, 1, 0);
            int var4 = 6;
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, var4, 1, 1, var4, 14, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, var4, 1, 9, var4, 14, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, var4, 1, 8, var4, 2, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, var4, 14, 8, var4, 14, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 2, 1, 4, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 1, 9, 1, 4, false, par2Random, strongholdStones);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava, Blocks.flowing_lava, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava, Blocks.flowing_lava, false);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 3, 1, 8, 7, 1, 12, false, par2Random, strongholdStones);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava, Blocks.flowing_lava, false);
            int var5 = 3;
            while (var5 < 14) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, var5, 0, 4, var5, Blocks.iron_bars, Blocks.iron_bars, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 10, 3, var5, 10, 4, var5, Blocks.iron_bars, Blocks.iron_bars, false);
                var5 += 2;
            }
            var5 = 2;
            while (var5 < 9) {
                this.func_151549_a(par1World, par3StructureBoundingBox, var5, 3, 15, var5, 4, 15, Blocks.iron_bars, Blocks.iron_bars, false);
                var5 += 2;
            }
            var5 = this.func_151555_a(Blocks.stone_brick_stairs, 3);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 6, 1, 7, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 2, 6, 6, 2, 7, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 3, 7, 6, 3, 7, false, par2Random, strongholdStones);
            int var6 = 4;
            while (var6 <= 6) {
                this.func_151550_a(par1World, Blocks.stone_brick_stairs, var5, var6, 1, 4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stone_brick_stairs, var5, var6, 2, 5, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stone_brick_stairs, var5, var6, 3, 6, par3StructureBoundingBox);
                ++var6;
            }
            int var14 = 2;
            int var7 = 0;
            int var8 = 3;
            int var9 = 1;
            switch (this.coordBaseMode) {
                case 0: {
                    var14 = 0;
                    var7 = 2;
                    break;
                }
                case 1: {
                    var14 = 1;
                    var7 = 3;
                    var8 = 0;
                    var9 = 2;
                }
                default: {
                    break;
                }
                case 3: {
                    var14 = 3;
                    var7 = 1;
                    var8 = 0;
                    var9 = 2;
                }
            }
            this.func_151550_a(par1World, Blocks.end_portal_frame, var14 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 4, 3, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var14 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 5, 3, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var14 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 6, 3, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var7 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 4, 3, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var7 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 5, 3, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var7 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 6, 3, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var8 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 3, 3, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var8 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 3, 3, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var8 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 3, 3, 11, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var9 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 7, 3, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var9 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 7, 3, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.end_portal_frame, var9 + (par2Random.nextFloat() > 0.9f ? 4 : 0), 7, 3, 11, par3StructureBoundingBox);
            if (!this.hasSpawner) {
                int var11;
                int var13 = this.getYWithOffset(3);
                int var10 = this.getXWithOffset(5, 6);
                if (par3StructureBoundingBox.isVecInside(var10, var13, var11 = this.getZWithOffset(5, 6))) {
                    this.hasSpawner = true;
                    par1World.setBlock(var10, var13, var11, Blocks.mob_spawner, 0, 2);
                    TileEntityMobSpawner var12 = (TileEntityMobSpawner)par1World.getTileEntity(var10, var13, var11);
                    if (var12 != null) {
                        var12.func_145881_a().setMobID("Silverfish");
                    }
                }
            }
            return true;
        }
    }

    public static class Prison
    extends Stronghold {
        private static final String __OBFID = "CL_00000494";

        public Prison() {
        }

        public Prison(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
        }

        public static Prison findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 9, 5, 11, par5);
            return Prison.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Prison(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 4, 10, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 10, 3, 3, 10, Blocks.air, Blocks.air, false);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 1, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 3, 4, 3, 3, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 7, 4, 3, 7, false, par2Random, strongholdStones);
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 3, 9, false, par2Random, strongholdStones);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 4, 4, 3, 6, Blocks.iron_bars, Blocks.iron_bars, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 1, 5, 7, 3, 5, Blocks.iron_bars, Blocks.iron_bars, false);
            this.func_151550_a(par1World, Blocks.iron_bars, 0, 4, 3, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_bars, 0, 4, 3, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3), 4, 1, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3) + 8, 4, 2, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3), 4, 1, 8, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3) + 8, 4, 2, 8, par3StructureBoundingBox);
            return true;
        }
    }

    public static class RightTurn
    extends LeftTurn {
        private static final String __OBFID = "CL_00000495";

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.getNextComponentX((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
            } else {
                this.getNextComponentZ((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
            }
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
            } else {
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }

    public static class RoomCrossing
    extends Stronghold {
        private static final WeightedRandomChestContent[] strongholdRoomCrossingChestContents = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1)};
        protected int roomType;
        private static final String __OBFID = "CL_00000496";

        public RoomCrossing() {
        }

        public RoomCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
            this.roomType = par2Random.nextInt(5);
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("Type", this.roomType);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.roomType = par1NBTTagCompound.getInteger("Type");
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 4, 1);
            this.getNextComponentX((Stairs2)par1StructureComponent, par2List, par3Random, 1, 4);
            this.getNextComponentZ((Stairs2)par1StructureComponent, par2List, par3Random, 1, 4);
        }

        public static RoomCrossing findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 7, 11, par5);
            return RoomCrossing.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new RoomCrossing(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 6, 10, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 4, 1, 0);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.air, Blocks.air, false);
            switch (this.roomType) {
                case 0: {
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 5, 1, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 5, 2, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 5, 3, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.torch, 0, 4, 3, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.torch, 0, 6, 3, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.torch, 0, 5, 3, 4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.torch, 0, 5, 3, 6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 4, 1, 4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 4, 1, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 4, 1, 6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 6, 1, 4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 6, 1, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 6, 1, 6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 5, 1, 4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_slab, 0, 5, 1, 6, par3StructureBoundingBox);
                    break;
                }
                case 1: {
                    int var4 = 0;
                    while (var4 < 5) {
                        this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 1, 3 + var4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.stonebrick, 0, 7, 1, 3 + var4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.stonebrick, 0, 3 + var4, 1, 3, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.stonebrick, 0, 3 + var4, 1, 7, par3StructureBoundingBox);
                        ++var4;
                    }
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 5, 1, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 5, 2, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 5, 3, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.flowing_water, 0, 5, 4, 5, par3StructureBoundingBox);
                    break;
                }
                case 2: {
                    int var4 = 1;
                    while (var4 <= 9) {
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, 1, 3, var4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, 9, 3, var4, par3StructureBoundingBox);
                        ++var4;
                    }
                    var4 = 1;
                    while (var4 <= 9) {
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, var4, 3, 1, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, var4, 3, 9, par3StructureBoundingBox);
                        ++var4;
                    }
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 5, 1, 4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 5, 1, 6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 5, 3, 4, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 5, 3, 6, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 1, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 6, 1, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, 3, 5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.cobblestone, 0, 6, 3, 5, par3StructureBoundingBox);
                    var4 = 1;
                    while (var4 <= 3) {
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, var4, 4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, 6, var4, 4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, 4, var4, 6, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.cobblestone, 0, 6, var4, 6, par3StructureBoundingBox);
                        ++var4;
                    }
                    this.func_151550_a(par1World, Blocks.torch, 0, 5, 3, 5, par3StructureBoundingBox);
                    var4 = 2;
                    while (var4 <= 8) {
                        this.func_151550_a(par1World, Blocks.planks, 0, 2, 3, var4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.planks, 0, 3, 3, var4, par3StructureBoundingBox);
                        if (var4 <= 3 || var4 >= 7) {
                            this.func_151550_a(par1World, Blocks.planks, 0, 4, 3, var4, par3StructureBoundingBox);
                            this.func_151550_a(par1World, Blocks.planks, 0, 5, 3, var4, par3StructureBoundingBox);
                            this.func_151550_a(par1World, Blocks.planks, 0, 6, 3, var4, par3StructureBoundingBox);
                        }
                        this.func_151550_a(par1World, Blocks.planks, 0, 7, 3, var4, par3StructureBoundingBox);
                        this.func_151550_a(par1World, Blocks.planks, 0, 8, 3, var4, par3StructureBoundingBox);
                        ++var4;
                    }
                    this.func_151550_a(par1World, Blocks.ladder, this.func_151555_a(Blocks.ladder, 4), 9, 1, 3, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.ladder, this.func_151555_a(Blocks.ladder, 4), 9, 2, 3, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.ladder, this.func_151555_a(Blocks.ladder, 4), 9, 3, 3, par3StructureBoundingBox);
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, WeightedRandomChestContent.func_92080_a(strongholdRoomCrossingChestContents, Items.enchanted_book.func_92114_b(par2Random)), 1 + par2Random.nextInt(4));
                }
            }
            return true;
        }
    }

    public static class Stairs
    extends Stronghold {
        private boolean field_75024_a;
        private static final String __OBFID = "CL_00000498";

        public Stairs() {
        }

        public Stairs(int par1, Random par2Random, int par3, int par4) {
            super(par1);
            this.field_75024_a = true;
            this.coordBaseMode = par2Random.nextInt(4);
            this.field_143013_d = Stronghold.Door.OPENING;
            switch (this.coordBaseMode) {
                case 0: 
                case 2: {
                    this.boundingBox = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
                }
            }
        }

        public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.field_75024_a = false;
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Source", this.field_75024_a);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.field_75024_a = par1NBTTagCompound.getBoolean("Source");
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            if (this.field_75024_a) {
                StructureStrongholdPieces.access$1(Crossing.class);
            }
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
        }

        public static Stairs getStrongholdStairsComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 5, par5);
            return Stairs.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Stairs(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 4, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 7, 0);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, Stronghold.Door.OPENING, 1, 1, 4);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 2, 6, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 5, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 0, 1, 6, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 5, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 0, 1, 5, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 2, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 3, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 0, 3, 4, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 3, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 0, 3, 3, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 2, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 1, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 0, 1, 2, 1, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 1, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.stone_slab, 0, 1, 1, 3, par3StructureBoundingBox);
            return true;
        }
    }

    public static class Stairs2
    extends Stairs {
        public PieceWeight strongholdPieceWeight;
        public PortalRoom strongholdPortalRoom;
        public List field_75026_c = new ArrayList();
        private static final String __OBFID = "CL_00000499";

        public Stairs2() {
        }

        public Stairs2(int par1, Random par2Random, int par3, int par4) {
            super(0, par2Random, par3, par4);
        }

        @Override
        public ChunkPosition func_151553_a() {
            return this.strongholdPortalRoom != null ? this.strongholdPortalRoom.func_151553_a() : super.func_151553_a();
        }
    }

    public static class StairsStraight
    extends Stronghold {
        private static final String __OBFID = "CL_00000501";

        public StairsStraight() {
        }

        public StairsStraight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
        }

        public static StairsStraight findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 8, par5);
            return StairsStraight.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StairsStraight(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 7, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 7, 0);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, Stronghold.Door.OPENING, 1, 1, 7);
            int var4 = this.func_151555_a(Blocks.stone_stairs, 2);
            int var5 = 0;
            while (var5 < 6) {
                this.func_151550_a(par1World, Blocks.stone_stairs, var4, 1, 6 - var5, 1 + var5, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stone_stairs, var4, 2, 6 - var5, 1 + var5, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.stone_stairs, var4, 3, 6 - var5, 1 + var5, par3StructureBoundingBox);
                if (var5 < 5) {
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 1, 5 - var5, 1 + var5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 2, 5 - var5, 1 + var5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, 3, 5 - var5, 1 + var5, par3StructureBoundingBox);
                }
                ++var5;
            }
            return true;
        }
    }

    static class Stones
    extends StructureComponent.BlockSelector {
        private static final String __OBFID = "CL_00000497";

        private Stones() {
        }

        @Override
        public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5) {
            if (par5) {
                this.field_151562_a = Blocks.stonebrick;
                float var6 = par1Random.nextFloat();
                if (var6 < 0.2f) {
                    this.selectedBlockMetaData = 2;
                } else if (var6 < 0.5f) {
                    this.selectedBlockMetaData = 1;
                } else if (var6 < 0.55f) {
                    this.field_151562_a = Blocks.monster_egg;
                    this.selectedBlockMetaData = 2;
                } else {
                    this.selectedBlockMetaData = 0;
                }
            } else {
                this.field_151562_a = Blocks.air;
                this.selectedBlockMetaData = 0;
            }
        }

        Stones(Object par1StructureStrongholdPieceWeight2) {
            this();
        }
    }

    public static class Straight
    extends Stronghold {
        private boolean expandsX;
        private boolean expandsZ;
        private static final String __OBFID = "CL_00000500";

        public Straight() {
        }

        public Straight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.field_143013_d = this.getRandomDoor(par2Random);
            this.boundingBox = par3StructureBoundingBox;
            this.expandsX = par2Random.nextInt(2) == 0;
            this.expandsZ = par2Random.nextInt(2) == 0;
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Left", this.expandsX);
            par1NBTTagCompound.setBoolean("Right", this.expandsZ);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.expandsX = par1NBTTagCompound.getBoolean("Left");
            this.expandsZ = par1NBTTagCompound.getBoolean("Right");
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
            if (this.expandsX) {
                this.getNextComponentX((Stairs2)par1StructureComponent, par2List, par3Random, 1, 2);
            }
            if (this.expandsZ) {
                this.getNextComponentZ((Stairs2)par1StructureComponent, par2List, par3Random, 1, 2);
            }
        }

        public static Straight findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
            return Straight.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Straight(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, strongholdStones);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.field_143013_d, 1, 1, 0);
            this.placeDoor(par1World, par2Random, par3StructureBoundingBox, Stronghold.Door.OPENING, 1, 1, 6);
            this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1f, 1, 2, 1, Blocks.torch, 0);
            this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1f, 3, 2, 1, Blocks.torch, 0);
            this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1f, 1, 2, 5, Blocks.torch, 0);
            this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1f, 3, 2, 5, Blocks.torch, 0);
            if (this.expandsX) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.air, Blocks.air, false);
            }
            if (this.expandsZ) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }

    static abstract class Stronghold
    extends StructureComponent {
        protected Door field_143013_d = Door.OPENING;
        private static final String __OBFID = "CL_00000503";

        public Stronghold() {
        }

        protected Stronghold(int par1) {
            super(par1);
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            par1NBTTagCompound.setString("EntryDoor", this.field_143013_d.name());
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            this.field_143013_d = Door.valueOf(par1NBTTagCompound.getString("EntryDoor"));
        }

        protected void placeDoor(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, Door par4EnumDoor, int par5, int par6, int par7) {
            switch (par4EnumDoor) {
                default: {
                    this.func_151549_a(par1World, par3StructureBoundingBox, par5, par6, par7, par5 + 3 - 1, par6 + 3 - 1, par7, Blocks.air, Blocks.air, false);
                    break;
                }
                case WOOD_DOOR: {
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.wooden_door, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.wooden_door, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                    break;
                }
                case GRATES: {
                    this.func_151550_a(par1World, Blocks.air, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.air, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_bars, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                    break;
                }
                case IRON_DOOR: {
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stonebrick, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_door, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.iron_door, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_button, this.func_151555_a(Blocks.stone_button, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.stone_button, this.func_151555_a(Blocks.stone_button, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
                }
            }
        }

        protected Door getRandomDoor(Random par1Random) {
            int var2 = par1Random.nextInt(5);
            switch (var2) {
                default: {
                    return Door.OPENING;
                }
                case 2: {
                    return Door.WOOD_DOOR;
                }
                case 3: {
                    return Door.GRATES;
                }
                case 4: 
            }
            return Door.IRON_DOOR;
        }

        protected StructureComponent getNextComponentNormal(Stairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5) {
            switch (this.coordBaseMode) {
                case 0: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
                }
                case 1: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType());
                }
                case 2: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
                }
                case 3: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType());
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentX(Stairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5) {
            switch (this.coordBaseMode) {
                case 0: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
                }
                case 1: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
                case 2: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
                }
                case 3: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentZ(Stairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5) {
            switch (this.coordBaseMode) {
                case 0: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
                }
                case 1: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
                case 2: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
                }
                case 3: {
                    return StructureStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
            }
            return null;
        }

        protected static boolean canStrongholdGoDeeper(StructureBoundingBox par0StructureBoundingBox) {
            if (par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10) {
                return true;
            }
            return false;
        }

        public static enum Door {
            OPENING("OPENING", 0),
            WOOD_DOOR("WOOD_DOOR", 1),
            GRATES("GRATES", 2),
            IRON_DOOR("IRON_DOOR", 3);
            
            private static final Door[] $VALUES;
            private static final String __OBFID = "CL_00000504";

            static {
                $VALUES = new Door[]{OPENING, WOOD_DOOR, GRATES, IRON_DOOR};
            }

            private Door(String par1Str, int par2, String string2, int n2) {
            }
        }

    }

}

