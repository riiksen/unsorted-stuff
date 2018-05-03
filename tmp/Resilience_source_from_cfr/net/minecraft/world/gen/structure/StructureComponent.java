/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public abstract class StructureComponent {
    protected StructureBoundingBox boundingBox;
    protected int coordBaseMode;
    protected int componentType;
    private static final String __OBFID = "CL_00000511";

    public StructureComponent() {
    }

    protected StructureComponent(int par1) {
        this.componentType = par1;
        this.coordBaseMode = -1;
    }

    public NBTTagCompound func_143010_b() {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("id", MapGenStructureIO.func_143036_a(this));
        var1.setTag("BB", this.boundingBox.func_151535_h());
        var1.setInteger("O", this.coordBaseMode);
        var1.setInteger("GD", this.componentType);
        this.func_143012_a(var1);
        return var1;
    }

    protected abstract void func_143012_a(NBTTagCompound var1);

    public void func_143009_a(World par1World, NBTTagCompound par2NBTTagCompound) {
        if (par2NBTTagCompound.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(par2NBTTagCompound.getIntArray("BB"));
        }
        this.coordBaseMode = par2NBTTagCompound.getInteger("O");
        this.componentType = par2NBTTagCompound.getInteger("GD");
        this.func_143011_b(par2NBTTagCompound);
    }

    protected abstract void func_143011_b(NBTTagCompound var1);

    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {
    }

    public abstract boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3);

    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public int getComponentType() {
        return this.componentType;
    }

    public static StructureComponent findIntersecting(List par0List, StructureBoundingBox par1StructureBoundingBox) {
        StructureComponent var3;
        Iterator<E> var2 = par0List.iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while ((var3 = (StructureComponent)var2.next()).getBoundingBox() == null || !var3.getBoundingBox().intersectsWith(par1StructureBoundingBox));
        return var3;
    }

    public ChunkPosition func_151553_a() {
        return new ChunkPosition(this.boundingBox.getCenterX(), this.boundingBox.getCenterY(), this.boundingBox.getCenterZ());
    }

    protected boolean isLiquidInStructureBoundingBox(World par1World, StructureBoundingBox par2StructureBoundingBox) {
        int var10;
        int var3 = Math.max(this.boundingBox.minX - 1, par2StructureBoundingBox.minX);
        int var4 = Math.max(this.boundingBox.minY - 1, par2StructureBoundingBox.minY);
        int var5 = Math.max(this.boundingBox.minZ - 1, par2StructureBoundingBox.minZ);
        int var6 = Math.min(this.boundingBox.maxX + 1, par2StructureBoundingBox.maxX);
        int var7 = Math.min(this.boundingBox.maxY + 1, par2StructureBoundingBox.maxY);
        int var8 = Math.min(this.boundingBox.maxZ + 1, par2StructureBoundingBox.maxZ);
        int var9 = var3;
        while (var9 <= var6) {
            var10 = var5;
            while (var10 <= var8) {
                if (par1World.getBlock(var9, var4, var10).getMaterial().isLiquid()) {
                    return true;
                }
                if (par1World.getBlock(var9, var7, var10).getMaterial().isLiquid()) {
                    return true;
                }
                ++var10;
            }
            ++var9;
        }
        var9 = var3;
        while (var9 <= var6) {
            var10 = var4;
            while (var10 <= var7) {
                if (par1World.getBlock(var9, var10, var5).getMaterial().isLiquid()) {
                    return true;
                }
                if (par1World.getBlock(var9, var10, var8).getMaterial().isLiquid()) {
                    return true;
                }
                ++var10;
            }
            ++var9;
        }
        var9 = var5;
        while (var9 <= var8) {
            var10 = var4;
            while (var10 <= var7) {
                if (par1World.getBlock(var3, var10, var9).getMaterial().isLiquid()) {
                    return true;
                }
                if (par1World.getBlock(var6, var10, var9).getMaterial().isLiquid()) {
                    return true;
                }
                ++var10;
            }
            ++var9;
        }
        return false;
    }

    protected int getXWithOffset(int par1, int par2) {
        switch (this.coordBaseMode) {
            case 0: 
            case 2: {
                return this.boundingBox.minX + par1;
            }
            case 1: {
                return this.boundingBox.maxX - par2;
            }
            case 3: {
                return this.boundingBox.minX + par2;
            }
        }
        return par1;
    }

    protected int getYWithOffset(int par1) {
        return this.coordBaseMode == -1 ? par1 : par1 + this.boundingBox.minY;
    }

    protected int getZWithOffset(int par1, int par2) {
        switch (this.coordBaseMode) {
            case 0: {
                return this.boundingBox.minZ + par2;
            }
            case 1: 
            case 3: {
                return this.boundingBox.minZ + par1;
            }
            case 2: {
                return this.boundingBox.maxZ - par2;
            }
        }
        return par2;
    }

    protected int func_151555_a(Block p_151555_1_, int p_151555_2_) {
        if (p_151555_1_ == Blocks.rail) {
            if (this.coordBaseMode == 1 || this.coordBaseMode == 3) {
                if (p_151555_2_ == 1) {
                    return 0;
                }
                return 1;
            }
        } else if (p_151555_1_ != Blocks.wooden_door && p_151555_1_ != Blocks.iron_door) {
            if (p_151555_1_ != Blocks.stone_stairs && p_151555_1_ != Blocks.oak_stairs && p_151555_1_ != Blocks.nether_brick_stairs && p_151555_1_ != Blocks.stone_brick_stairs && p_151555_1_ != Blocks.sandstone_stairs) {
                if (p_151555_1_ == Blocks.ladder) {
                    if (this.coordBaseMode == 0) {
                        if (p_151555_2_ == 2) {
                            return 3;
                        }
                        if (p_151555_2_ == 3) {
                            return 2;
                        }
                    } else if (this.coordBaseMode == 1) {
                        if (p_151555_2_ == 2) {
                            return 4;
                        }
                        if (p_151555_2_ == 3) {
                            return 5;
                        }
                        if (p_151555_2_ == 4) {
                            return 2;
                        }
                        if (p_151555_2_ == 5) {
                            return 3;
                        }
                    } else if (this.coordBaseMode == 3) {
                        if (p_151555_2_ == 2) {
                            return 5;
                        }
                        if (p_151555_2_ == 3) {
                            return 4;
                        }
                        if (p_151555_2_ == 4) {
                            return 2;
                        }
                        if (p_151555_2_ == 5) {
                            return 3;
                        }
                    }
                } else if (p_151555_1_ == Blocks.stone_button) {
                    if (this.coordBaseMode == 0) {
                        if (p_151555_2_ == 3) {
                            return 4;
                        }
                        if (p_151555_2_ == 4) {
                            return 3;
                        }
                    } else if (this.coordBaseMode == 1) {
                        if (p_151555_2_ == 3) {
                            return 1;
                        }
                        if (p_151555_2_ == 4) {
                            return 2;
                        }
                        if (p_151555_2_ == 2) {
                            return 3;
                        }
                        if (p_151555_2_ == 1) {
                            return 4;
                        }
                    } else if (this.coordBaseMode == 3) {
                        if (p_151555_2_ == 3) {
                            return 2;
                        }
                        if (p_151555_2_ == 4) {
                            return 1;
                        }
                        if (p_151555_2_ == 2) {
                            return 3;
                        }
                        if (p_151555_2_ == 1) {
                            return 4;
                        }
                    }
                } else if (p_151555_1_ != Blocks.tripwire_hook && !(p_151555_1_ instanceof BlockDirectional)) {
                    if (p_151555_1_ == Blocks.piston || p_151555_1_ == Blocks.sticky_piston || p_151555_1_ == Blocks.lever || p_151555_1_ == Blocks.dispenser) {
                        if (this.coordBaseMode == 0) {
                            if (p_151555_2_ == 2 || p_151555_2_ == 3) {
                                return Facing.oppositeSide[p_151555_2_];
                            }
                        } else if (this.coordBaseMode == 1) {
                            if (p_151555_2_ == 2) {
                                return 4;
                            }
                            if (p_151555_2_ == 3) {
                                return 5;
                            }
                            if (p_151555_2_ == 4) {
                                return 2;
                            }
                            if (p_151555_2_ == 5) {
                                return 3;
                            }
                        } else if (this.coordBaseMode == 3) {
                            if (p_151555_2_ == 2) {
                                return 5;
                            }
                            if (p_151555_2_ == 3) {
                                return 4;
                            }
                            if (p_151555_2_ == 4) {
                                return 2;
                            }
                            if (p_151555_2_ == 5) {
                                return 3;
                            }
                        }
                    }
                } else if (this.coordBaseMode == 0) {
                    if (p_151555_2_ == 0 || p_151555_2_ == 2) {
                        return Direction.rotateOpposite[p_151555_2_];
                    }
                } else if (this.coordBaseMode == 1) {
                    if (p_151555_2_ == 2) {
                        return 1;
                    }
                    if (p_151555_2_ == 0) {
                        return 3;
                    }
                    if (p_151555_2_ == 1) {
                        return 2;
                    }
                    if (p_151555_2_ == 3) {
                        return 0;
                    }
                } else if (this.coordBaseMode == 3) {
                    if (p_151555_2_ == 2) {
                        return 3;
                    }
                    if (p_151555_2_ == 0) {
                        return 1;
                    }
                    if (p_151555_2_ == 1) {
                        return 2;
                    }
                    if (p_151555_2_ == 3) {
                        return 0;
                    }
                }
            } else if (this.coordBaseMode == 0) {
                if (p_151555_2_ == 2) {
                    return 3;
                }
                if (p_151555_2_ == 3) {
                    return 2;
                }
            } else if (this.coordBaseMode == 1) {
                if (p_151555_2_ == 0) {
                    return 2;
                }
                if (p_151555_2_ == 1) {
                    return 3;
                }
                if (p_151555_2_ == 2) {
                    return 0;
                }
                if (p_151555_2_ == 3) {
                    return 1;
                }
            } else if (this.coordBaseMode == 3) {
                if (p_151555_2_ == 0) {
                    return 2;
                }
                if (p_151555_2_ == 1) {
                    return 3;
                }
                if (p_151555_2_ == 2) {
                    return 1;
                }
                if (p_151555_2_ == 3) {
                    return 0;
                }
            }
        } else if (this.coordBaseMode == 0) {
            if (p_151555_2_ == 0) {
                return 2;
            }
            if (p_151555_2_ == 2) {
                return 0;
            }
        } else {
            if (this.coordBaseMode == 1) {
                return p_151555_2_ + 1 & 3;
            }
            if (this.coordBaseMode == 3) {
                return p_151555_2_ + 3 & 3;
            }
        }
        return p_151555_2_;
    }

    protected void func_151550_a(World p_151550_1_, Block p_151550_2_, int p_151550_3_, int p_151550_4_, int p_151550_5_, int p_151550_6_, StructureBoundingBox p_151550_7_) {
        int var9;
        int var10;
        int var8 = this.getXWithOffset(p_151550_4_, p_151550_6_);
        if (p_151550_7_.isVecInside(var8, var9 = this.getYWithOffset(p_151550_5_), var10 = this.getZWithOffset(p_151550_4_, p_151550_6_))) {
            p_151550_1_.setBlock(var8, var9, var10, p_151550_2_, p_151550_3_, 2);
        }
    }

    protected Block func_151548_a(World p_151548_1_, int p_151548_2_, int p_151548_3_, int p_151548_4_, StructureBoundingBox p_151548_5_) {
        int var8;
        int var7;
        int var6 = this.getXWithOffset(p_151548_2_, p_151548_4_);
        return !p_151548_5_.isVecInside(var6, var7 = this.getYWithOffset(p_151548_3_), var8 = this.getZWithOffset(p_151548_2_, p_151548_4_)) ? Blocks.air : p_151548_1_.getBlock(var6, var7, var8);
    }

    protected void fillWithAir(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8) {
        int var9 = par4;
        while (var9 <= par7) {
            int var10 = par3;
            while (var10 <= par6) {
                int var11 = par5;
                while (var11 <= par8) {
                    this.func_151550_a(par1World, Blocks.air, 0, var10, var9, var11, par2StructureBoundingBox);
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
    }

    protected void func_151549_a(World p_151549_1_, StructureBoundingBox p_151549_2_, int p_151549_3_, int p_151549_4_, int p_151549_5_, int p_151549_6_, int p_151549_7_, int p_151549_8_, Block p_151549_9_, Block p_151549_10_, boolean p_151549_11_) {
        int var12 = p_151549_4_;
        while (var12 <= p_151549_7_) {
            int var13 = p_151549_3_;
            while (var13 <= p_151549_6_) {
                int var14 = p_151549_5_;
                while (var14 <= p_151549_8_) {
                    if (!p_151549_11_ || this.func_151548_a(p_151549_1_, var13, var12, var14, p_151549_2_).getMaterial() != Material.air) {
                        if (var12 != p_151549_4_ && var12 != p_151549_7_ && var13 != p_151549_3_ && var13 != p_151549_6_ && var14 != p_151549_5_ && var14 != p_151549_8_) {
                            this.func_151550_a(p_151549_1_, p_151549_10_, 0, var13, var12, var14, p_151549_2_);
                        } else {
                            this.func_151550_a(p_151549_1_, p_151549_9_, 0, var13, var12, var14, p_151549_2_);
                        }
                    }
                    ++var14;
                }
                ++var13;
            }
            ++var12;
        }
    }

    protected void func_151556_a(World p_151556_1_, StructureBoundingBox p_151556_2_, int p_151556_3_, int p_151556_4_, int p_151556_5_, int p_151556_6_, int p_151556_7_, int p_151556_8_, Block p_151556_9_, int p_151556_10_, Block p_151556_11_, int p_151556_12_, boolean p_151556_13_) {
        int var14 = p_151556_4_;
        while (var14 <= p_151556_7_) {
            int var15 = p_151556_3_;
            while (var15 <= p_151556_6_) {
                int var16 = p_151556_5_;
                while (var16 <= p_151556_8_) {
                    if (!p_151556_13_ || this.func_151548_a(p_151556_1_, var15, var14, var16, p_151556_2_).getMaterial() != Material.air) {
                        if (var14 != p_151556_4_ && var14 != p_151556_7_ && var15 != p_151556_3_ && var15 != p_151556_6_ && var16 != p_151556_5_ && var16 != p_151556_8_) {
                            this.func_151550_a(p_151556_1_, p_151556_11_, p_151556_12_, var15, var14, var16, p_151556_2_);
                        } else {
                            this.func_151550_a(p_151556_1_, p_151556_9_, p_151556_10_, var15, var14, var16, p_151556_2_);
                        }
                    }
                    ++var16;
                }
                ++var15;
            }
            ++var14;
        }
    }

    protected void fillWithRandomizedBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, boolean par9, Random par10Random, BlockSelector par11StructurePieceBlockSelector) {
        int var12 = par4;
        while (var12 <= par7) {
            int var13 = par3;
            while (var13 <= par6) {
                int var14 = par5;
                while (var14 <= par8) {
                    if (!par9 || this.func_151548_a(par1World, var13, var12, var14, par2StructureBoundingBox).getMaterial() != Material.air) {
                        par11StructurePieceBlockSelector.selectBlocks(par10Random, var13, var12, var14, var12 == par4 || var12 == par7 || var13 == par3 || var13 == par6 || var14 == par5 || var14 == par8);
                        this.func_151550_a(par1World, par11StructurePieceBlockSelector.func_151561_a(), par11StructurePieceBlockSelector.getSelectedBlockMetaData(), var13, var12, var14, par2StructureBoundingBox);
                    }
                    ++var14;
                }
                ++var13;
            }
            ++var12;
        }
    }

    protected void func_151551_a(World p_151551_1_, StructureBoundingBox p_151551_2_, Random p_151551_3_, float p_151551_4_, int p_151551_5_, int p_151551_6_, int p_151551_7_, int p_151551_8_, int p_151551_9_, int p_151551_10_, Block p_151551_11_, Block p_151551_12_, boolean p_151551_13_) {
        int var14 = p_151551_6_;
        while (var14 <= p_151551_9_) {
            int var15 = p_151551_5_;
            while (var15 <= p_151551_8_) {
                int var16 = p_151551_7_;
                while (var16 <= p_151551_10_) {
                    if (!(p_151551_3_.nextFloat() > p_151551_4_ || p_151551_13_ && this.func_151548_a(p_151551_1_, var15, var14, var16, p_151551_2_).getMaterial() == Material.air)) {
                        if (var14 != p_151551_6_ && var14 != p_151551_9_ && var15 != p_151551_5_ && var15 != p_151551_8_ && var16 != p_151551_7_ && var16 != p_151551_10_) {
                            this.func_151550_a(p_151551_1_, p_151551_12_, 0, var15, var14, var16, p_151551_2_);
                        } else {
                            this.func_151550_a(p_151551_1_, p_151551_11_, 0, var15, var14, var16, p_151551_2_);
                        }
                    }
                    ++var16;
                }
                ++var15;
            }
            ++var14;
        }
    }

    protected void func_151552_a(World p_151552_1_, StructureBoundingBox p_151552_2_, Random p_151552_3_, float p_151552_4_, int p_151552_5_, int p_151552_6_, int p_151552_7_, Block p_151552_8_, int p_151552_9_) {
        if (p_151552_3_.nextFloat() < p_151552_4_) {
            this.func_151550_a(p_151552_1_, p_151552_8_, p_151552_9_, p_151552_5_, p_151552_6_, p_151552_7_, p_151552_2_);
        }
    }

    protected void func_151547_a(World p_151547_1_, StructureBoundingBox p_151547_2_, int p_151547_3_, int p_151547_4_, int p_151547_5_, int p_151547_6_, int p_151547_7_, int p_151547_8_, Block p_151547_9_, boolean p_151547_10_) {
        float var11 = p_151547_6_ - p_151547_3_ + 1;
        float var12 = p_151547_7_ - p_151547_4_ + 1;
        float var13 = p_151547_8_ - p_151547_5_ + 1;
        float var14 = (float)p_151547_3_ + var11 / 2.0f;
        float var15 = (float)p_151547_5_ + var13 / 2.0f;
        int var16 = p_151547_4_;
        while (var16 <= p_151547_7_) {
            float var17 = (float)(var16 - p_151547_4_) / var12;
            int var18 = p_151547_3_;
            while (var18 <= p_151547_6_) {
                float var19 = ((float)var18 - var14) / (var11 * 0.5f);
                int var20 = p_151547_5_;
                while (var20 <= p_151547_8_) {
                    float var22;
                    float var21 = ((float)var20 - var15) / (var13 * 0.5f);
                    if (!(p_151547_10_ && this.func_151548_a(p_151547_1_, var18, var16, var20, p_151547_2_).getMaterial() == Material.air || (var22 = var19 * var19 + var17 * var17 + var21 * var21) > 1.05f)) {
                        this.func_151550_a(p_151547_1_, p_151547_9_, 0, var18, var16, var20, p_151547_2_);
                    }
                    ++var20;
                }
                ++var18;
            }
            ++var16;
        }
    }

    protected void clearCurrentPositionBlocksUpwards(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox) {
        int var8;
        int var7;
        int var6 = this.getXWithOffset(par2, par4);
        if (par5StructureBoundingBox.isVecInside(var6, var7 = this.getYWithOffset(par3), var8 = this.getZWithOffset(par2, par4))) {
            while (!par1World.isAirBlock(var6, var7, var8) && var7 < 255) {
                par1World.setBlock(var6, var7, var8, Blocks.air, 0, 2);
                ++var7;
            }
        }
    }

    protected void func_151554_b(World p_151554_1_, Block p_151554_2_, int p_151554_3_, int p_151554_4_, int p_151554_5_, int p_151554_6_, StructureBoundingBox p_151554_7_) {
        int var9;
        int var10;
        int var8 = this.getXWithOffset(p_151554_4_, p_151554_6_);
        if (p_151554_7_.isVecInside(var8, var9 = this.getYWithOffset(p_151554_5_), var10 = this.getZWithOffset(p_151554_4_, p_151554_6_))) {
            while ((p_151554_1_.isAirBlock(var8, var9, var10) || p_151554_1_.getBlock(var8, var9, var10).getMaterial().isLiquid()) && var9 > 1) {
                p_151554_1_.setBlock(var8, var9, var10, p_151554_2_, p_151554_3_, 2);
                --var9;
            }
        }
    }

    protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8) {
        int var11;
        int var10;
        int var9 = this.getXWithOffset(par4, par6);
        if (par2StructureBoundingBox.isVecInside(var9, var10 = this.getYWithOffset(par5), var11 = this.getZWithOffset(par4, par6)) && par1World.getBlock(var9, var10, var11) != Blocks.chest) {
            par1World.setBlock(var9, var10, var11, Blocks.chest, 0, 2);
            TileEntityChest var12 = (TileEntityChest)par1World.getTileEntity(var9, var10, var11);
            if (var12 != null) {
                WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var12, par8);
            }
            return true;
        }
        return false;
    }

    protected boolean generateStructureDispenserContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7, WeightedRandomChestContent[] par8ArrayOfWeightedRandomChestContent, int par9) {
        int var11;
        int var12;
        int var10 = this.getXWithOffset(par4, par6);
        if (par2StructureBoundingBox.isVecInside(var10, var11 = this.getYWithOffset(par5), var12 = this.getZWithOffset(par4, par6)) && par1World.getBlock(var10, var11, var12) != Blocks.dispenser) {
            par1World.setBlock(var10, var11, var12, Blocks.dispenser, this.func_151555_a(Blocks.dispenser, par7), 2);
            TileEntityDispenser var13 = (TileEntityDispenser)par1World.getTileEntity(var10, var11, var12);
            if (var13 != null) {
                WeightedRandomChestContent.func_150706_a(par3Random, par8ArrayOfWeightedRandomChestContent, var13, par9);
            }
            return true;
        }
        return false;
    }

    protected void placeDoorAtCurrentPosition(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7) {
        int var9;
        int var10;
        int var8 = this.getXWithOffset(par4, par6);
        if (par2StructureBoundingBox.isVecInside(var8, var9 = this.getYWithOffset(par5), var10 = this.getZWithOffset(par4, par6))) {
            ItemDoor.func_150924_a(par1World, var8, var9, var10, par7, Blocks.wooden_door);
        }
    }

    public static abstract class BlockSelector {
        protected Block field_151562_a = Blocks.air;
        protected int selectedBlockMetaData;
        private static final String __OBFID = "CL_00000512";

        protected BlockSelector() {
        }

        public abstract void selectBlocks(Random var1, int var2, int var3, int var4, boolean var5);

        public Block func_151561_a() {
            return this.field_151562_a;
        }

        public int getSelectedBlockMetaData() {
            return this.selectedBlockMetaData;
        }
    }

}

