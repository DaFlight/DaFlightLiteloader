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

/**
 * @author dags_ <dags@dags.me>
 */

public class DFPermissions
{

    private boolean canFly = true;
    private boolean canSprint = true;
    private boolean canFullbright = true;
    private boolean noFallDamage = true;

    public boolean flyEnabled()
    {
        return canFly;
    }

    public boolean sprintEnabled()
    {
        return canSprint;
    }

    public boolean fbEnabled()
    {
        return canFullbright;
    }

    public boolean noFallDamageEnabled()
    {
        return noFallDamage;
    }

    public void resetPermissions()
    {
        canFly = true;
        canSprint = true;
        canFullbright = true;
        noFallDamage = true;
    }

    public void setMovementModsEnabled(boolean b)
    {
        canFly = b;
        canSprint = b;
    }

    public void setFullbrightEnabled(boolean b)
    {
        canFullbright = b;
    }

    public void setNoFallDamage(boolean b)
    {
        noFallDamage = b;
    }
}
