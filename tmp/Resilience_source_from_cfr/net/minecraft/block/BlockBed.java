/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockBed
extends BlockDirectional {
    public static final int[][] field_149981_a;
    private IIcon[] field_149980_b;
    private IIcon[] field_149982_M;
    private IIcon[] field_149983_N;
    private static final String __OBFID = "CL_00000198";

    static {
        int[][] arrarrn = new int[4][];
        int[] arrn = new int[2];
        arrn[1] = 1;
        arrarrn[0] = arrn;
        int[] arrn2 = new int[2];
        arrn2[0] = -1;
        arrarrn[1] = arrn2;
        int[] arrn3 = new int[2];
        arrn3[1] = -1;
        arrarrn[2] = arrn3;
        int[] arrn4 = new int[2];
        arrn4[0] = 1;
        arrarrn[3] = arrn4;
        field_149981_a = arrarrn;
    }

    public BlockBed() {
        super(Material.cloth);
        this.func_149978_e();
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        if (!BlockBed.func_149975_b(var10)) {
            int var11 = BlockBed.func_149895_l(var10);
            if (p_149727_1_.getBlock(p_149727_2_ += field_149981_a[var11][0], p_149727_3_, p_149727_4_ += field_149981_a[var11][1]) != this) {
                return true;
            }
            var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        }
        if (p_149727_1_.provider.canRespawnHere() && p_149727_1_.getBiomeGenForCoords(p_149727_2_, p_149727_4_) != BiomeGenBase.hell) {
            EntityPlayer.EnumStatus var19;
            if (BlockBed.func_149976_c(var10)) {
                EntityPlayer var20 = null;
                for (EntityPlayer var21 : p_149727_1_.playerEntities) {
                    if (!var21.isPlayerSleeping()) continue;
                    ChunkCoordinates var14 = var21.playerLocation;
                    if (var14.posX != p_149727_2_ || var14.posY != p_149727_3_ || var14.posZ != p_149727_4_) continue;
                    var20 = var21;
                }
                if (var20 != null) {
                    p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                    return true;
                }
                BlockBed.func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, false);
            }
            if ((var19 = p_149727_5_.sleepInBedAt(p_149727_2_, p_149727_3_, p_149727_4_)) == EntityPlayer.EnumStatus.OK) {
                BlockBed.func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, true);
                return true;
            }
            if (var19 == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
                p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
            } else if (var19 == EntityPlayer.EnumStatus.NOT_SAFE) {
                p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
            }
            return true;
        }
        double var18 = (double)p_149727_2_ + 0.5;
        double var13 = (double)p_149727_3_ + 0.5;
        double var15 = (double)p_149727_4_ + 0.5;
        p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
        int var17 = BlockBed.func_149895_l(var10);
        if (p_149727_1_.getBlock(p_149727_2_ += field_149981_a[var17][0], p_149727_3_, p_149727_4_ += field_149981_a[var17][1]) == this) {
            p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
            var18 = (var18 + (double)p_149727_2_ + 0.5) / 2.0;
            var13 = (var13 + (double)p_149727_3_ + 0.5) / 2.0;
            var15 = (var15 + (double)p_149727_4_ + 0.5) / 2.0;
        }
        p_149727_1_.newExplosion(null, (float)p_149727_2_ + 0.5f, (float)p_149727_3_ + 0.5f, (float)p_149727_4_ + 0.5f, 5.0f, true, true);
        return true;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        int var5;
        if (p_149691_1_ == 0) {
            return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
        }
        int var3 = BlockBed.func_149895_l(p_149691_2_);
        int var4 = Direction.bedDirection[var3][p_149691_1_];
        int n = var5 = BlockBed.func_149975_b(p_149691_2_) ? 1 : 0;
        return !(var5 == 1 && var4 == 2 || var5 == 0 && var4 == 3) ? (var4 != 5 && var4 != 4 ? this.field_149983_N[var5] : this.field_149982_M[var5]) : this.field_149980_b[var5];
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149983_N = new IIcon[]{p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_feet_top"), p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_head_top")};
        this.field_149980_b = new IIcon[]{p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_feet_end"), p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_head_end")};
        this.field_149982_M = new IIcon[]{p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_feet_side"), p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_head_side")};
    }

    @Override
    public int getRenderType() {
        return 14;
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
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.func_149978_e();
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        int var7 = BlockBed.func_149895_l(var6);
        if (BlockBed.func_149975_b(var6)) {
            if (p_149695_1_.getBlock(p_149695_2_ - field_149981_a[var7][0], p_149695_3_, p_149695_4_ - field_149981_a[var7][1]) != this) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        } else if (p_149695_1_.getBlock(p_149695_2_ + field_149981_a[var7][0], p_149695_3_, p_149695_4_ + field_149981_a[var7][1]) != this) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            if (!p_149695_1_.isClient) {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
            }
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return BlockBed.func_149975_b(p_149650_1_) ? Item.getItemById(0) : Items.bed;
    }

    private void func_149978_e() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }

    public static boolean func_149975_b(int p_149975_0_) {
        if ((p_149975_0_ & 8) != 0) {
            return true;
        }
        return false;
    }

    public static boolean func_149976_c(int p_149976_0_) {
        if ((p_149976_0_ & 4) != 0) {
            return true;
        }
        return false;
    }

    public static void func_149979_a(World p_149979_0_, int p_149979_1_, int p_149979_2_, int p_149979_3_, boolean p_149979_4_) {
        int var5 = p_149979_0_.getBlockMetadata(p_149979_1_, p_149979_2_, p_149979_3_);
        var5 = p_149979_4_ ? (var5 |= 4) : (var5 &= -5);
        p_149979_0_.setBlockMetadataWithNotify(p_149979_1_, p_149979_2_, p_149979_3_, var5, 4);
    }

    public static ChunkCoordinates func_149977_a(World p_149977_0_, int p_149977_1_, int p_149977_2_, int p_149977_3_, int p_149977_4_) {
        int var5 = p_149977_0_.getBlockMetadata(p_149977_1_, p_149977_2_, p_149977_3_);
        int var6 = BlockDirectional.func_149895_l(var5);
        int var7 = 0;
        while (var7 <= 1) {
            int var8 = p_149977_1_ - field_149981_a[var6][0] * var7 - 1;
            int var9 = p_149977_3_ - field_149981_a[var6][1] * var7 - 1;
            int var10 = var8 + 2;
            int var11 = var9 + 2;
            int var12 = var8;
            while (var12 <= var10) {
                int var13 = var9;
                while (var13 <= var11) {
                    if (World.doesBlockHaveSolidTopSurface(p_149977_0_, var12, p_149977_2_ - 1, var13) && !p_149977_0_.getBlock(var12, p_149977_2_, var13).getMaterial().isOpaque() && !p_149977_0_.getBlock(var12, p_149977_2_ + 1, var13).getMaterial().isOpaque()) {
                        if (p_149977_4_ <= 0) {
                            return new ChunkCoordinates(var12, p_149977_2_, var13);
                        }
                        --p_149977_4_;
                    }
                    ++var13;
                }
                ++var12;
            }
            ++var7;
        }
        return null;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        if (!BlockBed.func_149975_b(p_149690_5_)) {
            super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        }
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.bed;
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        int var7;
        if (p_149681_6_.capabilities.isCreativeMode && BlockBed.func_149975_b(p_149681_5_) && p_149681_1_.getBlock(p_149681_2_ -= field_149981_a[var7 = BlockBed.func_149895_l(p_149681_5_)][0], p_149681_3_, p_149681_4_ -= field_149981_a[var7][1]) == this) {
            p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_, p_149681_4_);
        }
    }
}

