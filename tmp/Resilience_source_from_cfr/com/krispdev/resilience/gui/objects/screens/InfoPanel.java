/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;

public class InfoPanel
extends DefaultPanel {
    public InfoPanel(String name, int x, int y, int x1, int y1, boolean visible) {
        super(name, x, y, x1, y1, visible);
    }

    @Override
    public void draw(int x, int y) {
        super.draw(x, y);
        if (this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), this.getY() + 94, -1727790076);
            Resilience.getInstance().getStandardFont().drawString("X: \u00a7b" + new DecimalFormat("#.##").format(Resilience.getInstance().getInvoker().getPosX()), (float)this.getX() + 4.5f, this.getY() + 17 + 3, -1);
            Resilience.getInstance().getStandardFont().drawString("Y: \u00a7b" + new DecimalFormat("#.##").format(Resilience.getInstance().getInvoker().getPosY()), (float)this.getX() + 4.5f, this.getY() + 17 + 3 + 12, -1);
            Resilience.getInstance().getStandardFont().drawString("Z: \u00a7b" + new DecimalFormat("#.##").format(Resilience.getInstance().getInvoker().getPosZ()), (float)this.getX() + 4.5f, this.getY() + 17 + 3 + 24, -1);
            int yaw = MathHelper.floor_double((double)(Resilience.getInstance().getInvoker().getRotationYaw() * 4.0f / 360.0f) + 0.5) & 3;
            char firstC = Direction.directions[yaw].charAt(0);
            String firstLetter = String.valueOf(firstC).toUpperCase();
            String direction = String.valueOf(firstLetter) + Direction.directions[yaw].toLowerCase().replaceFirst(String.valueOf(firstC).toLowerCase(), "");
            Resilience.getInstance().getStandardFont().drawString("Direction: \u00a7b" + direction, (float)this.getX() + 4.5f, this.getY() + 17 + 3 + 36, -1);
            int floorX = MathHelper.floor_double(Resilience.getInstance().getInvoker().getPosX());
            int floorY = MathHelper.floor_double(Resilience.getInstance().getInvoker().getPosY());
            int floorZ = MathHelper.floor_double(Resilience.getInstance().getInvoker().getPosZ());
            Chunk currentChunck = Resilience.getInstance().getWrapper().getWorld().getChunkFromBlockCoords(floorX, floorZ);
            BiomeGenBase biome = currentChunck.getBiomeGenForWorldCoords(floorX & 15, floorZ & 15, Resilience.getInstance().getWrapper().getWorld().getWorldChunkManager());
            String biomeName = biome.biomeName;
            Resilience.getInstance().getStandardFont().drawString("Biome: \u00a7b" + biomeName, (float)this.getX() + 4.5f, this.getY() + 17 + 3 + 48, -1);
            Resilience.getInstance().getWrapper().getMinecraft();
            Resilience.getInstance().getStandardFont().drawString("FPS: \u00a7b" + Minecraft.debugFPS, (float)this.getX() + 4.5f, this.getY() + 17 + 3 + 60, -1);
        }
    }
}

