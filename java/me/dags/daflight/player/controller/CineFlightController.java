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

package me.dags.daflight.player.controller;

import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.abstraction.MinecraftGame;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.player.Direction;
import me.dags.daflight.player.Vector;
import me.dags.daflight.utils.Config;

public class CineFlightController extends MinecraftGame implements IController
{

    private Config c = Config.getInstance();
    private Double up;
    private Double down;
    private Double forward;
    private Double back;
    private Double left;
    private Double right;

    private Direction d;

    public CineFlightController()
    {
        d = new Direction();
        up = 0D;
        down = 0D;
        forward = 0D;
        back = 0D;
        left = 0D;
        right = 0D;
    }

    @Override
    public void input(Vector v)
    {
        setFlying();
        d.update((double) getPlayer().rotationYaw);

        double x = 0D;
        double y = 0D;
        double z = 0D;

        double pitch = getPlayer().rotationPitch;

        // FORWARD
        if (DaPlayer.KEY_BINDS.forward.keyHeld())
        {
            back = dec(back);
            if (back <= 0)
            {
                y = -1.1 * (pitch / 90);
                forward = inc(forward);
            }
            x += d.getX() * forward;
            z += d.getZ() * forward;
        }
        else
        {
            forward = dec(forward);
            x += d.getX() * forward;
            z += d.getZ() * forward;
            if (back <= 0)
                y = -1.1 * (pitch / 90);
        }
        // BACK
        if (DaPlayer.KEY_BINDS.backward.keyHeld())
        {
            forward = dec(forward);
            if (forward <= 0)
            {
                y = 1.1 * (pitch / 90);
                back = inc(back);
            }
            x += -d.getX() * back;
            z += -d.getZ() * back;
        }
        else
        {
            back = dec(back);
            x += -d.getX() * back;
            z += -d.getZ() * back;
        }
        // LEFT
        if (DaPlayer.KEY_BINDS.left.keyHeld())
        {
            right = dec(right);
            left = inc(left);
            x += d.getZ() * c.lrModifier * left;
            z += -d.getX() * c.lrModifier * left;
        }
        else
        {
            left = dec(left);
            x += d.getZ() * c.lrModifier * left;
            z += -d.getX() * c.lrModifier * left;
        }
        // RIGHT
        if (DaPlayer.KEY_BINDS.right.keyHeld())
        {
            left = dec(left);
            right = inc(right);
            x += -d.getZ() * c.lrModifier * right;
            z += d.getX() * c.lrModifier * right;
        }
        else
        {
            right = dec(right);
            x += -d.getZ() * c.lrModifier * right;
            z += d.getX() * c.lrModifier * right;
        }
        // UP
        if (DaPlayer.KEY_BINDS.flyUp.keyHeld())
        {
            up = inc(up);
            y += 1.1D * up;
        }
        else
        {
            up = dec(up);
            y += 1.1D * up;
        }
        // DOWN
        if (DaPlayer.KEY_BINDS.flyDown.keyHeld())
        {
            down = inc(down);
            y += -1.1D * down;
        }
        else
        {
            down = dec(down);
            y += -1.1D * down;
        }

        if (x == 0 & y == 0 & z == 0)
        {
            getPlayer().setVelocity(getPlayer().motionX * c.flySmoothing, getPlayer().motionY, getPlayer().motionZ * c.flySmoothing);
        }
        else
        {
            double speed = LiteModDaFlight.DAPLAYER.getSpeed();
            getPlayer().setVelocity(x * speed, y * speed, z * speed);
        }
    }

    @Override
    public void unFocused()
    {
        setFlying();
        getPlayer().setVelocity(getPlayer().motionX * c.flySmoothing, 0, getPlayer().motionZ * c.flySmoothing);
    }

    private Double dec(Double d)
    {
        if (d > 0)
            d -= 0.001;
        if (d < 0)
            d = 0D;
        return d;
    }

    private Double inc(Double d)
    {
        if (d < 1)
            d += 0.001;
        if (d < 0)
            d = 0D;
        return d;
    }
}
