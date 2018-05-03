/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;

public class CmdHelp
extends Command {
    private int resultsPerPage = 4;
    private ArrayList<Page> pages = new ArrayList();

    public CmdHelp() {
        super("help", " [Page #]", "Gives command help");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("help ");
        int page = Integer.parseInt(args[1].trim());
        if (page == 0) {
            Resilience.getInstance().getLogger().warningChat("Page value cannot be zero");
            return true;
        }
        this.pages.clear();
        int iterator = 0;
        int pageNum = 0;
        int numOfPages = 0;
        numOfPages = Command.cmdList.size() % this.resultsPerPage != 0 ? Command.cmdList.size() / this.resultsPerPage + 2 : Command.cmdList.size() / this.resultsPerPage + 1;
        int i = 1;
        while (i <= numOfPages) {
            this.pages.add(new Page(i));
            ++i;
        }
        for (Command command : Command.cmdList) {
            if (++iterator >= this.resultsPerPage) {
                ++pageNum;
                iterator = 0;
            }
            this.pages.get((int)pageNum).commands.add(command);
        }
        if (page > this.pages.size()) {
            Resilience.getInstance().getLogger().warningChat("Page value too high! Maximum: " + this.pages.size());
            return true;
        }
        Resilience.getInstance().getLogger().infoChat("\u00a77Showing page " + page + "/" + numOfPages);
        Resilience.getInstance().getInvoker().addChatMessage("");
        for (Command com : this.pages.get((int)(page - 1)).commands) {
            Resilience.getInstance().getInvoker().addChatMessage("\u00a7b" + Resilience.getInstance().getCmdPrefix() + com.getWords() + com.getExtras() + " \u00a7f- " + com.getDesc());
        }
        return true;
    }

    private class Page {
        private int number;
        public ArrayList<Command> commands;

        public Page(int number) {
            this.commands = new ArrayList();
            this.number = number;
        }
    }

}

