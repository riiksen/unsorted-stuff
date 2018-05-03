/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.gui.objects.components;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.components.Component;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class XrayBlockComponent
extends Component {
    private int blockId;
    private boolean enabled;

    public XrayBlockComponent(float x, float y, float x1, float y1, int blockId, boolean enabled) {
        super(x, y, x1, y1);
        this.blockId = blockId;
        this.enabled = enabled;
    }

    @Override
    public void draw(int x, int y) {
        boolean mouseDownOverComponent;
        boolean overComponent = (float)x >= this.getX() && (float)x <= this.getX1() && (float)y >= this.getY() && (float)y <= this.getY1();
        boolean bl = mouseDownOverComponent = overComponent && Mouse.isButtonDown((int)0);
        if (this.enabled) {
            Utils.drawRect(this.getX(), this.getY(), this.getX1(), this.getY1(), mouseDownOverComponent ? 1427990174 : (overComponent ? 1430559709 : 1428521673));
        } else {
            Utils.drawRect(this.getX(), this.getY(), this.getX1(), this.getY1(), mouseDownOverComponent ? 1426089425 : (overComponent ? 1432840039 : 1431655765));
        }
        ItemStack is = new ItemStack(Block.getBlockById(this.blockId));
        GL11.glPushMatrix();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)32826);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2929);
        try {
            Resilience.getInstance().getInvoker().renderItemIntoGUI(is, (int)this.getX() + 3, (int)this.getY() + 3);
            Resilience.getInstance().getInvoker().renderItemOverlayIntoGUI(is, (int)this.getX() + 3, (int)this.getY() + 3);
        }
        catch (Exception exception) {
            // empty catch block
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glPopMatrix();
    }

    @Override
    public void onComponentClicked(int x, int y, int btn) {
        if (btn == 0) {
            boolean bl = this.enabled = !this.enabled;
            if (!this.enabled) {
                Resilience.getInstance().getXrayUtils().removeBlock(this.blockId);
                if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
                    Resilience.getInstance().getInvoker().loadRenderers();
                }
                Resilience.getInstance().getFileManager().saveXrayBlocks(new String[0]);
            } else {
                Resilience.getInstance().getXrayUtils().addBlock(this.blockId);
                if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
                    Resilience.getInstance().getInvoker().loadRenderers();
                }
                Resilience.getInstance().getFileManager().saveXrayBlocks(new String[0]);
            }
        }
    }
}

