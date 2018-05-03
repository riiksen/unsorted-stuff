/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.modules.DefaultModule;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class CmdBindChange
extends Command {
    public CmdBindChange() {
        super("bind set ", "[Mod] [Key]", "Changes the current keybind of a mod");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split(" ");
        String modName = args[2];
        String code = args[3].toUpperCase();
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            if (!mod.getName().equalsIgnoreCase(modName)) continue;
            mod.setKeyBind(Keyboard.getKeyIndex((String)code));
            Resilience.getInstance().getLogger().infoChat("Set the keybind of the mod \u00a7b" + mod.getName() + " \u00a7fto \u00a7b" + Keyboard.getKeyName((int)mod.getKeyCode()) + "\u00a7f. Next time right click on the mod's button in the GUI and change the bind from there!");
            Resilience.getInstance().getFileManager().saveBinds(new String[0]);
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("Mod not found: \u00a7c" + modName + "\u00a7f. \u00a7bTry right clicking on the mod's button in the GUI and changing it from there!");
        return true;
    }
}

