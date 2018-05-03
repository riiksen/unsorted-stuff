/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class Teleporter {
    private final WorldServer worldServerInstance;
    private final Random random;
    private final LongHashMap destinationCoordinateCache = new LongHashMap();
    private final List destinationCoordinateKeys = new ArrayList();
    private static final String __OBFID = "CL_00000153";

    public Teleporter(WorldServer par1WorldServer) {
        this.worldServerInstance = par1WorldServer;
        this.random = new Random(par1WorldServer.getSeed());
    }

    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        if (this.worldServerInstance.provider.dimensionId != 1) {
            if (!this.placeInExistingPortal(par1Entity, par2, par4, par6, par8)) {
                this.makePortal(par1Entity);
                this.placeInExistingPortal(par1Entity, par2, par4, par6, par8);
            }
        } else {
            int var9 = MathHelper.floor_double(par1Entity.posX);
            int var10 = MathHelper.floor_double(par1Entity.posY) - 1;
            int var11 = MathHelper.floor_double(par1Entity.posZ);
            int var12 = 1;
            int var13 = 0;
            int var14 = -2;
            while (var14 <= 2) {
                int var15 = -2;
                while (var15 <= 2) {
                    int var16 = -1;
                    while (var16 < 3) {
                        int var17 = var9 + var15 * var12 + var14 * var13;
                        int var18 = var10 + var16;
                        int var19 = var11 + var15 * var13 - var14 * var12;
                        boolean var20 = var16 < 0;
                        this.worldServerInstance.setBlock(var17, var18, var19, var20 ? Blocks.obsidian : Blocks.air);
                        ++var16;
                    }
                    ++var15;
                }
                ++var14;
            }
            par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0f);
            par1Entity.motionZ = 0.0;
            par1Entity.motionY = 0.0;
            par1Entity.motionX = 0.0;
        }
    }

    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        double var27;
        int var48;
        int var9 = 128;
        double var10 = -1.0;
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        int var15 = MathHelper.floor_double(par1Entity.posX);
        int var16 = MathHelper.floor_double(par1Entity.posZ);
        long var17 = ChunkCoordIntPair.chunkXZ2Int(var15, var16);
        boolean var19 = true;
        if (this.destinationCoordinateCache.containsItem(var17)) {
            PortalPosition var20 = (PortalPosition)this.destinationCoordinateCache.getValueByKey(var17);
            var10 = 0.0;
            var12 = var20.posX;
            var13 = var20.posY;
            var14 = var20.posZ;
            var20.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            var19 = false;
        } else {
            var48 = var15 - var9;
            while (var48 <= var15 + var9) {
                double var21 = (double)var48 + 0.5 - par1Entity.posX;
                int var23 = var16 - var9;
                while (var23 <= var16 + var9) {
                    double var24 = (double)var23 + 0.5 - par1Entity.posZ;
                    int var26 = this.worldServerInstance.getActualHeight() - 1;
                    while (var26 >= 0) {
                        if (this.worldServerInstance.getBlock(var48, var26, var23) == Blocks.portal) {
                            while (this.worldServerInstance.getBlock(var48, var26 - 1, var23) == Blocks.portal) {
                                --var26;
                            }
                            var27 = (double)var26 + 0.5 - par1Entity.posY;
                            double var29 = var21 * var21 + var27 * var27 + var24 * var24;
                            if (var10 < 0.0 || var29 < var10) {
                                var10 = var29;
                                var12 = var48;
                                var13 = var26;
                                var14 = var23;
                            }
                        }
                        --var26;
                    }
                    ++var23;
                }
                ++var48;
            }
        }
        if (var10 >= 0.0) {
            if (var19) {
                this.destinationCoordinateCache.add(var17, new PortalPosition(var12, var13, var14, this.worldServerInstance.getTotalWorldTime()));
                this.destinationCoordinateKeys.add(var17);
            }
            double var49 = (double)var12 + 0.5;
            double var25 = (double)var13 + 0.5;
            var27 = (double)var14 + 0.5;
            int var50 = -1;
            if (this.worldServerInstance.getBlock(var12 - 1, var13, var14) == Blocks.portal) {
                var50 = 2;
            }
            if (this.worldServerInstance.getBlock(var12 + 1, var13, var14) == Blocks.portal) {
                var50 = 0;
            }
            if (this.worldServerInstance.getBlock(var12, var13, var14 - 1) == Blocks.portal) {
                var50 = 3;
            }
            if (this.worldServerInstance.getBlock(var12, var13, var14 + 1) == Blocks.portal) {
                var50 = 1;
            }
            int var30 = par1Entity.getTeleportDirection();
            if (var50 > -1) {
                boolean var37;
                int var32 = Direction.offsetX[var50];
                int var31 = Direction.rotateLeft[var50];
                int var34 = Direction.offsetX[var31];
                int var33 = Direction.offsetZ[var50];
                int var35 = Direction.offsetZ[var31];
                boolean var36 = !this.worldServerInstance.isAirBlock(var12 + var32 + var34, var13, var14 + var33 + var35) || !this.worldServerInstance.isAirBlock(var12 + var32 + var34, var13 + 1, var14 + var33 + var35);
                boolean bl = var37 = !this.worldServerInstance.isAirBlock(var12 + var32, var13, var14 + var33) || !this.worldServerInstance.isAirBlock(var12 + var32, var13 + 1, var14 + var33);
                if (var36 && var37) {
                    var50 = Direction.rotateOpposite[var50];
                    var31 = Direction.rotateOpposite[var31];
                    var32 = Direction.offsetX[var50];
                    var33 = Direction.offsetZ[var50];
                    var34 = Direction.offsetX[var31];
                    var35 = Direction.offsetZ[var31];
                    var48 = var12 - var34;
                    var49 -= (double)var34;
                    int var22 = var14 - var35;
                    var27 -= (double)var35;
                    var36 = !this.worldServerInstance.isAirBlock(var48 + var32 + var34, var13, var22 + var33 + var35) || !this.worldServerInstance.isAirBlock(var48 + var32 + var34, var13 + 1, var22 + var33 + var35);
                    var37 = !this.worldServerInstance.isAirBlock(var48 + var32, var13, var22 + var33) || !this.worldServerInstance.isAirBlock(var48 + var32, var13 + 1, var22 + var33);
                }
                float var38 = 0.5f;
                float var39 = 0.5f;
                if (!var36 && var37) {
                    var38 = 1.0f;
                } else if (var36 && !var37) {
                    var38 = 0.0f;
                } else if (var36 && var37) {
                    var39 = 0.0f;
                }
                var49 += (double)((float)var34 * var38 + var39 * (float)var32);
                var27 += (double)((float)var35 * var38 + var39 * (float)var33);
                float var40 = 0.0f;
                float var41 = 0.0f;
                float var42 = 0.0f;
                float var43 = 0.0f;
                if (var50 == var30) {
                    var40 = 1.0f;
                    var41 = 1.0f;
                } else if (var50 == Direction.rotateOpposite[var30]) {
                    var40 = -1.0f;
                    var41 = -1.0f;
                } else if (var50 == Direction.rotateRight[var30]) {
                    var42 = 1.0f;
                    var43 = -1.0f;
                } else {
                    var42 = -1.0f;
                    var43 = 1.0f;
                }
                double var44 = par1Entity.motionX;
                double var46 = par1Entity.motionZ;
                par1Entity.motionX = var44 * (double)var40 + var46 * (double)var43;
                par1Entity.motionZ = var44 * (double)var42 + var46 * (double)var41;
                par1Entity.rotationYaw = par8 - (float)(var30 * 90) + (float)(var50 * 90);
            } else {
                par1Entity.motionZ = 0.0;
                par1Entity.motionY = 0.0;
                par1Entity.motionX = 0.0;
            }
            par1Entity.setLocationAndAngles(var49, var25, var27, par1Entity.rotationYaw, par1Entity.rotationPitch);
            return true;
        }
        return false;
    }

    public boolean makePortal(Entity par1Entity) {
        double var31;
        int var22;
        boolean var33;
        int var24;
        double var17;
        int var16;
        int var19;
        int var23;
        int var21;
        int var26;
        int var27;
        int var25;
        double var32;
        int var20;
        double var14;
        int var2 = 16;
        double var3 = -1.0;
        int var5 = MathHelper.floor_double(par1Entity.posX);
        int var6 = MathHelper.floor_double(par1Entity.posY);
        int var7 = MathHelper.floor_double(par1Entity.posZ);
        int var8 = var5;
        int var9 = var6;
        int var10 = var7;
        int var11 = 0;
        int var12 = this.random.nextInt(4);
        int var13 = var5 - var2;
        while (var13 <= var5 + var2) {
            var14 = (double)var13 + 0.5 - par1Entity.posX;
            var16 = var7 - var2;
            while (var16 <= var7 + var2) {
                var17 = (double)var16 + 0.5 - par1Entity.posZ;
                var19 = this.worldServerInstance.getActualHeight() - 1;
                while (var19 >= 0) {
                    if (this.worldServerInstance.isAirBlock(var13, var19, var16)) {
                        while (var19 > 0 && this.worldServerInstance.isAirBlock(var13, var19 - 1, var16)) {
                            --var19;
                        }
                        var20 = var12;
                        block4 : while (var20 < var12 + 4) {
                            var21 = var20 % 2;
                            var22 = 1 - var21;
                            if (var20 % 4 >= 2) {
                                var21 = - var21;
                                var22 = - var22;
                            }
                            var23 = 0;
                            while (var23 < 3) {
                                var24 = 0;
                                while (var24 < 4) {
                                    var25 = -1;
                                    while (var25 < 4) {
                                        var26 = var13 + (var24 - 1) * var21 + var23 * var22;
                                        var27 = var19 + var25;
                                        int var28 = var16 + (var24 - 1) * var22 - var23 * var21;
                                        if (var25 < 0 && !this.worldServerInstance.getBlock(var26, var27, var28).getMaterial().isSolid() || var25 >= 0 && !this.worldServerInstance.isAirBlock(var26, var27, var28)) break block4;
                                        ++var25;
                                    }
                                    ++var24;
                                }
                                ++var23;
                            }
                            var32 = (double)var19 + 0.5 - par1Entity.posY;
                            var31 = var14 * var14 + var32 * var32 + var17 * var17;
                            if (var3 < 0.0 || var31 < var3) {
                                var3 = var31;
                                var8 = var13;
                                var9 = var19;
                                var10 = var16;
                                var11 = var20 % 4;
                            }
                            ++var20;
                        }
                    }
                    --var19;
                }
                ++var16;
            }
            ++var13;
        }
        if (var3 < 0.0) {
            var13 = var5 - var2;
            while (var13 <= var5 + var2) {
                var14 = (double)var13 + 0.5 - par1Entity.posX;
                var16 = var7 - var2;
                while (var16 <= var7 + var2) {
                    var17 = (double)var16 + 0.5 - par1Entity.posZ;
                    var19 = this.worldServerInstance.getActualHeight() - 1;
                    while (var19 >= 0) {
                        if (this.worldServerInstance.isAirBlock(var13, var19, var16)) {
                            while (var19 > 0 && this.worldServerInstance.isAirBlock(var13, var19 - 1, var16)) {
                                --var19;
                            }
                            var20 = var12;
                            block12 : while (var20 < var12 + 2) {
                                var21 = var20 % 2;
                                var22 = 1 - var21;
                                var23 = 0;
                                while (var23 < 4) {
                                    var24 = -1;
                                    while (var24 < 4) {
                                        var25 = var13 + (var23 - 1) * var21;
                                        var26 = var19 + var24;
                                        var27 = var16 + (var23 - 1) * var22;
                                        if (var24 < 0 && !this.worldServerInstance.getBlock(var25, var26, var27).getMaterial().isSolid() || var24 >= 0 && !this.worldServerInstance.isAirBlock(var25, var26, var27)) break block12;
                                        ++var24;
                                    }
                                    ++var23;
                                }
                                var32 = (double)var19 + 0.5 - par1Entity.posY;
                                var31 = var14 * var14 + var32 * var32 + var17 * var17;
                                if (var3 < 0.0 || var31 < var3) {
                                    var3 = var31;
                                    var8 = var13;
                                    var9 = var19;
                                    var10 = var16;
                                    var11 = var20 % 2;
                                }
                                ++var20;
                            }
                        }
                        --var19;
                    }
                    ++var16;
                }
                ++var13;
            }
        }
        int var29 = var8;
        int var15 = var9;
        var16 = var10;
        int var30 = var11 % 2;
        int var18 = 1 - var30;
        if (var11 % 4 >= 2) {
            var30 = - var30;
            var18 = - var18;
        }
        if (var3 < 0.0) {
            if (var9 < 70) {
                var9 = 70;
            }
            if (var9 > this.worldServerInstance.getActualHeight() - 10) {
                var9 = this.worldServerInstance.getActualHeight() - 10;
            }
            var15 = var9;
            var19 = -1;
            while (var19 <= 1) {
                var20 = 1;
                while (var20 < 3) {
                    var21 = -1;
                    while (var21 < 3) {
                        var22 = var29 + (var20 - 1) * var30 + var19 * var18;
                        var23 = var15 + var21;
                        var24 = var16 + (var20 - 1) * var18 - var19 * var30;
                        var33 = var21 < 0;
                        this.worldServerInstance.setBlock(var22, var23, var24, var33 ? Blocks.obsidian : Blocks.air);
                        ++var21;
                    }
                    ++var20;
                }
                ++var19;
            }
        }
        var19 = 0;
        while (var19 < 4) {
            var20 = 0;
            while (var20 < 4) {
                var21 = -1;
                while (var21 < 4) {
                    var22 = var29 + (var20 - 1) * var30;
                    var23 = var15 + var21;
                    var24 = var16 + (var20 - 1) * var18;
                    var33 = var20 == 0 || var20 == 3 || var21 == -1 || var21 == 3;
                    this.worldServerInstance.setBlock(var22, var23, var24, var33 ? Blocks.obsidian : Blocks.portal, 0, 2);
                    ++var21;
                }
                ++var20;
            }
            var20 = 0;
            while (var20 < 4) {
                var21 = -1;
                while (var21 < 4) {
                    var22 = var29 + (var20 - 1) * var30;
                    var23 = var15 + var21;
                    var24 = var16 + (var20 - 1) * var18;
                    this.worldServerInstance.notifyBlocksOfNeighborChange(var22, var23, var24, this.worldServerInstance.getBlock(var22, var23, var24));
                    ++var21;
                }
                ++var20;
            }
            ++var19;
        }
        return true;
    }

    public void removeStalePortalLocations(long par1) {
        if (par1 % 100 == 0) {
            Iterator var3 = this.destinationCoordinateKeys.iterator();
            long var4 = par1 - 600;
            while (var3.hasNext()) {
                Long var6 = (Long)var3.next();
                PortalPosition var7 = (PortalPosition)this.destinationCoordinateCache.getValueByKey(var6);
                if (var7 != null && var7.lastUpdateTime >= var4) continue;
                var3.remove();
                this.destinationCoordinateCache.remove(var6);
            }
        }
    }

    public class PortalPosition
    extends ChunkCoordinates {
        public long lastUpdateTime;
        private static final String __OBFID = "CL_00000154";

        public PortalPosition(int par2, int par3, int par4, long par5) {
            super(par2, par3, par4);
            this.lastUpdateTime = par5;
        }
    }

}

