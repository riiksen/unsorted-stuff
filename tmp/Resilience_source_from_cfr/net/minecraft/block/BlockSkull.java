/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSkull
extends BlockContainer {
    private static final String __OBFID = "CL_00000307";

    protected BlockSkull() {
        super(Material.circuits);
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }

    @Override
    public int getRenderType() {
        return -1;
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
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 7;
        switch (var5) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 2.5) & 3;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntitySkull();
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.skull;
    }

    @Override
    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        TileEntity var5 = p_149643_1_.getTileEntity(p_149643_2_, p_149643_3_, p_149643_4_);
        return var5 != null && var5 instanceof TileEntitySkull ? ((TileEntitySkull)var5).func_145904_a() : super.getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        if (p_149681_6_.capabilities.isCreativeMode) {
            p_149681_1_.setBlockMetadataWithNotify(p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_ |= 8, 4);
        }
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        if (!p_149749_1_.isClient) {
            if ((p_149749_6_ & 8) == 0) {
                ItemStack var7 = new ItemStack(Items.skull, 1, this.getDamageValue(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_));
                TileEntitySkull var8 = (TileEntitySkull)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
                if (var8.func_145904_a() == 3 && var8.func_145907_c() != null && var8.func_145907_c().length() > 0) {
                    var7.setTagCompound(new NBTTagCompound());
                    var7.getTagCompound().setString("SkullOwner", var8.func_145907_c());
                }
                this.dropBlockAsItem_do(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, var7);
            }
            super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.skull;
    }

    public void func_149965_a(World p_149965_1_, int p_149965_2_, int p_149965_3_, int p_149965_4_, TileEntitySkull p_149965_5_) {
        if (p_149965_5_.func_145904_a() == 1 && p_149965_3_ >= 2 && p_149965_1_.difficultySetting != EnumDifficulty.PEACEFUL && !p_149965_1_.isClient) {
            int var6 = -2;
            while (var6 <= 0) {
                if (p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2) == Blocks.soul_sand && this.func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6, 1) && this.func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, 1) && this.func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, 1)) {
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, 8, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1, BlockSkull.getBlockById(0), 0, 2);
                    if (!p_149965_1_.isClient) {
                        EntityWither var7 = new EntityWither(p_149965_1_);
                        var7.setLocationAndAngles((double)p_149965_2_ + 0.5, (double)p_149965_3_ - 1.45, (double)(p_149965_4_ + var6) + 1.5, 90.0f, 0.0f);
                        var7.renderYawOffset = 90.0f;
                        var7.func_82206_m();
                        if (!p_149965_1_.isClient) {
                            for (EntityPlayer var9 : p_149965_1_.getEntitiesWithinAABB(EntityPlayer.class, var7.boundingBox.expand(50.0, 50.0, 50.0))) {
                                var9.triggerAchievement(AchievementList.field_150963_I);
                            }
                        }
                        p_149965_1_.spawnEntityInWorld(var7);
                    }
                    int var10 = 0;
                    while (var10 < 120) {
                        p_149965_1_.spawnParticle("snowballpoof", (double)p_149965_2_ + p_149965_1_.rand.nextDouble(), (double)(p_149965_3_ - 2) + p_149965_1_.rand.nextDouble() * 3.9, (double)(p_149965_4_ + var6 + 1) + p_149965_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                        ++var10;
                    }
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1, BlockSkull.getBlockById(0));
                    return;
                }
                ++var6;
            }
            var6 = -2;
            while (var6 <= 0) {
                if (p_149965_1_.getBlock(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand && this.func_149966_a(p_149965_1_, p_149965_2_ + var6, p_149965_3_, p_149965_4_, 1) && this.func_149966_a(p_149965_1_, p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, 1) && this.func_149966_a(p_149965_1_, p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, 1)) {
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6, p_149965_3_, p_149965_4_, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, 8, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6, p_149965_3_, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_, BlockSkull.getBlockById(0), 0, 2);
                    if (!p_149965_1_.isClient) {
                        EntityWither var7 = new EntityWither(p_149965_1_);
                        var7.setLocationAndAngles((double)(p_149965_2_ + var6) + 1.5, (double)p_149965_3_ - 1.45, (double)p_149965_4_ + 0.5, 0.0f, 0.0f);
                        var7.func_82206_m();
                        if (!p_149965_1_.isClient) {
                            for (EntityPlayer var9 : p_149965_1_.getEntitiesWithinAABB(EntityPlayer.class, var7.boundingBox.expand(50.0, 50.0, 50.0))) {
                                var9.triggerAchievement(AchievementList.field_150963_I);
                            }
                        }
                        p_149965_1_.spawnEntityInWorld(var7);
                    }
                    int var10 = 0;
                    while (var10 < 120) {
                        p_149965_1_.spawnParticle("snowballpoof", (double)(p_149965_2_ + var6 + 1) + p_149965_1_.rand.nextDouble(), (double)(p_149965_3_ - 2) + p_149965_1_.rand.nextDouble() * 3.9, (double)p_149965_4_ + p_149965_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                        ++var10;
                    }
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6, p_149965_3_, p_149965_4_, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_, BlockSkull.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_, BlockSkull.getBlockById(0));
                    return;
                }
                ++var6;
            }
        }
    }

    private boolean func_149966_a(World p_149966_1_, int p_149966_2_, int p_149966_3_, int p_149966_4_, int p_149966_5_) {
        if (p_149966_1_.getBlock(p_149966_2_, p_149966_3_, p_149966_4_) != this) {
            return false;
        }
        TileEntity var6 = p_149966_1_.getTileEntity(p_149966_2_, p_149966_3_, p_149966_4_);
        return var6 != null && var6 instanceof TileEntitySkull ? ((TileEntitySkull)var6).func_145904_a() == p_149966_5_ : false;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.soul_sand.getBlockTextureFromSide(p_149691_1_);
    }

    @Override
    public String getItemIconName() {
        return String.valueOf(this.getTextureName()) + "_" + ItemSkull.field_94587_a[0];
    }
}

