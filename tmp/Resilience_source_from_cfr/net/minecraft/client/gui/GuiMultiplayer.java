/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenAddServer;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer
extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private final OldServerPinger field_146797_f = new OldServerPinger();
    private GuiScreen field_146798_g;
    private ServerSelectionList field_146803_h;
    private ServerList field_146804_i;
    private GuiButton field_146810_r;
    private GuiButton field_146809_s;
    private GuiButton field_146808_t;
    private boolean field_146807_u;
    private boolean field_146806_v;
    private boolean field_146805_w;
    private boolean field_146813_x;
    private String field_146812_y;
    private ServerData field_146811_z;
    private LanServerDetector.LanServerList field_146799_A;
    private LanServerDetector.ThreadLanServerFind field_146800_B;
    private boolean field_146801_C;
    private static final String __OBFID = "CL_00000814";

    public GuiMultiplayer(GuiScreen par1GuiScreen) {
        this.field_146798_g = par1GuiScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        if (!this.field_146801_C) {
            this.field_146801_C = true;
            this.field_146804_i = new ServerList(this.mc);
            this.field_146804_i.loadServerList();
            this.field_146799_A = new LanServerDetector.LanServerList();
            try {
                this.field_146800_B = new LanServerDetector.ThreadLanServerFind(this.field_146799_A);
                this.field_146800_B.start();
            }
            catch (Exception var2) {
                logger.warn("Unable to start LAN server detection: " + var2.getMessage());
            }
            this.field_146803_h = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
            this.field_146803_h.func_148195_a(this.field_146804_i);
        } else {
            this.field_146803_h.func_148122_a(this.width, this.height, 32, this.height - 64);
        }
        this.func_146794_g();
    }

    public void func_146794_g() {
        this.field_146810_r = new GuiButton(7, this.width / 2 - 154, this.height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0]));
        this.buttonList.add(this.field_146810_r);
        this.field_146808_t = new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0]));
        this.buttonList.add(this.field_146808_t);
        this.field_146809_s = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select", new Object[0]));
        this.buttonList.add(this.field_146809_s);
        this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_146790_a(this.field_146803_h.func_148193_k());
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.field_146799_A.getWasUpdated()) {
            List var1 = this.field_146799_A.getLanServers();
            this.field_146799_A.setWasNotUpdated();
            this.field_146803_h.func_148194_a(var1);
        }
        this.field_146797_f.func_147223_a();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
        if (this.field_146800_B != null) {
            this.field_146800_B.interrupt();
            this.field_146800_B = null;
        }
        this.field_146797_f.func_147226_b();
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            GuiListExtended.IGuiListEntry var2;
            GuiListExtended.IGuiListEntry iGuiListEntry = var2 = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
            if (p_146284_1_.id == 2 && var2 instanceof ServerListEntryNormal) {
                String var9 = ((ServerListEntryNormal)var2).func_148296_a().serverName;
                if (var9 != null) {
                    this.field_146807_u = true;
                    String var4 = I18n.format("selectServer.deleteQuestion", new Object[0]);
                    String var5 = "'" + var9 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
                    String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
                    String var7 = I18n.format("gui.cancel", new Object[0]);
                    GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.field_146803_h.func_148193_k());
                    this.mc.displayGuiScreen(var8);
                }
            } else if (p_146284_1_.id == 1) {
                this.func_146796_h();
            } else if (p_146284_1_.id == 4) {
                this.field_146813_x = true;
                this.field_146811_z = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "");
                this.mc.displayGuiScreen(new GuiScreenServerList(this, this.field_146811_z));
            } else if (p_146284_1_.id == 3) {
                this.field_146806_v = true;
                this.field_146811_z = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "");
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.field_146811_z));
            } else if (p_146284_1_.id == 7 && var2 instanceof ServerListEntryNormal) {
                this.field_146805_w = true;
                ServerData var3 = ((ServerListEntryNormal)var2).func_148296_a();
                this.field_146811_z = new ServerData(var3.serverName, var3.serverIP);
                this.field_146811_z.setHideAddress(var3.isHidingAddress());
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.field_146811_z));
            } else if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen(this.field_146798_g);
            } else if (p_146284_1_.id == 8) {
                this.func_146792_q();
            }
        }
    }

    private void func_146792_q() {
        this.mc.displayGuiScreen(new GuiMultiplayer(this.field_146798_g));
    }

    @Override
    public void confirmClicked(boolean par1, int par2) {
        GuiListExtended.IGuiListEntry var3;
        GuiListExtended.IGuiListEntry iGuiListEntry = var3 = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
        if (this.field_146807_u) {
            this.field_146807_u = false;
            if (par1 && var3 instanceof ServerListEntryNormal) {
                this.field_146804_i.removeServerData(this.field_146803_h.func_148193_k());
                this.field_146804_i.saveServerList();
                this.field_146803_h.func_148192_c(-1);
                this.field_146803_h.func_148195_a(this.field_146804_i);
            }
            this.mc.displayGuiScreen(this);
        } else if (this.field_146813_x) {
            this.field_146813_x = false;
            if (par1) {
                this.func_146791_a(this.field_146811_z);
            } else {
                this.mc.displayGuiScreen(this);
            }
        } else if (this.field_146806_v) {
            this.field_146806_v = false;
            if (par1) {
                this.field_146804_i.addServerData(this.field_146811_z);
                this.field_146804_i.saveServerList();
                this.field_146803_h.func_148192_c(-1);
                this.field_146803_h.func_148195_a(this.field_146804_i);
            }
            this.mc.displayGuiScreen(this);
        } else if (this.field_146805_w) {
            this.field_146805_w = false;
            if (par1 && var3 instanceof ServerListEntryNormal) {
                ServerData var4 = ((ServerListEntryNormal)var3).func_148296_a();
                var4.serverName = this.field_146811_z.serverName;
                var4.serverIP = this.field_146811_z.serverIP;
                var4.setHideAddress(this.field_146811_z.isHidingAddress());
                this.field_146804_i.saveServerList();
                this.field_146803_h.func_148195_a(this.field_146804_i);
            }
            this.mc.displayGuiScreen(this);
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        GuiListExtended.IGuiListEntry var4;
        int var3 = this.field_146803_h.func_148193_k();
        GuiListExtended.IGuiListEntry iGuiListEntry = var4 = var3 < 0 ? null : this.field_146803_h.func_148180_b(var3);
        if (par2 == 63) {
            this.func_146792_q();
        } else if (var3 >= 0) {
            if (par2 == 200) {
                if (GuiMultiplayer.isShiftKeyDown()) {
                    if (var3 > 0 && var4 instanceof ServerListEntryNormal) {
                        this.field_146804_i.swapServers(var3, var3 - 1);
                        this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                        this.field_146803_h.func_148145_f(- this.field_146803_h.func_148146_j());
                        this.field_146803_h.func_148195_a(this.field_146804_i);
                    }
                } else if (var3 > 0) {
                    this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                    this.field_146803_h.func_148145_f(- this.field_146803_h.func_148146_j());
                    if (this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.field_146803_h.func_148193_k() > 0) {
                            this.func_146790_a(this.field_146803_h.getSize() - 1);
                            this.field_146803_h.func_148145_f(- this.field_146803_h.func_148146_j());
                        } else {
                            this.func_146790_a(-1);
                        }
                    }
                } else {
                    this.func_146790_a(-1);
                }
            } else if (par2 == 208) {
                if (GuiMultiplayer.isShiftKeyDown()) {
                    if (var3 < this.field_146804_i.countServers() - 1) {
                        this.field_146804_i.swapServers(var3, var3 + 1);
                        this.func_146790_a(var3 + 1);
                        this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                        this.field_146803_h.func_148195_a(this.field_146804_i);
                    }
                } else if (var3 < this.field_146803_h.getSize()) {
                    this.func_146790_a(this.field_146803_h.func_148193_k() + 1);
                    this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                    if (this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.field_146803_h.func_148193_k() < this.field_146803_h.getSize() - 1) {
                            this.func_146790_a(this.field_146803_h.getSize() + 1);
                            this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                        } else {
                            this.func_146790_a(-1);
                        }
                    }
                } else {
                    this.func_146790_a(-1);
                }
            } else if (par2 != 28 && par2 != 156) {
                super.keyTyped(par1, par2);
            } else {
                this.actionPerformed((GuiButton)this.buttonList.get(2));
            }
        } else {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.field_146812_y = null;
        this.drawDefaultBackground();
        this.field_146803_h.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
        if (this.field_146812_y != null) {
            this.func_146283_a(Lists.newArrayList((Iterable)Splitter.on((String)"\n").split((CharSequence)this.field_146812_y)), par1, par2);
        }
    }

    public void func_146796_h() {
        GuiListExtended.IGuiListEntry var1;
        GuiListExtended.IGuiListEntry iGuiListEntry = var1 = this.field_146803_h.func_148193_k() < 0 ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
        if (var1 instanceof ServerListEntryNormal) {
            this.func_146791_a(((ServerListEntryNormal)var1).func_148296_a());
        } else if (var1 instanceof ServerListEntryLanDetected) {
            LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected)var1).func_148289_a();
            this.func_146791_a(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
        }
    }

    private void func_146791_a(ServerData p_146791_1_) {
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, p_146791_1_));
    }

    public void func_146790_a(int p_146790_1_) {
        this.field_146803_h.func_148192_c(p_146790_1_);
        GuiListExtended.IGuiListEntry var2 = p_146790_1_ < 0 ? null : this.field_146803_h.func_148180_b(p_146790_1_);
        this.field_146809_s.enabled = false;
        this.field_146810_r.enabled = false;
        this.field_146808_t.enabled = false;
        if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
            this.field_146809_s.enabled = true;
            if (var2 instanceof ServerListEntryNormal) {
                this.field_146810_r.enabled = true;
                this.field_146808_t.enabled = true;
            }
        }
    }

    public OldServerPinger func_146789_i() {
        return this.field_146797_f;
    }

    public void func_146793_a(String p_146793_1_) {
        this.field_146812_y = p_146793_1_;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_146803_h.func_148179_a(par1, par2, par3);
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        this.field_146803_h.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    public ServerList func_146795_p() {
        return this.field_146804_i;
    }
}

