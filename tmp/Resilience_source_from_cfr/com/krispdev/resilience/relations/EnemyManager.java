/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.relations;

import com.krispdev.resilience.relations.Enemy;
import java.util.ArrayList;
import net.minecraft.util.StringUtils;

public class EnemyManager {
    public static boolean isEnemy(String username) {
        for (Enemy friend : Enemy.enemyList) {
            if (!friend.getName().trim().equalsIgnoreCase(StringUtils.stripControlCodes(username.trim()))) continue;
            return true;
        }
        return false;
    }
}

