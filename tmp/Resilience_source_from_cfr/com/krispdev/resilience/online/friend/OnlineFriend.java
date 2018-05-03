/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.online.friend;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class OnlineFriend {
    private boolean online;
    private String username;
    private String status;
    private String mainURL = "http://resilience.krispdev.com/";
    private String channel;

    public OnlineFriend(String username, String status, String channel, boolean online) {
        this.username = username;
        this.status = status;
        this.online = online;
        this.channel = channel;
    }

    public void update() {
        new Thread(){

            @Override
            public void run() {
                try {
                    String info = Utils.sendGetRequest(String.valueOf(OnlineFriend.this.mainURL) + "getFriend.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&friend=" + OnlineFriend.this.username);
                    String[] splitInfo = info.split(":");
                    OnlineFriend.access$2(OnlineFriend.this, Boolean.parseBoolean(splitInfo[1]));
                    OnlineFriend.access$3(OnlineFriend.this, splitInfo[2]);
                    OnlineFriend.access$4(OnlineFriend.this, splitInfo[3]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getUsername() {
        return this.username;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isOnline() {
        return this.online;
    }

    public String getChannel() {
        return this.channel;
    }

    static /* synthetic */ void access$2(OnlineFriend onlineFriend, boolean bl) {
        onlineFriend.online = bl;
    }

    static /* synthetic */ void access$3(OnlineFriend onlineFriend, String string) {
        onlineFriend.status = string;
    }

    static /* synthetic */ void access$4(OnlineFriend onlineFriend, String string) {
        onlineFriend.channel = string;
    }

}

