/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public class ItemInWorldManager {
    public World theWorld;
    public EntityPlayerMP thisPlayerMP;
    private WorldSettings.GameType gameType = WorldSettings.GameType.NOT_SET;
    private boolean isDestroyingBlock;
    private int initialDamage;
    private int partiallyDestroyedBlockX;
    private int partiallyDestroyedBlockY;
    private int partiallyDestroyedBlockZ;
    private int curblockDamage;
    private boolean receivedFinishDiggingPacket;
    private int posX;
    private int posY;
    private int posZ;
    private int initialBlockDamage;
    private int durabilityRemainingOnBlock = -1;
    private static final String __OBFID = "CL_00001442";

    public ItemInWorldManager(World par1World) {
        this.theWorld = par1World;
    }

    public void setGameType(WorldSettings.GameType par1EnumGameType) {
        this.gameType = par1EnumGameType;
        par1EnumGameType.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
    }

    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }

    public boolean isCreative() {
        return this.gameType.isCreative();
    }

    public void initializeGameType(WorldSettings.GameType par1EnumGameType) {
        if (this.gameType == WorldSettings.GameType.NOT_SET) {
            this.gameType = par1EnumGameType;
        }
        this.setGameType(this.gameType);
    }

    public void updateBlockRemoving() {
        ++this.curblockDamage;
        if (this.receivedFinishDiggingPacket) {
            int var1 = this.curblockDamage - this.initialBlockDamage;
            Block var2 = this.theWorld.getBlock(this.posX, this.posY, this.posZ);
            if (var2.getMaterial() == Material.air) {
                this.receivedFinishDiggingPacket = false;
            } else {
                float var3 = var2.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.posX, this.posY, this.posZ) * (float)(var1 + 1);
                int var4 = (int)(var3 * 10.0f);
                if (var4 != this.durabilityRemainingOnBlock) {
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.posX, this.posY, this.posZ, var4);
                    this.durabilityRemainingOnBlock = var4;
                }
                if (var3 >= 1.0f) {
                    this.receivedFinishDiggingPacket = false;
                    this.tryHarvestBlock(this.posX, this.posY, this.posZ);
                }
            }
        } else if (this.isDestroyingBlock) {
            Block var5 = this.theWorld.getBlock(this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ);
            if (var5.getMaterial() == Material.air) {
                this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;
            } else {
                int var6 = this.curblockDamage - this.initialDamage;
                float var3 = var5.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ) * (float)(var6 + 1);
                int var4 = (int)(var3 * 10.0f);
                if (var4 != this.durabilityRemainingOnBlock) {
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, var4);
                    this.durabilityRemainingOnBlock = var4;
                }
            }
        }
    }

    public void onBlockClicked(int par1, int par2, int par3, int par4) {
        if (!this.gameType.isAdventure() || this.thisPlayerMP.isCurrentToolAdventureModeExempt(par1, par2, par3)) {
            if (this.isCreative()) {
                if (!this.theWorld.extinguishFire(null, par1, par2, par3, par4)) {
                    this.tryHarvestBlock(par1, par2, par3);
                }
            } else {
                this.theWorld.extinguishFire(null, par1, par2, par3, par4);
                this.initialDamage = this.curblockDamage;
                float var5 = 1.0f;
                Block var6 = this.theWorld.getBlock(par1, par2, par3);
                if (var6.getMaterial() != Material.air) {
                    var6.onBlockClicked(this.theWorld, par1, par2, par3, this.thisPlayerMP);
                    var5 = var6.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, par1, par2, par3);
                }
                if (var6.getMaterial() != Material.air && var5 >= 1.0f) {
                    this.tryHarvestBlock(par1, par2, par3);
                } else {
                    this.isDestroyingBlock = true;
                    this.partiallyDestroyedBlockX = par1;
                    this.partiallyDestroyedBlockY = par2;
                    this.partiallyDestroyedBlockZ = par3;
                    int var7 = (int)(var5 * 10.0f);
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), par1, par2, par3, var7);
                    this.durabilityRemainingOnBlock = var7;
                }
            }
        }
    }

    public void uncheckedTryHarvestBlock(int par1, int par2, int par3) {
        if (par1 == this.partiallyDestroyedBlockX && par2 == this.partiallyDestroyedBlockY && par3 == this.partiallyDestroyedBlockZ) {
            int var4 = this.curblockDamage - this.initialDamage;
            Block var5 = this.theWorld.getBlock(par1, par2, par3);
            if (var5.getMaterial() != Material.air) {
                float var6 = var5.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, par1, par2, par3) * (float)(var4 + 1);
                if (var6 >= 0.7f) {
                    this.isDestroyingBlock = false;
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), par1, par2, par3, -1);
                    this.tryHarvestBlock(par1, par2, par3);
                } else if (!this.receivedFinishDiggingPacket) {
                    this.isDestroyingBlock = false;
                    this.receivedFinishDiggingPacket = true;
                    this.posX = par1;
                    this.posY = par2;
                    this.posZ = par3;
                    this.initialBlockDamage = this.initialDamage;
                }
            }
        }
    }

    public void cancelDestroyingBlock(int par1, int par2, int par3) {
        this.isDestroyingBlock = false;
        this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, -1);
    }

    private boolean removeBlock(int par1, int par2, int par3) {
        Block var4 = this.theWorld.getBlock(par1, par2, par3);
        int var5 = this.theWorld.getBlockMetadata(par1, par2, par3);
        var4.onBlockHarvested(this.theWorld, par1, par2, par3, var5, this.thisPlayerMP);
        boolean var6 = this.theWorld.setBlockToAir(par1, par2, par3);
        if (var6) {
            var4.onBlockDestroyedByPlayer(this.theWorld, par1, par2, par3, var5);
        }
        return var6;
    }

    public boolean tryHarvestBlock(int par1, int par2, int par3) {
        if (this.gameType.isAdventure() && !this.thisPlayerMP.isCurrentToolAdventureModeExempt(par1, par2, par3)) {
            return false;
        }
        if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        Block var4 = this.theWorld.getBlock(par1, par2, par3);
        int var5 = this.theWorld.getBlockMetadata(par1, par2, par3);
        this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 2001, par1, par2, par3, Block.getIdFromBlock(var4) + (this.theWorld.getBlockMetadata(par1, par2, par3) << 12));
        boolean var6 = this.removeBlock(par1, par2, par3);
        if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(par1, par2, par3, this.theWorld));
        } else {
            ItemStack var7 = this.thisPlayerMP.getCurrentEquippedItem();
            boolean var8 = this.thisPlayerMP.canHarvestBlock(var4);
            if (var7 != null) {
                var7.func_150999_a(this.theWorld, var4, par1, par2, par3, this.thisPlayerMP);
                if (var7.stackSize == 0) {
                    this.thisPlayerMP.destroyCurrentEquippedItem();
                }
            }
            if (var6 && var8) {
                var4.harvestBlock(this.theWorld, this.thisPlayerMP, par1, par2, par3, var5);
            }
        }
        return var6;
    }

    public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack) {
        int var4 = par3ItemStack.stackSize;
        int var5 = par3ItemStack.getItemDamage();
        ItemStack var6 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
        if (var6 == par3ItemStack && (var6 == null || var6.stackSize == var4 && var6.getMaxItemUseDuration() <= 0 && var6.getItemDamage() == var5)) {
            return false;
        }
        par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var6;
        if (this.isCreative()) {
            var6.stackSize = var4;
            if (var6.isItemStackDamageable()) {
                var6.setItemDamage(var5);
            }
        }
        if (var6.stackSize == 0) {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
        }
        if (!par1EntityPlayer.isUsingItem()) {
            ((EntityPlayerMP)par1EntityPlayer).sendContainerToPlayer(par1EntityPlayer.inventoryContainer);
        }
        return true;
    }

    public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if ((!par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null) && par2World.getBlock(par4, par5, par6).onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10)) {
            return true;
        }
        if (par3ItemStack == null) {
            return false;
        }
        if (this.isCreative()) {
            int var11 = par3ItemStack.getItemDamage();
            int var12 = par3ItemStack.stackSize;
            boolean var13 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            par3ItemStack.setItemDamage(var11);
            par3ItemStack.stackSize = var12;
            return var13;
        }
        return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
    }

    public void setWorld(WorldServer par1WorldServer) {
        this.theWorld = par1WorldServer;
    }
}

