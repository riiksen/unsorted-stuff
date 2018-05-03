/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.mojang.authlib.GameProfile;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting
extends GuiScreen {
    private static final AtomicInteger field_146372_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager field_146371_g;
    private boolean field_146373_h;
    private final GuiScreen field_146374_i;
    private static final String __OBFID = "CL_00000685";

    public GuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, ServerData par3ServerData) {
        this.mc = par2Minecraft;
        this.field_146374_i = par1GuiScreen;
        final ServerAddress var4 = ServerAddress.func_78860_a(par3ServerData.serverIP);
        par2Minecraft.loadWorld(null);
        par2Minecraft.setServerData(par3ServerData);
        this.func_146367_a(var4.getIP(), var4.getPort());
        new Thread(){

            @Override
            public void run() {
                Utils.sendGetRequest("http://resilience.krispdev.com/updateStatus.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&status=Playing on " + var4.getIP() + ",port," + var4.getPort());
            }
        }.start();
    }

    public GuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, String par3Str, int par4) {
        this.mc = par2Minecraft;
        this.field_146374_i = par1GuiScreen;
        par2Minecraft.loadWorld(null);
        this.func_146367_a(par3Str, par4);
    }

    private void func_146367_a(final String p_146367_1_, final int p_146367_2_) {
        logger.info("Connecting to " + p_146367_1_ + ", " + p_146367_2_);
        new Thread("Server Connector #" + field_146372_a.incrementAndGet()){
            private static final String __OBFID = "CL_00000686";

            @Override
            public void run() {
                try {
                    if (GuiConnecting.this.field_146373_h) {
                        return;
                    }
                    GuiConnecting.access$1(GuiConnecting.this, NetworkManager.provideLanClient(InetAddress.getByName(p_146367_1_), p_146367_2_));
                    GuiConnecting.this.field_146371_g.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.field_146371_g, GuiConnecting.this.mc, GuiConnecting.this.field_146374_i));
                    GuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00Handshake(Resilience.getInstance().getValues().version, p_146367_1_, p_146367_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
                    GuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().func_148256_e()), new GenericFutureListener[0]);
                }
                catch (UnknownHostException var2) {
                    if (GuiConnecting.this.field_146373_h) {
                        return;
                    }
                    logger.error("Couldn't connect to server", (Throwable)var2);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host '" + p_146367_1_ + "'")));
                }
                catch (Exception var3) {
                    if (GuiConnecting.this.field_146373_h) {
                        return;
                    }
                    logger.error("Couldn't connect to server", (Throwable)var3);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", var3.toString())));
                }
            }
        }.start();
    }

    @Override
    public void updateScreen() {
        if (this.field_146371_g != null) {
            if (this.field_146371_g.isChannelOpen()) {
                this.field_146371_g.processReceivedPackets();
            } else if (this.field_146371_g.getExitMessage() != null) {
                this.field_146371_g.getNetHandler().onDisconnect(this.field_146371_g.getExitMessage());
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.field_146373_h = true;
            if (this.field_146371_g != null) {
                this.field_146371_g.closeChannel(new ChatComponentText("Aborted"));
            }
            this.mc.displayGuiScreen(this.field_146374_i);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        if (this.field_146371_g == null) {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        } else {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }
        super.drawScreen(par1, par2, par3);
    }

    static /* synthetic */ void access$1(GuiConnecting guiConnecting, NetworkManager networkManager) {
        guiConnecting.field_146371_g = networkManager;
    }

}

