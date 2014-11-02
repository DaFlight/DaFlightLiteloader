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

public class Speed
{

    private boolean boost;
    private Double baseSpeed;
    private Double multiplier;
    private Double totalSpeed;
    private Double maxSpeed;

    public Speed()
    {
        baseSpeed = 0.11;
        multiplier = 1.0;
        totalSpeed = 1.0;
        maxSpeed = 50D;
        boost = false;
    }

    public Double getTotalSpeed()
    {
        return totalSpeed;
    }

    public Double getBaseSpeed()
    {
        return baseSpeed;
    }

    public Double getMultiplier()
    {
        return multiplier;
    }

    public boolean isBoost()
    {
        return boost;
    }

    public void toggleBoost()
    {
        boost = !boost;
        update();
    }

    public void setBoost(boolean b)
    {
        boost = b;
        update();
    }

    public void incBaseSpeed()
    {
        if (10 * (baseSpeed + 0.01) * multiplier <= maxSpeed && baseSpeed * 10 <= 4.9D)
        {
            baseSpeed += 0.01;
        }
        update();
    }

    public void decBaseSpeed()
    {
        if (baseSpeed > 0.02)
        {
            baseSpeed -= 0.01;
        }
        update();
    }

    public void incMultiplier()
    {
        if (10 * baseSpeed * (multiplier + 0.1) <= maxSpeed && multiplier <= 9.9D)
        {
            multiplier += 0.1;
        }
        update();
    }

    public void decMultiplier()
    {
        if (multiplier > 1.0)
        {
            multiplier -= 0.1;
        }
        update();
    }

    public void setBaseSpeed(Double d)
    {
        if (10 * d * multiplier <= maxSpeed)
        {
            baseSpeed = d;
        }
        else
        {
            baseSpeed = 0.11;
        }
        update();
    }

    public void setMultiplier(Double d)
    {
        if (10 * baseSpeed * d <= maxSpeed)
        {
            multiplier = d;
        }
        else
        {
            multiplier = 1.1;
        }
        update();
    }

    public void setMaxSpeed(Double d)
    {
        maxSpeed = d;
        update();
    }

    private void update()
    {
        if (boost)
        {
            totalSpeed = baseSpeed * multiplier * 5;
        }
        else
        {
            totalSpeed = baseSpeed * 5;
        }

        if (totalSpeed * 2 > maxSpeed)
        {
            baseSpeed = 0.11;
            multiplier = 1.0;
            totalSpeed = baseSpeed * 5;
        }
    }

}
