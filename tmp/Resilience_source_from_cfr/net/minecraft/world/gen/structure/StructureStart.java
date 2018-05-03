/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class StructureStart {
    protected LinkedList components = new LinkedList();
    protected StructureBoundingBox boundingBox;
    private int field_143024_c;
    private int field_143023_d;
    private static final String __OBFID = "CL_00000513";

    public StructureStart() {
    }

    public StructureStart(int par1, int par2) {
        this.field_143024_c = par1;
        this.field_143023_d = par2;
    }

    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public LinkedList getComponents() {
        return this.components;
    }

    public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
        Iterator var4 = this.components.iterator();
        while (var4.hasNext()) {
            StructureComponent var5 = (StructureComponent)var4.next();
            if (!var5.getBoundingBox().intersectsWith(par3StructureBoundingBox) || var5.addComponentParts(par1World, par2Random, par3StructureBoundingBox)) continue;
            var4.remove();
        }
    }

    protected void updateBoundingBox() {
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();
        for (StructureComponent var2 : this.components) {
            this.boundingBox.expandTo(var2.getBoundingBox());
        }
    }

    public NBTTagCompound func_143021_a(int par1, int par2) {
        NBTTagCompound var3 = new NBTTagCompound();
        var3.setString("id", MapGenStructureIO.func_143033_a(this));
        var3.setInteger("ChunkX", par1);
        var3.setInteger("ChunkZ", par2);
        var3.setTag("BB", this.boundingBox.func_151535_h());
        NBTTagList var4 = new NBTTagList();
        for (StructureComponent var6 : this.components) {
            var4.appendTag(var6.func_143010_b());
        }
        var3.setTag("Children", var4);
        this.func_143022_a(var3);
        return var3;
    }

    public void func_143022_a(NBTTagCompound par1NBTTagCompound) {
    }

    public void func_143020_a(World par1World, NBTTagCompound par2NBTTagCompound) {
        this.field_143024_c = par2NBTTagCompound.getInteger("ChunkX");
        this.field_143023_d = par2NBTTagCompound.getInteger("ChunkZ");
        if (par2NBTTagCompound.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(par2NBTTagCompound.getIntArray("BB"));
        }
        NBTTagList var3 = par2NBTTagCompound.getTagList("Children", 10);
        int var4 = 0;
        while (var4 < var3.tagCount()) {
            this.components.add(MapGenStructureIO.func_143032_b(var3.getCompoundTagAt(var4), par1World));
            ++var4;
        }
        this.func_143017_b(par2NBTTagCompound);
    }

    public void func_143017_b(NBTTagCompound par1NBTTagCompound) {
    }

    protected void markAvailableHeight(World par1World, Random par2Random, int par3) {
        int var4 = 63 - par3;
        int var5 = this.boundingBox.getYSize() + 1;
        if (var5 < var4) {
            var5 += par2Random.nextInt(var4 - var5);
        }
        int var6 = var5 - this.boundingBox.maxY;
        this.boundingBox.offset(0, var6, 0);
        for (StructureComponent var8 : this.components) {
            var8.getBoundingBox().offset(0, var6, 0);
        }
    }

    protected void setRandomHeight(World par1World, Random par2Random, int par3, int par4) {
        int var5 = par4 - par3 + 1 - this.boundingBox.getYSize();
        boolean var6 = true;
        int var10 = var5 > 1 ? par3 + par2Random.nextInt(var5) : par3;
        int var7 = var10 - this.boundingBox.minY;
        this.boundingBox.offset(0, var7, 0);
        for (StructureComponent var9 : this.components) {
            var9.getBoundingBox().offset(0, var7, 0);
        }
    }

    public boolean isSizeableStructure() {
        return true;
    }

    public int func_143019_e() {
        return this.field_143024_c;
    }

    public int func_143018_f() {
        return this.field_143023_d;
    }
}

