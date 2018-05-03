/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.account;

import java.util.ArrayList;

public class Account {
    public static ArrayList<Account> accountList = new ArrayList();
    private String username;
    private String password;
    private boolean premium;

    public Account(String username) {
        if (username.equalsIgnoreCase("krisp")) {
            username = String.valueOf(username) + "_NotRealKrisp";
        }
        this.username = username;
        this.password = "";
        this.premium = false;
    }

    public Account(String username, String password) {
        if (username.equalsIgnoreCase("krisp")) {
            username = String.valueOf(username) + "_NotRealKrisp";
        }
        this.username = username;
        this.password = password;
        this.premium = password != null && !password.equals("");
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isPremium() {
        return this.premium;
    }
}

