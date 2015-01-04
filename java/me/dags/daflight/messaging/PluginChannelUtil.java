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

package me.dags.daflight.messaging;

import com.mumfrey.liteloader.core.ClientPluginChannels;
import com.mumfrey.liteloader.core.PluginChannels;
import io.netty.buffer.Unpooled;
import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.utils.Tools;
import net.minecraft.network.PacketBuffer;

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
        ClientPluginChannels.sendMessage("DaFlight", new PacketBuffer(Unpooled.copiedBuffer(out.getData())), PluginChannels.ChannelPolicy.DISPATCH_ALWAYS);
    }

}
