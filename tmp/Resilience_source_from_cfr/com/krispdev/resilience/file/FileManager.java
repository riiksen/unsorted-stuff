/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.krispdev.resilience.file;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.Account;
import com.krispdev.resilience.command.objects.Macro;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.file.ThreadUpdateGame;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.utilities.XrayBlock;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.utilities.value.Value;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.utilities.value.values.StringValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;

public class FileManager {
    public ThreadUpdateGame dF;
    public boolean loadGui = true;
    public boolean loadKeybinds = true;
    public boolean loadEnabledMods = true;
    public boolean loadXrayBlocks = true;
    public boolean loadEnemies = true;
    public boolean loadFriends = true;
    public boolean loadConfigs = true;
    public boolean loadWaypoints = true;
    public boolean loadMacros = true;
    public boolean shouldAsk = false;
    private File mainDir = new File(Resilience.getInstance().getName());
    private File accountsDir = new File(this.mainDir + File.separator + "Accounts");
    public File gui = new File(this.mainDir + File.separator + "GuiSettings.res");
    public File configs = new File(this.mainDir + File.separator + "Configs.res");
    public File keybinds = new File(this.mainDir + File.separator + "Keybinds.res");
    public File enabledMods = new File(this.mainDir + File.separator + "EnabledMods.res");
    public File firstTime = new File(this.mainDir + File.separator + "FirstTime.res");
    public File friends = new File(this.mainDir + File.separator + "Friends.res");
    public File enemies = new File(this.mainDir + File.separator + "Enemies.res");
    public File macros = new File(this.mainDir + File.separator + "Macros.res");
    public File waypoints = new File(this.mainDir + File.separator + "Waypoints.res");
    public File xray = new File(this.mainDir + File.separator + "XrayBlocks.res");
    public File ask = new File(this.mainDir + File.separator + "DonationAsk.res");
    public File accounts = new File(this.accountsDir + File.separator + "Accounts.res");
    public File online = new File(this.mainDir + File.separator + "Online.res");
    File[] oldFiles = new File[]{new File(this.mainDir + File.separator + "Keybinds.txt"), new File(this.mainDir + File.separator + "GuiSettings.txt"), new File(this.mainDir + File.separator + "EnabledMods.txt"), new File(this.mainDir + File.separator + "XrayBlocks.txt"), new File(this.mainDir + File.separator + "Enemies.txt"), new File(this.mainDir + File.separator + "Friends.txt"), new File(this.mainDir + File.separator + "Configs.txt"), new File(this.mainDir + File.separator + "Waypoints.txt"), new File(this.mainDir + File.separator + "FIRST_TIME_FILE_CHECKER"), new File(this.mainDir + File.separator + "Macros.txt"), new File(this.mainDir + File.separator + "FIRST_TIME_FILE_CHECK")};

