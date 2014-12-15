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

import me.dags.daflight.utils.Tools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class GuiSlider extends GuiOptionSlider
{

    private String name;
    private float min;
    private float max;
    private float value;

    public GuiSlider(int a, int b, int c, Options o, float f1, float f2)
    {
        super(a, b, c, o, f1, f2);
        min = f1;
        max = f2;
        value = 0.5F;
    }

    public void setDefaultValue(float f)
    {
        this.value = normalise(f);
    }

    public void draw(Minecraft m, int mouseX, int mouseY)
    {
        this.drawButton(m, mouseX, mouseY);
    }

    public void setDisplayString(String s)
    {
        this.displayString = s + ": " + Tools.df2.format(value);
        this.name = s;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return "" + value;
    }

    @Override
    public void mouseDragged(Minecraft p_146119_1_, int x, int y)
    {
        if (this.enabled)
        {
            if (this.dragging)
            {
                this.value = (float) (x - (this.xPosition + 4)) / (float) (((this.width) - 8) / max);

                if (this.value < min)
                {
                    this.value = min;
                }

                if (this.value > max)
                {
                    this.value = max;
                }

                this.value = normalise(value);
                this.displayString = name + ": " + Tools.df2.format(value);
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.value * (float) ((this.width - 8)) / max), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.value * (float) ((this.width - 8)) / max) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    private float normalise(float f)
    {
        return MathHelper.clamp_float(f, this.min, this.max);
    }

}
