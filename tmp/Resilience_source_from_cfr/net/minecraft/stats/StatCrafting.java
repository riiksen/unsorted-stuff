/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IChatComponent;

public class StatCrafting
extends StatBase {
    private final Item field_150960_a;
    private static final String __OBFID = "CL_00001470";

    public StatCrafting(String p_i45305_1_, IChatComponent p_i45305_2_, Item p_i45305_3_) {
        super(p_i45305_1_, p_i45305_2_);
        this.field_150960_a = p_i45305_3_;
    }

    public Item func_150959_a() {
        return this.field_150960_a;
    }
}

