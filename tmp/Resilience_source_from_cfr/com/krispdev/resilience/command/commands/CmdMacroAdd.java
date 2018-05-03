/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Macro;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class CmdMacroAdd
extends Command {
    public CmdMacroAdd() {
        super("macro add ", "[Key] [Command]", "Adds a macro");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] keybind;
        String[] args = cmd.split("macro add ");
        String[] args2 = args[1].split((keybind = args[1].split(" "))[0]);
        if (Character.isWhitespace(args2[1].charAt(0))) {
            args2[1] = args2[1].replaceFirst(" ", "");
        }
        Macro.macroList.add(new Macro(Keyboard.getKeyIndex((String)keybind[0].trim().toUpperCase()), args2[1]));
        Resilience.getInstance().getLogger().infoChat("Added a macro to \"\u00a7b" + keybind[0] + "\u00a7f\" that will say \"\u00a7b" + args2[1] + "\u00a7f\" in the chat");
        Resilience.getInstance().getFileManager().saveMacros(new String[0]);
        return true;
    }
}

