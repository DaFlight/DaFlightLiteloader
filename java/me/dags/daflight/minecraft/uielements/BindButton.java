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

import me.dags.daflight.gui.UIElement;
import me.dags.daflight.minecraft.MCGame;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class BindButton extends GuiButton implements UIElement
{
    private ToolTip toolTip;
    private String name;
    private String value;
    private String defaultValue;
    private boolean active;
    private int defaultY;

    public BindButton(int x, int y, int width, int height, boolean colour, String name, String value, String defaultValue)
    {
        super(0, x, y, width, height, name);
        this.name = name;
        this.value = value;
        super.displayString = getDisplayString();
        this.defaultY = y;
        this.defaultValue = defaultValue;
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
        this.active = super.mousePressed(MCGame.getMinecraft(), x, y);
        super.displayString = getDisplayString();
        return active;
    }

    @Override
    public boolean shiftClicked()
    {
        if (this.hovered)
            this.setValue(defaultValue);
        return this.hovered;
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
