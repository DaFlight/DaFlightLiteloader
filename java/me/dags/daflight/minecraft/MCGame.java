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

package me.dags.daflight.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.*;

/**
 * @author dags_ <dags@dags.me>
 */
public class MCGame
{
    private static ScaledResolution scaledResolution;
    private static int lastGuiScale = 0;
    private static int lastWidth = 0;
    private static int lastHeight = 0;

    public static Minecraft getMinecraft()
    {
        return Minecraft.getMinecraft();
    }

    public static GameSettings getGameSettings()
    {
        return getMinecraft().gameSettings;
    }

    public static GameSettings.Options getOptions()
    {
        return GameSettings.Options.getEnumOptions(0);
    }

    public static IChatComponent getMessage(String s)
    {
        IChatComponent message = new ChatComponentText("[DaFlight] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE));
        message.appendSibling(new ChatComponentText(s).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        return message;
    }

    public static void tellPlayer(String msg)
    {
        getPlayer().addChatMessage(getMessage(msg));
    }

    public static boolean onSolidBlock()
    {
        BlockPos pos = new BlockPos(getPlayer().posX, getPlayer().lastTickPosY, getPlayer().posZ);
        return getMinecraft().theWorld.getBlockState(pos.down()).getBlock().getMaterial().isSolid();
    }

    public static EntityPlayerSP getPlayer()
    {
        return getMinecraft().thePlayer;
    }

    public static ServerData getServerData()
    {
        return getMinecraft().getCurrentServerData();
    }

    public static ScaledResolution getScaledResolution()
    {
        if (screenSizeChanged())
        {
            lastWidth = getMinecraft().displayWidth;
            lastHeight = getMinecraft().displayHeight;
            lastGuiScale = getGameSettings().guiScale;
            scaledResolution = new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
        }
        return scaledResolution;
    }

    public static boolean screenSizeChanged()
    {
        return scaledResolution == null || getMinecraft().displayWidth != lastWidth || getMinecraft().displayHeight != lastHeight || getGameSettings().guiScale != lastGuiScale;
    }
}
