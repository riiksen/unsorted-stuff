/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class JsonException
extends IOException {
    private final List field_151383_a = Lists.newArrayList();
    private final String field_151382_b;
    private static final String __OBFID = "CL_00001414";

    public JsonException(String p_i45279_1_) {
        this.field_151383_a.add(new Entry(null));
        this.field_151382_b = p_i45279_1_;
    }

    public JsonException(String p_i45280_1_, Throwable p_i45280_2_) {
        super(p_i45280_2_);
        this.field_151383_a.add(new Entry(null));
        this.field_151382_b = p_i45280_1_;
    }

    public void func_151380_a(String p_151380_1_) {
        ((Entry)this.field_151383_a.get(0)).func_151373_a(p_151380_1_);
    }

    public void func_151381_b(String p_151381_1_) {
        Entry.access$1((Entry)this.field_151383_a.get(0), p_151381_1_);
        this.field_151383_a.add(0, new Entry(null));
    }

    @Override
    public String getMessage() {
        return "Invalid " + ((Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString() + ": " + this.field_151382_b;
    }

    public static JsonException func_151379_a(Exception p_151379_0_) {
        if (p_151379_0_ instanceof JsonException) {
            return (JsonException)p_151379_0_;
        }
        String var1 = p_151379_0_.getMessage();
        if (p_151379_0_ instanceof FileNotFoundException) {
            var1 = "File not found";
        }
        return new JsonException(var1, p_151379_0_);
    }

    public static class Entry {
        private String field_151376_a = null;
        private final List field_151375_b = Lists.newArrayList();
        private static final String __OBFID = "CL_00001416";

        private Entry() {
        }

        private void func_151373_a(String p_151373_1_) {
            this.field_151375_b.add(0, p_151373_1_);
        }

        public String func_151372_b() {
            return StringUtils.join((Iterable)this.field_151375_b, (String)"->");
        }

        public String toString() {
            return this.field_151376_a != null ? (!this.field_151375_b.isEmpty() ? String.valueOf(this.field_151376_a) + " " + this.func_151372_b() : this.field_151376_a) : (!this.field_151375_b.isEmpty() ? "(Unknown file) " + this.func_151372_b() : "(Unknown file)");
        }

        Entry(Object p_i45278_1_) {
            this();
        }

        static /* synthetic */ void access$1(Entry entry, String string) {
            entry.field_151376_a = string;
        }
    }

}

