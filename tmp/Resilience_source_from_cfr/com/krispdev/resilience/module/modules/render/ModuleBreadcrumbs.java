/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.module.modules.render;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModuleBreadcrumbs
extends DefaultModule {
    public ModuleBreadcrumbs() {
        super("Breadcrumbs", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws a line behind you");
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.invoker.getMotionX() != 0.0 || this.invoker.getMotionY() != 0.0 || this.invoker.getMotionZ() != 0.0) {
            Resilience.getInstance().getValues().breadcrumbPosList.add(new Double[]{this.invoker.getPosX(), this.invoker.getPosY(), this.invoker.getPosZ()});
        }
    }

    @Override
    public void onRender(EventOnRender render) {
        if (!this.isEnabled()) {
            return;
        }
        GL11.glPushMatrix();
        RenderUtils.setup3DLightlessModel();
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)3);
        for (Double[] pos : Resilience.getInstance().getValues().breadcrumbPosList) {
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)1.0f, (float)0.7f);
            GL11.glVertex3d((double)(pos[0] - this.invoker.getRenderPosX()), (double)(pos[1] - this.invoker.getRenderPosY() - (double)this.invoker.getEntityHeight(Resilience.getInstance().getWrapper().getPlayer())), (double)(pos[2] - this.invoker.getRenderPosZ()));
        }
        GL11.glEnd();
        RenderUtils.shutdown3DLightlessModel();
        GL11.glPopMatrix();
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}

