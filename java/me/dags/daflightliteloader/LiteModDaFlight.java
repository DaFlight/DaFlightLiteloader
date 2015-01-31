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

package me.dags.daflightliteloader;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import me.dags.daflight.DaFlight;
import me.dags.daflight.DaFlightMod;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflightapi.DaFlightAPI;
import me.dags.daflightapi.IDaFlightMod;
import me.dags.daflight.messaging.ChannelMessaging;
import me.dags.daflight.messaging.MessageHandler;
import me.dags.daflightapi.ui.DaFlightUI;
import me.dags.daflightliteloader.gui.UIHelper8;
import me.dags.daflightliteloader.gui.LiteloaderMenu;
import me.dags.daflightliteloader.messaging.MessageDispatcher;
import me.dags.daflightliteloader.minecraft.MCGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;

import java.io.File;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public class LiteModDaFlight implements DaFlightAPI, Tickable, HUDRenderListener, Configurable, JoinGameListener, PluginChannelListener
{
    private final IDaFlightMod DAFLIGHT_MOD = new DaFlightMod();

    @Override
    public String getName()
    {
        return DAFLIGHT_MOD.getName();
    }

    @Override
    public String getVersion()
    {
        return DAFLIGHT_MOD.getVersion();
    }

    @Override
    public void init(File configPath)
    {
        DAFLIGHT_MOD.onInit(new MCGame(), new ChannelMessaging(new MessageHandler(), new MessageDispatcher()), new UIHelper8(), configPath);
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
        DAFLIGHT_MOD.onTick(clock, inGame);
    }

    @Override
    public void onPreRenderHUD(int screenWidth, int screenHeight)
    {
    }

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight)
    {
        DAFLIGHT_MOD.onRender();
    }

    @Override
    public List<String> getChannels()
    {
        log("Registering DaFlight channel listener");
        return DAFLIGHT_MOD.getPluginChannels();
    }

    @Override
    public void onCustomPayload(String channel, PacketBuffer data)
    {
        DAFLIGHT_MOD.onPluginMessage(channel, data.array());
    }

    @Override
    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer)
    {
        DAFLIGHT_MOD.onJoinGame();
    }

    public static void log(String msg)
    {
        LiteLoaderLogger.info("[DaFlight] " + msg);
    }

    @Override
    public DaFlightUI getDaFlightUI()
    {
        return DaFlight.getHud();
    }
}
