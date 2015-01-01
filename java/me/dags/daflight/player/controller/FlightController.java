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

import me.dags.daflight.minecraft.MCGame;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.player.Vector;
import me.dags.daflight.utils.Config;

public class FlightController extends MCGame implements IController
{
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
            getPlayer().setVelocity(getPlayer().motionX * smoothing, 0, getPlayer().motionZ * smoothing);
        }
        if (getPlayer().movementInput.jump && !jumpyKeyIsFlyUp())
            getPlayer().motionY -= 0.15D;
        if (getPlayer().movementInput.sneak && !sneakKeyIsFlyDown())
            getPlayer().motionY += 0.15D;
    }

    @Override
    public void unFocused()
    {
        double smoothing = Config.getInstance().flySmoothing;
        getPlayer().setVelocity(getPlayer().motionX * smoothing, getPlayer().motionY * smoothing, getPlayer().motionZ * smoothing);
    }

    private boolean jumpyKeyIsFlyUp()
    {
        return DaPlayer.KEY_BINDS.flyUp.getId() == getGameSettings().keyBindJump.getKeyCode();
    }

    private boolean sneakKeyIsFlyDown()
    {
        return DaPlayer.KEY_BINDS.flyDown.getId() == getGameSettings().keyBindSneak.getKeyCode();
    }
}
