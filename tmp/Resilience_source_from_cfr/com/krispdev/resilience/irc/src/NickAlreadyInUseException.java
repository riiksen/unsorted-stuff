/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.irc.src;

import com.krispdev.resilience.irc.src.IrcException;

public class NickAlreadyInUseException
extends IrcException {
    public NickAlreadyInUseException(String e) {
        super(e);
    }
}

