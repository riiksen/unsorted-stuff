/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.UserAuthentication
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL15
 */
package com.krispdev.resilience.utilities;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class Utils {
    private static ByteBuffer boxSides;
    private static int cube;
    private static String returnString;
    private static String server;
    private static String username;
    private static State state;

    public static final void drawRect(float par0, float par1, float par2, float par3, int par4) {
        float var5;
        if (par0 < par2) {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }
        if (par1 < par3) {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }
        float var10 = (float)(par4 >> 24 & 255) / 255.0f;
        float var6 = (float)(par4 >> 16 & 255) / 255.0f;
        float var7 = (float)(par4 >> 8 & 255) / 255.0f;
        float var8 = (float)(par4 & 255) / 255.0f;
        Tessellator var9 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float)var6, (float)var7, (float)var8, (float)var10);
        var9.startDrawingQuads();
        var9.addVertex(par0, par3, 0.0);
        var9.addVertex(par2, par3, 0.0);
        var9.addVertex(par2, par1, 0.0);
        var9.addVertex(par0, par1, 0.0);
        var9.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static final void drawBetterRect(double x, double y, double x1, double y1, int color2, int color) {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        Utils.drawRect((int)x, (int)y, (int)x1, (int)y1, color);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        Utils.drawRect((int)x * 2 - 1, (int)y * 2, (int)x * 2, (int)y1 * 2 - 1, color2);
        Utils.drawRect((int)x * 2, (int)y * 2 - 1, (int)x1 * 2, (int)y * 2, color2);
        Utils.drawRect((int)x1 * 2, (int)y * 2, (int)x1 * 2 + 1, (int)y1 * 2 - 1, color2);
        Utils.drawRect((int)x * 2, (int)y1 * 2 - 1, (int)x1 * 2, (int)y1 * 2, color2);
        GL11.glDisable((int)3042);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static final void drawLine(float x, float y, float x1, float y1, float width, int colour) {
        float red = (float)(colour >> 16 & 255) / 255.0f;
        float green = (float)(colour >> 8 & 255) / 255.0f;
        float blue = (float)(colour & 255) / 255.0f;
        float alpha = (float)(colour >> 24 & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static final void drawSmallHL(float x, float y, float x2, int colour) {
        Utils.drawRect(x, y, x2, y + 0.5f, colour);
    }

    public static final void drawSmallVL(float y, float x, float y2, int colour) {
        Utils.drawRect(x, y, x + 0.5f, y2, colour);
    }

    public static final String setSessionData(String user, String pass) {
        YggdrasilAuthenticationService authentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)authentication.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(user);
        auth.setPassword(pass);
        try {
            auth.logIn();
            Resilience.getInstance().getWrapper().getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId(), auth.getAuthenticatedToken());
            return "\u00a7bSuccess!";
        }
        catch (AuthenticationException e) {
            return "\u00a7cBad Login";
        }
    }

    public static final void drawItemTag(int x, int y, ItemStack item) {
        GL11.glPushMatrix();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)32826);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2929);
        try {
            Resilience.getInstance().getInvoker().renderItemIntoGUI(item, x, y);
            Resilience.getInstance().getInvoker().renderItemOverlayIntoGUI(item, x, y);
        }
        catch (Exception exception) {
            // empty catch block
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float x, float y, double radius, int colour) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        radius *= 2.0;
        x *= 2.0f;
        y *= 2.0f;
        float red = (float)(colour >> 16 & 255) / 255.0f;
        float green = (float)(colour >> 8 & 255) / 255.0f;
        float blue = (float)(colour & 255) / 255.0f;
        float alpha = (float)(colour >> 24 & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)2);
        int i = 0;
        while (i <= 360) {
            double x1 = Math.sin((double)i * 3.141592653589793 / 180.0) * radius;
            double y1 = Math.cos((double)i * 3.141592653589793 / 180.0) * radius;
            GL11.glVertex2d((double)((double)x + x1), (double)((double)y + y1));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static final float sqrt_double(double par0) {
        return (float)Math.sqrt(par0);
    }

    public static final double wrapAngleTo180_double(double par0) {
        if ((par0 %= 360.0) >= 180.0) {
            par0 -= 360.0;
        }
        if (par0 < -180.0) {
            par0 += 360.0;
        }
        return par0;
    }

    public static final void initDonators() {
        new Thread(){

            @Override
            public void run() {
                try {
                    String temp;
                    Donator.donatorList.clear();
                    URL url = new URL("http://resilience.krispdev.com/Rerererencedonatorsx789");
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((temp = in.readLine()) != null) {
                        String[] args = temp.split("BITCHEZBECRAYCRAY123WAYOVER30CHAR");
                        if (Float.parseFloat(args[2]) < 5.0f) continue;
                        Donator.donatorList.add(new Donator(args[0], args[1], Float.parseFloat(args[2]), args[3]));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static AxisAlignedBB getAABB(int x, int y, int z) {
        HookEntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
        double var8 = p.lastTickPosX + (p.posX - p.lastTickPosX);
        double var10 = p.lastTickPosY + (p.posY - p.lastTickPosY);
        double var12 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ);
        float var6 = 0.002f;
        Block block = Minecraft.getMinecraft().theWorld.getBlock(x, y, z);
        return block.getSelectedBoundingBoxFromPool(Minecraft.getMinecraft().theWorld, x, y, z).expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).getOffsetBoundingBox(- var8, - var10, - var12);
    }

    public static ByteBuffer getSides() {
        boxSides = BufferUtils.createByteBuffer((int)24);
        byte[] arrby = new byte[24];
        arrby[1] = 3;
        arrby[2] = 2;
        arrby[3] = 1;
        arrby[4] = 2;
        arrby[5] = 5;
        arrby[6] = 6;
        arrby[7] = 1;
        arrby[8] = 6;
        arrby[9] = 7;
        arrby[11] = 1;
        arrby[13] = 7;
        arrby[14] = 4;
        arrby[15] = 3;
        arrby[16] = 4;
        arrby[17] = 7;
        arrby[18] = 6;
        arrby[19] = 5;
        arrby[20] = 2;
        arrby[21] = 3;
        arrby[22] = 4;
        arrby[23] = 5;
        boxSides.put(arrby);
        boxSides.flip();
        cube = GL15.glGenBuffers();
        GL15.glBindBuffer((int)34962, (int)cube);
        GL15.glBufferData((int)34962, (FloatBuffer)Utils.getBox(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f), (int)35044);
        GL15.glBindBuffer((int)34962, (int)0);
        return boxSides;
    }

    public static FloatBuffer getBox(AxisAlignedBB bound) {
        return Utils.getBox((float)bound.minX, (float)bound.minY, (float)bound.minZ, (float)bound.maxX, (float)bound.maxY, (float)bound.maxZ);
    }

    public static FloatBuffer getBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        FloatBuffer vertices = BufferUtils.createFloatBuffer((int)24);
        vertices.put(new float[]{minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, minX, minY, maxZ});
        vertices.flip();
        return vertices;
    }

    public static final String sendGetRequest(String ur) {
        String url1 = ur.replaceAll(" ", "%20").replaceAll("#", "%23");
        try {
            URL url;
            String inputLine;
            URL obj = url = new URL(url1);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            returnString = response.toString();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
        if (returnString == null) {
            return "null";
        }
        return returnString;
    }

    public static final void setOnline(boolean flag) {
        boolean online = flag;
        System.out.println("sending with channel: " + Resilience.getInstance().getValues().userChannel);
        String result = Utils.sendGetRequest("http://resilience.krispdev.com/updateOnline.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&online=" + online + "&channel=" + Resilience.getInstance().getValues().userChannel);
        if (!flag) {
            String string = Utils.sendGetRequest("http://resilience.krispdev.com/updateStatus.php?ign=" + Resilience.getInstance().getInvoker().getSessionUsername() + "&password=" + Resilience.getInstance().getValues().onlinePassword + "&status=Logging in...");
        }
    }

    public static final String getSiteContent(String u) {
        try {
            String inputLine;
            URL url = new URL(u);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String finalResult = "";
            while ((inputLine = in.readLine()) != null) {
                finalResult = String.valueOf(finalResult) + inputLine;
            }
            in.close();
            return finalResult;
        }
        catch (Exception e) {
            return "ERR";
        }
    }

    public static enum State {
        REGISTER,
        UNREGISTER,
        UPDATE;
        

        private State(String string2, int n2) {
        }
    }

}

