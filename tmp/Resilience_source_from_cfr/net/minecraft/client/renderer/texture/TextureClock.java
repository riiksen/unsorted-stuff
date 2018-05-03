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
import net.minecraft.world.WorldProvider;

public class TextureClock
extends TextureAtlasSprite {
    private double field_94239_h;
    private double field_94240_i;
    private static final String __OBFID = "CL_00001070";

    public TextureClock(String par1Str) {
        super(par1Str);
    }

    @Override
    public void updateAnimation() {
        if (!this.framesTextureData.isEmpty()) {
            Minecraft var1 = Minecraft.getMinecraft();
            double var2 = 0.0;
            if (var1.theWorld != null && var1.thePlayer != null) {
                float var4 = var1.theWorld.getCelestialAngle(1.0f);
                var2 = var4;
                if (!var1.theWorld.provider.isSurfaceWorld()) {
                    var2 = Math.random();
                }
            }
            double var7 = var2 - this.field_94239_h;
            while (var7 < -0.5) {
                var7 += 1.0;
            }
            while (var7 >= 0.5) {
                var7 -= 1.0;
            }
            if (var7 < -1.0) {
                var7 = -1.0;
            }
            if (var7 > 1.0) {
                var7 = 1.0;
            }
            this.field_94240_i += var7 * 0.1;
            this.field_94240_i *= 0.8;
            this.field_94239_h += this.field_94240_i;
            int var6 = (int)((this.field_94239_h + 1.0) * (double)this.framesTextureData.size()) % this.framesTextureData.size();
            while (var6 < 0) {
                var6 = (var6 + this.framesTextureData.size()) % this.framesTextureData.size();
            }
            if (var6 != this.frameCounter) {
                this.frameCounter = var6;
                TextureUtil.func_147955_a((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}

