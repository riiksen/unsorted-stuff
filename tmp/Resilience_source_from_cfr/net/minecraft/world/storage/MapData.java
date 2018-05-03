/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.WorldInfo;

public class MapData
extends WorldSavedData {
    public int xCenter;
    public int zCenter;
    public byte dimension;
    public byte scale;
    public byte[] colors = new byte[16384];
    public List playersArrayList = new ArrayList();
    private Map playersHashMap = new HashMap();
    public Map playersVisibleOnMap = new LinkedHashMap();
    private static final String __OBFID = "CL_00000577";

    public MapData(String par1Str) {
        super(par1Str);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.dimension = par1NBTTagCompound.getByte("dimension");
        this.xCenter = par1NBTTagCompound.getInteger("xCenter");
        this.zCenter = par1NBTTagCompound.getInteger("zCenter");
        this.scale = par1NBTTagCompound.getByte("scale");
        if (this.scale < 0) {
            this.scale = 0;
        }
        if (this.scale > 4) {
            this.scale = 4;
        }
        int var2 = par1NBTTagCompound.getShort("width");
        int var3 = par1NBTTagCompound.getShort("height");
        if (var2 == 128 && var3 == 128) {
            this.colors = par1NBTTagCompound.getByteArray("colors");
        } else {
            byte[] var4 = par1NBTTagCompound.getByteArray("colors");
            this.colors = new byte[16384];
            int var5 = (128 - var2) / 2;
            int var6 = (128 - var3) / 2;
            int var7 = 0;
            while (var7 < var3) {
                int var8 = var7 + var6;
                if (var8 >= 0 || var8 < 128) {
                    int var9 = 0;
                    while (var9 < var2) {
                        int var10 = var9 + var5;
                        if (var10 >= 0 || var10 < 128) {
                            this.colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
                        }
                        ++var9;
                    }
                }
                ++var7;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("dimension", this.dimension);
        par1NBTTagCompound.setInteger("xCenter", this.xCenter);
        par1NBTTagCompound.setInteger("zCenter", this.zCenter);
        par1NBTTagCompound.setByte("scale", this.scale);
        par1NBTTagCompound.setShort("width", 128);
        par1NBTTagCompound.setShort("height", 128);
        par1NBTTagCompound.setByteArray("colors", this.colors);
    }

    public void updateVisiblePlayers(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        if (!this.playersHashMap.containsKey(par1EntityPlayer)) {
            MapInfo var3 = new MapInfo(par1EntityPlayer);
            this.playersHashMap.put(par1EntityPlayer, var3);
            this.playersArrayList.add(var3);
        }
        if (!par1EntityPlayer.inventory.hasItemStack(par2ItemStack)) {
            this.playersVisibleOnMap.remove(par1EntityPlayer.getCommandSenderName());
        }
        int var5 = 0;
        while (var5 < this.playersArrayList.size()) {
            MapInfo var4 = (MapInfo)this.playersArrayList.get(var5);
            if (!var4.entityplayerObj.isDead && (var4.entityplayerObj.inventory.hasItemStack(par2ItemStack) || par2ItemStack.isOnItemFrame())) {
                if (!par2ItemStack.isOnItemFrame() && var4.entityplayerObj.dimension == this.dimension) {
                    this.func_82567_a(0, var4.entityplayerObj.worldObj, var4.entityplayerObj.getCommandSenderName(), var4.entityplayerObj.posX, var4.entityplayerObj.posZ, var4.entityplayerObj.rotationYaw);
                }
            } else {
                this.playersHashMap.remove(var4.entityplayerObj);
                this.playersArrayList.remove(var4);
            }
            ++var5;
        }
        if (par2ItemStack.isOnItemFrame()) {
            this.func_82567_a(1, par1EntityPlayer.worldObj, "frame-" + par2ItemStack.getItemFrame().getEntityId(), par2ItemStack.getItemFrame().field_146063_b, par2ItemStack.getItemFrame().field_146062_d, par2ItemStack.getItemFrame().hangingDirection * 90);
        }
    }

    private void func_82567_a(int par1, World par2World, String par3Str, double par4, double par6, double par8) {
        byte var15;
        int var10 = 1 << this.scale;
        float var11 = (float)(par4 - (double)this.xCenter) / (float)var10;
        float var12 = (float)(par6 - (double)this.zCenter) / (float)var10;
        byte var13 = (byte)((double)(var11 * 2.0f) + 0.5);
        byte var14 = (byte)((double)(var12 * 2.0f) + 0.5);
        int var16 = 63;
        if (var11 >= (float)(- var16) && var12 >= (float)(- var16) && var11 <= (float)var16 && var12 <= (float)var16) {
            var15 = (byte)((par8 += par8 < 0.0 ? -8.0 : 8.0) * 16.0 / 360.0);
            if (this.dimension < 0) {
                int var17 = (int)(par2World.getWorldInfo().getWorldTime() / 10);
                var15 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
            }
        } else {
            if (Math.abs(var11) >= 320.0f || Math.abs(var12) >= 320.0f) {
                this.playersVisibleOnMap.remove(par3Str);
                return;
            }
            par1 = 6;
            var15 = 0;
            if (var11 <= (float)(- var16)) {
                var13 = (byte)((double)(var16 * 2) + 2.5);
            }
            if (var12 <= (float)(- var16)) {
                var14 = (byte)((double)(var16 * 2) + 2.5);
            }
            if (var11 >= (float)var16) {
                var13 = (byte)(var16 * 2 + 1);
            }
            if (var12 >= (float)var16) {
                var14 = (byte)(var16 * 2 + 1);
            }
        }
        this.playersVisibleOnMap.put(par3Str, new MapCoord((byte)par1, var13, var14, var15));
    }

    public byte[] getUpdatePacketData(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        MapInfo var4 = (MapInfo)this.playersHashMap.get(par3EntityPlayer);
        return var4 == null ? null : var4.getPlayersOnMap(par1ItemStack);
    }

    public void setColumnDirty(int par1, int par2, int par3) {
        super.markDirty();
        int var4 = 0;
        while (var4 < this.playersArrayList.size()) {
            MapInfo var5 = (MapInfo)this.playersArrayList.get(var4);
            if (var5.field_76209_b[par1] < 0 || var5.field_76209_b[par1] > par2) {
                var5.field_76209_b[par1] = par2;
            }
            if (var5.field_76210_c[par1] < 0 || var5.field_76210_c[par1] < par3) {
                var5.field_76210_c[par1] = par3;
            }
            ++var4;
        }
    }

    public void updateMPMapData(byte[] par1ArrayOfByte) {
        if (par1ArrayOfByte[0] == 0) {
            int var2 = par1ArrayOfByte[1] & 255;
            int var3 = par1ArrayOfByte[2] & 255;
            int var4 = 0;
            while (var4 < par1ArrayOfByte.length - 3) {
                this.colors[(var4 + var3) * 128 + var2] = par1ArrayOfByte[var4 + 3];
                ++var4;
            }
            this.markDirty();
        } else if (par1ArrayOfByte[0] == 1) {
            this.playersVisibleOnMap.clear();
            int var2 = 0;
            while (var2 < (par1ArrayOfByte.length - 1) / 3) {
                byte var7 = (byte)(par1ArrayOfByte[var2 * 3 + 1] >> 4);
                byte var8 = par1ArrayOfByte[var2 * 3 + 2];
                byte var5 = par1ArrayOfByte[var2 * 3 + 3];
                byte var6 = (byte)(par1ArrayOfByte[var2 * 3 + 1] & 15);
                this.playersVisibleOnMap.put("icon-" + var2, new MapCoord(var7, var8, var5, var6));
                ++var2;
            }
        } else if (par1ArrayOfByte[0] == 2) {
            this.scale = par1ArrayOfByte[1];
        }
    }

    public MapInfo func_82568_a(EntityPlayer par1EntityPlayer) {
        MapInfo var2 = (MapInfo)this.playersHashMap.get(par1EntityPlayer);
        if (var2 == null) {
            var2 = new MapInfo(par1EntityPlayer);
            this.playersHashMap.put(par1EntityPlayer, var2);
            this.playersArrayList.add(var2);
        }
        return var2;
    }

    public class MapCoord {
        public byte iconSize;
        public byte centerX;
        public byte centerZ;
        public byte iconRotation;
        private static final String __OBFID = "CL_00000579";

        public MapCoord(byte par2, byte par3, byte par4, byte par5) {
            this.iconSize = par2;
            this.centerX = par3;
            this.centerZ = par4;
            this.iconRotation = par5;
        }
    }

    public class MapInfo {
        public final EntityPlayer entityplayerObj;
        public int[] field_76209_b;
        public int[] field_76210_c;
        private int currentRandomNumber;
        private int ticksUntilPlayerLocationMapUpdate;
        private byte[] lastPlayerLocationOnMap;
        public int field_82569_d;
        private boolean field_82570_i;
        private static final String __OBFID = "CL_00000578";

        public MapInfo(EntityPlayer par2EntityPlayer) {
            this.field_76209_b = new int[128];
            this.field_76210_c = new int[128];
            this.entityplayerObj = par2EntityPlayer;
            int var3 = 0;
            while (var3 < this.field_76209_b.length) {
                this.field_76209_b[var3] = 0;
                this.field_76210_c[var3] = 127;
                ++var3;
            }
        }

        public byte[] getPlayersOnMap(ItemStack par1ItemStack) {
            int var10;
            int var3;
            if (!this.field_82570_i) {
                byte[] var2 = new byte[]{2, MapData.this.scale};
                this.field_82570_i = true;
                return var2;
            }
            if (--this.ticksUntilPlayerLocationMapUpdate < 0) {
                byte[] var2;
                boolean var9;
                this.ticksUntilPlayerLocationMapUpdate = 4;
                var2 = new byte[MapData.this.playersVisibleOnMap.size() * 3 + 1];
                var2[0] = 1;
                var3 = 0;
                for (MapCoord var5 : MapData.this.playersVisibleOnMap.values()) {
                    var2[var3 * 3 + 1] = (byte)(var5.iconSize << 4 | var5.iconRotation & 15);
                    var2[var3 * 3 + 2] = var5.centerX;
                    var2[var3 * 3 + 3] = var5.centerZ;
                    ++var3;
                }
                boolean bl = var9 = !par1ItemStack.isOnItemFrame();
                if (this.lastPlayerLocationOnMap != null && this.lastPlayerLocationOnMap.length == var2.length) {
                    var10 = 0;
                    while (var10 < var2.length) {
                        if (var2[var10] != this.lastPlayerLocationOnMap[var10]) {
                            var9 = false;
                            break;
                        }
                        ++var10;
                    }
                } else {
                    var9 = false;
                }
                if (!var9) {
                    this.lastPlayerLocationOnMap = var2;
                    return var2;
                }
            }
            int var8 = 0;
            while (var8 < 1) {
                if (this.field_76209_b[var3 = this.currentRandomNumber++ * 11 % 128] >= 0) {
                    int var11 = this.field_76210_c[var3] - this.field_76209_b[var3] + 1;
                    var10 = this.field_76209_b[var3];
                    byte[] var6 = new byte[var11 + 3];
                    var6[0] = 0;
                    var6[1] = (byte)var3;
                    var6[2] = (byte)var10;
                    int var7 = 0;
                    while (var7 < var6.length - 3) {
                        var6[var7 + 3] = MapData.this.colors[(var7 + var10) * 128 + var3];
                        ++var7;
                    }
                    this.field_76210_c[var3] = -1;
                    this.field_76209_b[var3] = -1;
                    return var6;
                }
                ++var8;
            }
            return null;
        }
    }

}

