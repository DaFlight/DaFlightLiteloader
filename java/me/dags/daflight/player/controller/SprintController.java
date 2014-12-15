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

import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.player.Vector;
import me.dags.daflight.utils.Config;

public class SprintController extends MinecraftGame implements IController
{
    @Override
    public void input(Vector v)
    {
        if (DaPlayer.KEY_BINDS.jump.keyHeld())
        {
            if (onSolidBlock())
            {
                double yMotion = 0.42F + -getPlayer().motionY + Config.getInstance().jumpModifier * v.getSpeed();
                getPlayer().addVelocity(0, yMotion, 0);
            }
        }

        if (v.hasLateralInput())
        {
            getPlayer().setVelocity(v.getX(), getPlayer().motionY, v.getZ());
        }
    }

    @Override
    public void unFocused()
    {
    }
}
