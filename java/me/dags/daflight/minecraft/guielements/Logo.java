/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 *  Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 *  granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING
 *  ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL,
 *  DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
 *  WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE
 *  USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package me.dags.daflight.minecraft.guielements;

import me.dags.daflight.minecraft.MCGame;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class Logo implements UIElement
{
    private final ResourceLocation LOGO = new ResourceLocation("daflight", "daflight-logo.png");
    private int xPos;
    private int yPos;
    private int defaultY;
    private int imageWidth;
    private int imageHeight;
    private int width;
    private int height;

    public Logo()
    {
        imageWidth = 512;
        imageHeight = 512;
        width = 128;
        height = 128;
    }

    ;

    public Logo(int x, int y)
    {
        xPos = x;
        yPos = y;
        defaultY = y;
        imageWidth = 512;
        imageHeight = 512;
        width = 128;
        height = 128;
    }

    public Logo setXY(int x, int y)
    {
        xPos = x;
        yPos = y;
        defaultY = y;
        return this;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void draw()
    {
        MCGame.getMinecraft().getTextureManager().bindTexture(LOGO);
        glDrawTexturedRect(xPos, yPos, width, height, 1, 1, imageWidth, imageHeight);
    }

    private static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2)
    {
        glDisable(GL_LIGHTING);
        glEnable(GL_BLEND);
        glAlphaFunc(GL_GREATER, 0F);
        glEnable(GL_TEXTURE_2D);
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float texMapScale = 0.001953125F; // 512px

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, 0, u * texMapScale, v2 * texMapScale);
        tessellator.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
        tessellator.addVertexWithUV(x + width, y + 0, 0, u2 * texMapScale, v * texMapScale);
        tessellator.addVertexWithUV(x + 0, y + 0, 0, u * texMapScale, v * texMapScale);
        tessellator.draw();
        glDisable(GL_BLEND);
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        draw();
    }

    @Override
    public void renderToolTips(int mouseX, int mouseY)
    {

    }

    @Override
    public void addToolTip(ToolTip t)
    {

    }

    @Override
    public boolean mouseInput(int mouseX, int mouseY)
    {
        return false;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {

    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        this.yPos += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        this.yPos = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        this.yPos = defaultY;
    }
}
