/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class CommandKill
extends CommandBase {
    private static final String __OBFID = "CL_00000570";

    @Override
    public String getCommandName() {
        return "kill";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.kill.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        EntityPlayerMP var3 = CommandKill.getCommandSenderAsPlayer(par1ICommandSender);
        var3.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.kill.success", new Object[0]));
    }
}

