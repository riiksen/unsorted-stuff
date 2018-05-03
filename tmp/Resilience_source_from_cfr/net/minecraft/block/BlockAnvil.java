/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAnvil
extends BlockFalling {
    public static final String[] field_149834_a = new String[]{"intact", "slightlyDamaged", "veryDamaged"};
    private static final String[] field_149835_N = new String[]{"anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2"};
    public int field_149833_b;
    private IIcon[] field_149836_O;
    private static final String __OBFID = "CL_00000192";

    protected BlockAnvil() {
        super(Material.anvil);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
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
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (this.field_149833_b == 3 && p_149691_1_ == 1) {
            int var3 = (p_149691_2_ >> 2) % this.field_149836_O.length;
            return this.field_149836_O[var3];
        }
        return this.blockIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("anvil_base");
        this.field_149836_O = new IIcon[field_149835_N.length];
        int var2 = 0;
        while (var2 < this.field_149836_O.length) {
            this.field_149836_O[var2] = p_149651_1_.registerIcon(field_149835_N[var2]);
            ++var2;
        }
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        int var7 = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        int var8 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) >> 2;
        ++var7;
        if ((var7 %= 4) == 0) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2 | var8 << 2, 2);
        }
        if (var7 == 1) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3 | var8 << 2, 2);
        }
        if (var7 == 2) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var8 << 2, 2);
        }
        if (var7 == 3) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 1 | var8 << 2, 2);
        }
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        p_149727_5_.displayGUIAnvil(p_149727_2_, p_149727_3_, p_149727_4_);
        return true;
    }

    @Override
    public int getRenderType() {
        return 35;
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_ >> 2;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 3;
        if (var5 != 3 && var5 != 1) {
            this.setBlockBounds(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
    }

    @Override
    protected void func_149829_a(EntityFallingBlock p_149829_1_) {
        p_149829_1_.func_145806_a(true);
    }

    @Override
    public void func_149828_a(World p_149828_1_, int p_149828_2_, int p_149828_3_, int p_149828_4_, int p_149828_5_) {
        p_149828_1_.playAuxSFX(1022, p_149828_2_, p_149828_3_, p_149828_4_, 0);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return true;
    }
}

