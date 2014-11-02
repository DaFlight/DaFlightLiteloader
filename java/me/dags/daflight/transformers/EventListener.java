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

package me.dags.daflight.transformers;

import com.mumfrey.liteloader.transformers.event.EventInfo;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;
import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.abstraction.MinecraftGame;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author dags_ <dags@dags.me>
 */

public class EventListener extends MinecraftGame
{

    private static long time = 0L;

    public static void onFovCheck(ReturnEventInfo<EntityRenderer, Float> e, float arg1, boolean arg)
    {
        if (LiteModDaFlight.DAPLAYER.flyModOn)
        {
            time = System.currentTimeMillis();
            e.setReturnValue(getGameSettings().fovSetting);
            e.cancel();
        }
        else if (System.currentTimeMillis() - time < 500)
        {
            e.setReturnValue(getGameSettings().fovSetting);
            e.cancel();
        }
    }

    public static void onFall(EventInfo<EntityPlayer> e, float distance, float damageMultiplier)
    {
        if (LiteModDaFlight.DAPLAYER.softFall())
        {
            e.cancel();
        }
    }

}
