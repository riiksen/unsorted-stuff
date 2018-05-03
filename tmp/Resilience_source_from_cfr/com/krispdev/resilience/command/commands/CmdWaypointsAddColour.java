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

public class CmdWaypointsAddColour
extends Command {
    public CmdWaypointsAddColour() {
        super("waypoints add ", "[name] [%r] [%g] [%b]", "Adds a waypoint at your current coords (with color)");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (!cmd.startsWith("waypoints add")) {
            return false;
        }
        String[] check = cmd.split(" ");
        if (check.length < 6) {
            return false;
        }
        for (Waypoint w : Waypoint.waypointsList) {
            if (!w.getName().equalsIgnoreCase(check[2])) continue;
            Resilience.getInstance().getLogger().warningChat("Already waypoint with name \u00a7b" + w.getName());
            return true;
        }
        Waypoint.waypointsList.add(new Waypoint(check[2], (int)Math.round(Resilience.getInstance().getInvoker().getPosX()), (int)Math.round(Resilience.getInstance().getInvoker().getPosY()), (int)Math.round(Resilience.getInstance().getInvoker().getPosZ()), Float.parseFloat(check[3]) / 100.0f, Float.parseFloat(check[5]) / 100.0f, Float.parseFloat(check[4]) / 100.0f));
        Resilience.getInstance().getFileManager().saveWaypoints(new String[0]);
        Resilience.getInstance().getLogger().infoChat("Successfully added waypoint \u00a7b" + check[2]);
        return true;
    }
}

