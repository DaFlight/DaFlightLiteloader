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

package me.dags.daflight.input;

import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.input.binds.KeyBind;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.gui.hud.HUD;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.Tools;

/**
 * @author dags_ <dags@dags.me>
 */

public class KeybindHandler
{

    private static long ticker;

    public static void checkMenuKey()
    {
        if (KeyBinds.MENU_BINDING.isKeyPressed())
            KeyBinds.MENU_BINDING.displayGui();
    }

    public static void handleInput(DaPlayer daPlayer)
    {
        boolean result = false;
        for (KeyBind kb : DaPlayer.KEY_BINDS.binds)
        {
            if (kb.keyPressed())
            {
                pressed(kb, daPlayer);
                result = true;
            }
            else if (kb.keyHeld())
            {
                held(kb, daPlayer);
            }
            else if (kb.keyReleased())
            {
                released(kb, daPlayer);
                result = true;
            }
        }
        if (result)
        {
            LiteModDaFlight.getHud().updateMsg();
        }
    }

    private static void pressed(KeyBind kb, DaPlayer daPlayer)
    {
        switch (kb.getType())
        {
            case FLY:
                daPlayer.toggleFlight();
                break;
            case CINEFLIGHT:
                daPlayer.toggleCineFlight();
                break;
            case SPRINT:
                daPlayer.toggleSprint();
                break;
            case FULLBRIGHT:
                daPlayer.toggleFullbright();
                break;
            case MODIFIER:
                daPlayer.toggleSpeedModifier();
                break;
            case SPEEDUP:
                speedInc(daPlayer);
                break;
            case SPEEDDOWN:
                speedDec(daPlayer);
                break;
        }
    }

    private static void held(KeyBind kb, DaPlayer daPlayer)
    {
        boolean result = false;
        switch (kb.getType())
        {
            case FLY:
                if (!daPlayer.flyModOn)
                {
                    daPlayer.toggleFlight();
                    result = true;
                }
                break;
            case CINEFLIGHT:
                if (!daPlayer.cineFlightOn)
                {
                    daPlayer.toggleCineFlight();
                    result = true;
                }
                break;
            case SPRINT:
                if (!daPlayer.sprintModOn)
                {
                    daPlayer.toggleSprint();
                    result = true;
                }
                break;
            case FULLBRIGHT:
                if (!daPlayer.fullBrightOn)
                {
                    daPlayer.toggleFullbright();
                    result = true;
                }
                break;
            case MODIFIER:
                if (daPlayer.flyModOn && !daPlayer.flySpeed.isBoost() || daPlayer.sprintModOn && !daPlayer.sprintSpeed.isBoost())
                {
                    daPlayer.toggleSpeedModifier();
                    result = true;
                }
                break;
            case SPEEDUP:
                speedInc(daPlayer);
                break;
            case SPEEDDOWN:
                speedDec(daPlayer);
                break;
        }
        if (result)
        {
            LiteModDaFlight.getHud().updateMsg();
        }
    }

    private static void released(KeyBind kb, DaPlayer daPlayer)
    {
        HUD r = LiteModDaFlight.getHud();
        switch (kb.getType())
        {
            case FLY:
                if (daPlayer.flyModOn)
                {
                    daPlayer.toggleFlight();
                }
                break;
            case CINEFLIGHT:
                if (daPlayer.cineFlightOn)
                {
                    daPlayer.toggleCineFlight();
                }
                break;
            case SPRINT:
                if (daPlayer.sprintModOn)
                {
                    daPlayer.toggleSprint();
                }
                break;
            case FULLBRIGHT:
                if (daPlayer.fullBrightOn)
                {
                    daPlayer.toggleFullbright();
                }
                break;
            case MODIFIER:
                if (daPlayer.flyModOn)
                {
                    daPlayer.flySpeed.setBoost(false);
                }
                else if (daPlayer.sprintModOn)
                {
                    daPlayer.sprintSpeed.setBoost(false);
                }
                break;
        }
        r.updateMsg();
    }

