/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock
extends EntityMinecart {
    private final CommandBlockLogic field_145824_a;
    private int field_145823_b;
    private static final String __OBFID = "CL_00001672";

    public EntityMinecartCommandBlock(World p_i45321_1_) {
        super(p_i45321_1_);
        this.field_145824_a = new CommandBlockLogic(){
            private static final String __OBFID = "CL_00001673";

            @Override
            public void func_145756_e() {
                EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, this.func_145753_i());
                EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(this.func_145749_h()));
            }

            @Override
            public int func_145751_f() {
                return 1;
            }

            @Override
            public void func_145757_a(ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(EntityMinecartCommandBlock.this.getEntityId());
            }

            @Override
            public ChunkCoordinates getPlayerCoordinates() {
                return new ChunkCoordinates(MathHelper.floor_double(EntityMinecartCommandBlock.this.posX), MathHelper.floor_double(EntityMinecartCommandBlock.this.posY + 0.5), MathHelper.floor_double(EntityMinecartCommandBlock.this.posZ));
            }

            @Override
            public World getEntityWorld() {
                return EntityMinecartCommandBlock.this.worldObj;
            }
        };
        this.field_145823_b = 0;
    }

    public EntityMinecartCommandBlock(World p_i45322_1_, double p_i45322_2_, double p_i45322_4_, double p_i45322_6_) {
        super(p_i45322_1_, p_i45322_2_, p_i45322_4_, p_i45322_6_);
        this.field_145824_a = new ;
        this.field_145823_b = 0;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(23, "");
        this.getDataWatcher().addObject(24, "");
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.field_145824_a.func_145759_b(par1NBTTagCompound);
        this.getDataWatcher().updateObject(23, this.func_145822_e().func_145753_i());
        this.getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(this.func_145822_e().func_145749_h()));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        this.field_145824_a.func_145758_a(par1NBTTagCompound);
    }

    @Override
    public int getMinecartType() {
        return 6;
    }

    @Override
    public Block func_145817_o() {
        return Blocks.command_block;
    }

    public CommandBlockLogic func_145822_e() {
        return this.field_145824_a;
    }

    @Override
    public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {
        if (par4 && this.ticksExisted - this.field_145823_b >= 4) {
            this.func_145822_e().func_145755_a(this.worldObj);
            this.field_145823_b = this.ticksExisted;
        }
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.worldObj.isClient) {
            par1EntityPlayer.func_146095_a(this.func_145822_e());
        }
        return super.interactFirst(par1EntityPlayer);
    }

    @Override
    public void func_145781_i(int p_145781_1_) {
        super.func_145781_i(p_145781_1_);
        if (p_145781_1_ == 24) {
            try {
                this.field_145824_a.func_145750_b(IChatComponent.Serializer.func_150699_a(this.getDataWatcher().getWatchableObjectString(24)));
            }
            catch (Throwable throwable) {}
        } else if (p_145781_1_ == 23) {
            this.field_145824_a.func_145752_a(this.getDataWatcher().getWatchableObjectString(23));
        }
    }

}

