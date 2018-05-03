/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;

public class CmdWaypointsAdd
extends Command {
    public CmdWaypointsAdd() {
        super("waypoints add ", "[name]", "Adds a waypoint at your coordinates");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (!cmd.startsWith("waypoints add")) {
            return false;
        }
        String[] check = cmd.split(" ");
        if (check.length > 3) {
            return false;
        }
        String[] args = cmd.split("add ");
        for (Waypoint w : Waypoint.waypointsList) {
            if (!w.getName().equalsIgnoreCase(check[2])) continue;
            Resilience.getInstance().getLogger().warningChat("Already waypoint with name \u00a7b" + w.getName());
            return true;
        }
        Waypoint.waypointsList.add(new Waypoint(args[1], (int)Resilience.getInstance().getInvoker().getPosX(), (int)Resilience.getInstance().getInvoker().getPosY(), (int)Resilience.getInstance().getInvoker().getPosZ(), 1.0f, 1.0f, 1.0f));
        Resilience.getInstance().getFileManager().saveWaypoints(new String[0]);
        Resilience.getInstance().getLogger().infoChat("Successfully added waypoint \u00a7b" + args[1]);
        return true;
    }
}

