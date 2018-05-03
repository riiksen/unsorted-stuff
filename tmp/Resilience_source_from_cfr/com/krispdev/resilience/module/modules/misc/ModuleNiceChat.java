/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;

public class ModuleNiceChat
extends DefaultModule {
    public ModuleNiceChat() {
        super("NiceChat", 0);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Changes the chat font to Arial");
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getValues().niceChatEnabled = true;
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getValues().niceChatEnabled = false;
    }
}

