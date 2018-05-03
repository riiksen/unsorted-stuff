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
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import net.minecraft.block.Block;
import org.lwjgl.opengl.GL11;

public class ModuleSearch
extends DefaultModule {
    private ArrayList<Float[]> blocksList = new ArrayList();

    public ModuleSearch() {
        super("Search", 0);
        this.setCategory(ModuleCategory.RENDER);
        this.setDescription("Lights up blocks that you say should be lit up");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        ++Resilience.getInstance().getValues().ticksForSearch;
        if (Resilience.getInstance().getValues().ticksForSearch > 70) {
            Resilience.getInstance().getValues().ticksForSearch = 0;
            this.blocksList.clear();
            new Thread(){
                int radius;
                {
                    this.radius = (int)Resilience.getInstance().getValues().searchRange.getValue();
                }

                @Override
                public void run() {
                    int x = (- this.radius) / 2;
                    while (x < this.radius / 2) {
                        int y = (- this.radius) / 2;
                        while (y < this.radius / 2) {
                            int z = (- this.radius) / 2;
                            while (z < this.radius / 2) {
                                int posX = (int)(ModuleSearch.this.invoker.getPosX() + (double)x);
                                int posY = (int)(ModuleSearch.this.invoker.getPosY() + (double)y);
                                int posZ = (int)(ModuleSearch.this.invoker.getPosZ() + (double)z);
                                Block block = ModuleSearch.this.invoker.getBlock(posX, posY, posZ);
                                for (Float[] id : Resilience.getInstance().getValues().searchIds) {
                                    if (ModuleSearch.this.invoker.getIdFromBlock(block) != (int)id[0].floatValue()) continue;
                                    ModuleSearch.this.blocksList.add(new Float[]{Float.valueOf(posX), Float.valueOf(posY), Float.valueOf(posZ), id[1], id[2], id[3]});
                                }
                                ++z;
                            }
                            ++y;
                        }
                        ++x;
                    }
                }
            }.start();
        }
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Float[] coords : this.blocksList) {
            GL11.glPushMatrix();
            RenderUtils.setup3DLightlessModel();
            RenderUtils.drawESP(false, (double)coords[0].floatValue() - this.invoker.getRenderPosX(), (double)coords[1].floatValue() - this.invoker.getRenderPosY(), (double)coords[2].floatValue() - this.invoker.getRenderPosZ(), (double)(coords[0].floatValue() + 1.0f) - this.invoker.getRenderPosX(), (double)(coords[1].floatValue() + 1.0f) - this.invoker.getRenderPosY(), (double)(coords[2].floatValue() + 1.0f) - this.invoker.getRenderPosZ(), coords[3].floatValue(), coords[4].floatValue(), coords[5].floatValue(), 0.19, coords[3].floatValue(), coords[4].floatValue(), coords[5].floatValue(), 1.0);
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

