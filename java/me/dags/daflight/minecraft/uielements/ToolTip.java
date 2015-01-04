/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.dags.daflight.minecraft.uielements;

import me.dags.daflight.minecraft.MCGame;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author dags_ <dags@dags.me>
 */

public class ToolTip extends Gui
{
    private String[] lines;

    public ToolTip(String title, String[] lines)
    {
        this.lines = new String[lines.length + 1];
        this.lines[0] = EnumChatFormatting.DARK_AQUA + title;
        for (int i = 0; i < lines.length; i++)
        {
            this.lines[i + 1] = EnumChatFormatting.GRAY + lines[i];
        }
    }

    public void draw(int mouseX, int mouseY)
    {
        if (lines != null)
        {
            int var4 = 0;

            for (String s : lines)
            {
                int var7 = MCGame.getMinecraft().fontRendererObj.getStringWidth(s);
                if (var7 > var4)
                {
                    var4 = var7;
                }
            }

            int xPos = mouseX + 12;
            int yPos = mouseY - 12;
            int var8 = 8;

            if (lines.length > 1)
            {
                var8 += 2 + (lines.length - 1) * 10;
            }

            int var9 = -267386864;
            this.drawGradientRect(xPos - 3, yPos - 4, xPos + var4 + 3, yPos - 3, var9, var9);
            this.drawGradientRect(xPos - 3, yPos + var8 + 3, xPos + var4 + 3, yPos + var8 + 4, var9, var9);
            this.drawGradientRect(xPos - 3, yPos - 3, xPos + var4 + 3, yPos + var8 + 3, var9, var9);
            this.drawGradientRect(xPos - 4, yPos - 3, xPos - 3, yPos + var8 + 3, var9, var9);
            this.drawGradientRect(xPos + var4 + 3, yPos - 3, xPos + var4 + 4, yPos + var8 + 3, var9, var9);

            int var10 = 1347420415;
            int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
            this.drawGradientRect(xPos - 3, yPos - 3 + 1, xPos - 3 + 1, yPos + var8 + 3 - 1, var10, var11);
            this.drawGradientRect(xPos + var4 + 2, yPos - 3 + 1, xPos + var4 + 3, yPos + var8 + 3 - 1, var10, var11);
            this.drawGradientRect(xPos - 3, yPos - 3, xPos + var4 + 3, yPos - 3 + 1, var10, var10);
            this.drawGradientRect(xPos - 3, yPos + var8 + 2, xPos + var4 + 3, yPos + var8 + 3, var11, var11);

            for (int i = 0; i < lines.length; i++)
            {
                MCGame.getMinecraft().fontRendererObj.drawStringWithShadow(lines[i], xPos, yPos, -1);
                if (i == 0)
                    yPos += 2;
                yPos += 10;
            }
        }
    }
}
