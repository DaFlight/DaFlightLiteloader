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

package me.dags.daflight.abstraction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

/**
 * @author dags_ <dags@dags.me>
 */
public class MinecraftGame
{

    public static GameSettings getGameSettings()
    {
        return getMinecraft().gameSettings;
    }

    /**
     * Minecraft methods
     */

    public static Minecraft getMinecraft()
    {
        return Minecraft.getMinecraft();
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
        BlockPos pos = new BlockPos(getPlayer().posX, getPlayer().lastTickPosY, getPlayer().posZ);
        return getMinecraft().theWorld.getBlockState(pos.down()).getBlock().getMaterial().isSolid();
    }

    public static EntityPlayerSP getPlayer()
    {
        return getMinecraft().thePlayer;
    }
}
