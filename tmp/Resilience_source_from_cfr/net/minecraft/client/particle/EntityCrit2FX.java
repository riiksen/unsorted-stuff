/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityCrit2FX
extends EntityFX {
    private Entity theEntity;
    private int currentLife;
    private int maximumLife;
    private String particleName;
    private static final String __OBFID = "CL_00000899";

    public EntityCrit2FX(World par1World, Entity par2Entity) {
        this(par1World, par2Entity, "crit");
    }

    public EntityCrit2FX(World par1World, Entity par2Entity, String par3Str) {
        super(par1World, par2Entity.posX, par2Entity.boundingBox.minY + (double)(par2Entity.height / 2.0f), par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        this.theEntity = par2Entity;
        this.maximumLife = 3;
        this.particleName = par3Str;
        this.onUpdate();
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
    }

    @Override
    public void onUpdate() {
        int var1 = 0;
        while (var1 < 16) {
            double var6;
            double var4;
            double var2 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (var2 * var2 + (var4 = (double)(this.rand.nextFloat() * 2.0f - 1.0f)) * var4 + (var6 = (double)(this.rand.nextFloat() * 2.0f - 1.0f)) * var6 <= 1.0) {
                double var8 = this.theEntity.posX + var2 * (double)this.theEntity.width / 4.0;
                double var10 = this.theEntity.boundingBox.minY + (double)(this.theEntity.height / 2.0f) + var4 * (double)this.theEntity.height / 4.0;
                double var12 = this.theEntity.posZ + var6 * (double)this.theEntity.width / 4.0;
                this.worldObj.spawnParticle(this.particleName, var8, var10, var12, var2, var4 + 0.2, var6);
            }
            ++var1;
        }
        ++this.currentLife;
        if (this.currentLife >= this.maximumLife) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}

