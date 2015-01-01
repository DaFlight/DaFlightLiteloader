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
import net.minecraft.util.EnumChatFormatting;

public class Label implements UIElement
{

    private String color;
    private String label;
    private int xPos;
    private int yPos;
    private int defaultY;

    public Label(int x, int y, String label)
    {
        this.xPos = x;
        this.yPos = y;
        this.label = label;
        this.color = "";
        defaultY = y;
    }

    public Label setColour(EnumChatFormatting e)
    {
        color = e.toString();
        return this;
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        MCGame.getMinecraft().fontRendererObj.drawStringWithShadow(this.color + this.label, this.xPos, this.yPos, 0xFFFFFF);
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
        yPos += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        this.yPos = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        yPos = defaultY;
    }
}
