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
import me.dags.daflight.utils.PacketManager;
import me.dags.daflight.abstraction.MinecraftGame;
import me.dags.daflight.utils.Config;
import me.dags.daflight.input.KeybindHandler;
import me.dags.daflight.input.MovementHandler;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflight.player.controller.CineFlightController;
import me.dags.daflight.player.controller.FlightController;
import me.dags.daflight.player.controller.IController;
import me.dags.daflight.player.controller.SprintController;

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
    private long lastUpdate = 0L;
    private boolean landed = true;
    private boolean softFall = false;

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
        byte[] b = new byte[] {1};
        PacketManager.dispatchPacket(b);
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
            MovementHandler.handleMovementInput(this);
            KeybindHandler.handleInput(this);
            if (isModOn() && controller != null)
            {
                controller.input(movementVector);
            }
            checkIsOnGround();
        }
        else if (!inMenus)
        {
            inMenus = true;
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
            softFall = true;
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
        if (!sprintModOn)
        {
            softFall = true;
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
            Config.getInstance().viewBobbing = getMinecraft().gameSettings.viewBobbing;
            Config.saveSettings();
            // disable viewBobbing & save
            getMinecraft().gameSettings.viewBobbing = false;
            getMinecraft().gameSettings.saveOptions();
        }
        else
        {
            // reset viewBobbing to user's backed-up setting & save
            getMinecraft().gameSettings.viewBobbing = Config.getInstance().viewBobbing;
            getMinecraft().gameSettings.saveOptions();
        }
    }

    public void toggleFullbright()
    {
        float brightness = 9999F;
        if (fullBrightOn || !DF_PERMISSIONS.fbEnabled())
        {
            fullBrightOn = false;
            brightness = Config.getInstance().brightness;
        }
        else
        {
            fullBrightOn = true;
            Config.getInstance().brightness = getMinecraft().gameSettings.gammaSetting;
            Config.saveSettings();
        }
        getMinecraft().gameSettings.gammaSetting = brightness;
    }

    public void disableFly()
    {
        if (flyModOn)
        {
            toggleFlight();
        }
        if (sprintModOn)
        {
            toggleFlight();
        }
        flySpeed.setBoost(false);
        sprintSpeed.setBoost(false);
        LiteModDaFlight.getHud().updateMsg();
    }

    public void checkIsOnGround()
    {
        if (!softFall)
        {
            return;
        }
        if (onGround())
        {
            if (landed)
            {
                if (System.currentTimeMillis() - lastUpdate > 500)
                {
                    softFall = false;
                    landed = false;
                }
                return;
            }
            landed = true;
            lastUpdate = System.currentTimeMillis();
            return;
        }
        landed = false;
    }

    public boolean softFall()
    {
        return flyModOn || sprintModOn || softFall;
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
