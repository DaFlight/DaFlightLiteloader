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

package me.dags.daflight.minecraft.uielements;

import me.dags.daflight.gui.UIElement;
import net.minecraft.client.gui.Gui;

/**
 * @author dags_ <dags@dags.me>
 */

public class ScrollBar extends Gui implements UIElement
{
    private static final int BAR = 0x66D1D1D1;
    private static final int BAR_HOVER = 0x99B3B3B3;
    private static final int RUNNER = 0xFF9E9E9E;
    private static final int RUNNER_HOVER = 0xFFD1D1D1;
    private static final int RUNNER_LENGTH = 15;

    private boolean visible;
    private boolean hovered;
    private boolean active;
    private int yOffset;
    private int xPos;
    private int yPos;
    private int windowHeight;
    private int runnerYPos;
    private int maxYPos;

    public ScrollBar(int x, int y, int windowHeight, int contentHeight)
    {
        super();
        this.xPos = x;
        this.yPos = y;
        this.windowHeight = windowHeight;
        this.maxYPos = contentHeight;
    }

    public ScrollBar setVisible(boolean b)
    {
        this.visible = b;
        return this;
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        if (!visible)
            return;
        hovered = mouseX >= xPos - 1 && mouseX <= xPos + 2 && mouseY >= runnerYPos - RUNNER_LENGTH && mouseY <= runnerYPos + RUNNER_LENGTH;
        if (active)
        {
            runnerYPos = mouseY;
            yOffset = getOffsetFromRunnerPos();
        }
        drawRect(xPos, yPos, xPos + 1, windowHeight, hovered || active ? BAR_HOVER : BAR);
        drawRect(xPos - 1, runnerYPos - RUNNER_LENGTH, xPos + 2, runnerYPos + RUNNER_LENGTH, hovered || active ? RUNNER_HOVER : RUNNER);
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
        return hovered && (active = true);
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {
        if (active)
        {
            active = false;
        }
    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        this.yOffset += offset;
        this.runnerYPos = getRunnerPos();
    }

    @Override
    public void setYPos(int pos)
    {

    }

    @Override
    public void resetYOffset()
    {
        this.yOffset = 0;
        this.runnerYPos = 0;
    }

    private int getRunnerPos()
    {
        return (int) (windowHeight * (double) yOffset / (double) maxYPos);
    }

    private int getOffsetFromRunnerPos()
    {
        return (int) ((double) runnerYPos / (double) windowHeight * (double) maxYPos);
    }

    public boolean isActive()
    {
        return active;
    }

    public int getYOffset()
    {
        return yOffset;
    }
}
