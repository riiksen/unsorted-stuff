/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class StructureNetherBridgePieces {
    private static final PieceWeight[] primaryComponents = new PieceWeight[]{new PieceWeight(Straight.class, 30, 0, true), new PieceWeight(Crossing3.class, 10, 4), new PieceWeight(Crossing.class, 10, 4), new PieceWeight(Stairs.class, 10, 3), new PieceWeight(Throne.class, 5, 2), new PieceWeight(Entrance.class, 5, 1)};
    private static final PieceWeight[] secondaryComponents = new PieceWeight[]{new PieceWeight(Corridor5.class, 25, 0, true), new PieceWeight(Crossing2.class, 15, 5), new PieceWeight(Corridor2.class, 5, 10), new PieceWeight(Corridor.class, 5, 10), new PieceWeight(Corridor3.class, 10, 3, true), new PieceWeight(Corridor4.class, 7, 2), new PieceWeight(NetherStalkRoom.class, 5, 2)};
    private static final String __OBFID = "CL_00000453";

    public static void func_143049_a() {
        MapGenStructureIO.func_143031_a(Crossing3.class, "NeBCr");
        MapGenStructureIO.func_143031_a(End.class, "NeBEF");
        MapGenStructureIO.func_143031_a(Straight.class, "NeBS");
        MapGenStructureIO.func_143031_a(Corridor3.class, "NeCCS");
        MapGenStructureIO.func_143031_a(Corridor4.class, "NeCTB");
        MapGenStructureIO.func_143031_a(Entrance.class, "NeCE");
        MapGenStructureIO.func_143031_a(Crossing2.class, "NeSCSC");
        MapGenStructureIO.func_143031_a(Corridor.class, "NeSCLT");
        MapGenStructureIO.func_143031_a(Corridor5.class, "NeSC");
        MapGenStructureIO.func_143031_a(Corridor2.class, "NeSCRT");
        MapGenStructureIO.func_143031_a(NetherStalkRoom.class, "NeCSR");
        MapGenStructureIO.func_143031_a(Throne.class, "NeMT");
        MapGenStructureIO.func_143031_a(Crossing.class, "NeRC");
        MapGenStructureIO.func_143031_a(Stairs.class, "NeSR");
        MapGenStructureIO.func_143031_a(Start.class, "NeStart");
    }

    private static Piece createNextComponentRandom(PieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
        Class var8 = par0StructureNetherBridgePieceWeight.weightClass;
        Piece var9 = null;
        if (var8 == Straight.class) {
            var9 = Straight.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Crossing3.class) {
            var9 = Crossing3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Crossing.class) {
            var9 = Crossing.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Stairs.class) {
            var9 = Stairs.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Throne.class) {
            var9 = Throne.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Entrance.class) {
            var9 = Entrance.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Corridor5.class) {
            var9 = Corridor5.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Corridor2.class) {
            var9 = Corridor2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Corridor.class) {
            var9 = Corridor.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Corridor3.class) {
            var9 = Corridor3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Corridor4.class) {
            var9 = Corridor4.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == Crossing2.class) {
            var9 = Crossing2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        } else if (var8 == NetherStalkRoom.class) {
            var9 = NetherStalkRoom.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        return var9;
    }

    public static class Corridor
    extends Piece {
        private boolean field_111021_b;
        private static final String __OBFID = "CL_00000461";

        public Corridor() {
        }

        public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.field_111021_b = par2Random.nextInt(3) == 0;
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.field_111021_b = par1NBTTagCompound.getBoolean("Chest");
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Chest", this.field_111021_b);
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentX((Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
        }

        public static Corridor createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return Corridor.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Corridor(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var5;
            int var4;
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 3, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            if (this.field_111021_b) {
                int var6;
                var4 = this.getYWithOffset(2);
                var5 = this.getXWithOffset(3, 3);
                if (par3StructureBoundingBox.isVecInside(var5, var4, var6 = this.getZWithOffset(3, 3))) {
                    this.field_111021_b = false;
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
                }
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            var4 = 0;
            while (var4 <= 4) {
                var5 = 0;
                while (var5 <= 4) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Corridor2
    extends Piece {
        private boolean field_111020_b;
        private static final String __OBFID = "CL_00000463";

        public Corridor2() {
        }

        public Corridor2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.field_111020_b = par2Random.nextInt(3) == 0;
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.field_111020_b = par1NBTTagCompound.getBoolean("Chest");
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Chest", this.field_111020_b);
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentZ((Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
        }

        public static Corridor2 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return Corridor2.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Corridor2(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var5;
            int var4;
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 4, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            if (this.field_111020_b) {
                int var6;
                var4 = this.getYWithOffset(2);
                var5 = this.getXWithOffset(1, 3);
                if (par3StructureBoundingBox.isVecInside(var5, var4, var6 = this.getZWithOffset(1, 3))) {
                    this.field_111020_b = false;
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 1, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
                }
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            var4 = 0;
            while (var4 <= 4) {
                var5 = 0;
                while (var5 <= 4) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Corridor3
    extends Piece {
        private static final String __OBFID = "CL_00000457";

        public Corridor3() {
        }

        public Corridor3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
        }

        public static Corridor3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 14, 10, par5);
            return Corridor3.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Corridor3(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var4 = this.func_151555_a(Blocks.nether_brick_stairs, 2);
            int var5 = 0;
            while (var5 <= 9) {
                int var6 = Math.max(1, 7 - var5);
                int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
                int var8 = var5;
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, var5, 4, var6, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air, Blocks.air, false);
                if (var5 <= 6) {
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 1, var6 + 1, var5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 2, var6 + 1, var5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 3, var6 + 1, var5, par3StructureBoundingBox);
                }
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, var7, var5, 4, var7, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                if ((var5 & 1) == 0) {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                }
                int var9 = 0;
                while (var9 <= 4) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var9, -1, var8, par3StructureBoundingBox);
                    ++var9;
                }
                ++var5;
            }
            return true;
        }
    }

    public static class Corridor4
    extends Piece {
        private static final String __OBFID = "CL_00000458";

        public Corridor4() {
        }

        public Corridor4(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            int var4 = 1;
            if (this.coordBaseMode == 1 || this.coordBaseMode == 2) {
                var4 = 5;
            }
            this.getNextComponentX((Start)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
            this.getNextComponentZ((Start)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
        }

        public static Corridor4 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -3, 0, 0, 9, 7, 9, par5);
            return Corridor4.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Corridor4(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 8, 5, 8, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 8, 6, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 2, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 0, 8, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 8, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 2, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 4, 7, 2, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 3, 4, 8, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 5, 2, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 5, 7, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            int var4 = 0;
            while (var4 <= 5) {
                int var5 = 0;
                while (var5 <= 8) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var5, -1, var4, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Corridor5
    extends Piece {
        private static final String __OBFID = "CL_00000462";

        public Corridor5() {
        }

        public Corridor5(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
        }

        public static Corridor5 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return Corridor5.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Corridor5(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 4) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing
    extends Piece {
        private static final String __OBFID = "CL_00000468";

        public Crossing() {
        }

        public Crossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 2, 0, false);
            this.getNextComponentX((Start)par1StructureComponent, par2List, par3Random, 0, 2, false);
            this.getNextComponentZ((Start)par1StructureComponent, par2List, par3Random, 0, 2, false);
        }

        public static Crossing createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 9, 7, par5);
            return Crossing.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Crossing(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            int var4 = 0;
            while (var4 <= 6) {
                int var5 = 0;
                while (var5 <= 6) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing2
    extends Piece {
        private static final String __OBFID = "CL_00000460";

        public Crossing2() {
        }

        public Crossing2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
            this.getNextComponentX((Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
            this.getNextComponentZ((Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
        }

        public static Crossing2 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return Crossing2.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Crossing2(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 4, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 4) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing3
    extends Piece {
        private static final String __OBFID = "CL_00000454";

        public Crossing3() {
        }

        public Crossing3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        protected Crossing3(Random par1Random, int par2, int par3) {
            super(0);
            this.coordBaseMode = par1Random.nextInt(4);
            switch (this.coordBaseMode) {
                case 0: 
                case 2: {
                    this.boundingBox = new StructureBoundingBox(par2, 64, par3, par2 + 19 - 1, 73, par3 + 19 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(par2, 64, par3, par2 + 19 - 1, 73, par3 + 19 - 1);
                }
            }
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 8, 3, false);
            this.getNextComponentX((Start)par1StructureComponent, par2List, par3Random, 3, 8, false);
            this.getNextComponentZ((Start)par1StructureComponent, par2List, par3Random, 3, 8, false);
        }

        public static Crossing3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -8, -3, 0, 19, 10, 19, par5);
            return Crossing3.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Crossing3(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var5;
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4 = 7;
            while (var4 <= 11) {
                var5 = 0;
                while (var5 <= 2) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            var4 = 0;
            while (var4 <= 2) {
                var5 = 7;
                while (var5 <= 11) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, 18 - var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class End
    extends Piece {
        private int fillSeed;
        private static final String __OBFID = "CL_00000455";

        public End() {
        }

        public End(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.fillSeed = par2Random.nextInt();
        }

        public static End func_74971_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 8, par5);
            return End.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new End(par6, par1Random, var7, par5) : null;
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.fillSeed = par1NBTTagCompound.getInteger("Seed");
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("Seed", this.fillSeed);
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var6;
            int var7;
            Random var4 = new Random(this.fillSeed);
            int var5 = 0;
            while (var5 <= 4) {
                var6 = 3;
                while (var6 <= 4) {
                    var7 = var4.nextInt(8);
                    this.func_151549_a(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Blocks.nether_brick, Blocks.nether_brick, false);
                    ++var6;
                }
                ++var5;
            }
            var5 = var4.nextInt(8);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, var5, Blocks.nether_brick, Blocks.nether_brick, false);
            var5 = var4.nextInt(8);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, var5, Blocks.nether_brick, Blocks.nether_brick, false);
            var5 = 0;
            while (var5 <= 4) {
                var6 = var4.nextInt(5);
                this.func_151549_a(par1World, par3StructureBoundingBox, var5, 2, 0, var5, 2, var6, Blocks.nether_brick, Blocks.nether_brick, false);
                ++var5;
            }
            var5 = 0;
            while (var5 <= 4) {
                var6 = 0;
                while (var6 <= 1) {
                    var7 = var4.nextInt(3);
                    this.func_151549_a(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Blocks.nether_brick, Blocks.nether_brick, false);
                    ++var6;
                }
                ++var5;
            }
            return true;
        }
    }

    public static class Entrance
    extends Piece {
        private static final String __OBFID = "CL_00000459";

        public Entrance() {
        }

        public Entrance(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 5, 3, true);
        }

        public static Entrance createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
            return Entrance.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Entrance(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var5;
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            int var4 = 1;
            while (var4 <= 11) {
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 0, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 12, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
                var4 += 2;
            }
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, 0, par3StructureBoundingBox);
            var4 = 3;
            while (var4 <= 9) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                var4 += 2;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            var4 = 4;
            while (var4 <= 8) {
                var5 = 0;
                while (var5 <= 2) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 12 - var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            var4 = 0;
            while (var4 <= 2) {
                var5 = 4;
                while (var5 <= 8) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, 12 - var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 5, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 6, 6, 4, 6, Blocks.air, Blocks.air, false);
            this.func_151550_a(par1World, Blocks.nether_brick, 0, 6, 0, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.flowing_lava, 0, 6, 5, 6, par3StructureBoundingBox);
            var4 = this.getXWithOffset(6, 6);
            var5 = this.getYWithOffset(5);
            int var6 = this.getZWithOffset(6, 6);
            if (par3StructureBoundingBox.isVecInside(var4, var5, var6)) {
                par1World.scheduledUpdatesAreImmediate = true;
                Blocks.flowing_lava.updateTick(par1World, var4, var5, var6, par2Random);
                par1World.scheduledUpdatesAreImmediate = false;
            }
            return true;
        }
    }

    public static class NetherStalkRoom
    extends Piece {
        private static final String __OBFID = "CL_00000464";

        public NetherStalkRoom() {
        }

        public NetherStalkRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 5, 3, true);
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 5, 11, true);
        }

        public static NetherStalkRoom createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
            return NetherStalkRoom.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new NetherStalkRoom(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var8;
            int var6;
            int var7;
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4 = 1;
            while (var4 <= 11) {
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 0, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 12, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
                var4 += 2;
            }
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, 0, par3StructureBoundingBox);
            var4 = 3;
            while (var4 <= 9) {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                var4 += 2;
            }
            var4 = this.func_151555_a(Blocks.nether_brick_stairs, 3);
            int var5 = 0;
            while (var5 <= 6) {
                var6 = var5 + 4;
                var7 = 5;
                while (var7 <= 7) {
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, var7, 5 + var5, var6, par3StructureBoundingBox);
                    ++var7;
                }
                if (var6 >= 5 && var6 <= 8) {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, var6, 7, var5 + 4, var6, Blocks.nether_brick, Blocks.nether_brick, false);
                } else if (var6 >= 9 && var6 <= 10) {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 5, 8, var6, 7, var5 + 4, var6, Blocks.nether_brick, Blocks.nether_brick, false);
                }
                if (var5 >= 1) {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.air, Blocks.air, false);
                }
                ++var5;
            }
            var5 = 5;
            while (var5 <= 7) {
                this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, var5, 12, 11, par3StructureBoundingBox);
                ++var5;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            var5 = this.func_151555_a(Blocks.nether_brick_stairs, 0);
            var6 = this.func_151555_a(Blocks.nether_brick_stairs, 1);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 10, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.soul_sand, Blocks.soul_sand, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.soul_sand, Blocks.soul_sand, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.nether_wart, Blocks.nether_wart, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.nether_wart, Blocks.nether_wart, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            var7 = 4;
            while (var7 <= 8) {
                var8 = 0;
                while (var8 <= 2) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, var8, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, 12 - var8, par3StructureBoundingBox);
                    ++var8;
                }
                ++var7;
            }
            var7 = 0;
            while (var7 <= 2) {
                var8 = 4;
                while (var8 <= 8) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, var8, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, 12 - var7, -1, var8, par3StructureBoundingBox);
                    ++var8;
                }
                ++var7;
            }
            return true;
        }
    }

    static abstract class Piece
    extends StructureComponent {
        protected static final WeightedRandomChestContent[] field_111019_a = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3)};
        private static final String __OBFID = "CL_00000466";

        public Piece() {
        }

        protected Piece(int par1) {
            super(par1);
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
        }

        private int getTotalWeight(List par1List) {
            boolean var2 = false;
            int var3 = 0;
            for (PieceWeight var5 : par1List) {
                if (var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d) {
                    var2 = true;
                }
                var3 += var5.field_78826_b;
            }
            return var2 ? var3 : -1;
        }

        private Piece getNextComponent(Start par1ComponentNetherBridgeStartPiece, List par2List, List par3List, Random par4Random, int par5, int par6, int par7, int par8, int par9) {
            int var10 = this.getTotalWeight(par2List);
            boolean var11 = var10 > 0 && par9 <= 30;
            int var12 = 0;
            block0 : while (var12 < 5 && var11) {
                ++var12;
                int var13 = par4Random.nextInt(var10);
                for (PieceWeight var15 : par2List) {
                    if ((var13 -= var15.field_78826_b) >= 0) continue;
                    if (!var15.func_78822_a(par9) || var15 == par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight && !var15.field_78825_e) continue block0;
                    Piece var16 = StructureNetherBridgePieces.createNextComponentRandom(var15, par3List, par4Random, par5, par6, par7, par8, par9);
                    if (var16 == null) continue;
                    ++var15.field_78827_c;
                    par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight = var15;
                    if (!var15.func_78823_a()) {
                        par2List.remove(var15);
                    }
                    return var16;
                }
            }
            return End.func_74971_a(par3List, par4Random, par5, par6, par7, par8, par9);
        }

        private StructureComponent getNextComponent(Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8, boolean par9) {
            if (Math.abs(par4 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par6 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minZ) <= 112) {
                Piece var11;
                List var10 = par1ComponentNetherBridgeStartPiece.primaryWeights;
                if (par9) {
                    var10 = par1ComponentNetherBridgeStartPiece.secondaryWeights;
                }
                if ((var11 = this.getNextComponent(par1ComponentNetherBridgeStartPiece, var10, par2List, par3Random, par4, par5, par6, par7, par8 + 1)) != null) {
                    par2List.add(var11);
                    par1ComponentNetherBridgeStartPiece.field_74967_d.add(var11);
                }
                return var11;
            }
            return End.func_74971_a(par2List, par3Random, par4, par5, par6, par7, par8);
        }

        protected StructureComponent getNextComponentNormal(Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6) {
            switch (this.coordBaseMode) {
                case 0: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType(), par6);
                }
                case 1: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType(), par6);
                }
                case 2: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType(), par6);
                }
                case 3: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType(), par6);
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentX(Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6) {
            switch (this.coordBaseMode) {
                case 0: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType(), par6);
                }
                case 1: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType(), par6);
                }
                case 2: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType(), par6);
                }
                case 3: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType(), par6);
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentZ(Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6) {
            switch (this.coordBaseMode) {
                case 0: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType(), par6);
                }
                case 1: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType(), par6);
                }
                case 2: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType(), par6);
                }
                case 3: {
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType(), par6);
                }
            }
            return null;
        }

        protected static boolean isAboveGround(StructureBoundingBox par0StructureBoundingBox) {
            if (par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10) {
                return true;
            }
            return false;
        }
    }

    static class PieceWeight {
        public Class weightClass;
        public final int field_78826_b;
        public int field_78827_c;
        public int field_78824_d;
        public boolean field_78825_e;
        private static final String __OBFID = "CL_00000467";

        public PieceWeight(Class par1Class, int par2, int par3, boolean par4) {
            this.weightClass = par1Class;
            this.field_78826_b = par2;
            this.field_78824_d = par3;
            this.field_78825_e = par4;
        }

        public PieceWeight(Class par1Class, int par2, int par3) {
            this(par1Class, par2, par3, false);
        }

        public boolean func_78822_a(int par1) {
            if (this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d) {
                return false;
            }
            return true;
        }

        public boolean func_78823_a() {
            if (this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d) {
                return false;
            }
            return true;
        }
    }

    public static class Stairs
    extends Piece {
        private static final String __OBFID = "CL_00000469";

        public Stairs() {
        }

        public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentZ((Start)par1StructureComponent, par2List, par3Random, 6, 2, false);
        }

        public static Stairs createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 11, 7, par5);
            return Stairs.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Stairs(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 10, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 1, 6, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 6, 5, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151550_a(par1World, Blocks.nether_brick, 0, 5, 2, 5, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 5, 4, 3, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 2, 5, 3, 4, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 2, 5, 2, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 1, 6, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 7, 1, 5, 7, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 8, 2, 6, 8, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            int var4 = 0;
            while (var4 <= 6) {
                int var5 = 0;
                while (var5 <= 6) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Start
    extends Crossing3 {
        public PieceWeight theNetherBridgePieceWeight;
        public List primaryWeights;
        public List secondaryWeights;
        public ArrayList field_74967_d = new ArrayList();
        private static final String __OBFID = "CL_00000470";

        public Start() {
        }

        public Start(Random par1Random, int par2, int par3) {
            PieceWeight var7;
            super(par1Random, par2, par3);
            this.primaryWeights = new ArrayList();
            PieceWeight[] var4 = primaryComponents;
            int var5 = var4.length;
            int var6 = 0;
            while (var6 < var5) {
                var7 = var4[var6];
                var7.field_78827_c = 0;
                this.primaryWeights.add(var7);
                ++var6;
            }
            this.secondaryWeights = new ArrayList();
            var4 = secondaryComponents;
            var5 = var4.length;
            var6 = 0;
            while (var6 < var5) {
                var7 = var4[var6];
                var7.field_78827_c = 0;
                this.secondaryWeights.add(var7);
                ++var6;
            }
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
        }
    }

    public static class Straight
    extends Piece {
        private static final String __OBFID = "CL_00000456";

        public Straight() {
        }

        public Straight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
            this.getNextComponentNormal((Start)par1StructureComponent, par2List, par3Random, 1, 3, false);
        }

        public static Straight createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 19, par5);
            return Straight.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Straight(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 2) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            return true;
        }
    }

    public static class Throne
    extends Piece {
        private boolean hasSpawner;
        private static final String __OBFID = "CL_00000465";

        public Throne() {
        }

        public Throne(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4) {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        @Override
        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
            super.func_143011_b(par1NBTTagCompound);
            this.hasSpawner = par1NBTTagCompound.getBoolean("Mob");
        }

        @Override
        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Mob", this.hasSpawner);
        }

        public static Throne createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 8, 9, par5);
            return Throne.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new Throne(par6, par1Random, var7, par5) : null;
        }

        @Override
        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
            int var5;
            int var4;
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 7, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 1, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 1, 5, 2, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 2, 5, 3, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 3, 5, 4, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 1, 4, 2, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 5, 4, 2, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 2, 1, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 2, 5, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 3, 0, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 3, 6, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 8, 5, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 1, 6, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 5, 6, 3, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            if (!this.hasSpawner) {
                int var6;
                var4 = this.getYWithOffset(5);
                var5 = this.getXWithOffset(3, 5);
                if (par3StructureBoundingBox.isVecInside(var5, var4, var6 = this.getZWithOffset(3, 5))) {
                    this.hasSpawner = true;
                    par1World.setBlock(var5, var4, var6, Blocks.mob_spawner, 0, 2);
                    TileEntityMobSpawner var7 = (TileEntityMobSpawner)par1World.getTileEntity(var5, var4, var6);
                    if (var7 != null) {
                        var7.func_145881_a().setMobID("Blaze");
                    }
                }
            }
            var4 = 0;
            while (var4 <= 6) {
                var5 = 0;
                while (var5 <= 6) {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

}

