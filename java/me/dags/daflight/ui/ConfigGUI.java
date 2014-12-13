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

package me.dags.daflight.ui;

import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import me.dags.daflight.abstraction.MinecraftGame;
import me.dags.daflight.ui.pages.Page0;
import me.dags.daflight.ui.pages.Page1;
import me.dags.daflight.utils.Config;

public class ConfigGUI extends MinecraftGame implements ConfigPanel
{

    private Page0 page0;
    private Page1 page1;
    private int height;

    public ConfigGUI()
    {
        height = 250;
    }

    @Override
    public String getPanelTitle()
    {
        return "DaFlight";
    }

    @Override
    public int getContentHeight()
    {
        return height;
    }

    @Override
    public void onPanelShown(ConfigPanelHost host)
    {
        Settings settings = new Settings();
        page0 = new Page0(settings, host.getWidth(), 0, 0);
        page1 = new Page1(settings, host.getWidth(), 0, 1);
        setSize(host.getWidth());
    }

    @Override
    public void onPanelResize(ConfigPanelHost host)
    {
        if (page0 == null || page1 == null)
        {
            return;
        }
        setSize(host.getWidth());
    }

    private void setSize(int width)
    {
        if (width > 330)
        {
            page0.setMargin(0);
            page1.setMargin(170);
            page1.setTopMargin(0);
            height = 250;
        }
        else
        {
            int i = Math.round(width / 4);
            page0.setMargin(i);
            page1.setMargin(i + 3);
            page1.setTopMargin(245);
            height = 400;
        }
        page0.load();
        page1.load();
    }

    @Override
    public void onPanelHidden()
    {
        page0.save();
        page1.save();
        Config.saveSettings();
        Config.applySettings();
    }

    @Override
    public void onTick(ConfigPanelHost host)
    {
    }

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        page0.drawPage(mouseX, mouseY);
        page1.drawPage(mouseX, mouseY);
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        page0.clicked(mouseX, mouseY, mouseButton);
        page1.clicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        page0.released(mouseX, mouseY);
        page1.released(mouseX, mouseY);
    }

    @Override
    public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY)
    {
    }

    @Override
    public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
    {
        boolean shouldExit = page1.keyPress(keyChar, keyCode);
        if (shouldExit)
        {
            host.close();
        }
    }
}
