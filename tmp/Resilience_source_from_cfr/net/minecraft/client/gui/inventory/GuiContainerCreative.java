/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.inventory;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiContainerCreative
extends InventoryEffectRenderer {
    private static final ResourceLocation field_147061_u = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
    private static int field_147058_w = CreativeTabs.tabBlock.getTabIndex();
    private float field_147067_x;
    private boolean field_147066_y;
    private boolean field_147065_z;
    private GuiTextField field_147062_A;
    private List field_147063_B;
    private Slot field_147064_C;
    private boolean field_147057_D;
    private CreativeCrafting field_147059_E;
    private static final String __OBFID = "CL_00000752";

    public GuiContainerCreative(EntityPlayer par1EntityPlayer) {
        super(new ContainerCreative(par1EntityPlayer));
        par1EntityPlayer.openContainer = this.field_147002_h;
        this.field_146291_p = true;
        this.field_147000_g = 136;
        this.field_146999_f = 195;
    }

    @Override
    public void updateScreen() {
        if (!this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }

    @Override
    protected void func_146984_a(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_) {
        this.field_147057_D = true;
        boolean var5 = p_146984_4_ == 1;
        int n = p_146984_4_ = p_146984_2_ == -999 && p_146984_4_ == 0 ? 4 : p_146984_4_;
        if (p_146984_1_ == null && field_147058_w != CreativeTabs.tabInventory.getTabIndex() && p_146984_4_ != 5) {
            InventoryPlayer var11 = this.mc.thePlayer.inventory;
            if (var11.getItemStack() != null) {
                if (p_146984_3_ == 0) {
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(var11.getItemStack(), true);
                    this.mc.playerController.sendPacketDropItem(var11.getItemStack());
                    var11.setItemStack(null);
                }
                if (p_146984_3_ == 1) {
                    ItemStack var7 = var11.getItemStack().splitStack(1);
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(var7, true);
                    this.mc.playerController.sendPacketDropItem(var7);
                    if (var11.getItemStack().stackSize == 0) {
                        var11.setItemStack(null);
                    }
                }
            }
        } else if (p_146984_1_ == this.field_147064_C && var5) {
            int var10 = 0;
            while (var10 < this.mc.thePlayer.inventoryContainer.getInventory().size()) {
                this.mc.playerController.sendSlotPacket(null, var10);
                ++var10;
            }
        } else if (field_147058_w == CreativeTabs.tabInventory.getTabIndex()) {
            if (p_146984_1_ == this.field_147064_C) {
                this.mc.thePlayer.inventory.setItemStack(null);
            } else if (p_146984_4_ == 4 && p_146984_1_ != null && p_146984_1_.getHasStack()) {
                ItemStack var6 = p_146984_1_.decrStackSize(p_146984_3_ == 0 ? 1 : p_146984_1_.getStack().getMaxStackSize());
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(var6, true);
                this.mc.playerController.sendPacketDropItem(var6);
            } else if (p_146984_4_ == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
                this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
                this.mc.thePlayer.inventory.setItemStack(null);
            } else {
                this.mc.thePlayer.inventoryContainer.slotClick(p_146984_1_ == null ? p_146984_2_ : CreativeSlot.access$0((CreativeSlot)((CreativeSlot)p_146984_1_)).slotNumber, p_146984_3_, p_146984_4_, this.mc.thePlayer);
                this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
            }
        } else if (p_146984_4_ != 5 && p_146984_1_.inventory == field_147060_v) {
            InventoryPlayer var11 = this.mc.thePlayer.inventory;
            ItemStack var7 = var11.getItemStack();
            ItemStack var8 = p_146984_1_.getStack();
            if (p_146984_4_ == 2) {
                if (var8 != null && p_146984_3_ >= 0 && p_146984_3_ < 9) {
                    ItemStack var9 = var8.copy();
                    var9.stackSize = var9.getMaxStackSize();
                    this.mc.thePlayer.inventory.setInventorySlotContents(p_146984_3_, var9);
                    this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
                }
                return;
            }
            if (p_146984_4_ == 3) {
                if (var11.getItemStack() == null && p_146984_1_.getHasStack()) {
                    ItemStack var9 = p_146984_1_.getStack().copy();
                    var9.stackSize = var9.getMaxStackSize();
                    var11.setItemStack(var9);
                }
                return;
            }
            if (p_146984_4_ == 4) {
                if (var8 != null) {
                    ItemStack var9 = var8.copy();
                    var9.stackSize = p_146984_3_ == 0 ? 1 : var9.getMaxStackSize();
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(var9, true);
                    this.mc.playerController.sendPacketDropItem(var9);
                }
                return;
            }
            if (var7 != null && var8 != null && var7.isItemEqual(var8)) {
                if (p_146984_3_ == 0) {
                    if (var5) {
                        var7.stackSize = var7.getMaxStackSize();
                    } else if (var7.stackSize < var7.getMaxStackSize()) {
                        ++var7.stackSize;
                    }
                } else if (var7.stackSize <= 1) {
                    var11.setItemStack(null);
                } else {
                    --var7.stackSize;
                }
            } else if (var8 != null && var7 == null) {
                var11.setItemStack(ItemStack.copyItemStack(var8));
                var7 = var11.getItemStack();
                if (var5) {
                    var7.stackSize = var7.getMaxStackSize();
                }
            } else {
                var11.setItemStack(null);
            }
        } else {
            this.field_147002_h.slotClick(p_146984_1_ == null ? p_146984_2_ : p_146984_1_.slotNumber, p_146984_3_, p_146984_4_, this.mc.thePlayer);
            if (Container.func_94532_c(p_146984_3_) == 2) {
                int var10 = 0;
                while (var10 < 9) {
                    this.mc.playerController.sendSlotPacket(this.field_147002_h.getSlot(45 + var10).getStack(), 36 + var10);
                    ++var10;
                }
            } else if (p_146984_1_ != null) {
                ItemStack var6 = this.field_147002_h.getSlot(p_146984_1_.slotNumber).getStack();
                this.mc.playerController.sendSlotPacket(var6, p_146984_1_.slotNumber - this.field_147002_h.inventorySlots.size() + 9 + 36);
            }
        }
    }

    @Override
    public void initGui() {
        if (this.mc.playerController.isInCreativeMode()) {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents((boolean)true);
            this.field_147062_A = new GuiTextField(this.fontRendererObj, this.field_147003_i + 82, this.field_147009_r + 6, 89, this.fontRendererObj.FONT_HEIGHT);
            this.field_147062_A.func_146203_f(15);
            this.field_147062_A.func_146185_a(false);
            this.field_147062_A.func_146189_e(false);
            this.field_147062_A.func_146193_g(16777215);
            int var1 = field_147058_w;
            field_147058_w = -1;
            this.func_147050_b(CreativeTabs.creativeTabArray[var1]);
            this.field_147059_E = new CreativeCrafting(this.mc);
            this.mc.thePlayer.inventoryContainer.addCraftingToCrafters(this.field_147059_E);
        } else {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
            this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
        }
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (field_147058_w != CreativeTabs.tabAllSearch.getTabIndex()) {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
                this.func_147050_b(CreativeTabs.tabAllSearch);
            } else {
                super.keyTyped(par1, par2);
            }
        } else {
            if (this.field_147057_D) {
                this.field_147057_D = false;
                this.field_147062_A.setText("");
            }
            if (!this.func_146983_a(par2)) {
                if (this.field_147062_A.textboxKeyTyped(par1, par2)) {
                    this.func_147053_i();
                } else {
                    super.keyTyped(par1, par2);
                }
            }
        }
    }

    private void func_147053_i() {
        ContainerCreative var1 = (ContainerCreative)this.field_147002_h;
        var1.field_148330_a.clear();
        for (Item var3 : Item.itemRegistry) {
            if (var3 == null || var3.getCreativeTab() == null) continue;
            var3.getSubItems(var3, null, var1.field_148330_a);
        }
        Enchantment[] var8 = Enchantment.enchantmentsList;
        int var9 = var8.length;
        int var4 = 0;
        while (var4 < var9) {
            Enchantment var5 = var8[var4];
            if (var5 != null && var5.type != null) {
                Items.enchanted_book.func_92113_a(var5, var1.field_148330_a);
            }
            ++var4;
        }
        Iterator var2 = var1.field_148330_a.iterator();
        String var10 = this.field_147062_A.getText().toLowerCase();
        while (var2.hasNext()) {
            ItemStack var11 = (ItemStack)var2.next();
            boolean var12 = false;
            for (String var7 : var11.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips)) {
                if (!var7.toLowerCase().contains(var10)) continue;
                var12 = true;
                break;
            }
            if (var12) continue;
            var2.remove();
        }
        this.field_147067_x = 0.0f;
        var1.func_148329_a(0.0f);
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        CreativeTabs var3 = CreativeTabs.creativeTabArray[field_147058_w];
        if (var3.drawInForegroundOfTab()) {
            GL11.glDisable((int)3042);
            this.fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel(), new Object[0]), 8.0f, 6.0f, 4210752);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int var4 = par1 - this.field_147003_i;
            int var5 = par2 - this.field_147009_r;
            CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
            int var7 = var6.length;
            int var8 = 0;
            while (var8 < var7) {
                CreativeTabs var9 = var6[var8];
                if (this.func_147049_a(var9, var4, var5)) {
                    return;
                }
                ++var8;
            }
        }
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        if (p_146286_3_ == 0) {
            int var4 = p_146286_1_ - this.field_147003_i;
            int var5 = p_146286_2_ - this.field_147009_r;
            CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
            int var7 = var6.length;
            int var8 = 0;
            while (var8 < var7) {
                CreativeTabs var9 = var6[var8];
                if (this.func_147049_a(var9, var4, var5)) {
                    this.func_147050_b(var9);
                    return;
                }
                ++var8;
            }
        }
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    private boolean func_147055_p() {
        if (field_147058_w != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[field_147058_w].shouldHidePlayerInventory() && ((ContainerCreative)this.field_147002_h).func_148328_e()) {
            return true;
        }
        return false;
    }

    private void func_147050_b(CreativeTabs p_147050_1_) {
        int var2 = field_147058_w;
        field_147058_w = p_147050_1_.getTabIndex();
        ContainerCreative var3 = (ContainerCreative)this.field_147002_h;
        this.field_147008_s.clear();
        var3.field_148330_a.clear();
        p_147050_1_.displayAllReleventItems(var3.field_148330_a);
        if (p_147050_1_ == CreativeTabs.tabInventory) {
            Container var4 = this.mc.thePlayer.inventoryContainer;
            if (this.field_147063_B == null) {
                this.field_147063_B = var3.inventorySlots;
            }
            var3.inventorySlots = new ArrayList();
            int var5 = 0;
            while (var5 < var4.inventorySlots.size()) {
                int var9;
                int var8;
                int var7;
                CreativeSlot var6 = new CreativeSlot((Slot)var4.inventorySlots.get(var5), var5);
                var3.inventorySlots.add(var6);
                if (var5 >= 5 && var5 < 9) {
                    var7 = var5 - 5;
                    var8 = var7 / 2;
                    var9 = var7 % 2;
                    var6.xDisplayPosition = 9 + var8 * 54;
                    var6.yDisplayPosition = 6 + var9 * 27;
                } else if (var5 >= 0 && var5 < 5) {
                    var6.yDisplayPosition = -2000;
                    var6.xDisplayPosition = -2000;
                } else if (var5 < var4.inventorySlots.size()) {
                    var7 = var5 - 9;
                    var8 = var7 % 9;
                    var9 = var7 / 9;
                    var6.xDisplayPosition = 9 + var8 * 18;
                    var6.yDisplayPosition = var5 >= 36 ? 112 : 54 + var9 * 18;
                }
                ++var5;
            }
            this.field_147064_C = new Slot(field_147060_v, 0, 173, 112);
            var3.inventorySlots.add(this.field_147064_C);
        } else if (var2 == CreativeTabs.tabInventory.getTabIndex()) {
            var3.inventorySlots = this.field_147063_B;
            this.field_147063_B = null;
        }
        if (this.field_147062_A != null) {
            if (p_147050_1_ == CreativeTabs.tabAllSearch) {
                this.field_147062_A.func_146189_e(true);
                this.field_147062_A.func_146205_d(false);
                this.field_147062_A.setFocused(true);
                this.field_147062_A.setText("");
                this.func_147053_i();
            } else {
                this.field_147062_A.func_146189_e(false);
                this.field_147062_A.func_146205_d(true);
                this.field_147062_A.setFocused(false);
            }
        }
        this.field_147067_x = 0.0f;
        var3.func_148329_a(0.0f);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0 && this.func_147055_p()) {
            int var2 = ((ContainerCreative)this.field_147002_h).field_148330_a.size() / 9 - 5 + 1;
            if (var1 > 0) {
                var1 = 1;
            }
            if (var1 < 0) {
                var1 = -1;
            }
            this.field_147067_x = (float)((double)this.field_147067_x - (double)var1 / (double)var2);
            if (this.field_147067_x < 0.0f) {
                this.field_147067_x = 0.0f;
            }
            if (this.field_147067_x > 1.0f) {
                this.field_147067_x = 1.0f;
            }
            ((ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        boolean var4 = Mouse.isButtonDown((int)0);
        int var5 = this.field_147003_i;
        int var6 = this.field_147009_r;
        int var7 = var5 + 175;
        int var8 = var6 + 18;
        int var9 = var7 + 14;
        int var10 = var8 + 112;
        if (!this.field_147065_z && var4 && par1 >= var7 && par2 >= var8 && par1 < var9 && par2 < var10) {
            this.field_147066_y = this.func_147055_p();
        }
        if (!var4) {
            this.field_147066_y = false;
        }
        this.field_147065_z = var4;
        if (this.field_147066_y) {
            this.field_147067_x = ((float)(par2 - var8) - 7.5f) / ((float)(var10 - var8) - 15.0f);
            if (this.field_147067_x < 0.0f) {
                this.field_147067_x = 0.0f;
            }
            if (this.field_147067_x > 1.0f) {
                this.field_147067_x = 1.0f;
            }
            ((ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
        }
        super.drawScreen(par1, par2, par3);
        CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
        int var12 = var11.length;
        int var13 = 0;
        while (var13 < var12) {
            CreativeTabs var14 = var11[var13];
            if (this.func_147052_b(var14, par1, par2)) break;
            ++var13;
        }
        if (this.field_147064_C != null && field_147058_w == CreativeTabs.tabInventory.getTabIndex() && this.func_146978_c(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, par1, par2)) {
            this.func_146279_a(I18n.format("inventory.binSlot", new Object[0]), par1, par2);
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)2896);
    }

    @Override
    protected void func_146285_a(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_) {
        if (field_147058_w == CreativeTabs.tabAllSearch.getTabIndex()) {
            Map var6;
            List var4 = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
            CreativeTabs var5 = p_146285_1_.getItem().getCreativeTab();
            if (var5 == null && p_146285_1_.getItem() == Items.enchanted_book && (var6 = EnchantmentHelper.getEnchantments(p_146285_1_)).size() == 1) {
                Enchantment var7 = Enchantment.enchantmentsList[(Integer)var6.keySet().iterator().next()];
                CreativeTabs[] var8 = CreativeTabs.creativeTabArray;
                int var9 = var8.length;
                int var10 = 0;
                while (var10 < var9) {
                    CreativeTabs var11 = var8[var10];
                    if (var11.func_111226_a(var7.type)) {
                        var5 = var11;
                        break;
                    }
                    ++var10;
                }
            }
            if (var5 != null) {
                var4.add(1, (Object)((Object)EnumChatFormatting.BOLD) + (Object)((Object)EnumChatFormatting.BLUE) + I18n.format(var5.getTranslatedTabLabel(), new Object[0]));
            }
            int var12 = 0;
            while (var12 < var4.size()) {
                if (var12 == 0) {
                    var4.set(var12, (Object)((Object)p_146285_1_.getRarity().rarityColor) + (String)var4.get(var12));
                } else {
                    var4.set(var12, (Object)((Object)EnumChatFormatting.GRAY) + (String)var4.get(var12));
                }
                ++var12;
            }
            this.func_146283_a(var4, p_146285_2_, p_146285_3_);
        } else {
            super.func_146285_a(p_146285_1_, p_146285_2_, p_146285_3_);
        }
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.enableGUIStandardItemLighting();
        CreativeTabs var4 = CreativeTabs.creativeTabArray[field_147058_w];
        CreativeTabs[] var5 = CreativeTabs.creativeTabArray;
        int var6 = var5.length;
        int var7 = 0;
        while (var7 < var6) {
            CreativeTabs var8 = var5[var7];
            this.mc.getTextureManager().bindTexture(field_147061_u);
            if (var8.getTabIndex() != field_147058_w) {
                this.func_147051_a(var8);
            }
            ++var7;
        }
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
        this.drawTexturedModalRect(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        this.field_147062_A.drawTextBox();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int var9 = this.field_147003_i + 175;
        var6 = this.field_147009_r + 18;
        var7 = var6 + 112;
        this.mc.getTextureManager().bindTexture(field_147061_u);
        if (var4.shouldHidePlayerInventory()) {
            this.drawTexturedModalRect(var9, var6 + (int)((float)(var7 - var6 - 17) * this.field_147067_x), 232 + (this.func_147055_p() ? 0 : 12), 0, 12, 15);
        }
        this.func_147051_a(var4);
        if (var4 == CreativeTabs.tabInventory) {
            GuiInventory.func_147046_a(this.field_147003_i + 43, this.field_147009_r + 45, 20, this.field_147003_i + 43 - p_146976_2_, this.field_147009_r + 45 - 30 - p_146976_3_, this.mc.thePlayer);
        }
    }

    protected boolean func_147049_a(CreativeTabs p_147049_1_, int p_147049_2_, int p_147049_3_) {
        int var4 = p_147049_1_.getTabColumn();
        int var5 = 28 * var4;
        int var6 = 0;
        if (var4 == 5) {
            var5 = this.field_146999_f - 28 + 2;
        } else if (var4 > 0) {
            var5 += var4;
        }
        int var7 = p_147049_1_.isTabInFirstRow() ? var6 - 32 : var6 + this.field_147000_g;
        if (p_147049_2_ >= var5 && p_147049_2_ <= var5 + 28 && p_147049_3_ >= var7 && p_147049_3_ <= var7 + 32) {
            return true;
        }
        return false;
    }

    protected boolean func_147052_b(CreativeTabs p_147052_1_, int p_147052_2_, int p_147052_3_) {
        int var4 = p_147052_1_.getTabColumn();
        int var5 = 28 * var4;
        int var6 = 0;
        if (var4 == 5) {
            var5 = this.field_146999_f - 28 + 2;
        } else if (var4 > 0) {
            var5 += var4;
        }
        int var7 = p_147052_1_.isTabInFirstRow() ? var6 - 32 : var6 + this.field_147000_g;
        if (this.func_146978_c(var5 + 3, var7 + 3, 23, 27, p_147052_2_, p_147052_3_)) {
            this.func_146279_a(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_, p_147052_3_);
            return true;
        }
        return false;
    }

    protected void func_147051_a(CreativeTabs p_147051_1_) {
        boolean var2 = p_147051_1_.getTabIndex() == field_147058_w;
        boolean var3 = p_147051_1_.isTabInFirstRow();
        int var4 = p_147051_1_.getTabColumn();
        int var5 = var4 * 28;
        int var6 = 0;
        int var7 = this.field_147003_i + 28 * var4;
        int var8 = this.field_147009_r;
        int var9 = 32;
        if (var2) {
            var6 += 32;
        }
        if (var4 == 5) {
            var7 = this.field_147003_i + this.field_146999_f - 28;
        } else if (var4 > 0) {
            var7 += var4;
        }
        if (var3) {
            var8 -= 28;
        } else {
            var6 += 64;
            var8 += this.field_147000_g - 4;
        }
        GL11.glDisable((int)2896);
        this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
        this.zLevel = 100.0f;
        GuiContainerCreative.itemRender.zLevel = 100.0f;
        GL11.glEnable((int)2896);
        GL11.glEnable((int)32826);
        ItemStack var10 = p_147051_1_.getIconItemStack();
        itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var7 += 6, var8 += 8 + (var3 ? 1 : -1));
        itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var7, var8);
        GL11.glDisable((int)2896);
        GuiContainerCreative.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
        }
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
        }
    }

    public int func_147056_g() {
        return field_147058_w;
    }

    static class ContainerCreative
    extends Container {
        public List field_148330_a = new ArrayList();
        private static final String __OBFID = "CL_00000753";

        public ContainerCreative(EntityPlayer par1EntityPlayer) {
            InventoryPlayer var2 = par1EntityPlayer.inventory;
            int var3 = 0;
            while (var3 < 5) {
                int var4 = 0;
                while (var4 < 9) {
                    this.addSlotToContainer(new Slot(field_147060_v, var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
                    ++var4;
                }
                ++var3;
            }
            var3 = 0;
            while (var3 < 9) {
                this.addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
                ++var3;
            }
            this.func_148329_a(0.0f);
        }

        @Override
        public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
            return true;
        }

        public void func_148329_a(float p_148329_1_) {
            int var2 = this.field_148330_a.size() / 9 - 5 + 1;
            int var3 = (int)((double)(p_148329_1_ * (float)var2) + 0.5);
            if (var3 < 0) {
                var3 = 0;
            }
            int var4 = 0;
            while (var4 < 5) {
                int var5 = 0;
                while (var5 < 9) {
                    int var6 = var5 + (var4 + var3) * 9;
                    if (var6 >= 0 && var6 < this.field_148330_a.size()) {
                        field_147060_v.setInventorySlotContents(var5 + var4 * 9, (ItemStack)this.field_148330_a.get(var6));
                    } else {
                        field_147060_v.setInventorySlotContents(var5 + var4 * 9, null);
                    }
                    ++var5;
                }
                ++var4;
            }
        }

        public boolean func_148328_e() {
            if (this.field_148330_a.size() > 45) {
                return true;
            }
            return false;
        }

        @Override
        protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer) {
        }

        @Override
        public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
            Slot var3;
            if (par2 >= this.inventorySlots.size() - 9 && par2 < this.inventorySlots.size() && (var3 = (Slot)this.inventorySlots.get(par2)) != null && var3.getHasStack()) {
                var3.putStack(null);
            }
            return null;
        }

        @Override
        public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
            if (par2Slot.yDisplayPosition > 90) {
                return true;
            }
            return false;
        }

        @Override
        public boolean canDragIntoSlot(Slot par1Slot) {
            if (!(par1Slot.inventory instanceof InventoryPlayer || par1Slot.yDisplayPosition > 90 && par1Slot.xDisplayPosition <= 162)) {
                return false;
            }
            return true;
        }
    }

    class CreativeSlot
    extends Slot {
        private final Slot field_148332_b;
        private static final String __OBFID = "CL_00000754";

        public CreativeSlot(Slot par2Slot, int par3) {
            super(par2Slot.inventory, par3, 0, 0);
            this.field_148332_b = par2Slot;
        }

        @Override
        public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
            this.field_148332_b.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
        }

        @Override
        public boolean isItemValid(ItemStack par1ItemStack) {
            return this.field_148332_b.isItemValid(par1ItemStack);
        }

        @Override
        public ItemStack getStack() {
            return this.field_148332_b.getStack();
        }

        @Override
        public boolean getHasStack() {
            return this.field_148332_b.getHasStack();
        }

        @Override
        public void putStack(ItemStack par1ItemStack) {
            this.field_148332_b.putStack(par1ItemStack);
        }

        @Override
        public void onSlotChanged() {
            this.field_148332_b.onSlotChanged();
        }

        @Override
        public int getSlotStackLimit() {
            return this.field_148332_b.getSlotStackLimit();
        }

        @Override
        public IIcon getBackgroundIconIndex() {
            return this.field_148332_b.getBackgroundIconIndex();
        }

        @Override
        public ItemStack decrStackSize(int par1) {
            return this.field_148332_b.decrStackSize(par1);
        }

        @Override
        public boolean isSlotInInventory(IInventory par1IInventory, int par2) {
            return this.field_148332_b.isSlotInInventory(par1IInventory, par2);
        }

        static /* synthetic */ Slot access$0(CreativeSlot creativeSlot) {
            return creativeSlot.field_148332_b;
        }
    }

}

