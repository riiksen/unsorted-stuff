/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.multiplayer;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerControllerMP {
    protected final Minecraft mc;
    protected final NetHandlerPlayClient netClientHandler;
    protected int currentBlockX = -1;
    protected int currentBlockY = -1;
    protected int currentblockZ = -1;
    private ItemStack currentItemHittingBlock;
    protected float curBlockDamageMP;
    protected float stepSoundTickCounter;
    protected int blockHitDelay;
    protected boolean isHittingBlock;
    protected WorldSettings.GameType currentGameType = WorldSettings.GameType.SURVIVAL;
    private int currentPlayerItem;
    private static final String __OBFID = "CL_00000881";

    public PlayerControllerMP(Minecraft p_i45062_1_, NetHandlerPlayClient netHandlerPlayClient) {
        this.mc = p_i45062_1_;
        this.netClientHandler = netHandlerPlayClient;
    }

    public static void clickBlockCreative(Minecraft par0Minecraft, PlayerControllerMP par1PlayerControllerMP, int par2, int par3, int par4, int par5) {
        if (!par0Minecraft.theWorld.extinguishFire(par0Minecraft.thePlayer, par2, par3, par4, par5)) {
            par1PlayerControllerMP.onPlayerDestroyBlock(par2, par3, par4, par5);
        }
    }

    public void setPlayerCapabilities(EntityPlayer par1EntityPlayer) {
        this.currentGameType.configurePlayerCapabilities(par1EntityPlayer.capabilities);
    }

    public boolean enableEverythingIsScrewedUpMode() {
        return false;
    }

    public void setGameType(WorldSettings.GameType par1EnumGameType) {
        this.currentGameType = par1EnumGameType;
        this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
    }

    public void flipPlayer(EntityPlayer par1EntityPlayer) {
        par1EntityPlayer.rotationYaw = -180.0f;
    }

    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }

    public boolean onPlayerDestroyBlock(int par1, int par2, int par3, int par4) {
        ItemStack var9;
        if (this.currentGameType.isAdventure() && !this.mc.thePlayer.isCurrentToolAdventureModeExempt(par1, par2, par3)) {
            return false;
        }
        if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        WorldClient var5 = this.mc.theWorld;
        Block var6 = var5.getBlock(par1, par2, par3);
        if (var6.getMaterial() == Material.air) {
            return false;
        }
        var5.playAuxSFX(2001, par1, par2, par3, Block.getIdFromBlock(var6) + (var5.getBlockMetadata(par1, par2, par3) << 12));
        int var7 = var5.getBlockMetadata(par1, par2, par3);
        boolean var8 = var5.setBlockToAir(par1, par2, par3);
        if (var8) {
            var6.onBlockDestroyedByPlayer(var5, par1, par2, par3, var7);
        }
        this.currentBlockY = -1;
        if (!this.currentGameType.isCreative() && (var9 = this.mc.thePlayer.getCurrentEquippedItem()) != null) {
            var9.func_150999_a(var5, var6, par1, par2, par3, this.mc.thePlayer);
            if (var9.stackSize == 0) {
                this.mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        return var8;
    }

    public void clickBlock(int par1, int par2, int par3, int par4) {
        if (!this.currentGameType.isAdventure() || this.mc.thePlayer.isCurrentToolAdventureModeExempt(par1, par2, par3)) {
            if (this.currentGameType.isCreative()) {
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
                PlayerControllerMP.clickBlockCreative(this.mc, this, par1, par2, par3, par4);
                this.blockHitDelay = 5;
            } else if (!this.isHittingBlock || !this.sameToolAndBlock(par1, par2, par3)) {
                boolean var6;
                if (this.isHittingBlock) {
                    this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, par4));
                }
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
                Block var5 = this.mc.theWorld.getBlock(par1, par2, par3);
                boolean bl = var6 = var5.getMaterial() != Material.air;
                if (var6 && this.curBlockDamageMP == 0.0f) {
                    var5.onBlockClicked(this.mc.theWorld, par1, par2, par3, this.mc.thePlayer);
                }
                if (var6 && var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, par1, par2, par3) >= 1.0f) {
                    this.onPlayerDestroyBlock(par1, par2, par3, par4);
                } else {
                    this.isHittingBlock = true;
                    this.currentBlockX = par1;
                    this.currentBlockY = par2;
                    this.currentblockZ = par3;
                    this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
                    this.curBlockDamageMP = 0.0f;
                    this.stepSoundTickCounter = 0.0f;
                    this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0f) - 1);
                }
            }
        }
    }

    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, -1));
        }
        this.isHittingBlock = false;
        this.curBlockDamageMP = 0.0f;
        this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, -1);
    }

    public void onPlayerDamageBlock(int par1, int par2, int par3, int par4) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
        } else if (this.currentGameType.isCreative()) {
            this.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
            PlayerControllerMP.clickBlockCreative(this.mc, this, par1, par2, par3, par4);
        } else if (this.sameToolAndBlock(par1, par2, par3)) {
            Block var5 = this.mc.theWorld.getBlock(par1, par2, par3);
            if (var5.getMaterial() == Material.air) {
                this.isHittingBlock = false;
                return;
            }
            this.curBlockDamageMP += var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, par1, par2, par3);
            if (this.stepSoundTickCounter % 4.0f == 0.0f) {
                this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var5.stepSound.func_150498_e()), (var5.stepSound.func_150497_c() + 1.0f) / 8.0f, var5.stepSound.func_150494_d() * 0.5f, (float)par1 + 0.5f, (float)par2 + 0.5f, (float)par3 + 0.5f));
            }
            this.stepSoundTickCounter += 1.0f;
            if (this.curBlockDamageMP >= 1.0f) {
                this.isHittingBlock = false;
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(2, par1, par2, par3, par4));
                this.onPlayerDestroyBlock(par1, par2, par3, par4);
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.blockHitDelay = 5;
            }
            this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0f) - 1);
        } else {
            this.clickBlock(par1, par2, par3, par4);
        }
    }

    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }

    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
        } else if (this.netClientHandler.getNetworkManager().getExitMessage() != null) {
            this.netClientHandler.getNetworkManager().getNetHandler().onDisconnect(this.netClientHandler.getNetworkManager().getExitMessage());
        } else {
            this.netClientHandler.getNetworkManager().getNetHandler().onDisconnect(new ChatComponentText("Disconnected from server"));
        }
    }

    protected boolean sameToolAndBlock(int par1, int par2, int par3) {
        boolean var5;
        ItemStack var4 = this.mc.thePlayer.getHeldItem();
        boolean bl = var5 = this.currentItemHittingBlock == null && var4 == null;
        if (this.currentItemHittingBlock != null && var4 != null) {
            boolean bl2 = var5 = var4.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(var4, this.currentItemHittingBlock) && (var4.isItemStackDamageable() || var4.getItemDamage() == this.currentItemHittingBlock.getItemDamage());
        }
        if (par1 == this.currentBlockX && par2 == this.currentBlockY && par3 == this.currentblockZ && var5) {
            return true;
        }
        return false;
    }

    protected void syncCurrentPlayItem() {
        int var1 = this.mc.thePlayer.inventory.currentItem;
        if (var1 != this.currentPlayerItem) {
            this.currentPlayerItem = var1;
            this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
        }
    }

    public boolean onPlayerRightClick(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, Vec3 par8Vec3) {
        ItemBlock var13;
        this.syncCurrentPlayItem();
        float var9 = (float)par8Vec3.xCoord - (float)par4;
        float var10 = (float)par8Vec3.yCoord - (float)par5;
        float var11 = (float)par8Vec3.zCoord - (float)par6;
        boolean var12 = false;
        if ((!par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null) && par2World.getBlock(par4, par5, par6).onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, var9, var10, var11)) {
            var12 = true;
        }
        if (!var12 && par3ItemStack != null && par3ItemStack.getItem() instanceof ItemBlock && !(var13 = (ItemBlock)par3ItemStack.getItem()).func_150936_a(par2World, par4, par5, par6, par7, par1EntityPlayer, par3ItemStack)) {
            return false;
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(par4, par5, par6, par7, par1EntityPlayer.inventory.getCurrentItem(), var9, var10, var11));
        if (var12) {
            return true;
        }
        if (par3ItemStack == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            int var16 = par3ItemStack.getItemDamage();
            int var14 = par3ItemStack.stackSize;
            boolean var15 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
            par3ItemStack.setItemDamage(var16);
            par3ItemStack.stackSize = var14;
            return var15;
        }
        return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
    }

    public boolean sendUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(-1, -1, -1, 255, par1EntityPlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
        int var4 = par3ItemStack.stackSize;
        ItemStack var5 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
        if (var5 == par3ItemStack && (var5 == null || var5.stackSize == var4)) {
            return false;
        }
        par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var5;
        if (var5.stackSize == 0) {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
        }
        return true;
    }

    public HookEntityClientPlayerMP func_147493_a(World p_147493_1_, StatFileWriter p_147493_2_) {
        return new HookEntityClientPlayerMP(this.mc, p_147493_1_, this.mc.getSession(), this.netClientHandler, p_147493_2_);
    }

    public void attackEntity(EntityPlayer par1EntityPlayer, Entity par2Entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(par2Entity, C02PacketUseEntity.Action.ATTACK));
        par1EntityPlayer.attackTargetEntityWithCurrentItem(par2Entity);
    }

    public boolean interactWithEntitySendPacket(EntityPlayer par1EntityPlayer, Entity par2Entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(par2Entity, C02PacketUseEntity.Action.INTERACT));
        return par1EntityPlayer.interactWith(par2Entity);
    }

    public ItemStack windowClick(int par1, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        short var6 = par5EntityPlayer.openContainer.getNextTransactionID(par5EntityPlayer.inventory);
        ItemStack var7 = par5EntityPlayer.openContainer.slotClick(par2, par3, par4, par5EntityPlayer);
        this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(par1, par2, par3, par4, var7, var6));
        return var7;
    }

    public void sendEnchantPacket(int par1, int par2) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(par1, par2));
    }

    public void sendSlotPacket(ItemStack par1ItemStack, int par2) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(par2, par1ItemStack));
        }
    }

    public void sendPacketDropItem(ItemStack par1ItemStack) {
        if (this.currentGameType.isCreative() && par1ItemStack != null) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, par1ItemStack));
        }
    }

    public void onStoppedUsingItem(EntityPlayer par1EntityPlayer) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(5, 0, 0, 0, 255));
        par1EntityPlayer.stopUsingItem();
    }

    public boolean gameIsSurvivalOrAdventure() {
        return this.currentGameType.isSurvivalOrAdventure();
    }

    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }

    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }

    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }

    public boolean func_110738_j() {
        if (this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse) {
            return true;
        }
        return false;
    }
}

