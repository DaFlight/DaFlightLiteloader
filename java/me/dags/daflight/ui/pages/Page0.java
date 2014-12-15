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

package me.dags.daflight.ui.pages;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import me.dags.daflight.minecraft.Colour;
import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.minecraft.uielements.GuiCheck;
import me.dags.daflight.ui.Settings;
import me.dags.daflight.minecraft.uielements.GuiEntryBox;
import me.dags.daflight.minecraft.uielements.GuiLabel;
import me.dags.daflight.minecraft.uielements.GuiSlider;

import java.util.HashSet;
import java.util.Set;

public class Page0 extends MinecraftGame
{

    private Settings settings;

    public String pageTitle;
    public int margin;
    public int pageNumber;

    private Set<GuiLabel> labels;
    private Set<GuiEntryBox> keyBinds;
    private Set<GuiSlider> sliders;
    private Set<GuiCheck> checkBoxes;

    public Page0(Settings s, int width, int height, int page)
    {
        this.settings = s;
        this.pageTitle = "New Page";
        this.pageNumber = page;
        this.margin = (int) Math.round(width / 2.1) * this.pageNumber;
        if (this.pageNumber == 1)
        {
            this.margin += 5;
        }
    }

    public void drawPage(int mouseX, int mouseY)
    {
        for (GuiLabel l : this.labels)
        {
            l.draw();
        }
        for (GuiCheckbox gc : this.checkBoxes)
        {
            gc.drawButton(getMinecraft(), mouseX, mouseY);
        }
        for (GuiSlider gs : sliders)
        {
            gs.draw(getMinecraft(), mouseX, mouseY);
        }
    }

    public void clicked(int mouseX, int mouseY, int mouseButton)
    {
        for (GuiCheckbox gc : this.checkBoxes)
        {
            if (gc.mousePressed(getMinecraft(), mouseX, mouseY))
            {
                gc.checked = !gc.checked;
            }
        }
        for (GuiSlider gs : this.sliders)
        {
            if (gs.mousePressed(getMinecraft(), mouseX, mouseY))
            {
                gs.enabled = true;
            }
        }
    }

    public void released(int mouseX, int mouseY)
    {
        for (GuiSlider gs : sliders)
        {
            gs.mouseReleased(mouseX, mouseY);
        }
    }

    public void setWidth(int i)
    {
        this.margin = (int) Math.round(i / 2.1) * this.pageNumber;
        if (this.pageNumber == 1)
        {
            this.margin += 5;
        }
    }

    public void setMargin(int i)
    {
        this.margin = i;
    }

    public void save()
    {
        for (GuiCheckbox gc : this.checkBoxes)
        {
            settings.updateSetting(gc.displayString, "" + gc.checked);
        }
        for (GuiSlider gs : sliders)
        {
            settings.updateSetting(gs.getName(), gs.getValue());
        }
    }

    public void load()
    {
        this.labels = new HashSet<GuiLabel>();
        this.sliders = new HashSet<GuiSlider>();
        this.checkBoxes = new HashSet<GuiCheck>();

        int[] xy = new int[]{this.margin + 10, 10};

        if (this.pageNumber == 1)
        {
            xy = loadBinds(xy[0], xy[1]);
        }

        xy = setTitle("Preferences", xy[0], xy[1]);
        xy = loadChecks(xy[0], xy[1]);
        xy = setTitle("Settings", xy[0], xy[1]);
        loadSliders(xy[0], xy[1]);
    }

    private int[] setTitle(String s, int x, int y)
    {
        GuiLabel gl = new GuiLabel(getMinecraft().fontRendererObj, x, y);
        gl.setLabel(Colour.DARK_AQUA + s + ":");
        gl.setShadow(true);
        this.labels.add(gl);

        y += 10;

        return new int[]{x, y};
    }

    private int[] loadBinds(int x, int y)
    {
        int[] temp = new int[]{x, y};

        if (!settings.getKeyBinds().isEmpty())
        {
            temp = setTitle("KeyBinds", temp[0], temp[1]);
            temp[1] += 5;

            for (String s : settings.getKeyBinds().keySet())
            {
                GuiLabel gl = new GuiLabel(getMinecraft().fontRendererObj, temp[0], temp[1]);
                gl.setLabel(s);
                this.labels.add(gl);

                GuiEntryBox gb = new GuiEntryBox(getMinecraft().fontRendererObj, temp[0] + 80, temp[1] - 1, 50, 10);
                gb.name(settings.getKeyBinds().get(s));
                keyBinds.add(gb);
                temp[1] += 15;
            }
            temp[1] += 5;
        }

        return temp;
    }

    private int[] loadChecks(int x, int y)
    {
        int xTemp = x;
        int yTemp = y;

        for (String s : settings.getBooleans().keySet())
        {
            GuiCheck gc = new GuiCheck(5, xTemp, yTemp, s);
            gc.checked = settings.getBooleans().get(s);
            checkBoxes.add(gc);

            if (xTemp == x)
            {
                xTemp += 90;
            }
            else
            {
                xTemp = x;
                yTemp += 15;
            }
        }
        if (!settings.getBooleans().isEmpty())
        {
            yTemp += 25;
        }
        xTemp = this.margin + 10;
        return new int[]{xTemp, yTemp};
    }

    private int[] loadSliders(int x, int y)
    {
        int xTemp = x;
        int yTemp = y;

        for (String s : settings.getSettings().keySet())
        {
            float min = 0F;
            float max = 1F;
            float value = settings.getSettings().get(s);

            if (s.equals("FlySpeed"))
            {
                min = 0.2F;
                max = 4.9F;
                value = settings.getSettings().get(s) * 10;
            }
            else if (s.equals("FlySpeedMultiplier"))
            {
                min = 0.1F;
                max = 10.0F;
            }
            else if (s.equals("FlightSmoothingFactor"))
            {
                min = 0.0F;
                max = 1.0F;
            }
            else if (s.equals("SprintSpeed"))
            {
                min = 0.2F;
                max = 4.9F;
                value = settings.getSettings().get(s) * 10;
            }
            else if (s.equals("SprintSpeedMultiplier"))
            {
                min = 0.1F;
                max = 10.0F;
            }
            else if (s.equals("JumpModifier"))
            {
                min = 0.01F;
                max = 1.0F;
            }
            else if (s.equals("Left/RightModifier"))
            {
                min = 0.01F;
                max = 1.0F;
            }

            GuiSlider gs = new GuiSlider(1, xTemp, yTemp, getOptions(), min, max);
            gs.setDefaultValue(value);
            gs.setDisplayString(s);
            sliders.add(gs);

            yTemp += 25;
        }
        if (!settings.getSettings().isEmpty())
        {
            yTemp += 25;
        }
        xTemp = this.margin + 10;
        return new int[]{xTemp, yTemp};
    }

    public Settings getSettings()
    {
        return this.settings;
    }
}
