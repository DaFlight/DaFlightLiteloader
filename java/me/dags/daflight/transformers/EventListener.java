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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author dags_ <dags@dags.me>
 */

@SuppressWarnings("unused")
public class EventListener extends MinecraftGame
{
    // Prevents FOV changes when toggling flight on
    // Updates player's fly state if needed
    @SuppressWarnings("unused")
    public static void onFovCheck(ReturnEventInfo<AbstractClientPlayer, Float> e)
    {
        if (LiteModDaFlight.DAPLAYER.flyModOn)
        {
            e.setReturnValue(1.0F);
            e.cancel();
            if (!e.getSource().capabilities.isFlying)
            {
                e.getSource().capabilities.isFlying = true;
                e.getSource().sendPlayerAbilities();
            }
        }
    }

    // Intercepts outbound movement packets to tell the server the player is on the ground (prevents fall damage)
    @SuppressWarnings("unused")
    public static void onUpdateWalkingPlayer(EventInfo<EntityPlayerSP> e)
    {
        if (LiteModDaFlight.DAPLAYER.softFallOn())
        {
            EntityPlayerSP ep = e.getSource();
            e.getSource().sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(ep.posX, ep.getEntityBoundingBox().minY, ep.posZ, ep.rotationYaw, ep.rotationPitch, /*onGround*/ true));
            e.cancel();
            ep.isOnLadder();
        }
    }

    // Intercepts falling
    @SuppressWarnings("unused")
    public static void onFall(EventInfo<EntityPlayer> e, float distance, float damageMultiplier)
    {
        if (LiteModDaFlight.DAPLAYER.softFallOn())
        {
            e.cancel();
        }
    }

    // Ignore ladder effects if player is flying
    @SuppressWarnings("unused")
    public static void isOnLadder(ReturnEventInfo<EntityLivingBase, Boolean> e)
    {
        System.out.println(".");
        if (e.getSource().getEntityId() == getPlayer().getEntityId() && LiteModDaFlight.DAPLAYER.flyModOn)
        {
            e.setReturnValue(false);
        }
    }
}
