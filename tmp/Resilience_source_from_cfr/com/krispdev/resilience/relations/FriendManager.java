/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.relations;

import com.krispdev.resilience.relations.Friend;
import java.util.ArrayList;
import net.minecraft.util.StringUtils;

public class FriendManager {
    public static boolean isFriend(String username) {
        for (Friend friend : Friend.friendList) {
            if (!friend.getName().trim().equalsIgnoreCase(StringUtils.stripControlCodes(username.trim()))) continue;
            return true;
        }
        return false;
    }
}

