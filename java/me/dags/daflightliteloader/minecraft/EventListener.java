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

package me.dags.daflightliteloader.minecraft;

import com.mumfrey.liteloader.transformers.event.EventInfo;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;
import me.dags.daflight.DaFlight;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

@SuppressWarnings("unused")
public class EventListener extends MCGame
{
    public static void onSetupViewBobbing(EventInfo<EntityRenderer> e, float f)
    {
        if (DaFlight.get().DFController.flyModOn)
        {
            e.cancel();
        }
    }

    public static void onGetCollidingBoundingBoxes(ReturnEventInfo<World, List> e, Entity en, AxisAlignedBB bb)
    {
        if (en instanceof EntityPlayer && DaFlight.get().DFController.noClipOn && DaFlight.get().DFController.flyModOn)
        {
            e.setReturnValue(new ArrayList());
            e.cancel();
        }
    }
}
