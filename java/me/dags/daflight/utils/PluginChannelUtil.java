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

import com.mumfrey.liteloader.core.ClientPluginChannels;
import com.mumfrey.liteloader.core.PluginChannels;
import io.netty.buffer.Unpooled;
import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.player.DaPlayer;
import net.minecraft.network.PacketBuffer;

public class PluginChannelUtil
{

    public static void onReceivedPacket(String channel, PacketBuffer pb)
    {
        byte[] data = pb.readByteArray();
        int length = data.length;
        if (channel.equals("DaFlight"))
        {
            if (length != 2)
            {
                return;
            }
            int key = data[0];
            int value = data[1];

            switch (key)
            {
                case 1:
                    // FullBright perms
                    if (value == 0)
                    {
                        if (DaPlayer.DF_PERMISSIONS.fbEnabled())
                        {
                            Tools.tellPlayer("Fullbright disabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setFullbrightEnabled(false);
                    }
                    else if (value == 1)
                    {
                        if (!DaPlayer.DF_PERMISSIONS.fbEnabled())
                        {
                            Tools.tellPlayer("Fullbright enabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setFullbrightEnabled(true);
                    }
                    break;
                case 2:
                    // FlyMod perms
                    if (value == 0)
                    {
                        if (DaPlayer.DF_PERMISSIONS.flyEnabled())
                        {
                            Tools.tellPlayer("Flymod disabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setFlightEnabled(false);
                        LiteModDaFlight.DAPLAYER.disableFly();
                    }
                    else if (value == 1)
                    {
                        if (!DaPlayer.DF_PERMISSIONS.flyEnabled())
                        {
                            Tools.tellPlayer("Flymod enabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setFlightEnabled(true);
                    }
                    break;
                case 3:
                    // NoFallDamage perms
                    if (value == 0)
                    {
                        if (DaPlayer.DF_PERMISSIONS.noFallDamageEnabled())
                        {
                            Tools.tellPlayer("Survival SoftFall disabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setNoFallDamage(false);
                    }
                    else if (value == 1)
                    {
                        if (!DaPlayer.DF_PERMISSIONS.noFallDamageEnabled())
                        {
                            Tools.tellPlayer("Survival SoftFall enabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setNoFallDamage(true);
                    }
                    break;
                case 4:
                    // Refresh perms
                    if (LiteModDaFlight.DAPLAYER.flyModOn || LiteModDaFlight.DAPLAYER.sprintModOn)
                    {
                        byte[] d = new byte[]{2, 1};
                        dispatchPacket(d);
                    }
                    break;
                case 100:
                    // MaxSpeed perms
                    Tools.tellPlayer("Max speed set by server! " + value);
                    LiteModDaFlight.DAPLAYER.flySpeed.setMaxSpeed((double) value);
                    LiteModDaFlight.DAPLAYER.sprintSpeed.setMaxSpeed((double) value);
                    LiteModDaFlight.getHud().updateMsg();
                    break;
            }
        }
    }

    public static void dispatchPacket(byte[] data)
    {
        PacketBuffer pb = new PacketBuffer(Unpooled.copiedBuffer(data));
        ClientPluginChannels.sendMessage("DaFlight", pb, PluginChannels.ChannelPolicy.DISPATCH_ALWAYS);
    }

}
