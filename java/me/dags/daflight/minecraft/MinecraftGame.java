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

package me.dags.daflight.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

/**
 * @author dags_ <dags@dags.me>
 */
public class MinecraftGame
{

    private static ScaledResolution scaledResolution;
    private static int lastGuiScale = 0;
    private static int lastWidth = 0;
    private static int lastHeight = 0;

    /**
     * Minecraft methods
     */

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

    public static ChatComponentText getMessage(String s)
    {
        String format = Colour.DARK_PURPLE + "[DaFlight]" + Colour.GREY + " %2$s";
        return new ChatComponentText(format.replace("%2$s", s));
    }

    public static boolean onSolidBlock()
    {
        int x = floorDouble(getPlayer().posX);
        int y = floorDouble(getPlayer().lastTickPosY) - 2;
        int z = floorDouble(getPlayer().posZ);
        return getMinecraft().theWorld.getBlock(x, y, z).getMaterial().isSolid();
    }

    public static int floorDouble(double d)
    {
        return MathHelper.floor_double(d);
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