    public void init() throws Exception {
        int num;
        if (!this.mainDir.exists()) {
            this.mainDir.mkdir();
            Resilience.getInstance().getLogger().info("Created the main client directory");
        }
        if (!this.accountsDir.exists()) {
            this.accountsDir.mkdir();
        }
        if (!this.ask.exists()) {
            this.saveOptions("3");
        }
        if (!this.firstTime.exists()) {
            this.firstTime.createNewFile();
            Resilience.getInstance().setFirstTime();
            File[] arrfile = this.oldFiles;
            int n = arrfile.length;
            int n2 = 0;
            while (n2 < n) {
                File file = arrfile[n2];
                if (file.exists()) {
                    file.delete();
                }
                ++n2;
            }
        }
        if (!this.keybinds.exists()) {
            this.saveBinds(new String[0]);
            Resilience.getInstance().getLogger().info("Created Keybinds.res");
        }
        if (!this.gui.exists()) {
            try {
                this.gui.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Resilience.getInstance().getLogger().info("Created GuiSettings.res");
        }
        if (!this.configs.exists()) {
            Resilience.getInstance().getValues().speed.setValue(11.0f);
            Resilience.getInstance().getValues().range.setValue(4.0f);
            this.saveConfigs(new String[0]);
            Resilience.getInstance().getLogger().info("Created Configs.res");
        }
        if (!this.enabledMods.exists()) {
            this.saveEnabledMods("Enabled Mods", "NiceChat", "Target Players", "Target Mobs", "Target Animals", "IRC");
            Resilience.getInstance().getLogger().info("Created EnabledMods.res");
        }
        if (!this.friends.exists()) {
            this.saveFriends(new String[0]);
            Resilience.getInstance().getLogger().info("Created Friends.res");
        }
        if (!this.enemies.exists()) {
            this.saveEnemies(new String[0]);
            Resilience.getInstance().getLogger().info("Created Enemies.res");
        }
        if (!this.macros.exists()) {
            this.saveMacros(new String[0]);
            Resilience.getInstance().getLogger().info("Created Macros.res");
        }
        if (!this.waypoints.exists()) {
            this.saveWaypoints(new String[0]);
            Resilience.getInstance().getLogger().info("Created Waypoints.res");
        }
        if (!this.xray.exists()) {
            this.saveXrayBlocks("56", "57", "14", "15", "16", "41", "42", "73", "74", "152", "173", "129", "133", "10", "11", "8", "9");
            Resilience.getInstance().getLogger().info("Saved XrayBlocks.res");
        }
        if (!this.online.exists() && this.firstTime.exists()) {
            Resilience.getInstance().setFirstTimeOnline();
            this.online.createNewFile();
        } else if (!FileUtils.containsString(this.online, Resilience.getInstance().getInvoker().getSessionUsername(), 0)) {
            Resilience.getInstance().setNewAccount();
        }
        if (!this.accounts.exists()) {
            this.saveAccounts(new String[0]);
            Resilience.getInstance().getLogger().info("Saved Accounts.res");
        }
        this.loadConfigs();
        if (this.loadKeybinds) {
            this.loadBinds();
        }
        if (this.loadEnabledMods) {
            this.loadEnabledMods();
        }
        if (this.loadFriends) {
            this.loadFriends();
        }
        if (this.loadEnemies) {
            this.loadEnemies();
        }
        if (this.loadMacros) {
            this.loadMacros();
        }
        if (this.loadWaypoints) {
            this.loadWaypoints();
        }
        if (this.loadXrayBlocks) {
            this.loadXrayBlocks();
        }
        if ((num = this.loadOptions()) != -1) {
            this.saveOptions(String.valueOf(num + 1));
            boolean bl = this.shouldAsk = num > 2 && !Resilience.getInstance().getValues().isDonator(Resilience.getInstance().getInvoker().getSessionUsername());
            if (this.shouldAsk) {
                this.saveOptions("0");
            }
        }
    }

    public /* varargs */ void saveGui(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        Resilience.getInstance().getClickGui();
        for (DefaultPanel panel : ClickGui.windows) {
            toPrint.add(String.valueOf(panel.getTitle()) + ":" + panel.getDragX() + ":" + panel.getDragY() + ":" + panel.isExtended() + ":" + panel.isPinned() + ":" + panel.isVisible());
        }
        FileUtils.print(toPrint, this.gui, true);
    }

    public void loadGui() {
        ArrayList<String> lines = FileUtils.read(this.gui, true);
        for (DefaultPanel panel : ClickGui.windows) {
            for (String s : lines) {
                String[] args = s.split(":");
                if (!panel.getTitle().equalsIgnoreCase(args[0])) continue;
                panel.setDragX(Integer.parseInt(args[1]));
                panel.setDragY(Integer.parseInt(args[2]));
                panel.setExtended(Boolean.parseBoolean(args[3]));
                panel.setPinned(Boolean.parseBoolean(args[4]));
                panel.setVisible(Boolean.parseBoolean(args[5]));
            }
        }
    }

    public /* varargs */ void saveConfigs(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (Value value : Resilience.getInstance().getValues().values) {
            if (value instanceof NumberValue) {
                NumberValue numValue;
                toPrint.add(String.valueOf(value.getName()) + ":" + ((numValue = (NumberValue)value).shouldRound() ? (float)((int)numValue.getValue()) : numValue.getValue()));
                continue;
            }
            if (value instanceof BoolValue) {
                BoolValue tfValue = (BoolValue)value;
                toPrint.add(String.valueOf(value.getName()) + ":" + tfValue.getState());
                continue;
            }
            if (value instanceof StringValue) {
                StringValue strValue = (StringValue)value;
                toPrint.add(String.valueOf(strValue.getName()) + ":" + strValue.getValue());
                continue;
            }
            toPrint.add(value.getName());
        }
        FileUtils.print(toPrint, this.configs, true);
    }

    public void loadConfigs() throws Exception {
        ArrayList<String> lines = FileUtils.read(this.configs, true);
        for (String s : lines) {
            String[] args = s.split(":");
            for (Value value : Resilience.getInstance().getValues().values) {
                if (!value.getName().equalsIgnoreCase(args[0])) continue;
                if (value instanceof NumberValue) {
                    for (NumberValue numValue : Resilience.getInstance().getValues().numValues) {
                        if (!numValue.getName().equalsIgnoreCase(args[0].trim())) continue;
                        numValue.setValue(Float.parseFloat(args[1]));
                    }
                    continue;
                }
                if (value instanceof BoolValue) {
                    for (BoolValue boolValue : Resilience.getInstance().getValues().boolValues) {
                        if (!boolValue.getName().equalsIgnoreCase(args[0].trim())) continue;
                        boolValue.setState(Boolean.parseBoolean(args[1]));
                    }
                    continue;
                }
                if (!(value instanceof StringValue)) continue;
                for (StringValue strValue : Resilience.getInstance().getValues().strValues) {
                    if (!strValue.getName().equalsIgnoreCase(args[0].trim())) continue;
                    strValue.setValue(args[1]);
                }
            }
        }
    }

    public /* varargs */ void saveBinds(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            toPrint.add(String.valueOf(mod.getName()) + ":" + Keyboard.getKeyName((int)mod.getKeyCode()));
        }
        FileUtils.print(toPrint, this.keybinds, true);
    }

