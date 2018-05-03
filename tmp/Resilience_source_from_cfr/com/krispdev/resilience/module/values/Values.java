/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.values;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.online.gui.GuiGroupChat;
import com.krispdev.resilience.utilities.value.Value;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.utilities.value.values.StringValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public class Values {
    public ArrayList<Value> values = new ArrayList();
    public ArrayList<NumberValue> numValues = new ArrayList();
    public ArrayList<BoolValue> boolValues = new ArrayList();
    public ArrayList<StringValue> strValues = new ArrayList();
    public Waypoint deathWaypoint;
    public NumberValue antiAFKSeconds = new NumberValue(60.0f, 1.0f, 120.0f, "AntiAFK Delay (Sec.)", true);
    public NumberValue autoSoupHealth = new NumberValue(14.0f, 1.0f, 19.0f, "AutoSoup Threshold", true);
    public NumberValue flySpeed = new NumberValue(2.0f, 0.0f, 10.0f, "Flight Speed", false);
    public NumberValue speed = new NumberValue(11.0f, 3.0f, 15.0f, "KillAura Speed", false);
    public NumberValue range = new NumberValue(3.9f, 2.5f, 6.0f, "KillAura Range", false);
    public NumberValue nukerRadius = new NumberValue(4.0f, 2.0f, 6.0f, "Nuker Radius", true);
    public NumberValue fastBreakSpeed = new NumberValue(0.8f, 0.1f, 0.9f, "FastBreak Speed", false);
    public NumberValue timerSpeed = new NumberValue(2.0f, 0.1f, 10.0f, "Speed Multiplier", false);
    public NumberValue highJumpMultiplier = new NumberValue(3.0f, 2.0f, 30.0f, "HighJump Multiplier", true);
    public NumberValue stepHeight = new NumberValue(2.0f, 1.0f, 30.0f, "Step Height", true);
    public NumberValue searchRange = new NumberValue(100.0f, 40.0f, 300.0f, "Search Range", true);
    public NumberValue buttonSize = new NumberValue(1.0f, 1.0f, 2.0f, "GUI Button Size", true);
    public BoolValue players = new BoolValue("Attack Players", true);
    public BoolValue mobs = new BoolValue("Attack Mobs", true);
    public BoolValue animals = new BoolValue("Attack Animals", true);
    public BoolValue invisibles = new BoolValue("Attack Invisibles", false);
    public BoolValue propBlocks = new BoolValue("Attack PropBlocks", false);
    public BoolValue safeMode = new BoolValue("Safe Mode", false);
    public BoolValue chestESPTracers = new BoolValue("Draw ChestESP Tracers", false);
    public StringValue nameProtectAlias = new StringValue("NameProtect Alias", "SetNameProtectAlias");
    public StringValue cmdPrefix = new StringValue("Command Prefix", ".");
    public StringValue ircPrefix = new StringValue("IRC Prefix", "@");
    public boolean enabledModsEnabled = false;
    public boolean flightEnabled = false;
    public boolean niceChatEnabled = false;
    public boolean nameProtectEnabled = false;
    public boolean caveFinderEnabled = false;
    public boolean namesEnabled = false;
    public boolean noFireEffectEnabled = false;
    public boolean potionEffectsEnabled = false;
    public boolean fastBreakEnabled = false;
    public boolean ircEnabled = false;
    public boolean killAuraEnabled = false;
    public boolean autoBlockEnabled = false;
    public boolean noHurtcamEnabled = false;
    public boolean freecamEnabled = false;
    public boolean highJumpEnabled = false;
    public boolean jesusEnabled = false;
    public boolean noSlowdownEnabled = false;
    public boolean autoRespawnEnabled = false;
    public boolean antiBlindessEnabled = false;
    public boolean antiNauseaEnabled = false;
    public boolean safeWalkEnabled = false;
    public boolean autoChestStealEnabled = false;
    public boolean wireFrameEnabled = false;
    public List<Double[]> breadcrumbPosList = new ArrayList<Double[]>();
    public List<Double[]> trackPosList = new ArrayList<Double[]>();
    public String trackName = "No one to track";
    public String banReason = "No reason given. Contact krisphf@gmail.com for details.";
    public String banTime = "Infinite";
    public final ResourceLocation altBackground = new ResourceLocation("textures/blocks/stone.png");
    public final int UPDATE_VERSION = 28;
    public ArrayList<Float[]> searchIds = new ArrayList();
    public int ticksForSearch = 70;
    public int version = 4;
    public String onlinePassword;
    public String ircChannel = "#ResilienceMCChan";
    public String userChannel;
    public GuiGroupChat userChat;

    public void initValues() {
        this.add(new Value("=-=-=-=Number Settings=-=-=-="));
        this.add(this.autoSoupHealth);
        this.add(this.antiAFKSeconds);
        this.add(this.fastBreakSpeed);
        this.add(this.flySpeed);
        this.add(this.highJumpMultiplier);
        this.add(this.nukerRadius);
        this.add(this.timerSpeed);
        this.add(this.searchRange);
        this.add(this.speed);
        this.add(this.stepHeight);
        this.add(this.range);
        this.add(new Value("=-=-=-=True/False Settings=-=-=-="));
        this.add(this.chestESPTracers);
        this.add(this.players);
        this.add(this.mobs);
        this.add(this.animals);
        this.add(new Value("=-=-=-=Other Settings=-=-=-="));
        this.add(this.nameProtectAlias);
        this.add(this.cmdPrefix);
        this.add(this.ircPrefix);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isDonator(String username) {
        try {
            String[] args;
            String temp;
            URL url = new URL("http://resilience.krispdev.com/Rerererencedonatorsx789");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            do {
                if ((temp = in.readLine()) != null) continue;
                return false;
            } while (!username.equalsIgnoreCase((args = temp.split("BITCHEZBECRAYCRAY123WAYOVER30CHAR"))[3]));
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isAccountBanned() {
        try {
            String[] args;
            String temp;
            URL url = new URL("http://resilience.krispdev.com/BannedMembers");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            do {
                if ((temp = in.readLine()) != null) continue;
                return false;
            } while (!(args = temp.split("~SPLITCHAR~"))[0].trim().equalsIgnoreCase(Resilience.getInstance().getInvoker().getSessionUsername()));
            this.banReason = args[1];
            this.banTime = args[2];
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean needsUpdate() {
        try {
            URL url = new URL("http://krispdev.com/Resilience-Version");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String text = in.readLine();
            if (Integer.parseInt(text) > 28) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void add(Value v) {
        if (v instanceof NumberValue) {
            this.numValues.add((NumberValue)v);
        } else if (v instanceof BoolValue) {
            this.boolValues.add((BoolValue)v);
        } else if (v instanceof StringValue) {
            this.strValues.add((StringValue)v);
        }
        this.values.add(v);
    }
}

