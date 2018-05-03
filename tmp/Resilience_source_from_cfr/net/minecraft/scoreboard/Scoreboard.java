/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class Scoreboard {
    private final Map scoreObjectives = new HashMap();
    private final Map scoreObjectiveCriterias = new HashMap();
    private final Map field_96544_c = new HashMap();
    private final ScoreObjective[] field_96541_d = new ScoreObjective[3];
    private final Map teams = new HashMap();
    private final Map teamMemberships = new HashMap();
    private static final String __OBFID = "CL_00000619";

    public ScoreObjective getObjective(String par1Str) {
        return (ScoreObjective)this.scoreObjectives.get(par1Str);
    }

    public ScoreObjective addScoreObjective(String par1Str, IScoreObjectiveCriteria par2ScoreObjectiveCriteria) {
        ScoreObjective var3 = this.getObjective(par1Str);
        if (var3 != null) {
            throw new IllegalArgumentException("An objective with the name '" + par1Str + "' already exists!");
        }
        var3 = new ScoreObjective(this, par1Str, par2ScoreObjectiveCriteria);
        ArrayList<ScoreObjective> var4 = (ArrayList<ScoreObjective>)this.scoreObjectiveCriterias.get(par2ScoreObjectiveCriteria);
        if (var4 == null) {
            var4 = new ArrayList<ScoreObjective>();
            this.scoreObjectiveCriterias.put(par2ScoreObjectiveCriteria, var4);
        }
        var4.add(var3);
        this.scoreObjectives.put(par1Str, var3);
        this.func_96522_a(var3);
        return var3;
    }

    public Collection func_96520_a(IScoreObjectiveCriteria par1ScoreObjectiveCriteria) {
        Collection var2 = (Collection)this.scoreObjectiveCriterias.get(par1ScoreObjectiveCriteria);
        return var2 == null ? new ArrayList() : new ArrayList(var2);
    }

    public Score func_96529_a(String par1Str, ScoreObjective par2ScoreObjective) {
        Score var4;
        HashMap var3 = (HashMap)this.field_96544_c.get(par1Str);
        if (var3 == null) {
            var3 = new HashMap();
            this.field_96544_c.put(par1Str, var3);
        }
        if ((var4 = (Score)var3.get(par2ScoreObjective)) == null) {
            var4 = new Score(this, par2ScoreObjective, par1Str);
            ((Map)var3).put(par2ScoreObjective, var4);
        }
        return var4;
    }

    public Collection func_96534_i(ScoreObjective par1ScoreObjective) {
        ArrayList<Score> var2 = new ArrayList<Score>();
        for (Map var4 : this.field_96544_c.values()) {
            Score var5 = (Score)var4.get(par1ScoreObjective);
            if (var5 == null) continue;
            var2.add(var5);
        }
        Collections.sort(var2, Score.field_96658_a);
        return var2;
    }

    public Collection getScoreObjectives() {
        return this.scoreObjectives.values();
    }

    public Collection getObjectiveNames() {
        return this.field_96544_c.keySet();
    }

    public void func_96515_c(String par1Str) {
        Map var2 = (Map)this.field_96544_c.remove(par1Str);
        if (var2 != null) {
            this.func_96516_a(par1Str);
        }
    }

    public Collection func_96528_e() {
        Collection var1 = this.field_96544_c.values();
        ArrayList var2 = new ArrayList();
        for (Map var4 : var1) {
            var2.addAll(var4.values());
        }
        return var2;
    }

    public Map func_96510_d(String par1Str) {
        HashMap var2 = (HashMap)this.field_96544_c.get(par1Str);
        if (var2 == null) {
            var2 = new HashMap();
        }
        return var2;
    }

    public void func_96519_k(ScoreObjective par1ScoreObjective) {
        this.scoreObjectives.remove(par1ScoreObjective.getName());
        int var2 = 0;
        while (var2 < 3) {
            if (this.func_96539_a(var2) == par1ScoreObjective) {
                this.func_96530_a(var2, null);
            }
            ++var2;
        }
        List var5 = (List)this.scoreObjectiveCriterias.get(par1ScoreObjective.getCriteria());
        if (var5 != null) {
            var5.remove(par1ScoreObjective);
        }
        for (Map var4 : this.field_96544_c.values()) {
            var4.remove(par1ScoreObjective);
        }
        this.func_96533_c(par1ScoreObjective);
    }

    public void func_96530_a(int par1, ScoreObjective par2ScoreObjective) {
        this.field_96541_d[par1] = par2ScoreObjective;
    }

    public ScoreObjective func_96539_a(int par1) {
        return this.field_96541_d[par1];
    }

    public ScorePlayerTeam getTeam(String par1Str) {
        return (ScorePlayerTeam)this.teams.get(par1Str);
    }

    public ScorePlayerTeam createTeam(String par1Str) {
        ScorePlayerTeam var2 = this.getTeam(par1Str);
        if (var2 != null) {
            throw new IllegalArgumentException("A team with the name '" + par1Str + "' already exists!");
        }
        var2 = new ScorePlayerTeam(this, par1Str);
        this.teams.put(par1Str, var2);
        this.func_96523_a(var2);
        return var2;
    }

    public void removeTeam(ScorePlayerTeam par1ScorePlayerTeam) {
        this.teams.remove(par1ScorePlayerTeam.getRegisteredName());
        for (String var3 : par1ScorePlayerTeam.getMembershipCollection()) {
            this.teamMemberships.remove(var3);
        }
        this.func_96513_c(par1ScorePlayerTeam);
    }

    public boolean func_151392_a(String p_151392_1_, String p_151392_2_) {
        if (!this.teams.containsKey(p_151392_2_)) {
            return false;
        }
        ScorePlayerTeam var3 = this.getTeam(p_151392_2_);
        if (this.getPlayersTeam(p_151392_1_) != null) {
            this.func_96524_g(p_151392_1_);
        }
        this.teamMemberships.put(p_151392_1_, var3);
        var3.getMembershipCollection().add(p_151392_1_);
        return true;
    }

    public boolean func_96524_g(String par1Str) {
        ScorePlayerTeam var2 = this.getPlayersTeam(par1Str);
        if (var2 != null) {
            this.removePlayerFromTeam(par1Str, var2);
            return true;
        }
        return false;
    }

    public void removePlayerFromTeam(String par1Str, ScorePlayerTeam par2ScorePlayerTeam) {
        if (this.getPlayersTeam(par1Str) != par2ScorePlayerTeam) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + par2ScorePlayerTeam.getRegisteredName() + "'.");
        }
        this.teamMemberships.remove(par1Str);
        par2ScorePlayerTeam.getMembershipCollection().remove(par1Str);
    }

    public Collection getTeamNames() {
        return this.teams.keySet();
    }

    public Collection getTeams() {
        return this.teams.values();
    }

    public ScorePlayerTeam getPlayersTeam(String par1Str) {
        return (ScorePlayerTeam)this.teamMemberships.get(par1Str);
    }

    public void func_96522_a(ScoreObjective par1ScoreObjective) {
    }

    public void func_96532_b(ScoreObjective par1ScoreObjective) {
    }

    public void func_96533_c(ScoreObjective par1ScoreObjective) {
    }

    public void func_96536_a(Score par1Score) {
    }

    public void func_96516_a(String par1Str) {
    }

    public void func_96523_a(ScorePlayerTeam par1ScorePlayerTeam) {
    }

    public void func_96538_b(ScorePlayerTeam par1ScorePlayerTeam) {
    }

    public void func_96513_c(ScorePlayerTeam par1ScorePlayerTeam) {
    }

    public static String getObjectiveDisplaySlot(int par0) {
        switch (par0) {
            case 0: {
                return "list";
            }
            case 1: {
                return "sidebar";
            }
            case 2: {
                return "belowName";
            }
        }
        return null;
    }

    public static int getObjectiveDisplaySlotNumber(String par0Str) {
        return par0Str.equalsIgnoreCase("list") ? 0 : (par0Str.equalsIgnoreCase("sidebar") ? 1 : (par0Str.equalsIgnoreCase("belowName") ? 2 : -1));
    }
}

