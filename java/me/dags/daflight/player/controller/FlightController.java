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

import me.dags.daflight.abstraction.MinecraftGame;
import me.dags.daflight.player.Vector;
import me.dags.daflight.utils.Config;

public class FlightController extends MinecraftGame implements IController
{

    private boolean active;



    public FlightController()
    {
        this.active = false;
    }

    @Override
    public void reset()
    {

    }

    @Override
    public void input(Vector v)
    {
        if (v.hasInput())
        {
            getPlayer().setVelocity(v.getX(), v.getY(), v.getZ());
        }
        else
        {
            double smoothing = Config.getInstance().flySmoothing;
            getPlayer().setVelocity(getPlayer().motionX * smoothing, getPlayer().motionY * smoothing, getPlayer().motionZ * smoothing);
        }
        if (!getPlayer().capabilities.isFlying)
        {
            getPlayer().capabilities.isFlying = true;
        }
    }

    @Override
    public void unFocused()
    {
        double smoothing = Config.getInstance().flySmoothing;
        getPlayer().setVelocity(getPlayer().motionX * smoothing, getPlayer().motionY * smoothing, getPlayer().motionZ * smoothing);
        if (!getPlayer().capabilities.isFlying)
        {
            getPlayer().capabilities.isFlying = true;
        }
    }    @Override
    public void setActive(boolean b)
    {
        this.active = b;
    }

    @Override
    public boolean isActive()
    {
        return this.active;
    }

}
