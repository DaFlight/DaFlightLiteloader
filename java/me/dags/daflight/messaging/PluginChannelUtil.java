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

package me.dags.daflight.messaging;

import com.mumfrey.liteloader.core.ClientPluginChannels;
import com.mumfrey.liteloader.core.PluginChannels;
import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.utils.Tools;

public class PluginChannelUtil
{

    public static void onReceivedPacket(String channel, byte[] data)
    {
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
                            Tools.tellPlayer("Fly/Sprint mod disabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setMovementModsEnabled(false);
                        LiteModDaFlight.DAPLAYER.disableMovementMods();
                    }
                    else if (value == 1)
                    {
                        if (!DaPlayer.DF_PERMISSIONS.flyEnabled())
                        {
                            Tools.tellPlayer("Fly/Sprint mod enabled!");
                        }
                        DaPlayer.DF_PERMISSIONS.setMovementModsEnabled(true);
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
                        dispatchPacket(PacketData.MOD_ON);
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

    public static void dispatchPacket(PacketData out)
    {
        ClientPluginChannels.sendMessage("DaFlight", out.getData(), PluginChannels.ChannelPolicy.DISPATCH_ALWAYS);
    }

}
