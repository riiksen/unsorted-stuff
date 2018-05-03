/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.commands.CmdAllOff;
import com.krispdev.resilience.command.commands.CmdAntiAfkSet;
import com.krispdev.resilience.command.commands.CmdBindChange;
import com.krispdev.resilience.command.commands.CmdBindRemove;
import com.krispdev.resilience.command.commands.CmdBreadcrumbsClear;
import com.krispdev.resilience.command.commands.CmdEnchant;
import com.krispdev.resilience.command.commands.CmdEnemiesClear;
import com.krispdev.resilience.command.commands.CmdEnemyAdd;
import com.krispdev.resilience.command.commands.CmdEnemyDel;
import com.krispdev.resilience.command.commands.CmdFakeChat;
import com.krispdev.resilience.command.commands.CmdFriendAdd;
import com.krispdev.resilience.command.commands.CmdFriendAddAlias;
import com.krispdev.resilience.command.commands.CmdFriendClear;
import com.krispdev.resilience.command.commands.CmdFriendDel;
import com.krispdev.resilience.command.commands.CmdGetIP;
import com.krispdev.resilience.command.commands.CmdHelp;
import com.krispdev.resilience.command.commands.CmdIRCNick;
import com.krispdev.resilience.command.commands.CmdIRCPrefixChange;
import com.krispdev.resilience.command.commands.CmdInvSee;
import com.krispdev.resilience.command.commands.CmdKillAuraMode;
import com.krispdev.resilience.command.commands.CmdMacroAdd;
import com.krispdev.resilience.command.commands.CmdMacroClear;
import com.krispdev.resilience.command.commands.CmdNameProtectSet;
import com.krispdev.resilience.command.commands.CmdPrefixChange;
import com.krispdev.resilience.command.commands.CmdReName;
import com.krispdev.resilience.command.commands.CmdReload;
import com.krispdev.resilience.command.commands.CmdRemoteView;
import com.krispdev.resilience.command.commands.CmdSay;
import com.krispdev.resilience.command.commands.CmdSearchAdd;
import com.krispdev.resilience.command.commands.CmdSearchClear;
import com.krispdev.resilience.command.commands.CmdSearchDel;
import com.krispdev.resilience.command.commands.CmdToggle;
import com.krispdev.resilience.command.commands.CmdTrackClear;
import com.krispdev.resilience.command.commands.CmdTrackSet;
import com.krispdev.resilience.command.commands.CmdVClip;
import com.krispdev.resilience.command.commands.CmdWaypointsAdd;
import com.krispdev.resilience.command.commands.CmdWaypointsAddColour;
import com.krispdev.resilience.command.commands.CmdWaypointsClear;
import com.krispdev.resilience.command.commands.CmdWaypointsDel;
import com.krispdev.resilience.command.commands.CmdWaypointsList;
import com.krispdev.resilience.command.commands.CmdXrayAdd;
import com.krispdev.resilience.command.commands.CmdXrayClear;
import com.krispdev.resilience.command.commands.CmdXrayDel;
import com.krispdev.resilience.command.commands.CmdXrayReset;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public abstract class Command {
    private String words;
    private String extras;
    private String desc;
    protected Minecraft mc = Resilience.getInstance().getWrapper().getMinecraft();
    public static ArrayList<Command> cmdList = new ArrayList();

    public Command(String words, String extras, String desc) {
        this.words = words;
        this.extras = extras;
        this.desc = desc;
    }

    public static void instantiateCommands() {
        Command.add(new CmdAllOff());
        Command.add(new CmdAntiAfkSet());
        Command.add(new CmdBindChange());
        Command.add(new CmdBindRemove());
        Command.add(new CmdBreadcrumbsClear());
        Command.add(new CmdEnchant());
        Command.add(new CmdEnemyAdd());
        Command.add(new CmdEnemyDel());
        Command.add(new CmdEnemiesClear());
        Command.add(new CmdFakeChat());
        Command.add(new CmdFriendAdd());
        Command.add(new CmdFriendAddAlias());
        Command.add(new CmdFriendClear());
        Command.add(new CmdFriendDel());
        Command.add(new CmdGetIP());
        Command.add(new CmdHelp());
        Command.add(new CmdInvSee());
        Command.add(new CmdIRCNick());
        Command.add(new CmdIRCPrefixChange());
        Command.add(new CmdKillAuraMode());
        Command.add(new CmdMacroAdd());
        Command.add(new CmdMacroClear());
        Command.add(new CmdReName());
        Command.add(new CmdNameProtectSet());
        Command.add(new CmdPrefixChange());
        Command.add(new CmdReload());
        Command.add(new CmdRemoteView());
        Command.add(new CmdSay());
        Command.add(new CmdSearchAdd());
        Command.add(new CmdSearchDel());
        Command.add(new CmdSearchClear());
        Command.add(new CmdToggle());
        Command.add(new CmdTrackSet());
        Command.add(new CmdTrackClear());
        Command.add(new CmdVClip());
        Command.add(new CmdWaypointsAdd());
        Command.add(new CmdWaypointsAddColour());
        Command.add(new CmdWaypointsClear());
        Command.add(new CmdWaypointsDel());
        Command.add(new CmdWaypointsList());
        Command.add(new CmdXrayAdd());
        Command.add(new CmdXrayDel());
        Command.add(new CmdXrayClear());
        Command.add(new CmdXrayReset());
    }

    private static void add(Command cmd) {
        cmdList.add(cmd);
    }

    public abstract boolean recieveCommand(String var1) throws Exception;

    public String getWords() {
        return this.words;
    }

    public String getExtras() {
        return this.extras;
    }

    public String getDesc() {
        return this.desc;
    }

    protected void setWords(String words) {
        this.words = words;
    }

    protected void setExtras(String extras) {
        this.extras = extras;
    }

    public String getFirstWord() {
        String[] wordArray = this.words.split(" ");
        return wordArray[0];
    }
}

