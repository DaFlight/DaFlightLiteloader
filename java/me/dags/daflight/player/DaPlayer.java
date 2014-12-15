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

package me.dags.daflight.player;

import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.input.KeybindHandler;
import me.dags.daflight.input.MovementHandler;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.player.controller.CineFlightController;
import me.dags.daflight.player.controller.FlightController;
import me.dags.daflight.player.controller.IController;
import me.dags.daflight.player.controller.SprintController;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.GlobalConfig;
import me.dags.daflight.utils.PluginChannelUtil;

/**
 * @author dags_ <dags@dags.me>
 */

public class DaPlayer extends MinecraftGame
{

    public static final KeyBinds KEY_BINDS = new KeyBinds();
    public static final DFPermissions DF_PERMISSIONS = new DFPermissions();

    public Speed sprintSpeed;
    public Speed flySpeed;

    public boolean flyModOn = false;
    public boolean sprintModOn = false;
    public boolean cineFlightOn = false;
    public boolean fullBrightOn = false;

    public Direction direction;
    public Vector movementVector;
    private IController controller;

    private boolean inMenus = true;
    private boolean wasFlying = false;
    private int softFallTicks = 0;

    public DaPlayer()
    {
        direction = new Direction();
        movementVector = new Vector();

        flySpeed = new Speed();
        flySpeed.setBaseSpeed(Config.getInstance().flySpeed);
        flySpeed.setMultiplier(Config.getInstance().flySpeedMult);

        sprintSpeed = new Speed();
        sprintSpeed.setBaseSpeed(Config.getInstance().sprintSpeed);
        sprintSpeed.setMultiplier(Config.getInstance().sprintSpeedMult);
    }

    public void onGameJoin()
    {
        DF_PERMISSIONS.resetPermissions();
        flySpeed.setMaxSpeed(50D);
        sprintSpeed.setMaxSpeed(50D);
        PluginChannelUtil.dispatchPacket(new byte[]{1});
    }

    public void tickUpdate()
    {
        if (getMinecraft().inGameHasFocus)
        {
            if (wasFlying && onSolidBlock())
            {
                wasFlying = false;
                softFallTicks = 5;
            }
            softFallTicks--;
        }
    }

    public void update()
    {
        if (getMinecraft().inGameHasFocus)
        {
            if (inMenus)
            {
                inMenus = false;
                KEY_BINDS.updateMovementKeys();
            }
            KeybindHandler.handleInput(this);
            if (isModOn() && controller != null)
            {
                MovementHandler.handleMovementInput(this);
                controller.input(movementVector);
            }
        }
        else
        {
            if (!inMenus)
            {
              inMenus = true;
            }
            if (isModOn() && controller != null)
            {
                controller.unFocused();
            }
        }
    }

    public void toggleFlight()
    {
        flyModOn = DF_PERMISSIONS.flyEnabled() && !flyModOn;
        toggleFlightCommon();
        toggleViewBobbing();
        if (!flyModOn && !Config.getInstance().speedIsToggle)
        {
            flySpeed.setBoost(false);
        }
        if (!flyModOn)
        {
            getPlayer().capabilities.isFlying = false;
            getPlayer().sendPlayerAbilities();
            if (cineFlightOn)
            {
                getGameSettings().smoothCamera = false;
            }
        }
        if (flyModOn && cineFlightOn)
        {
            getGameSettings().smoothCamera = true;
        }
    }

    public void toggleCineFlight()
    {
        if (flyModOn)
        {
            cineFlightOn = !cineFlightOn;
            getMinecraft().gameSettings.smoothCamera = cineFlightOn;
        }
        toggleFlightCommon();
    }

    public void toggleSprint()
    {
        sprintModOn = DF_PERMISSIONS.sprintEnabled() && !sprintModOn;
        controller = getActiveController();
        if (!sprintModOn && !Config.getInstance().speedIsToggle)
        {
            sprintSpeed.setBoost(false);
        }
    }

    public void toggleSpeedModifier()
    {
        if (flyModOn)
        {
            flySpeed.toggleBoost();
        }
        else if (sprintModOn)
        {
            sprintSpeed.toggleBoost();
        }
    }

    private void toggleFlightCommon()
    {
        controller = getActiveController();
        if (!flyModOn && !cineFlightOn)
        {
            getPlayer().capabilities.isFlying = false;
            getPlayer().sendPlayerAbilities();
        }
    }

    public void toggleViewBobbing()
    {
        if (flyModOn)
        {
            // back up user's viewBob setting
            GlobalConfig.getInstance().viewBobbing = getMinecraft().gameSettings.viewBobbing;
            GlobalConfig.saveSettings();
            // disable viewBobbing & save
            getMinecraft().gameSettings.viewBobbing = false;
            getMinecraft().gameSettings.saveOptions();
        }
        else
        {
            // reset viewBobbing to user's backed-up setting & save
            getMinecraft().gameSettings.viewBobbing = GlobalConfig.getInstance().viewBobbing;
            getMinecraft().gameSettings.saveOptions();
        }
    }

    public void toggleFullbright()
    {
        float brightness = 9999F;
        if (fullBrightOn || !DF_PERMISSIONS.fbEnabled())
        {
            fullBrightOn = false;
            brightness = GlobalConfig.getInstance().brightness;
        }
        else
        {
            fullBrightOn = true;
            GlobalConfig.getInstance().brightness = getMinecraft().gameSettings.gammaSetting;
            GlobalConfig.saveSettings();
        }
        getMinecraft().gameSettings.gammaSetting = brightness;
    }

    public void disableAll()
    {
        if (getMinecraft().inGameHasFocus)
        {
            if (flyModOn)
            {
                toggleFlight();
            }
            if (sprintModOn)
            {
                toggleFlight();
            }
            if (fullBrightOn)
            {
                toggleFullbright();
            }
        }
    }

    public void disableMovementMods()
    {
        if (flyModOn)
        {
            toggleFlight();
        }
        if (sprintModOn)
        {
            toggleSprint();
        }
        flySpeed.setBoost(false);
        sprintSpeed.setBoost(false);
        LiteModDaFlight.getHud().updateMsg();
    }

    public boolean softFallOn()
    {
        if (Config.getInstance().disabled || !DF_PERMISSIONS.noFallDamageEnabled())
        {
            return false;
        }
        if (flyModOn || sprintModOn)
        {
            return wasFlying = true;
        }
        return wasFlying || softFallTicks > 0 || (wasFlying = false);
    }

    private IController getActiveController()
    {
        return flyModOn && cineFlightOn ? new CineFlightController() : flyModOn ? new FlightController() : sprintModOn ? new SprintController() : null;
    }

    private boolean isModOn()
    {
        return flyModOn || cineFlightOn || sprintModOn;
    }

    public boolean is3DFlightOn()
    {
        return Config.getInstance().threeDFlight;
    }

    public double getSpeed()
    {
        if (flyModOn)
        {
            return flySpeed.getTotalSpeed();
        }
        else if (sprintModOn)
        {
            return sprintSpeed.getTotalSpeed();
        }
        return 1D;
    }

}
