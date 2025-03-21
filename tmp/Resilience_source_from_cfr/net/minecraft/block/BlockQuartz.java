/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockQuartz
extends Block {
    public static final String[] field_150191_a = new String[]{"default", "chiseled", "lines"};
    private static final String[] field_150189_b;
    private IIcon[] field_150192_M;
    private IIcon field_150193_N;
    private IIcon field_150194_O;
    private IIcon field_150190_P;
    private IIcon field_150188_Q;
    private static final String __OBFID = "CL_00000292";

    static {
        String[] arrstring = new String[5];
        arrstring[0] = "side";
        arrstring[1] = "chiseled";
        arrstring[2] = "lines";
        field_150189_b = arrstring;
    }

    public BlockQuartz() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ != 2 && p_149691_2_ != 3 && p_149691_2_ != 4) {
            if (p_149691_1_ != 1 && (p_149691_1_ != 0 || p_149691_2_ != 1)) {
                if (p_149691_1_ == 0) {
                    return this.field_150188_Q;
                }
                if (p_149691_2_ < 0 || p_149691_2_ >= this.field_150192_M.length) {
                    p_149691_2_ = 0;
                }
                return this.field_150192_M[p_149691_2_];
            }
            return p_149691_2_ == 1 ? this.field_150193_N : this.field_150190_P;
        }
        return p_149691_2_ == 2 && (p_149691_1_ == 1 || p_149691_1_ == 0) ? this.field_150194_O : (p_149691_2_ == 3 && (p_149691_1_ == 5 || p_149691_1_ == 4) ? this.field_150194_O : (p_149691_2_ == 4 && (p_149691_1_ == 2 || p_149691_1_ == 3) ? this.field_150194_O : this.field_150192_M[p_149691_2_]));
    }

    @Override
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        if (p_149660_9_ == 2) {
            switch (p_149660_5_) {
                case 0: 
                case 1: {
                    p_149660_9_ = 2;
                    break;
                }
                case 2: 
                case 3: {
                    p_149660_9_ = 4;
                    break;
                }
                case 4: 
                case 5: {
                    p_149660_9_ = 3;
                }
            }
        }
        return p_149660_9_;
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_ != 3 && p_149692_1_ != 4 ? p_149692_1_ : 2;
    }

    @Override
    protected ItemStack createStackedBlock(int p_149644_1_) {
        return p_149644_1_ != 3 && p_149644_1_ != 4 ? super.createStackedBlock(p_149644_1_) : new ItemStack(Item.getItemFromBlock(this), 1, 2);
    }

    @Override
    public int getRenderType() {
        return 39;
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_150192_M = new IIcon[field_150189_b.length];
        int var2 = 0;
        while (var2 < this.field_150192_M.length) {
            this.field_150192_M[var2] = field_150189_b[var2] == null ? this.field_150192_M[var2 - 1] : p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + field_150189_b[var2]);
            ++var2;
        }
        this.field_150190_P = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "top");
        this.field_150193_N = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "chiseled_top");
        this.field_150194_O = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "lines_top");
        this.field_150188_Q = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "bottom");
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        return MapColor.field_151677_p;
    }
}

