/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom {
    private static final String __OBFID = "CL_00001503";

    public static int getTotalWeight(Collection par0Collection) {
        int var1 = 0;
        for (Item var3 : par0Collection) {
            var1 += var3.itemWeight;
        }
        return var1;
    }

    public static Item getRandomItem(Random par0Random, Collection par1Collection, int par2) {
        Item var5;
        if (par2 <= 0) {
            throw new IllegalArgumentException();
        }
        int var3 = par0Random.nextInt(par2);
        Iterator var4 = par1Collection.iterator();
        do {
            if (!var4.hasNext()) {
                return null;
            }
            var5 = (Item)var4.next();
        } while ((var3 -= var5.itemWeight) >= 0);
        return var5;
    }

    public static Item getRandomItem(Random par0Random, Collection par1Collection) {
        return WeightedRandom.getRandomItem(par0Random, par1Collection, WeightedRandom.getTotalWeight(par1Collection));
    }

    public static int getTotalWeight(Item[] par0ArrayOfWeightedRandomItem) {
        int var1 = 0;
        Item[] var2 = par0ArrayOfWeightedRandomItem;
        int var3 = par0ArrayOfWeightedRandomItem.length;
        int var4 = 0;
        while (var4 < var3) {
            Item var5 = var2[var4];
            var1 += var5.itemWeight;
            ++var4;
        }
        return var1;
    }

    public static Item getRandomItem(Random par0Random, Item[] par1ArrayOfWeightedRandomItem, int par2) {
        if (par2 <= 0) {
            throw new IllegalArgumentException();
        }
        int var3 = par0Random.nextInt(par2);
        Item[] var4 = par1ArrayOfWeightedRandomItem;
        int var5 = par1ArrayOfWeightedRandomItem.length;
        int var6 = 0;
        while (var6 < var5) {
            Item var7 = var4[var6];
            if ((var3 -= var7.itemWeight) < 0) {
                return var7;
            }
            ++var6;
        }
        return null;
    }

    public static Item getRandomItem(Random par0Random, Item[] par1ArrayOfWeightedRandomItem) {
        return WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomItem, WeightedRandom.getTotalWeight(par1ArrayOfWeightedRandomItem));
    }

    public static class Item {
        protected int itemWeight;
        private static final String __OBFID = "CL_00001504";

        public Item(int par1) {
            this.itemWeight = par1;
        }
    }

}

