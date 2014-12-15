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

package me.dags.daflight;

import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import me.dags.daflight.api.DaFlightUI;
import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.ui.ConfigGUI;
import me.dags.daflight.ui.hud.HUD;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.GlobalConfig;
import me.dags.daflight.utils.PluginChannelUtil;
import me.dags.daflight.utils.Tools;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.S01PacketJoinGame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public class LiteModDaFlight implements Tickable, HUDRenderListener, Configurable, JoinGameListener, PluginChannelListener
{
    public static final DaPlayer DAPLAYER = new DaPlayer();
    public static boolean wasInGame = false;
    private static HUD hud;

    @Override
    public String getName()
    {
        return "DaFlight";
    }

    @Override
    public String getVersion()
    {
        return "2.0b6";
    }

    @Override
    public void init(File configPath)
    {
        Config.getInstance();
        Config.applySettings();
        Config.applyDefaults();
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return ConfigGUI.class;
    }

    @Override
    public void onTick(Minecraft m, float t, boolean inGame, boolean clock)
    {
        if (clock && !inGame && wasInGame)
        {
            wasInGame = false;
            Config.reloadConfig();
            Config.applySettings();
        }
        if (Config.getInstance().disabled)
        {
            if (clock)
                DAPLAYER.disableAll();
        }
        else if (inGame)
        {
            DAPLAYER.update();
            wasInGame = true;
            if (clock)
                DAPLAYER.tickUpdate();
        }
    }

    @Override
    public void onPreRenderHUD(int screenWidth, int screenHeight)
    {
    }

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight)
    {
        getHud().render();
    }

    public static HUD getHud()
    {
        if (hud == null)
        {
            hud = new HUD();
        }
        return hud;
    }

    @Override
    public List<String> getChannels()
    {
        Tools.log("Registering DaFlight channel listener");
        List<String> channel = new ArrayList<String>();
        channel.add("DaFlight");
        return channel;
    }

    @Override
    public void onCustomPayload(String channel, int length, byte[] data)
    {
        PluginChannelUtil.onReceivedPacket(channel, data);
    }

    @Override
    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket)
    {
        DAPLAYER.onGameJoin();
        if (GlobalConfig.perServerConfig())
        {
            Config.loadServerConfig();
            Config.applySettings();
            Tools.tellPlayer("Server config loaded for: " + MinecraftGame.getServerData().serverIP);
        }
    }

    @SuppressWarnings("unused")
    public static DaFlightUI getDaFlightUI()
    {
        return getHud();
    }
}