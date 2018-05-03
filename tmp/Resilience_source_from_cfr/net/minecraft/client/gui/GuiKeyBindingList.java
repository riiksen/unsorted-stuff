/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList
extends GuiListExtended {
    private final GuiControls field_148191_k;
    private final Minecraft field_148189_l;
    private final GuiListExtended.IGuiListEntry[] field_148190_m;
    private int field_148188_n = 0;
    private static final String __OBFID = "CL_00000732";

    public GuiKeyBindingList(GuiControls p_i45031_1_, Minecraft p_i45031_2_) {
        super(p_i45031_2_, p_i45031_1_.width, p_i45031_1_.height, 63, p_i45031_1_.height - 32, 20);
        this.field_148191_k = p_i45031_1_;
        this.field_148189_l = p_i45031_2_;
        Object[] var3 = (KeyBinding[])ArrayUtils.clone((Object[])p_i45031_2_.gameSettings.keyBindings);
        this.field_148190_m = new GuiListExtended.IGuiListEntry[var3.length + KeyBinding.func_151467_c().size()];
        Arrays.sort(var3);
        int var4 = 0;
        String var5 = null;
        Object[] var6 = var3;
        int var7 = var3.length;
        int var8 = 0;
        while (var8 < var7) {
            int var11;
            Object var9 = var6[var8];
            String var10 = var9.getKeyCategory();
            if (!var10.equals(var5)) {
                var5 = var10;
                this.field_148190_m[var4++] = new CategoryEntry(var10);
            }
            if ((var11 = p_i45031_2_.fontRenderer.getStringWidth(I18n.format(var9.getKeyDescription(), new Object[0]))) > this.field_148188_n) {
                this.field_148188_n = var11;
            }
            this.field_148190_m[var4++] = new KeyEntry((KeyBinding)var9, null);
            ++var8;
        }
    }

    @Override
    protected int getSize() {
        return this.field_148190_m.length;
    }

    @Override
    public GuiListExtended.IGuiListEntry func_148180_b(int p_148180_1_) {
        return this.field_148190_m[p_148180_1_];
    }

    @Override
    protected int func_148137_d() {
        return super.func_148137_d() + 15;
    }

    @Override
    public int func_148139_c() {
        return super.func_148139_c() + 32;
    }

    static /* synthetic */ GuiControls access$1(GuiKeyBindingList guiKeyBindingList) {
        return guiKeyBindingList.field_148191_k;
    }

    public class CategoryEntry
    implements GuiListExtended.IGuiListEntry {
        private final String field_148285_b;
        private final int field_148286_c;
        private static final String __OBFID = "CL_00000734";

        public CategoryEntry(String p_i45028_2_) {
            this.field_148285_b = I18n.format(p_i45028_2_, new Object[0]);
            this.field_148286_c = GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).fontRenderer.getStringWidth(this.field_148285_b);
        }

        @Override
        public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_) {
            GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).fontRenderer.drawString(this.field_148285_b, GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).currentScreen.width / 2 - this.field_148286_c / 2, p_148279_3_ + p_148279_5_ - GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).fontRenderer.FONT_HEIGHT - 1, 16777215);
        }

        @Override
        public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return false;
        }

        @Override
        public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
        }
    }

    public class KeyEntry
    implements GuiListExtended.IGuiListEntry {
        private final KeyBinding field_148282_b;
        private final String field_148283_c;
        private final GuiButton field_148280_d;
        private final GuiButton field_148281_e;
        private static final String __OBFID = "CL_00000735";

        private KeyEntry(KeyBinding p_i45029_2_) {
            this.field_148282_b = p_i45029_2_;
            this.field_148283_c = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
            this.field_148280_d = new GuiButton(0, 0, 0, 75, 18, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
            this.field_148281_e = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
        }

        @Override
        public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_) {
            boolean var10 = GuiKeyBindingList.access$1((GuiKeyBindingList)GuiKeyBindingList.this).field_146491_f == this.field_148282_b;
            GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).fontRenderer.drawString(this.field_148283_c, p_148279_2_ + 90 - GuiKeyBindingList.this.field_148188_n, p_148279_3_ + p_148279_5_ / 2 - GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).fontRenderer.FONT_HEIGHT / 2, 16777215);
            this.field_148281_e.field_146128_h = p_148279_2_ + 190;
            this.field_148281_e.field_146129_i = p_148279_3_;
            this.field_148281_e.enabled = this.field_148282_b.getKeyCode() != this.field_148282_b.getKeyCodeDefault();
            this.field_148281_e.drawButton(GuiKeyBindingList.this.field_148189_l, p_148279_7_, p_148279_8_);
            this.field_148280_d.field_146128_h = p_148279_2_ + 105;
            this.field_148280_d.field_146129_i = p_148279_3_;
            this.field_148280_d.displayString = GameSettings.getKeyDisplayString(this.field_148282_b.getKeyCode());
            boolean var11 = false;
            if (this.field_148282_b.getKeyCode() != 0) {
                KeyBinding[] var12 = GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).gameSettings.keyBindings;
                int var13 = var12.length;
                int var14 = 0;
                while (var14 < var13) {
                    KeyBinding var15 = var12[var14];
                    if (var15 != this.field_148282_b && var15.getKeyCode() == this.field_148282_b.getKeyCode()) {
                        var11 = true;
                        break;
                    }
                    ++var14;
                }
            }
            if (var10) {
                this.field_148280_d.displayString = (Object)((Object)EnumChatFormatting.WHITE) + "> " + (Object)((Object)EnumChatFormatting.YELLOW) + this.field_148280_d.displayString + (Object)((Object)EnumChatFormatting.WHITE) + " <";
            } else if (var11) {
                this.field_148280_d.displayString = (Object)((Object)EnumChatFormatting.RED) + this.field_148280_d.displayString;
            }
            this.field_148280_d.drawButton(GuiKeyBindingList.this.field_148189_l, p_148279_7_, p_148279_8_);
        }

        @Override
        public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            if (this.field_148280_d.mousePressed(GuiKeyBindingList.this.field_148189_l, p_148278_2_, p_148278_3_)) {
                GuiKeyBindingList.access$1((GuiKeyBindingList)GuiKeyBindingList.this).field_146491_f = this.field_148282_b;
                return true;
            }
            if (this.field_148281_e.mousePressed(GuiKeyBindingList.this.field_148189_l, p_148278_2_, p_148278_3_)) {
                GuiKeyBindingList.access$0((GuiKeyBindingList)GuiKeyBindingList.this).gameSettings.setKeyCodeSave(this.field_148282_b, this.field_148282_b.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                return true;
            }
            return false;
        }

        @Override
        public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
            this.field_148280_d.mouseReleased(p_148277_2_, p_148277_3_);
            this.field_148281_e.mouseReleased(p_148277_2_, p_148277_3_);
        }

        KeyEntry(KeyBinding p_i45030_2_, Object p_i45030_3_) {
            this(p_i45030_2_);
        }
    }

}

