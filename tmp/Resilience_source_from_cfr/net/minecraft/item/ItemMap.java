/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultiset
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Multiset
 *  com.google.common.collect.Multisets
 */
package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.WorldInfo;

public class ItemMap
extends ItemMapBase {
    private static final String __OBFID = "CL_00000047";

    protected ItemMap() {
        this.setHasSubtypes(true);
    }

    public static MapData func_150912_a(int p_150912_0_, World p_150912_1_) {
        String var2 = "map_" + p_150912_0_;
        MapData var3 = (MapData)p_150912_1_.loadItemData(MapData.class, var2);
        if (var3 == null) {
            var3 = new MapData(var2);
            p_150912_1_.setItemData(var2, var3);
        }
        return var3;
    }

    public MapData getMapData(ItemStack par1ItemStack, World par2World) {
        String var3 = "map_" + par1ItemStack.getItemDamage();
        MapData var4 = (MapData)par2World.loadItemData(MapData.class, var3);
        if (var4 == null && !par2World.isClient) {
            par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
            var3 = "map_" + par1ItemStack.getItemDamage();
            var4 = new MapData(var3);
            var4.scale = 3;
            int var5 = 128 * (1 << var4.scale);
            var4.xCenter = Math.round((float)par2World.getWorldInfo().getSpawnX() / (float)var5) * var5;
            var4.zCenter = Math.round(par2World.getWorldInfo().getSpawnZ() / var5) * var5;
            var4.dimension = (byte)par2World.provider.dimensionId;
            var4.markDirty();
            par2World.setItemData(var3, var4);
        }
        return var4;
    }

    public void updateMapData(World par1World, Entity par2Entity, MapData par3MapData) {
        if (par1World.provider.dimensionId == par3MapData.dimension && par2Entity instanceof EntityPlayer) {
            int var4 = 1 << par3MapData.scale;
            int var5 = par3MapData.xCenter;
            int var6 = par3MapData.zCenter;
            int var7 = MathHelper.floor_double(par2Entity.posX - (double)var5) / var4 + 64;
            int var8 = MathHelper.floor_double(par2Entity.posZ - (double)var6) / var4 + 64;
            int var9 = 128 / var4;
            if (par1World.provider.hasNoSky) {
                var9 /= 2;
            }
            MapData.MapInfo var10 = par3MapData.func_82568_a((EntityPlayer)par2Entity);
            ++var10.field_82569_d;
            int var11 = var7 - var9 + 1;
            while (var11 < var7 + var9) {
                if ((var11 & 15) == (var10.field_82569_d & 15)) {
                    int var12 = 255;
                    int var13 = 0;
                    double var14 = 0.0;
                    int var16 = var8 - var9 - 1;
                    while (var16 < var8 + var9) {
                        if (var11 >= 0 && var16 >= -1 && var11 < 128 && var16 < 128) {
                            int var17 = var11 - var7;
                            int var18 = var16 - var8;
                            boolean var19 = var17 * var17 + var18 * var18 > (var9 - 2) * (var9 - 2);
                            int var20 = (var5 / var4 + var11 - 64) * var4;
                            int var21 = (var6 / var4 + var16 - 64) * var4;
                            HashMultiset var22 = HashMultiset.create();
                            Chunk var23 = par1World.getChunkFromBlockCoords(var20, var21);
                            if (!var23.isEmpty()) {
                                byte var40;
                                MapColor var38;
                                int var29;
                                byte var37;
                                int var24 = var20 & 15;
                                int var25 = var21 & 15;
                                int var26 = 0;
                                double var27 = 0.0;
                                if (par1World.provider.hasNoSky) {
                                    var29 = var20 + var21 * 231871;
                                    if (((var29 = var29 * var29 * 31287121 + var29 * 11) >> 20 & 1) == 0) {
                                        var22.add((Object)Blocks.dirt.getMapColor(0), 10);
                                    } else {
                                        var22.add((Object)Blocks.stone.getMapColor(0), 100);
                                    }
                                    var27 = 100.0;
                                } else {
                                    var29 = 0;
                                    while (var29 < var4) {
                                        int var30 = 0;
                                        while (var30 < var4) {
                                            int var31 = var23.getHeightValue(var29 + var24, var30 + var25) + 1;
                                            Block var32 = Blocks.air;
                                            int var33 = 0;
                                            if (var31 > 1) {
                                                while ((var32 = var23.func_150810_a(var29 + var24, --var31, var30 + var25)).getMapColor(var33 = var23.getBlockMetadata(var29 + var24, var31, var30 + var25)) == MapColor.field_151660_b && var31 > 0) {
                                                }
                                                if (var31 > 0 && var32.getMaterial().isLiquid()) {
                                                    Block var35;
                                                    int var34 = var31 - 1;
                                                    do {
                                                        var35 = var23.func_150810_a(var29 + var24, var34--, var30 + var25);
                                                        ++var26;
                                                    } while (var34 > 0 && var35.getMaterial().isLiquid());
                                                }
                                            }
                                            var27 += (double)var31 / (double)(var4 * var4);
                                            var22.add((Object)var32.getMapColor(var33));
                                            ++var30;
                                        }
                                        ++var29;
                                    }
                                }
                                var26 /= var4 * var4;
                                double var36 = (var27 - var14) * 4.0 / (double)(var4 + 4) + ((double)(var11 + var16 & 1) - 0.5) * 0.4;
                                int var39 = 1;
                                if (var36 > 0.6) {
                                    var39 = 2;
                                }
                                if (var36 < -0.6) {
                                    var39 = 0;
                                }
                                if ((var38 = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)var22), (Object)MapColor.field_151660_b)) == MapColor.field_151662_n) {
                                    var36 = (double)var26 * 0.1 + (double)(var11 + var16 & 1) * 0.2;
                                    var39 = 1;
                                    if (var36 < 0.5) {
                                        var39 = 2;
                                    }
                                    if (var36 > 0.9) {
                                        var39 = 0;
                                    }
                                }
                                var14 = var27;
                                if (!(var16 < 0 || var17 * var17 + var18 * var18 >= var9 * var9 || var19 && (var11 + var16 & 1) == 0 || (var37 = par3MapData.colors[var11 + var16 * 128]) == (var40 = (byte)(var38.colorIndex * 4 + var39)))) {
                                    if (var12 > var16) {
                                        var12 = var16;
                                    }
                                    if (var13 < var16) {
                                        var13 = var16;
                                    }
                                    par3MapData.colors[var11 + var16 * 128] = var40;
                                }
                            }
                        }
                        ++var16;
                    }
                    if (var12 <= var13) {
                        par3MapData.setColumnDirty(var11, var12, var13);
                    }
                }
                ++var11;
            }
        }
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (!par2World.isClient) {
            MapData var6 = this.getMapData(par1ItemStack, par2World);
            if (par3Entity instanceof EntityPlayer) {
                EntityPlayer var7 = (EntityPlayer)par3Entity;
                var6.updateVisiblePlayers(var7, par1ItemStack);
            }
            if (par5) {
                this.updateMapData(par2World, par3Entity, var6);
            }
        }
    }

    @Override
    public Packet func_150911_c(ItemStack p_150911_1_, World p_150911_2_, EntityPlayer p_150911_3_) {
        byte[] var4 = this.getMapData(p_150911_1_, p_150911_2_).getUpdatePacketData(p_150911_1_, p_150911_2_, p_150911_3_);
        return var4 == null ? null : new S34PacketMaps(p_150911_1_.getItemDamage(), var4);
    }

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().getBoolean("map_is_scaling")) {
            MapData var4 = Items.filled_map.getMapData(par1ItemStack, par2World);
            par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
            MapData var5 = new MapData("map_" + par1ItemStack.getItemDamage());
            var5.scale = (byte)(var4.scale + 1);
            if (var5.scale > 4) {
                var5.scale = 4;
            }
            var5.xCenter = var4.xCenter;
            var5.zCenter = var4.zCenter;
            var5.dimension = var4.dimension;
            var5.markDirty();
            par2World.setItemData("map_" + par1ItemStack.getItemDamage(), var5);
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        MapData var5 = this.getMapData(par1ItemStack, par2EntityPlayer.worldObj);
        if (par4) {
            if (var5 == null) {
                par3List.add("Unknown map");
            } else {
                par3List.add("Scaling at 1:" + (1 << var5.scale));
                par3List.add("(Level " + var5.scale + "/" + 4 + ")");
            }
        }
    }
}

