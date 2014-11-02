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
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author dags_ <dags@dags.me>
 */

public class EventListener extends MinecraftGame
{

    // seems to be broken outside of the dev environment
    public static void onQueuePacket(EventInfo<NetHandlerPlayClient> e, Packet packet)
    {
        if (packet instanceof C03PacketPlayer)
        {
            C03PacketPlayer p = (C03PacketPlayer) packet;
            if (p.func_149465_i() || !LiteModDaFlight.DAPLAYER.softFall())
            {
                return;
            }
            if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition)
            {
                p = new C03PacketPlayer.C04PacketPlayerPosition(p.getPositionX(), p.getPositionY(), p.getPositionZ(), true);
                e.getSource().addToSendQueue(p);
                e.cancel();
            }
            else if (packet instanceof C03PacketPlayer.C05PacketPlayerLook)
            {
                p = new C03PacketPlayer.C05PacketPlayerLook(p.getPitch(), p.getYaw(), true);
                e.getSource().addToSendQueue(p);
                e.cancel();
            }
            else if (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)
            {
                p = new C03PacketPlayer.C06PacketPlayerPosLook(p.getPositionX(), p.getPositionY(), p.getPositionZ(), p.getPitch(), p.getYaw(), true);
                e.getSource().addToSendQueue(p);
                e.cancel();
            }
        }
    }

    public static void onFall(EventInfo<EntityPlayer> e, float distance, float damageMultiplier)
    {
        if (LiteModDaFlight.DAPLAYER.softFall())
        {
            e.cancel();
        }
    }

    public static void onFovCheck(ReturnEventInfo<AbstractClientPlayer, Float> e)
    {
        if (LiteModDaFlight.DAPLAYER.flyModOn)
        {
            e.setReturnValue(1.0F);
            e.cancel();
        }
    }

}
