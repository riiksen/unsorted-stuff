/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;

public class CommandScoreboard
extends CommandBase {
    private static final String __OBFID = "CL_00000896";

    @Override
    public String getCommandName() {
        return "scoreboard";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.scoreboard.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1) {
            if (par2ArrayOfStr[0].equalsIgnoreCase("objectives")) {
                if (par2ArrayOfStr.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (par2ArrayOfStr[1].equalsIgnoreCase("list")) {
                    this.func_147196_d(par1ICommandSender);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                    if (par2ArrayOfStr.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.func_147193_c(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.func_147191_h(par1ICommandSender, par2ArrayOfStr[2]);
                } else {
                    if (!par2ArrayOfStr[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.func_147198_k(par1ICommandSender, par2ArrayOfStr, 2);
                }
                return;
            }
            if (par2ArrayOfStr[0].equalsIgnoreCase("players")) {
                if (par2ArrayOfStr.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (par2ArrayOfStr[1].equalsIgnoreCase("list")) {
                    if (par2ArrayOfStr.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.func_147195_l(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                    if (par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.func_147197_m(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.func_147197_m(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("set")) {
                    if (par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.func_147197_m(par1ICommandSender, par2ArrayOfStr, 2);
                } else {
                    if (!par2ArrayOfStr[1].equalsIgnoreCase("reset")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.func_147187_n(par1ICommandSender, par2ArrayOfStr, 2);
                }
                return;
            }
            if (par2ArrayOfStr[0].equalsIgnoreCase("teams")) {
                if (par2ArrayOfStr.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (par2ArrayOfStr[1].equalsIgnoreCase("list")) {
                    if (par2ArrayOfStr.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.func_147186_g(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                    if (par2ArrayOfStr.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.func_147185_d(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.func_147194_f(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("empty")) {
                    if (par2ArrayOfStr.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.func_147188_j(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("join")) {
                    if (!(par2ArrayOfStr.length >= 4 || par2ArrayOfStr.length == 3 && par1ICommandSender instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.func_147190_h(par1ICommandSender, par2ArrayOfStr, 2);
                } else if (par2ArrayOfStr[1].equalsIgnoreCase("leave")) {
                    if (par2ArrayOfStr.length < 3 && !(par1ICommandSender instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                    }
                    this.func_147199_i(par1ICommandSender, par2ArrayOfStr, 2);
                } else {
                    if (!par2ArrayOfStr[1].equalsIgnoreCase("option")) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }
                    if (par2ArrayOfStr.length != 4 && par2ArrayOfStr.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    this.func_147200_e(par1ICommandSender, par2ArrayOfStr, 2);
                }
                return;
            }
        }
        throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
    }

    protected Scoreboard func_147192_d() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }

    protected ScoreObjective func_147189_a(String p_147189_1_, boolean p_147189_2_) {
        Scoreboard var3 = this.func_147192_d();
        ScoreObjective var4 = var3.getObjective(p_147189_1_);
        if (var4 == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", p_147189_1_);
        }
        if (p_147189_2_ && var4.getCriteria().isReadOnly()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", p_147189_1_);
        }
        return var4;
    }

    protected ScorePlayerTeam func_147183_a(String p_147183_1_) {
        Scoreboard var2 = this.func_147192_d();
        ScorePlayerTeam var3 = var2.getTeam(p_147183_1_);
        if (var3 == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", p_147183_1_);
        }
        return var3;
    }

    protected void func_147193_c(ICommandSender p_147193_1_, String[] p_147193_2_, int p_147193_3_) {
        String var4 = p_147193_2_[p_147193_3_++];
        String var5 = p_147193_2_[p_147193_3_++];
        Scoreboard var6 = this.func_147192_d();
        IScoreObjectiveCriteria var7 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.field_96643_a.get(var5);
        if (var7 == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", var5);
        }
        if (var6.getObjective(var4) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", var4);
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", var4, 16);
        }
        if (var4.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        if (p_147193_2_.length > p_147193_3_) {
            String var8 = CommandScoreboard.func_147178_a(p_147193_1_, p_147193_2_, p_147193_3_).getUnformattedText();
            if (var8.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", var8, 32);
            }
            if (var8.length() > 0) {
                var6.addScoreObjective(var4, var7).setDisplayName(var8);
            } else {
                var6.addScoreObjective(var4, var7);
            }
        } else {
            var6.addScoreObjective(var4, var7);
        }
        CommandScoreboard.notifyAdmins(p_147193_1_, "commands.scoreboard.objectives.add.success", var4);
    }

    protected void func_147185_d(ICommandSender p_147185_1_, String[] p_147185_2_, int p_147185_3_) {
        String var4 = p_147185_2_[p_147185_3_++];
        Scoreboard var5 = this.func_147192_d();
        if (var5.getTeam(var4) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", var4);
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", var4, 16);
        }
        if (var4.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        if (p_147185_2_.length > p_147185_3_) {
            String var6 = CommandScoreboard.func_147178_a(p_147185_1_, p_147185_2_, p_147185_3_).getUnformattedText();
            if (var6.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", var6, 32);
            }
            if (var6.length() > 0) {
                var5.createTeam(var4).setTeamName(var6);
            } else {
                var5.createTeam(var4);
            }
        } else {
            var5.createTeam(var4);
        }
        CommandScoreboard.notifyAdmins(p_147185_1_, "commands.scoreboard.teams.add.success", var4);
    }

    protected void func_147200_e(ICommandSender p_147200_1_, String[] p_147200_2_, int p_147200_3_) {
        ScorePlayerTeam var4;
        if ((var4 = this.func_147183_a(p_147200_2_[p_147200_3_++])) != null) {
            String var5;
            if (!((var5 = p_147200_2_[p_147200_3_++].toLowerCase()).equalsIgnoreCase("color") || var5.equalsIgnoreCase("friendlyfire") || var5.equalsIgnoreCase("seeFriendlyInvisibles"))) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            if (p_147200_2_.length == 4) {
                if (var5.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var5, CommandScoreboard.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)));
                }
                if (!var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var5, CommandScoreboard.joinNiceStringFromCollection(Arrays.asList("true", "false")));
            }
            String var6 = p_147200_2_[p_147200_3_++];
            if (var5.equalsIgnoreCase("color")) {
                EnumChatFormatting var7 = EnumChatFormatting.getValueByName(var6);
                if (var7 == null || var7.isFancyStyling()) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var5, CommandScoreboard.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)));
                }
                var4.setNamePrefix(var7.toString());
                var4.setNameSuffix(EnumChatFormatting.RESET.toString());
            } else if (var5.equalsIgnoreCase("friendlyfire")) {
                if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var5, CommandScoreboard.joinNiceStringFromCollection(Arrays.asList("true", "false")));
                }
                var4.setAllowFriendlyFire(var6.equalsIgnoreCase("true"));
            } else if (var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var5, CommandScoreboard.joinNiceStringFromCollection(Arrays.asList("true", "false")));
                }
                var4.setSeeFriendlyInvisiblesEnabled(var6.equalsIgnoreCase("true"));
            }
            CommandScoreboard.notifyAdmins(p_147200_1_, "commands.scoreboard.teams.option.success", var5, var4.getRegisteredName(), var6);
        }
    }

    protected void func_147194_f(ICommandSender p_147194_1_, String[] p_147194_2_, int p_147194_3_) {
        ScorePlayerTeam var5;
        Scoreboard var4 = this.func_147192_d();
        if ((var5 = this.func_147183_a(p_147194_2_[p_147194_3_++])) != null) {
            var4.removeTeam(var5);
            CommandScoreboard.notifyAdmins(p_147194_1_, "commands.scoreboard.teams.remove.success", var5.getRegisteredName());
        }
    }

    protected void func_147186_g(ICommandSender p_147186_1_, String[] p_147186_2_, int p_147186_3_) {
        Scoreboard var4 = this.func_147192_d();
        if (p_147186_2_.length > p_147186_3_) {
            ScorePlayerTeam var5;
            if ((var5 = this.func_147183_a(p_147186_2_[p_147186_3_++])) == null) {
                return;
            }
            Collection var6 = var5.getMembershipCollection();
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", var5.getRegisteredName());
            }
            ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", var6.size(), var5.getRegisteredName());
            var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147186_1_.addChatMessage(var7);
            p_147186_1_.addChatMessage(new ChatComponentText(CommandScoreboard.joinNiceString(var6.toArray())));
        } else {
            Collection var9 = var4.getTeams();
            if (var9.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            ChatComponentTranslation var10 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", var9.size());
            var10.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147186_1_.addChatMessage(var10);
            for (ScorePlayerTeam var8 : var9) {
                p_147186_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", var8.getRegisteredName(), var8.func_96669_c(), var8.getMembershipCollection().size()));
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void func_147190_h(ICommandSender p_147190_1_, String[] p_147190_2_, int p_147190_3_) {
        block5 : {
            var4 = this.func_147192_d();
            var5 = p_147190_2_[p_147190_3_++];
            var6 = new HashSet<String>();
            var7 = new HashSet<String>();
            if (!(p_147190_1_ instanceof EntityPlayer) || p_147190_3_ != p_147190_2_.length) ** GOTO lbl16
            var8 = CommandScoreboard.getCommandSenderAsPlayer(p_147190_1_).getCommandSenderName();
            if (var4.func_151392_a(var8, var5)) {
                var6.add(var8);
            } else {
                var7.add(var8);
            }
            break block5;
lbl-1000: // 1 sources:
            {
                if (var4.func_151392_a(var8 = CommandScoreboard.func_96332_d(p_147190_1_, p_147190_2_[p_147190_3_++]), var5)) {
                    var6.add(var8);
                    continue;
                }
                var7.add(var8);
lbl16: // 3 sources:
                ** while (p_147190_3_ < p_147190_2_.length)
            }
        }
        if (!var6.isEmpty()) {
            CommandScoreboard.notifyAdmins(p_147190_1_, "commands.scoreboard.teams.join.success", new Object[]{var6.size(), var5, CommandScoreboard.joinNiceString(var6.toArray(new String[0]))});
        }
        if (var7.isEmpty() != false) return;
        throw new CommandException("commands.scoreboard.teams.join.failure", new Object[]{var7.size(), var5, CommandScoreboard.joinNiceString(var7.toArray(new String[0]))});
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void func_147199_i(ICommandSender p_147199_1_, String[] p_147199_2_, int p_147199_3_) {
        block5 : {
            var4 = this.func_147192_d();
            var5 = new HashSet<String>();
            var6 = new HashSet<String>();
            if (!(p_147199_1_ instanceof EntityPlayer) || p_147199_3_ != p_147199_2_.length) ** GOTO lbl15
            var7 = CommandScoreboard.getCommandSenderAsPlayer(p_147199_1_).getCommandSenderName();
            if (var4.func_96524_g(var7)) {
                var5.add(var7);
            } else {
                var6.add(var7);
            }
            break block5;
lbl-1000: // 1 sources:
            {
                if (var4.func_96524_g(var7 = CommandScoreboard.func_96332_d(p_147199_1_, p_147199_2_[p_147199_3_++]))) {
                    var5.add(var7);
                    continue;
                }
                var6.add(var7);
lbl15: // 3 sources:
                ** while (p_147199_3_ < p_147199_2_.length)
            }
        }
        if (!var5.isEmpty()) {
            CommandScoreboard.notifyAdmins(p_147199_1_, "commands.scoreboard.teams.leave.success", new Object[]{var5.size(), CommandScoreboard.joinNiceString(var5.toArray(new String[0]))});
        }
        if (var6.isEmpty() != false) return;
        throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[]{var6.size(), CommandScoreboard.joinNiceString(var6.toArray(new String[0]))});
    }

    protected void func_147188_j(ICommandSender p_147188_1_, String[] p_147188_2_, int p_147188_3_) {
        ScorePlayerTeam var5;
        Scoreboard var4 = this.func_147192_d();
        if ((var5 = this.func_147183_a(p_147188_2_[p_147188_3_++])) != null) {
            ArrayList var6 = new ArrayList(var5.getMembershipCollection());
            if (var6.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", var5.getRegisteredName());
            }
            for (String var8 : var6) {
                var4.removePlayerFromTeam(var8, var5);
            }
            CommandScoreboard.notifyAdmins(p_147188_1_, "commands.scoreboard.teams.empty.success", var6.size(), var5.getRegisteredName());
        }
    }

    protected void func_147191_h(ICommandSender p_147191_1_, String p_147191_2_) {
        Scoreboard var3 = this.func_147192_d();
        ScoreObjective var4 = this.func_147189_a(p_147191_2_, false);
        var3.func_96519_k(var4);
        CommandScoreboard.notifyAdmins(p_147191_1_, "commands.scoreboard.objectives.remove.success", p_147191_2_);
    }

    protected void func_147196_d(ICommandSender p_147196_1_) {
        Scoreboard var2 = this.func_147192_d();
        Collection var3 = var2.getScoreObjectives();
        if (var3.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        ChatComponentTranslation var4 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", var3.size());
        var4.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        p_147196_1_.addChatMessage(var4);
        for (ScoreObjective var6 : var3) {
            p_147196_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", var6.getName(), var6.getDisplayName(), var6.getCriteria().func_96636_a()));
        }
    }

    protected void func_147198_k(ICommandSender p_147198_1_, String[] p_147198_2_, int p_147198_3_) {
        Scoreboard var4 = this.func_147192_d();
        String var5 = p_147198_2_[p_147198_3_++];
        int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
        ScoreObjective var7 = null;
        if (p_147198_2_.length == 4) {
            var7 = this.func_147189_a(p_147198_2_[p_147198_3_++], false);
        }
        if (var6 < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", var5);
        }
        var4.func_96530_a(var6, var7);
        if (var7 != null) {
            CommandScoreboard.notifyAdmins(p_147198_1_, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(var6), var7.getName());
        } else {
            CommandScoreboard.notifyAdmins(p_147198_1_, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(var6));
        }
    }

    protected void func_147195_l(ICommandSender p_147195_1_, String[] p_147195_2_, int p_147195_3_) {
        Scoreboard var4 = this.func_147192_d();
        if (p_147195_2_.length > p_147195_3_) {
            Map var6;
            String var5;
            if ((var6 = var4.func_96510_d(var5 = CommandScoreboard.func_96332_d(p_147195_1_, p_147195_2_[p_147195_3_++]))).size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", var5);
            }
            ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", var6.size(), var5);
            var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147195_1_.addChatMessage(var7);
            for (Score var9 : var6.values()) {
                p_147195_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", var9.getScorePoints(), var9.func_96645_d().getDisplayName(), var9.func_96645_d().getName()));
            }
        } else {
            Collection var10 = var4.getObjectiveNames();
            if (var10.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            ChatComponentTranslation var11 = new ChatComponentTranslation("commands.scoreboard.players.list.count", var10.size());
            var11.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147195_1_.addChatMessage(var11);
            p_147195_1_.addChatMessage(new ChatComponentText(CommandScoreboard.joinNiceString(var10.toArray())));
        }
    }

    protected void func_147197_m(ICommandSender p_147197_1_, String[] p_147197_2_, int p_147197_3_) {
        String var4 = p_147197_2_[p_147197_3_ - 1];
        String var5 = CommandScoreboard.func_96332_d(p_147197_1_, p_147197_2_[p_147197_3_++]);
        ScoreObjective var6 = this.func_147189_a(p_147197_2_[p_147197_3_++], true);
        int n = var4.equalsIgnoreCase("set") ? CommandScoreboard.parseInt(p_147197_1_, p_147197_2_[p_147197_3_++]) : CommandScoreboard.parseIntWithMin(p_147197_1_, p_147197_2_[p_147197_3_++], 1);
        int var7 = n;
        Scoreboard var8 = this.func_147192_d();
        Score var9 = var8.func_96529_a(var5, var6);
        if (var4.equalsIgnoreCase("set")) {
            var9.func_96647_c(var7);
        } else if (var4.equalsIgnoreCase("add")) {
            var9.func_96649_a(var7);
        } else {
            var9.func_96646_b(var7);
        }
        CommandScoreboard.notifyAdmins(p_147197_1_, "commands.scoreboard.players.set.success", var6.getName(), var5, var9.getScorePoints());
    }

    protected void func_147187_n(ICommandSender p_147187_1_, String[] p_147187_2_, int p_147187_3_) {
        Scoreboard var4 = this.func_147192_d();
        String var5 = CommandScoreboard.func_96332_d(p_147187_1_, p_147187_2_[p_147187_3_++]);
        var4.func_96515_c(var5);
        CommandScoreboard.notifyAdmins(p_147187_1_, "commands.scoreboard.players.reset.success", var5);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "objectives", "players", "teams");
        }
        if (par2ArrayOfStr[0].equalsIgnoreCase("objectives")) {
            if (par2ArrayOfStr.length == 2) {
                return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "list", "add", "remove", "setdisplay");
            }
            if (par2ArrayOfStr[1].equalsIgnoreCase("add")) {
                if (par2ArrayOfStr.length == 4) {
                    Set var3 = IScoreObjectiveCriteria.field_96643_a.keySet();
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, var3);
                }
            } else if (par2ArrayOfStr[1].equalsIgnoreCase("remove")) {
                if (par2ArrayOfStr.length == 3) {
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147184_a(false));
                }
            } else if (par2ArrayOfStr[1].equalsIgnoreCase("setdisplay")) {
                if (par2ArrayOfStr.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "list", "sidebar", "belowName");
                }
                if (par2ArrayOfStr.length == 4) {
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147184_a(false));
                }
            }
        } else if (par2ArrayOfStr[0].equalsIgnoreCase("players")) {
            if (par2ArrayOfStr.length == 2) {
                return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "set", "add", "remove", "reset", "list");
            }
            if (!(par2ArrayOfStr[1].equalsIgnoreCase("set") || par2ArrayOfStr[1].equalsIgnoreCase("add") || par2ArrayOfStr[1].equalsIgnoreCase("remove"))) {
                if ((par2ArrayOfStr[1].equalsIgnoreCase("reset") || par2ArrayOfStr[1].equalsIgnoreCase("list")) && par2ArrayOfStr.length == 3) {
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147192_d().getObjectiveNames());
                }
            } else {
                if (par2ArrayOfStr.length == 3) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
                }
                if (par2ArrayOfStr.length == 4) {
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147184_a(true));
                }
            }
        } else if (par2ArrayOfStr[0].equalsIgnoreCase("teams")) {
            if (par2ArrayOfStr.length == 2) {
                return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (par2ArrayOfStr[1].equalsIgnoreCase("join")) {
                if (par2ArrayOfStr.length == 3) {
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147192_d().getTeamNames());
                }
                if (par2ArrayOfStr.length >= 4) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
                }
            } else {
                if (par2ArrayOfStr[1].equalsIgnoreCase("leave")) {
                    return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
                }
                if (!(par2ArrayOfStr[1].equalsIgnoreCase("empty") || par2ArrayOfStr[1].equalsIgnoreCase("list") || par2ArrayOfStr[1].equalsIgnoreCase("remove"))) {
                    if (par2ArrayOfStr[1].equalsIgnoreCase("option")) {
                        if (par2ArrayOfStr.length == 3) {
                            return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147192_d().getTeamNames());
                        }
                        if (par2ArrayOfStr.length == 4) {
                            return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "color", "friendlyfire", "seeFriendlyInvisibles");
                        }
                        if (par2ArrayOfStr.length == 5) {
                            if (par2ArrayOfStr[3].equalsIgnoreCase("color")) {
                                return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, EnumChatFormatting.getValidValues(true, false));
                            }
                            if (par2ArrayOfStr[3].equalsIgnoreCase("friendlyfire") || par2ArrayOfStr[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return CommandScoreboard.getListOfStringsMatchingLastWord(par2ArrayOfStr, "true", "false");
                            }
                        }
                    }
                } else if (par2ArrayOfStr.length == 3) {
                    return CommandScoreboard.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, this.func_147192_d().getTeamNames());
                }
            }
        }
        return null;
    }

    protected List func_147184_a(boolean p_147184_1_) {
        Collection var2 = this.func_147192_d().getScoreObjectives();
        ArrayList<String> var3 = new ArrayList<String>();
        for (ScoreObjective var5 : var2) {
            if (p_147184_1_ && var5.getCriteria().isReadOnly()) continue;
            var3.add(var5.getName());
        }
        return var3;
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        return par1ArrayOfStr[0].equalsIgnoreCase("players") ? par2 == 2 : (!par1ArrayOfStr[0].equalsIgnoreCase("teams") ? false : par2 == 2 || par2 == 3);
    }
}

