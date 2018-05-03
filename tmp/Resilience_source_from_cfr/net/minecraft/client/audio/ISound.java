/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public interface ISound {
    public ResourceLocation func_147650_b();

    public boolean func_147657_c();

    public int func_147652_d();

    public float func_147653_e();

    public float func_147655_f();

    public float func_147649_g();

    public float func_147654_h();

    public float func_147651_i();

    public AttenuationType func_147656_j();

    public static enum AttenuationType {
        NONE("NONE", 0, 0),
        LINEAR("LINEAR", 1, 2);
        
        private final int field_148589_c;
        private static final AttenuationType[] $VALUES;
        private static final String __OBFID = "CL_00001126";

        static {
            $VALUES = new AttenuationType[]{NONE, LINEAR};
        }

        private AttenuationType(String p_i45110_1_, int p_i45110_2_, String p_i45110_3_, int n2, int n3) {
            this.field_148589_c = p_i45110_3_;
        }

        public int func_148586_a() {
            return this.field_148589_c;
        }
    }

}

