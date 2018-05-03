/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.authlib.exceptions.InvalidCredentialsException
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  io.netty.util.concurrent.Future
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.net.Proxy;
import java.security.PublicKey;
import java.util.UUID;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient
implements INetHandlerLoginClient {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft field_147394_b;
    private final GuiScreen field_147395_c;
    private final NetworkManager field_147393_d;
    private static final String __OBFID = "CL_00000876";

    public NetHandlerLoginClient(NetworkManager p_i45059_1_, Minecraft p_i45059_2_, GuiScreen p_i45059_3_) {
        this.field_147393_d = p_i45059_1_;
        this.field_147394_b = p_i45059_2_;
        this.field_147395_c = p_i45059_3_;
    }

    @Override
    public void handleEncryptionRequest(S01PacketEncryptionRequest p_147389_1_) {
        final SecretKey var2 = CryptManager.createNewSharedKey();
        String var3 = p_147389_1_.func_149609_c();
        PublicKey var4 = p_147389_1_.func_149608_d();
        String var5 = new BigInteger(CryptManager.getServerIdHash(var3, var4, var2)).toString(16);
        try {
            this.func_147391_c().joinServer(this.field_147394_b.getSession().func_148256_e(), this.field_147394_b.getSession().getToken(), var5);
        }
        catch (AuthenticationUnavailableException var7) {
            this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])));
            return;
        }
        catch (InvalidCredentialsException var8) {
            this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])));
            return;
        }
        catch (AuthenticationException var9) {
            this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", var9.getMessage()));
            return;
        }
        this.field_147393_d.scheduleOutboundPacket(new C01PacketEncryptionResponse(var2, var4, p_147389_1_.func_149607_e()), new GenericFutureListener(){
            private static final String __OBFID = "CL_00000877";

            public void operationComplete(Future p_operationComplete_1_) {
                NetHandlerLoginClient.this.field_147393_d.enableEncryption(var2);
            }
        });
    }

    private MinecraftSessionService func_147391_c() {
        return new YggdrasilAuthenticationService(this.field_147394_b.getProxy(), UUID.randomUUID().toString()).createMinecraftSessionService();
    }

    @Override
    public void handleLoginSuccess(S02PacketLoginSuccess p_147390_1_) {
        this.field_147393_d.setConnectionState(EnumConnectionState.PLAY);
    }

    @Override
    public void onDisconnect(IChatComponent p_147231_1_) {
        this.field_147394_b.displayGuiScreen(new GuiDisconnected(this.field_147395_c, "connect.failed", p_147231_1_));
    }

    @Override
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {
        logger.debug("Switching protocol from " + (Object)((Object)p_147232_1_) + " to " + (Object)((Object)p_147232_2_));
        if (p_147232_2_ == EnumConnectionState.PLAY) {
            this.field_147393_d.setNetHandler(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d));
        }
    }

    @Override
    public void onNetworkTick() {
    }

    @Override
    public void handleDisconnect(S00PacketDisconnect p_147388_1_) {
        this.field_147393_d.closeChannel(p_147388_1_.func_149603_c());
    }

}

