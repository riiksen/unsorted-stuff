/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.texture;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class TextureCompass
extends TextureAtlasSprite {
    public double currentAngle;
    public double angleDelta;
    private static final String __OBFID = "CL_00001071";

    public TextureCompass(String par1Str) {
        super(par1Str);
    }

    @Override
    public void updateAnimation() {
        Minecraft var1 = Minecraft.getMinecraft();
        if (var1.theWorld != null && var1.thePlayer != null) {
            this.updateCompass(var1.theWorld, var1.thePlayer.posX, var1.thePlayer.posZ, var1.thePlayer.rotationYaw, false, false);
        } else {
            this.updateCompass(null, 0.0, 0.0, 0.0, true, false);
        }
    }

    public void updateCompass(World par1World, double par2, double par4, double par6, boolean par8, boolean par9) {
        if (!this.framesTextureData.isEmpty()) {
            double var10 = 0.0;
            if (par1World != null && !par8) {
                ChunkCoordinates var12 = par1World.getSpawnPoint();
                double var13 = (double)var12.posX - par2;
                double var15 = (double)var12.posZ - par4;
                var10 = - ((par6 %= 360.0) - 90.0) * 3.141592653589793 / 180.0 - Math.atan2(var15, var13);
                if (!par1World.provider.isSurfaceWorld()) {
                    var10 = Math.random() * 3.141592653589793 * 2.0;
                }
            }
            if (par9) {
                this.currentAngle = var10;
            } else {
                double var17 = var10 - this.currentAngle;
                while (var17 < -3.141592653589793) {
                    var17 += 6.283185307179586;
                }
                while (var17 >= 3.141592653589793) {
                    var17 -= 6.283185307179586;
                }
                if (var17 < -1.0) {
                    var17 = -1.0;
                }
                if (var17 > 1.0) {
                    var17 = 1.0;
                }
                this.angleDelta += var17 * 0.1;
                this.angleDelta *= 0.8;
                this.currentAngle += this.angleDelta;
            }
            int var18 = (int)((this.currentAngle / 6.283185307179586 + 1.0) * (double)this.framesTextureData.size()) % this.framesTextureData.size();
            while (var18 < 0) {
                var18 = (var18 + this.framesTextureData.size()) % this.framesTextureData.size();
            }
            if (var18 != this.frameCounter) {
                this.frameCounter = var18;
                TextureUtil.func_147955_a((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}

