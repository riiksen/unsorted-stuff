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
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import org.lwjgl.opengl.GL11;

public class ModuleProphuntESP
extends DefaultModule {
    private ArrayList<EntityFallingBlock> entities = new ArrayList();
    private int[] badIds = new int[]{78};
    private int ticks = 0;

    public ModuleProphuntESP() {
        super("Prophunt ESP", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Draws a coloured box around Prohunt objects");
    }

    @Override
    public void onRender(EventOnRender event) {
        this.entities.clear();
        for (Object o : this.invoker.getEntityList()) {
            EntityFallingBlock e;
            if (!(o instanceof EntityFallingBlock) || this.entities.contains(e = (EntityFallingBlock)o)) continue;
            this.entities.add(e);
        }
        for (EntityFallingBlock e : this.entities) {
            Block block = this.invoker.getBlock((int)this.invoker.getPosX(e) - 1, (int)this.invoker.getPosY(e), (int)this.invoker.getPosZ(e));
            GL11.glPushMatrix();
            RenderUtils.setup3DLightlessModel();
            RenderUtils.drawESP(false, this.invoker.getPosX(e) - this.invoker.getRenderPosX() - 0.5, this.invoker.getPosY(e) - this.invoker.getRenderPosY() - 0.5, this.invoker.getPosZ(e) - this.invoker.getRenderPosZ() - 0.5, this.invoker.getPosX(e) - this.invoker.getRenderPosX() + 0.5, this.invoker.getPosY(e) - this.invoker.getRenderPosY() + 0.5, this.invoker.getPosZ(e) - this.invoker.getRenderPosZ() + 0.5, 0.5, 0.5, 1.0, 0.19, 0.5, 0.5, 1.0, 1.0);
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

