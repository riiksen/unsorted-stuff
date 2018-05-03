/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.hooks;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.irc.MainIRCBot;
import com.krispdev.resilience.irc.MainIRCManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.online.irc.extern.PublicBot;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.PrintStream;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class HookEntityClientPlayerMP
extends EntityClientPlayerMP {
    private int cooldownTimer = 0;
    private String prevMessage = "";

    public HookEntityClientPlayerMP(Minecraft p_i45064_1_, World p_i45064_2_, Session p_i45064_3_, NetHandlerPlayClient netClientHandler, StatFileWriter p_i45064_5_) {
        super(p_i45064_1_, p_i45064_2_, p_i45064_3_, netClientHandler, p_i45064_5_);
    }

    @Override
    public void onUpdate() {
        if (this.cooldownTimer > 0) {
            --this.cooldownTimer;
        }
        EventOnUpdate updateEvent = new EventOnUpdate(this);
        updateEvent.onEvent();
        if (updateEvent.isCancelled()) {
            updateEvent.setCancelled(false);
            return;
        }
        super.onUpdate();
    }

    @Override
    public void sendChatMessage(String s) {
        block17 : {
            if (s.startsWith(Resilience.getInstance().getCmdPrefix()) && Resilience.getInstance().isEnabled()) {
                for (Command cmd : Command.cmdList) {
                    try {
                        String replaced = s.replaceFirst(Resilience.getInstance().getCmdPrefix(), "");
                        String[] inputWords = replaced.split(" ");
                        if (replaced.startsWith(cmd.getWords())) {
                            try {
                                if (!cmd.recieveCommand(s.replaceFirst(Resilience.getInstance().getCmdPrefix(), ""))) continue;
                                break;
                            }
                            catch (Exception e) {
                                Resilience.getInstance().getLogger().warningChat("\u00a7cInternal error! \u00a7fSyntax: \u00a7b" + cmd.getWords().concat(cmd.getExtras()));
                                continue;
                            }
                        }
                        s.replace(Resilience.getInstance().getCmdPrefix(), "").toLowerCase().startsWith(cmd.getFirstWord());
                    }
                    catch (Exception ex) {
                        Resilience.getInstance().getLogger().warningChat("Reset the command prefix to \".\" due to strange internal exception!");
                        Resilience.getInstance().setCmdPrefix(".");
                    }
                }
            } else if (s.startsWith(Resilience.getInstance().getIRCPrefix()) && Resilience.getInstance().isEnabled()) {
                try {
                    if (!Resilience.getInstance().getValues().ircEnabled) {
                        Resilience.getInstance().getLogger().warningChat("Please enable \"IRC\" to chat in the IRC!");
                        return;
                    }
                    String msg = s.replaceFirst(Resilience.getInstance().getIRCPrefix(), "");
                    if (this.cooldownTimer < 2) {
                        this.cooldownTimer = 30;
                        if (!msg.trim().equalsIgnoreCase(this.prevMessage)) {
                            this.prevMessage = msg;
                            Resilience.getInstance().getIRCChatManager().bot.sendMessage(Resilience.getInstance().getValues().ircChannel, s.replaceFirst(Resilience.getInstance().getIRCPrefix(), ""));
                            System.out.println("sending message to " + Resilience.getInstance().getValues().ircChannel);
                            String msgToPlace = Resilience.getInstance().getIRCManager().bot.getNick();
                            boolean nick = msgToPlace.startsWith("XxXN");
                            if (nick) {
                                msgToPlace = msgToPlace.replaceFirst("XxXN", "");
                            }
                            msgToPlace.replaceFirst("Krisp_", "Krisp");
                            boolean krisp = msgToPlace.equals("Krisp_");
                            boolean vip = Donator.isDonator(msgToPlace, 5.0f);
                            Resilience.getInstance().getLogger().irc(String.valueOf(msgToPlace) + ": " + msg);
                            Resilience.getInstance().getLogger().ircChat(String.valueOf(nick ? "\u00a7f[\u00a73NickName\u00a7f]\u00a7b " : "") + (krisp ? "\u00a7f[\u00a7cOwner\u00a7f] \u00a7b" : (vip ? "\u00a7f[\u00a76VIP\u00a7f]\u00a7b " : "\u00a7b")) + msgToPlace + "\u00a78:" + (krisp ? "\u00a7c " : (vip ? "\u00a76 " : "\u00a7f ")) + msg);
                        } else {
                            Resilience.getInstance().getLogger().warningChat("Please don't send the same message twice in a row!");
                        }
                        break block17;
                    }
                    Resilience.getInstance().getLogger().warningChat("Please wait a bit between IRC chats!");
                }
                catch (Exception e) {
                    Resilience.getInstance().getLogger().warningChat("Error in IRC. Have you enabled \"IRC\"? To be safe, we have reset the IRC prefix to \"@\"");
                    Resilience.getInstance().setIRCPrefix("@");
                    e.printStackTrace();
                }
            } else {
                super.sendChatMessage(s);
            }
        }
    }

    @Override
    public void sendMotionUpdates() {
        if (Resilience.getInstance().getValues().freecamEnabled) {
            return;
        }
        float prevPitch = Resilience.getInstance().getInvoker().getRotationPitch();
        float prevYaw = Resilience.getInstance().getInvoker().getRotationYaw();
        EventPreMotion eventPre = new EventPreMotion(this);
        eventPre.onEvent();
        if (eventPre.isCancelled()) {
            eventPre.setCancelled(false);
            return;
        }
        super.sendMotionUpdates();
        Resilience.getInstance().getInvoker().setRotationPitch(prevPitch);
        Resilience.getInstance().getInvoker().setRotationYaw(prevYaw);
        EventPostMotion eventPost = new EventPostMotion(this);
        eventPost.onEvent();
    }

    @Override
    public void moveEntity(double par1, double par3, double par5) {
        super.moveEntity(par1, par3, par5);
        if (Resilience.getInstance().getValues().flightEnabled) {
            this.inWater = false;
        }
    }

    @Override
    public boolean handleWaterMovement() {
        if (Resilience.getInstance().getValues().flightEnabled) {
            return false;
        }
        return super.handleWaterMovement();
    }

    @Override
    public void setHealth(float health) {
        EventHealthUpdate eventHealth = new EventHealthUpdate(health);
        eventHealth.onEvent();
        super.setHealth(health);
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (Resilience.getInstance().getValues().freecamEnabled) {
            return false;
        }
        return super.isEntityInsideOpaqueBlock();
    }

    @Override
    protected boolean func_145771_j(double par1, double par3, double par5) {
        if (Resilience.getInstance().getValues().freecamEnabled) {
            return false;
        }
        return super.func_145771_j(par1, par3, par5);
    }
}

