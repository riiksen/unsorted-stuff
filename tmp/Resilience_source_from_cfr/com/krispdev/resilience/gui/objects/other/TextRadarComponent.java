/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.krispdev.resilience.gui.objects.other;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.screens.TextRadarPanel;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class TextRadarComponent
implements Comparable<TextRadarComponent> {
    public static List<TextRadarComponent> players = new ArrayList<TextRadarComponent>();
    private String name;
    private int blocksAway;
    private float posX;
    private float yPos;
    private boolean isFriend;
    private String tempName;
    private TextRadarPanel panel;

    public TextRadarComponent(String name, int blocksAway, int x, int y, boolean isFriend, TextRadarPanel panel) {
        this.tempName = this.name;
        this.name = name;
        this.tempName = name;
        this.blocksAway = blocksAway;
        this.posX = x;
        this.yPos = y;
        this.panel = panel;
    }

    public void draw(int x, int y) {
        for (Friend friend : Friend.friendList) {
            if (!StringUtils.containsIgnoreCase((CharSequence)this.tempName, (CharSequence)friend.getName())) continue;
            this.tempName = this.tempName.replaceAll("(?i)" + friend.getName(), friend.getAlias());
        }
        Utils.drawBetterRect(this.posX, this.yPos, this.posX + 104.0f, this.yPos + 13.0f, -1157562111, -1155127770);
        if (FriendManager.isFriend(this.name)) {
            Resilience.getInstance().getStandardFont().drawString(this.tempName, (int)this.posX + 2, (float)((int)this.yPos) + 0.5f, -11141121);
        } else {
            Resilience.getInstance().getStandardFont().drawString(this.name, (int)this.posX + 2, (float)((int)this.yPos) + 0.5f, -1);
        }
        Utils.drawBetterRect(this.posX + 91.0f, this.yPos + 1.0f, (double)this.posX + 103.5, (double)this.yPos + 12.5, -16777216, FriendManager.isFriend(this.name) ? -1152627636 : -1155127770);
        if (FriendManager.isFriend(this.name)) {
            Resilience.getInstance().getStandardFont().drawString("F", (float)((int)this.posX) + 94.5f, (int)this.yPos + 1, -11141121);
        } else {
            Resilience.getInstance().getStandardFont().drawString("F", (float)((int)this.posX) + 94.5f, (int)this.yPos + 1, -1);
        }
        Resilience.getInstance().getStandardFont().drawString("" + this.blocksAway, (float)((int)this.posX) + 88.5f - Resilience.getInstance().getStandardFont().getWidth("" + this.blocksAway), (int)this.yPos + 1, -1);
    }

    public boolean mouseClicked(int x, int y, int b) {
        if ((float)x >= this.posX + 91.0f && (double)x <= (double)this.posX + 103.5 && (float)y >= this.yPos + 1.0f && (double)y <= (double)this.yPos + 12.5) {
            Resilience.getInstance().getClickGui().focusWindow(this.panel);
            if (FriendManager.isFriend(this.name)) {
                for (Friend friend : Friend.friendList) {
                    if (!friend.getName().trim().equalsIgnoreCase(this.name) && !friend.getAlias().trim().equalsIgnoreCase(this.name)) continue;
                    Friend.friendList.remove(Friend.friendList.indexOf(friend));
                    Resilience.getInstance().getFileManager().saveFriends(new String[0]);
                }
            } else {
                Friend.friendList.add(new Friend(this.name, this.name));
            }
            return true;
        }
        return false;
    }

    public int getBlocksAway() {
        return this.blocksAway;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(TextRadarComponent arg0) {
        return this.blocksAway - arg0.blocksAway;
    }
}

