/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.other.TextRadarComponent;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public class TextRadarPanel
extends DefaultPanel {
    private int count = 17;
    ArrayList<TextRadarComponent> inOrder = new ArrayList();

    public TextRadarPanel(String title, int x, int y, int x1, int y1, boolean visible) {
        super(title, x, y, x1, y1, visible);
    }

    @Override
    public void draw(int i, int j) {
        TextRadarComponent.players.clear();
        this.inOrder.clear();
        super.draw(i, j);
        for (Object o : Resilience.getInstance().getInvoker().getEntityList()) {
            if (!((Entity)o instanceof EntityOtherPlayerMP)) continue;
            EntityOtherPlayerMP otherPlayer = (EntityOtherPlayerMP)o;
            if (Resilience.getInstance().getInvoker().getPlayerName(otherPlayer).equalsIgnoreCase(Resilience.getInstance().getInvoker().getSessionUsername())) continue;
            this.inOrder.add(new TextRadarComponent(Resilience.getInstance().getInvoker().stripControlCodes(Resilience.getInstance().getInvoker().getPlayerName(otherPlayer)), (int)Resilience.getInstance().getInvoker().getDistanceToEntity(otherPlayer, Resilience.getInstance().getWrapper().getPlayer()), this.getX() + 3, this.getY() + this.count, FriendManager.isFriend(otherPlayer.func_145748_c_().getUnformattedText()), this));
        }
        Collections.sort(this.inOrder);
        for (TextRadarComponent rad : this.inOrder) {
            TextRadarComponent.players.add(new TextRadarComponent(rad.getName(), rad.getBlocksAway(), this.getX() + 3, this.count + this.getY() + 4, FriendManager.isFriend(rad.getName()), this));
            this.count += 15;
        }
        if (this.inOrder.size() != 0 && this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), (float)this.getY() + ((float)(15 * this.inOrder.size()) + 20.5f), -1727790076);
        }
        for (TextRadarComponent radar1 : TextRadarComponent.players) {
            if (!this.isExtended()) continue;
            radar1.draw(i, j);
        }
        this.count = 16;
    }

    @Override
    public boolean onClick(int i, int j, int k) {
        if (super.onClick(i, j, k)) {
            return true;
        }
        for (TextRadarComponent radar : TextRadarComponent.players) {
            if (!radar.mouseClicked(i, j, k)) continue;
            return true;
        }
        return false;
    }
}

