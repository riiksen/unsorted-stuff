/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class ModuleClientTips
extends DefaultModule {
    private Timer timer = new Timer();
    private int interval = 5000;
    private int index;
    private String[] idleTips = new String[]{"Did you know that you can change binds and see descriptions by right clicking a button in the GUI?", "Using the console isn't something only to be used by advanced players. Hit the \"Minus\" (\"-\") button and you're able to customize the client in many ways!", "Ever want to know what kind of enchantments a player has before you PvP them? .invsee will show you their armour and they're item in hand!", "See a player on a map but don't know where they are or what they're doing? .remoteview will show you where they are and what they're looking at!", "You can add friends by typing \"friend add [Username]\" in the console, by using the mod \"Middle Click Friends\", or by clicking the \"F\" button in the Text Radar! Friends will not be attacked by KillAura.", "Ever want to know where your friends are playing? Add them with the Resilience Online feature and you'll be able to see just that!"};

    public ModuleClientTips() {
        super("Client Tips", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Gives tips about the client every 5 minutes, or after an event.");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        this.interval = 300000;
        if (this.timer.hasTimePassed(this.interval)) {
            this.timer.reset();
            if (this.index >= this.idleTips.length) {
                this.index = 0;
            }
            Resilience.getInstance().getLogger().infoChat("\u00a7f[\u00a7bTIP\u00a7f] \u00a76" + this.idleTips[this.index]);
            ++this.index;
        }
    }

    @Override
    public void onPacketReceive(EventPacketReceive event) {
        String line;
        S02PacketChat p;
        if (event.getPacket() instanceof S02PacketChat && ((line = (p = (S02PacketChat)event.getPacket()).func_148915_c().getUnformattedText()).toLowerCase().contains(".help") || line.toLowerCase().contains(".legit") && !line.contains(this.invoker.getSessionUsername()))) {
            Resilience.getInstance().getLogger().infoChat("\u00a7f[\u00a7bTIP\u00a7f] \u00a7cDid someone just tell you to type \".help\"? Don't worry! You can type \".say .help\" and it will say it in the chat!");
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

