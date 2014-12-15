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

package me.dags.daflight.ui.hud;

import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.api.DaFlightUI;
import me.dags.daflight.minecraft.Colour;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Renders text to screen when certain components of the mod are enabled
 *
 * @author dags_
 */
public class HUD implements DaFlightUI
{

    private List<DFEntry> mods;
    private int counter = 50;

    public HUD()
    {
        mods = new ArrayList<DFEntry>();
        mods.add(0, new DFEntry("", false));
        mods.add(1, new DFEntry("", false));
        mods.add(2, new DFEntry(Colour.addColour(Config.getInstance().fullBrightStatus), false));
        mods.add(3, new DFEntry("", false));
    }

    public void updateMsg()
    {
        DaPlayer dp = LiteModDaFlight.DAPLAYER;
        boolean flyModifier = false;
        // Flight
        if (dp.flyModOn || DaPlayer.KEY_BINDS.enableFly.keyHeld())
        {
            String s = "";
            flyModifier = DaPlayer.KEY_BINDS.speedModifier.keyHeld();
            if (dp.flyModOn)
            {
                s = Colour.addColour(Config.getInstance().flightStatus);
                if (dp.cineFlightOn)
                {
                    s = Colour.addColour(Config.getInstance().cineFlightStatus);
                }
            }
            if (DaPlayer.DF_PERMISSIONS.flyEnabled() && (dp.flySpeed.isBoost() || flyModifier))
            {
                s = s + Colour.addColour(Config.getInstance().speedStatus);
            }
            mods.get(0).setTitle(s);
            mods.get(0).setShow(true);
        }
        else
        {
            mods.get(0).setShow(false);
        }
        // Sprint
        if (DaPlayer.DF_PERMISSIONS.sprintEnabled() && (dp.sprintModOn || DaPlayer.KEY_BINDS.enableSprint.keyHeld()))
        {
            String s = Colour.addColour(Config.getInstance().runStatus);
            if (dp.sprintSpeed.isBoost() || (DaPlayer.KEY_BINDS.speedModifier.keyHeld() && !flyModifier))
            {
                s = s + Colour.addColour(Config.getInstance().speedStatus);
            }
            mods.get(1).setTitle(s);
            mods.get(1).setShow(true);
        }
        else
        {
            mods.get(1).setShow(false);
        }
        // FullBright
        mods.get(2).setTitle(Colour.addColour(Config.getInstance().fullBrightStatus));
        mods.get(2).setShow(dp.fullBrightOn);
    }

    public void renderTemp(String s)
    {
        counter = 50;
        mods.get(3).setTitle(s);
        mods.get(3).setShow(true);
    }

    public void setTemp(Boolean b)
    {
        mods.get(3).setShow(b);
    }

    public void render()
    {
        if (Config.getInstance().disabled)
        {
            return;
        }
        counter--;
        if (Config.getInstance().showHud && Tools.getMinecraft().inGameHasFocus && !Tools.getMinecraft().gameSettings.showDebugInfo)
        {
            int slot = 5;
            for (DFEntry d : mods)
            {
                if (d.isShown())
                {
                    if (Config.getInstance().textShadow)
                    {
                        Tools.getMinecraft().fontRendererObj.drawStringWithShadow(d.getTitle(), 5, slot, 0xFFFFFF);
                    }
                    else
                    {
                        Tools.getMinecraft().fontRendererObj.drawString(d.getTitle(), 5, slot, 0xFFFFFF);
                    }
                    slot += 10;
                }
            }
        }
        if (counter <= 0)
        {
            setTemp(false);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public int addModStatus(String s)
    {
        int i = mods.size();
        mods.add(i, new DFEntry(s, false));
        return i;
    }

    @SuppressWarnings("unused")
    @Override
    public void removeModStatus(int id)
    {
        if (id < mods.size() && id > 3)
        {
            mods.remove(id);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void setStatusVisibility(int id, boolean b)
    {
        if (id < mods.size() && id > 3)
        {
            mods.get(id).setShow(b);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void setStatus(int id, String s)
    {
        if (id < mods.size() && id > 3)
        {
            mods.get(id).setTitle(s);
        }
    }

}
