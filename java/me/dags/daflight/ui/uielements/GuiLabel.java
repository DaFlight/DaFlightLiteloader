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

import net.minecraft.client.gui.FontRenderer;

public class GuiLabel
{

    private final FontRenderer fr;
    private String label;
    private boolean shadow;

    private int color;
    private int id;
    private int xPos;
    private int yPos;

    public GuiLabel(FontRenderer fR, int x, int y)
    {
        this.fr = fR;
        this.xPos = x;
        this.yPos = y;
        this.id = 0;
        this.color = 0xFFFFFF;
    }

    public void setLabel(String s)
    {
        this.label = s;
    }

    public void setId(int i)
    {
        this.id = i;
    }

    public void setColor(int i)
    {
        this.color = i;
    }

    public void setShadow(boolean b)
    {
        this.shadow = b;
    }

    public void draw()
    {
        if (shadow)
        {
            this.fr.drawStringWithShadow(this.label, this.xPos, this.yPos, 0xFFFFFF);
        }
        else
        {
            this.fr.drawString(this.label, this.xPos, this.yPos, 0xFFFFFF);
        }
    }

}
