/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.mco;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.mco.ExceptionMcoHttp;
import net.minecraft.client.mco.McoClientConfig;

public abstract class Request {
    protected HttpURLConnection field_148677_a;
    private boolean field_148676_c;
    protected String field_148675_b;
    private static final String __OBFID = "CL_00001159";

    public Request(String par1Str, int par2, int par3) {
        try {
            this.field_148675_b = par1Str;
            Proxy var4 = McoClientConfig.func_148685_a();
            this.field_148677_a = var4 != null ? (HttpURLConnection)new URL(par1Str).openConnection(var4) : (HttpURLConnection)new URL(par1Str).openConnection();
            this.field_148677_a.setConnectTimeout(par2);
            this.field_148677_a.setReadTimeout(par3);
        }
        catch (Exception var5) {
            throw new ExceptionMcoHttp("Failed URL: " + par1Str, var5);
        }
    }

    public void func_148665_a(String p_148665_1_, String p_148665_2_) {
        String var3 = this.field_148677_a.getRequestProperty("Cookie");
        if (var3 == null) {
            this.field_148677_a.setRequestProperty("Cookie", String.valueOf(p_148665_1_) + "=" + p_148665_2_);
        } else {
            this.field_148677_a.setRequestProperty("Cookie", String.valueOf(var3) + ";" + p_148665_1_ + "=" + p_148665_2_);
        }
    }

    public int func_148671_a() {
        try {
            this.func_148667_e();
            return this.field_148677_a.getResponseCode();
        }
        catch (Exception var2) {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
        }
    }

    public int func_148664_b() {
        String var1 = this.field_148677_a.getHeaderField("Retry-After");
        try {
            return Integer.valueOf(var1);
        }
        catch (Exception var3) {
            return 5;
        }
    }

    public String func_148659_d() {
        try {
            this.func_148667_e();
            String var1 = this.func_148671_a() >= 400 ? this.func_148660_a(this.field_148677_a.getErrorStream()) : this.func_148660_a(this.field_148677_a.getInputStream());
            this.func_148674_h();
            return var1;
        }
        catch (IOException var2) {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
        }
    }

    private String func_148660_a(InputStream p_148660_1_) throws IOException {
        if (p_148660_1_ == null) {
            return "";
        }
        StringBuilder var2 = new StringBuilder();
        int var3 = p_148660_1_.read();
        while (var3 != -1) {
            var2.append((char)var3);
            var3 = p_148660_1_.read();
        }
        return var2.toString();
    }

    private void func_148674_h() {
        byte[] var1 = new byte[1024];
        try {
            boolean var2 = false;
            InputStream var3 = this.field_148677_a.getInputStream();
            while (var3.read(var1) > 0) {
            }
            var3.close();
        }
        catch (Exception var6) {
            try {
                InputStream var3 = this.field_148677_a.getErrorStream();
                boolean var4 = false;
                if (var3 == null) {
                    return;
                }
                while (var3.read(var1) > 0) {
                }
                var3.close();
            }
            catch (IOException var4) {
                // empty catch block
            }
        }
    }

    protected Request func_148667_e() {
        if (!this.field_148676_c) {
            Request var1 = this.func_148662_f();
            this.field_148676_c = true;
            return var1;
        }
        return this;
    }

    protected abstract Request func_148662_f();

    public static Request func_148666_a(String p_148666_0_) {
        return new Get(p_148666_0_, 5000, 10000);
    }

    public static Request func_148670_a(String p_148670_0_, int p_148670_1_, int p_148670_2_) {
        return new Get(p_148670_0_, p_148670_1_, p_148670_2_);
    }

    public static Request func_148672_c(String p_148672_0_, String p_148672_1_) {
        return new Post(p_148672_0_, p_148672_1_.getBytes(), 5000, 10000);
    }

    public static Request func_148661_a(String p_148661_0_, String p_148661_1_, int p_148661_2_, int p_148661_3_) {
        return new Post(p_148661_0_, p_148661_1_.getBytes(), p_148661_2_, p_148661_3_);
    }

    public static Request func_148663_b(String p_148663_0_) {
        return new Delete(p_148663_0_, 5000, 10000);
    }

    public static Request func_148668_d(String p_148668_0_, String p_148668_1_) {
        return new Put(p_148668_0_, p_148668_1_.getBytes(), 5000, 10000);
    }

    public static Request func_148669_b(String p_148669_0_, String p_148669_1_, int p_148669_2_, int p_148669_3_) {
        return new Put(p_148669_0_, p_148669_1_.getBytes(), p_148669_2_, p_148669_3_);
    }

    public int func_148673_g() {
        String var1 = this.field_148677_a.getHeaderField("Error-Code");
        try {
            return Integer.valueOf(var1);
        }
        catch (Exception var3) {
            return -1;
        }
    }

    public static class Delete
    extends Request {
        private static final String __OBFID = "CL_00001160";

        public Delete(String par1Str, int par2, int par3) {
            super(par1Str, par2, par3);
        }

        @Override
        public Delete func_148662_f() {
            try {
                this.field_148677_a.setDoOutput(true);
                this.field_148677_a.setRequestMethod("DELETE");
                this.field_148677_a.connect();
                return this;
            }
            catch (Exception var2) {
                throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
            }
        }
    }

    public static class Get
    extends Request {
        private static final String __OBFID = "CL_00001161";

        public Get(String par1Str, int par2, int par3) {
            super(par1Str, par2, par3);
        }

        @Override
        public Get func_148662_f() {
            try {
                this.field_148677_a.setDoInput(true);
                this.field_148677_a.setDoOutput(true);
                this.field_148677_a.setUseCaches(false);
                this.field_148677_a.setRequestMethod("GET");
                return this;
            }
            catch (Exception var2) {
                throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
            }
        }
    }

    public static class Post
    extends Request {
        private byte[] field_148683_c;
        private static final String __OBFID = "CL_00001162";

        public Post(String par1Str, byte[] par2ArrayOfByte, int par3, int par4) {
            super(par1Str, par3, par4);
            this.field_148683_c = par2ArrayOfByte;
        }

        @Override
        public Post func_148662_f() {
            try {
                this.field_148677_a.setDoInput(true);
                this.field_148677_a.setDoOutput(true);
                this.field_148677_a.setUseCaches(false);
                this.field_148677_a.setRequestMethod("POST");
                OutputStream var1 = this.field_148677_a.getOutputStream();
                var1.write(this.field_148683_c);
                var1.flush();
                return this;
            }
            catch (Exception var2) {
                throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
            }
        }
    }

    public static class Put
    extends Request {
        private byte[] field_148681_c;
        private static final String __OBFID = "CL_00001163";

        public Put(String par1Str, byte[] par2ArrayOfByte, int par3, int par4) {
            super(par1Str, par3, par4);
            this.field_148681_c = par2ArrayOfByte;
        }

        @Override
        public Put func_148662_f() {
            try {
                this.field_148677_a.setDoOutput(true);
                this.field_148677_a.setDoInput(true);
                this.field_148677_a.setRequestMethod("PUT");
                OutputStream var1 = this.field_148677_a.getOutputStream();
                var1.write(this.field_148681_c);
                var1.flush();
                return this;
            }
            catch (Exception var2) {
                throw new ExceptionMcoHttp("Failed URL: " + this.field_148675_b, var2);
            }
        }
    }

}

