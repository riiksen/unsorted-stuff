/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package com.krispdev.resilience.wrappers;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.hooks.HookGuiIngame;
import com.krispdev.resilience.wrappers.Wrapper;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MethodInvoker {
    private RenderItem renderItem = new RenderItem();
    private Wrapper wrapper = Resilience.getInstance().getWrapper();
    private String entityLivingBaseLoc = "net.minecraft.entity.EntityLivingBase";

    public void sendChatMessage(String msg) {
        this.wrapper.getPlayer().sendChatMessage(msg);
    }

    public void addChatMessage(String str) {
        ChatComponentText chat = new ChatComponentText(str);
        if (str != null) {
            this.wrapper.getMinecraft().ingameGUI.getChatGUI().func_146227_a(chat);
        }
    }

    public float getRotationYaw() {
        return this.wrapper.getPlayer().rotationYaw;
    }

    public float getRotationPitch() {
        return this.wrapper.getPlayer().rotationPitch;
    }

    public void setRotationYaw(float yaw) {
        this.wrapper.getPlayer().rotationYaw = yaw;
    }

    public void setRotationPitch(float pitch) {
        this.wrapper.getPlayer().rotationPitch = pitch;
    }

    public void setSprinting(boolean sprinting) {
        this.wrapper.getPlayer().setSprinting(sprinting);
    }

    public boolean isOnLadder() {
        return this.wrapper.getPlayer().isOnLadder();
    }

    public float moveForward() {
        return this.wrapper.getPlayer().moveForward;
    }

    public boolean isCollidedHorizontally() {
        return this.wrapper.getPlayer().isCollidedHorizontally;
    }

    public void setMotionX(double x) {
        this.wrapper.getPlayer().motionX = x;
    }

    public void setMotionY(double y) {
        this.wrapper.getPlayer().motionY = y;
    }

    public void setMotionZ(double z) {
        this.wrapper.getPlayer().motionZ = z;
    }

    public double getMotionX() {
        return this.wrapper.getPlayer().motionX;
    }

    public double getMotionY() {
        return this.wrapper.getPlayer().motionY;
    }

    public double getMotionZ() {
        return this.wrapper.getPlayer().motionZ;
    }

    public void setLandMovementFactor(float newFactor) {
        try {
            Class elb = Class.forName(this.entityLivingBaseLoc);
            Field landMovement = elb.getDeclaredField("landMovementFactor");
            landMovement.setAccessible(true);
            landMovement.set(this.wrapper.getPlayer(), Float.valueOf(newFactor));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJumpMovementFactor(float newFactor) {
        try {
            Class elb = Class.forName(this.entityLivingBaseLoc);
            Field landMovement = elb.getDeclaredField("jumpMovementFactor");
            landMovement.setAccessible(true);
            landMovement.set(this.wrapper.getPlayer(), Float.valueOf(newFactor));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getGammaSetting() {
        return this.wrapper.getGameSettings().gammaSetting;
    }

    public void setGammaSetting(float newSetting) {
        this.wrapper.getGameSettings().gammaSetting = newSetting;
    }

    public int getJumpCode() {
        return this.wrapper.getGameSettings().keyBindJump.getKeyCode();
    }

    public int getForwardCode() {
        return this.wrapper.getGameSettings().keyBindForward.getKeyCode();
    }

    public void setJumpKeyPressed(boolean pressed) {
        this.wrapper.getGameSettings().keyBindJump.pressed = pressed;
    }

    public void setForwardKeyPressed(boolean pressed) {
        this.wrapper.getGameSettings().keyBindForward.pressed = pressed;
    }

    public void setUseItemKeyPressed(boolean pressed) {
        this.wrapper.getGameSettings().keyBindUseItem.pressed = pressed;
    }

    public int getSneakCode() {
        return this.wrapper.getGameSettings().keyBindSneak.getKeyCode();
    }

    public synchronized void displayScreen(GuiScreen screen) {
        this.wrapper.getMinecraft().displayGuiScreen(screen);
    }

    public List getEntityList() {
        return this.wrapper.getWorld().loadedEntityList;
    }

    public float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    public boolean isEntityDead(Entity e) {
        return e.isDead;
    }

    public boolean canEntityBeSeen(Entity e) {
        return this.wrapper.getPlayer().canEntityBeSeen(e);
    }

    public void attackEntity(Entity e) {
        this.wrapper.getPlayerController().attackEntity(this.wrapper.getPlayer(), e);
    }

    public void swingItem() {
        this.wrapper.getPlayer().swingItem();
    }

    public float getEyeHeight() {
        return this.wrapper.getPlayer().getEyeHeight();
    }

    public float getEyeHeight(Entity e) {
        return e.getEyeHeight();
    }

    public double getPosX() {
        return this.wrapper.getPlayer().posX;
    }

    public double getPosY() {
        return this.wrapper.getPlayer().posY;
    }

    public double getPosZ() {
        return this.wrapper.getPlayer().posZ;
    }

    public double getPosX(Entity e) {
        return e.posX;
    }

    public double getPosY(Entity e) {
        return e.posY;
    }

    public double getPosZ(Entity e) {
        return e.posZ;
    }

    public void setInvSlot(int slot) {
        this.wrapper.getPlayer().inventory.currentItem = slot;
    }

    public int getCurInvSlot() {
        return this.wrapper.getPlayer().inventory.currentItem;
    }

    public ItemStack getCurrentItem() {
        return this.wrapper.getPlayer().getCurrentEquippedItem();
    }

    public ItemStack getItemAtSlot(int slot) {
        return this.wrapper.getPlayer().inventoryContainer.getSlot(slot).getStack();
    }

    public ItemStack getItemAtSlotHotbar(int slot) {
        return this.wrapper.getPlayer().inventory.getStackInSlot(slot);
    }

    public int getIdFromItem(Item item) {
        return Item.getIdFromItem(item);
    }

    public void clickWindow(int slot, int mode, int button, EntityPlayer player) {
        this.wrapper.getPlayerController().windowClick(player.inventoryContainer.windowId, slot, button, mode, player);
    }

    public void clickWindow(int id, int slot, int mode, int button, EntityPlayer player) {
        this.wrapper.getPlayerController().windowClick(id, slot, button, mode, player);
    }

    public void sendUseItem(ItemStack itemStack, EntityPlayer player) {
        this.wrapper.getPlayerController().sendUseItem(player, this.wrapper.getWorld(), itemStack);
    }

    public Item getItemById(int id) {
        return Item.getItemById(id);
    }

    public void dropItemStack(int slot) {
        int i = 0;
        while (i < this.wrapper.getPlayer().inventory.getStackInSlot((int)slot).stackSize) {
            this.wrapper.getPlayer().dropOneItem(false);
            ++i;
        }
    }

    public int getPacketVelocityEntityId(S12PacketEntityVelocity p) {
        return p.func_149412_c();
    }

    public Entity getEntityById(int id) {
        return this.wrapper.getWorld().getEntityByID(id);
    }

    public int getXMovePacketVel(S12PacketEntityVelocity p) {
        return p.func_149411_d();
    }

    public int getYMovePacketVel(S12PacketEntityVelocity p) {
        return p.func_149410_e();
    }

    public int getZMovePacketVel(S12PacketEntityVelocity p) {
        return p.func_149409_f();
    }

    public void rightClick() {
        this.wrapper.getMinecraft().func_147121_ag();
    }

    public void leftClick() {
        this.wrapper.getMinecraft().func_147116_af();
    }

    public void setKeyBindAttackPressed(boolean flag) {
        this.wrapper.getGameSettings().keyBindAttack.pressed = flag;
    }

    public MovingObjectPosition getObjectMouseOver() {
        return this.wrapper.getMinecraft().objectMouseOver;
    }

    public Block getBlock(int x, int y, int z) {
        return this.wrapper.getWorld().getBlock(x, y, z);
    }

    public float getStrVsBlock(ItemStack item, Block block) {
        return item.func_150997_a(block);
    }

    public void useItemRightClick(ItemStack item) {
        item.useItemRightClick(this.wrapper.getWorld(), this.wrapper.getPlayer());
    }

    public ItemStack[] getArmourInventory() {
        return this.wrapper.getPlayer().inventory.armorInventory;
    }

    public void enableStandardItemLighting() {
        RenderHelper.enableStandardItemLighting();
    }

    public void disableStandardItemLighting() {
        RenderHelper.disableStandardItemLighting();
    }

    public void renderItemIntoGUI(ItemStack is, int x, int y) {
        this.renderItem.renderItemIntoGUI(this.wrapper.getFontRenderer(), this.wrapper.getRenderEngine(), is, x, y);
    }

    public void renderItemOverlayIntoGUI(ItemStack is, int x, int y) {
        this.renderItem.renderItemOverlayIntoGUI(this.wrapper.getFontRenderer(), this.wrapper.getRenderEngine(), is, x, y);
    }

    public String stripControlCodes(String s) {
        return StringUtils.stripControlCodes(s);
    }

    public String getPlayerName(EntityPlayer player) {
        return player.getCommandSenderName();
    }

    public String getSessionUsername() {
        return this.wrapper.getSession().getUsername();
    }

    public double getBoundboxMinY(Entity e) {
        return e.boundingBox.minY;
    }

    public double getBoundboxMaxY(Entity e) {
        return e.boundingBox.maxY;
    }

    public double getBoundboxMinX(Entity e) {
        return e.boundingBox.minX;
    }

    public double getBoundboxMaxX(Entity e) {
        return e.boundingBox.maxX;
    }

    public double getBoundboxMinZ(Entity e) {
        return e.boundingBox.minZ;
    }

    public double getBoundboxMaxZ(Entity e) {
        return e.boundingBox.maxZ;
    }

    public int getDisplayHeight() {
        return this.wrapper.getMinecraft().displayHeight;
    }

    public int getDisplayWidth() {
        return this.wrapper.getMinecraft().displayWidth;
    }

    public GuiScreen getCurrentScreen() {
        return this.wrapper.getMinecraft().currentScreen;
    }

    public void setupOverlayRendering() {
        this.wrapper.getEntityRenderer().setupOverlayRendering();
    }

    public int getScaledWidth(ScaledResolution sr) {
        return sr.getScaledWidth();
    }

    public int getScaledHeight(ScaledResolution sr) {
        return sr.getScaledHeight();
    }

    public void setEntityLight(boolean state) {
        if (state) {
            this.wrapper.getEntityRenderer().enableLightmap(1.0);
        } else {
            this.wrapper.getEntityRenderer().disableLightmap(1.0);
        }
    }

    public ServerData getCurrentServerData() {
        return this.wrapper.getMinecraft().currentServerData;
    }

    public boolean isInCreativeMode() {
        return this.wrapper.getPlayerController().isInCreativeMode();
    }

    public void setStackDisplayName(ItemStack is, String s) {
        is.setStackDisplayName(s);
    }

    public String getItemDisplayName(ItemStack is) {
        return is.getDisplayName();
    }

    public void sendPacket(Packet p) {
        this.wrapper.getPlayer().sendQueue.addToSendQueue(p);
    }

    public Enchantment[] getEnchantList() {
        return Enchantment.enchantmentsList;
    }

    public void addEnchantment(ItemStack is, Enchantment e, int level) {
        is.addEnchantment(e, 127);
    }

    public double getLastTickPosX(Entity e) {
        return e.lastTickPosX;
    }

    public double getLastTickPosY(Entity e) {
        return e.lastTickPosY;
    }

    public double getLastTickPosZ(Entity e) {
        return e.lastTickPosZ;
    }

    public float getEntityHeight(Entity e) {
        return e.height;
    }

    public double getRenderPosX() {
        return RenderManager.renderPosX;
    }

    public double getRenderPosY() {
        return RenderManager.renderPosY;
    }

    public double getRenderPosZ() {
        return RenderManager.renderPosZ;
    }

    public float getPlayerViewX() {
        return RenderManager.instance.playerViewX;
    }

    public float getPlayerViewY() {
        return RenderManager.instance.playerViewY;
    }

    public void loadRenderers() {
        if (this.wrapper.getMinecraft().renderGlobal != null) {
            this.wrapper.getMinecraft().renderGlobal.loadRenderers();
        }
    }

    public void setSmoothLighting(int mode) {
        this.wrapper.getGameSettings().ambientOcclusion = mode;
    }

    public int getSmoothLighting() {
        return this.wrapper.getGameSettings() == null ? 2 : this.wrapper.getGameSettings().ambientOcclusion;
    }

    public int getIdFromBlock(Block block) {
        return Block.getIdFromBlock(block);
    }

    public List getTileEntityList() {
        return this.wrapper.getWorld().field_147482_g;
    }

    public int getChestX(TileEntityChest chest) {
        return chest.field_145851_c;
    }

    public int getChestY(TileEntityChest chest) {
        return chest.field_145848_d;
    }

    public int getChestZ(TileEntityChest chest) {
        return chest.field_145849_e;
    }

    public boolean isBurning() {
        return this.wrapper.getPlayer().isBurning();
    }

    public void setRightDelayTimer(int delay) {
        this.wrapper.getMinecraft().rightClickDelayTimer = delay;
    }

    public int getItemInUseDuration() {
        return this.wrapper.getPlayer().getItemInUseDuration();
    }

    public boolean isOnGround() {
        return this.wrapper.getPlayer().onGround;
    }

    public boolean isInWater() {
        return this.wrapper.getPlayer().isInWater();
    }

    public void setFallDistance(float f) {
        this.wrapper.getPlayer().fallDistance = f;
    }

    public void setOnGround(boolean b) {
        this.wrapper.getPlayer().onGround = b;
    }

    public int getFoodLevel() {
        return this.wrapper.getPlayer().getFoodStats().getFoodLevel();
    }

    public float getHealth() {
        return this.wrapper.getPlayer().getHealth();
    }

    public void removeEntityFromWorld(int id) {
        this.wrapper.getWorld().removeEntityFromWorld(id);
    }

    public void addEntityToWorld(Entity e, int id) {
        this.wrapper.getWorld().addEntityToWorld(id, e);
    }

    public void setTimerSpeed(float speed) {
        this.wrapper.getMinecraft().timer.timerSpeed = speed;
    }

    public void jump() {
        this.wrapper.getPlayer().jump();
    }

    public GameProfile getGameProfile(EntityPlayer ep) {
        return ep.field_146106_i;
    }

    public void setGameProfile(GameProfile profile, EntityPlayer ep) {
        ep.field_146106_i = profile;
    }

    public void setPositionAndRotation(Entity e, double x, double y, double z, float yaw, float pitch) {
        e.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    public void copyInventory(EntityPlayer from, EntityPlayer to) {
        from.inventory.copyInventory(to.inventory);
    }

    public void setNoClip(boolean state) {
        this.wrapper.getPlayer().noClip = state;
    }

    public void setSneakKeyPressed(boolean pressed) {
        this.wrapper.getGameSettings().keyBindSneak.pressed = pressed;
    }

    public boolean isSneaking() {
        return this.wrapper.getPlayer().isSneaking();
    }

    public void setStepHeight(float value) {
        this.wrapper.getPlayer().stepHeight = value;
    }

    public int getWidth() {
        return this.getDisplayWidth() / 2;
    }

    public int getHeight() {
        return this.getDisplayHeight() / 2;
    }

    public int getId(GuiButton btn) {
        return btn.id;
    }

    public void addButton(GuiScreen screen, GuiButton btn) {
        screen.buttonList.add(btn);
    }

    public void clearButtons(GuiScreen screen) {
        screen.buttonList.clear();
    }

    public Wrapper getWrapper() {
        return this.wrapper;
    }

    public MovingObjectPosition rayTraceBlocks(Vec3 vecFromPool, Vec3 vecFromPool2) {
        return this.wrapper.getWorld().rayTraceBlocks(vecFromPool, vecFromPool2);
    }

    public Block getTileEntityBlock(EntityFallingBlock b) {
        return b.func_145805_f();
    }

    public float getFallDistance(Entity e) {
        return e.fallDistance;
    }

    public boolean isInvisible(Entity e) {
        return e.isInvisible();
    }
}

