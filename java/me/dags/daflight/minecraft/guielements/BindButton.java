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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class BindButton extends GuiButton implements UIElement
{
    private ToolTip toolTip;
    private String name;
    private String value;
    private boolean active;
    private int defaultY;

    public BindButton(int x, int y, int width, int height, boolean colour, String name, String value)
    {
        super(0, x, y, width, height, name);
        this.name = name;
        this.value = value;
        super.displayString = getDisplayString();
        defaultY = y;
    }

    public String getDisplayString()
    {
        return active ? EnumChatFormatting.RED + name + ":" : name + ": " + value;
    }

    public void setValue(String s)
    {
        value = s;
        super.displayString = getDisplayString();
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        super.drawButton(MCGame.getMinecraft(), mouseX, mouseY);
    }

    @Override
    public void renderToolTips(int mouseX, int mouseY)
    {
        if (this.hovered && this.toolTip != null)
            this.toolTip.draw(mouseX, mouseY);
    }

    @Override
    public void addToolTip(ToolTip t)
    {
        this.toolTip = t;
    }

    @Override
    public boolean mouseInput(int x, int y)
    {
        active = super.mousePressed(MCGame.getMinecraft(), x, y);
        super.displayString = getDisplayString();
        return active;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {
        super.mouseReleased(mouseX, mouseY);
    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        if (active)
        {
            active = false;
            if (keyId == Keyboard.KEY_ESCAPE)
            {
                value = "";
                super.displayString = getDisplayString();
                return true;
            }
            value = Keyboard.getKeyName(keyId);
            super.displayString = getDisplayString();
        }
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        this.yPosition += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        this.yPosition = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        this.yPosition = defaultY;
    }
}
