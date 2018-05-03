/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Cylinder
 */
package com.krispdev.resilience.module.modules.render;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

public class ModuleTrajectories
extends DefaultModule {
    private ArrayList<Double[]> linePoints = new ArrayList();
    private MovingObjectPosition hit = null;

    public ModuleTrajectories() {
        super("Projectiles", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws a line showing the path of a projectile");
    }

    @Override
    public void onRender(EventOnRender event) {
        boolean bow = false;
        EntityClientPlayerMP player = this.invoker.getWrapper().getPlayer();
        if (player.getCurrentEquippedItem() != null) {
            Item item = player.getCurrentEquippedItem().getItem();
            if (!(item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEnderPearl || item instanceof ItemEgg)) {
                return;
            }
            if (item instanceof ItemBow) {
                bow = true;
            }
        } else {
            return;
        }
        double posX = RenderManager.renderPosX - (double)(MathHelper.cos(player.rotationYaw / 180.0f * 3.141593f) * 0.16f);
        double posY = RenderManager.renderPosY + (double)player.getEyeHeight() - 0.1000000014901161;
        double posZ = RenderManager.renderPosZ - (double)(MathHelper.sin(player.rotationYaw / 180.0f * 3.141593f) * 0.16f);
        double motionX = (double)((- MathHelper.sin(player.rotationYaw / 180.0f * 3.141593f)) * MathHelper.cos(player.rotationPitch / 180.0f * 3.141593f)) * (bow ? 1.0 : 0.4);
        double motionY = (double)(- MathHelper.sin(player.rotationPitch / 180.0f * 3.141593f)) * (bow ? 1.0 : 0.4);
        double motionZ = (double)(MathHelper.cos(player.rotationYaw / 180.0f * 3.141593f) * MathHelper.cos(player.rotationPitch / 180.0f * 3.141593f)) * (bow ? 1.0 : 0.4);
        if (player.getItemInUseCount() <= 0 && bow) {
            return;
        }
        int var6 = 72000 - player.getItemInUseCount();
        float power = (float)var6 / 20.0f;
        if ((double)(power = (power * power + power * 2.0f) / 3.0f) < 0.1) {
            return;
        }
        if (power > 1.0f) {
            power = 1.0f;
        }
        GL11.glPushMatrix();
        RenderUtils.setup3DLightlessModel();
        GL11.glColor3f((float)(1.0f - power), (float)0.0f, (float)power);
        float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= (double)distance;
        motionY /= (double)distance;
        motionZ /= (double)distance;
        motionX *= (double)(bow ? power * 2.0f : 1.0f) * 1.5;
        motionY *= (double)(bow ? power * 2.0f : 1.0f) * 1.5;
        motionZ *= (double)(bow ? power * 2.0f : 1.0f) * 1.5;
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)3);
        boolean hasLanded = false;
        boolean isEntity = false;
        MovingObjectPosition landingPosition = null;
        float size = (float)(bow ? 0.3 : 0.25);
        while (!hasLanded && posY > 0.0) {
            AxisAlignedBB boundingBox;
            Vec3 present = this.invoker.getWrapper().getWorld().getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
            Vec3 future = this.invoker.getWrapper().getWorld().getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition possibleLandingStrip = this.invoker.getWrapper().getWorld().func_147447_a(present, future, false, true, false);
            present = this.invoker.getWrapper().getWorld().getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
            future = this.invoker.getWrapper().getWorld().getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
            if (possibleLandingStrip != null) {
                hasLanded = true;
                landingPosition = possibleLandingStrip;
            }
            Object hitEntity = null;
            AxisAlignedBB arrowBox = AxisAlignedBB.getBoundingBox(posX - (double)size, posY - (double)size, posZ - (double)size, posX + (double)size, posY + (double)size, posZ + (double)size);
            List entities = this.getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            int index = 0;
            while (index < entities.size()) {
                MovingObjectPosition possibleEntityLanding;
                AxisAlignedBB var12;
                float var11;
                Entity entity = (Entity)entities.get(index);
                if (entity.canBeCollidedWith() && entity != player && (possibleEntityLanding = (var12 = entity.boundingBox.expand(var11 = 0.3f, var11, var11)).calculateIntercept(present, future)) != null) {
                    hasLanded = true;
                    isEntity = true;
                    landingPosition = possibleEntityLanding;
                }
                ++index;
            }
            float motionAdjustment = 0.99f;
            if (this.isInMaterial(boundingBox = AxisAlignedBB.getBoundingBox(posX - (double)size, posY - (double)size, posZ - (double)size, (posX += motionX) + (double)size, (posY += motionY) + (double)size, (posZ += motionZ) + (double)size), Material.water)) {
                motionAdjustment = 0.8f;
            }
            motionX *= (double)motionAdjustment;
            motionY *= (double)motionAdjustment;
            motionZ *= (double)motionAdjustment;
            motionY -= bow ? 0.05 : 0.03;
            GL11.glVertex3d((double)(posX - RenderManager.renderPosX), (double)(posY - RenderManager.renderPosY), (double)(posZ - RenderManager.renderPosZ));
        }
        GL11.glEnd();
        RenderUtils.shutdown3DLightlessModel();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)(posX - RenderManager.renderPosX), (double)(posY - RenderManager.renderPosY), (double)(posZ - RenderManager.renderPosZ));
        if (landingPosition != null) {
            switch (landingPosition.sideHit) {
                case 2: {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    break;
                }
                case 3: {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    break;
                }
                case 4: {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    break;
                }
                case 5: {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                }
            }
            if (isEntity) {
                GL11.glColor3f((float)1.0f, (float)0.0f, (float)0.0f);
            }
        }
        this.renderPoint();
        GL11.glPopMatrix();
    }

    private void renderPoint() {
        GL11.glPushMatrix();
        RenderUtils.setup3DLightlessModel();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)-0.5, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)-0.5);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.5, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.5);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glEnd();
        Cylinder c = new Cylinder();
        GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glLineWidth((float)0.5f);
        GL11.glEnable((int)2848);
        c.setDrawStyle(100013);
        c.setNormals(100000);
        c.draw(0.5f, 0.5f, 0.1f, 400, 1);
        Cylinder c2 = new Cylinder();
        GL11.glLineWidth((float)0.5f);
        GL11.glEnable((int)2848);
        c2.setDrawStyle(100013);
        c2.setNormals(100000);
        c2.draw(0.3f, 0.3f, 0.1f, 200, 1);
        Cylinder c3 = new Cylinder();
        GL11.glLineWidth((float)0.5f);
        GL11.glEnable((int)2848);
        c3.setDrawStyle(100013);
        c3.setNormals(100000);
        c3.draw(0.1f, 0.1f, 0.1f, 100, 1);
        RenderUtils.shutdown3DLightlessModel();
        GL11.glPopMatrix();
    }

    private boolean isInMaterial(AxisAlignedBB axisalignedBB, Material material) {
        int chunkMinX = MathHelper.floor_double(axisalignedBB.minX);
        int chunkMaxX = MathHelper.floor_double(axisalignedBB.maxX + 1.0);
        int chunkMinY = MathHelper.floor_double(axisalignedBB.minY);
        int chunkMaxY = MathHelper.floor_double(axisalignedBB.maxY + 1.0);
        int chunkMinZ = MathHelper.floor_double(axisalignedBB.minZ);
        int chunkMaxZ = MathHelper.floor_double(axisalignedBB.maxZ + 1.0);
        if (!this.invoker.getWrapper().getWorld().checkChunksExist(chunkMinX, chunkMinY, chunkMinZ, chunkMaxX, chunkMaxY, chunkMaxZ)) {
            return false;
        }
        boolean isWithin = false;
        Vec3 vector = this.invoker.getWrapper().getWorld().getWorldVec3Pool().getVecFromPool(0.0, 0.0, 0.0);
        int x = chunkMinX;
        while (x < chunkMaxX) {
            int y = chunkMinY;
            while (y < chunkMaxY) {
                int z = chunkMinZ;
                while (z < chunkMaxZ) {
                    double liquidHeight;
                    Block block = this.invoker.getBlock(x, y, z);
                    if (block != null && block.getMaterial() == material && (double)chunkMaxY >= (liquidHeight = (double)((float)(y + 1) - BlockLiquid.func_149801_b(this.invoker.getWrapper().getWorld().getBlockMetadata(x, y, z))))) {
                        isWithin = true;
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        return isWithin;
    }

    private List getEntitiesWithinAABB(AxisAlignedBB axisalignedBB) {
        ArrayList list = new ArrayList();
        int chunkMinX = MathHelper.floor_double((axisalignedBB.minX - 2.0) / 16.0);
        int chunkMaxX = MathHelper.floor_double((axisalignedBB.maxX + 2.0) / 16.0);
        int chunkMinZ = MathHelper.floor_double((axisalignedBB.minZ - 2.0) / 16.0);
        int chunkMaxZ = MathHelper.floor_double((axisalignedBB.maxZ + 2.0) / 16.0);
        int x = chunkMinX;
        while (x <= chunkMaxX) {
            int z = chunkMinZ;
            while (z <= chunkMaxZ) {
                if (this.invoker.getWrapper().getWorld().getChunkProvider().chunkExists(x, z)) {
                    this.invoker.getWrapper().getWorld().getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity(this.invoker.getWrapper().getPlayer(), axisalignedBB, list, null);
                }
                ++z;
            }
            ++x;
        }
        return list;
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

