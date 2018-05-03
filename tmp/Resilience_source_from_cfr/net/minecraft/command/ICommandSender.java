/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public interface ICommandSender {
    public String getCommandSenderName();

    public IChatComponent func_145748_c_();

    public void addChatMessage(IChatComponent var1);

    public boolean canCommandSenderUseCommand(int var1, String var2);

    public ChunkCoordinates getPlayerCoordinates();

    public World getEntityWorld();
}

