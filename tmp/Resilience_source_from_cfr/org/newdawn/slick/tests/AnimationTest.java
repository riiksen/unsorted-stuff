/*
 * Decompiled with CFR 0_123.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationTest
extends BasicGame {
    private Animation animation;
    private Animation limited;
    private Animation manual;
    private Animation pingPong;
    private GameContainer container;
    private int start = 5000;

    public AnimationTest() {
        super("Animation Test");
    }

    public void init(GameContainer container) throws SlickException {
        int i;
        this.container = container;
        SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
        this.animation = new Animation();
        for (i = 0; i < 8; ++i) {
            this.animation.addFrame(sheet.getSprite(i, 0), 150);
        }
        this.limited = new Animation();
        for (i = 0; i < 8; ++i) {
            this.limited.addFrame(sheet.getSprite(i, 0), 150);
        }
        this.limited.stopAt(7);
        this.manual = new Animation(false);
        for (i = 0; i < 8; ++i) {
            this.manual.addFrame(sheet.getSprite(i, 0), 150);
        }
        this.pingPong = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
        this.pingPong.setPingPong(true);
        container.getGraphics().setBackground(new Color(0.4f, 0.6f, 0.6f));
    }

    public void render(GameContainer container, Graphics g) {
        g.drawString("Space to restart() animation", 100.0f, 50.0f);
        g.drawString("Til Limited animation: " + this.start, 100.0f, 500.0f);
        g.drawString("Hold 1 to move the manually animated", 100.0f, 70.0f);
        g.drawString("PingPong Frame:" + this.pingPong.getFrame(), 600.0f, 70.0f);
        g.scale(-1.0f, 1.0f);
        this.animation.draw(-100.0f, 100.0f);
        this.animation.draw(-200.0f, 100.0f, 144.0f, 260.0f);
        if (this.start < 0) {
            this.limited.draw(-400.0f, 100.0f, 144.0f, 260.0f);
        }
        this.manual.draw(-600.0f, 100.0f, 144.0f, 260.0f);
        this.pingPong.draw(-700.0f, 100.0f, 72.0f, 130.0f);
    }

    public void update(GameContainer container, int delta) {
        if (container.getInput().isKeyDown(2)) {
            this.manual.update(delta);
        }
        if (this.start >= 0) {
            this.start -= delta;
        }
    }

    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new AnimationTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(int key, char c) {
        if (key == 1) {
            this.container.exit();
        }
        if (key == 57) {
            this.limited.restart();
        }
    }
}

