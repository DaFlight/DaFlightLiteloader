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

package me.dags.daflightliteloader.minecraft.transformers;

import com.mumfrey.liteloader.core.runtime.Obf;

/**
 * @author dags_ <dags@dags.me>
 */

public class ObfTable extends Obf
{
    public static ObfTable EntityRenderer = new ObfTable("net.minecraft.client.renderer.EntityRenderer", "blt");
    public static ObfTable setupViewBobbing = new ObfTable("func_78475_f", "g", "setupViewBobbing");

    public static ObfTable World = new ObfTable("net.minecraft.world.World", "ahb");
    public static ObfTable getCollidingBoundingBoxes = new ObfTable("func_72945_a", "a", "getCollidingBoundingBoxes");

    public ObfTable(String seargeName, String obfName)
    {
        super(seargeName, obfName, seargeName);
    }

    public ObfTable(String seargeName, String obfName, String mcpName)
    {
        super(seargeName, obfName, mcpName);
    }
}
