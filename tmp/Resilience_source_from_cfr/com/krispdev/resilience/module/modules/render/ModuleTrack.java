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
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ModuleTrack
extends DefaultModule {
    public ModuleTrack() {
        super("Track", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws a line behind the specified player");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        for (Object o : this.invoker.getEntityList()) {
            EntityOtherPlayerMP player;
            if (!(o instanceof EntityOtherPlayerMP) || !this.invoker.getPlayerName(player = (EntityOtherPlayerMP)o).equalsIgnoreCase(Resilience.getInstance().getValues().trackName)) continue;
            Resilience.getInstance().getValues().trackPosList.add(new Double[]{this.invoker.getPosX(player), this.invoker.getPosY(player), this.invoker.getPosZ(player)});
        }
    }

    @Override
    public void onRender(EventOnRender event) {
        GL11.glPushMatrix();
        RenderUtils.setup3DLightlessModel();
        GL11.glBegin((int)3);
        for (Double[] pos : Resilience.getInstance().getValues().trackPosList) {
            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.7f);
            GL11.glVertex3d((double)(pos[0] - this.invoker.getRenderPosX()), (double)(pos[1] - this.invoker.getRenderPosY()), (double)(pos[2] - this.invoker.getRenderPosZ()));
        }
        GL11.glEnd();
        RenderUtils.shutdown3DLightlessModel();
        GL11.glPopMatrix();
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

