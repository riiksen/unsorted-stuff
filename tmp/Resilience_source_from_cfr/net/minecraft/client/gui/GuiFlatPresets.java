/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiFlatPresets
extends GuiScreen {
    private static RenderItem field_146437_a = new RenderItem();
    private static final List field_146431_f = new ArrayList();
    private final GuiCreateFlatWorld field_146432_g;
    private String field_146438_h;
    private String field_146439_i;
    private String field_146436_r;
    private ListSlot field_146435_s;
    private GuiButton field_146434_t;
    private GuiTextField field_146433_u;
    private static final String __OBFID = "CL_00000704";

    static {
        GuiFlatPresets.func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList("village"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock));
        GuiFlatPresets.func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        GuiFlatPresets.func_146421_a("Water World", Item.getItemFromBlock(Blocks.flowing_water), BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        GuiFlatPresets.func_146421_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BiomeGenBase.plains, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        GuiFlatPresets.func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        GuiFlatPresets.func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone));
        GuiFlatPresets.func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        GuiFlatPresets.func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
    }

    public GuiFlatPresets(GuiCreateFlatWorld par1GuiCreateFlatWorld) {
        this.field_146432_g = par1GuiCreateFlatWorld;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_146438_h = I18n.format("createWorld.customize.presets.title", new Object[0]);
        this.field_146439_i = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.field_146433_u = new GuiTextField(this.fontRendererObj, 50, 40, this.width - 100, 20);
        this.field_146435_s = new ListSlot();
        this.field_146433_u.func_146203_f(1230);
        this.field_146433_u.setText(this.field_146432_g.func_146384_e());
        this.field_146434_t = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0]));
        this.buttonList.add(this.field_146434_t);
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_146426_g();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        this.field_146433_u.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (!this.field_146433_u.textboxKeyTyped(par1, par2)) {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0 && this.func_146430_p()) {
            this.field_146432_g.func_146383_a(this.field_146433_u.getText());
            this.mc.displayGuiScreen(this.field_146432_g);
        } else if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(this.field_146432_g);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.field_146435_s.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, this.field_146438_h, this.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.field_146439_i, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
        this.field_146433_u.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void updateScreen() {
        this.field_146433_u.updateCursorCounter();
        super.updateScreen();
    }

    public void func_146426_g() {
        boolean var1;
        this.field_146434_t.enabled = var1 = this.func_146430_p();
    }

    private boolean func_146430_p() {
        if ((this.field_146435_s.field_148175_k <= -1 || this.field_146435_s.field_148175_k >= field_146431_f.size()) && this.field_146433_u.getText().length() <= 1) {
            return false;
        }
        return true;
    }

    private static /* varargs */ void func_146425_a(String p_146425_0_, Item p_146425_1_, BiomeGenBase p_146425_2_, FlatLayerInfo ... p_146425_3_) {
        GuiFlatPresets.func_146421_a(p_146425_0_, p_146425_1_, p_146425_2_, null, p_146425_3_);
    }

    private static /* varargs */ void func_146421_a(String p_146421_0_, Item p_146421_1_, BiomeGenBase p_146421_2_, List p_146421_3_, FlatLayerInfo ... p_146421_4_) {
        FlatGeneratorInfo var5 = new FlatGeneratorInfo();
        int var6 = p_146421_4_.length - 1;
        while (var6 >= 0) {
            var5.getFlatLayers().add(p_146421_4_[var6]);
            --var6;
        }
        var5.setBiome(p_146421_2_.biomeID);
        var5.func_82645_d();
        if (p_146421_3_ != null) {
            for (String var7 : p_146421_3_) {
                var5.getWorldFeatures().put(var7, new HashMap());
            }
        }
        field_146431_f.add(new LayerItem(p_146421_1_, p_146421_0_, var5.toString()));
    }

    static /* synthetic */ ListSlot access$3(GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146435_s;
    }

    static class LayerItem {
        public Item field_148234_a;
        public String field_148232_b;
        public String field_148233_c;
        private static final String __OBFID = "CL_00000705";

        public LayerItem(Item p_i45022_1_, String p_i45022_2_, String p_i45022_3_) {
            this.field_148234_a = p_i45022_1_;
            this.field_148232_b = p_i45022_2_;
            this.field_148233_c = p_i45022_3_;
        }
    }

    class ListSlot
    extends GuiSlot {
        public int field_148175_k;
        private static final String __OBFID = "CL_00000706";

        public ListSlot() {
            super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80, GuiFlatPresets.this.height - 37, 24);
            this.field_148175_k = -1;
        }

        private void func_148172_a(int p_148172_1_, int p_148172_2_, Item p_148172_3_) {
            this.func_148173_e(p_148172_1_ + 1, p_148172_2_ + 1);
            GL11.glEnable((int)32826);
            RenderHelper.enableGUIStandardItemLighting();
            field_146437_a.renderItemIntoGUI(GuiFlatPresets.this.fontRendererObj, GuiFlatPresets.this.mc.getTextureManager(), new ItemStack(p_148172_3_, 1, 0), p_148172_1_ + 2, p_148172_2_ + 2);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable((int)32826);
        }

        private void func_148173_e(int p_148173_1_, int p_148173_2_) {
            this.func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
        }

        private void func_148171_c(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GuiFlatPresets.this.mc.getTextureManager().bindTexture(Gui.statIcons);
            float var5 = 0.0078125f;
            float var6 = 0.0078125f;
            boolean var7 = true;
            boolean var8 = true;
            Tessellator var9 = Tessellator.instance;
            var9.startDrawingQuads();
            var9.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 18, GuiFlatPresets.this.zLevel, (float)(p_148171_3_ + 0) * 0.0078125f, (float)(p_148171_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 18, GuiFlatPresets.this.zLevel, (float)(p_148171_3_ + 18) * 0.0078125f, (float)(p_148171_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 0, GuiFlatPresets.this.zLevel, (float)(p_148171_3_ + 18) * 0.0078125f, (float)(p_148171_4_ + 0) * 0.0078125f);
            var9.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 0, GuiFlatPresets.this.zLevel, (float)(p_148171_3_ + 0) * 0.0078125f, (float)(p_148171_4_ + 0) * 0.0078125f);
            var9.draw();
        }

        @Override
        protected int getSize() {
            return field_146431_f.size();
        }

        @Override
        protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
            this.field_148175_k = p_148144_1_;
            GuiFlatPresets.this.func_146426_g();
            GuiFlatPresets.this.field_146433_u.setText(((LayerItem)GuiFlatPresets.access$1().get((int)GuiFlatPresets.access$3((GuiFlatPresets)GuiFlatPresets.this).field_148175_k)).field_148233_c);
        }

        @Override
        protected boolean isSelected(int p_148131_1_) {
            if (p_148131_1_ == this.field_148175_k) {
                return true;
            }
            return false;
        }

        @Override
        protected void drawBackground() {
        }

        @Override
        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            LayerItem var8 = (LayerItem)field_146431_f.get(p_148126_1_);
            this.func_148172_a(p_148126_2_, p_148126_3_, var8.field_148234_a);
            GuiFlatPresets.this.fontRendererObj.drawString(var8.field_148232_b, p_148126_2_ + 18 + 5, p_148126_3_ + 6, 16777215);
        }
    }

}

