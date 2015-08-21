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

package me.dags.daflightliteloader.minecraft;

import me.dags.daflightapi.minecraft.MinecraftGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

/**
 * @author dags_ <dags@dags.me>
 */
public class MCGame implements MinecraftGame
{

    private static ScaledResolution scaledResolution;
    private static int lastGuiScale = 0;
    private static int lastWidth = 0;
    private static int lastHeight = 0;

    /**
     * Minecraft methods
     */

    public Minecraft getMinecraft()
    {
        return Minecraft.getMinecraft();
    }

    public GameSettings getGameSettings()
    {
        return getMinecraft().gameSettings;
    }

    public IChatComponent getMessage(String s)
    {
        IChatComponent message = new ChatComponentText("[DaFlight] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE));
        message.appendSibling(new ChatComponentText(s).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        return message;
    }

    @Override
    public void setInvulnerable(boolean invulnerable)
    {
        getPlayer().capabilities.disableDamage = invulnerable;
        getPlayer().sendPlayerAbilities();
        EntityPlayer ep = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(getMinecraft().getSession().getUsername());
        ep.capabilities.disableDamage = invulnerable;
        ep.sendPlayerAbilities();
    }

    public void tellPlayer(String s)
    {
        getPlayer().addChatMessage(getMessage(s));
    }

    public boolean onSolidBlock()
    {
        int x = floorDouble(getPlayer().posX);
        int y = floorDouble(getPlayer().lastTickPosY) - 2;
        int z = floorDouble(getPlayer().posZ);
        return getMinecraft().theWorld.getBlock(x, y, z).getMaterial().isSolid();
    }

    public int floorDouble(double d)
    {
        return MathHelper.floor_double(d);
    }

    public EntityPlayerSP getPlayer()
    {
        return getMinecraft().thePlayer;
    }

    public ServerData getServerData()
    {
        return getMinecraft().getCurrentServerData();
    }

    @Override
    public boolean isSinglePlayer()
    {
        return getMinecraft().isSingleplayer();
    }

    public ScaledResolution getScaledResolution()
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

    public boolean screenSizeChanged()
    {
        return scaledResolution == null || getMinecraft().displayWidth != lastWidth || getMinecraft().displayHeight != lastHeight || getGameSettings().guiScale != lastGuiScale;
    }
}
