/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.world;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class ModuleClickNuker
extends DefaultModule {
    private int xPos = -1;
    private int yPos = -1;
    private int zPos = -1;
    private Block selected;

    public ModuleClickNuker() {
        super("Click Nuker", 0);
        this.setDescription("Automatically destroys blocks around you when you click");
        this.setCategory(ModuleCategory.WORLD);
    }

    @Override
    public void onBlockClicked(EventBlockClick event) {
        if (!this.invoker.isInCreativeMode()) {
            return;
        }
        int y = (int)Resilience.getInstance().getValues().nukerRadius.getValue();
        while (y >= (int)(- Resilience.getInstance().getValues().nukerRadius.getValue())) {
            int z = (int)(- Resilience.getInstance().getValues().nukerRadius.getValue());
            while ((float)z <= Resilience.getInstance().getValues().nukerRadius.getValue()) {
                int x = (int)(- Resilience.getInstance().getValues().nukerRadius.getValue());
                while ((float)x <= Resilience.getInstance().getValues().nukerRadius.getValue()) {
                    this.xPos = Math.round(event.getX() + x);
                    this.yPos = Math.round(event.getY() + y);
                    this.zPos = Math.round(event.getZ() + z);
                    Block block = this.invoker.getBlock(this.xPos, this.yPos, this.zPos);
                    this.invoker.sendPacket(new C07PacketPlayerDigging(0, this.xPos, this.yPos, this.zPos, 1));
                    this.invoker.sendPacket(new C07PacketPlayerDigging(2, this.xPos, this.yPos, this.zPos, 1));
                    ++x;
                }
                ++z;
            }
            --y;
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

