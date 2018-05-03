/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.potion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionHelper {
    public static final String field_77924_a = null;
    public static final String sugarEffect;
    public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
    public static final String spiderEyeEffect;
    public static final String fermentedSpiderEyeEffect;
    public static final String speckledMelonEffect;
    public static final String blazePowderEffect;
    public static final String magmaCreamEffect;
    public static final String redstoneEffect;
    public static final String glowstoneEffect;
    public static final String gunpowderEffect;
    public static final String goldenCarrotEffect;
    public static final String field_151423_m;
    private static final HashMap potionRequirements;
    private static final HashMap potionAmplifiers;
    private static final HashMap field_77925_n;
    private static final String[] potionPrefixes;
    private static final String __OBFID = "CL_00000078";

    static {
        potionRequirements = new HashMap();
        potionAmplifiers = new HashMap();
        potionRequirements.put(Potion.regeneration.getId(), "0 & !1 & !2 & !3 & 0+6");
        sugarEffect = "-0+1-2-3&4-4+13";
        potionRequirements.put(Potion.moveSpeed.getId(), "!0 & 1 & !2 & !3 & 1+6");
        magmaCreamEffect = "+0+1-2-3&4-4+13";
        potionRequirements.put(Potion.fireResistance.getId(), "0 & 1 & !2 & !3 & 0+6");
        speckledMelonEffect = "+0-1+2-3&4-4+13";
        potionRequirements.put(Potion.heal.getId(), "0 & !1 & 2 & !3");
        spiderEyeEffect = "-0-1+2-3&4-4+13";
        potionRequirements.put(Potion.poison.getId(), "!0 & !1 & 2 & !3 & 2+6");
        fermentedSpiderEyeEffect = "-0+3-4+13";
        potionRequirements.put(Potion.weakness.getId(), "!0 & !1 & !2 & 3 & 3+6");
        potionRequirements.put(Potion.harm.getId(), "!0 & !1 & 2 & 3");
        potionRequirements.put(Potion.moveSlowdown.getId(), "!0 & 1 & !2 & 3 & 3+6");
        blazePowderEffect = "+0-1-2+3&4-4+13";
        potionRequirements.put(Potion.damageBoost.getId(), "0 & !1 & !2 & 3 & 3+6");
        goldenCarrotEffect = "-0+1+2-3+13&4-4";
        potionRequirements.put(Potion.nightVision.getId(), "!0 & 1 & 2 & !3 & 2+6");
        potionRequirements.put(Potion.invisibility.getId(), "!0 & 1 & 2 & 3 & 2+6");
        field_151423_m = "+0-1+2+3+13&4-4";
        potionRequirements.put(Potion.waterBreathing.getId(), "0 & !1 & 2 & 3 & 2+6");
        glowstoneEffect = "+5-6-7";
        potionAmplifiers.put(Potion.moveSpeed.getId(), "5");
        potionAmplifiers.put(Potion.digSpeed.getId(), "5");
        potionAmplifiers.put(Potion.damageBoost.getId(), "5");
        potionAmplifiers.put(Potion.regeneration.getId(), "5");
        potionAmplifiers.put(Potion.harm.getId(), "5");
        potionAmplifiers.put(Potion.heal.getId(), "5");
        potionAmplifiers.put(Potion.resistance.getId(), "5");
        potionAmplifiers.put(Potion.poison.getId(), "5");
        redstoneEffect = "-5+6-7";
        gunpowderEffect = "+14&13-13";
        field_77925_n = new HashMap();
        potionPrefixes = new String[]{"potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky"};
    }

    public static boolean checkFlag(int par0, int par1) {
        if ((par0 & 1 << par1) != 0) {
            return true;
        }
        return false;
    }

    private static int isFlagSet(int par0, int par1) {
        return PotionHelper.checkFlag(par0, par1) ? 1 : 0;
    }

    private static int isFlagUnset(int par0, int par1) {
        return PotionHelper.checkFlag(par0, par1) ? 0 : 1;
    }

    public static int func_77909_a(int par0) {
        return PotionHelper.func_77908_a(par0, 5, 4, 3, 2, 1);
    }

    public static int calcPotionLiquidColor(Collection par0Collection) {
        int var1 = 3694022;
        if (par0Collection != null && !par0Collection.isEmpty()) {
            float var2 = 0.0f;
            float var3 = 0.0f;
            float var4 = 0.0f;
            float var5 = 0.0f;
            for (PotionEffect var7 : par0Collection) {
                int var8 = Potion.potionTypes[var7.getPotionID()].getLiquidColor();
                int var9 = 0;
                while (var9 <= var7.getAmplifier()) {
                    var2 += (float)(var8 >> 16 & 255) / 255.0f;
                    var3 += (float)(var8 >> 8 & 255) / 255.0f;
                    var4 += (float)(var8 >> 0 & 255) / 255.0f;
                    var5 += 1.0f;
                    ++var9;
                }
            }
            var2 = var2 / var5 * 255.0f;
            var3 = var3 / var5 * 255.0f;
            var4 = var4 / var5 * 255.0f;
            return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
        }
        return var1;
    }

    public static boolean func_82817_b(Collection par0Collection) {
        PotionEffect var2;
        Iterator var1 = par0Collection.iterator();
        do {
            if (var1.hasNext()) continue;
            return true;
        } while ((var2 = (PotionEffect)var1.next()).getIsAmbient());
        return false;
    }

    public static int func_77915_a(int par0, boolean par1) {
        if (!par1) {
            if (field_77925_n.containsKey(par0)) {
                return (Integer)field_77925_n.get(par0);
            }
            int var2 = PotionHelper.calcPotionLiquidColor(PotionHelper.getPotionEffects(par0, false));
            field_77925_n.put(par0, var2);
            return var2;
        }
        return PotionHelper.calcPotionLiquidColor(PotionHelper.getPotionEffects(par0, par1));
    }

    public static String func_77905_c(int par0) {
        int var1 = PotionHelper.func_77909_a(par0);
        return potionPrefixes[var1];
    }

    private static int func_77904_a(boolean par0, boolean par1, boolean par2, int par3, int par4, int par5, int par6) {
        int var7 = 0;
        if (par0) {
            var7 = PotionHelper.isFlagUnset(par6, par4);
        } else if (par3 != -1) {
            if (par3 == 0 && PotionHelper.countSetFlags(par6) == par4) {
                var7 = 1;
            } else if (par3 == 1 && PotionHelper.countSetFlags(par6) > par4) {
                var7 = 1;
            } else if (par3 == 2 && PotionHelper.countSetFlags(par6) < par4) {
                var7 = 1;
            }
        } else {
            var7 = PotionHelper.isFlagSet(par6, par4);
        }
        if (par1) {
            var7 *= par5;
        }
        if (par2) {
            var7 *= -1;
        }
        return var7;
    }

    private static int countSetFlags(int par0) {
        int var1 = 0;
        while (par0 > 0) {
            par0 &= par0 - 1;
            ++var1;
        }
        return var1;
    }

    private static int parsePotionEffects(String par0Str, int par1, int par2, int par3) {
        if (par1 < par0Str.length() && par2 >= 0 && par1 < par2) {
            int var4 = par0Str.indexOf(124, par1);
            if (var4 >= 0 && var4 < par2) {
                int var5 = PotionHelper.parsePotionEffects(par0Str, par1, var4 - 1, par3);
                if (var5 > 0) {
                    return var5;
                }
                int var17 = PotionHelper.parsePotionEffects(par0Str, var4 + 1, par2, par3);
                return var17 > 0 ? var17 : 0;
            }
            int var5 = par0Str.indexOf(38, par1);
            if (var5 >= 0 && var5 < par2) {
                int var17 = PotionHelper.parsePotionEffects(par0Str, par1, var5 - 1, par3);
                if (var17 <= 0) {
                    return 0;
                }
                int var18 = PotionHelper.parsePotionEffects(par0Str, var5 + 1, par2, par3);
                return var18 <= 0 ? 0 : (var17 > var18 ? var17 : var18);
            }
            boolean var6 = false;
            boolean var7 = false;
            boolean var8 = false;
            boolean var9 = false;
            boolean var10 = false;
            int var11 = -1;
            int var12 = 0;
            int var13 = 0;
            int var14 = 0;
            int var15 = par1;
            while (var15 < par2) {
                char var16 = par0Str.charAt(var15);
                if (var16 >= '0' && var16 <= '9') {
                    if (var6) {
                        var13 = var16 - 48;
                        var7 = true;
                    } else {
                        var12 *= 10;
                        var12 += var16 - 48;
                        var8 = true;
                    }
                } else if (var16 == '*') {
                    var6 = true;
                } else if (var16 == '!') {
                    if (var8) {
                        var14 += PotionHelper.func_77904_a(var9, var7, var10, var11, var12, var13, par3);
                        var9 = false;
                        var10 = false;
                        var6 = false;
                        var7 = false;
                        var8 = false;
                        var13 = 0;
                        var12 = 0;
                        var11 = -1;
                    }
                    var9 = true;
                } else if (var16 == '-') {
                    if (var8) {
                        var14 += PotionHelper.func_77904_a(var9, var7, var10, var11, var12, var13, par3);
                        var9 = false;
                        var10 = false;
                        var6 = false;
                        var7 = false;
                        var8 = false;
                        var13 = 0;
                        var12 = 0;
                        var11 = -1;
                    }
                    var10 = true;
                } else if (var16 != '=' && var16 != '<' && var16 != '>') {
                    if (var16 == '+' && var8) {
                        var14 += PotionHelper.func_77904_a(var9, var7, var10, var11, var12, var13, par3);
                        var9 = false;
                        var10 = false;
                        var6 = false;
                        var7 = false;
                        var8 = false;
                        var13 = 0;
                        var12 = 0;
                        var11 = -1;
                    }
                } else {
                    if (var8) {
                        var14 += PotionHelper.func_77904_a(var9, var7, var10, var11, var12, var13, par3);
                        var9 = false;
                        var10 = false;
                        var6 = false;
                        var7 = false;
                        var8 = false;
                        var13 = 0;
                        var12 = 0;
                        var11 = -1;
                    }
                    if (var16 == '=') {
                        var11 = 0;
                    } else if (var16 == '<') {
                        var11 = 2;
                    } else if (var16 == '>') {
                        var11 = 1;
                    }
                }
                ++var15;
            }
            if (var8) {
                var14 += PotionHelper.func_77904_a(var9, var7, var10, var11, var12, var13, par3);
            }
            return var14;
        }
        return 0;
    }

    public static List getPotionEffects(int par0, boolean par1) {
        ArrayList<PotionEffect> var2 = null;
        Potion[] var3 = Potion.potionTypes;
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            String var7;
            int var8;
            Potion var6 = var3[var5];
            if (var6 != null && (!var6.isUsable() || par1) && (var7 = (String)potionRequirements.get(var6.getId())) != null && (var8 = PotionHelper.parsePotionEffects(var7, 0, var7.length(), par0)) > 0) {
                int var9 = 0;
                String var10 = (String)potionAmplifiers.get(var6.getId());
                if (var10 != null && (var9 = PotionHelper.parsePotionEffects(var10, 0, var10.length(), par0)) < 0) {
                    var9 = 0;
                }
                if (var6.isInstant()) {
                    var8 = 1;
                } else {
                    var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
                    var8 >>= var9;
                    var8 = (int)Math.round((double)var8 * var6.getEffectiveness());
                    if ((par0 & 16384) != 0) {
                        var8 = (int)Math.round((double)var8 * 0.75 + 0.5);
                    }
                }
                if (var2 == null) {
                    var2 = new ArrayList<PotionEffect>();
                }
                PotionEffect var11 = new PotionEffect(var6.getId(), var8, var9);
                if ((par0 & 16384) != 0) {
                    var11.setSplashPotion(true);
                }
                var2.add(var11);
            }
            ++var5;
        }
        return var2;
    }

    private static int brewBitOperations(int par0, int par1, boolean par2, boolean par3, boolean par4) {
        if (par4) {
            if (!PotionHelper.checkFlag(par0, par1)) {
                return 0;
            }
        } else {
            par0 = par2 ? (par0 &= ~ (1 << par1)) : (par3 ? ((par0 & 1 << par1) == 0 ? (par0 |= 1 << par1) : (par0 &= ~ (1 << par1))) : (par0 |= 1 << par1));
        }
        return par0;
    }

    public static int applyIngredient(int par0, String par1Str) {
        int var2 = 0;
        int var3 = par1Str.length();
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var8 = 0;
        int var9 = var2;
        while (var9 < var3) {
            char var10 = par1Str.charAt(var9);
            if (var10 >= '0' && var10 <= '9') {
                var8 *= 10;
                var8 += var10 - 48;
                var4 = true;
            } else if (var10 == '!') {
                if (var4) {
                    par0 = PotionHelper.brewBitOperations(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
                var5 = true;
            } else if (var10 == '-') {
                if (var4) {
                    par0 = PotionHelper.brewBitOperations(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
                var6 = true;
            } else if (var10 == '+') {
                if (var4) {
                    par0 = PotionHelper.brewBitOperations(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
            } else if (var10 == '&') {
                if (var4) {
                    par0 = PotionHelper.brewBitOperations(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
                var7 = true;
            }
            ++var9;
        }
        if (var4) {
            par0 = PotionHelper.brewBitOperations(par0, var8, var6, var5, var7);
        }
        return par0 & 32767;
    }

    public static int func_77908_a(int par0, int par1, int par2, int par3, int par4, int par5) {
        return (PotionHelper.checkFlag(par0, par1) ? 16 : 0) | (PotionHelper.checkFlag(par0, par2) ? 8 : 0) | (PotionHelper.checkFlag(par0, par3) ? 4 : 0) | (PotionHelper.checkFlag(par0, par4) ? 2 : 0) | (PotionHelper.checkFlag(par0, par5) ? 1 : 0);
    }
}

