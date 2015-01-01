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

package me.dags.daflight.gui;

import me.dags.daflight.minecraft.MCGame;
import me.dags.daflight.minecraft.ConfigGui;
import me.dags.daflight.minecraft.guielements.UIElement;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

/**
 * @author dags_ <dags@dags.me>
 */

public class QuickMenu extends ConfigGui
{
    public QuickMenu(GuiScreen parent)
    {
        super(parent);
    }

    @Override
    public void drawScreen(int x, int y, float f)
    {
        checkSizeChange();
        drawDefaultBackground();
        handleScrolling();
        super.drawScreen(x, y, f);
        super.handleScrollbar(x, y);
    }

    @Override
    public void keyTyped(char keyChar, int keyId)
    {
        boolean exit = keyInput(keyChar, keyId);
        if (exit)
            close();
    }

    private void checkSizeChange()
    {
        if (MCGame.screenSizeChanged())
        {
            save();
            super.maxYOffset = 0;
            super.yOffset = 0;
            applySizeChange(MCGame.getScaledResolution().getScaledWidth(), MCGame.getScaledResolution().getScaledHeight());
        }
    }

    public void handleScrolling()
    {
        if (Mouse.hasWheel() && super.isScrollable)
        {
            int offset = 0;
            int i = Mouse.getDWheel();
            Mouse.getDWheel();
            if (i > 0)
                offset = 10;
            else if (i < 0)
                offset = -10;
            if (offset != 0)
            {
                if (super.yOffset + offset > 0 || super.yOffset + offset < super.maxYOffset)
                    offset = 0;
                super.yOffset += offset;
                scroll(offset);
            }
        }
    }

    private void scroll(int offset)
    {
        for (UIElement e : super.uiElements)
            e.setYOffset(offset);
    }

    private void close()
    {
        save();
        MCGame.getMinecraft().displayGuiScreen(super.parent);
        MCGame.getMinecraft().setIngameFocus();
    }
}
