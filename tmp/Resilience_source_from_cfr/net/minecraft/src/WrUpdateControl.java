/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.src.Config;
import net.minecraft.src.IWrUpdateControl;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3Pool;

public class WrUpdateControl
implements IWrUpdateControl {
    private boolean hasForge = Reflector.ForgeHooksClient.exists();
    private int renderPass = 0;

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
        AxisAlignedBB.getAABBPool().cleanPool();
        WorldClient theWorld = Config.getMinecraft().theWorld;
        if (theWorld != null) {
            theWorld.getWorldVec3Pool().clear();
        }
    }

    public void setRenderPass(int renderPass) {
        this.renderPass = renderPass;
    }
}

