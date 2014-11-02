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

public class Vector
{

    private volatile double x;
    private volatile double y;
    private volatile double z;
    private volatile double speed;

    private volatile boolean hasInput;
    private volatile boolean hasLateralInput;

    public Vector()
    {
        this.x = 0D;
        this.y = 0D;
        this.z = 0D;
        this.speed = 1D;
        this.hasInput = false;
        this.hasLateralInput = false;
    }

    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = 1D;
        this.hasInput = false;
        this.hasLateralInput = false;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public double getSpeed()
    {
        return this.speed;
    }

    public boolean hasInput()
    {
        return this.hasInput;
    }

    public boolean hasLateralInput()
    {
        return this.hasLateralInput;
    }

    public void add(double newX, double newY, double newZ)
    {
        x += newX;
        y += newY;
        z += newZ;
    }

    public void set(double newX, double newY, double newZ)
    {
        x = newX;
        y = newY;
        z = newZ;
    }

    public void multiply(double factor)
    {
        x *= factor;
        y *= factor;
        z *= factor;
    }

    public void setSpeed(double d)
    {
        this.speed = d;
    }

    public void setHasInput(boolean b)
    {
        this.hasInput = b;
    }

    public void setHasLateralInput(boolean b)
    {
        this.hasLateralInput = b;
    }

    public void reset()
    {
        x = 0;
        y = 0;
        z = 0;
    }

}
