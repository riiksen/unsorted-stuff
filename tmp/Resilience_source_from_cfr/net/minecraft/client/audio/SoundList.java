/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.audio.SoundCategory;

public class SoundList {
    private final List field_148577_a = Lists.newArrayList();
    private boolean field_148575_b;
    private SoundCategory field_148576_c;
    private static final String __OBFID = "CL_00001121";

    public List func_148570_a() {
        return this.field_148577_a;
    }

    public boolean func_148574_b() {
        return this.field_148575_b;
    }

    public void func_148572_a(boolean p_148572_1_) {
        this.field_148575_b = p_148572_1_;
    }

    public SoundCategory func_148573_c() {
        return this.field_148576_c;
    }

    public void func_148571_a(SoundCategory p_148571_1_) {
        this.field_148576_c = p_148571_1_;
    }

    public static class SoundEntry {
        private String field_148569_a;
        private float field_148567_b = 1.0f;
        private float field_148568_c = 1.0f;
        private int field_148565_d = 1;
        private Type field_148566_e = Type.FILE;
        private boolean field_148564_f = false;
        private static final String __OBFID = "CL_00001122";

        public String func_148556_a() {
            return this.field_148569_a;
        }

        public void func_148561_a(String p_148561_1_) {
            this.field_148569_a = p_148561_1_;
        }

        public float func_148558_b() {
            return this.field_148567_b;
        }

        public void func_148553_a(float p_148553_1_) {
            this.field_148567_b = p_148553_1_;
        }

        public float func_148560_c() {
            return this.field_148568_c;
        }

        public void func_148559_b(float p_148559_1_) {
            this.field_148568_c = p_148559_1_;
        }

        public int func_148555_d() {
            return this.field_148565_d;
        }

        public void func_148554_a(int p_148554_1_) {
            this.field_148565_d = p_148554_1_;
        }

        public Type func_148563_e() {
            return this.field_148566_e;
        }

        public void func_148562_a(Type p_148562_1_) {
            this.field_148566_e = p_148562_1_;
        }

        public boolean func_148552_f() {
            return this.field_148564_f;
        }

        public void func_148557_a(boolean p_148557_1_) {
            this.field_148564_f = p_148557_1_;
        }

        public static enum Type {
            FILE("FILE", 0, "file"),
            SOUND_EVENT("SOUND_EVENT", 1, "event");
            
            private final String field_148583_c;
            private static final Type[] $VALUES;
            private static final String __OBFID = "CL_00001123";

            static {
                $VALUES = new Type[]{FILE, SOUND_EVENT};
            }

            private Type(String p_i45109_1_, int p_i45109_2_, String p_i45109_3_, int n2, String string2) {
                this.field_148583_c = p_i45109_3_;
            }

            public static Type func_148580_a(String p_148580_0_) {
                Type[] var1 = Type.values();
                int var2 = var1.length;
                int var3 = 0;
                while (var3 < var2) {
                    Type var4 = var1[var3];
                    if (var4.field_148583_c.equals(p_148580_0_)) {
                        return var4;
                    }
                    ++var3;
                }
                return null;
            }
        }

    }

}

