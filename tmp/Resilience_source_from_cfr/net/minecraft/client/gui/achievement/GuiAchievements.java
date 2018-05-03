/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.achievement;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAchievements
extends GuiScreen
implements IProgressMeter {
    private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
    private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
    private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
    private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
    private static final ResourceLocation field_146561_C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    protected GuiScreen field_146562_a;
    protected int field_146555_f = 256;
    protected int field_146557_g = 202;
    protected int field_146563_h;
    protected int field_146564_i;
    protected float field_146570_r = 1.0f;
    protected double field_146569_s;
    protected double field_146568_t;
    protected double field_146567_u;
    protected double field_146566_v;
    protected double field_146565_w;
    protected double field_146573_x;
    private int field_146554_D;
    private StatFileWriter field_146556_E;
    private boolean field_146558_F = true;
    private static final String __OBFID = "CL_00000722";

    public GuiAchievements(GuiScreen p_i45026_1_, StatFileWriter p_i45026_2_) {
        this.field_146562_a = p_i45026_1_;
        this.field_146556_E = p_i45026_2_;
        int var3 = 141;
        int var4 = 141;
        this.field_146567_u = this.field_146565_w = (double)(AchievementList.openInventory.displayColumn * 24 - var3 / 2 - 12);
        this.field_146569_s = this.field_146565_w;
        this.field_146566_v = this.field_146573_x = (double)(AchievementList.openInventory.displayRow * 24 - var4 / 2);
        this.field_146568_t = this.field_146573_x;
    }

    @Override
    public void initGui() {
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (!this.field_146558_F && p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(this.field_146562_a);
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        } else {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (this.field_146558_F) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
            this.drawCenteredString(this.fontRendererObj, field_146510_b_[(int)(Minecraft.getSystemTime() / 150 % (long)field_146510_b_.length)], this.width / 2, this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
        } else {
            int var4;
            if (Mouse.isButtonDown((int)0)) {
                var4 = (this.width - this.field_146555_f) / 2;
                int var5 = (this.height - this.field_146557_g) / 2;
                int var6 = var4 + 8;
                int var7 = var5 + 17;
                if ((this.field_146554_D == 0 || this.field_146554_D == 1) && par1 >= var6 && par1 < var6 + 224 && par2 >= var7 && par2 < var7 + 155) {
                    if (this.field_146554_D == 0) {
                        this.field_146554_D = 1;
                    } else {
                        this.field_146567_u -= (double)((float)(par1 - this.field_146563_h) * this.field_146570_r);
                        this.field_146566_v -= (double)((float)(par2 - this.field_146564_i) * this.field_146570_r);
                        this.field_146565_w = this.field_146569_s = this.field_146567_u;
                        this.field_146573_x = this.field_146568_t = this.field_146566_v;
                    }
                    this.field_146563_h = par1;
                    this.field_146564_i = par2;
                }
            } else {
                this.field_146554_D = 0;
            }
            var4 = Mouse.getDWheel();
            float var11 = this.field_146570_r;
            if (var4 < 0) {
                this.field_146570_r += 0.25f;
            } else if (var4 > 0) {
                this.field_146570_r -= 0.25f;
            }
            this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0f, 2.0f);
            if (this.field_146570_r != var11) {
                float var10000 = var11 - this.field_146570_r;
                float var12 = var11 * (float)this.field_146555_f;
                float var8 = var11 * (float)this.field_146557_g;
                float var9 = this.field_146570_r * (float)this.field_146555_f;
                float var10 = this.field_146570_r * (float)this.field_146557_g;
                this.field_146567_u -= (double)((var9 - var12) * 0.5f);
                this.field_146566_v -= (double)((var10 - var8) * 0.5f);
                this.field_146565_w = this.field_146569_s = this.field_146567_u;
                this.field_146573_x = this.field_146568_t = this.field_146566_v;
            }
            if (this.field_146565_w < (double)field_146572_y) {
                this.field_146565_w = field_146572_y;
            }
            if (this.field_146573_x < (double)field_146571_z) {
                this.field_146573_x = field_146571_z;
            }
            if (this.field_146565_w >= (double)field_146559_A) {
                this.field_146565_w = field_146559_A - 1;
            }
            if (this.field_146573_x >= (double)field_146560_B) {
                this.field_146573_x = field_146560_B - 1;
            }
            this.drawDefaultBackground();
            this.func_146552_b(par1, par2, par3);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            this.func_146553_h();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
        }
    }

    @Override
    public void func_146509_g() {
        if (this.field_146558_F) {
            this.field_146558_F = false;
        }
    }

    @Override
    public void updateScreen() {
        if (!this.field_146558_F) {
            this.field_146569_s = this.field_146567_u;
            this.field_146568_t = this.field_146566_v;
            double var1 = this.field_146565_w - this.field_146567_u;
            double var3 = this.field_146573_x - this.field_146566_v;
            if (var1 * var1 + var3 * var3 < 4.0) {
                this.field_146567_u += var1;
                this.field_146566_v += var3;
            } else {
                this.field_146567_u += var1 * 0.85;
                this.field_146566_v += var3 * 0.85;
            }
        }
    }

    protected void func_146553_h() {
        int var1 = (this.width - this.field_146555_f) / 2;
        int var2 = (this.height - this.field_146557_g) / 2;
        this.fontRendererObj.drawString("Achievements", var1 + 15, var2 + 5, 4210752);
    }

    protected void func_146552_b(int p_146552_1_, int p_146552_2_, float p_146552_3_) {
        int var25;
        int var40;
        int var42;
        int var43;
        int var24;
        int var4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * (double)p_146552_3_);
        int var5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * (double)p_146552_3_);
        if (var4 < field_146572_y) {
            var4 = field_146572_y;
        }
        if (var5 < field_146571_z) {
            var5 = field_146571_z;
        }
        if (var4 >= field_146559_A) {
            var4 = field_146559_A - 1;
        }
        if (var5 >= field_146560_B) {
            var5 = field_146560_B - 1;
        }
        int var6 = (this.width - this.field_146555_f) / 2;
        int var7 = (this.height - this.field_146557_g) / 2;
        int var8 = var6 + 16;
        int var9 = var7 + 17;
        this.zLevel = 0.0f;
        GL11.glDepthFunc((int)518);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var8, (float)var9, (float)-200.0f);
        GL11.glScalef((float)(1.0f / this.field_146570_r), (float)(1.0f / this.field_146570_r), (float)0.0f);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        int var10 = var4 + 288 >> 4;
        int var11 = var5 + 288 >> 4;
        int var12 = (var4 + 288) % 16;
        int var13 = (var5 + 288) % 16;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        Random var19 = new Random();
        float var20 = 16.0f / this.field_146570_r;
        float var21 = 16.0f / this.field_146570_r;
        int var22 = 0;
        while ((float)var22 * var20 - (float)var13 < 155.0f) {
            float var23 = 0.6f - (float)(var11 + var22) / 25.0f * 0.3f;
            GL11.glColor4f((float)var23, (float)var23, (float)var23, (float)1.0f);
            var24 = 0;
            while ((float)var24 * var21 - (float)var12 < 224.0f) {
                var19.setSeed(this.mc.getSession().getPlayerID().hashCode() + var10 + var24 + (var11 + var22) * 16);
                var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
                IIcon var26 = Blocks.sand.getIcon(0, 0);
                if (var25 <= 37 && var11 + var22 != 35) {
                    if (var25 == 22) {
                        var26 = var19.nextInt(2) == 0 ? Blocks.diamond_ore.getIcon(0, 0) : Blocks.redstone_ore.getIcon(0, 0);
                    } else if (var25 == 10) {
                        var26 = Blocks.iron_ore.getIcon(0, 0);
                    } else if (var25 == 8) {
                        var26 = Blocks.coal_ore.getIcon(0, 0);
                    } else if (var25 > 4) {
                        var26 = Blocks.stone.getIcon(0, 0);
                    } else if (var25 > 0) {
                        var26 = Blocks.dirt.getIcon(0, 0);
                    }
                } else {
                    var26 = Blocks.bedrock.getIcon(0, 0);
                }
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModelRectFromIcon(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
                ++var24;
            }
            ++var22;
        }
        GL11.glEnable((int)2929);
        GL11.glDepthFunc((int)515);
        this.mc.getTextureManager().bindTexture(field_146561_C);
        var22 = 0;
        while (var22 < AchievementList.achievementList.size()) {
            Achievement var35 = (Achievement)AchievementList.achievementList.get(var22);
            if (var35.parentAchievement != null) {
                var24 = var35.displayColumn * 24 - var4 + 11;
                var25 = var35.displayRow * 24 - var5 + 11;
                var43 = var35.parentAchievement.displayColumn * 24 - var4 + 11;
                int var27 = var35.parentAchievement.displayRow * 24 - var5 + 11;
                boolean var28 = this.field_146556_E.hasAchievementUnlocked(var35);
                boolean var29 = this.field_146556_E.canUnlockAchievement(var35);
                int var30 = this.field_146556_E.func_150874_c(var35);
                if (var30 <= 4) {
                    int var31 = -16777216;
                    if (var28) {
                        var31 = -6250336;
                    } else if (var29) {
                        var31 = -16711936;
                    }
                    this.drawHorizontalLine(var24, var43, var25, var31);
                    this.drawVerticalLine(var43, var25, var27, var31);
                    if (var24 > var43) {
                        this.drawTexturedModalRect(var24 - 11 - 7, var25 - 5, 114, 234, 7, 11);
                    } else if (var24 < var43) {
                        this.drawTexturedModalRect(var24 + 11, var25 - 5, 107, 234, 7, 11);
                    } else if (var25 > var27) {
                        this.drawTexturedModalRect(var24 - 5, var25 - 11 - 7, 96, 234, 11, 7);
                    } else if (var25 < var27) {
                        this.drawTexturedModalRect(var24 - 5, var25 + 11, 96, 241, 11, 7);
                    }
                }
            }
            ++var22;
        }
        Achievement var34 = null;
        RenderItem var38 = new RenderItem();
        float var36 = (float)(p_146552_1_ - var8) * this.field_146570_r;
        float var37 = (float)(p_146552_2_ - var9) * this.field_146570_r;
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable((int)2896);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        var43 = 0;
        while (var43 < AchievementList.achievementList.size()) {
            block50 : {
                Achievement var41;
                float var45;
                block52 : {
                    int var30;
                    block55 : {
                        block54 : {
                            block53 : {
                                block51 : {
                                    var41 = (Achievement)AchievementList.achievementList.get(var43);
                                    var40 = var41.displayColumn * 24 - var4;
                                    var42 = var41.displayRow * 24 - var5;
                                    if (var40 < -24 || var42 < -24 || (float)var40 > 224.0f * this.field_146570_r || (float)var42 > 155.0f * this.field_146570_r) break block50;
                                    var30 = this.field_146556_E.func_150874_c(var41);
                                    if (!this.field_146556_E.hasAchievementUnlocked(var41)) break block51;
                                    var45 = 0.75f;
                                    GL11.glColor4f((float)var45, (float)var45, (float)var45, (float)1.0f);
                                    break block52;
                                }
                                if (!this.field_146556_E.canUnlockAchievement(var41)) break block53;
                                var45 = 1.0f;
                                GL11.glColor4f((float)var45, (float)var45, (float)var45, (float)1.0f);
                                break block52;
                            }
                            if (var30 >= 3) break block54;
                            var45 = 0.3f;
                            GL11.glColor4f((float)var45, (float)var45, (float)var45, (float)1.0f);
                            break block52;
                        }
                        if (var30 != 3) break block55;
                        var45 = 0.2f;
                        GL11.glColor4f((float)var45, (float)var45, (float)var45, (float)1.0f);
                        break block52;
                    }
                    if (var30 != 4) break block50;
                    var45 = 0.1f;
                    GL11.glColor4f((float)var45, (float)var45, (float)var45, (float)1.0f);
                }
                this.mc.getTextureManager().bindTexture(field_146561_C);
                if (var41.getSpecial()) {
                    this.drawTexturedModalRect(var40 - 2, var42 - 2, 26, 202, 26, 26);
                } else {
                    this.drawTexturedModalRect(var40 - 2, var42 - 2, 0, 202, 26, 26);
                }
                if (!this.field_146556_E.canUnlockAchievement(var41)) {
                    var45 = 0.1f;
                    GL11.glColor4f((float)var45, (float)var45, (float)var45, (float)1.0f);
                    var38.renderWithColor = false;
                }
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2884);
                var38.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var41.theItemStack, var40 + 3, var42 + 3);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glDisable((int)2896);
                if (!this.field_146556_E.canUnlockAchievement(var41)) {
                    var38.renderWithColor = true;
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                if (var36 >= (float)var40 && var36 <= (float)(var40 + 22) && var37 >= (float)var42 && var37 <= (float)(var42 + 22)) {
                    var34 = var41;
                }
            }
            ++var43;
        }
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_146561_C);
        this.drawTexturedModalRect(var6, var7, 0, 0, this.field_146555_f, this.field_146557_g);
        this.zLevel = 0.0f;
        GL11.glDepthFunc((int)515);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3553);
        super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);
        if (var34 != null) {
            String var44 = var34.func_150951_e().getUnformattedText();
            String var39 = var34.getDescription();
            var40 = p_146552_1_ + 12;
            var42 = p_146552_2_ - 4;
            int var30 = this.field_146556_E.func_150874_c(var34);
            if (!this.field_146556_E.canUnlockAchievement(var34)) {
                if (var30 == 3) {
                    var44 = I18n.format("achievement.unknown", new Object[0]);
                    int var31 = Math.max(this.fontRendererObj.getStringWidth(var44), 120);
                    String var32 = new ChatComponentTranslation("achievement.requires", var34.parentAchievement.func_150951_e()).getUnformattedText();
                    int var33 = this.fontRendererObj.splitStringWidth(var32, var31);
                    this.drawGradientRect(var40 - 3, var42 - 3, var40 + var31 + 3, var42 + var33 + 12 + 3, -1073741824, -1073741824);
                    this.fontRendererObj.drawSplitString(var32, var40, var42 + 12, var31, -9416624);
                } else if (var30 < 3) {
                    int var31 = Math.max(this.fontRendererObj.getStringWidth(var44), 120);
                    String var32 = new ChatComponentTranslation("achievement.requires", var34.parentAchievement.func_150951_e()).getUnformattedText();
                    int var33 = this.fontRendererObj.splitStringWidth(var32, var31);
                    this.drawGradientRect(var40 - 3, var42 - 3, var40 + var31 + 3, var42 + var33 + 12 + 3, -1073741824, -1073741824);
                    this.fontRendererObj.drawSplitString(var32, var40, var42 + 12, var31, -9416624);
                } else {
                    var44 = null;
                }
            } else {
                int var31 = Math.max(this.fontRendererObj.getStringWidth(var44), 120);
                int var46 = this.fontRendererObj.splitStringWidth(var39, var31);
                if (this.field_146556_E.hasAchievementUnlocked(var34)) {
                    var46 += 12;
                }
                this.drawGradientRect(var40 - 3, var42 - 3, var40 + var31 + 3, var42 + var46 + 3 + 12, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(var39, var40, var42 + 12, var31, -6250336);
                if (this.field_146556_E.hasAchievementUnlocked(var34)) {
                    this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken", new Object[0]), var40, var42 + var46 + 4, -7302913);
                }
            }
            if (var44 != null) {
                this.fontRendererObj.drawStringWithShadow(var44, var40, var42, this.field_146556_E.canUnlockAchievement(var34) ? (var34.getSpecial() ? -128 : -1) : (var34.getSpecial() ? -8355776 : -8355712));
            }
        }
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return !this.field_146558_F;
    }
}

