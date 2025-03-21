/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityPickupFX
extends EntityFX {
    private Entity entityToPickUp;
    private Entity entityPickingUp;
    private int age;
    private int maxAge;
    private float yOffs;
    private static final String __OBFID = "CL_00000930";

    public EntityPickupFX(World par1World, Entity par2Entity, Entity par3Entity, float par4) {
        super(par1World, par2Entity.posX, par2Entity.posY, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        this.entityToPickUp = par2Entity;
        this.entityPickingUp = par3Entity;
        this.maxAge = 3;
        this.yOffs = par4;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        float var8 = ((float)this.age + par2) / (float)this.maxAge;
        var8 *= var8;
        double var9 = this.entityToPickUp.posX;
        double var11 = this.entityToPickUp.posY;
        double var13 = this.entityToPickUp.posZ;
        double var15 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * (double)par2;
        double var17 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * (double)par2 + (double)this.yOffs;
        double var19 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * (double)par2;
        double var21 = var9 + (var15 - var9) * (double)var8;
        double var23 = var11 + (var17 - var11) * (double)var8;
        double var25 = var13 + (var19 - var13) * (double)var8;
        int var27 = this.getBrightnessForRender(par2);
        int var28 = var27 % 65536;
        int var29 = var27 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var28 / 1.0f, (float)var29 / 1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderManager.instance.func_147940_a(this.entityToPickUp, (float)(var21 -= interpPosX), (float)(var23 -= interpPosY), (float)(var25 -= interpPosZ), this.entityToPickUp.rotationYaw, par2);
    }

    @Override
    public void onUpdate() {
        ++this.age;
        if (this.age == this.maxAge) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}

