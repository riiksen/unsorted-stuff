/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import net.minecraft.client.renderer.IImageBuffer;

public class ImageBufferDownload
implements IImageBuffer {
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;
    private static final String __OBFID = "CL_00000956";

    @Override
    public BufferedImage parseUserSkin(BufferedImage par1BufferedImage) {
        if (par1BufferedImage == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 32;
        int srcWidth = par1BufferedImage.getWidth();
        int srcHeight = par1BufferedImage.getHeight();
        if (srcWidth != 64 || srcHeight != 32 && srcHeight != 64) {
            while (this.imageWidth < srcWidth || this.imageHeight < srcHeight) {
                this.imageWidth *= 2;
                this.imageHeight *= 2;
            }
        }
        BufferedImage bufferedimage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics g = bufferedimage.getGraphics();
        g.drawImage(par1BufferedImage, 0, 0, null);
        g.dispose();
        this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
        int w = this.imageWidth;
        int h = this.imageHeight;
        this.setAreaOpaque(0, 0, w / 2, h / 2);
        this.setAreaTransparent(w / 2, 0, w, h);
        this.setAreaOpaque(0, h / 2, w, h);
        return bufferedimage;
    }

    private void setAreaTransparent(int par1, int par2, int par3, int par4) {
        if (!this.hasTransparency(par1, par2, par3, par4)) {
            int var5 = par1;
            while (var5 < par3) {
                int var6 = par2;
                while (var6 < par4) {
                    int[] arrn = this.imageData;
                    int n = var5 + var6 * this.imageWidth;
                    arrn[n] = arrn[n] & 16777215;
                    ++var6;
                }
                ++var5;
            }
        }
    }

    private void setAreaOpaque(int par1, int par2, int par3, int par4) {
        int var5 = par1;
        while (var5 < par3) {
            int var6 = par2;
            while (var6 < par4) {
                int[] arrn = this.imageData;
                int n = var5 + var6 * this.imageWidth;
                arrn[n] = arrn[n] | -16777216;
                ++var6;
            }
            ++var5;
        }
    }

    private boolean hasTransparency(int par1, int par2, int par3, int par4) {
        int var5 = par1;
        while (var5 < par3) {
            int var6 = par2;
            while (var6 < par4) {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 255) < 128) {
                    return true;
                }
                ++var6;
            }
            ++var5;
        }
        return false;
    }
}

