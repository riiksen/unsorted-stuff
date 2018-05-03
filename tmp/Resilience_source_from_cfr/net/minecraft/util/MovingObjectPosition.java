/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;

public class MovingObjectPosition {
    public MovingObjectType typeOfHit;
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    public Vec3 hitVec;
    public Entity entityHit;
    private static final String __OBFID = "CL_00000610";

    public MovingObjectPosition(int par1, int par2, int par3, int par4, Vec3 par5Vec3) {
        this(par1, par2, par3, par4, par5Vec3, true);
    }

    public MovingObjectPosition(int p_i45481_1_, int p_i45481_2_, int p_i45481_3_, int p_i45481_4_, Vec3 p_i45481_5_, boolean p_i45481_6_) {
        this.typeOfHit = p_i45481_6_ ? MovingObjectType.BLOCK : MovingObjectType.MISS;
        this.blockX = p_i45481_1_;
        this.blockY = p_i45481_2_;
        this.blockZ = p_i45481_3_;
        this.sideHit = p_i45481_4_;
        this.hitVec = p_i45481_5_.myVec3LocalPool.getVecFromPool(p_i45481_5_.xCoord, p_i45481_5_.yCoord, p_i45481_5_.zCoord);
    }

    public MovingObjectPosition(Entity par1Entity) {
        this(par1Entity, par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY, par1Entity.posZ));
    }

    public MovingObjectPosition(Entity p_i45482_1_, Vec3 p_i45482_2_) {
        this.typeOfHit = MovingObjectType.ENTITY;
        this.entityHit = p_i45482_1_;
        this.hitVec = p_i45482_2_;
    }

    public String toString() {
        return "HitResult{type=" + (Object)((Object)this.typeOfHit) + ", x=" + this.blockX + ", y=" + this.blockY + ", z=" + this.blockZ + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public static enum MovingObjectType {
        MISS("MISS", 0),
        BLOCK("BLOCK", 1),
        ENTITY("ENTITY", 2);
        
        private static final MovingObjectType[] $VALUES;
        private static final String __OBFID = "CL_00000611";

        static {
            $VALUES = new MovingObjectType[]{MISS, BLOCK, ENTITY};
        }

        private MovingObjectType(String par1Str, int par2, String string2, int n2) {
        }
    }

}

