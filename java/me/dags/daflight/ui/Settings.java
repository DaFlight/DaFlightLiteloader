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

import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.input.binds.KeyBind;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.GlobalConfig;

import java.util.LinkedHashMap;
import java.util.Properties;

public class Settings
{

    private LinkedHashMap<String, String> keyBinds;
    private LinkedHashMap<String, Float> settings;
    private LinkedHashMap<String, Boolean> booleans;

    public Settings()
    {
        readSettings();
    }

    public KeyBind[] getBinds()
    {
        return DaPlayer.KEY_BINDS.binds;
    }

    public LinkedHashMap<String, String> getKeyBinds()
    {
        return this.keyBinds;
    }

    public LinkedHashMap<String, Float> getSettings()
    {
        return settings;
    }

    public LinkedHashMap<String, Boolean> getBooleans()
    {
        return booleans;
    }

    public void readSettings()
    {
        Config c = Config.getInstance();

        keyBinds = new LinkedHashMap<String, String>();
        settings = new LinkedHashMap<String, Float>();
        booleans = new LinkedHashMap<String, Boolean>();

        keyBinds.put("FullBright", c.fullBrightKey);
        keyBinds.put("Speed++", c.speedUpKey);
        keyBinds.put("Speed--", c.speedDownKey);
        keyBinds.put("FlyUp", c.upKey);
        keyBinds.put("FlyDown", c.downKey);
        keyBinds.put("Fly", c.flyKey);
        keyBinds.put("CineFlight", c.cineFlyKey);
        keyBinds.put("Sprint", c.sprintKey);
        keyBinds.put("SpeedMod", c.speedKey);

        settings.put("FlySpeed", (float) c.flySpeed);
        settings.put("FlySpeedMultiplier", (float) c.flySpeedMult);
        settings.put("FlightSmoothingFactor", (float) c.flySmoothing);
        settings.put("SprintSpeed", (float) c.sprintSpeed);
        settings.put("SprintSpeedMultiplier", (float) c.sprintSpeedMult);
        settings.put("JumpModifier", (float) c.jumpModifier);
        settings.put("Left/RightModifier", (float) c.lrModifier);

        booleans.put("PerServer", GlobalConfig.perServerConfig());
        booleans.put("Disable", c.disabled);
        booleans.put("3DFlight", c.threeDFlight);
        booleans.put("DisplayHud", c.showHud);
    }

    public void updateSetting(String k, String v)
    {
        Config c = Config.getInstance();
        if (keyBinds.containsKey(k))
        {
            if (k.equals("FlyUp"))
            {
                c.upKey = v;
            }
            else if (k.equals("FlyDown"))
            {
                c.downKey = v;
            }
            else if (k.equals("Fly"))
            {
                c.flyKey = v;
            }
            else if (k.equals("Sprint"))
            {
                c.sprintKey = v;
            }
            else if (k.equals("SpeedMod"))
            {
                c.speedKey = v;
            }
            else if (k.equals("CineFlight"))
            {
                c.cineFlyKey = v;
            }
            else if (k.equals("Speed++"))
            {
                c.speedUpKey = v;
            }
            else if (k.equals("Speed--"))
            {
                c.speedDownKey = v;
            }
            else if (k.equals("FullBright"))
            {
                c.fullBrightKey = v;
            }
            return;
        }
        if (booleans.containsKey(k))
        {
            boolean b = getBool(v);
            if (k.equals("PerServer"))
            {
                GlobalConfig.setPerServerConfig(b);
            }
            if (k.equals("Disable"))
            {
                c.disabled = b;
            }
            else if (k.equals("3DFlight"))
            {
                c.threeDFlight = b;
            }
            else if (k.equals("DisplayHud"))
            {
                c.showHud = b;
            }
            return;
        }
        if (settings.containsKey(k) && isValid(v))
        {
            Double d = Double.parseDouble(v);
            setValidSetting(k, d);
        }
    }

    public void setKeyBindToggles(String k, boolean b)
    {
        Config c = Config.getInstance();
        KeyBinds binds = DaPlayer.KEY_BINDS;
        if (k.equals("Fly"))
        {
            c.flyIsToggle = b;
        }
        else if (k.equals("Sprint"))
        {
            c.sprintIsToggle = b;
        }
        else if (k.equals("SpeedMod"))
        {
            c.speedIsToggle = b;
            if (!b)
            {
                LiteModDaFlight.DAPLAYER.flySpeed.setBoost(false);
                LiteModDaFlight.DAPLAYER.sprintSpeed.setBoost(false);
                binds.speedModifier.setState(false);
                LiteModDaFlight.getHud().updateMsg();
            }
        }
        else if (k.equals("FullBright"))
        {
            c.fullbrightIsToggle = b;
        }
    }

    private boolean isValid(String s)
    {
        try
        {
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    private float parseProp(Properties p, String s)
    {
        try
        {
            String value = p.getProperty(s);
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e)
        {
            return 0.2F;
        }
    }

    private boolean getBool(String s)
    {
        try
        {
            return Boolean.parseBoolean(s);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void setValidSetting(String s, Double d)
    {
        Config c = Config.getInstance();
        if (s.equals("FlySpeed") && ((d / 10) > 0 && (d / 10) < 0.5))
        {
            c.flySpeed = d / 10;
        }
        else if (s.equals("FlySpeedMultiplier") && (d > 0 && d <= 10))
        {
            c.flySpeedMult = d;
        }
        else if (s.equals("FlightSmoothingFactor") && (d > 0 && d <= 1.0))
        {
            c.flySmoothing = d;
        }
        else if (s.equals("SprintSpeed") && ((d / 10) > 0 && (d / 10) < 0.5))
        {
            c.sprintSpeed = d / 10;
        }
        else if (s.equals("SprintSpeedMultiplier") && (d > 0 && d <= 10))
        {
            c.sprintSpeedMult = d;
        }
        else if (s.equals("JumpModifier") && (d > 0 && d <= 1.0))
        {
            c.jumpModifier = d;
        }
        else if (s.equals("Left/RightModifier") && (d > 0 && d <= 1.0))
        {
            c.lrModifier = d;
        }
    }

}
