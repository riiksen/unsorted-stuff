/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;

public class ServerSelectionList
extends GuiListExtended {
    private final GuiMultiplayer field_148200_k;
    private final List field_148198_l = Lists.newArrayList();
    private final List field_148199_m = Lists.newArrayList();
    private final GuiListExtended.IGuiListEntry field_148196_n = new ServerListEntryLanScan();
    private int field_148197_o = -1;
    private static final String __OBFID = "CL_00000819";

    public ServerSelectionList(GuiMultiplayer p_i45049_1_, Minecraft p_i45049_2_, int p_i45049_3_, int p_i45049_4_, int p_i45049_5_, int p_i45049_6_, int p_i45049_7_) {
        super(p_i45049_2_, p_i45049_3_, p_i45049_4_, p_i45049_5_, p_i45049_6_, p_i45049_7_);
        this.field_148200_k = p_i45049_1_;
    }

    @Override
    public GuiListExtended.IGuiListEntry func_148180_b(int p_148180_1_) {
        if (p_148180_1_ < this.field_148198_l.size()) {
            return (GuiListExtended.IGuiListEntry)this.field_148198_l.get(p_148180_1_);
        }
        if ((p_148180_1_ -= this.field_148198_l.size()) == 0) {
            return this.field_148196_n;
        }
        return (GuiListExtended.IGuiListEntry)this.field_148199_m.get(--p_148180_1_);
    }

    @Override
    protected int getSize() {
        return this.field_148198_l.size() + 1 + this.field_148199_m.size();
    }

    public void func_148192_c(int p_148192_1_) {
        this.field_148197_o = p_148192_1_;
    }

    @Override
    protected boolean isSelected(int p_148131_1_) {
        if (p_148131_1_ == this.field_148197_o) {
            return true;
        }
        return false;
    }

    public int func_148193_k() {
        return this.field_148197_o;
    }

    public void func_148195_a(ServerList p_148195_1_) {
        this.field_148198_l.clear();
        int var2 = 0;
        while (var2 < p_148195_1_.countServers()) {
            this.field_148198_l.add(new ServerListEntryNormal(this.field_148200_k, p_148195_1_.getServerData(var2)));
            ++var2;
        }
    }

    public void func_148194_a(List p_148194_1_) {
        this.field_148199_m.clear();
        for (LanServerDetector.LanServer var3 : p_148194_1_) {
            this.field_148199_m.add(new ServerListEntryLanDetected(this.field_148200_k, var3));
        }
    }

    @Override
    protected int func_148137_d() {
        return super.func_148137_d() + 30;
    }

    @Override
    public int func_148139_c() {
        return super.func_148139_c() + 85;
    }
}

