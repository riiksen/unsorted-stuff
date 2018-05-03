/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class ModelRenderer {
    public float textureWidth = 64.0f;
    public float textureHeight = 32.0f;
    private int textureOffsetX;
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled;
    private int displayList;
    public boolean mirror;
    public boolean showModel = true;
    public boolean isHidden;
    public List cubeList = new ArrayList();
    public List childModels;
    public final String boxName;
    private ModelBase baseModel;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    private static final String __OBFID = "CL_00000874";

    public ModelRenderer(ModelBase par1ModelBase, String par2Str) {
        this.baseModel = par1ModelBase;
        par1ModelBase.boxList.add(this);
        this.boxName = par2Str;
        this.setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
    }

    public ModelRenderer(ModelBase par1ModelBase) {
        this(par1ModelBase, null);
    }

    public ModelRenderer(ModelBase par1ModelBase, int par2, int par3) {
        this(par1ModelBase);
        this.setTextureOffset(par2, par3);
    }

    public void addChild(ModelRenderer par1ModelRenderer) {
        if (this.childModels == null) {
            this.childModels = new ArrayList();
        }
        this.childModels.add(par1ModelRenderer);
    }

    public ModelRenderer setTextureOffset(int par1, int par2) {
        this.textureOffsetX = par1;
        this.textureOffsetY = par2;
        return this;
    }

    public ModelRenderer addBox(String par1Str, float par2, float par3, float par4, int par5, int par6, int par7) {
        par1Str = String.valueOf(this.boxName) + "." + par1Str;
        TextureOffset var8 = this.baseModel.getTextureOffset(par1Str);
        this.setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par2, par3, par4, par5, par6, par7, 0.0f).func_78244_a(par1Str));
        return this;
    }

    public ModelRenderer addBox(float par1, float par2, float par3, int par4, int par5, int par6) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, 0.0f));
        return this;
    }

    public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
    }

    public void setRotationPoint(float par1, float par2, float par3) {
        this.rotationPointX = par1;
        this.rotationPointY = par2;
        this.rotationPointZ = par3;
    }

    public void render(float par1) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            GL11.glTranslatef((float)this.offsetX, (float)this.offsetY, (float)this.offsetZ);
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                    GL11.glCallList((int)this.displayList);
                    if (this.childModels != null) {
                        int var2 = 0;
                        while (var2 < this.childModels.size()) {
                            ((ModelRenderer)this.childModels.get(var2)).render(par1);
                            ++var2;
                        }
                    }
                } else {
                    GL11.glTranslatef((float)(this.rotationPointX * par1), (float)(this.rotationPointY * par1), (float)(this.rotationPointZ * par1));
                    GL11.glCallList((int)this.displayList);
                    if (this.childModels != null) {
                        int var2 = 0;
                        while (var2 < this.childModels.size()) {
                            ((ModelRenderer)this.childModels.get(var2)).render(par1);
                            ++var2;
                        }
                    }
                    GL11.glTranslatef((float)((- this.rotationPointX) * par1), (float)((- this.rotationPointY) * par1), (float)((- this.rotationPointZ) * par1));
                }
            } else {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.rotationPointX * par1), (float)(this.rotationPointY * par1), (float)(this.rotationPointZ * par1));
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
                GL11.glCallList((int)this.displayList);
                if (this.childModels != null) {
                    int var2 = 0;
                    while (var2 < this.childModels.size()) {
                        ((ModelRenderer)this.childModels.get(var2)).render(par1);
                        ++var2;
                    }
                }
                GL11.glPopMatrix();
            }
            GL11.glTranslatef((float)(- this.offsetX), (float)(- this.offsetY), (float)(- this.offsetZ));
        }
    }

    public void renderWithRotation(float par1) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.rotationPointX * par1), (float)(this.rotationPointY * par1), (float)(this.rotationPointZ * par1));
            if (this.rotateAngleY != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glCallList((int)this.displayList);
            GL11.glPopMatrix();
        }
    }

    public void postRender(float par1) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GL11.glTranslatef((float)(this.rotationPointX * par1), (float)(this.rotationPointY * par1), (float)(this.rotationPointZ * par1));
                }
            } else {
                GL11.glTranslatef((float)(this.rotationPointX * par1), (float)(this.rotationPointY * par1), (float)(this.rotationPointZ * par1));
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
            }
        }
    }

    private void compileDisplayList(float par1) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList((int)this.displayList, (int)4864);
        Tessellator var2 = Tessellator.instance;
        int var3 = 0;
        while (var3 < this.cubeList.size()) {
            ((ModelBox)this.cubeList.get(var3)).render(var2, par1);
            ++var3;
        }
        GL11.glEndList();
        this.compiled = true;
    }

    public ModelRenderer setTextureSize(int par1, int par2) {
        this.textureWidth = par1;
        this.textureHeight = par2;
        return this;
    }
}

