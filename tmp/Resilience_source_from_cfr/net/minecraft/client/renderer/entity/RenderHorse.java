/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHorse
extends RenderLiving {
    private static final Map field_110852_a = Maps.newHashMap();
    private static final ResourceLocation whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
    private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
    private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
    private static final ResourceLocation zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
    private static final ResourceLocation skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
    private static final String __OBFID = "CL_00001000";

    public RenderHorse(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    protected void preRenderCallback(EntityHorse par1EntityHorse, float par2) {
        float var3 = 1.0f;
        int var4 = par1EntityHorse.getHorseType();
        if (var4 == 1) {
            var3 *= 0.87f;
        } else if (var4 == 2) {
            var3 *= 0.92f;
        }
        GL11.glScalef((float)var3, (float)var3, (float)var3);
        super.preRenderCallback(par1EntityHorse, par2);
    }

    protected void renderModel(EntityHorse par1EntityHorse, float par2, float par3, float par4, float par5, float par6, float par7) {
        if (par1EntityHorse.isInvisible()) {
            this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityHorse);
        } else {
            this.bindEntityTexture(par1EntityHorse);
            this.mainModel.render(par1EntityHorse, par2, par3, par4, par5, par6, par7);
        }
    }

    protected ResourceLocation getEntityTexture(EntityHorse par1EntityHorse) {
        if (!par1EntityHorse.func_110239_cn()) {
            switch (par1EntityHorse.getHorseType()) {
                default: {
                    return whiteHorseTextures;
                }
                case 1: {
                    return donkeyTextures;
                }
                case 2: {
                    return muleTextures;
                }
                case 3: {
                    return zombieHorseTextures;
                }
                case 4: 
            }
            return skeletonHorseTextures;
        }
        return this.func_110848_b(par1EntityHorse);
    }

    private ResourceLocation func_110848_b(EntityHorse par1EntityHorse) {
        String var2 = par1EntityHorse.getHorseTexture();
        ResourceLocation var3 = (ResourceLocation)field_110852_a.get(var2);
        if (var3 == null) {
            var3 = new ResourceLocation(var2);
            Minecraft.getMinecraft().getTextureManager().loadTexture(var3, new LayeredTexture(par1EntityHorse.getVariantTexturePaths()));
            field_110852_a.put(var2, var3);
        }
        return var3;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityHorse)par1EntityLivingBase, par2);
    }

    @Override
    protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.renderModel((EntityHorse)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityHorse)par1Entity);
    }
}

