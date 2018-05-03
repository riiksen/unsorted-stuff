/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEnderCrystal
extends Render {
    private static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    private ModelBase field_76995_b;
    private static final String __OBFID = "CL_00000987";

    public RenderEnderCrystal() {
        this.shadowSize = 0.5f;
        this.field_76995_b = new ModelEnderCrystal(0.0f, true);
    }

    public void doRender(EntityEnderCrystal par1EntityEnderCrystal, double par2, double par4, double par6, float par8, float par9) {
        float var10 = (float)par1EntityEnderCrystal.innerRotation + par9;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        this.bindTexture(enderCrystalTextures);
        float var11 = MathHelper.sin(var10 * 0.2f) / 2.0f + 0.5f;
        var11 += var11 * var11;
        this.field_76995_b.render(par1EntityEnderCrystal, 0.0f, var10 * 3.0f, var11 * 0.2f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityEnderCrystal par1EntityEnderCrystal) {
        return enderCrystalTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityEnderCrystal)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityEnderCrystal)par1Entity, par2, par4, par6, par8, par9);
    }
}

