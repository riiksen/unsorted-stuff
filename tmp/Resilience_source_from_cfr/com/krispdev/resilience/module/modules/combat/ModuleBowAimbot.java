/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ModuleBowAimbot
extends DefaultModule {
    private float pitch;
    private float yaw;
    private EntityLivingBase e;
    private EntityUtils entityUtils = new EntityUtils();

    public ModuleBowAimbot() {
        super("BowAimbot", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically aims your bow at entities");
    }

    @Override
    public void onPreMotion(EventPreMotion event) {
        if (this.invoker.getCurrentItem() != null && this.invoker.getCurrentItem().getItem() instanceof ItemBow) {
            this.e = this.getCursorEntity();
            if (this.e == null) {
                return;
            }
            this.pitch = this.invoker.getRotationPitch();
            this.yaw = this.invoker.getRotationYaw();
            this.silentAim(this.e);
        }
    }

    @Override
    public void onPostMotion(EventPostMotion event) {
        if (this.e != null && this.invoker.getCurrentItem() != null && this.invoker.getCurrentItem().getItem() instanceof ItemBow) {
            this.invoker.setRotationPitch(this.pitch);
            this.invoker.setRotationYaw(this.yaw);
        }
    }

    public void silentAim(EntityLivingBase e) {
        int bowCurrentCharge = this.invoker.getItemInUseDuration();
        float velocity = (float)bowCurrentCharge / 20.0f;
        if ((double)(velocity = (velocity * velocity + velocity * 2.0f) / 3.0f) < 0.1) {
            return;
        }
        if (velocity > 1.0f) {
            velocity = 1.0f;
        }
        double x = this.invoker.getPosX(e) - this.invoker.getPosX();
        double z = this.invoker.getPosZ(e) - this.invoker.getPosZ();
        double h = this.invoker.getPosY(e) + (double)this.invoker.getEyeHeight(e) - (this.invoker.getPosY() + (double)this.invoker.getEyeHeight());
        double h1 = Math.sqrt(x * x + z * z);
        double h2 = Math.sqrt(h1 * h1 + h * h);
        float f = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        float traj = - this.getTrajAngleSolutionLow((float)h1, (float)h, velocity);
        this.invoker.setRotationPitch(traj);
        this.invoker.setRotationYaw(f);
    }

    private float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt(sqrt)) / (double)(g * d3)));
    }

    public EntityLivingBase getCursorEntity() {
        EntityLivingBase poorEntity = null;
        double distance = 1000.0;
        for (Object o : this.invoker.getEntityList()) {
            double ydist;
            Entity e;
            if (!(o instanceof Entity) || !((e = (Entity)o) instanceof EntityLivingBase) || this.entityUtils.isThePlayer(e) || e.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer()) > 140.0f || !this.invoker.canEntityBeSeen(e) || ((EntityLivingBase)e).deathTime > 0 || this.entityUtils.isEntityFriend(e)) continue;
            if (poorEntity == null) {
                poorEntity = (EntityLivingBase)e;
            }
            double x = e.posX - this.invoker.getPosX();
            double z = e.posZ - this.invoker.getPosY();
            double h = this.invoker.getPosY() + (double)this.invoker.getEyeHeight() - (e.posY + (double)this.invoker.getEntityHeight(e));
            double h1 = Math.sqrt(x * x + z * z);
            float f = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
            float f1 = (float)(Math.atan2(h, h1) * 180.0 / 3.141592653589793);
            double xdist = this.getDistanceBetweenAngles(f, this.invoker.getRotationYaw() % 360.0f);
            double dist = Math.sqrt(xdist * xdist + (ydist = (double)this.getDistanceBetweenAngles(f1, this.invoker.getRotationPitch() % 360.0f)) * ydist);
            if (dist >= distance) continue;
            poorEntity = (EntityLivingBase)e;
            distance = dist;
        }
        return poorEntity;
    }

    private float getDistanceBetweenAngles(float par1, float par2) {
        float angle = Math.abs(par1 - par2) % 360.0f;
        if (angle > 180.0f) {
            angle = 360.0f - angle;
        }
        return angle;
    }

    @Override
    public void onRender(EventOnRender event) {
        if (this.e != null && this.isEnabled()) {
            GL11.glPushMatrix();
            RenderUtils.setup3DLightlessModel();
            double posX = this.e.lastTickPosX + (this.e.posX - this.e.lastTickPosX) - RenderManager.renderPosX;
            double posY = this.e.lastTickPosY + 1.0 + (this.e.posY - this.e.lastTickPosY) - RenderManager.renderPosY;
            double posZ = this.e.lastTickPosZ + (this.e.posZ - this.e.lastTickPosZ) - RenderManager.renderPosZ;
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f);
            GL11.glBegin((int)2);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
            GL11.glEnd();
            RenderUtils.shutdown3DLightlessModel();
            GL11.glPopMatrix();
        }
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

