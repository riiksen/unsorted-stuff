/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.world;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class ModuleNuker
extends DefaultModule {
    private boolean shouldSelect;
    private int xPos = -1;
    private int yPos = -1;
    private int zPos = -1;
    private Block selected;
    private ArrayList<Integer[]> positions = new ArrayList();

    public ModuleNuker() {
        super("Nuker", 49);
        this.setDescription("Automatically destroys blocks around you");
        this.setCategory(ModuleCategory.WORLD);
    }

    @Override
    public void onBlockClicked(EventBlockClick event) {
        if (this.invoker.isInCreativeMode()) {
            return;
        }
        if (this.selected == null) {
            Resilience.getInstance().getLogger().infoChat("Now nuking " + event.getBlock().getLocalizedName());
        }
        Block previous = this.selected;
        this.selected = event.getBlock();
        if (previous != null && this.selected != null && !previous.getLocalizedName().equalsIgnoreCase(this.selected.getLocalizedName())) {
            Resilience.getInstance().getLogger().infoChat("Now nuking " + this.selected.getLocalizedName());
        }
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.shouldSelect = !this.invoker.isInCreativeMode();
        this.positions.clear();
        int y = (int)Resilience.getInstance().getValues().nukerRadius.getValue();
        while (y >= (int)(- Resilience.getInstance().getValues().nukerRadius.getValue())) {
            int z = (int)(- Resilience.getInstance().getValues().nukerRadius.getValue());
            while ((float)z <= Resilience.getInstance().getValues().nukerRadius.getValue()) {
                int x = (int)(- Resilience.getInstance().getValues().nukerRadius.getValue());
                while ((float)x <= Resilience.getInstance().getValues().nukerRadius.getValue()) {
                    this.xPos = (int)Math.round(this.invoker.getPosX() + (double)x);
                    this.yPos = (int)((float)((int)Math.round(this.invoker.getPosY() + (double)y)) - this.invoker.getEntityHeight(Resilience.getInstance().getWrapper().getPlayer()) / 2.0f);
                    this.zPos = (int)Math.round(this.invoker.getPosZ() + (double)z);
                    Block block = this.invoker.getBlock(this.xPos, this.yPos, this.zPos);
                    if (this.shouldSelect) {
                        if (block != null && this.selected != null && this.invoker.getIdFromBlock(this.selected) == this.invoker.getIdFromBlock(block)) {
                            this.positions.add(new Integer[]{this.xPos, this.yPos, this.zPos});
                            this.invoker.sendPacket(new C07PacketPlayerDigging(0, this.xPos, this.yPos, this.zPos, 1));
                            this.invoker.sendPacket(new C07PacketPlayerDigging(2, this.xPos, this.yPos, this.zPos, 1));
                        }
                    } else if (this.invoker.getIdFromBlock(block) != 0) {
                        this.positions.add(new Integer[]{this.xPos, this.yPos, this.zPos});
                        this.invoker.sendPacket(new C07PacketPlayerDigging(0, this.xPos, this.yPos, this.zPos, 1));
                        this.invoker.sendPacket(new C07PacketPlayerDigging(2, this.xPos, this.yPos, this.zPos, 1));
                    }
                    ++x;
                }
                ++z;
            }
            --y;
        }
    }

    @Override
    public void onRender(EventOnRender event) {
        for (Integer[] pos : this.positions) {
            RenderUtils.drawESP(false, (double)pos[0].intValue() - this.invoker.getRenderPosX(), (double)pos[1].intValue() - this.invoker.getRenderPosY(), (double)pos[2].intValue() - this.invoker.getRenderPosZ(), (double)(pos[0] + 1) - this.invoker.getRenderPosX(), (double)(pos[1] + 1) - this.invoker.getRenderPosY(), (double)(pos[2] + 1) - this.invoker.getRenderPosZ(), 0.5, 0.5, 1.0, 0.15, 0.5, 0.5, 1.0, 0.15);
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

