/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.world.World;

public class EntitySplashFX
extends EntityRainFX {
    private static final String __OBFID = "CL_00000927";

    public EntitySplashFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6);
        this.particleGravity = 0.04f;
        this.nextTextureIndexX();
        if (par10 == 0.0 && (par8 != 0.0 || par12 != 0.0)) {
            this.motionX = par8;
            this.motionY = par10 + 0.1;
            this.motionZ = par12;
        }
    }
}

