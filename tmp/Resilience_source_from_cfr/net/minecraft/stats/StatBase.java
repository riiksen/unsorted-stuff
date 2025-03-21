/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.event.HoverEvent;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.ObjectiveStat;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class StatBase {
    public final String statId;
    private final IChatComponent statName;
    public boolean isIndependent;
    private final IStatType type;
    private final IScoreObjectiveCriteria field_150957_c;
    private Class field_150956_d;
    private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
    public static IStatType simpleStatType = new IStatType(){
        private static final String __OBFID = "CL_00001473";

        @Override
        public String format(int par1) {
            return numberFormat.format(par1);
        }
    };
    private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
    public static IStatType timeStatType = new IStatType(){
        private static final String __OBFID = "CL_00001474";

        @Override
        public String format(int par1) {
            double var2 = (double)par1 / 20.0;
            double var4 = var2 / 60.0;
            double var6 = var4 / 60.0;
            double var8 = var6 / 24.0;
            double var10 = var8 / 365.0;
            return var10 > 0.5 ? String.valueOf(decimalFormat.format(var10)) + " y" : (var8 > 0.5 ? String.valueOf(decimalFormat.format(var8)) + " d" : (var6 > 0.5 ? String.valueOf(decimalFormat.format(var6)) + " h" : (var4 > 0.5 ? String.valueOf(decimalFormat.format(var4)) + " m" : String.valueOf(var2) + " s")));
        }
    };
    public static IStatType distanceStatType = new IStatType(){
        private static final String __OBFID = "CL_00001475";

        @Override
        public String format(int par1) {
            double var2 = (double)par1 / 100.0;
            double var4 = var2 / 1000.0;
            return var4 > 0.5 ? String.valueOf(decimalFormat.format(var4)) + " km" : (var2 > 0.5 ? String.valueOf(decimalFormat.format(var2)) + " m" : String.valueOf(par1) + " cm");
        }
    };
    public static IStatType field_111202_k = new IStatType(){
        private static final String __OBFID = "CL_00001476";

        @Override
        public String format(int par1) {
            return decimalFormat.format((double)par1 * 0.1);
        }
    };
    private static final String __OBFID = "CL_00001472";

    public StatBase(String p_i45307_1_, IChatComponent p_i45307_2_, IStatType p_i45307_3_) {
        this.statId = p_i45307_1_;
        this.statName = p_i45307_2_;
        this.type = p_i45307_3_;
        this.field_150957_c = new ObjectiveStat(this);
        IScoreObjectiveCriteria.field_96643_a.put(this.field_150957_c.func_96636_a(), this.field_150957_c);
    }

    public StatBase(String p_i45308_1_, IChatComponent p_i45308_2_) {
        this(p_i45308_1_, p_i45308_2_, simpleStatType);
    }

    public StatBase initIndependentStat() {
        this.isIndependent = true;
        return this;
    }

    public StatBase registerStat() {
        if (StatList.oneShotStats.containsKey(this.statId)) {
            throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.oneShotStats.get((Object)this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
        }
        StatList.allStats.add(this);
        StatList.oneShotStats.put(this.statId, this);
        return this;
    }

    public boolean isAchievement() {
        return false;
    }

    public String func_75968_a(int par1) {
        return this.type.format(par1);
    }

    public IChatComponent func_150951_e() {
        IChatComponent var1 = this.statName.createCopy();
        var1.getChatStyle().setColor(EnumChatFormatting.GRAY);
        var1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
        return var1;
    }

    public IChatComponent func_150955_j() {
        IChatComponent var1 = this.func_150951_e();
        IChatComponent var2 = new ChatComponentText("[").appendSibling(var1).appendText("]");
        var2.setChatStyle(var1.getChatStyle());
        return var2;
    }

    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (par1Obj != null && this.getClass() == par1Obj.getClass()) {
            StatBase var2 = (StatBase)par1Obj;
            return this.statId.equals(var2.statId);
        }
        return false;
    }

    public int hashCode() {
        return this.statId.hashCode();
    }

    public String toString() {
        return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.field_150957_c + '}';
    }

    public IScoreObjectiveCriteria func_150952_k() {
        return this.field_150957_c;
    }

    public Class func_150954_l() {
        return this.field_150956_d;
    }

    public StatBase func_150953_b(Class p_150953_1_) {
        this.field_150956_d = p_150953_1_;
        return this;
    }

}

