/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.scoreboard;

import java.util.Collection;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData
extends WorldSavedData {
    private static final Logger logger = LogManager.getLogger();
    private Scoreboard theScoreboard;
    private NBTTagCompound field_96506_b;
    private static final String __OBFID = "CL_00000620";

    public ScoreboardSaveData() {
        this("scoreboard");
    }

    public ScoreboardSaveData(String par1Str) {
        super(par1Str);
    }

    public void func_96499_a(Scoreboard par1Scoreboard) {
        this.theScoreboard = par1Scoreboard;
        if (this.field_96506_b != null) {
            this.readFromNBT(this.field_96506_b);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        if (this.theScoreboard == null) {
            this.field_96506_b = par1NBTTagCompound;
        } else {
            this.func_96501_b(par1NBTTagCompound.getTagList("Objectives", 10));
            this.func_96500_c(par1NBTTagCompound.getTagList("PlayerScores", 10));
            if (par1NBTTagCompound.func_150297_b("DisplaySlots", 10)) {
                this.func_96504_c(par1NBTTagCompound.getCompoundTag("DisplaySlots"));
            }
            if (par1NBTTagCompound.func_150297_b("Teams", 9)) {
                this.func_96498_a(par1NBTTagCompound.getTagList("Teams", 10));
            }
        }
    }

    protected void func_96498_a(NBTTagList par1NBTTagList) {
        int var2 = 0;
        while (var2 < par1NBTTagList.tagCount()) {
            NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
            ScorePlayerTeam var4 = this.theScoreboard.createTeam(var3.getString("Name"));
            var4.setTeamName(var3.getString("DisplayName"));
            var4.setNamePrefix(var3.getString("Prefix"));
            var4.setNameSuffix(var3.getString("Suffix"));
            if (var3.func_150297_b("AllowFriendlyFire", 99)) {
                var4.setAllowFriendlyFire(var3.getBoolean("AllowFriendlyFire"));
            }
            if (var3.func_150297_b("SeeFriendlyInvisibles", 99)) {
                var4.setSeeFriendlyInvisiblesEnabled(var3.getBoolean("SeeFriendlyInvisibles"));
            }
            this.func_96502_a(var4, var3.getTagList("Players", 8));
            ++var2;
        }
    }

    protected void func_96502_a(ScorePlayerTeam par1ScorePlayerTeam, NBTTagList par2NBTTagList) {
        int var3 = 0;
        while (var3 < par2NBTTagList.tagCount()) {
            this.theScoreboard.func_151392_a(par2NBTTagList.getStringTagAt(var3), par1ScorePlayerTeam.getRegisteredName());
            ++var3;
        }
    }

    protected void func_96504_c(NBTTagCompound par1NBTTagCompound) {
        int var2 = 0;
        while (var2 < 3) {
            if (par1NBTTagCompound.func_150297_b("slot_" + var2, 8)) {
                String var3 = par1NBTTagCompound.getString("slot_" + var2);
                ScoreObjective var4 = this.theScoreboard.getObjective(var3);
                this.theScoreboard.func_96530_a(var2, var4);
            }
            ++var2;
        }
    }

    protected void func_96501_b(NBTTagList par1NBTTagList) {
        int var2 = 0;
        while (var2 < par1NBTTagList.tagCount()) {
            NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
            IScoreObjectiveCriteria var4 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.field_96643_a.get(var3.getString("CriteriaName"));
            ScoreObjective var5 = this.theScoreboard.addScoreObjective(var3.getString("Name"), var4);
            var5.setDisplayName(var3.getString("DisplayName"));
            ++var2;
        }
    }

    protected void func_96500_c(NBTTagList par1NBTTagList) {
        int var2 = 0;
        while (var2 < par1NBTTagList.tagCount()) {
            NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
            ScoreObjective var4 = this.theScoreboard.getObjective(var3.getString("Objective"));
            Score var5 = this.theScoreboard.func_96529_a(var3.getString("Name"), var4);
            var5.func_96647_c(var3.getInteger("Score"));
            ++var2;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        if (this.theScoreboard == null) {
            logger.warn("Tried to save scoreboard without having a scoreboard...");
        } else {
            par1NBTTagCompound.setTag("Objectives", this.func_96505_b());
            par1NBTTagCompound.setTag("PlayerScores", this.func_96503_e());
            par1NBTTagCompound.setTag("Teams", this.func_96496_a());
            this.func_96497_d(par1NBTTagCompound);
        }
    }

    protected NBTTagList func_96496_a() {
        NBTTagList var1 = new NBTTagList();
        Collection var2 = this.theScoreboard.getTeams();
        for (ScorePlayerTeam var4 : var2) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.getRegisteredName());
            var5.setString("DisplayName", var4.func_96669_c());
            var5.setString("Prefix", var4.getColorPrefix());
            var5.setString("Suffix", var4.getColorSuffix());
            var5.setBoolean("AllowFriendlyFire", var4.getAllowFriendlyFire());
            var5.setBoolean("SeeFriendlyInvisibles", var4.func_98297_h());
            NBTTagList var6 = new NBTTagList();
            for (String var8 : var4.getMembershipCollection()) {
                var6.appendTag(new NBTTagString(var8));
            }
            var5.setTag("Players", var6);
            var1.appendTag(var5);
        }
        return var1;
    }

    protected void func_96497_d(NBTTagCompound par1NBTTagCompound) {
        NBTTagCompound var2 = new NBTTagCompound();
        boolean var3 = false;
        int var4 = 0;
        while (var4 < 3) {
            ScoreObjective var5 = this.theScoreboard.func_96539_a(var4);
            if (var5 != null) {
                var2.setString("slot_" + var4, var5.getName());
                var3 = true;
            }
            ++var4;
        }
        if (var3) {
            par1NBTTagCompound.setTag("DisplaySlots", var2);
        }
    }

    protected NBTTagList func_96505_b() {
        NBTTagList var1 = new NBTTagList();
        Collection var2 = this.theScoreboard.getScoreObjectives();
        for (ScoreObjective var4 : var2) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.getName());
            var5.setString("CriteriaName", var4.getCriteria().func_96636_a());
            var5.setString("DisplayName", var4.getDisplayName());
            var1.appendTag(var5);
        }
        return var1;
    }

    protected NBTTagList func_96503_e() {
        NBTTagList var1 = new NBTTagList();
        Collection var2 = this.theScoreboard.func_96528_e();
        for (Score var4 : var2) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.getPlayerName());
            var5.setString("Objective", var4.func_96645_d().getName());
            var5.setInteger("Score", var4.getScorePoints());
            var1.appendTag(var5);
        }
        return var1;
    }
}

