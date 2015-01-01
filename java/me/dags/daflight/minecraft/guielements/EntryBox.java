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

import me.dags.daflight.minecraft.Colour;
import me.dags.daflight.minecraft.MCGame;
import me.dags.daflight.minecraft.reflection.GuiTextFieldYPositionReflector;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class EntryBox extends GuiTextField implements UIElement
{
    private static final GuiTextFieldYPositionReflector reflector = new GuiTextFieldYPositionReflector();
    private ToolTip toolTip;
    private boolean hovered;
    private String label;
    private boolean coloured = true;
    private boolean isActive;
    private int xPos;
    private int yPos;
    private int height;
    private int defaultY;

    public EntryBox(int x, int y, int width, int height, String label, boolean colour)
    {
        super(MCGame.getMinecraft().fontRendererObj, x, y, width, height);
        this.setFocused(false);
        this.setEnabled(true);
        this.coloured = colour;
        this.xPos = x;
        this.yPos = y;
        this.height = height;
        this.defaultY = y;
        this.label = label + ": ";
    }

    public EntryBox setString(String s)
    {
        if (coloured)
        {
            s = Colour.addColour(s);
        }
        this.setText(label + s);
        return this;
    }

    public void setActive()
    {
        this.setText(Colour.stripColour(getText().replace(label, "")));
        this.setFocused(coloured);
        this.setTextColor(0xFF5555);
        this.isActive = true;
    }

    public void unsetActive()
    {
        this.isActive = false;
        this.setFocused(false);
        this.setTextColor(0xFFFFFF);
        this.setString(getText());
        this.setCursorPositionZero();
    }

    public String getValue()
    {
        return Colour.stripColour(getText().replace(label, ""));
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        super.drawTextBox();
        hovered = mouseX >= this.xPos && mouseX <= this.xPos + this.getWidth() && mouseY >= this.yPos && mouseY <= this.height;
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
    public boolean mouseInput(int mouseX, int mouseY)
    {
        super.mouseClicked(mouseX, mouseY, 0);
        if (this.isFocused())
        {
            if (!this.isActive)
            {
                this.setActive();
                return true;
            }
        }
        else if (this.isActive)
        {
            this.unsetActive();
        }
        return false;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {
    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        if (keyId == Keyboard.KEY_BACK && this.getText().length() == 0)
        {
            return false;
        }
        super.textboxKeyTyped(keyChar, keyId);
        if (this.isActive && (keyId == Keyboard.KEY_RETURN || keyId == Keyboard.KEY_ESCAPE))
        {
            this.unsetActive();
            return true;
        }
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        this.yPos += offset;
        reflector.applyValue(this, yPos);
    }

    @Override
    public void setYPos(int pos)
    {
        this.yPos = this.defaultY + pos;
        reflector.applyValue(this, yPos);
    }

    @Override
    public void resetYOffset()
    {
        this.yPos = defaultY;
        reflector.applyValue(this, yPos);
    }
}