    private static void speedInc(DaPlayer daPlayer)
    {
        if (!tick())
        {
            return;
        }
        if (DaPlayer.DF_PERMISSIONS.flyEnabled())
        {
            HUD r = LiteModDaFlight.getHud();
            if (daPlayer.flyModOn || DaPlayer.KEY_BINDS.enableFly.keyHeld())
            {
                if (daPlayer.flySpeed.isBoost() || DaPlayer.KEY_BINDS.speedModifier.keyHeld())
                {
                    daPlayer.flySpeed.incMultiplier();
                    Config.getInstance().flySpeedMult = daPlayer.flySpeed.getMultiplier();
                    r.renderTemp("X" + Tools.df1.format(Config.getInstance().flySpeedMult));
                }
                else
                {
                    daPlayer.flySpeed.incBaseSpeed();
                    Config.getInstance().flySpeed = daPlayer.flySpeed.getBaseSpeed();
                    r.renderTemp("x" + Tools.df1.format(Config.getInstance().flySpeed * 10));
                }
                Config.saveSettings();
            }
            else if (daPlayer.sprintModOn || DaPlayer.KEY_BINDS.enableSprint.keyHeld())
            {
                if (daPlayer.sprintSpeed.isBoost() || DaPlayer.KEY_BINDS.speedModifier.keyHeld())
                {
                    daPlayer.sprintSpeed.incMultiplier();
                    Config.getInstance().sprintSpeedMult = daPlayer.sprintSpeed.getMultiplier();
                    r.renderTemp("X" + Tools.df1.format(Config.getInstance().sprintSpeedMult));
                }
                else
                {
                    daPlayer.sprintSpeed.incBaseSpeed();
                    Config.getInstance().sprintSpeed = daPlayer.sprintSpeed.getBaseSpeed();
                    r.renderTemp("x" + Tools.df1.format(Config.getInstance().sprintSpeed * 10));
                }
                Config.saveSettings();
            }
        }
    }

    private static void speedDec(DaPlayer daPlayer)
    {
        if (!tick())
        {
            return;
        }
        if (DaPlayer.DF_PERMISSIONS.flyEnabled())
        {
            HUD r = LiteModDaFlight.getHud();
            if (daPlayer.flyModOn || DaPlayer.KEY_BINDS.enableFly.keyHeld())
            {
                if (daPlayer.flySpeed.isBoost() || DaPlayer.KEY_BINDS.speedModifier.keyHeld())
                {
                    daPlayer.flySpeed.decMultiplier();
                    Config.getInstance().flySpeedMult = daPlayer.flySpeed.getMultiplier();
                    r.renderTemp("X" + Tools.df1.format(Config.getInstance().flySpeedMult));
                }
                else
                {
                    daPlayer.flySpeed.decBaseSpeed();
                    Config.getInstance().flySpeed = daPlayer.flySpeed.getBaseSpeed();
                    r.renderTemp("x" + Tools.df1.format(Config.getInstance().flySpeed * 10));
                }
                Config.saveSettings();
            }
            else if (daPlayer.sprintModOn || DaPlayer.KEY_BINDS.enableSprint.keyHeld())
            {
                if (daPlayer.sprintSpeed.isBoost() || DaPlayer.KEY_BINDS.speedModifier.keyHeld())
                {
                    daPlayer.sprintSpeed.decMultiplier();
                    Config.getInstance().sprintSpeedMult = daPlayer.sprintSpeed.getMultiplier();
                    r.renderTemp("X" + Tools.df1.format(Config.getInstance().sprintSpeedMult));
                }
                else
                {
                    daPlayer.sprintSpeed.decBaseSpeed();
                    Config.getInstance().sprintSpeed = daPlayer.sprintSpeed.getBaseSpeed();
                    r.renderTemp("x" + Tools.df1.format(Config.getInstance().sprintSpeed * 10));
                }
                Config.saveSettings();
            }
        }
    }

    private static boolean tick()
    {
        if (System.currentTimeMillis() - ticker >= 50)
        {
            ticker = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
