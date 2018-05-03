/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityPainting
extends EntityHanging {
    public EnumArt art;
    private static final String __OBFID = "CL_00001556";

    public EntityPainting(World par1World) {
        super(par1World);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5) {
        super(par1World, par2, par3, par4, par5);
        ArrayList<EnumArt> var6 = new ArrayList<EnumArt>();
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;
        int var9 = 0;
        while (var9 < var8) {
            EnumArt var10;
            this.art = var10 = var7[var9];
            this.setDirection(par5);
            if (this.onValidSurface()) {
                var6.add(var10);
            }
            ++var9;
        }
        if (!var6.isEmpty()) {
            this.art = (EnumArt)((Object)var6.get(this.rand.nextInt(var6.size())));
        }
        this.setDirection(par5);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5, String par6Str) {
        this(par1World, par2, par3, par4, par5);
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;
        int var9 = 0;
        while (var9 < var8) {
            EnumArt var10 = var7[var9];
            if (var10.title.equals(par6Str)) {
                this.art = var10;
                break;
            }
            ++var9;
        }
        this.setDirection(par5);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        String var2 = par1NBTTagCompound.getString("Motive");
        EnumArt[] var3 = EnumArt.values();
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            EnumArt var6 = var3[var5];
            if (var6.title.equals(var2)) {
                this.art = var6;
            }
            ++var5;
        }
        if (this.art == null) {
            this.art = EnumArt.Kebab;
        }
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    @Override
    public int getWidthPixels() {
        return this.art.sizeX;
    }

    @Override
    public int getHeightPixels() {
        return this.art.sizeY;
    }

    @Override
    public void onBroken(Entity par1Entity) {
        if (par1Entity instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)par1Entity;
            if (var2.capabilities.isCreativeMode) {
                return;
            }
        }
        this.entityDropItem(new ItemStack(Items.painting), 0.0f);
    }

    public static enum EnumArt {
        Kebab("Kebab", 0, "Kebab", 16, 16, 0, 0),
        Aztec("Aztec", 1, "Aztec", 16, 16, 16, 0),
        Alban("Alban", 2, "Alban", 16, 16, 32, 0),
        Aztec2("Aztec2", 3, "Aztec2", 16, 16, 48, 0),
        Bomb("Bomb", 4, "Bomb", 16, 16, 64, 0),
        Plant("Plant", 5, "Plant", 16, 16, 80, 0),
        Wasteland("Wasteland", 6, "Wasteland", 16, 16, 96, 0),
        Pool("Pool", 7, "Pool", 32, 16, 0, 32),
        Courbet("Courbet", 8, "Courbet", 32, 16, 32, 32),
        Sea("Sea", 9, "Sea", 32, 16, 64, 32),
        Sunset("Sunset", 10, "Sunset", 32, 16, 96, 32),
        Creebet("Creebet", 11, "Creebet", 32, 16, 128, 32),
        Wanderer("Wanderer", 12, "Wanderer", 16, 32, 0, 64),
        Graham("Graham", 13, "Graham", 16, 32, 16, 64),
        Match("Match", 14, "Match", 32, 32, 0, 128),
        Bust("Bust", 15, "Bust", 32, 32, 32, 128),
        Stage("Stage", 16, "Stage", 32, 32, 64, 128),
        Void("Void", 17, "Void", 32, 32, 96, 128),
        SkullAndRoses("SkullAndRoses", 18, "SkullAndRoses", 32, 32, 128, 128),
        Wither("Wither", 19, "Wither", 32, 32, 160, 128),
        Fighters("Fighters", 20, "Fighters", 64, 32, 0, 96),
        Pointer("Pointer", 21, "Pointer", 64, 64, 0, 192),
        Pigscene("Pigscene", 22, "Pigscene", 64, 64, 64, 192),
        BurningSkull("BurningSkull", 23, "BurningSkull", 64, 64, 128, 192),
        Skeleton("Skeleton", 24, "Skeleton", 64, 48, 192, 64),
        DonkeyKong("DonkeyKong", 25, "DonkeyKong", 64, 48, 192, 112);
        
        public static final int maxArtTitleLength;
        public final String title;
        public final int sizeX;
        public final int sizeY;
        public final int offsetX;
        public final int offsetY;
        private static final EnumArt[] $VALUES;
        private static final String __OBFID = "CL_00001557";

        static {
            maxArtTitleLength = "SkullAndRoses".length();
            $VALUES = new EnumArt[]{Kebab, Aztec, Alban, Aztec2, Bomb, Plant, Wasteland, Pool, Courbet, Sea, Sunset, Creebet, Wanderer, Graham, Match, Bust, Stage, Void, SkullAndRoses, Wither, Fighters, Pointer, Pigscene, BurningSkull, Skeleton, DonkeyKong};
        }

        private EnumArt(String par1Str, int par2, String par3Str, int par4, String par5, int par6, int par7, int n2, int n3) {
            this.title = par3Str;
            this.sizeX = par4;
            this.sizeY = par5;
            this.offsetX = par6;
            this.offsetY = par7;
        }
    }

}

