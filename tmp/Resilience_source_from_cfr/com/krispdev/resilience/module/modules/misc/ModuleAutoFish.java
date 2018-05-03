/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class ModuleAutoFish
extends DefaultModule {
    public ModuleAutoFish() {
        super("AutoFish", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Automatically fishes for you; casts and recasts.");
    }

    @Override
    public void onPacketReceive(EventPacketReceive event) {
        S12PacketEntityVelocity p;
        Entity e;
        Packet packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity && (e = this.invoker.getEntityById(this.invoker.getPacketVelocityEntityId(p = (S12PacketEntityVelocity)packet))) instanceof EntityFishHook) {
            int x = this.invoker.getXMovePacketVel(p);
            int y = this.invoker.getYMovePacketVel(p);
            int z = this.invoker.getZMovePacketVel(p);
            if (x == 0 && y != 0 && z == 0) {
                new Thread(){

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(40);
                            ModuleAutoFish.this.invoker.rightClick();
                            Thread.sleep(700);
                            ModuleAutoFish.this.invoker.rightClick();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
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

