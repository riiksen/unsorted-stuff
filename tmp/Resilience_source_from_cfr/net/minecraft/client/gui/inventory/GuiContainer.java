/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.inventory;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainer
extends GuiScreen {
    protected static final ResourceLocation field_147001_a = new ResourceLocation("textures/gui/container/inventory.png");
    protected int field_146999_f = 176;
    protected int field_147000_g = 166;
    public Container field_147002_h;
    protected int field_147003_i;
    protected int field_147009_r;
    private Slot field_147006_u;
    private Slot field_147005_v;
    private boolean field_147004_w;
    private ItemStack field_147012_x;
    private int field_147011_y;
    private int field_147010_z;
    private Slot field_146989_A;
    private long field_146990_B;
    private ItemStack field_146991_C;
    private Slot field_146985_D;
    private long field_146986_E;
    protected final Set field_147008_s = new HashSet();
    protected boolean field_147007_t;
    private int field_146987_F;
    private int field_146988_G;
    private boolean field_146995_H;
    private int field_146996_I;
    private long field_146997_J;
    private Slot field_146998_K;
    private int field_146992_L;
    private boolean field_146993_M;
    private ItemStack field_146994_N;
    private static final String __OBFID = "CL_00000737";

    public GuiContainer(Container par1Container) {
        this.field_147002_h = par1Container;
        this.field_146995_H = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.field_147002_h;
        this.field_147003_i = (this.width - this.field_146999_f) / 2;
        this.field_147009_r = (this.height - this.field_147000_g) / 2;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        int var11;
        ItemStack var16;
        this.drawDefaultBackground();
        int var4 = this.field_147003_i;
        int var5 = this.field_147009_r;
        this.func_146976_a(par3, par1, par2);
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)32826);
        this.field_147006_u = null;
        int var6 = 240;
        int var7 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0f, (float)var7 / 1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int var8 = 0;
        while (var8 < this.field_147002_h.inventorySlots.size()) {
            Slot var9 = (Slot)this.field_147002_h.inventorySlots.get(var8);
            this.func_146977_a(var9);
            if (this.func_146981_a(var9, par1, par2) && var9.func_111238_b()) {
                this.field_147006_u = var9;
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                int var10 = var9.xDisplayPosition;
                var11 = var9.yDisplayPosition;
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
                this.drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
            }
            ++var8;
        }
        this.func_146979_b(par1, par2);
        InventoryPlayer var15 = this.mc.thePlayer.inventory;
        ItemStack itemStack = var16 = this.field_147012_x == null ? var15.getItemStack() : this.field_147012_x;
        if (var16 != null) {
            int var17 = 8;
            var11 = this.field_147012_x == null ? 8 : 16;
            String var12 = null;
            if (this.field_147012_x != null && this.field_147004_w) {
                var16 = var16.copy();
                var16.stackSize = MathHelper.ceiling_float_int((float)var16.stackSize / 2.0f);
            } else if (this.field_147007_t && this.field_147008_s.size() > 1) {
                var16 = var16.copy();
                var16.stackSize = this.field_146996_I;
                if (var16.stackSize == 0) {
                    var12 = (Object)((Object)EnumChatFormatting.YELLOW) + "0";
                }
            }
            this.func_146982_a(var16, par1 - var4 - var17, par2 - var5 - var11, var12);
        }
        if (this.field_146991_C != null) {
            float var18 = (float)(Minecraft.getSystemTime() - this.field_146990_B) / 100.0f;
            if (var18 >= 1.0f) {
                var18 = 1.0f;
                this.field_146991_C = null;
            }
            var11 = this.field_146989_A.xDisplayPosition - this.field_147011_y;
            int var20 = this.field_146989_A.yDisplayPosition - this.field_147010_z;
            int var13 = this.field_147011_y + (int)((float)var11 * var18);
            int var14 = this.field_147010_z + (int)((float)var20 * var18);
            this.func_146982_a(this.field_146991_C, var13, var14, null);
        }
        GL11.glPopMatrix();
        if (var15.getItemStack() == null && this.field_147006_u != null && this.field_147006_u.getHasStack()) {
            ItemStack var19 = this.field_147006_u.getStack();
            this.func_146285_a(var19, par1, par2);
        }
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        RenderHelper.enableStandardItemLighting();
    }

    private void func_146982_a(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)32.0f);
        this.zLevel = 200.0f;
        GuiContainer.itemRender.zLevel = 200.0f;
        itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - (this.field_147012_x == null ? 0 : 8), p_146982_4_);
        this.zLevel = 0.0f;
        GuiContainer.itemRender.zLevel = 0.0f;
    }

    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
    }

    protected abstract void func_146976_a(float var1, int var2, int var3);

    private void func_146977_a(Slot p_146977_1_) {
        IIcon var9;
        int var2 = p_146977_1_.xDisplayPosition;
        int var3 = p_146977_1_.yDisplayPosition;
        ItemStack var4 = p_146977_1_.getStack();
        boolean var5 = false;
        boolean var6 = p_146977_1_ == this.field_147005_v && this.field_147012_x != null && !this.field_147004_w;
        ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
        String var8 = null;
        if (p_146977_1_ == this.field_147005_v && this.field_147012_x != null && this.field_147004_w && var4 != null) {
            var4 = var4.copy();
            var4.stackSize /= 2;
        } else if (this.field_147007_t && this.field_147008_s.contains(p_146977_1_) && var7 != null) {
            if (this.field_147008_s.size() == 1) {
                return;
            }
            if (Container.func_94527_a(p_146977_1_, var7, true) && this.field_147002_h.canDragIntoSlot(p_146977_1_)) {
                var4 = var7.copy();
                var5 = true;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, var4, p_146977_1_.getStack() == null ? 0 : p_146977_1_.getStack().stackSize);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var8 = (Object)((Object)EnumChatFormatting.YELLOW) + var4.getMaxStackSize();
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > p_146977_1_.getSlotStackLimit()) {
                    var8 = (Object)((Object)EnumChatFormatting.YELLOW) + p_146977_1_.getSlotStackLimit();
                    var4.stackSize = p_146977_1_.getSlotStackLimit();
                }
            } else {
                this.field_147008_s.remove(p_146977_1_);
                this.func_146980_g();
            }
        }
        this.zLevel = 100.0f;
        GuiContainer.itemRender.zLevel = 100.0f;
        if (var4 == null && (var9 = p_146977_1_.getBackgroundIconIndex()) != null) {
            GL11.glDisable((int)2896);
            this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
            this.drawTexturedModelRectFromIcon(var2, var3, var9, 16, 16);
            GL11.glEnable((int)2896);
            var6 = true;
        }
        if (!var6) {
            if (var5) {
                GuiContainer.drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
            }
            GL11.glEnable((int)2929);
            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var4, var2, var3);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var4, var2, var3, var8);
        }
        GuiContainer.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }

    private void func_146980_g() {
        ItemStack var1 = this.mc.thePlayer.inventory.getItemStack();
        if (var1 != null && this.field_147007_t) {
            this.field_146996_I = var1.stackSize;
            for (Slot var3 : this.field_147008_s) {
                ItemStack var4 = var1.copy();
                int var5 = var3.getStack() == null ? 0 : var3.getStack().stackSize;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, var4, var5);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > var3.getSlotStackLimit()) {
                    var4.stackSize = var3.getSlotStackLimit();
                }
                this.field_146996_I -= var4.stackSize - var5;
            }
        }
    }

    private Slot func_146975_c(int p_146975_1_, int p_146975_2_) {
        int var3 = 0;
        while (var3 < this.field_147002_h.inventorySlots.size()) {
            Slot var4 = (Slot)this.field_147002_h.inventorySlots.get(var3);
            if (this.func_146981_a(var4, p_146975_1_, p_146975_2_)) {
                return var4;
            }
            ++var3;
        }
        return null;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        boolean var4 = par3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        Slot var5 = this.func_146975_c(par1, par2);
        long var6 = Minecraft.getSystemTime();
        this.field_146993_M = this.field_146998_K == var5 && var6 - this.field_146997_J < 250 && this.field_146992_L == par3;
        this.field_146995_H = false;
        if (par3 == 0 || par3 == 1 || var4) {
            int var8 = this.field_147003_i;
            int var9 = this.field_147009_r;
            boolean var10 = par1 < var8 || par2 < var9 || par1 >= var8 + this.field_146999_f || par2 >= var9 + this.field_147000_g;
            int var11 = -1;
            if (var5 != null) {
                var11 = var5.slotNumber;
            }
            if (var10) {
                var11 = -999;
            }
            if (this.mc.gameSettings.touchscreen && var10 && this.mc.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (var11 != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (var5 != null && var5.getHasStack()) {
                        this.field_147005_v = var5;
                        this.field_147012_x = null;
                        this.field_147004_w = par3 == 1;
                    } else {
                        this.field_147005_v = null;
                    }
                } else if (!this.field_147007_t) {
                    if (this.mc.thePlayer.inventory.getItemStack() == null) {
                        if (par3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            this.func_146984_a(var5, var11, par3, 3);
                        } else {
                            boolean var12 = var11 != -999 && (Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54));
                            int var13 = 0;
                            if (var12) {
                                this.field_146994_N = var5 != null && var5.getHasStack() ? var5.getStack() : null;
                                var13 = 1;
                            } else if (var11 == -999) {
                                var13 = 4;
                            }
                            this.func_146984_a(var5, var11, par3, var13);
                        }
                        this.field_146995_H = true;
                    } else {
                        this.field_147007_t = true;
                        this.field_146988_G = par3;
                        this.field_147008_s.clear();
                        if (par3 == 0) {
                            this.field_146987_F = 0;
                        } else if (par3 == 1) {
                            this.field_146987_F = 1;
                        }
                    }
                }
            }
        }
        this.field_146998_K = var5;
        this.field_146997_J = var6;
        this.field_146992_L = par3;
    }

    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
        Slot var6 = this.func_146975_c(p_146273_1_, p_146273_2_);
        ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
        if (this.field_147005_v != null && this.mc.gameSettings.touchscreen) {
            if (p_146273_3_ == 0 || p_146273_3_ == 1) {
                if (this.field_147012_x == null) {
                    if (var6 != this.field_147005_v) {
                        this.field_147012_x = this.field_147005_v.getStack().copy();
                    }
                } else if (this.field_147012_x.stackSize > 1 && var6 != null && Container.func_94527_a(var6, this.field_147012_x, false)) {
                    long var8 = Minecraft.getSystemTime();
                    if (this.field_146985_D == var6) {
                        if (var8 - this.field_146986_E > 500) {
                            this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, 0, 0);
                            this.func_146984_a(var6, var6.slotNumber, 1, 0);
                            this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, 0, 0);
                            this.field_146986_E = var8 + 750;
                            --this.field_147012_x.stackSize;
                        }
                    } else {
                        this.field_146985_D = var6;
                        this.field_146986_E = var8;
                    }
                }
            }
        } else if (this.field_147007_t && var6 != null && var7 != null && var7.stackSize > this.field_147008_s.size() && Container.func_94527_a(var6, var7, true) && var6.isItemValid(var7) && this.field_147002_h.canDragIntoSlot(var6)) {
            this.field_147008_s.add(var6);
            this.func_146980_g();
        }
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        Slot var4 = this.func_146975_c(p_146286_1_, p_146286_2_);
        int var5 = this.field_147003_i;
        int var6 = this.field_147009_r;
        boolean var7 = p_146286_1_ < var5 || p_146286_2_ < var6 || p_146286_1_ >= var5 + this.field_146999_f || p_146286_2_ >= var6 + this.field_147000_g;
        int var8 = -1;
        if (var4 != null) {
            var8 = var4.slotNumber;
        }
        if (var7) {
            var8 = -999;
        }
        if (this.field_146993_M && var4 != null && p_146286_3_ == 0 && this.field_147002_h.func_94530_a(null, var4)) {
            if (GuiContainer.isShiftKeyDown()) {
                if (var4 != null && var4.inventory != null && this.field_146994_N != null) {
                    for (Slot var10 : this.field_147002_h.inventorySlots) {
                        if (var10 == null || !var10.canTakeStack(this.mc.thePlayer) || !var10.getHasStack() || var10.inventory != var4.inventory || !Container.func_94527_a(var10, this.field_146994_N, true)) continue;
                        this.func_146984_a(var10, var10.slotNumber, p_146286_3_, 1);
                    }
                }
            } else {
                this.func_146984_a(var4, var8, p_146286_3_, 6);
            }
            this.field_146993_M = false;
            this.field_146997_J = 0;
        } else {
            if (this.field_147007_t && this.field_146988_G != p_146286_3_) {
                this.field_147007_t = false;
                this.field_147008_s.clear();
                this.field_146995_H = true;
                return;
            }
            if (this.field_146995_H) {
                this.field_146995_H = false;
                return;
            }
            if (this.field_147005_v != null && this.mc.gameSettings.touchscreen) {
                if (p_146286_3_ == 0 || p_146286_3_ == 1) {
                    if (this.field_147012_x == null && var4 != this.field_147005_v) {
                        this.field_147012_x = this.field_147005_v.getStack();
                    }
                    boolean var9 = Container.func_94527_a(var4, this.field_147012_x, false);
                    if (var8 != -1 && this.field_147012_x != null && var9) {
                        this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, p_146286_3_, 0);
                        this.func_146984_a(var4, var8, 0, 0);
                        if (this.mc.thePlayer.inventory.getItemStack() != null) {
                            this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, p_146286_3_, 0);
                            this.field_147011_y = p_146286_1_ - var5;
                            this.field_147010_z = p_146286_2_ - var6;
                            this.field_146989_A = this.field_147005_v;
                            this.field_146991_C = this.field_147012_x;
                            this.field_146990_B = Minecraft.getSystemTime();
                        } else {
                            this.field_146991_C = null;
                        }
                    } else if (this.field_147012_x != null) {
                        this.field_147011_y = p_146286_1_ - var5;
                        this.field_147010_z = p_146286_2_ - var6;
                        this.field_146989_A = this.field_147005_v;
                        this.field_146991_C = this.field_147012_x;
                        this.field_146990_B = Minecraft.getSystemTime();
                    }
                    this.field_147012_x = null;
                    this.field_147005_v = null;
                }
            } else if (this.field_147007_t && !this.field_147008_s.isEmpty()) {
                this.func_146984_a(null, -999, Container.func_94534_d(0, this.field_146987_F), 5);
                for (Slot var10 : this.field_147008_s) {
                    this.func_146984_a(var10, var10.slotNumber, Container.func_94534_d(1, this.field_146987_F), 5);
                }
                this.func_146984_a(null, -999, Container.func_94534_d(2, this.field_146987_F), 5);
            } else if (this.mc.thePlayer.inventory.getItemStack() != null) {
                if (p_146286_3_ == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    this.func_146984_a(var4, var8, p_146286_3_, 3);
                } else {
                    boolean var9;
                    boolean bl = var9 = var8 != -999 && (Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54));
                    if (var9) {
                        this.field_146994_N = var4 != null && var4.getHasStack() ? var4.getStack() : null;
                    }
                    this.func_146984_a(var4, var8, p_146286_3_, var9 ? 1 : 0);
                }
            }
        }
        if (this.mc.thePlayer.inventory.getItemStack() == null) {
            this.field_146997_J = 0;
        }
        this.field_147007_t = false;
    }

    private boolean func_146981_a(Slot p_146981_1_, int p_146981_2_, int p_146981_3_) {
        return this.func_146978_c(p_146981_1_.xDisplayPosition, p_146981_1_.yDisplayPosition, 16, 16, p_146981_2_, p_146981_3_);
    }

    protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_) {
        int var7 = this.field_147003_i;
        int var8 = this.field_147009_r;
        if ((p_146978_5_ -= var7) >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && p_146978_6_ >= p_146978_2_ - 1 && (p_146978_6_ -= var8) < p_146978_2_ + p_146978_4_ + 1) {
            return true;
        }
        return false;
    }

    protected void func_146984_a(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_) {
        if (p_146984_1_ != null) {
            p_146984_2_ = p_146984_1_.slotNumber;
        }
        this.mc.playerController.windowClick(this.field_147002_h.windowId, p_146984_2_, p_146984_3_, p_146984_4_, this.mc.thePlayer);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
        this.func_146983_a(par2);
        if (this.field_147006_u != null && this.field_147006_u.getHasStack()) {
            if (par2 == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, 0, 3);
            } else if (par2 == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, GuiContainer.isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }

    protected boolean func_146983_a(int p_146983_1_) {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.field_147006_u != null) {
            int var2 = 0;
            while (var2 < 9) {
                if (p_146983_1_ == this.mc.gameSettings.keyBindsHotbar[var2].getKeyCode()) {
                    this.func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, var2, 2);
                    return true;
                }
                ++var2;
            }
        }
        return false;
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.field_147002_h.onContainerClosed(this.mc.thePlayer);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }
}

