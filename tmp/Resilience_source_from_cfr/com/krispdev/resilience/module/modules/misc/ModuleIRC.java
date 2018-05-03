/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.online.irc.extern.PublicBot;

public class ModuleIRC
extends DefaultModule {
    public ModuleIRC() {
        super("IRC", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Enables/Disables the Internet Relay Chat ingame");
    }

    @Override
    public void onEnable() {
        new Thread(){

            @Override
            public void run() {
                Resilience.getInstance().getIRCManager().bot.joinChannel(Resilience.getInstance().getValues().ircChannel);
            }
        }.start();
        Resilience.getInstance().getValues().ircEnabled = true;
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().ircEnabled = false;
        Resilience.getInstance().getIRCManager().bot.partChannel(Resilience.getInstance().getValues().ircChannel);
    }

}

