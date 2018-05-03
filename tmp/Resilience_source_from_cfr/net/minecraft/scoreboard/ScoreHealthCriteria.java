/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreDummyCriteria;
import net.minecraft.util.MathHelper;

public class ScoreHealthCriteria
extends ScoreDummyCriteria {
    private static final String __OBFID = "CL_00000623";

    public ScoreHealthCriteria(String par1Str) {
        super(par1Str);
    }

    @Override
    public int func_96635_a(List par1List) {
        float var2 = 0.0f;
        for (EntityPlayer var4 : par1List) {
            var2 += var4.getHealth() + var4.getAbsorptionAmount();
        }
        if (par1List.size() > 0) {
            var2 /= (float)par1List.size();
        }
        return MathHelper.ceiling_float_int(var2);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }
}

