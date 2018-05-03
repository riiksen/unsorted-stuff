/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.scoreboard.ScoreDummyCriteria;
import net.minecraft.scoreboard.ScoreHealthCriteria;

public interface IScoreObjectiveCriteria {
    public static final Map field_96643_a = new HashMap();
    public static final IScoreObjectiveCriteria field_96641_b = new ScoreDummyCriteria("dummy");
    public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
    public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
    public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
    public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");

    public String func_96636_a();

    public int func_96635_a(List var1);

    public boolean isReadOnly();
}

