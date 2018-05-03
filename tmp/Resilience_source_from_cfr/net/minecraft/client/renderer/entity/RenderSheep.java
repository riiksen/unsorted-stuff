/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSheep
extends RenderLiving {
    private static final ResourceLocation sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
    private static final String __OBFID = "CL_00001021";

    public RenderSheep(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }

    protected int shouldRenderPass(EntitySheep par1EntitySheep, int par2, float par3) {
        if (par2 == 0 && !par1EntitySheep.getSheared()) {
            this.bindTexture(sheepTextures);
            int var4 = par1EntitySheep.getFleeceColor();
            GL11.glColor3f((float)EntitySheep.fleeceColorTable[var4][0], (float)EntitySheep.fleeceColorTable[var4][1], (float)EntitySheep.fleeceColorTable[var4][2]);
            return 1;
        }
        return -1;
    }

    protected ResourceLocation getEntityTexture(EntitySheep par1EntitySheep) {
        return shearedSheepTextures;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntitySheep)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntitySheep)par1Entity);
    }
}

