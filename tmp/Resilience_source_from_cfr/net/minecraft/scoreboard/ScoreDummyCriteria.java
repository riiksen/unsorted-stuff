/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.scoreboard;

import java.util.List;
import java.util.Map;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;

public class ScoreDummyCriteria
implements IScoreObjectiveCriteria {
    private final String field_96644_g;
    private static final String __OBFID = "CL_00000622";

    public ScoreDummyCriteria(String par1Str) {
        this.field_96644_g = par1Str;
        IScoreObjectiveCriteria.field_96643_a.put(par1Str, this);
    }

    @Override
    public String func_96636_a() {
        return this.field_96644_g;
    }

    @Override
    public int func_96635_a(List par1List) {
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }
}

