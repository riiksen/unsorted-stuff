/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class ModuleMiddleClickFriends
extends DefaultModule {
    public ModuleMiddleClickFriends() {
        super("Middle Click Friends", 0);
        this.setVisible(false);
        this.setCategory(ModuleCategory.MISC);
        this.setDescription("Adds the person you middle click on to your friends list");
    }

    @Override
    public void onClick(EventOnClick event) {
        Entity e;
        MovingObjectPosition obj;
        if (event.getButton() == 2 && (obj = this.invoker.getObjectMouseOver()) != null && obj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && (e = obj.entityHit) instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP player = (EntityOtherPlayerMP)e;
            for (Friend friend : Friend.friendList) {
                if (!friend.getName().equalsIgnoreCase(this.invoker.getPlayerName(player))) continue;
                Friend.friendList.remove(Friend.friendList.indexOf(friend));
                Resilience.getInstance().getFileManager().saveFriends(new String[0]);
                return;
            }
            for (Enemy enemy : Enemy.enemyList) {
                if (!enemy.getName().equalsIgnoreCase(this.invoker.getPlayerName(player))) continue;
                Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
                Resilience.getInstance().getFileManager().saveEnemies(new String[0]);
                break;
            }
            Friend.friendList.add(new Friend(this.invoker.getPlayerName(player), this.invoker.getPlayerName(player)));
            Resilience.getInstance().getFileManager().saveFriends(new String[0]);
        }
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

