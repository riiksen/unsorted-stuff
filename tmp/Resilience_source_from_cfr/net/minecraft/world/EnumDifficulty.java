/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

public enum EnumDifficulty {
    PEACEFUL(0, "options.difficulty.peaceful"),
    EASY(1, "options.difficulty.easy"),
    NORMAL(2, "options.difficulty.normal"),
    HARD(3, "options.difficulty.hard");
    
    private static final EnumDifficulty[] difficultyEnums;
    private final int difficultyId;
    private final String difficultyResourceKey;
    private static final String __OBFID = "CL_00001510";

    static {
        difficultyEnums = new EnumDifficulty[EnumDifficulty.values().length];
        EnumDifficulty[] var0 = EnumDifficulty.values();
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            EnumDifficulty var3;
            EnumDifficulty.difficultyEnums[var3.difficultyId] = var3 = var0[var2];
            ++var2;
        }
    }

    private EnumDifficulty(String p_i45312_3_, int p_i45312_4_, int n2, String string2) {
        this.difficultyId = p_i45312_3_;
        this.difficultyResourceKey = (String)p_i45312_4_;
    }

    public int getDifficultyId() {
        return this.difficultyId;
    }

    public static EnumDifficulty getDifficultyEnum(int p_151523_0_) {
        return difficultyEnums[p_151523_0_ % difficultyEnums.length];
    }

    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }
}

