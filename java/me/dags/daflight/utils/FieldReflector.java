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

package me.dags.daflight.utils;

import java.lang.reflect.Field;

/**
 * @author dags_ <dags@dags.me>
 */

public class FieldReflector
{
    private final Field field;
    private final String[] fieldNames;
    private int attempt = 0;

    public FieldReflector(Class owner, String[] fieldNames)
    {
        this.fieldNames = fieldNames;
        this.field = getField(owner);
    }

    public FieldReflector(Class owner, String fieldName)
    {
        this.fieldNames = new String[]{fieldName};
        this.field = getField(owner);
    }

    private Field getField(Class ownerClass)
    {
        if (this.attempt >= this.fieldNames.length)
            return null;
        Field f;
        try
        {
            f = ownerClass.getDeclaredField(this.fieldNames[this.attempt]);
            f.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            this.attempt++;
            f = getField(ownerClass);
        }
        return f;
    }

    public Field getField()
    {
        return this.field;
    }

    public void applyValue(Object owner, Object value)
    {
        if (this.field != null)
        {
            try
            {
                this.field.set(owner, value);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}
