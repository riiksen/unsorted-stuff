/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public class ClickEvent {
    private final Action action;
    private final String value;
    private static final String __OBFID = "CL_00001260";

    public ClickEvent(Action p_i45156_1_, String p_i45156_2_) {
        this.action = p_i45156_1_;
        this.value = p_i45156_2_;
    }

    public Action getAction() {
        return this.action;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (par1Obj != null && this.getClass() == par1Obj.getClass()) {
            ClickEvent var2 = (ClickEvent)par1Obj;
            if (this.action != var2.action) {
                return false;
            }
            if (this.value != null ? !this.value.equals(var2.value) : var2.value != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String toString() {
        return "ClickEvent{action=" + (Object)((Object)this.action) + ", value='" + this.value + '\'' + '}';
    }

    public int hashCode() {
        int var1 = this.action.hashCode();
        var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
        return var1;
    }

    public static enum Action {
        OPEN_URL("OPEN_URL", 0, "open_url", true),
        OPEN_FILE("OPEN_FILE", 1, "open_file", false),
        RUN_COMMAND("RUN_COMMAND", 2, "run_command", true),
        SUGGEST_COMMAND("SUGGEST_COMMAND", 3, "suggest_command", true);
        
        private static final Map nameMapping;
        private final boolean allowedInChat;
        private final String canonicalName;
        private static final Action[] $VALUES;
        private static final String __OBFID = "CL_00001261";

        static {
            nameMapping = Maps.newHashMap();
            $VALUES = new Action[]{OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND};
            Action[] var0 = Action.values();
            int var1 = var0.length;
            int var2 = 0;
            while (var2 < var1) {
                Action var3 = var0[var2];
                nameMapping.put(var3.getCanonicalName(), var3);
                ++var2;
            }
        }

        private Action(String p_i45155_1_, int p_i45155_2_, String p_i45155_3_, int p_i45155_4_, String string2, boolean bl) {
            this.canonicalName = p_i45155_3_;
            this.allowedInChat = p_i45155_4_;
        }

        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }

        public String getCanonicalName() {
            return this.canonicalName;
        }

        public static Action getValueByCanonicalName(String p_150672_0_) {
            return (Action)((Object)nameMapping.get(p_150672_0_));
        }
    }

}

