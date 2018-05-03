/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStairs
extends Block {
    private static final int[][] field_150150_a;
    private final Block field_150149_b;
    private final int field_150151_M;
    private boolean field_150152_N;
    private int field_150153_O;
    private static final String __OBFID = "CL_00000314";

    static {
        int[][] arrarrn = new int[8][];
        arrarrn[0] = new int[]{2, 6};
        arrarrn[1] = new int[]{3, 7};
        arrarrn[2] = new int[]{2, 3};
        arrarrn[3] = new int[]{6, 7};
        int[] arrn = new int[2];
        arrn[1] = 4;
        arrarrn[4] = arrn;
        arrarrn[5] = new int[]{1, 5};
        int[] arrn2 = new int[2];
        arrn2[1] = 1;
        arrarrn[6] = arrn2;
        arrarrn[7] = new int[]{4, 5};
        field_150150_a = arrarrn;
    }

    protected BlockStairs(Block p_i45428_1_, int p_i45428_2_) {
        super(p_i45428_1_.blockMaterial);
        this.field_150149_b = p_i45428_1_;
        this.field_150151_M = p_i45428_2_;
        this.setHardness(p_i45428_1_.blockHardness);
        this.setResistance(p_i45428_1_.blockResistance / 3.0f);
        this.setStepSound(p_i45428_1_.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        if (this.field_150152_N) {
            this.setBlockBounds(0.5f * (float)(this.field_150153_O % 2), 0.5f * (float)(this.field_150153_O / 2 % 2), 0.5f * (float)(this.field_150153_O / 4 % 2), 0.5f + 0.5f * (float)(this.field_150153_O % 2), 0.5f + 0.5f * (float)(this.field_150153_O / 2 % 2), 0.5f + 0.5f * (float)(this.field_150153_O / 4 % 2));
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 10;
    }

    public void func_150147_e(IBlockAccess p_150147_1_, int p_150147_2_, int p_150147_3_, int p_150147_4_) {
        int var5 = p_150147_1_.getBlockMetadata(p_150147_2_, p_150147_3_, p_150147_4_);
        if ((var5 & 4) != 0) {
            this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }

    public static boolean func_150148_a(Block p_150148_0_) {
        return p_150148_0_ instanceof BlockStairs;
    }

    private boolean func_150146_f(IBlockAccess p_150146_1_, int p_150146_2_, int p_150146_3_, int p_150146_4_, int p_150146_5_) {
        Block var6 = p_150146_1_.getBlock(p_150146_2_, p_150146_3_, p_150146_4_);
        if (BlockStairs.func_150148_a(var6) && p_150146_1_.getBlockMetadata(p_150146_2_, p_150146_3_, p_150146_4_) == p_150146_5_) {
            return true;
        }
        return false;
    }

    public boolean func_150145_f(IBlockAccess p_150145_1_, int p_150145_2_, int p_150145_3_, int p_150145_4_) {
        int var5 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_);
        int var6 = var5 & 3;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if ((var5 & 4) != 0) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 1.0f;
        float var11 = 0.0f;
        float var12 = 0.5f;
        boolean var13 = true;
        if (var6 == 0) {
            var9 = 0.5f;
            var12 = 1.0f;
            Block var14 = p_150145_1_.getBlock(p_150145_2_ + 1, p_150145_3_, p_150145_4_);
            int var15 = p_150145_1_.getBlockMetadata(p_150145_2_ + 1, p_150145_3_, p_150145_4_);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                int var16 = var15 & 3;
                if (var16 == 3 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ + 1, var5)) {
                    var12 = 0.5f;
                    var13 = false;
                } else if (var16 == 2 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ - 1, var5)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        } else if (var6 == 1) {
            var10 = 0.5f;
            var12 = 1.0f;
            Block var14 = p_150145_1_.getBlock(p_150145_2_ - 1, p_150145_3_, p_150145_4_);
            int var15 = p_150145_1_.getBlockMetadata(p_150145_2_ - 1, p_150145_3_, p_150145_4_);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                int var16 = var15 & 3;
                if (var16 == 3 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ + 1, var5)) {
                    var12 = 0.5f;
                    var13 = false;
                } else if (var16 == 2 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ - 1, var5)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        } else if (var6 == 2) {
            var11 = 0.5f;
            var12 = 1.0f;
            Block var14 = p_150145_1_.getBlock(p_150145_2_, p_150145_3_, p_150145_4_ + 1);
            int var15 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_ + 1);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                int var16 = var15 & 3;
                if (var16 == 1 && !this.func_150146_f(p_150145_1_, p_150145_2_ + 1, p_150145_3_, p_150145_4_, var5)) {
                    var10 = 0.5f;
                    var13 = false;
                } else if (var16 == 0 && !this.func_150146_f(p_150145_1_, p_150145_2_ - 1, p_150145_3_, p_150145_4_, var5)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        } else if (var6 == 3) {
            Block var14 = p_150145_1_.getBlock(p_150145_2_, p_150145_3_, p_150145_4_ - 1);
            int var15 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_ - 1);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                int var16 = var15 & 3;
                if (var16 == 1 && !this.func_150146_f(p_150145_1_, p_150145_2_ + 1, p_150145_3_, p_150145_4_, var5)) {
                    var10 = 0.5f;
                    var13 = false;
                } else if (var16 == 0 && !this.func_150146_f(p_150145_1_, p_150145_2_ - 1, p_150145_3_, p_150145_4_, var5)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        return var13;
    }

    public boolean func_150144_g(IBlockAccess p_150144_1_, int p_150144_2_, int p_150144_3_, int p_150144_4_) {
        int var5 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_);
        int var6 = var5 & 3;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if ((var5 & 4) != 0) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 0.5f;
        float var11 = 0.5f;
        float var12 = 1.0f;
        boolean var13 = false;
        if (var6 == 0) {
            Block var14 = p_150144_1_.getBlock(p_150144_2_ - 1, p_150144_3_, p_150144_4_);
            int var15 = p_150144_1_.getBlockMetadata(p_150144_2_ - 1, p_150144_3_, p_150144_4_);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                int var16 = var15 & 3;
                if (var16 == 3 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ - 1, var5)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                } else if (var16 == 2 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ + 1, var5)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        } else if (var6 == 1) {
            Block var14 = p_150144_1_.getBlock(p_150144_2_ + 1, p_150144_3_, p_150144_4_);
            int var15 = p_150144_1_.getBlockMetadata(p_150144_2_ + 1, p_150144_3_, p_150144_4_);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                var9 = 0.5f;
                var10 = 1.0f;
                int var16 = var15 & 3;
                if (var16 == 3 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ - 1, var5)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                } else if (var16 == 2 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ + 1, var5)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        } else if (var6 == 2) {
            Block var14 = p_150144_1_.getBlock(p_150144_2_, p_150144_3_, p_150144_4_ - 1);
            int var15 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_ - 1);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                var11 = 0.0f;
                var12 = 0.5f;
                int var16 = var15 & 3;
                if (var16 == 1 && !this.func_150146_f(p_150144_1_, p_150144_2_ - 1, p_150144_3_, p_150144_4_, var5)) {
                    var13 = true;
                } else if (var16 == 0 && !this.func_150146_f(p_150144_1_, p_150144_2_ + 1, p_150144_3_, p_150144_4_, var5)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        } else if (var6 == 3) {
            Block var14 = p_150144_1_.getBlock(p_150144_2_, p_150144_3_, p_150144_4_ + 1);
            int var15 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_ + 1);
            if (BlockStairs.func_150148_a(var14) && (var5 & 4) == (var15 & 4)) {
                int var16 = var15 & 3;
                if (var16 == 1 && !this.func_150146_f(p_150144_1_, p_150144_2_ - 1, p_150144_3_, p_150144_4_, var5)) {
                    var13 = true;
                } else if (var16 == 0 && !this.func_150146_f(p_150144_1_, p_150144_2_ + 1, p_150144_3_, p_150144_4_, var5)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        if (var13) {
            this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        }
        return var13;
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        this.func_150147_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        boolean var8 = this.func_150145_f(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        if (var8 && this.func_150144_g(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_)) {
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        this.field_150149_b.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
    }

    @Override
    public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {
        this.field_150149_b.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }

    @Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
        this.field_150149_b.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
    }

    @Override
    public int getBlockBrightness(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
        return this.field_150149_b.getBlockBrightness(p_149677_1_, p_149677_2_, p_149677_3_, p_149677_4_);
    }

    @Override
    public float getExplosionResistance(Entity p_149638_1_) {
        return this.field_150149_b.getExplosionResistance(p_149638_1_);
    }

    @Override
    public int getRenderBlockPass() {
        return this.field_150149_b.getRenderBlockPass();
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return this.field_150149_b.getIcon(p_149691_1_, this.field_150151_M);
    }

    @Override
    public int func_149738_a(World p_149738_1_) {
        return this.field_150149_b.func_149738_a(p_149738_1_);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
        return this.field_150149_b.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    @Override
    public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_) {
        this.field_150149_b.velocityToAddToEntity(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_, p_149640_5_, p_149640_6_);
    }

    @Override
    public boolean isCollidable() {
        return this.field_150149_b.isCollidable();
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
        return this.field_150149_b.canCollideCheck(p_149678_1_, p_149678_2_);
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return this.field_150149_b.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        this.onNeighborBlockChange(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, Blocks.air);
        this.field_150149_b.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        this.field_150149_b.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    @Override
    public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_) {
        this.field_150149_b.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        this.field_150149_b.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        return this.field_150149_b.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, 0, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
        this.field_150149_b.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        return this.field_150149_b.getMapColor(this.field_150151_M);
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        int var8 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) & 4;
        if (var7 == 0) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2 | var8, 2);
        }
        if (var7 == 1) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 1 | var8, 2);
        }
        if (var7 == 2) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3 | var8, 2);
        }
        if (var7 == 3) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var8, 2);
        }
    }

    @Override
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        return p_149660_5_ != 0 && (p_149660_5_ == 1 || (double)p_149660_7_ <= 0.5) ? p_149660_9_ : p_149660_9_ | 4;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
        int var14;
        int var16;
        int var15;
        MovingObjectPosition[] var7 = new MovingObjectPosition[8];
        int var8 = p_149731_1_.getBlockMetadata(p_149731_2_, p_149731_3_, p_149731_4_);
        int var9 = var8 & 3;
        boolean var10 = (var8 & 4) == 4;
        int[] var11 = field_150150_a[var9 + (var10 ? 4 : 0)];
        this.field_150152_N = true;
        int var12 = 0;
        while (var12 < 8) {
            this.field_150153_O = var12;
            int[] var13 = var11;
            var14 = var11.length;
            var15 = 0;
            while (var15 < var14) {
                var16 = var13[var15];
                if (var16 == var12) {
                    // empty if block
                }
                ++var15;
            }
            var7[var12] = super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
            ++var12;
        }
        int[] var21 = var11;
        int var24 = var11.length;
        var14 = 0;
        while (var14 < var24) {
            var15 = var21[var14];
            var7[var15] = null;
            ++var14;
        }
        MovingObjectPosition var23 = null;
        double var22 = 0.0;
        MovingObjectPosition[] var25 = var7;
        var16 = var7.length;
        int var17 = 0;
        while (var17 < var16) {
            double var19;
            MovingObjectPosition var18 = var25[var17];
            if (var18 != null && (var19 = var18.hitVec.squareDistanceTo(p_149731_6_)) > var22) {
                var23 = var18;
                var22 = var19;
            }
            ++var17;
        }
        return var23;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }
}

