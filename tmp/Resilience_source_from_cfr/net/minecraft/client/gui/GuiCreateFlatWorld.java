/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiFlatPresets;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.opengl.GL11;

public class GuiCreateFlatWorld
extends GuiScreen {
    private static RenderItem field_146392_a = new RenderItem();
    private final GuiCreateWorld field_146385_f;
    private FlatGeneratorInfo field_146387_g = FlatGeneratorInfo.getDefaultFlatGenerator();
    private String field_146393_h;
    private String field_146394_i;
    private String field_146391_r;
    private Details field_146390_s;
    private GuiButton field_146389_t;
    private GuiButton field_146388_u;
    private GuiButton field_146386_v;
    private static final String __OBFID = "CL_00000687";

    public GuiCreateFlatWorld(GuiCreateWorld par1GuiCreateWorld, String par2Str) {
        this.field_146385_f = par1GuiCreateWorld;
        this.func_146383_a(par2Str);
    }

    public String func_146384_e() {
        return this.field_146387_g.toString();
    }

    public void func_146383_a(String p_146383_1_) {
        this.field_146387_g = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.field_146393_h = I18n.format("createWorld.customize.flat.title", new Object[0]);
        this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
        this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
        this.field_146390_s = new Details();
        this.field_146389_t = new GuiButton(2, this.width / 2 - 154, this.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.addLayer", new Object[0])) + " (NYI)");
        this.buttonList.add(this.field_146389_t);
        this.field_146388_u = new GuiButton(3, this.width / 2 - 50, this.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.editLayer", new Object[0])) + " (NYI)");
        this.buttonList.add(this.field_146388_u);
        this.field_146386_v = new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0]));
        this.buttonList.add(this.field_146386_v);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146388_u.field_146125_m = false;
        this.field_146389_t.field_146125_m = false;
        this.field_146387_g.func_82645_d();
        this.func_146375_g();
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        int var2 = this.field_146387_g.getFlatLayers().size() - this.field_146390_s.field_148228_k - 1;
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(this.field_146385_f);
        } else if (p_146284_1_.id == 0) {
            this.field_146385_f.field_146334_a = this.func_146384_e();
            this.mc.displayGuiScreen(this.field_146385_f);
        } else if (p_146284_1_.id == 5) {
            this.mc.displayGuiScreen(new GuiFlatPresets(this));
        } else if (p_146284_1_.id == 4 && this.func_146382_i()) {
            this.field_146387_g.getFlatLayers().remove(var2);
            this.field_146390_s.field_148228_k = Math.min(this.field_146390_s.field_148228_k, this.field_146387_g.getFlatLayers().size() - 1);
        }
        this.field_146387_g.func_82645_d();
        this.func_146375_g();
    }

    public void func_146375_g() {
        boolean var1;
        this.field_146386_v.enabled = var1 = this.func_146382_i();
        this.field_146388_u.enabled = var1;
        this.field_146388_u.enabled = false;
        this.field_146389_t.enabled = false;
    }

    private boolean func_146382_i() {
        if (this.field_146390_s.field_148228_k > -1 && this.field_146390_s.field_148228_k < this.field_146387_g.getFlatLayers().size()) {
            return true;
        }
        return false;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.field_146390_s.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, this.field_146393_h, this.width / 2, 8, 16777215);
        int var4 = this.width / 2 - 92 - 16;
        this.drawString(this.fontRendererObj, this.field_146394_i, var4, 32, 16777215);
        this.drawString(this.fontRendererObj, this.field_146391_r, var4 + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    class Details
    extends GuiSlot {
        public int field_148228_k;
        private static final String __OBFID = "CL_00000688";

        public Details() {
            super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43, GuiCreateFlatWorld.this.height - 60, 24);
            this.field_148228_k = -1;
        }

        private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_) {
            this.func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
            GL11.glEnable((int)32826);
            if (p_148225_3_ != null) {
                RenderHelper.enableGUIStandardItemLighting();
                field_146392_a.renderItemIntoGUI(GuiCreateFlatWorld.this.fontRendererObj, GuiCreateFlatWorld.this.mc.getTextureManager(), p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
                RenderHelper.disableStandardItemLighting();
            }
            GL11.glDisable((int)32826);
        }

        private void func_148226_e(int p_148226_1_, int p_148226_2_) {
            this.func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
        }

        private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GuiCreateFlatWorld.this.mc.getTextureManager().bindTexture(Gui.statIcons);
            float var5 = 0.0078125f;
            float var6 = 0.0078125f;
            boolean var7 = true;
            boolean var8 = true;
            Tessellator var9 = Tessellator.instance;
            var9.startDrawingQuads();
            var9.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 18, GuiCreateFlatWorld.this.zLevel, (float)(p_148224_3_ + 0) * 0.0078125f, (float)(p_148224_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 18, GuiCreateFlatWorld.this.zLevel, (float)(p_148224_3_ + 18) * 0.0078125f, (float)(p_148224_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 0, GuiCreateFlatWorld.this.zLevel, (float)(p_148224_3_ + 18) * 0.0078125f, (float)(p_148224_4_ + 0) * 0.0078125f);
            var9.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 0, GuiCreateFlatWorld.this.zLevel, (float)(p_148224_3_ + 0) * 0.0078125f, (float)(p_148224_4_ + 0) * 0.0078125f);
            var9.draw();
        }

        @Override
        protected int getSize() {
            return GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size();
        }

        @Override
        protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
            this.field_148228_k = p_148144_1_;
            GuiCreateFlatWorld.this.func_146375_g();
        }

        @Override
        protected boolean isSelected(int p_148131_1_) {
            if (p_148131_1_ == this.field_148228_k) {
                return true;
            }
            return false;
        }

        @Override
        protected void drawBackground() {
        }

        @Override
        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            FlatLayerInfo var8 = (FlatLayerInfo)GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().get(GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - p_148126_1_ - 1);
            Item var9 = Item.getItemFromBlock(var8.func_151536_b());
            ItemStack var10 = var8.func_151536_b() == Blocks.air ? null : new ItemStack(var9, 1, var8.getFillBlockMeta());
            String var11 = var10 != null && var9 != null ? var9.getItemStackDisplayName(var10) : "Air";
            this.func_148225_a(p_148126_2_, p_148126_3_, var10);
            GuiCreateFlatWorld.this.fontRendererObj.drawString(var11, p_148126_2_ + 18 + 5, p_148126_3_ + 3, 16777215);
            String var12 = p_148126_1_ == 0 ? I18n.format("createWorld.customize.flat.layer.top", var8.getLayerCount()) : (p_148126_1_ == GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - 1 ? I18n.format("createWorld.customize.flat.layer.bottom", var8.getLayerCount()) : I18n.format("createWorld.customize.flat.layer", var8.getLayerCount()));
            GuiCreateFlatWorld.this.fontRendererObj.drawString(var12, p_148126_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(var12), p_148126_3_ + 3, 16777215);
        }

        @Override
        protected int func_148137_d() {
            return this.field_148155_a - 70;
        }
    }

}

