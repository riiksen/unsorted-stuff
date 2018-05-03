/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererPiston
extends TileEntitySpecialRenderer {
    private RenderBlocks field_147516_b;
    private static final String __OBFID = "CL_00000969";

    public void renderTileEntityAt(TileEntityPiston p_147515_1_, double p_147515_2_, double p_147515_4_, double p_147515_6_, float p_147515_8_) {
        Block var9 = p_147515_1_.func_145861_a();
        if (var9.getMaterial() != Material.air && p_147515_1_.func_145860_a(p_147515_8_) < 1.0f) {
            Tessellator var10 = Tessellator.instance;
            this.bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2884);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GL11.glShadeModel((int)7425);
            } else {
                GL11.glShadeModel((int)7424);
            }
            var10.startDrawingQuads();
            var10.setTranslation((float)p_147515_2_ - (float)p_147515_1_.field_145851_c + p_147515_1_.func_145865_b(p_147515_8_), (float)p_147515_4_ - (float)p_147515_1_.field_145848_d + p_147515_1_.func_145862_c(p_147515_8_), (float)p_147515_6_ - (float)p_147515_1_.field_145849_e + p_147515_1_.func_145859_d(p_147515_8_));
            var10.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            if (var9 == Blocks.piston_head && p_147515_1_.func_145860_a(p_147515_8_) < 0.5f) {
                this.field_147516_b.renderPistonExtensionAllFaces(var9, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e, false);
            } else if (p_147515_1_.func_145867_d() && !p_147515_1_.func_145868_b()) {
                Blocks.piston_head.func_150086_a(((BlockPistonBase)var9).func_150073_e());
                this.field_147516_b.renderPistonExtensionAllFaces(Blocks.piston_head, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e, p_147515_1_.func_145860_a(p_147515_8_) < 0.5f);
                Blocks.piston_head.func_150087_e();
                var10.setTranslation((float)p_147515_2_ - (float)p_147515_1_.field_145851_c, (float)p_147515_4_ - (float)p_147515_1_.field_145848_d, (float)p_147515_6_ - (float)p_147515_1_.field_145849_e);
                this.field_147516_b.renderPistonBaseAllFaces(var9, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e);
            } else {
                this.field_147516_b.renderBlockAllFaces(var9, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e);
            }
            var10.setTranslation(0.0, 0.0, 0.0);
            var10.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }

    @Override
    public void func_147496_a(World p_147496_1_) {
        this.field_147516_b = new RenderBlocks(p_147496_1_);
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityPiston)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

