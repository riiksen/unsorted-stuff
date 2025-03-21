/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.scoreboard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

public class ScorePlayerTeam
extends Team {
    private final Scoreboard theScoreboard;
    private final String field_96675_b;
    private final Set membershipSet = new HashSet();
    private String teamNameSPT;
    private String namePrefixSPT = "";
    private String colorSuffix = "";
    private boolean allowFriendlyFire = true;
    private boolean canSeeFriendlyInvisibles = true;
    private static final String __OBFID = "CL_00000616";

    public ScorePlayerTeam(Scoreboard par1Scoreboard, String par2Str) {
        this.theScoreboard = par1Scoreboard;
        this.field_96675_b = par2Str;
        this.teamNameSPT = par2Str;
    }

    @Override
    public String getRegisteredName() {
        return this.field_96675_b;
    }

    public String func_96669_c() {
        return this.teamNameSPT;
    }

    public void setTeamName(String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.teamNameSPT = par1Str;
        this.theScoreboard.func_96538_b(this);
    }

    public Collection getMembershipCollection() {
        return this.membershipSet;
    }

    public String getColorPrefix() {
        return this.namePrefixSPT;
    }

    public void setNamePrefix(String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.namePrefixSPT = par1Str;
        this.theScoreboard.func_96538_b(this);
    }

    public String getColorSuffix() {
        return this.colorSuffix;
    }

    public void setNameSuffix(String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Suffix cannot be null");
        }
        this.colorSuffix = par1Str;
        this.theScoreboard.func_96538_b(this);
    }

    @Override
    public String func_142053_d(String par1Str) {
        return String.valueOf(this.getColorPrefix()) + par1Str + this.getColorSuffix();
    }

    public static String formatPlayerName(Team par0Team, String par1Str) {
        return par0Team == null ? par1Str : par0Team.func_142053_d(par1Str);
    }

    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean par1) {
        this.allowFriendlyFire = par1;
        this.theScoreboard.func_96538_b(this);
    }

    @Override
    public boolean func_98297_h() {
        return this.canSeeFriendlyInvisibles;
    }

    public void setSeeFriendlyInvisiblesEnabled(boolean par1) {
        this.canSeeFriendlyInvisibles = par1;
        this.theScoreboard.func_96538_b(this);
    }

    public int func_98299_i() {
        int var1 = 0;
        if (this.getAllowFriendlyFire()) {
            var1 |= true;
        }
        if (this.func_98297_h()) {
            var1 |= 2;
        }
        return var1;
    }

    public void func_98298_a(int par1) {
        this.setAllowFriendlyFire((par1 & 1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((par1 & 2) > 0);
    }
}

