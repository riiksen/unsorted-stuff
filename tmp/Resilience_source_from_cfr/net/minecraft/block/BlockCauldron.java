/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron
extends Block {
    private IIcon field_150029_a;
    private IIcon field_150028_b;
    private IIcon field_150030_M;
    private static final String __OBFID = "CL_00000213";

    public BlockCauldron() {
        super(Material.iron);
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.field_150028_b : (p_149691_1_ == 0 ? this.field_150030_M : this.blockIcon);
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.field_150029_a = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "inner");
        this.field_150028_b = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_top");
        this.field_150030_M = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_" + "bottom");
        this.blockIcon = p_149651_1_.registerIcon(String.valueOf(this.getTextureName()) + "_side");
    }

    public static IIcon func_150026_e(String p_150026_0_) {
        return p_150026_0_.equals("inner") ? Blocks.cauldron.field_150029_a : (p_150026_0_.equals("bottom") ? Blocks.cauldron.field_150030_M : null);
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        float var8 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, var8, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var8);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(1.0f - var8, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - var8, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 24;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        int var6 = BlockCauldron.func_150027_b(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
        float var7 = (float)p_149670_3_ + (6.0f + (float)(3 * var6)) / 16.0f;
        if (!p_149670_1_.isClient && p_149670_5_.isBurning() && var6 > 0 && p_149670_5_.boundingBox.minY <= (double)var7) {
            p_149670_5_.extinguish();
            this.func_150024_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6 - 1);
        }
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        ItemStack var10 = p_149727_5_.inventory.getCurrentItem();
        if (var10 == null) {
            return true;
        }
        int var11 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        int var12 = BlockCauldron.func_150027_b(var11);
        if (var10.getItem() == Items.water_bucket) {
            if (var12 < 3) {
                if (!p_149727_5_.capabilities.isCreativeMode) {
                    p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, new ItemStack(Items.bucket));
                }
                this.func_150024_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, 3);
            }
            return true;
        }
        if (var10.getItem() == Items.glass_bottle) {
            if (var12 > 0) {
                if (!p_149727_5_.capabilities.isCreativeMode) {
                    ItemStack var13 = new ItemStack(Items.potionitem, 1, 0);
                    if (!p_149727_5_.inventory.addItemStackToInventory(var13)) {
                        p_149727_1_.spawnEntityInWorld(new EntityItem(p_149727_1_, (double)p_149727_2_ + 0.5, (double)p_149727_3_ + 1.5, (double)p_149727_4_ + 0.5, var13));
                    } else if (p_149727_5_ instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)p_149727_5_).sendContainerToPlayer(p_149727_5_.inventoryContainer);
                    }
                    --var10.stackSize;
                    if (var10.stackSize <= 0) {
                        p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, null);
                    }
                }
                this.func_150024_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, var12 - 1);
            }
        } else if (var12 > 0 && var10.getItem() instanceof ItemArmor && ((ItemArmor)var10.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH) {
            ItemArmor var14 = (ItemArmor)var10.getItem();
            var14.removeColor(var10);
            this.func_150024_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, var12 - 1);
            return true;
        }
        return false;
    }

    public void func_150024_a(World p_150024_1_, int p_150024_2_, int p_150024_3_, int p_150024_4_, int p_150024_5_) {
        p_150024_1_.setBlockMetadataWithNotify(p_150024_2_, p_150024_3_, p_150024_4_, MathHelper.clamp_int(p_150024_5_, 0, 3), 2);
        p_150024_1_.func_147453_f(p_150024_2_, p_150024_3_, p_150024_4_, this);
    }

    @Override
    public void fillWithRain(World p_149639_1_, int p_149639_2_, int p_149639_3_, int p_149639_4_) {
        int var5;
        if (p_149639_1_.rand.nextInt(20) == 1 && (var5 = p_149639_1_.getBlockMetadata(p_149639_2_, p_149639_3_, p_149639_4_)) < 3) {
            p_149639_1_.setBlockMetadataWithNotify(p_149639_2_, p_149639_3_, p_149639_4_, var5 + 1, 2);
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.cauldron;
    }

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Items.cauldron;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_) {
        int var6 = p_149736_1_.getBlockMetadata(p_149736_2_, p_149736_3_, p_149736_4_);
        return BlockCauldron.func_150027_b(var6);
    }

    public static int func_150027_b(int p_150027_0_) {
        return p_150027_0_;
    }

    public static float func_150025_c(int p_150025_0_) {
        int var1 = MathHelper.clamp_int(p_150025_0_, 0, 3);
        return (float)(6 + 3 * var1) / 16.0f;
    }
}

