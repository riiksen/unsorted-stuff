/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityHugeExplodeFX
extends EntityFX {
    private int timeSinceStart;
    private int maximumTime = 8;
    private static final String __OBFID = "CL_00000911";

    public EntityHugeExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
    }

    @Override
    public void onUpdate() {
        int var1 = 0;
        while (var1 < 6) {
            double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double var4 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double var6 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            this.worldObj.spawnParticle("largeexplode", var2, var4, var6, (float)this.timeSinceStart / (float)this.maximumTime, 0.0, 0.0);
            ++var1;
        }
        ++this.timeSinceStart;
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}

