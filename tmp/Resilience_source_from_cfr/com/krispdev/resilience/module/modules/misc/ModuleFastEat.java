/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOutwardPacket;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class ModuleFastEat
extends DefaultModule {
    public ModuleFastEat() {
        super("FastEat", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Allows really fast food eating");
    }

    @Override
    public void onOutwardPacket(EventOutwardPacket event) {
        if (!this.invoker.isOnGround()) {
            return;
        }
        Packet packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            if (!(this.invoker.getCurrentItem().getItem() instanceof ItemFood)) {
                return;
            }
            C08PacketPlayerBlockPlacement packetBlockPlacement = (C08PacketPlayerBlockPlacement)packet;
            if (packetBlockPlacement.func_149576_c() != -1 || packetBlockPlacement.func_149571_d() != -1 || packetBlockPlacement.func_149570_e() != -1 || packetBlockPlacement.func_149568_f() != 255) {
                return;
            }
            event.addPacketToList(new C09PacketHeldItemChange(Minecraft.getMinecraft().thePlayer.inventory.currentItem));
            int i = 0;
            while (i < 40) {
                event.addPacketToList(new C03PacketPlayer(false));
                ++i;
            }
            event.addPacketToList(new C07PacketPlayerDigging(5, 0, 0, 0, 255));
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

