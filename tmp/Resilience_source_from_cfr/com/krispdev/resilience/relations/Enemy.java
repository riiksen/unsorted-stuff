/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.relations;

import java.util.ArrayList;

public class Enemy {
    public static ArrayList<Enemy> enemyList = new ArrayList();
    private String name;

    public Enemy(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

