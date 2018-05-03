/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.online.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.online.irc.extern.PublicBot;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class GuiGroupChat
extends JFrame
implements ActionListener {
    private String channel;
    private String theirUsername;
    private BotManager bot;
    private boolean setNullOnClose;
    private JTextArea chatArea;
    private JTextField chat;
    private JScrollPane sbrText;
    private GuiGroupChat instance;

    public GuiGroupChat(String theirUsername, String initChannel, BotManager bot, boolean setNullOnClose) {
        this.setNullOnClose = setNullOnClose;
        this.theirUsername = theirUsername;
        this.bot = bot;
        this.channel = initChannel;
        bot.getBot().joinChannel(this.channel);
        this.initComponents();
        this.instance = this;
    }

    public void initComponents() {
        this.setDefaultCloseOperation(2);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setSize(372, 240);
        this.setLayout(new FlowLayout());
        this.setResizable(true);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (GuiGroupChat.this.setNullOnClose) {
                    Resilience.getInstance().getValues().userChat = null;
                } else {
                    GuiGroupChat.this.bot.getBot().partChannel(GuiGroupChat.this.channel);
                }
                GuiGroupChat.this.bot.getBot().removeGui(GuiGroupChat.this.instance);
                super.windowClosing(windowEvent);
            }
        });
        this.setTitle((this.setNullOnClose ? "Your" : new StringBuilder(String.valueOf(this.theirUsername)).append("'s").toString()) + " chat room");
        this.chatArea = new JTextArea(10, 30);
        this.chat = new JTextField(30);
        this.chatArea.setLineWrap(true);
        this.chatArea.setEditable(false);
        this.sbrText = new JScrollPane(this.chatArea);
        this.sbrText.setVerticalScrollBarPolicy(22);
        this.chat.addActionListener(this);
        this.add(this.sbrText);
        this.add(this.chat);
        this.setVisible(true);
        this.setAlwaysOnTop(false);
        if (!this.setNullOnClose) {
            this.bot.getBot().addGui(this);
        }
        this.chatArea.append("You are now chatting in " + (this.setNullOnClose ? "your" : new StringBuilder(String.valueOf(this.theirUsername)).append("'s").toString()) + " chat room.");
    }

    public void addLine(String line) {
        this.chatArea.append("\n" + line);
        this.toFront();
        this.chatArea.setCaretPosition(this.chatArea.getDocument().getLength());
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.chat) {
            this.chatArea.append("\nMe: " + this.chat.getText());
            this.bot.getBot().sendMessage(this.channel, this.chat.getText());
            this.chat.setText("");
            this.chatArea.setCaretPosition(this.chatArea.getDocument().getLength());
        }
    }

}

