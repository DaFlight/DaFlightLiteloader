/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.dags.daflightliteloader.minecraft.transformers;

import com.mumfrey.liteloader.transformers.event.EventInfo;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;
import me.dags.daflight.DaFlight;
import me.dags.daflight.utils.Config;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;

/**
 * @author dags_ <dags@dags.me>
 */

@SuppressWarnings("unused")
public class EventListener
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
        if (DaFlight.get().daPlayer.flyModOn)
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

    // Disables viewbobbing whilst flying
    @SuppressWarnings("unused")
    public static void onSetupViewBobbing(EventInfo<EntityRenderer> e, float f)
    {
        if (DaFlight.get().daPlayer.flyModOn)
        {
            e.cancel();
        }
    }

    // Intercepts falling
    @SuppressWarnings("unused")
    public static void onFall(EventInfo<EntityPlayer> e, float distance, float damageMultiplier)
    {
        if (DaFlight.get().daPlayer.softFallOn())
        {
            e.cancel();
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
        if (DaFlight.get().daPlayer.softFallOn())
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

    @SuppressWarnings("unused")
    public static void onJump(EventInfo<EntityPlayer> e)
    {
        if (DaFlight.get().daPlayer.sprintModOn && !e.getSource().capabilities.isFlying)
        {
            e.getSource().motionY = 0.42F + 1.25 * Config.getInstance().jumpModifier * DaFlight.get().daPlayer.getSpeed();
            e.cancel();
        }
    }
}
