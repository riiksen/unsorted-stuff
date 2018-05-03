/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPumpkin
extends BlockDirectional {
    private boolean field_149985_a;
    private IIcon field_149984_b;
    private IIcon field_149986_M;
    private static final String __OBFID = "CL_00000291";

    protected BlockPumpkin(boolean p_i45419_1_) {
        super(Material.field_151572_C);
        this.setTickRandomly(true);
        this.field_149985_a = p_i45419_1_;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.field_149984_b : (p_149691_1_ == 0 ? this.field_149984_b : (p_149691_2_ == 2 && p_149691_1_ == 2 ? this.field_149986_M : (p_149691_2_ == 3 && p_149691_1_ == 5 ? this.field_149986_M : (p_149691_2_ == 0 && p_149691_1_ == 3 ? this.field_149986_M : (p_149691_2_ == 1 && p_149691_1_ == 4 ? this.field_149986_M : this.blockIcon)))));
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.snow && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.snow) {
            if (!p_149726_1_.isClient) {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                EntitySnowman var9 = new EntitySnowman(p_149726_1_);
                var9.setLocationAndAngles((double)p_149726_2_ + 0.5, (double)p_149726_3_ - 1.95, (double)p_149726_4_ + 0.5, 0.0f, 0.0f);
                p_149726_1_.spawnEntityInWorld(var9);
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, BlockPumpkin.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, BlockPumpkin.getBlockById(0));
            }
            int var10 = 0;
            while (var10 < 120) {
                p_149726_1_.spawnParticle("snowshovel", (double)p_149726_2_ + p_149726_1_.rand.nextDouble(), (double)(p_149726_3_ - 2) + p_149726_1_.rand.nextDouble() * 2.5, (double)p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                ++var10;
            }
        } else if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.iron_block) {
            boolean var6;
            boolean var5 = p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block;
            boolean bl = var6 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1) == Blocks.iron_block;
            if (var5 || var6) {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                if (var5) {
                    p_149726_1_.setBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                    p_149726_1_.setBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0), 0, 2);
                } else {
                    p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, BlockPumpkin.getBlockById(0), 0, 2);
                    p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, BlockPumpkin.getBlockById(0), 0, 2);
                }
                EntityIronGolem var7 = new EntityIronGolem(p_149726_1_);
                var7.setPlayerCreated(true);
                var7.setLocationAndAngles((double)p_149726_2_ + 0.5, (double)p_149726_3_ - 1.95, (double)p_149726_4_ + 0.5, 0.0f, 0.0f);
                p_149726_1_.spawnEntityInWorld(var7);
                int var8 = 0;
                while (var8 < 120) {
                    p_149726_1_.spawnParticle("snowballpoof", (double)p_149726_2_ + p_149726_1_.rand.nextDouble(), (double)(p_149726_3_ - 2) + p_149726_1_.rand.nextDouble() * 3.9, (double)p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                    ++var8;
                }
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, BlockPumpkin.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, BlockPumpkin.getBlockById(0));
                if (var5) {
                    p_149726_1_.notifyBlockChange(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0));
                    p_149726_1_.notifyBlockChange(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, BlockPumpkin.getBlockById(0));
                } else {
                    p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, BlockPumpkin.getBlockById(0));
                    p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, BlockPumpkin.getBlockById(0));
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        if (p_149742_1_.getBlock((int)p_149742_2_, (int)p_149742_3_, (int)p_149742_4_).blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_)) {
            return true;
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 2.5) & 3;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_149986_M = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_face_" + (this.field_149985_a ? "on" : "off"));
        this.field_149984_b = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_top");
        this.blockIcon = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_side");
    }
}

