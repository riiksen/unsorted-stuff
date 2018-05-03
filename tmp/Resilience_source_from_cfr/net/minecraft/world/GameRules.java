/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import java.util.Set;
import java.util.TreeMap;
import net.minecraft.nbt.NBTTagCompound;

public class GameRules {
    private TreeMap theGameRules = new TreeMap();
    private static final String __OBFID = "CL_00000136";

    public GameRules() {
        this.addGameRule("doFireTick", "true");
        this.addGameRule("mobGriefing", "true");
        this.addGameRule("keepInventory", "false");
        this.addGameRule("doMobSpawning", "true");
        this.addGameRule("doMobLoot", "true");
        this.addGameRule("doTileDrops", "true");
        this.addGameRule("commandBlockOutput", "true");
        this.addGameRule("naturalRegeneration", "true");
        this.addGameRule("doDaylightCycle", "true");
    }

    public void addGameRule(String par1Str, String par2Str) {
        this.theGameRules.put(par1Str, new Value(par2Str));
    }

    public void setOrCreateGameRule(String par1Str, String par2Str) {
        Value var3 = (Value)this.theGameRules.get(par1Str);
        if (var3 != null) {
            var3.setValue(par2Str);
        } else {
            this.addGameRule(par1Str, par2Str);
        }
    }

    public String getGameRuleStringValue(String par1Str) {
        Value var2 = (Value)this.theGameRules.get(par1Str);
        return var2 != null ? var2.getGameRuleStringValue() : "";
    }

    public boolean getGameRuleBooleanValue(String par1Str) {
        Value var2 = (Value)this.theGameRules.get(par1Str);
        return var2 != null ? var2.getGameRuleBooleanValue() : false;
    }

    public NBTTagCompound writeGameRulesToNBT() {
        NBTTagCompound var1 = new NBTTagCompound();
        for (String var3 : this.theGameRules.keySet()) {
            Value var4 = (Value)this.theGameRules.get(var3);
            var1.setString(var3, var4.getGameRuleStringValue());
        }
        return var1;
    }

    public void readGameRulesFromNBT(NBTTagCompound par1NBTTagCompound) {
        Set var2 = par1NBTTagCompound.func_150296_c();
        for (String var4 : var2) {
            String var6 = par1NBTTagCompound.getString(var4);
            this.setOrCreateGameRule(var4, var6);
        }
    }

    public String[] getRules() {
        return this.theGameRules.keySet().toArray(new String[0]);
    }

    public boolean hasRule(String par1Str) {
        return this.theGameRules.containsKey(par1Str);
    }

    static class Value {
        private String valueString;
        private boolean valueBoolean;
        private int valueInteger;
        private double valueDouble;
        private static final String __OBFID = "CL_00000137";

        public Value(String par1Str) {
            this.setValue(par1Str);
        }

        public void setValue(String par1Str) {
            this.valueString = par1Str;
            if (par1Str != null) {
                if (par1Str.equals("false")) {
                    this.valueBoolean = false;
                    return;
                }
                if (par1Str.equals("true")) {
                    this.valueBoolean = true;
                    return;
                }
            }
            this.valueBoolean = Boolean.parseBoolean(par1Str);
            try {
                this.valueInteger = Integer.parseInt(par1Str);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            try {
                this.valueDouble = Double.parseDouble(par1Str);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }

        public String getGameRuleStringValue() {
            return this.valueString;
        }

        public boolean getGameRuleBooleanValue() {
            return this.valueBoolean;
        }
    }

}

