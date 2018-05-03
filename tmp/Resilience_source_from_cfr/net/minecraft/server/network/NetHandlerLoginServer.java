/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer
implements INetHandlerLoginServer {
    private static final AtomicInteger field_147331_b = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random field_147329_d = new Random();
    private final byte[] field_147330_e = new byte[4];
    private final MinecraftServer field_147327_f;
    public final NetworkManager field_147333_a;
    private LoginState field_147328_g = LoginState.HELLO;
    private int field_147336_h;
    private GameProfile field_147337_i;
    private String field_147334_j = "";
    private SecretKey field_147335_k;
    private static final String __OBFID = "CL_00001458";

    public NetHandlerLoginServer(MinecraftServer p_i45298_1_, NetworkManager p_i45298_2_) {
        this.field_147327_f = p_i45298_1_;
        this.field_147333_a = p_i45298_2_;
        field_147329_d.nextBytes(this.field_147330_e);
    }

    @Override
    public void onNetworkTick() {
        if (this.field_147328_g == LoginState.READY_TO_ACCEPT) {
            this.func_147326_c();
        }
        if (this.field_147336_h++ == 600) {
            this.func_147322_a("Took too long to log in");
        }
    }

    public void func_147322_a(String p_147322_1_) {
        try {
            logger.info("Disconnecting " + this.func_147317_d() + ": " + p_147322_1_);
            ChatComponentText var2 = new ChatComponentText(p_147322_1_);
            this.field_147333_a.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
            this.field_147333_a.closeChannel(var2);
        }
        catch (Exception var3) {
            logger.error("Error whilst disconnecting player", (Throwable)var3);
        }
    }

    public void func_147326_c() {
        String var2;
        if (!this.field_147337_i.isComplete()) {
            UUID var1 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.field_147337_i.getName()).getBytes(Charsets.UTF_8));
            this.field_147337_i = new GameProfile(var1.toString().replaceAll("-", ""), this.field_147337_i.getName());
        }
        if ((var2 = this.field_147327_f.getConfigurationManager().func_148542_a(this.field_147333_a.getSocketAddress(), this.field_147337_i)) != null) {
            this.func_147322_a(var2);
        } else {
            this.field_147328_g = LoginState.ACCEPTED;
            this.field_147333_a.scheduleOutboundPacket(new S02PacketLoginSuccess(this.field_147337_i), new GenericFutureListener[0]);
            this.field_147327_f.getConfigurationManager().initializeConnectionToPlayer(this.field_147333_a, this.field_147327_f.getConfigurationManager().func_148545_a(this.field_147337_i));
        }
    }

    @Override
    public void onDisconnect(IChatComponent p_147231_1_) {
        logger.info(String.valueOf(this.func_147317_d()) + " lost connection: " + p_147231_1_.getUnformattedText());
    }

    public String func_147317_d() {
        return this.field_147337_i != null ? String.valueOf(this.field_147337_i.toString()) + " (" + this.field_147333_a.getSocketAddress().toString() + ")" : String.valueOf(this.field_147333_a.getSocketAddress());
    }

    @Override
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {
        Validate.validState((boolean)(this.field_147328_g == LoginState.ACCEPTED || this.field_147328_g == LoginState.HELLO), (String)"Unexpected change in protocol", (Object[])new Object[0]);
        Validate.validState((boolean)(p_147232_2_ == EnumConnectionState.PLAY || p_147232_2_ == EnumConnectionState.LOGIN), (String)("Unexpected protocol " + (Object)((Object)p_147232_2_)), (Object[])new Object[0]);
    }

    @Override
    public void processLoginStart(C00PacketLoginStart p_147316_1_) {
        Validate.validState((boolean)(this.field_147328_g == LoginState.HELLO), (String)"Unexpected hello packet", (Object[])new Object[0]);
        this.field_147337_i = p_147316_1_.func_149304_c();
        if (this.field_147327_f.isServerInOnlineMode() && !this.field_147333_a.isLocalChannel()) {
            this.field_147328_g = LoginState.KEY;
            this.field_147333_a.scheduleOutboundPacket(new S01PacketEncryptionRequest(this.field_147334_j, this.field_147327_f.getKeyPair().getPublic(), this.field_147330_e), new GenericFutureListener[0]);
        } else {
            this.field_147328_g = LoginState.READY_TO_ACCEPT;
        }
    }

    @Override
    public void processEncryptionResponse(C01PacketEncryptionResponse p_147315_1_) {
        Validate.validState((boolean)(this.field_147328_g == LoginState.KEY), (String)"Unexpected key packet", (Object[])new Object[0]);
        PrivateKey var2 = this.field_147327_f.getKeyPair().getPrivate();
        if (!Arrays.equals(this.field_147330_e, p_147315_1_.func_149299_b(var2))) {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.field_147335_k = p_147315_1_.func_149300_a(var2);
        this.field_147328_g = LoginState.AUTHENTICATING;
        this.field_147333_a.enableEncryption(this.field_147335_k);
        new Thread("User Authenticator #" + field_147331_b.incrementAndGet()){
            private static final String __OBFID = "CL_00001459";

            @Override
            public void run() {
                try {
                    String var1 = new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.this.field_147334_j, NetHandlerLoginServer.this.field_147327_f.getKeyPair().getPublic(), NetHandlerLoginServer.this.field_147335_k)).toString(16);
                    NetHandlerLoginServer.access$4(NetHandlerLoginServer.this, NetHandlerLoginServer.this.field_147327_f.func_147130_as().hasJoinedServer(new GameProfile(null, NetHandlerLoginServer.this.field_147337_i.getName()), var1));
                    if (NetHandlerLoginServer.this.field_147337_i != null) {
                        logger.info("UUID of player " + NetHandlerLoginServer.this.field_147337_i.getName() + " is " + NetHandlerLoginServer.this.field_147337_i.getId());
                        NetHandlerLoginServer.access$6(NetHandlerLoginServer.this, LoginState.READY_TO_ACCEPT);
                    } else {
                        NetHandlerLoginServer.this.func_147322_a("Failed to verify username!");
                        logger.error("Username '" + NetHandlerLoginServer.this.field_147337_i.getName() + "' tried to join with an invalid session");
                    }
                }
                catch (AuthenticationUnavailableException var2) {
                    NetHandlerLoginServer.this.func_147322_a("Authentication servers are down. Please try again later, sorry!");
                    logger.error("Couldn't verify username because servers are unavailable");
                }
            }
        }.start();
    }

    static /* synthetic */ void access$4(NetHandlerLoginServer netHandlerLoginServer, GameProfile gameProfile) {
        netHandlerLoginServer.field_147337_i = gameProfile;
    }

    static /* synthetic */ void access$6(NetHandlerLoginServer netHandlerLoginServer, LoginState loginState) {
        netHandlerLoginServer.field_147328_g = loginState;
    }

    static enum LoginState {
        HELLO("HELLO", 0),
        KEY("KEY", 1),
        AUTHENTICATING("AUTHENTICATING", 2),
        READY_TO_ACCEPT("READY_TO_ACCEPT", 3),
        ACCEPTED("ACCEPTED", 4);
        
        private static final LoginState[] $VALUES;
        private static final String __OBFID = "CL_00001463";

        static {
            $VALUES = new LoginState[]{HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED};
        }

        private LoginState(String p_i45297_1_, int p_i45297_2_, String string2, int n2) {
        }
    }

}

