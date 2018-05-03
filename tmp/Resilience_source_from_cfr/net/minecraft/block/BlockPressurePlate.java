/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockPressurePlate
extends BlockBasePressurePlate {
    private Sensitivity field_150069_a;
    private static final String __OBFID = "CL_00000289";

    protected BlockPressurePlate(String p_i45418_1_, Material p_i45418_2_, Sensitivity p_i45418_3_) {
        super(p_i45418_1_, p_i45418_2_);
        this.field_150069_a = p_i45418_3_;
    }

    @Override
    protected int func_150066_d(int p_150066_1_) {
        return p_150066_1_ > 0 ? 1 : 0;
    }

    @Override
    protected int func_150060_c(int p_150060_1_) {
        return p_150060_1_ == 1 ? 15 : 0;
    }

    @Override
    protected int func_150065_e(World p_150065_1_, int p_150065_2_, int p_150065_3_, int p_150065_4_) {
        List var5 = null;
        if (this.field_150069_a == Sensitivity.everything) {
            var5 = p_150065_1_.getEntitiesWithinAABBExcludingEntity(null, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }
        if (this.field_150069_a == Sensitivity.mobs) {
            var5 = p_150065_1_.getEntitiesWithinAABB(EntityLivingBase.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }
        if (this.field_150069_a == Sensitivity.players) {
            var5 = p_150065_1_.getEntitiesWithinAABB(EntityPlayer.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }
        if (var5 != null && !var5.isEmpty()) {
            for (Entity var7 : var5) {
                if (var7.doesEntityNotTriggerPressurePlate()) continue;
                return 15;
            }
        }
        return 0;
    }

    public static enum Sensitivity {
        everything("everything", 0),
        mobs("mobs", 1),
        players("players", 2);
        
        private static final Sensitivity[] $VALUES;
        private static final String __OBFID = "CL_00000290";

        static {
            $VALUES = new Sensitivity[]{everything, mobs, players};
        }

        private Sensitivity(String p_i45417_1_, int p_i45417_2_, String string2, int n2) {
        }
    }

}

