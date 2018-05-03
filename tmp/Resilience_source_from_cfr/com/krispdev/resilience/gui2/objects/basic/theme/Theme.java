/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui2.objects.basic.theme;

import com.krispdev.resilience.gui2.objects.basic.theme.ThemeColour;
import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;

public abstract class Theme {
    private String name;
    private ThemeColour panelColour;
    private ThemeColour pinButtonColour;
    private ThemeColour expandButtonColour;
    private ThemeColour expandedColour;
    private int textColour;
    private Rectangle panelSize;
    private Rectangle pinButtonSize;
    private Rectangle expandButtonSize;

    public Theme(String name, ThemeColour panelColour, ThemeColour pinButtonColour, ThemeColour expandButtonColour, ThemeColour expandedColour, int textColour, Rectangle panelSize, Rectangle pinButtonSize, Rectangle expandButtonSize) {
        this.name = name;
        this.panelColour = panelColour;
        this.pinButtonColour = pinButtonColour;
        this.expandButtonColour = expandButtonColour;
        this.expandedColour = expandedColour;
        this.textColour = textColour;
    }

    public ThemeColour getPanelColour() {
        return this.panelColour;
    }

    public ThemeColour getPinButtonColour() {
        return this.pinButtonColour;
    }

    public ThemeColour getExpandButtonColour() {
        return this.expandButtonColour;
    }

    public ThemeColour getExpandedColour() {
        return this.expandedColour;
    }

    public String getName() {
        return this.name;
    }
}

