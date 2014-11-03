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

package me.dags.daflight.ui.uielements;

import com.mumfrey.liteloader.client.api.LiteLoaderBrandingProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import static org.lwjgl.opengl.GL11.glColor4f;

public class GuiSetting
{

    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean drawButton;
    protected boolean field_146123_n;
    private boolean keyBind;

    public void setIsKeyBind(boolean b)
    {
        this.keyBind = b;
    }

    public boolean isKeyBind()
    {
        return this.keyBind;
    }

    public GuiSetting(int controlId, int xPosition, int yPosition, String displayString)
    {
        guiSetting(controlId, xPosition, yPosition, 200, 15, displayString);
    }

    public void guiSetting(int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.drawButton = true;
        this.id = par1;
        this.xPosition = par2;
        this.yPosition = par3;
        this.width = par4;
        this.height = par5;
        this.displayString = par6Str;
    }

    public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
    {
        if (this.drawButton)
        {
            minecraft.getTextureManager().bindTexture(LiteLoaderBrandingProvider.ABOUT_TEXTURE);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            // this.drawTexturedModalRect(this.xPosition, this.yPosition,
            // this.checked ? 134 : 122, 80, 12, 12);
            this.mouseDragged(minecraft, mouseX, mouseY);

            int colour = 0xE0E0E0;
            if (!this.enabled)
                colour = 0xA0A0A0;
            else if (this.field_146123_n)
                colour = 0xFFFFA0;

            this.drawString(minecraft.fontRendererObj, this.displayString, this.xPosition + 16, this.yPosition + 2, colour);
        }
    }

    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_)
    {
    }

    public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
    {
        par1FontRenderer.func_175063_a(par2Str, par3, par4, par5);
    }

    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
    {
        return this.enabled && this.drawButton && p_146116_2_ >= this.xPosition && p_146116_3_ >= this.yPosition && p_146116_2_ < this.xPosition + this.width && p_146116_3_ < this.yPosition + this.height;
    }

}
