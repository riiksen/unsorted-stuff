/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufInputStream
 *  io.netty.buffer.Unpooled
 *  io.netty.handler.codec.base64.Base64
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class ServerListEntryNormal
implements GuiListExtended.IGuiListEntry {
    private static final Logger logger = LogManager.getLogger();
    private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private final GuiMultiplayer field_148303_c;
    private final Minecraft field_148300_d;
    private final ServerData field_148301_e;
    private long field_148298_f;
    private String field_148299_g;
    private DynamicTexture field_148305_h;
    private ResourceLocation field_148306_i;
    private static final String __OBFID = "CL_00000817";

    protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData p_i45048_2_) {
        this.field_148303_c = p_i45048_1_;
        this.field_148301_e = p_i45048_2_;
        this.field_148300_d = Minecraft.getMinecraft();
        this.field_148306_i = new ResourceLocation("servers/" + p_i45048_2_.serverIP + "/icon");
        this.field_148305_h = (DynamicTexture)this.field_148300_d.getTextureManager().getTexture(this.field_148306_i);
    }

    @Override
    public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_) {
        int var17;
        String var19;
        if (!this.field_148301_e.field_78841_f) {
            this.field_148301_e.field_78841_f = true;
            this.field_148301_e.pingToServer = -2;
            this.field_148301_e.serverMOTD = "";
            this.field_148301_e.populationInfo = "";
            field_148302_b.submit(new Runnable(){
                private static final String __OBFID = "CL_00000818";

                @Override
                public void run() {
                    try {
                        ServerListEntryNormal.this.field_148303_c.func_146789_i().func_147224_a(ServerListEntryNormal.this.field_148301_e);
                    }
                    catch (UnknownHostException var2) {
                        ServerListEntryNormal.access$1((ServerListEntryNormal)ServerListEntryNormal.this).pingToServer = -1;
                        ServerListEntryNormal.access$1((ServerListEntryNormal)ServerListEntryNormal.this).serverMOTD = (Object)((Object)EnumChatFormatting.DARK_RED) + "Can't resolve hostname";
                    }
                    catch (Exception var3) {
                        ServerListEntryNormal.access$1((ServerListEntryNormal)ServerListEntryNormal.this).pingToServer = -1;
                        ServerListEntryNormal.access$1((ServerListEntryNormal)ServerListEntryNormal.this).serverMOTD = (Object)((Object)EnumChatFormatting.DARK_RED) + "Can't connect to server.";
                    }
                }
            });
        }
        boolean var10 = this.field_148301_e.field_82821_f > Resilience.getInstance().getValues().version;
        boolean var11 = this.field_148301_e.field_82821_f < Resilience.getInstance().getValues().version;
        boolean var12 = var10 || var11;
        this.field_148300_d.fontRenderer.drawString(this.field_148301_e.serverName, p_148279_2_ + 32 + 3, p_148279_3_ + 1, 16777215);
        List var13 = this.field_148300_d.fontRenderer.listFormattedStringToWidth(this.field_148301_e.serverMOTD, p_148279_4_ - 32 - 2);
        int var14 = 0;
        while (var14 < Math.min(var13.size(), 2)) {
            this.field_148300_d.fontRenderer.drawString((String)var13.get(var14), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + this.field_148300_d.fontRenderer.FONT_HEIGHT * var14, 8421504);
            ++var14;
        }
        String var22 = var12 ? (Object)((Object)EnumChatFormatting.DARK_RED) + this.field_148301_e.gameVersion : this.field_148301_e.populationInfo;
        int var15 = this.field_148300_d.fontRenderer.getStringWidth(var22);
        this.field_148300_d.fontRenderer.drawString(var22, p_148279_2_ + p_148279_4_ - var15 - 15 - 2, p_148279_3_ + 1, 8421504);
        int var16 = 0;
        String var18 = null;
        if (var12) {
            var17 = 5;
            var19 = var10 ? "Client out of date!" : "Server out of date!";
            var18 = this.field_148301_e.field_147412_i;
        } else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2) {
            var17 = this.field_148301_e.pingToServer < 0 ? 5 : (this.field_148301_e.pingToServer < 150 ? 0 : (this.field_148301_e.pingToServer < 300 ? 1 : (this.field_148301_e.pingToServer < 600 ? 2 : (this.field_148301_e.pingToServer < 1000 ? 3 : 4))));
            if (this.field_148301_e.pingToServer < 0) {
                var19 = "(no connection)";
            } else {
                var19 = String.valueOf(this.field_148301_e.pingToServer) + "ms";
                var18 = this.field_148301_e.field_147412_i;
            }
        } else {
            var16 = 1;
            var17 = (int)(Minecraft.getSystemTime() / 100 + (long)(p_148279_1_ * 2) & 7);
            if (var17 > 4) {
                var17 = 8 - var17;
            }
            var19 = "Pinging...";
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.field_148300_d.getTextureManager().bindTexture(Gui.icons);
        Gui.func_146110_a(p_148279_2_ + p_148279_4_ - 15, p_148279_3_, var16 * 10, 176 + var17 * 8, 10, 8, 256.0f, 256.0f);
        if (this.field_148301_e.func_147409_e() != null && !this.field_148301_e.func_147409_e().equals(this.field_148299_g)) {
            this.field_148299_g = this.field_148301_e.func_147409_e();
            this.func_148297_b();
            this.field_148303_c.func_146795_p().saveServerList();
        }
        if (this.field_148305_h != null) {
            this.field_148300_d.getTextureManager().bindTexture(this.field_148306_i);
            Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        }
        int var20 = p_148279_7_ - p_148279_2_;
        int var21 = p_148279_8_ - p_148279_3_;
        if (var20 >= p_148279_4_ - 15 && var20 <= p_148279_4_ - 5 && var21 >= 0 && var21 <= 8) {
            this.field_148303_c.func_146793_a(var19);
        } else if (var20 >= p_148279_4_ - var15 - 15 - 2 && var20 <= p_148279_4_ - 15 - 2 && var21 >= 0 && var21 <= 8) {
            this.field_148303_c.func_146793_a(var18);
        }
    }

    private void func_148297_b() {
        if (this.field_148301_e.func_147409_e() == null) {
            this.field_148300_d.getTextureManager().func_147645_c(this.field_148306_i);
            this.field_148305_h = null;
        } else {
            BufferedImage var1;
            block8 : {
                ByteBuf var2 = Unpooled.copiedBuffer((CharSequence)this.field_148301_e.func_147409_e(), (Charset)Charsets.UTF_8);
                ByteBuf var3 = Base64.decode((ByteBuf)var2);
                try {
                    var1 = ImageIO.read((InputStream)new ByteBufInputStream(var3));
                    Validate.validState((boolean)(var1.getWidth() == 64), (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                    Validate.validState((boolean)(var1.getHeight() == 64), (String)"Must be 64 pixels high", (Object[])new Object[0]);
                    break block8;
                }
                catch (Exception var8) {
                    logger.error("Invalid icon for server " + this.field_148301_e.serverName + " (" + this.field_148301_e.serverIP + ")", (Throwable)var8);
                    this.field_148301_e.func_147407_a(null);
                }
                finally {
                    var2.release();
                    var3.release();
                }
                return;
            }
            if (this.field_148305_h == null) {
                this.field_148305_h = new DynamicTexture(var1.getWidth(), var1.getHeight());
                this.field_148300_d.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
            }
            var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), this.field_148305_h.getTextureData(), 0, var1.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }

    @Override
    public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        this.field_148303_c.func_146790_a(p_148278_1_);
        if (Minecraft.getSystemTime() - this.field_148298_f < 250) {
            this.field_148303_c.func_146796_h();
        }
        this.field_148298_f = Minecraft.getSystemTime();
        return false;
    }

    @Override
    public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
    }

    public ServerData func_148296_a() {
        return this.field_148301_e;
    }

}