    public void loadBinds() {
        ArrayList<String> lines = FileUtils.read(this.keybinds, true);
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            for (String s : lines) {
                String[] args = s.split(":");
                if (!mod.getName().equalsIgnoreCase(args[0].trim())) continue;
                mod.setKeyBind(Keyboard.getKeyIndex((String)args[1]));
            }
        }
    }

    public /* varargs */ void saveEnabledMods(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
            if (!mod.isEnabled() || mod.getCategory() == ModuleCategory.GUI || mod.getCategory() == ModuleCategory.NONE || !mod.shouldSave()) continue;
            toPrint.add(mod.getName());
        }
        FileUtils.print(toPrint, this.enabledMods, false);
    }

    public void loadEnabledMods() {
        ArrayList<String> lines = FileUtils.read(this.enabledMods, true);
        for (String s : lines) {
            for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
                if (!mod.getName().equalsIgnoreCase(s.trim())) continue;
                mod.setEnabled(true);
            }
        }
    }

    public /* varargs */ void saveFriends(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (Friend friend : Friend.friendList) {
            toPrint.add(String.valueOf(friend.getName()) + ":" + friend.getAlias());
        }
        FileUtils.print(toPrint, this.friends, true);
    }

    public void loadFriends() {
        ArrayList<String> lines = FileUtils.read(this.friends, true);
        for (String line : lines) {
            String[] args = line.split(":");
            if (args.length < 1) {
                Friend.friendList.add(new Friend(args[0], args[0]));
                continue;
            }
            Friend.friendList.add(new Friend(args[0], args[1]));
        }
    }

    public /* varargs */ void saveEnemies(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (Enemy enemy : Enemy.enemyList) {
            toPrint.add(enemy.getName());
        }
        FileUtils.print(toPrint, this.enemies, true);
    }

    public void loadEnemies() {
        ArrayList<String> lines = FileUtils.read(this.enemies, true);
        for (String line : lines) {
            Enemy.enemyList.add(new Enemy(line));
        }
    }

    public /* varargs */ void saveMacros(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (Macro macro : Macro.macroList) {
            toPrint.add(String.valueOf(macro.getCommand()) + ":" + macro.getKey());
        }
        FileUtils.print(toPrint, this.macros, true);
    }

    public void loadMacros() {
        ArrayList<String> lines = FileUtils.read(this.macros, true);
        for (String line : lines) {
            String[] args = line.split(":");
            Macro.macroList.add(new Macro(Integer.parseInt(args[1]), args[0]));
        }
    }

    public /* varargs */ void saveWaypoints(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (Waypoint w : Waypoint.waypointsList) {
            toPrint.add(String.valueOf(w.getName()) + ":" + w.getX() + ":" + w.getY() + ":" + w.getZ() + ":" + w.getR() + ":" + w.getG() + ":" + w.getB());
        }
        FileUtils.print(toPrint, this.waypoints, true);
    }

    public void loadWaypoints() {
        ArrayList<String> lines = FileUtils.read(this.waypoints, true);
        for (String s : lines) {
            String[] args = s.split(":");
            Waypoint.waypointsList.add(new Waypoint(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[6]), Float.parseFloat(args[5])));
        }
    }

    public void loadXrayBlocks() {
        ArrayList<String> lines = FileUtils.read(this.xray, true);
        for (String s : lines) {
            Resilience.getInstance().getXrayUtils().xrayBlocks.add(new XrayBlock(Integer.parseInt(s)));
        }
    }

    public /* varargs */ void saveXrayBlocks(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (XrayBlock xrayBlock : Resilience.getInstance().getXrayUtils().xrayBlocks) {
            toPrint.add(String.valueOf(xrayBlock.getId()));
        }
        FileUtils.print(toPrint, this.xray, true);
    }

    public void saveOptions(String num) {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add(num);
        FileUtils.print(toPrint, this.ask, false);
    }

    public int loadOptions() {
        ArrayList<String> line = FileUtils.read(this.ask, false);
        return Integer.parseInt(line.get(0));
    }

    public void downloadFile(File dir, URL fileLocation) {
        this.dF = new ThreadUpdateGame(fileLocation, dir);
        this.dF.start();
    }

    public /* varargs */ void saveAccounts(String ... presets) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String[] arrstring = presets;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String line = arrstring[n2];
            toPrint.add(line);
            ++n2;
        }
        for (Account acc : Account.accountList) {
            toPrint.add(String.valueOf(acc.getUsername()) + ":" + acc.getPassword());
        }
        FileUtils.print(toPrint, this.accounts, true);
    }

    public void loadAccounts() {
        ArrayList<String> lines = FileUtils.read(this.accounts, true);
        if (lines == null) {
            return;
        }
        for (String s : lines) {
            String[] args = s.split(":");
            if (args.length > 1 && !args[0].equals("") && args[0] != null && args[1] != null && !args[1].equals("")) {
                Account.accountList.add(new Account(args[0], args[1]));
                continue;
            }
            if (args.length != 1 || args[0].equals("") || args[0] == null) continue;
            Account.accountList.add(new Account(args[0], ""));
        }
    }
}

