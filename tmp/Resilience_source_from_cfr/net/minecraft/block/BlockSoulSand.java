/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSoulSand
extends Block {
    private static final String __OBFID = "CL_00000310";

    public BlockSoulSand() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        float var5 = 0.125f;
        return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_, p_149668_3_, p_149668_4_, p_149668_2_ + 1, (float)(p_149668_3_ + 1) - var5, p_149668_4_ + 1);
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        if (Resilience.getInstance().getValues().noSlowdownEnabled) {
            return;
        }
        p_149670_5_.motionX *= 0.4;
        p_149670_5_.motionZ *= 0.4;
    }
}

