/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiScreenBook
extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation field_146466_f = new ResourceLocation("textures/gui/book.png");
    private final EntityPlayer field_146468_g;
    private final ItemStack field_146474_h;
    private final boolean field_146475_i;
    private boolean field_146481_r;
    private boolean field_146480_s;
    private int field_146479_t;
    private int field_146478_u = 192;
    private int field_146477_v = 192;
    private int field_146476_w = 1;
    private int field_146484_x;
    private NBTTagList field_146483_y;
    private String field_146482_z = "";
    private NextPageButton field_146470_A;
    private NextPageButton field_146471_B;
    private GuiButton field_146472_C;
    private GuiButton field_146465_D;
    private GuiButton field_146467_E;
    private GuiButton field_146469_F;
    private static final String __OBFID = "CL_00000744";

    public GuiScreenBook(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, boolean par3) {
        this.field_146468_g = par1EntityPlayer;
        this.field_146474_h = par2ItemStack;
        this.field_146475_i = par3;
        if (par2ItemStack.hasTagCompound()) {
            NBTTagCompound var4 = par2ItemStack.getTagCompound();
            this.field_146483_y = var4.getTagList("pages", 8);
            if (this.field_146483_y != null) {
                this.field_146483_y = (NBTTagList)this.field_146483_y.copy();
                this.field_146476_w = this.field_146483_y.tagCount();
                if (this.field_146476_w < 1) {
                    this.field_146476_w = 1;
                }
            }
        }
        if (this.field_146483_y == null && par3) {
            this.field_146483_y = new NBTTagList();
            this.field_146483_y.appendTag(new NBTTagString(""));
            this.field_146476_w = 1;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146479_t;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        if (this.field_146475_i) {
            this.field_146465_D = new GuiButton(3, this.width / 2 - 100, 4 + this.field_146477_v, 98, 20, I18n.format("book.signButton", new Object[0]));
            this.buttonList.add(this.field_146465_D);
            this.field_146472_C = new GuiButton(0, this.width / 2 + 2, 4 + this.field_146477_v, 98, 20, I18n.format("gui.done", new Object[0]));
            this.buttonList.add(this.field_146472_C);
            this.field_146467_E = new GuiButton(5, this.width / 2 - 100, 4 + this.field_146477_v, 98, 20, I18n.format("book.finalizeButton", new Object[0]));
            this.buttonList.add(this.field_146467_E);
            this.field_146469_F = new GuiButton(4, this.width / 2 + 2, 4 + this.field_146477_v, 98, 20, I18n.format("gui.cancel", new Object[0]));
            this.buttonList.add(this.field_146469_F);
        } else {
            this.field_146472_C = new GuiButton(0, this.width / 2 - 100, 4 + this.field_146477_v, 200, 20, I18n.format("gui.done", new Object[0]));
            this.buttonList.add(this.field_146472_C);
        }
        int var1 = (this.width - this.field_146478_u) / 2;
        int var2 = 2;
        this.field_146470_A = new NextPageButton(1, var1 + 120, var2 + 154, true);
        this.buttonList.add(this.field_146470_A);
        this.field_146471_B = new NextPageButton(2, var1 + 38, var2 + 154, false);
        this.buttonList.add(this.field_146471_B);
        this.func_146464_h();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    private void func_146464_h() {
        this.field_146470_A.field_146125_m = !this.field_146480_s && (this.field_146484_x < this.field_146476_w - 1 || this.field_146475_i);
        this.field_146471_B.field_146125_m = !this.field_146480_s && this.field_146484_x > 0;
        boolean bl = this.field_146472_C.field_146125_m = !this.field_146475_i || !this.field_146480_s;
        if (this.field_146475_i) {
            this.field_146465_D.field_146125_m = !this.field_146480_s;
            this.field_146469_F.field_146125_m = this.field_146480_s;
            this.field_146467_E.field_146125_m = this.field_146480_s;
            this.field_146467_E.enabled = this.field_146482_z.trim().length() > 0;
        }
    }

    private void func_146462_a(boolean p_146462_1_) {
        if (this.field_146475_i && this.field_146481_r && this.field_146483_y != null) {
            String var2;
            while (this.field_146483_y.tagCount() > 1) {
                var2 = this.field_146483_y.getStringTagAt(this.field_146483_y.tagCount() - 1);
                if (var2.length() != 0) break;
                this.field_146483_y.removeTag(this.field_146483_y.tagCount() - 1);
            }
            if (this.field_146474_h.hasTagCompound()) {
                NBTTagCompound var10 = this.field_146474_h.getTagCompound();
                var10.setTag("pages", this.field_146483_y);
            } else {
                this.field_146474_h.setTagInfo("pages", this.field_146483_y);
            }
            var2 = "MC|BEdit";
            if (p_146462_1_) {
                var2 = "MC|BSign";
                this.field_146474_h.setTagInfo("author", new NBTTagString(this.field_146468_g.getCommandSenderName()));
                this.field_146474_h.setTagInfo("title", new NBTTagString(this.field_146482_z.trim()));
                this.field_146474_h.func_150996_a(Items.written_book);
            }
            ByteBuf var3 = Unpooled.buffer();
            try {
                try {
                    new PacketBuffer(var3).writeItemStackToBuffer(this.field_146474_h);
                    this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
                }
                catch (Exception var8) {
                    logger.error("Couldn't send book info", (Throwable)var8);
                    var3.release();
                }
            }
            finally {
                var3.release();
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen(null);
                this.func_146462_a(false);
            } else if (p_146284_1_.id == 3 && this.field_146475_i) {
                this.field_146480_s = true;
            } else if (p_146284_1_.id == 1) {
                if (this.field_146484_x < this.field_146476_w - 1) {
                    ++this.field_146484_x;
                } else if (this.field_146475_i) {
                    this.func_146461_i();
                    if (this.field_146484_x < this.field_146476_w - 1) {
                        ++this.field_146484_x;
                    }
                }
            } else if (p_146284_1_.id == 2) {
                if (this.field_146484_x > 0) {
                    --this.field_146484_x;
                }
            } else if (p_146284_1_.id == 5 && this.field_146480_s) {
                this.func_146462_a(true);
                this.mc.displayGuiScreen(null);
            } else if (p_146284_1_.id == 4 && this.field_146480_s) {
                this.field_146480_s = false;
            }
            this.func_146464_h();
        }
    }

    private void func_146461_i() {
        if (this.field_146483_y != null && this.field_146483_y.tagCount() < 50) {
            this.field_146483_y.appendTag(new NBTTagString(""));
            ++this.field_146476_w;
            this.field_146481_r = true;
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);
        if (this.field_146475_i) {
            if (this.field_146480_s) {
                this.func_146460_c(par1, par2);
            } else {
                this.func_146463_b(par1, par2);
            }
        }
    }

    private void func_146463_b(char p_146463_1_, int p_146463_2_) {
        switch (p_146463_1_) {
            case '\u0016': {
                this.func_146459_b(GuiScreen.getClipboardString());
                return;
            }
        }
        switch (p_146463_2_) {
            case 14: {
                String var3 = this.func_146456_p();
                if (var3.length() > 0) {
                    this.func_146457_a(var3.substring(0, var3.length() - 1));
                }
                return;
            }
            case 28: 
            case 156: {
                this.func_146459_b("\n");
                return;
            }
        }
        if (ChatAllowedCharacters.isAllowedCharacter(p_146463_1_)) {
            this.func_146459_b(Character.toString(p_146463_1_));
        }
    }

    private void func_146460_c(char p_146460_1_, int p_146460_2_) {
        switch (p_146460_2_) {
            case 14: {
                if (!this.field_146482_z.isEmpty()) {
                    this.field_146482_z = this.field_146482_z.substring(0, this.field_146482_z.length() - 1);
                    this.func_146464_h();
                }
                return;
            }
            case 28: 
            case 156: {
                if (!this.field_146482_z.isEmpty()) {
                    this.func_146462_a(true);
                    this.mc.displayGuiScreen(null);
                }
                return;
            }
        }
        if (this.field_146482_z.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
            this.field_146482_z = String.valueOf(this.field_146482_z) + Character.toString(p_146460_1_);
            this.func_146464_h();
            this.field_146481_r = true;
        }
    }

    private String func_146456_p() {
        return this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.tagCount() ? this.field_146483_y.getStringTagAt(this.field_146484_x) : "";
    }

    private void func_146457_a(String p_146457_1_) {
        if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.tagCount()) {
            this.field_146483_y.func_150304_a(this.field_146484_x, new NBTTagString(p_146457_1_));
            this.field_146481_r = true;
        }
    }

    private void func_146459_b(String p_146459_1_) {
        String var2 = this.func_146456_p();
        String var3 = String.valueOf(var2) + p_146459_1_;
        int var4 = this.fontRendererObj.splitStringWidth(String.valueOf(var3) + (Object)((Object)EnumChatFormatting.BLACK) + "_", 118);
        if (var4 <= 118 && var3.length() < 256) {
            this.func_146457_a(var3);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_146466_f);
        int var4 = (this.width - this.field_146478_u) / 2;
        int var5 = 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146478_u, this.field_146477_v);
        if (this.field_146480_s) {
            String var6 = this.field_146482_z;
            if (this.field_146475_i) {
                var6 = this.field_146479_t / 6 % 2 == 0 ? String.valueOf(var6) + (Object)((Object)EnumChatFormatting.BLACK) + "_" : String.valueOf(var6) + (Object)((Object)EnumChatFormatting.GRAY) + "_";
            }
            String var7 = I18n.format("book.editTitle", new Object[0]);
            int var8 = this.fontRendererObj.getStringWidth(var7);
            this.fontRendererObj.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
            int var9 = this.fontRendererObj.getStringWidth(var6);
            this.fontRendererObj.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
            String var10 = I18n.format("book.byAuthor", this.field_146468_g.getCommandSenderName());
            int var11 = this.fontRendererObj.getStringWidth(var10);
            this.fontRendererObj.drawString((Object)((Object)EnumChatFormatting.DARK_GRAY) + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
            String var12 = I18n.format("book.finalizeWarning", new Object[0]);
            this.fontRendererObj.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
        } else {
            String var6 = I18n.format("book.pageIndicator", this.field_146484_x + 1, this.field_146476_w);
            String var7 = "";
            if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.tagCount()) {
                var7 = this.field_146483_y.getStringTagAt(this.field_146484_x);
            }
            if (this.field_146475_i) {
                var7 = this.fontRendererObj.getBidiFlag() ? String.valueOf(var7) + "_" : (this.field_146479_t / 6 % 2 == 0 ? String.valueOf(var7) + (Object)((Object)EnumChatFormatting.BLACK) + "_" : String.valueOf(var7) + (Object)((Object)EnumChatFormatting.GRAY) + "_");
            }
            int var8 = this.fontRendererObj.getStringWidth(var6);
            this.fontRendererObj.drawString(var6, var4 - var8 + this.field_146478_u - 44, var5 + 16, 0);
            this.fontRendererObj.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
        }
        super.drawScreen(par1, par2, par3);
    }

    static class NextPageButton
    extends GuiButton {
        private final boolean field_146151_o;
        private static final String __OBFID = "CL_00000745";

        public NextPageButton(int par1, int par2, int par3, boolean par4) {
            super(par1, par2, par3, 23, 13, "");
            this.field_146151_o = par4;
        }

        @Override
        public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
            if (this.field_146125_m) {
                boolean var4 = p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g;
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                p_146112_1_.getTextureManager().bindTexture(field_146466_f);
                int var5 = 0;
                int var6 = 192;
                if (var4) {
                    var5 += 23;
                }
                if (!this.field_146151_o) {
                    var6 += 13;
                }
                this.drawTexturedModalRect(this.field_146128_h, this.field_146129_i, var5, var6, 23, 13);
            }
        }
    }

}

