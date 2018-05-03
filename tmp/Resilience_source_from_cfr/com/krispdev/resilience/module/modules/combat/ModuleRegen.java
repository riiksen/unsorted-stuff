/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class ModuleRegen
extends DefaultModule {
    private boolean shouldHeal = true;

    public ModuleRegen() {
        super("Regen", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Regenerates your health");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.invoker.getFoodLevel() > 17 && !this.invoker.isInCreativeMode() && this.invoker.getHealth() < 19.0f && this.invoker.isOnGround() && this.shouldHeal) {
            this.shouldHeal = false;
            new Thread(){

                @Override
                public void run() {
                    int s = 0;
                    while (s < 3100) {
                        ModuleRegen.this.invoker.sendPacket(new C03PacketPlayer());
                        s = (short)(s + 1);
                    }
                    ModuleRegen.access$1(ModuleRegen.this, true);
                }
            }.start();
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

    static /* synthetic */ void access$1(ModuleRegen moduleRegen, boolean bl) {
        moduleRegen.shouldHeal = bl;
    }

}

