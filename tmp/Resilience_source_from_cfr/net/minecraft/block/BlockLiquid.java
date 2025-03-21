/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BlockLiquid
extends Block {
    private IIcon[] field_149806_a;
    private static final String __OBFID = "CL_00000265";

    protected BlockLiquid(Material p_i45413_1_) {
        super(p_i45413_1_);
        float var2 = 0.0f;
        float var3 = 0.0f;
        this.setBlockBounds(0.0f + var3, 0.0f + var2, 0.0f + var3, 1.0f + var3, 1.0f + var2, 1.0f + var3);
        this.setTickRandomly(true);
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        if (this.blockMaterial != Material.lava) {
            return true;
        }
        return false;
    }

    @Override
    public int getBlockColor() {
        return 16777215;
    }

    @Override
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        if (this.blockMaterial != Material.water) {
            return 16777215;
        }
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = -1;
        while (var8 <= 1) {
            int var9 = -1;
            while (var9 <= 1) {
                int var10 = p_149720_1_.getBiomeGenForCoords((int)(p_149720_2_ + var9), (int)(p_149720_4_ + var8)).waterColorMultiplier;
                var5 += (var10 & 16711680) >> 16;
                var6 += (var10 & 65280) >> 8;
                var7 += var10 & 255;
                ++var9;
            }
            ++var8;
        }
        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
    }

    public static float func_149801_b(int p_149801_0_) {
        if (p_149801_0_ >= 8) {
            p_149801_0_ = 0;
        }
        return (float)(p_149801_0_ + 1) / 9.0f;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ != 0 && p_149691_1_ != 1 ? this.field_149806_a[1] : this.field_149806_a[0];
    }

    protected int func_149804_e(World p_149804_1_, int p_149804_2_, int p_149804_3_, int p_149804_4_) {
        return p_149804_1_.getBlock(p_149804_2_, p_149804_3_, p_149804_4_).getMaterial() == this.blockMaterial ? p_149804_1_.getBlockMetadata(p_149804_2_, p_149804_3_, p_149804_4_) : -1;
    }

    protected int func_149798_e(IBlockAccess p_149798_1_, int p_149798_2_, int p_149798_3_, int p_149798_4_) {
        if (p_149798_1_.getBlock(p_149798_2_, p_149798_3_, p_149798_4_).getMaterial() != this.blockMaterial) {
            return -1;
        }
        int var5 = p_149798_1_.getBlockMetadata(p_149798_2_, p_149798_3_, p_149798_4_);
        if (var5 >= 8) {
            var5 = 0;
        }
        return var5;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
        if (p_149678_2_ && p_149678_1_ == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        Material var6 = p_149747_1_.getBlock(p_149747_2_, p_149747_3_, p_149747_4_).getMaterial();
        return var6 == this.blockMaterial ? false : (p_149747_5_ == 1 ? true : (var6 == Material.ice ? false : super.isBlockSolid(p_149747_1_, p_149747_2_, p_149747_3_, p_149747_4_, p_149747_5_)));
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        Material var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_).getMaterial();
        return var6 == this.blockMaterial ? false : (p_149646_5_ == 1 ? true : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return Resilience.getInstance().getValues().jesusEnabled ? AxisAlignedBB.getAABBPool().getAABB((double)p_149668_2_ + this.field_149759_B, (double)p_149668_3_ + this.field_149760_C, (double)p_149668_4_ + this.field_149754_D, (double)p_149668_2_ + this.field_149755_E, (double)p_149668_3_ + this.field_149756_F, (double)p_149668_4_ + this.field_149757_G) : null;
    }

    @Override
    public int getRenderType() {
        return 4;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    private Vec3 func_149800_f(IBlockAccess p_149800_1_, int p_149800_2_, int p_149800_3_, int p_149800_4_) {
        Vec3 var5 = p_149800_1_.getWorldVec3Pool().getVecFromPool(0.0, 0.0, 0.0);
        int var6 = this.func_149798_e(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_);
        int var7 = 0;
        while (var7 < 4) {
            int var11;
            int var12;
            int var8 = p_149800_2_;
            int var10 = p_149800_4_;
            if (var7 == 0) {
                var8 = p_149800_2_ - 1;
            }
            if (var7 == 1) {
                var10 = p_149800_4_ - 1;
            }
            if (var7 == 2) {
                ++var8;
            }
            if (var7 == 3) {
                ++var10;
            }
            if ((var11 = this.func_149798_e(p_149800_1_, var8, p_149800_3_, var10)) < 0) {
                if (!p_149800_1_.getBlock(var8, p_149800_3_, var10).getMaterial().blocksMovement() && (var11 = this.func_149798_e(p_149800_1_, var8, p_149800_3_ - 1, var10)) >= 0) {
                    var12 = var11 - (var6 - 8);
                    var5 = var5.addVector((var8 - p_149800_2_) * var12, (p_149800_3_ - p_149800_3_) * var12, (var10 - p_149800_4_) * var12);
                }
            } else if (var11 >= 0) {
                var12 = var11 - var6;
                var5 = var5.addVector((var8 - p_149800_2_) * var12, (p_149800_3_ - p_149800_3_) * var12, (var10 - p_149800_4_) * var12);
            }
            ++var7;
        }
        if (p_149800_1_.getBlockMetadata(p_149800_2_, p_149800_3_, p_149800_4_) >= 8) {
            boolean var13 = false;
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_ - 1, 2)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_ + 1, 3)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_ - 1, p_149800_3_, p_149800_4_, 4)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_ + 1, p_149800_3_, p_149800_4_, 5)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_ + 1, p_149800_4_ - 1, 2)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_ + 1, p_149800_4_ + 1, 3)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_ - 1, p_149800_3_ + 1, p_149800_4_, 4)) {
                var13 = true;
            }
            if (var13 || this.isBlockSolid(p_149800_1_, p_149800_2_ + 1, p_149800_3_ + 1, p_149800_4_, 5)) {
                var13 = true;
            }
            if (var13) {
                var5 = var5.normalize().addVector(0.0, -6.0, 0.0);
            }
        }
        var5 = var5.normalize();
        return var5;
    }

    @Override
    public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_) {
        Vec3 var7 = this.func_149800_f(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_);
        p_149640_6_.xCoord += var7.xCoord;
        p_149640_6_.yCoord += var7.yCoord;
        p_149640_6_.zCoord += var7.zCoord;
    }

    @Override
    public int func_149738_a(World p_149738_1_) {
        return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? (p_149738_1_.provider.hasNoSky ? 10 : 30) : 0);
    }

    @Override
    public int getBlockBrightness(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
        int var5 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 0);
        int var6 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_ + 1, p_149677_4_, 0);
        int var7 = var5 & 255;
        int var8 = var6 & 255;
        int var9 = var5 >> 16 & 255;
        int var10 = var6 >> 16 & 255;
        return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
    }

    @Override
    public int getRenderBlockPass() {
        return this.blockMaterial == Material.water ? 1 : 0;
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        int var6;
        double var22;
        if (this.blockMaterial == Material.water) {
            if (p_149734_5_.nextInt(10) == 0 && ((var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_)) <= 0 || var6 >= 8)) {
                p_149734_1_.spawnParticle("suspended", (float)p_149734_2_ + p_149734_5_.nextFloat(), (float)p_149734_3_ + p_149734_5_.nextFloat(), (float)p_149734_4_ + p_149734_5_.nextFloat(), 0.0, 0.0, 0.0);
            }
            var6 = 0;
            while (var6 < 0) {
                int var7 = p_149734_5_.nextInt(4);
                int var8 = p_149734_2_;
                int var9 = p_149734_4_;
                if (var7 == 0) {
                    var8 = p_149734_2_ - 1;
                }
                if (var7 == 1) {
                    ++var8;
                }
                if (var7 == 2) {
                    var9 = p_149734_4_ - 1;
                }
                if (var7 == 3) {
                    ++var9;
                }
                if (p_149734_1_.getBlock(var8, p_149734_3_, var9).getMaterial() == Material.air && (p_149734_1_.getBlock(var8, p_149734_3_ - 1, var9).getMaterial().blocksMovement() || p_149734_1_.getBlock(var8, p_149734_3_ - 1, var9).getMaterial().isLiquid())) {
                    float var10 = 0.0625f;
                    double var11 = (float)p_149734_2_ + p_149734_5_.nextFloat();
                    double var13 = (float)p_149734_3_ + p_149734_5_.nextFloat();
                    double var15 = (float)p_149734_4_ + p_149734_5_.nextFloat();
                    if (var7 == 0) {
                        var11 = (float)p_149734_2_ - var10;
                    }
                    if (var7 == 1) {
                        var11 = (float)(p_149734_2_ + 1) + var10;
                    }
                    if (var7 == 2) {
                        var15 = (float)p_149734_4_ - var10;
                    }
                    if (var7 == 3) {
                        var15 = (float)(p_149734_4_ + 1) + var10;
                    }
                    double var17 = 0.0;
                    double var19 = 0.0;
                    if (var7 == 0) {
                        var17 = - var10;
                    }
                    if (var7 == 1) {
                        var17 = var10;
                    }
                    if (var7 == 2) {
                        var19 = - var10;
                    }
                    if (var7 == 3) {
                        var19 = var10;
                    }
                    p_149734_1_.spawnParticle("splash", var11, var13, var15, var17, 0.0, var19);
                }
                ++var6;
            }
        }
        if (this.blockMaterial == Material.water && p_149734_5_.nextInt(64) == 0 && (var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_)) > 0 && var6 < 8) {
            p_149734_1_.playSound((float)p_149734_2_ + 0.5f, (float)p_149734_3_ + 0.5f, (float)p_149734_4_ + 0.5f, "liquid.water", p_149734_5_.nextFloat() * 0.25f + 0.75f, p_149734_5_.nextFloat() * 1.0f + 0.5f, false);
        }
        if (this.blockMaterial == Material.lava && p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).getMaterial() == Material.air && !p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).isOpaqueCube()) {
            if (p_149734_5_.nextInt(100) == 0) {
                double var21 = (float)p_149734_2_ + p_149734_5_.nextFloat();
                var22 = (double)p_149734_3_ + this.field_149756_F;
                double var23 = (float)p_149734_4_ + p_149734_5_.nextFloat();
                p_149734_1_.spawnParticle("lava", var21, var22, var23, 0.0, 0.0, 0.0);
                p_149734_1_.playSound(var21, var22, var23, "liquid.lavapop", 0.2f + p_149734_5_.nextFloat() * 0.2f, 0.9f + p_149734_5_.nextFloat() * 0.15f, false);
            }
            if (p_149734_5_.nextInt(200) == 0) {
                p_149734_1_.playSound(p_149734_2_, p_149734_3_, p_149734_4_, "liquid.lava", 0.2f + p_149734_5_.nextFloat() * 0.2f, 0.9f + p_149734_5_.nextFloat() * 0.15f, false);
            }
        }
        if (p_149734_5_.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && !p_149734_1_.getBlock(p_149734_2_, p_149734_3_ - 2, p_149734_4_).getMaterial().blocksMovement()) {
            double var21 = (float)p_149734_2_ + p_149734_5_.nextFloat();
            var22 = (double)p_149734_3_ - 1.05;
            double var23 = (float)p_149734_4_ + p_149734_5_.nextFloat();
            if (this.blockMaterial == Material.water) {
                p_149734_1_.spawnParticle("dripWater", var21, var22, var23, 0.0, 0.0, 0.0);
            } else {
                p_149734_1_.spawnParticle("dripLava", var21, var22, var23, 0.0, 0.0, 0.0);
            }
        }
    }

    public static double func_149802_a(IBlockAccess p_149802_0_, int p_149802_1_, int p_149802_2_, int p_149802_3_, Material p_149802_4_) {
        Vec3 var5 = null;
        if (p_149802_4_ == Material.water) {
            var5 = Blocks.flowing_water.func_149800_f(p_149802_0_, p_149802_1_, p_149802_2_, p_149802_3_);
        }
        if (p_149802_4_ == Material.lava) {
            var5 = Blocks.flowing_lava.func_149800_f(p_149802_0_, p_149802_1_, p_149802_2_, p_149802_3_);
        }
        return var5.xCoord == 0.0 && var5.zCoord == 0.0 ? -1000.0 : Math.atan2(var5.zCoord, var5.xCoord) - 1.5707963267948966;
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        this.func_149805_n(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        this.func_149805_n(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    private void func_149805_n(World p_149805_1_, int p_149805_2_, int p_149805_3_, int p_149805_4_) {
        if (p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_) == this && this.blockMaterial == Material.lava) {
            boolean var5 = false;
            if (var5 || p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_ - 1).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_ + 1).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_ - 1, p_149805_3_, p_149805_4_).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_ + 1, p_149805_3_, p_149805_4_).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_, p_149805_3_ + 1, p_149805_4_).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5) {
                int var6 = p_149805_1_.getBlockMetadata(p_149805_2_, p_149805_3_, p_149805_4_);
                if (var6 == 0) {
                    p_149805_1_.setBlock(p_149805_2_, p_149805_3_, p_149805_4_, Blocks.obsidian);
                } else if (var6 <= 4) {
                    p_149805_1_.setBlock(p_149805_2_, p_149805_3_, p_149805_4_, Blocks.cobblestone);
                }
                this.func_149799_m(p_149805_1_, p_149805_2_, p_149805_3_, p_149805_4_);
            }
        }
    }

    protected void func_149799_m(World p_149799_1_, int p_149799_2_, int p_149799_3_, int p_149799_4_) {
        p_149799_1_.playSoundEffect((float)p_149799_2_ + 0.5f, (float)p_149799_3_ + 0.5f, (float)p_149799_4_ + 0.5f, "random.fizz", 0.5f, 2.6f + (p_149799_1_.rand.nextFloat() - p_149799_1_.rand.nextFloat()) * 0.8f);
        int var5 = 0;
        while (var5 < 8) {
            p_149799_1_.spawnParticle("largesmoke", (double)p_149799_2_ + Math.random(), (double)p_149799_3_ + 1.2, (double)p_149799_4_ + Math.random(), 0.0, 0.0, 0.0);
            ++var5;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149806_a = this.blockMaterial == Material.lava ? new IIcon[]{p_149651_1_.registerIcon("lava_still"), p_149651_1_.registerIcon("lava_flow")} : new IIcon[]{p_149651_1_.registerIcon("water_still"), p_149651_1_.registerIcon("water_flow")};
    }

    public static IIcon func_149803_e(String p_149803_0_) {
        return p_149803_0_ == "water_still" ? Blocks.flowing_water.field_149806_a[0] : (p_149803_0_ == "water_flow" ? Blocks.flowing_water.field_149806_a[1] : (p_149803_0_ == "lava_still" ? Blocks.flowing_lava.field_149806_a[0] : (p_149803_0_ == "lava_flow" ? Blocks.flowing_lava.field_149806_a[1] : null)));
    }
}

