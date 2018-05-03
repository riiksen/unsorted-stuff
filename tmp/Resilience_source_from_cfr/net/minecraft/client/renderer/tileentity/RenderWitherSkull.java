/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderWitherSkull
extends Render {
    private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
    private static final String __OBFID = "CL_00001035";

    private float func_82400_a(float par1, float par2, float par3) {
        float var4 = par2 - par1;
        while (var4 < -180.0f) {
            var4 += 360.0f;
        }
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return par1 + par3 * var4;
    }

    public void doRender(EntityWitherSkull par1EntityWitherSkull, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        float var10 = this.func_82400_a(par1EntityWitherSkull.prevRotationYaw, par1EntityWitherSkull.rotationYaw, par9);
        float var11 = par1EntityWitherSkull.prevRotationPitch + (par1EntityWitherSkull.rotationPitch - par1EntityWitherSkull.prevRotationPitch) * par9;
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        float var12 = 0.0625f;
        GL11.glEnable((int)32826);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        GL11.glEnable((int)3008);
        this.bindEntityTexture(par1EntityWitherSkull);
        this.skeletonHeadModel.render(par1EntityWitherSkull, 0.0f, 0.0f, 0.0f, var10, var11, var12);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityWitherSkull par1EntityWitherSkull) {
        return par1EntityWitherSkull.isInvulnerable() ? invulnerableWitherTextures : witherTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityWitherSkull)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWitherSkull)par1Entity, par2, par4, par6, par8, par9);
    }
}

