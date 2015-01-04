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

package me.dags.daflight;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import me.dags.daflight.gui.LiteloaderMenu;
import me.dags.daflight.gui.hud.HUD;
import me.dags.daflight.input.KeybindHandler;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflight.messaging.PluginChannelUtil;
import me.dags.daflight.minecraft.MCGame;
import me.dags.daflight.player.DaPlayer;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.GlobalConfig;
import me.dags.daflight.utils.Tools;
import me.dags.daflightapi.DaFlightAPI;
import me.dags.daflightapi.ui.DaFlightUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public class LiteModDaFlight implements Tickable, HUDRenderListener, Configurable, JoinGameListener, PluginChannelListener, DaFlightAPI
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
        return "2.1";
    }

    @Override
    public void init(File configPath)
    {
        Config.getInstance();
        Config.applySettings();
        GlobalConfig.applyDefaults();
        LiteLoader.getInput().registerKeyBinding(KeyBinds.MENU_BINDING);
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return LiteloaderMenu.class;
    }

    @Override
    public void onTick(Minecraft m, float t, boolean inGame, boolean clock)
    {
        if (clock)
        {
            KeybindHandler.checkMenuKey();
            if (!inGame && wasInGame)
            {
                wasInGame = false;
                Config.reloadConfig();
                Config.applySettings();
            }
            if (Config.getInstance().disabled)
                DAPLAYER.disableAll();
        }
        if (inGame && !Config.getInstance().disabled)
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
    public void onCustomPayload(String channel, PacketBuffer data)
    {
        PluginChannelUtil.onReceivedPacket(channel, data.array());
    }

    @Override
    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer)
    {
        DAPLAYER.onGameJoin();
        if (GlobalConfig.perServerConfig() && !MCGame.getMinecraft().isSingleplayer())
        {
            Config.loadServerConfig();
            Config.applySettings();
            Tools.tellPlayer("Server config loaded for: " + serverData.serverIP);
        }
    }

    @Override
    public DaFlightUI getDaFlightUI()
    {
        return getHud();
    }
}
