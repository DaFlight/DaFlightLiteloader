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
import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.utils.Config;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;

/**
 * @author dags_ <dags@dags.me>
 */

@SuppressWarnings("unused")
public class EventListener extends MinecraftGame
{
    // Prevents FOV changes when toggling flightStatus on
    // Updates player's fly state if needed
    @SuppressWarnings("unused")
    public static void onFovCheck(ReturnEventInfo<AbstractClientPlayer, Float> e)
    {
        if (Config.getInstance().disabled)
        {
            return;
        }
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
        if (e.getSource().getEntityId() == getPlayer().getEntityId() && LiteModDaFlight.DAPLAYER.flyModOn)
        {
            e.setReturnValue(false);
        }
    }

    private static int ticksSinceMovePacket = 0;
    private static boolean wasSneaking = false;
    private static double oldPosX;
    private static double oldMinY;
    private static double oldPosZ;
    private static double oldRotationYaw;
    private static double oldRotationPitch;

    // Intercepts outbound movement packets to tell the server the player is on the ground (prevents fall damage)
    @SuppressWarnings("unused")
    public static void onUpdateWalkingPlayer(EventInfo<EntityPlayerSP> e)
    {
        if (LiteModDaFlight.DAPLAYER.softFallOn())
        {
            EntityPlayerSP ep = e.getSource();
            boolean sneaking = ep.isSneaking();
            if (sneaking != wasSneaking)
            {
                if (sneaking)
                {
                    ep.sendQueue.addToSendQueue(new C0BPacketEntityAction(ep, C0BPacketEntityAction.Action.START_SNEAKING));
                }
                else
                {
                    ep.sendQueue.addToSendQueue(new C0BPacketEntityAction(ep, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
                wasSneaking = sneaking;
            }
            double xChange = ep.posX - oldPosX;
            double yChange = ep.getEntityBoundingBox().minY - oldMinY;
            double zChange = ep.posZ - oldPosZ;
            double rotationChange = ep.rotationYaw - oldRotationYaw;
            double pitchChange = ep.rotationPitch - oldRotationPitch;
            boolean sendMovementUpdate = xChange * xChange + yChange * yChange + zChange * zChange > 9.0E-4D || ticksSinceMovePacket >= 20;
            boolean sendLookUpdate = rotationChange != 0.0D || pitchChange != 0.0D;
            if (sendMovementUpdate && sendLookUpdate)
            {
                ep.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(ep.posX, ep.getEntityBoundingBox().minY, ep.posZ, ep.rotationYaw, ep.rotationPitch, true));
            }
            else if (sendMovementUpdate)
            {
                ep.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ep.posX, ep.getEntityBoundingBox().minY, ep.posZ, true));
            }
            else if (sendLookUpdate)
            {
                ep.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(ep.rotationYaw, ep.rotationPitch, true));
            }
            else
            {
                ep.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
            ++ticksSinceMovePacket;
            if (sendMovementUpdate)
            {
                oldPosX = ep.posX;
                oldMinY = ep.getEntityBoundingBox().minY;
                oldPosZ = ep.posZ;
                ticksSinceMovePacket = 0;
            }
            if (sendLookUpdate)
            {
                oldRotationPitch = ep.rotationPitch;
                oldRotationYaw = ep.rotationYaw;
            }
            e.cancel();
        }
    }
}
