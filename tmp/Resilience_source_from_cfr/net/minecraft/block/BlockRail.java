/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockRail
extends BlockRailBase {
    private IIcon field_150056_b;
    private static final String __OBFID = "CL_00000293";

    protected BlockRail() {
        super(false);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_2_ >= 6 ? this.field_150056_b : this.blockIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        this.field_150056_b = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_turned");
    }

    @Override
    protected void func_150048_a(World p_150048_1_, int p_150048_2_, int p_150048_3_, int p_150048_4_, int p_150048_5_, int p_150048_6_, Block p_150048_7_) {
        if (p_150048_7_.canProvidePower() && new BlockRailBase.Rail(this, p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_).func_150650_a() == 3) {
            this.func_150052_a(p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_, false);
        }
    }
}

