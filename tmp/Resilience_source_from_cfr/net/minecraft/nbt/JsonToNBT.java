/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.nbt;

import java.util.ArrayList;
import java.util.Stack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonToNBT {
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00001232";

    public static NBTBase func_150315_a(String p_150315_0_) throws NBTException {
        int var1 = JsonToNBT.func_150310_b(p_150315_0_ = p_150315_0_.trim());
        if (var1 != 1) {
            throw new NBTException("Encountered multiple top tags, only one expected");
        }
        Any var2 = null;
        var2 = p_150315_0_.startsWith("{") ? JsonToNBT.func_150316_a("tag", p_150315_0_) : JsonToNBT.func_150316_a(JsonToNBT.func_150313_b(p_150315_0_, false), JsonToNBT.func_150311_c(p_150315_0_, false));
        return var2.func_150489_a();
    }

    static int func_150310_b(String p_150310_0_) throws NBTException {
        int var1 = 0;
        boolean var2 = false;
        Stack<Character> var3 = new Stack<Character>();
        int var4 = 0;
        while (var4 < p_150310_0_.length()) {
            char var5 = p_150310_0_.charAt(var4);
            if (var5 == '\"') {
                if (var4 > 0 && p_150310_0_.charAt(var4 - 1) == '\\') {
                    if (!var2) {
                        throw new NBTException("Illegal use of \\\": " + p_150310_0_);
                    }
                } else {
                    var2 = !var2;
                }
            } else if (!var2) {
                if (var5 != '{' && var5 != '[') {
                    if (var5 == '}' && (var3.isEmpty() || ((Character)var3.pop()).charValue() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + p_150310_0_);
                    }
                    if (var5 == ']' && (var3.isEmpty() || ((Character)var3.pop()).charValue() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + p_150310_0_);
                    }
                } else {
                    if (var3.isEmpty()) {
                        ++var1;
                    }
                    var3.push(Character.valueOf(var5));
                }
            }
            ++var4;
        }
        if (var2) {
            throw new NBTException("Unbalanced quotation: " + p_150310_0_);
        }
        if (!var3.isEmpty()) {
            throw new NBTException("Unbalanced brackets: " + p_150310_0_);
        }
        if (var1 == 0 && !p_150310_0_.isEmpty()) {
            return 1;
        }
        return var1;
    }

    static Any func_150316_a(String p_150316_0_, String p_150316_1_) throws NBTException {
        p_150316_1_ = p_150316_1_.trim();
        JsonToNBT.func_150310_b(p_150316_1_);
        if (p_150316_1_.startsWith("{")) {
            if (!p_150316_1_.endsWith("}")) {
                throw new NBTException("Unable to locate ending bracket for: " + p_150316_1_);
            }
            p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
            Compound var7 = new Compound(p_150316_0_);
            while (p_150316_1_.length() > 0) {
                String var3 = JsonToNBT.func_150314_a(p_150316_1_, false);
                if (var3.length() <= 0) continue;
                String var4 = JsonToNBT.func_150313_b(var3, false);
                String var5 = JsonToNBT.func_150311_c(var3, false);
                var7.field_150491_b.add(JsonToNBT.func_150316_a(var4, var5));
                if (p_150316_1_.length() < var3.length() + 1) break;
                char var6 = p_150316_1_.charAt(var3.length());
                if (var6 != ',' && var6 != '{' && var6 != '}' && var6 != '[' && var6 != ']') {
                    throw new NBTException("Unexpected token '" + var6 + "' at: " + p_150316_1_.substring(var3.length()));
                }
                p_150316_1_ = p_150316_1_.substring(var3.length() + 1);
            }
            return var7;
        }
        if (p_150316_1_.startsWith("[") && !p_150316_1_.matches("\\[[-\\d|,\\s]+\\]")) {
            if (!p_150316_1_.endsWith("]")) {
                throw new NBTException("Unable to locate ending bracket for: " + p_150316_1_);
            }
            p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
            List var2 = new List(p_150316_0_);
            while (p_150316_1_.length() > 0) {
                String var3 = JsonToNBT.func_150314_a(p_150316_1_, true);
                if (var3.length() > 0) {
                    String var4 = JsonToNBT.func_150313_b(var3, true);
                    String var5 = JsonToNBT.func_150311_c(var3, true);
                    var2.field_150492_b.add(JsonToNBT.func_150316_a(var4, var5));
                    if (p_150316_1_.length() < var3.length() + 1) break;
                    char var6 = p_150316_1_.charAt(var3.length());
                    if (var6 != ',' && var6 != '{' && var6 != '}' && var6 != '[' && var6 != ']') {
                        throw new NBTException("Unexpected token '" + var6 + "' at: " + p_150316_1_.substring(var3.length()));
                    }
                    p_150316_1_ = p_150316_1_.substring(var3.length() + 1);
                    continue;
                }
                logger.debug(p_150316_1_);
            }
            return var2;
        }
        return new Primitive(p_150316_0_, p_150316_1_);
    }

    private static String func_150314_a(String p_150314_0_, boolean p_150314_1_) throws NBTException {
        int var2 = JsonToNBT.func_150312_a(p_150314_0_, ':');
        if (var2 < 0 && !p_150314_1_) {
            throw new NBTException("Unable to locate name/value separator for string: " + p_150314_0_);
        }
        int var3 = JsonToNBT.func_150312_a(p_150314_0_, ',');
        if (var3 >= 0 && var3 < var2 && !p_150314_1_) {
            throw new NBTException("Name error at: " + p_150314_0_);
        }
        if (p_150314_1_ && (var2 < 0 || var2 > var3)) {
            var2 = -1;
        }
        Stack<Character> var4 = new Stack<Character>();
        int var5 = var2 + 1;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        int var9 = 0;
        while (var5 < p_150314_0_.length()) {
            char var10 = p_150314_0_.charAt(var5);
            if (var10 == '\"') {
                if (var5 > 0 && p_150314_0_.charAt(var5 - 1) == '\\') {
                    if (!var6) {
                        throw new NBTException("Illegal use of \\\": " + p_150314_0_);
                    }
                } else {
                    boolean bl = var6 = !var6;
                    if (var6 && !var8) {
                        var7 = true;
                    }
                    if (!var6) {
                        var9 = var5;
                    }
                }
            } else if (!var6) {
                if (var10 != '{' && var10 != '[') {
                    if (var10 == '}' && (var4.isEmpty() || ((Character)var4.pop()).charValue() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + p_150314_0_);
                    }
                    if (var10 == ']' && (var4.isEmpty() || ((Character)var4.pop()).charValue() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + p_150314_0_);
                    }
                    if (var10 == ',' && var4.isEmpty()) {
                        return p_150314_0_.substring(0, var5);
                    }
                } else {
                    var4.push(Character.valueOf(var10));
                }
            }
            if (!Character.isWhitespace(var10)) {
                if (!var6 && var7 && var9 != var5) {
                    return p_150314_0_.substring(0, var9 + 1);
                }
                var8 = true;
            }
            ++var5;
        }
        return p_150314_0_.substring(0, var5);
    }

    private static String func_150313_b(String p_150313_0_, boolean p_150313_1_) throws NBTException {
        if (p_150313_1_ && ((p_150313_0_ = p_150313_0_.trim()).startsWith("{") || p_150313_0_.startsWith("["))) {
            return "";
        }
        int var2 = p_150313_0_.indexOf(58);
        if (var2 < 0) {
            if (p_150313_1_) {
                return "";
            }
            throw new NBTException("Unable to locate name/value separator for string: " + p_150313_0_);
        }
        return p_150313_0_.substring(0, var2).trim();
    }

    private static String func_150311_c(String p_150311_0_, boolean p_150311_1_) throws NBTException {
        if (p_150311_1_ && ((p_150311_0_ = p_150311_0_.trim()).startsWith("{") || p_150311_0_.startsWith("["))) {
            return p_150311_0_;
        }
        int var2 = p_150311_0_.indexOf(58);
        if (var2 < 0) {
            if (p_150311_1_) {
                return p_150311_0_;
            }
            throw new NBTException("Unable to locate name/value separator for string: " + p_150311_0_);
        }
        return p_150311_0_.substring(var2 + 1).trim();
    }

    private static int func_150312_a(String p_150312_0_, char p_150312_1_) {
        int var2 = 0;
        boolean var3 = false;
        while (var2 < p_150312_0_.length()) {
            char var4 = p_150312_0_.charAt(var2);
            if (var4 == '\"') {
                if (var2 <= 0 || p_150312_0_.charAt(var2 - 1) != '\\') {
                    var3 = !var3;
                }
            } else if (!var3) {
                if (var4 == p_150312_1_) {
                    return var2;
                }
                if (var4 == '{' || var4 == '[') {
                    return -1;
                }
            }
            ++var2;
        }
        return -1;
    }

    static abstract class Any {
        protected String field_150490_a;
        private static final String __OBFID = "CL_00001233";

        Any() {
        }

        public abstract NBTBase func_150489_a();
    }

    static class Compound
    extends Any {
        protected ArrayList field_150491_b = new ArrayList();
        private static final String __OBFID = "CL_00001234";

        public Compound(String p_i45137_1_) {
            this.field_150490_a = p_i45137_1_;
        }

        @Override
        public NBTBase func_150489_a() {
            NBTTagCompound var1 = new NBTTagCompound();
            for (Any var3 : this.field_150491_b) {
                var1.setTag(var3.field_150490_a, var3.func_150489_a());
            }
            return var1;
        }
    }

    static class List
    extends Any {
        protected ArrayList field_150492_b = new ArrayList();
        private static final String __OBFID = "CL_00001235";

        public List(String p_i45138_1_) {
            this.field_150490_a = p_i45138_1_;
        }

        @Override
        public NBTBase func_150489_a() {
            NBTTagList var1 = new NBTTagList();
            for (Any var3 : this.field_150492_b) {
                var1.appendTag(var3.func_150489_a());
            }
            return var1;
        }
    }

    static class Primitive
    extends Any {
        protected String field_150493_b;
        private static final String __OBFID = "CL_00001236";

        public Primitive(String p_i45139_1_, String p_i45139_2_) {
            this.field_150490_a = p_i45139_1_;
            this.field_150493_b = p_i45139_2_;
        }

        /*
         * Exception decompiling
         */
        @Override
        public NBTBase func_150489_a() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[CATCHBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:419)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:471)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2880)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:837)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:217)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:162)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:357)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:769)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:682)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:765)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:701)
            // org.benf.cfr.reader.Main.doJar(Main.java:134)
            // org.benf.cfr.reader.Main.main(Main.java:189)
            throw new IllegalStateException("Decompilation failed");
        }
    }

}

