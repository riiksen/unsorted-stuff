/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty
extends CommandBase {
    private static final String __OBFID = "CL_00000422";

    @Override
    public String getCommandName() {
        return "difficulty";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.difficulty.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length <= 0) {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
        EnumDifficulty var3 = this.func_147201_h(par1ICommandSender, par2ArrayOfStr[0]);
        MinecraftServer.getServer().func_147139_a(var3);
        CommandDifficulty.notifyAdmins(par1ICommandSender, "commands.difficulty.success", new ChatComponentTranslation(var3.getDifficultyResourceKey(), new Object[0]));
    }

    protected EnumDifficulty func_147201_h(ICommandSender p_147201_1_, String p_147201_2_) {
        return !p_147201_2_.equalsIgnoreCase("peaceful") && !p_147201_2_.equalsIgnoreCase("p") ? (!p_147201_2_.equalsIgnoreCase("easy") && !p_147201_2_.equalsIgnoreCase("e") ? (!p_147201_2_.equalsIgnoreCase("normal") && !p_147201_2_.equalsIgnoreCase("n") ? (!p_147201_2_.equalsIgnoreCase("hard") && !p_147201_2_.equalsIgnoreCase("h") ? EnumDifficulty.getDifficultyEnum(CommandDifficulty.parseIntBounded(p_147201_1_, p_147201_2_, 0, 3)) : EnumDifficulty.HARD) : EnumDifficulty.NORMAL) : EnumDifficulty.EASY) : EnumDifficulty.PEACEFUL;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandDifficulty.getListOfStringsMatchingLastWord(par2ArrayOfStr, "peaceful", "easy", "normal", "hard") : null;
    }
}

