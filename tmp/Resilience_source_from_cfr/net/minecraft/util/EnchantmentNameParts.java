/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.Random;

public class EnchantmentNameParts {
    public static final EnchantmentNameParts instance = new EnchantmentNameParts();
    private Random rand = new Random();
    private String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
    private static final String __OBFID = "CL_00000756";

    public String generateNewRandomName() {
        int var1 = this.rand.nextInt(2) + 3;
        String var2 = "";
        int var3 = 0;
        while (var3 < var1) {
            if (var3 > 0) {
                var2 = String.valueOf(var2) + " ";
            }
            var2 = String.valueOf(var2) + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
            ++var3;
        }
        return var2;
    }

    public void reseedRandomGenerator(long p_148335_1_) {
        this.rand.setSeed(p_148335_1_);
    }
}

