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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * @author dags_ <dags@dags.me>
 */

public class ToggleButton extends GuiButton implements UIElement
{
    private ToolTip toolTip;
    private boolean toggle = false;
    private String[] statusText;
    private int defaultY;

    public ToggleButton(int id, int x, int y, int width, int height, String name, boolean toggleState)
    {
        super(id, x, y, width, height, name);
        this.enabled = true;
        this.toggle = toggleState;
        this.statusText = new String[]{name + ": false", name + ": true"};
        this.displayString = getDisplayString();
        defaultY = y;
    }

    public ToggleButton(int id, int x, int y, int width, int height, String name, boolean toggleState, String[] toggleText)
    {
        super(id, x, y, width, height, name);
        this.enabled = true;
        this.toggle = toggleState;
        this.statusText = toggleText;
        this.displayString = getDisplayString();
        defaultY = y;
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
        boolean result = super.mousePressed(MCGame.getMinecraft(), x, y);
        if (result)
        {
            this.toggle = !toggle;
            this.displayString = getDisplayString();
        }
        return result;
    }

    @Override
    public boolean shiftClicked()
    {
        return false;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {
        super.mouseReleased(mouseX, mouseY);
    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        super.yPosition += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        super.yPosition = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        super.yPosition = defaultY;
    }

    @Override
    public void drawButton(Minecraft m, int x, int y)
    {
        super.drawButton(m, x, y);
    }

    public String getDisplayString()
    {
        return toggle ? statusText[1] : statusText[0];
    }

    public boolean getToggleState()
    {
        return enabled && toggle;
    }
}
