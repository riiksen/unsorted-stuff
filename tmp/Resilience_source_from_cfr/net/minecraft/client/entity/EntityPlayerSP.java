/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.hooks.HookGuiIngame;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import com.krispdev.resilience.module.values.Values;
import com.mojang.authlib.GameProfile;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class EntityPlayerSP
extends AbstractClientPlayer {
    public MovementInput movementInput;
    protected Minecraft mc;
    protected int sprintToggleTimer;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;
    private MouseFilter field_71162_ch = new MouseFilter();
    private MouseFilter field_71160_ci = new MouseFilter();
    private MouseFilter field_71161_cj = new MouseFilter();
    public float timeInPortal;
    public float prevTimeInPortal;
    private static final String __OBFID = "CL_00000938";

    public EntityPlayerSP(Minecraft par1Minecraft, World par2World, Session par3Session, int par4) {
        super(par2World, par3Session.func_148256_e());
        this.mc = par1Minecraft;
        this.dimension = par4;
    }

    @Override
    public void updateEntityActionState() {
        super.updateEntityActionState();
        this.moveStrafing = this.movementInput.moveStrafe;
        this.moveForward = this.movementInput.moveForward;
        this.isJumping = this.movementInput.jump;
        this.prevRenderArmYaw = this.renderArmYaw;
        this.prevRenderArmPitch = this.renderArmPitch;
        this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5);
        this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5);
    }

    @Override
    public void onLivingUpdate() {
        if (this.sprintingTicksLeft > 0) {
            --this.sprintingTicksLeft;
            if (this.sprintingTicksLeft == 0) {
                this.setSprinting(false);
            }
        }
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            this.posZ = 0.5;
            this.posX = 0.5;
            this.posX = 0.0;
            this.posZ = 0.0;
            this.rotationYaw = (float)this.ticksExisted / 12.0f;
            this.rotationPitch = 10.0f;
            this.posY = 68.5;
        } else {
            boolean var4;
            this.prevTimeInPortal = this.timeInPortal;
            if (this.inPortal) {
                if (this.mc.currentScreen != null) {
                    this.mc.displayGuiScreen(null);
                }
                if (this.timeInPortal == 0.0f) {
                    this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4f + 0.8f));
                }
                this.timeInPortal += 0.0125f;
                if (this.timeInPortal >= 1.0f) {
                    this.timeInPortal = 1.0f;
                }
                this.inPortal = false;
            } else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
                this.timeInPortal += 0.006666667f;
                if (this.timeInPortal > 1.0f) {
                    this.timeInPortal = 1.0f;
                }
            } else {
                if (this.timeInPortal > 0.0f) {
                    this.timeInPortal -= 0.05f;
                }
                if (this.timeInPortal < 0.0f) {
                    this.timeInPortal = 0.0f;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            boolean var1 = this.movementInput.jump;
            float var2 = 0.8f;
            boolean var3 = this.movementInput.moveForward >= var2;
            this.movementInput.updatePlayerMoveState();
            if (this.isUsingItem() && !this.isRiding() && !Resilience.getInstance().getValues().noSlowdownEnabled) {
                this.movementInput.moveStrafe *= 0.2f;
                this.movementInput.moveForward *= 0.2f;
                this.sprintToggleTimer = 0;
            }
            if (this.movementInput.sneak && this.ySize < 0.2f) {
                this.ySize = 0.2f;
            }
            this.func_145771_j(this.posX - (double)this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + (double)this.width * 0.35);
            this.func_145771_j(this.posX - (double)this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - (double)this.width * 0.35);
            this.func_145771_j(this.posX + (double)this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - (double)this.width * 0.35);
            this.func_145771_j(this.posX + (double)this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + (double)this.width * 0.35);
            boolean bl = var4 = (float)this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
            if (this.onGround && !var3 && this.movementInput.moveForward >= var2 && !this.isSprinting() && var4 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                    this.sprintToggleTimer = 7;
                } else {
                    this.setSprinting(true);
                }
            }
            if (!this.isSprinting() && this.movementInput.moveForward >= var2 && var4 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                this.setSprinting(true);
            }
            if (this.isSprinting() && (this.movementInput.moveForward < var2 || this.isCollidedHorizontally || !var4)) {
                this.setSprinting(false);
            }
            if (this.capabilities.allowFlying && !var1 && this.movementInput.jump) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
            if (this.capabilities.isFlying) {
                if (this.movementInput.sneak) {
                    this.motionY -= 0.15;
                }
                if (this.movementInput.jump) {
                    this.motionY += 0.15;
                }
            }
            if (this.isRidingHorse()) {
                if (this.horseJumpPowerCounter < 0) {
                    ++this.horseJumpPowerCounter;
                    if (this.horseJumpPowerCounter == 0) {
                        this.horseJumpPower = 0.0f;
                    }
                }
                if (var1 && !this.movementInput.jump) {
                    this.horseJumpPowerCounter = -10;
                    this.func_110318_g();
                } else if (!var1 && this.movementInput.jump) {
                    this.horseJumpPowerCounter = 0;
                    this.horseJumpPower = 0.0f;
                } else if (var1) {
                    ++this.horseJumpPowerCounter;
                    this.horseJumpPower = this.horseJumpPowerCounter < 10 ? (float)this.horseJumpPowerCounter * 0.1f : 0.8f + 2.0f / (float)(this.horseJumpPowerCounter - 9) * 0.1f;
                }
            } else {
                this.horseJumpPower = 0.0f;
            }
            super.onLivingUpdate();
            if (this.onGround && this.capabilities.isFlying) {
                this.capabilities.isFlying = false;
                this.sendPlayerAbilities();
            }
        }
    }

    public float getFOVMultiplier() {
        float var1 = 1.0f;
        if (this.capabilities.isFlying) {
            var1 *= 1.1f;
        }
        IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        var1 = (float)((double)var1 * ((var2.getAttributeValue() / (double)this.capabilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(var1) || Float.isInfinite(var1)) {
            var1 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            int var3 = this.getItemInUseDuration();
            float var4 = (float)var3 / 20.0f;
            var4 = var4 > 1.0f ? 1.0f : (var4 *= var4);
            var1 *= 1.0f - var4 * 0.15f;
        }
        return var1;
    }

    @Override
    public void closeScreen() {
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }

    @Override
    public void func_146100_a(TileEntity p_146100_1_) {
        if (p_146100_1_ instanceof TileEntitySign) {
            this.mc.displayGuiScreen(new GuiEditSign((TileEntitySign)p_146100_1_));
        } else if (p_146100_1_ instanceof TileEntityCommandBlock) {
            this.mc.displayGuiScreen(new GuiCommandBlock(((TileEntityCommandBlock)p_146100_1_).func_145993_a()));
        }
    }

    @Override
    public void func_146095_a(CommandBlockLogic p_146095_1_) {
        this.mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
    }

    @Override
    public void displayGUIBook(ItemStack par1ItemStack) {
        Item var2 = par1ItemStack.getItem();
        if (var2 == Items.written_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, false));
        } else if (var2 == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, true));
        }
    }

    @Override
    public void displayGUIChest(IInventory par1IInventory) {
        this.mc.displayGuiScreen(new GuiChest(this.inventory, par1IInventory));
    }

    @Override
    public void func_146093_a(TileEntityHopper p_146093_1_) {
        this.mc.displayGuiScreen(new GuiHopper(this.inventory, p_146093_1_));
    }

    @Override
    public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper) {
        this.mc.displayGuiScreen(new GuiHopper(this.inventory, par1EntityMinecartHopper));
    }

    @Override
    public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, par2IInventory, par1EntityHorse));
    }

    @Override
    public void displayGUIWorkbench(int par1, int par2, int par3) {
        this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, par1, par2, par3));
    }

    @Override
    public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str) {
        this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, par1, par2, par3, par4Str));
    }

    @Override
    public void displayGUIAnvil(int par1, int par2, int par3) {
        this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj, par1, par2, par3));
    }

    @Override
    public void func_146101_a(TileEntityFurnace p_146101_1_) {
        this.mc.displayGuiScreen(new GuiFurnace(this.inventory, p_146101_1_));
    }

    @Override
    public void func_146098_a(TileEntityBrewingStand p_146098_1_) {
        this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, p_146098_1_));
    }

    @Override
    public void func_146104_a(TileEntityBeacon p_146104_1_) {
        this.mc.displayGuiScreen(new GuiBeacon(this.inventory, p_146104_1_));
    }

    @Override
    public void func_146102_a(TileEntityDispenser p_146102_1_) {
        this.mc.displayGuiScreen(new GuiDispenser(this.inventory, p_146102_1_));
    }

    @Override
    public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, par1IMerchant, this.worldObj, par2Str));
    }

    @Override
    public void onCriticalHit(Entity par1Entity) {
        this.mc.effectRenderer.addEffect(new EntityCrit2FX(this.mc.theWorld, par1Entity));
    }

    @Override
    public void onEnchantmentCritical(Entity par1Entity) {
        EntityCrit2FX var2 = new EntityCrit2FX(this.mc.theWorld, par1Entity, "magicCrit");
        this.mc.effectRenderer.addEffect(var2);
    }

    @Override
    public void onItemPickup(Entity par1Entity, int par2) {
        this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, par1Entity, this, -0.5f));
    }

    @Override
    public boolean isSneaking() {
        if (this.movementInput.sneak && !this.sleeping) {
            return true;
        }
        return false;
    }

    public void setPlayerSPHealth(float par1) {
        float var2 = this.getHealth() - par1;
        if (var2 <= 0.0f) {
            this.setHealth(par1);
            if (var2 < 0.0f) {
                this.hurtResistantTime = this.maxHurtResistantTime / 2;
            }
        } else {
            this.lastDamage = var2;
            this.setHealth(this.getHealth());
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(DamageSource.generic, var2);
            this.maxHurtTime = 10;
            this.hurtTime = 10;
        }
    }

    @Override
    public void addChatComponentMessage(IChatComponent p_146105_1_) {
        this.mc.ingameGUI.getChatGUI().func_146227_a(p_146105_1_);
    }

    private boolean isBlockTranslucent(int par1, int par2, int par3) {
        return this.worldObj.getBlock(par1, par2, par3).isNormalCube();
    }

    @Override
    protected boolean func_145771_j(double p_145771_1_, double p_145771_3_, double p_145771_5_) {
        int var7 = MathHelper.floor_double(p_145771_1_);
        int var8 = MathHelper.floor_double(p_145771_3_);
        int var9 = MathHelper.floor_double(p_145771_5_);
        double var10 = p_145771_1_ - (double)var7;
        double var12 = p_145771_5_ - (double)var9;
        if (this.isBlockTranslucent(var7, var8, var9) || this.isBlockTranslucent(var7, var8 + 1, var9)) {
            boolean var14 = !this.isBlockTranslucent(var7 - 1, var8, var9) && !this.isBlockTranslucent(var7 - 1, var8 + 1, var9);
            boolean var15 = !this.isBlockTranslucent(var7 + 1, var8, var9) && !this.isBlockTranslucent(var7 + 1, var8 + 1, var9);
            boolean var16 = !this.isBlockTranslucent(var7, var8, var9 - 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 - 1);
            boolean var17 = !this.isBlockTranslucent(var7, var8, var9 + 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 + 1);
            int var18 = -1;
            double var19 = 9999.0;
            if (var14 && var10 < var19) {
                var19 = var10;
                var18 = 0;
            }
            if (var15 && 1.0 - var10 < var19) {
                var19 = 1.0 - var10;
                var18 = 1;
            }
            if (var16 && var12 < var19) {
                var19 = var12;
                var18 = 4;
            }
            if (var17 && 1.0 - var12 < var19) {
                var19 = 1.0 - var12;
                var18 = 5;
            }
            float var21 = 0.1f;
            if (var18 == 0) {
                this.motionX = - var21;
            }
            if (var18 == 1) {
                this.motionX = var21;
            }
            if (var18 == 4) {
                this.motionZ = - var21;
            }
            if (var18 == 5) {
                this.motionZ = var21;
            }
        }
        return false;
    }

    @Override
    public void setSprinting(boolean par1) {
        super.setSprinting(par1);
        this.sprintingTicksLeft = par1 ? 600 : 0;
    }

    public void setXPStats(float par1, int par2, int par3) {
        this.experience = par1;
        this.experienceTotal = par2;
        this.experienceLevel = par3;
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {
        this.mc.ingameGUI.getChatGUI().func_146227_a(p_145747_1_);
    }

    @Override
    public boolean canCommandSenderUseCommand(int par1, String par2Str) {
        if (par1 <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ + 0.5));
    }

    @Override
    public void playSound(String par1Str, float par2, float par3) {
        this.worldObj.playSound(this.posX, this.posY - (double)this.yOffset, this.posZ, par1Str, par2, par3, false);
    }

    @Override
    public boolean isClientWorld() {
        return true;
    }

    public boolean isRidingHorse() {
        if (this.ridingEntity != null && this.ridingEntity instanceof EntityHorse) {
            return true;
        }
        return false;
    }

    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }

    protected void func_110318_g() {
    }
}

