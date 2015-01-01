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
