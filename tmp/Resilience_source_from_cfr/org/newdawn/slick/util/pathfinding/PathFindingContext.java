/*
 * Decompiled with CFR 0_123.
 */
package org.newdawn.slick.util.pathfinding;

import org.newdawn.slick.util.pathfinding.Mover;

public interface PathFindingContext {
    public Mover getMover();

    public int getSourceX();

    public int getSourceY();

    public int getSearchDistance();
}

