/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Lists
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.util.concurrent.Future
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.network;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer
implements INetHandlerPlayServer {
    private static final Logger logger = LogManager.getLogger();
    public final NetworkManager netManager;
    private final MinecraftServer serverController;
    public EntityPlayerMP playerEntity;
    private int networkTickCount;
    private int floatingTickCount;
    private boolean field_147366_g;
    private int field_147378_h;
    private long field_147379_i;
    private static Random field_147376_j = new Random();
    private long field_147377_k;
    private int chatSpamThresholdCount;
    private int field_147375_m;
    private IntHashMap field_147372_n = new IntHashMap();
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private boolean hasMoved = true;
    private static final String __OBFID = "CL_00001452";

    public NetHandlerPlayServer(MinecraftServer par1MinecraftServer, NetworkManager par2INetworkManager, EntityPlayerMP par3EntityPlayerMP) {
        this.serverController = par1MinecraftServer;
        this.netManager = par2INetworkManager;
        par2INetworkManager.setNetHandler(this);
        this.playerEntity = par3EntityPlayerMP;
        par3EntityPlayerMP.playerNetServerHandler = this;
    }

    @Override
    public void onNetworkTick() {
        this.field_147366_g = false;
        ++this.networkTickCount;
        this.serverController.theProfiler.startSection("keepAlive");
        if ((long)this.networkTickCount - this.field_147377_k > 40) {
            this.field_147377_k = this.networkTickCount;
            this.field_147379_i = this.func_147363_d();
            this.field_147378_h = (int)this.field_147379_i;
            this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
        }
        if (this.chatSpamThresholdCount > 0) {
            --this.chatSpamThresholdCount;
        }
        if (this.field_147375_m > 0) {
            --this.field_147375_m;
        }
        this.serverController.theProfiler.endStartSection("playerTick");
        this.serverController.theProfiler.endSection();
    }

    public NetworkManager func_147362_b() {
        return this.netManager;
    }

    public void kickPlayerFromServer(String p_147360_1_) {
        final ChatComponentText var2 = new ChatComponentText(p_147360_1_);
        this.netManager.scheduleOutboundPacket(new S40PacketDisconnect(var2), new GenericFutureListener(){
            private static final String __OBFID = "CL_00001453";

            public void operationComplete(Future p_operationComplete_1_) {
                NetHandlerPlayServer.this.netManager.closeChannel(var2);
            }
        });
        this.netManager.disableAutoRead();
    }

    @Override
    public void processInput(C0CPacketInput p_147358_1_) {
        this.playerEntity.setEntityActionState(p_147358_1_.func_149620_c(), p_147358_1_.func_149616_d(), p_147358_1_.func_149618_e(), p_147358_1_.func_149617_f());
    }

    @Override
    public void processPlayer(C03PacketPlayer p_147347_1_) {
        WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        this.field_147366_g = true;
        if (!this.playerEntity.playerConqueredTheEnd) {
            double var3;
            if (!this.hasMoved) {
                var3 = p_147347_1_.func_149467_d() - this.lastPosY;
                if (p_147347_1_.func_149464_c() == this.lastPosX && var3 * var3 < 0.01 && p_147347_1_.func_149472_e() == this.lastPosZ) {
                    this.hasMoved = true;
                }
            }
            if (this.hasMoved) {
                double var21;
                double var23;
                double var13;
                if (this.playerEntity.ridingEntity != null) {
                    float var34 = this.playerEntity.rotationYaw;
                    float var4 = this.playerEntity.rotationPitch;
                    this.playerEntity.ridingEntity.updateRiderPosition();
                    double var5 = this.playerEntity.posX;
                    double var7 = this.playerEntity.posY;
                    double var9 = this.playerEntity.posZ;
                    if (p_147347_1_.func_149463_k()) {
                        var34 = p_147347_1_.func_149462_g();
                        var4 = p_147347_1_.func_149470_h();
                    }
                    this.playerEntity.onGround = p_147347_1_.func_149465_i();
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.ySize = 0.0f;
                    this.playerEntity.setPositionAndRotation(var5, var7, var9, var34, var4);
                    if (this.playerEntity.ridingEntity != null) {
                        this.playerEntity.ridingEntity.updateRiderPosition();
                    }
                    this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                    if (this.hasMoved) {
                        this.lastPosX = this.playerEntity.posX;
                        this.lastPosY = this.playerEntity.posY;
                        this.lastPosZ = this.playerEntity.posZ;
                    }
                    var2.updateEntity(this.playerEntity);
                    return;
                }
                if (this.playerEntity.isPlayerSleeping()) {
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    var2.updateEntity(this.playerEntity);
                    return;
                }
                var3 = this.playerEntity.posY;
                this.lastPosX = this.playerEntity.posX;
                this.lastPosY = this.playerEntity.posY;
                this.lastPosZ = this.playerEntity.posZ;
                double var5 = this.playerEntity.posX;
                double var7 = this.playerEntity.posY;
                double var9 = this.playerEntity.posZ;
                float var11 = this.playerEntity.rotationYaw;
                float var12 = this.playerEntity.rotationPitch;
                if (p_147347_1_.func_149466_j() && p_147347_1_.func_149467_d() == -999.0 && p_147347_1_.func_149471_f() == -999.0) {
                    p_147347_1_.func_149469_a(false);
                }
                if (p_147347_1_.func_149466_j()) {
                    var5 = p_147347_1_.func_149464_c();
                    var7 = p_147347_1_.func_149467_d();
                    var9 = p_147347_1_.func_149472_e();
                    var13 = p_147347_1_.func_149471_f() - p_147347_1_.func_149467_d();
                    if (!(this.playerEntity.isPlayerSleeping() || var13 <= 1.65 && var13 >= 0.1)) {
                        this.kickPlayerFromServer("Illegal stance");
                        logger.warn(String.valueOf(this.playerEntity.getCommandSenderName()) + " had an illegal stance: " + var13);
                        return;
                    }
                    if (Math.abs(p_147347_1_.func_149464_c()) > 3.2E7 || Math.abs(p_147347_1_.func_149472_e()) > 3.2E7) {
                        this.kickPlayerFromServer("Illegal position");
                        return;
                    }
                }
                if (p_147347_1_.func_149463_k()) {
                    var11 = p_147347_1_.func_149462_g();
                    var12 = p_147347_1_.func_149470_h();
                }
                this.playerEntity.onUpdateEntity();
                this.playerEntity.ySize = 0.0f;
                this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var11, var12);
                if (!this.hasMoved) {
                    return;
                }
                var13 = var5 - this.playerEntity.posX;
                double var15 = var7 - this.playerEntity.posY;
                double var17 = var9 - this.playerEntity.posZ;
                double var19 = Math.min(Math.abs(var13), Math.abs(this.playerEntity.motionX));
                double var25 = var19 * var19 + (var21 = Math.min(Math.abs(var15), Math.abs(this.playerEntity.motionY))) * var21 + (var23 = Math.min(Math.abs(var17), Math.abs(this.playerEntity.motionZ))) * var23;
                if (!(var25 <= 100.0 || this.serverController.isSinglePlayer() && this.serverController.getServerOwner().equals(this.playerEntity.getCommandSenderName()))) {
                    logger.warn(String.valueOf(this.playerEntity.getCommandSenderName()) + " moved too quickly! " + var13 + "," + var15 + "," + var17 + " (" + var19 + ", " + var21 + ", " + var23 + ")");
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    return;
                }
                float var27 = 0.0625f;
                boolean var28 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
                if (this.playerEntity.onGround && !p_147347_1_.func_149465_i() && var15 > 0.0) {
                    this.playerEntity.jump();
                }
                this.playerEntity.moveEntity(var13, var15, var17);
                this.playerEntity.onGround = p_147347_1_.func_149465_i();
                this.playerEntity.addMovementStat(var13, var15, var17);
                double var29 = var15;
                var13 = var5 - this.playerEntity.posX;
                var15 = var7 - this.playerEntity.posY;
                if (var15 > -0.5 || var15 < 0.5) {
                    var15 = 0.0;
                }
                var17 = var9 - this.playerEntity.posZ;
                var25 = var13 * var13 + var15 * var15 + var17 * var17;
                boolean var31 = false;
                if (var25 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                    var31 = true;
                    logger.warn(String.valueOf(this.playerEntity.getCommandSenderName()) + " moved wrongly!");
                }
                this.playerEntity.setPositionAndRotation(var5, var7, var9, var11, var12);
                boolean var32 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
                if (var28 && (var31 || !var32) && !this.playerEntity.isPlayerSleeping()) {
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var11, var12);
                    return;
                }
                AxisAlignedBB var33 = this.playerEntity.boundingBox.copy().expand(var27, var27, var27).addCoord(0.0, -0.55, 0.0);
                if (!(this.serverController.isFlightAllowed() || this.playerEntity.theItemInWorldManager.isCreative() || var2.checkBlockCollision(var33))) {
                    if (var29 >= -0.03125) {
                        ++this.floatingTickCount;
                        if (this.floatingTickCount > 80) {
                            logger.warn(String.valueOf(this.playerEntity.getCommandSenderName()) + " was kicked for floating too long!");
                            this.kickPlayerFromServer("Flying is not enabled on this server");
                            return;
                        }
                    }
                } else {
                    this.floatingTickCount = 0;
                }
                this.playerEntity.onGround = p_147347_1_.func_149465_i();
                this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                this.playerEntity.handleFalling(this.playerEntity.posY - var3, p_147347_1_.func_149465_i());
            } else if (this.networkTickCount % 20 == 0) {
                this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
            }
        }
    }

    public void setPlayerLocation(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_) {
        this.hasMoved = false;
        this.lastPosX = p_147364_1_;
        this.lastPosY = p_147364_3_;
        this.lastPosZ = p_147364_5_;
        this.playerEntity.setPositionAndRotation(p_147364_1_, p_147364_3_, p_147364_5_, p_147364_7_, p_147364_8_);
        this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(p_147364_1_, p_147364_3_ + 1.6200000047683716, p_147364_5_, p_147364_7_, p_147364_8_, false));
    }

    @Override
    public void processPlayerDigging(C07PacketPlayerDigging p_147345_1_) {
        WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        this.playerEntity.func_143004_u();
        if (p_147345_1_.func_149506_g() == 4) {
            this.playerEntity.dropOneItem(false);
        } else if (p_147345_1_.func_149506_g() == 3) {
            this.playerEntity.dropOneItem(true);
        } else if (p_147345_1_.func_149506_g() == 5) {
            this.playerEntity.stopUsingItem();
        } else {
            boolean var3 = false;
            if (p_147345_1_.func_149506_g() == 0) {
                var3 = true;
            }
            if (p_147345_1_.func_149506_g() == 1) {
                var3 = true;
            }
            if (p_147345_1_.func_149506_g() == 2) {
                var3 = true;
            }
            int var4 = p_147345_1_.func_149505_c();
            int var5 = p_147345_1_.func_149503_d();
            int var6 = p_147345_1_.func_149502_e();
            if (var3) {
                double var7 = this.playerEntity.posX - ((double)var4 + 0.5);
                double var9 = this.playerEntity.posY - ((double)var5 + 0.5) + 1.5;
                double var11 = this.playerEntity.posZ - ((double)var6 + 0.5);
                double var13 = var7 * var7 + var9 * var9 + var11 * var11;
                if (var13 > 36.0) {
                    return;
                }
                if (var5 >= this.serverController.getBuildLimit()) {
                    return;
                }
            }
            if (p_147345_1_.func_149506_g() == 0) {
                if (!this.serverController.isBlockProtected(var2, var4, var5, var6, this.playerEntity)) {
                    this.playerEntity.theItemInWorldManager.onBlockClicked(var4, var5, var6, p_147345_1_.func_149501_f());
                } else {
                    this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            } else if (p_147345_1_.func_149506_g() == 2) {
                this.playerEntity.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);
                if (var2.getBlock(var4, var5, var6).getMaterial() != Material.air) {
                    this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            } else if (p_147345_1_.func_149506_g() == 1) {
                this.playerEntity.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);
                if (var2.getBlock(var4, var5, var6).getMaterial() != Material.air) {
                    this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
        }
    }

    @Override
    public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement p_147346_1_) {
        WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
        boolean var4 = false;
        int var5 = p_147346_1_.func_149576_c();
        int var6 = p_147346_1_.func_149571_d();
        int var7 = p_147346_1_.func_149570_e();
        int var8 = p_147346_1_.func_149568_f();
        this.playerEntity.func_143004_u();
        if (p_147346_1_.func_149568_f() == 255) {
            if (var3 == null) {
                return;
            }
            this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, var2, var3);
        } else if (p_147346_1_.func_149571_d() >= this.serverController.getBuildLimit() - 1 && (p_147346_1_.func_149568_f() == 1 || p_147346_1_.func_149571_d() >= this.serverController.getBuildLimit())) {
            ChatComponentTranslation var9 = new ChatComponentTranslation("build.tooHigh", this.serverController.getBuildLimit());
            var9.getChatStyle().setColor(EnumChatFormatting.RED);
            this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var9));
            var4 = true;
        } else {
            if (this.hasMoved && this.playerEntity.getDistanceSq((double)var5 + 0.5, (double)var6 + 0.5, (double)var7 + 0.5) < 64.0 && !this.serverController.isBlockProtected(var2, var5, var6, var7, this.playerEntity)) {
                this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(this.playerEntity, var2, var3, var5, var6, var7, var8, p_147346_1_.func_149573_h(), p_147346_1_.func_149569_i(), p_147346_1_.func_149575_j());
            }
            var4 = true;
        }
        if (var4) {
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6, var7, var2));
            if (var8 == 0) {
                --var6;
            }
            if (var8 == 1) {
                ++var6;
            }
            if (var8 == 2) {
                --var7;
            }
            if (var8 == 3) {
                ++var7;
            }
            if (var8 == 4) {
                --var5;
            }
            if (var8 == 5) {
                ++var5;
            }
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6, var7, var2));
        }
        if ((var3 = this.playerEntity.inventory.getCurrentItem()) != null && var3.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            var3 = null;
        }
        if (var3 == null || var3.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            Slot var10 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = false;
            if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), p_147346_1_.func_149574_g())) {
                this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, var10.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }

    @Override
    public void onDisconnect(IChatComponent p_147231_1_) {
        logger.info(String.valueOf(this.playerEntity.getCommandSenderName()) + " lost connection: " + p_147231_1_);
        this.serverController.func_147132_au();
        ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", this.playerEntity.func_145748_c_());
        var2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.serverController.getConfigurationManager().func_148539_a(var2);
        this.playerEntity.mountEntityAndWakeUp();
        this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
        if (this.serverController.isSinglePlayer() && this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())) {
            logger.info("Stopping singleplayer server as player logged out");
            this.serverController.initiateShutdown();
        }
    }

    public void sendPacket(final Packet p_147359_1_) {
        if (p_147359_1_ instanceof S02PacketChat) {
            S02PacketChat var2 = (S02PacketChat)p_147359_1_;
            EntityPlayer.EnumChatVisibility var3 = this.playerEntity.func_147096_v();
            if (var3 == EntityPlayer.EnumChatVisibility.HIDDEN) {
                return;
            }
            if (var3 == EntityPlayer.EnumChatVisibility.SYSTEM && !var2.func_148916_d()) {
                return;
            }
        }
        try {
            this.netManager.scheduleOutboundPacket(p_147359_1_, new GenericFutureListener[0]);
        }
        catch (Throwable var5) {
            CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
            CrashReportCategory var4 = var6.makeCategory("Packet being sent");
            var4.addCrashSectionCallable("Packet class", new Callable(){
                private static final String __OBFID = "CL_00001454";

                public String call() {
                    return p_147359_1_.getClass().getCanonicalName();
                }
            });
            throw new ReportedException(var6);
        }
    }

    @Override
    public void processHeldItemChange(C09PacketHeldItemChange p_147355_1_) {
        if (p_147355_1_.func_149614_c() >= 0 && p_147355_1_.func_149614_c() < InventoryPlayer.getHotbarSize()) {
            this.playerEntity.inventory.currentItem = p_147355_1_.func_149614_c();
            this.playerEntity.func_143004_u();
        } else {
            logger.warn(String.valueOf(this.playerEntity.getCommandSenderName()) + " tried to set an invalid carried item");
        }
    }

    @Override
    public void processChatMessage(C01PacketChatMessage p_147354_1_) {
        if (this.playerEntity.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            ChatComponentTranslation var4 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            var4.getChatStyle().setColor(EnumChatFormatting.RED);
            this.sendPacket(new S02PacketChat(var4));
        } else {
            this.playerEntity.func_143004_u();
            String var2 = p_147354_1_.func_149439_c();
            var2 = StringUtils.normalizeSpace((String)var2);
            int var3 = 0;
            while (var3 < var2.length()) {
                if (!ChatAllowedCharacters.isAllowedCharacter(var2.charAt(var3))) {
                    this.kickPlayerFromServer("Illegal characters in chat");
                    return;
                }
                ++var3;
            }
            if (var2.startsWith("/")) {
                this.handleSlashCommand(var2);
            } else {
                ChatComponentTranslation var5 = new ChatComponentTranslation("chat.type.text", this.playerEntity.func_145748_c_(), var2);
                this.serverController.getConfigurationManager().func_148544_a(var5, false);
            }
            this.chatSpamThresholdCount += 20;
            if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().isPlayerOpped(this.playerEntity.getCommandSenderName())) {
                this.kickPlayerFromServer("disconnect.spam");
            }
        }
    }

    private void handleSlashCommand(String p_147361_1_) {
        this.serverController.getCommandManager().executeCommand(this.playerEntity, p_147361_1_);
    }

    @Override
    public void processAnimation(C0APacketAnimation p_147350_1_) {
        this.playerEntity.func_143004_u();
        if (p_147350_1_.func_149421_d() == 1) {
            this.playerEntity.swingItem();
        }
    }

    @Override
    public void processEntityAction(C0BPacketEntityAction p_147357_1_) {
        this.playerEntity.func_143004_u();
        if (p_147357_1_.func_149513_d() == 1) {
            this.playerEntity.setSneaking(true);
        } else if (p_147357_1_.func_149513_d() == 2) {
            this.playerEntity.setSneaking(false);
        } else if (p_147357_1_.func_149513_d() == 4) {
            this.playerEntity.setSprinting(true);
        } else if (p_147357_1_.func_149513_d() == 5) {
            this.playerEntity.setSprinting(false);
        } else if (p_147357_1_.func_149513_d() == 3) {
            this.playerEntity.wakeUpPlayer(false, true, true);
            this.hasMoved = false;
        } else if (p_147357_1_.func_149513_d() == 6) {
            if (this.playerEntity.ridingEntity != null && this.playerEntity.ridingEntity instanceof EntityHorse) {
                ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(p_147357_1_.func_149512_e());
            }
        } else if (p_147357_1_.func_149513_d() == 7 && this.playerEntity.ridingEntity != null && this.playerEntity.ridingEntity instanceof EntityHorse) {
            ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
        }
    }

    @Override
    public void processUseEntity(C02PacketUseEntity p_147340_1_) {
        WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        Entity var3 = p_147340_1_.func_149564_a(var2);
        this.playerEntity.func_143004_u();
        if (var3 != null) {
            boolean var4 = this.playerEntity.canEntityBeSeen(var3);
            double var5 = 36.0;
            if (!var4) {
                var5 = 9.0;
            }
            if (this.playerEntity.getDistanceSqToEntity(var3) < var5) {
                if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
                    this.playerEntity.interactWith(var3);
                } else if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.ATTACK) {
                    if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
                        return;
                    }
                    this.playerEntity.attackTargetEntityWithCurrentItem(var3);
                }
            }
        }
    }

    @Override
    public void processClientStatus(C16PacketClientStatus p_147342_1_) {
        this.playerEntity.func_143004_u();
        C16PacketClientStatus.EnumState var2 = p_147342_1_.func_149435_c();
        switch (SwitchEnumState.field_151290_a[var2.ordinal()]) {
            case 1: {
                if (this.playerEntity.playerConqueredTheEnd) {
                    this.playerEntity = this.serverController.getConfigurationManager().respawnPlayer(this.playerEntity, 0, true);
                    break;
                }
                if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                    if (this.serverController.isSinglePlayer() && this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())) {
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                        this.serverController.deleteWorldAndStopServer();
                        break;
                    }
                    BanEntry var3 = new BanEntry(this.playerEntity.getCommandSenderName());
                    var3.setBanReason("Death in Hardcore");
                    this.serverController.getConfigurationManager().getBannedPlayers().put(var3);
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                    break;
                }
                if (this.playerEntity.getHealth() > 0.0f) {
                    return;
                }
                this.playerEntity = this.serverController.getConfigurationManager().respawnPlayer(this.playerEntity, 0, false);
                break;
            }
            case 2: {
                this.playerEntity.func_147099_x().func_150876_a(this.playerEntity);
                break;
            }
            case 3: {
                this.playerEntity.triggerAchievement(AchievementList.openInventory);
            }
        }
    }

    @Override
    public void processCloseWindow(C0DPacketCloseWindow p_147356_1_) {
        this.playerEntity.closeContainer();
    }

    @Override
    public void processClickWindow(C0EPacketClickWindow p_147351_1_) {
        this.playerEntity.func_143004_u();
        if (this.playerEntity.openContainer.windowId == p_147351_1_.func_149548_c() && this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            ItemStack var2 = this.playerEntity.openContainer.slotClick(p_147351_1_.func_149544_d(), p_147351_1_.func_149543_e(), p_147351_1_.func_149542_h(), this.playerEntity);
            if (ItemStack.areItemStacksEqual(p_147351_1_.func_149546_g(), var2)) {
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), true));
                this.playerEntity.isChangingQuantityOnly = true;
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.isChangingQuantityOnly = false;
            } else {
                this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, p_147351_1_.func_149547_f());
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), false));
                this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, false);
                ArrayList<ItemStack> var3 = new ArrayList<ItemStack>();
                int var4 = 0;
                while (var4 < this.playerEntity.openContainer.inventorySlots.size()) {
                    var3.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(var4)).getStack());
                    ++var4;
                }
                this.playerEntity.sendContainerAndContentsToPlayer(this.playerEntity.openContainer, var3);
            }
        }
    }

    @Override
    public void processEnchantItem(C11PacketEnchantItem p_147338_1_) {
        this.playerEntity.func_143004_u();
        if (this.playerEntity.openContainer.windowId == p_147338_1_.func_149539_c() && this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            this.playerEntity.openContainer.enchantItem(this.playerEntity, p_147338_1_.func_149537_d());
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }

    @Override
    public void processCreativeInventoryAction(C10PacketCreativeInventoryAction p_147344_1_) {
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            boolean var6;
            boolean var2 = p_147344_1_.func_149627_c() < 0;
            ItemStack var3 = p_147344_1_.func_149625_d();
            boolean var4 = p_147344_1_.func_149627_c() >= 1 && p_147344_1_.func_149627_c() < 36 + InventoryPlayer.getHotbarSize();
            boolean var5 = var3 == null || var3.getItem() != null;
            boolean bl = var6 = var3 == null || var3.getItemDamage() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;
            if (var4 && var5 && var6) {
                if (var3 == null) {
                    this.playerEntity.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), null);
                } else {
                    this.playerEntity.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), var3);
                }
                this.playerEntity.inventoryContainer.setPlayerIsPresent(this.playerEntity, true);
            } else if (var2 && var5 && var6 && this.field_147375_m < 200) {
                this.field_147375_m += 20;
                EntityItem var7 = this.playerEntity.dropPlayerItemWithRandomChoice(var3, true);
                if (var7 != null) {
                    var7.setAgeToCreativeDespawnTime();
                }
            }
        }
    }

    @Override
    public void processConfirmTransaction(C0FPacketConfirmTransaction p_147339_1_) {
        Short var2 = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
        if (var2 != null && p_147339_1_.func_149533_d() == var2.shortValue() && this.playerEntity.openContainer.windowId == p_147339_1_.func_149532_c() && !this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, true);
        }
    }

    @Override
    public void processUpdateSign(C12PacketUpdateSign p_147343_1_) {
        this.playerEntity.func_143004_u();
        WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        if (var2.blockExists(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e())) {
            TileEntitySign var4;
            TileEntity var3 = var2.getTileEntity(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e());
            if (var3 instanceof TileEntitySign && (!(var4 = (TileEntitySign)var3).func_145914_a() || var4.func_145911_b() != this.playerEntity)) {
                this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " just tried to change non-editable sign");
                return;
            }
            int var8 = 0;
            while (var8 < 4) {
                boolean var5 = true;
                if (p_147343_1_.func_149589_f()[var8].length() > 15) {
                    var5 = false;
                } else {
                    int var6 = 0;
                    while (var6 < p_147343_1_.func_149589_f()[var8].length()) {
                        if (!ChatAllowedCharacters.isAllowedCharacter(p_147343_1_.func_149589_f()[var8].charAt(var6))) {
                            var5 = false;
                        }
                        ++var6;
                    }
                }
                if (!var5) {
                    p_147343_1_.func_149589_f()[var8] = "!?";
                }
                ++var8;
            }
            if (var3 instanceof TileEntitySign) {
                var8 = p_147343_1_.func_149588_c();
                int var9 = p_147343_1_.func_149586_d();
                int var6 = p_147343_1_.func_149585_e();
                TileEntitySign var7 = (TileEntitySign)var3;
                System.arraycopy(p_147343_1_.func_149589_f(), 0, var7.field_145915_a, 0, 4);
                var7.onInventoryChanged();
                var2.func_147471_g(var8, var9, var6);
            }
        }
    }

    @Override
    public void processKeepAlive(C00PacketKeepAlive p_147353_1_) {
        if (p_147353_1_.func_149460_c() == this.field_147378_h) {
            int var2 = (int)(this.func_147363_d() - this.field_147379_i);
            this.playerEntity.ping = (this.playerEntity.ping * 3 + var2) / 4;
        }
    }

    private long func_147363_d() {
        return System.nanoTime() / 1000000;
    }

    @Override
    public void processPlayerAbilities(C13PacketPlayerAbilities p_147348_1_) {
        this.playerEntity.capabilities.isFlying = p_147348_1_.func_149488_d() && this.playerEntity.capabilities.allowFlying;
    }

    @Override
    public void processTabComplete(C14PacketTabComplete p_147341_1_) {
        ArrayList var2 = Lists.newArrayList();
        for (String var4 : this.serverController.getPossibleCompletions(this.playerEntity, p_147341_1_.func_149419_c())) {
            var2.add(var4);
        }
        this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete(var2.toArray(new String[var2.size()])));
    }

    @Override
    public void processClientSettings(C15PacketClientSettings p_147352_1_) {
        this.playerEntity.func_147100_a(p_147352_1_);
    }

    @Override
    public void processVanilla250Packet(C17PacketCustomPayload p_147349_1_) {
        if ("MC|BEdit".equals(p_147349_1_.func_149559_c())) {
            try {
                ItemStack var2 = new PacketBuffer(Unpooled.wrappedBuffer((byte[])p_147349_1_.func_149558_e())).readItemStackFromBuffer();
                if (!ItemWritableBook.func_150930_a(var2.getTagCompound())) {
                    throw new IOException("Invalid book tag!");
                }
                ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
                if (var2.getItem() == Items.writable_book && var2.getItem() == var3.getItem()) {
                    var3.setTagInfo("pages", var2.getTagCompound().getTagList("pages", 8));
                }
            }
            catch (Exception var12) {
                logger.error("Couldn't handle book info", (Throwable)var12);
            }
        } else if ("MC|BSign".equals(p_147349_1_.func_149559_c())) {
            try {
                ItemStack var2 = new PacketBuffer(Unpooled.wrappedBuffer((byte[])p_147349_1_.func_149558_e())).readItemStackFromBuffer();
                if (!ItemEditableBook.validBookTagContents(var2.getTagCompound())) {
                    throw new IOException("Invalid book tag!");
                }
                ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
                if (var2.getItem() == Items.written_book && var3.getItem() == Items.writable_book) {
                    var3.setTagInfo("author", new NBTTagString(this.playerEntity.getCommandSenderName()));
                    var3.setTagInfo("title", new NBTTagString(var2.getTagCompound().getString("title")));
                    var3.setTagInfo("pages", var2.getTagCompound().getTagList("pages", 8));
                    var3.func_150996_a(Items.written_book);
                }
            }
            catch (Exception var11) {
                logger.error("Couldn't sign book", (Throwable)var11);
            }
        } else if ("MC|TrSel".equals(p_147349_1_.func_149559_c())) {
            try {
                DataInputStream var13 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
                int var16 = var13.readInt();
                Container var4 = this.playerEntity.openContainer;
                if (var4 instanceof ContainerMerchant) {
                    ((ContainerMerchant)var4).setCurrentRecipeIndex(var16);
                }
            }
            catch (Exception var10) {
                logger.error("Couldn't select trade", (Throwable)var10);
            }
        } else if ("MC|AdvCdm".equals(p_147349_1_.func_149559_c())) {
            if (!this.serverController.isCommandBlockEnabled()) {
                this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
            } else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
                try {
                    Entity var20;
                    PacketBuffer var14 = new PacketBuffer(Unpooled.wrappedBuffer((byte[])p_147349_1_.func_149558_e()));
                    byte var17 = var14.readByte();
                    CommandBlockLogic var18 = null;
                    if (var17 == 0) {
                        TileEntity var5 = this.playerEntity.worldObj.getTileEntity(var14.readInt(), var14.readInt(), var14.readInt());
                        if (var5 instanceof TileEntityCommandBlock) {
                            var18 = ((TileEntityCommandBlock)var5).func_145993_a();
                        }
                    } else if (var17 == 1 && (var20 = this.playerEntity.worldObj.getEntityByID(var14.readInt())) instanceof EntityMinecartCommandBlock) {
                        var18 = ((EntityMinecartCommandBlock)var20).func_145822_e();
                    }
                    String var23 = var14.readStringFromBuffer(var14.readableBytes());
                    if (var18 != null) {
                        var18.func_145752_a(var23);
                        var18.func_145756_e();
                        this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", var23));
                    }
                }
                catch (Exception var9) {
                    logger.error("Couldn't set command block", (Throwable)var9);
                }
            } else {
                this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
            }
        } else if ("MC|Beacon".equals(p_147349_1_.func_149559_c())) {
            if (this.playerEntity.openContainer instanceof ContainerBeacon) {
                try {
                    DataInputStream var13 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
                    int var16 = var13.readInt();
                    int var22 = var13.readInt();
                    ContainerBeacon var21 = (ContainerBeacon)this.playerEntity.openContainer;
                    Slot var6 = var21.getSlot(0);
                    if (var6.getHasStack()) {
                        var6.decrStackSize(1);
                        TileEntityBeacon var7 = var21.func_148327_e();
                        var7.func_146001_d(var16);
                        var7.func_146004_e(var22);
                        var7.onInventoryChanged();
                    }
                }
                catch (Exception var8) {
                    logger.error("Couldn't set beacon", (Throwable)var8);
                }
            }
        } else if ("MC|ItemName".equals(p_147349_1_.func_149559_c()) && this.playerEntity.openContainer instanceof ContainerRepair) {
            ContainerRepair var15 = (ContainerRepair)this.playerEntity.openContainer;
            if (p_147349_1_.func_149558_e() != null && p_147349_1_.func_149558_e().length >= 1) {
                String var19 = ChatAllowedCharacters.filerAllowedCharacters(new String(p_147349_1_.func_149558_e(), Charsets.UTF_8));
                if (var19.length() <= 30) {
                    var15.updateItemName(var19);
                }
            } else {
                var15.updateItemName("");
            }
        }
    }

    @Override
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {
        if (p_147232_2_ != EnumConnectionState.PLAY) {
            throw new IllegalStateException("Unexpected change in protocol!");
        }
    }

    static final class SwitchEnumState {
        static final int[] field_151290_a = new int[C16PacketClientStatus.EnumState.values().length];
        private static final String __OBFID = "CL_00001455";

        static {
            try {
                SwitchEnumState.field_151290_a[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumState.field_151290_a[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumState.field_151290_a[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchEnumState() {
        }
    }

}

