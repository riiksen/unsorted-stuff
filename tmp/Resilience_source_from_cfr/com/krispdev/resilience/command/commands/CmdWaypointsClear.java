/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import java.util.List;

public class CmdWaypointsClear
extends Command {
    public CmdWaypointsClear() {
        super("waypoints clear", "", "Clears all the waypoints");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (!cmd.startsWith("waypoints clear")) {
            return false;
        }
        if (cmd.equalsIgnoreCase("waypoints clear")) {
            Waypoint.waypointsList.clear();
            Resilience.getInstance().getFileManager().saveWaypoints(new String[0]);
            Resilience.getInstance().getLogger().infoChat("Cleared all waypoints");
            return true;
        }
        return false;
    }
}

