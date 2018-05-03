/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityEnderman
extends EntityMob {
    private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 6.199999809265137, 0).setSaved(false);
    private static boolean[] carriableBlocks = new boolean[256];
    private int teleportDelay;
    private int stareTimer;
    private Entity lastEntityToAttack;
    private boolean isAggressive;
    private static final String __OBFID = "CL_00001685";

    static {
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.grass)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.dirt)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.sand)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.gravel)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.yellow_flower)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.red_flower)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.brown_mushroom)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.red_mushroom)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.tnt)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.cactus)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.clay)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.pumpkin)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.melon_block)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock((Block)Blocks.mycelium)] = true;
    }

    public EntityEnderman(World par1World) {
        super(par1World);
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
        this.dataWatcher.addObject(17, new Byte(0));
        this.dataWatcher.addObject(18, new Byte(0));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("carried", (short)Block.getIdFromBlock(this.func_146080_bZ()));
        par1NBTTagCompound.setShort("carriedData", (short)this.getCarryingData());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.func_146081_a(Block.getBlockById(par1NBTTagCompound.getShort("carried")));
        this.setCarryingData(par1NBTTagCompound.getShort("carriedData"));
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0);
        if (var1 != null) {
            if (this.shouldAttackPlayer(var1)) {
                this.isAggressive = true;
                if (this.stareTimer == 0) {
                    this.worldObj.playSoundEffect(var1.posX, var1.posY, var1.posZ, "mob.endermen.stare", 1.0f, 1.0f);
                }
                if (this.stareTimer++ == 5) {
                    this.stareTimer = 0;
                    this.setScreaming(true);
                    return var1;
                }
            } else {
                this.stareTimer = 0;
            }
        }
        return null;
    }

    private boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.armorInventory[3];
        if (var2 != null && var2.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return false;
        }
        Vec3 var3 = par1EntityPlayer.getLook(1.0f).normalize();
        Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1EntityPlayer.posX, this.boundingBox.minY + (double)(this.height / 2.0f) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
        double var5 = var4.lengthVector();
        double var7 = var3.dotProduct(var4 = var4.normalize());
        return var7 > 1.0 - 0.025 / var5 ? par1EntityPlayer.canEntityBeSeen(this) : false;
    }

    @Override
    public void onLivingUpdate() {
        float var7;
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this.lastEntityToAttack != this.entityToAttack) {
            IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.removeModifier(attackingSpeedBoostModifier);
            if (this.entityToAttack != null) {
                var1.applyModifier(attackingSpeedBoostModifier);
            }
        }
        this.lastEntityToAttack = this.entityToAttack;
        if (!this.worldObj.isClient && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            Block var4;
            int var3;
            int var2;
            if (this.func_146080_bZ().getMaterial() == Material.air) {
                int var6;
                if (this.rand.nextInt(20) == 0 && carriableBlocks[Block.getIdFromBlock(var4 = this.worldObj.getBlock(var6 = MathHelper.floor_double(this.posX - 2.0 + this.rand.nextDouble() * 4.0), var2 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0), var3 = MathHelper.floor_double(this.posZ - 2.0 + this.rand.nextDouble() * 4.0)))]) {
                    this.func_146081_a(var4);
                    this.setCarryingData(this.worldObj.getBlockMetadata(var6, var2, var3));
                    this.worldObj.setBlock(var6, var2, var3, Blocks.air);
                }
            } else if (this.rand.nextInt(2000) == 0) {
                int var6 = MathHelper.floor_double(this.posX - 1.0 + this.rand.nextDouble() * 2.0);
                var2 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0);
                var3 = MathHelper.floor_double(this.posZ - 1.0 + this.rand.nextDouble() * 2.0);
                var4 = this.worldObj.getBlock(var6, var2, var3);
                Block var5 = this.worldObj.getBlock(var6, var2 - 1, var3);
                if (var4.getMaterial() == Material.air && var5.getMaterial() != Material.air && var5.renderAsNormalBlock()) {
                    this.worldObj.setBlock(var6, var2, var3, this.func_146080_bZ(), this.getCarryingData(), 3);
                    this.func_146081_a(Blocks.air);
                }
            }
        }
        int var6 = 0;
        while (var6 < 2) {
            this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * (double)this.width, (this.rand.nextDouble() - 0.5) * 2.0, - this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0);
            ++var6;
        }
        if (this.worldObj.isDaytime() && !this.worldObj.isClient && (var7 = this.getBrightness(1.0f)) > 0.5f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (var7 - 0.4f) * 2.0f) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.isAggressive = false;
            this.teleportRandomly();
        }
        if (this.isWet() || this.isBurning()) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.isAggressive = false;
            this.teleportRandomly();
        }
        if (this.isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0) {
            this.setScreaming(false);
        }
        this.isJumping = false;
        if (this.entityToAttack != null) {
            this.faceEntity(this.entityToAttack, 100.0f, 100.0f);
        }
        if (!this.worldObj.isClient && this.isEntityAlive()) {
            if (this.entityToAttack != null) {
                if (this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.entityToAttack)) {
                    if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0) {
                        this.teleportRandomly();
                    }
                    this.teleportDelay = 0;
                } else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0 && this.teleportDelay++ >= 30 && this.teleportToEntity(this.entityToAttack)) {
                    this.teleportDelay = 0;
                }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }

    protected boolean teleportRandomly() {
        double var1 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        double var3 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double var5 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(var1, var3, var5);
    }

    protected boolean teleportToEntity(Entity par1Entity) {
        Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1Entity.posX, this.boundingBox.minY + (double)(this.height / 2.0f) - par1Entity.posY + (double)par1Entity.getEyeHeight(), this.posZ - par1Entity.posZ);
        var2 = var2.normalize();
        double var3 = 16.0;
        double var5 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - var2.xCoord * var3;
        double var7 = this.posY + (double)(this.rand.nextInt(16) - 8) - var2.yCoord * var3;
        double var9 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - var2.zCoord * var3;
        return this.teleportTo(var5, var7, var9);
    }

    protected boolean teleportTo(double par1, double par3, double par5) {
        int var16;
        int var15;
        double var7 = this.posX;
        double var9 = this.posY;
        double var11 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        boolean var13 = false;
        int var14 = MathHelper.floor_double(this.posX);
        if (this.worldObj.blockExists(var14, var15 = MathHelper.floor_double(this.posY), var16 = MathHelper.floor_double(this.posZ))) {
            boolean var17 = false;
            while (!var17 && var15 > 0) {
                Block var18 = this.worldObj.getBlock(var14, var15 - 1, var16);
                if (var18.getMaterial().blocksMovement()) {
                    var17 = true;
                    continue;
                }
                this.posY -= 1.0;
                --var15;
            }
            if (var17) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                    var13 = true;
                }
            }
        }
        if (!var13) {
            this.setPosition(var7, var9, var11);
            return false;
        }
        int var30 = 128;
        int var31 = 0;
        while (var31 < var30) {
            double var19 = (double)var31 / ((double)var30 - 1.0);
            float var21 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            float var22 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            float var23 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            double var24 = var7 + (this.posX - var7) * var19 + (this.rand.nextDouble() - 0.5) * (double)this.width * 2.0;
            double var26 = var9 + (this.posY - var9) * var19 + this.rand.nextDouble() * (double)this.height;
            double var28 = var11 + (this.posZ - var11) * var19 + (this.rand.nextDouble() - 0.5) * (double)this.width * 2.0;
            this.worldObj.spawnParticle("portal", var24, var26, var28, var21, var22, var23);
            ++var31;
        }
        this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0f, 1.0f);
        this.playSound("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }

    @Override
    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    @Override
    protected Item func_146068_u() {
        return Items.ender_pearl;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        Item var3 = this.func_146068_u();
        if (var3 != null) {
            int var4 = this.rand.nextInt(2 + par2);
            int var5 = 0;
            while (var5 < var4) {
                this.func_145779_a(var3, 1);
                ++var5;
            }
        }
    }

    public void func_146081_a(Block p_146081_1_) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(Block.getIdFromBlock(p_146081_1_) & 255)));
    }

    public Block func_146080_bZ() {
        return Block.getBlockById(this.dataWatcher.getWatchableObjectByte(16));
    }

    public void setCarryingData(int par1) {
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1 & 255)));
    }

    public int getCarryingData() {
        return this.dataWatcher.getWatchableObjectByte(17);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setScreaming(true);
        if (par1DamageSource instanceof EntityDamageSource && par1DamageSource.getEntity() instanceof EntityPlayer) {
            this.isAggressive = true;
        }
        if (par1DamageSource instanceof EntityDamageSourceIndirect) {
            this.isAggressive = false;
            int var3 = 0;
            while (var3 < 64) {
                if (this.teleportRandomly()) {
                    return true;
                }
                ++var3;
            }
            return false;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public boolean isScreaming() {
        if (this.dataWatcher.getWatchableObjectByte(18) > 0) {
            return true;
        }
        return false;
    }

    public void setScreaming(boolean par1) {
        this.dataWatcher.updateObject(18, Byte.valueOf(par1 ? 1 : 0));
    }
}

