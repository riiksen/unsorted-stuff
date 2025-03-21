/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntitySkullRenderer
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147537_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation field_147534_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final ResourceLocation field_147535_e = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation field_147532_f = new ResourceLocation("textures/entity/creeper/creeper.png");
    public static TileEntitySkullRenderer field_147536_b;
    private ModelSkeletonHead field_147533_g = new ModelSkeletonHead(0, 0, 64, 32);
    private ModelSkeletonHead field_147538_h = new ModelSkeletonHead(0, 0, 64, 64);
    private static final String __OBFID = "CL_00000971";

    public void renderTileEntityAt(TileEntitySkull p_147531_1_, double p_147531_2_, double p_147531_4_, double p_147531_6_, float p_147531_8_) {
        this.func_147530_a((float)p_147531_2_, (float)p_147531_4_, (float)p_147531_6_, p_147531_1_.getBlockMetadata() & 7, (float)(p_147531_1_.func_145906_b() * 360) / 16.0f, p_147531_1_.func_145904_a(), p_147531_1_.func_145907_c());
    }

    @Override
    public void func_147497_a(TileEntityRendererDispatcher p_147497_1_) {
        super.func_147497_a(p_147497_1_);
        field_147536_b = this;
    }

    public void func_147530_a(float p_147530_1_, float p_147530_2_, float p_147530_3_, int p_147530_4_, float p_147530_5_, int p_147530_6_, String p_147530_7_) {
        ModelSkeletonHead var8 = this.field_147533_g;
        switch (p_147530_6_) {
            default: {
                this.bindTexture(field_147537_c);
                break;
            }
            case 1: {
                this.bindTexture(field_147534_d);
                break;
            }
            case 2: {
                this.bindTexture(field_147535_e);
                var8 = this.field_147538_h;
                break;
            }
            case 3: {
                ResourceLocation var9 = AbstractClientPlayer.locationStevePng;
                if (p_147530_7_ != null && p_147530_7_.length() > 0) {
                    var9 = AbstractClientPlayer.getLocationSkull(p_147530_7_);
                    AbstractClientPlayer.getDownloadImageSkin(var9, p_147530_7_);
                }
                this.bindTexture(var9);
                break;
            }
            case 4: {
                this.bindTexture(field_147532_f);
            }
        }
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        if (p_147530_4_ != 1) {
            switch (p_147530_4_) {
                case 2: {
                    GL11.glTranslatef((float)(p_147530_1_ + 0.5f), (float)(p_147530_2_ + 0.25f), (float)(p_147530_3_ + 0.74f));
                    break;
                }
                case 3: {
                    GL11.glTranslatef((float)(p_147530_1_ + 0.5f), (float)(p_147530_2_ + 0.25f), (float)(p_147530_3_ + 0.26f));
                    p_147530_5_ = 180.0f;
                    break;
                }
                case 4: {
                    GL11.glTranslatef((float)(p_147530_1_ + 0.74f), (float)(p_147530_2_ + 0.25f), (float)(p_147530_3_ + 0.5f));
                    p_147530_5_ = 270.0f;
                    break;
                }
                default: {
                    GL11.glTranslatef((float)(p_147530_1_ + 0.26f), (float)(p_147530_2_ + 0.25f), (float)(p_147530_3_ + 0.5f));
                    p_147530_5_ = 90.0f;
                    break;
                }
            }
        } else {
            GL11.glTranslatef((float)(p_147530_1_ + 0.5f), (float)p_147530_2_, (float)(p_147530_3_ + 0.5f));
        }
        float var10 = 0.0625f;
        GL11.glEnable((int)32826);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        GL11.glEnable((int)3008);
        var8.render(null, 0.0f, 0.0f, 0.0f, p_147530_5_, 0.0f, var10);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntitySkull)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

