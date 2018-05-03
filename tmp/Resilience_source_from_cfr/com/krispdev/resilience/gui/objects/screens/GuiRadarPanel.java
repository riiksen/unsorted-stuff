/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiRadarPanel
extends DefaultPanel {
    private int length = 112;

    public GuiRadarPanel(String title, int x, int y, int x1, int y1, boolean visible) {
        super(title, x, y, x1, y1, visible);
    }

    @Override
    public void draw(int i, int j) {
        this.length = 133;
        super.draw(i, j);
        if (this.isExtended()) {
            Utils.drawRect(this.getX(), this.getY() + 17, this.getX1(), this.getY() + this.length, -1727790076);
            Utils.drawRect(this.getX() + this.length / 2 - 1, this.getY() + this.length / 2 - 1 + 8 + 1, this.getX() + this.length / 2 - 1, this.getY() + (this.length / 2 + 1) + 8 + 1, -6710785);
            Utils.drawSmallHL((float)this.getX() + 0.5f, this.getY() + this.length / 2 + 8, (float)(this.getX() + this.length) - 0.5f, 1155851492);
            Utils.drawSmallVL(this.getY() + this.length, this.getX() + this.length / 2, (float)this.getY() + 17.5f, 1155851492);
            Utils.drawSmallHL(this.getX(), this.getY() + 17, this.getX() + this.length, 1155851492);
            Utils.drawSmallVL(this.getY() + this.length, this.getX(), (float)this.getY() + 17.5f, 1155851492);
            Utils.drawSmallHL(this.getX(), this.getY() + this.length, this.getX() + this.length, 1155851492);
            Utils.drawSmallVL(this.getY() + this.length, (float)(this.getX() + this.length) - 0.5f, (float)this.getY() + 17.5f, 1155851492);
            for (Object o : Resilience.getInstance().getInvoker().getEntityList()) {
                if (!(o instanceof EntityOtherPlayerMP)) continue;
                EntityOtherPlayerMP entity = (EntityOtherPlayerMP)o;
                double diffX = Resilience.getInstance().getInvoker().getPosX() - Resilience.getInstance().getInvoker().getPosX(entity);
                double diffZ = Resilience.getInstance().getInvoker().getPosZ() - Resilience.getInstance().getInvoker().getPosZ(entity);
                double xzDiff = Math.sqrt(diffX * diffX + diffZ * diffZ);
                double angleDiff = Utils.wrapAngleTo180_double((double)Resilience.getInstance().getInvoker().getRotationYaw() - Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793);
                double finalX = Math.cos(Math.toRadians(angleDiff)) * xzDiff * 2.0;
                double finalY = (- Math.sin(Math.toRadians(angleDiff))) * xzDiff * 2.0;
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.getX() + this.length / 2), (float)(this.getY() + this.length / 2 + 8), (float)0.0f);
                if (xzDiff < 55.0 && !Resilience.getInstance().getInvoker().getPlayerName(entity).equalsIgnoreCase(Resilience.getInstance().getInvoker().getSessionUsername())) {
                    Resilience.getInstance().getRadarFont().drawCenteredString(String.valueOf(Resilience.getInstance().getInvoker().getPlayerName(entity)) + " \u00a7f[\u00a7b" + (int)Resilience.getInstance().getInvoker().getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), entity) + "\u00a7f]", (float)finalX / 2.0f, (float)finalY / 2.0f + 2.0f, FriendManager.isFriend(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? -11184641 : (EnemyManager.isEnemy(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? -65536 : -1));
                    Utils.drawRect((float)finalX / 2.0f, (float)finalY / 2.0f, 2.0f + (float)finalX / 2.0f, 2.0f + (float)finalY / 2.0f, FriendManager.isFriend(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? -12303207 : (EnemyManager.isEnemy(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? -65536 : -1));
                    GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                    EntityOtherPlayerMP p = entity;
                    GL11.glScalef((float)1.0f, (float)0.5f, (float)1.0f);
                }
                GL11.glPopMatrix();
            }
        }
    }
}

