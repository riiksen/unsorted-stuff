/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.online.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.friend.OnlineFriend;
import com.krispdev.resilience.online.gui.GuiAddFriend;
import com.krispdev.resilience.online.gui.GuiFriendSlot;
import com.krispdev.resilience.online.gui.GuiGroupChat;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

public class GuiFriendManager
extends GuiScreen {
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private GuiScreen parent;
    public static ArrayList<OnlineFriend> friends = new ArrayList();
    public static ArrayList<String> requests = new ArrayList();
    private GuiFriendSlot friendSlot;
    public String state = "You have no pending requests";
    private Timer timer = new Timer();
    private Timer timer2 = new Timer();
    private int index;
    private ResilienceButton toggleButton;

    public GuiFriendManager(GuiScreen parent) {
        friends.clear();
        this.parent = parent;
        this.state = "Loading...";
        this.update();
    }

    @Override
    public void initGui() {
        this.friendSlot = new GuiFriendSlot(this.invoker.getWrapper().getMinecraft(), this);
        this.friendSlot.registerScrollButtons(7, 8);
        this.invoker.addButton(this, new ResilienceButton(1, this.width / 2 - 100, this.height - 54 + 1 + 2, 200.0f, 20.0f, "Join"));
        this.invoker.addButton(this, new ResilienceButton(2, this.width / 2 - 100, this.height - 30 + 1 + 2, 200.0f, 20.0f, "Refresh"));
        this.invoker.addButton(this, new ResilienceButton(3, this.width / 2 + 104, this.height - 30 + 1 + 2, 100.0f, 20.0f, "Remove"));
        this.invoker.addButton(this, new ResilienceButton(4, this.width / 2 - 204, this.height - 30 + 1 + 2, 100.0f, 20.0f, "Add"));
        this.invoker.addButton(this, new ResilienceButton(7, this.width / 2 - 204, this.height - 53 + 2, 100.0f, 20.0f, "Chat"));
        this.toggleButton = new ResilienceButton(8, this.width / 2 + 104, this.height - 53 + 2, 100.0f, 20.0f, "Go Offline");
        this.invoker.addButton(this, this.toggleButton);
        this.invoker.addButton(this, new ResilienceButton(5, this.width / 2 - 60 - 60, 14.0f, 60.0f, 15.0f, "Accept"));
        this.invoker.addButton(this, new ResilienceButton(6, this.width / 2 + 60, 14.0f, 60.0f, 15.0f, "Deny"));
        this.invoker.addButton(this, new ResilienceButton(0, 4.0f, 4.0f, 50.0f, 20.0f, "Back"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        try {
            for (OnlineFriend friend : friends) {
                if (!friend.getUsername().equals("")) continue;
                friends.remove(friends.indexOf(friend));
            }
        }
        catch (Exception friend) {
            // empty catch block
        }
        if (this.timer.hasTimePassed(10000) && friends.size() > 0) {
            OnlineFriend friend;
            this.timer.reset();
            try {
                friends.get(this.index);
            }
            catch (Exception e) {
                this.index = 0;
            }
            friend = friends.get(this.index);
            friend.update();
            ++this.index;
        }
        if (this.friendSlot != null) {
            this.friendSlot.drawScreen(i, j, f);
        }
        Resilience.getInstance().getStandardFont().drawCenteredString(this.state, this.invoker.getWidth() / 2, 1.5f, -1);
        int var4 = 0;
        while (var4 < this.buttonList.size()) {
            if (this.invoker.getId((GuiButton)this.buttonList.get(var4)) == 5 || this.invoker.getId((GuiButton)this.buttonList.get(var4)) == 6) {
                if (requests.size() > 0) {
                    ((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, i, j);
                }
            } else {
                ((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, i, j);
            }
            ++var4;
        }
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        try {
            if (this.invoker.getId(btn) == 0) {
                this.invoker.displayScreen(this.parent);
            } else if (this.invoker.getId(btn) == 1) {
                OnlineFriend friend = friends.get(this.friendSlot.getSelectedSlot());
                if (friend.isOnline()) {
                    if (friend.getStatus().startsWith("Playing on ")) {
                        String[] server = friend.getStatus().split("Playing on ");
                        String serverIPNonSplit = server[1];
                        String[] serverIP = serverIPNonSplit.split(",port,");
                        this.invoker.displayScreen(new GuiConnecting(this, this.invoker.getWrapper().getMinecraft(), new ServerData("Friend's Server", String.valueOf(serverIP[0]) + ":" + serverIP[1])));
                    } else {
                        this.state = "User is not playing on a server!";
                    }
                } else {
                    this.state = "User is not online!";
                }
            } else if (this.invoker.getId(btn) == 5) {
                if (requests.size() > 0) {
                    String result = Utils.sendGetRequest("http://resilience.krispdev.com/acceptRequest.php?ign=" + this.invoker.getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&request=" + requests.get(0));
                    requests.remove(0);
                    this.update();
                    this.state = requests.size() > 0 ? "You have a friend request from \u00a7b" + requests.get(0) : "You have no pending friend requests";
                }
            } else if (this.invoker.getId(btn) == 6) {
                if (requests.size() > 0) {
                    String result = Utils.sendGetRequest("http://resilience.krispdev.com/denyRequest.php?ign=" + this.invoker.getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&request=" + requests.get(0));
                    requests.remove(0);
                    this.update();
                    this.state = requests.size() > 0 ? "You have a friend request from \u00a7b" + requests.get(0) : "You have no pending friend requests";
                }
            } else if (this.invoker.getId(btn) == 2) {
                this.update();
            } else if (this.invoker.getId(btn) == 4) {
                this.invoker.displayScreen(new GuiAddFriend(this));
            } else if (this.invoker.getId(btn) == 3) {
                OnlineFriend friend = friends.get(this.friendSlot.getSelectedSlot());
                String result = Utils.sendGetRequest("http://resilience.krispdev.com/denyFriend.php?ign=" + this.invoker.getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&friend=" + friend.getUsername());
                this.update();
                this.state = result.equals("") ? "Removed friend!" : "Error removing friend! Please try again later";
            } else if (this.invoker.getId(btn) == 7) {
                OnlineFriend friend = friends.get(this.friendSlot.getSelectedSlot());
                if (!friend.isOnline()) {
                    this.state = "User is not online!";
                    return;
                }
                friend.update();
                System.out.println(friend.getChannel());
                GuiGroupChat result = new GuiGroupChat(friend.getUsername(), friend.getChannel(), Resilience.getInstance().getIRCManager(), false);
            } else if (this.invoker.getId(btn) == 8) {
                if (this.toggleButton.displayString.equals("Go Offline")) {
                    this.toggleButton.displayString = "Go Online";
                    Utils.setOnline(false);
                } else {
                    this.toggleButton.displayString = "Go Offline";
                    Utils.setOnline(true);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update() {
        friends.clear();
        requests.clear();
        new Thread(){

            @Override
            public void run() {
                int n;
                String[] arrstring;
                int n2;
                GuiFriendManager.friends.clear();
                GuiFriendManager.requests.clear();
                String result = Utils.sendGetRequest("http://resilience.krispdev.com/getFriends.php?ign=" + GuiFriendManager.this.invoker.getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword);
                if (!result.equals("")) {
                    try {
                        String[] friends;
                        arrstring = friends = result.split(";");
                        n = arrstring.length;
                        n2 = 0;
                        while (n2 < n) {
                            String friend = arrstring[n2];
                            String[] info = friend.split(":");
                            try {
                                GuiFriendManager.friends.add(new OnlineFriend(info[0], info[2], info[3], Boolean.parseBoolean(info[1])));
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ++n2;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!(result = Utils.sendGetRequest("http://resilience.krispdev.com/getRequests.php?ign=" + GuiFriendManager.this.invoker.getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword)).equals("")) {
                    try {
                        String[] requests;
                        arrstring = requests = result.split(":");
                        n = arrstring.length;
                        n2 = 0;
                        while (n2 < n) {
                            String request = arrstring[n2];
                            GuiFriendManager.requests.add(request);
                            ++n2;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                GuiFriendManager.this.state = GuiFriendManager.requests.size() > 0 ? "You have a friend request from \u00a7b" + GuiFriendManager.requests.get(0) : "You have no pending friend requests";
            }
        }.start();
    }

}

