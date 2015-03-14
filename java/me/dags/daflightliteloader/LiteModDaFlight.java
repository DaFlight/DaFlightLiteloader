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

package me.dags.daflightliteloader;

import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import me.dags.daflight.DaFlight;
import me.dags.daflight.DaFlightMod;
import me.dags.daflight.input.Binds;
import me.dags.daflight.messaging.ChannelMessaging;
import me.dags.daflight.messaging.MessageHandler;
import me.dags.daflightapi.DaFlightAPI;
import me.dags.daflightapi.ui.DaFlightUI;
import me.dags.daflightliteloader.gui.LiteloaderMenu;
import me.dags.daflightliteloader.gui.UIHelper7;
import me.dags.daflightliteloader.messaging.MessageDispatcher;
import me.dags.daflightliteloader.minecraft.MCGame;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.S01PacketJoinGame;

import java.io.File;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public class LiteModDaFlight implements Tickable, Configurable, HUDRenderListener, JoinGameListener, PluginChannelListener, DaFlightAPI
{
    private final DaFlightMod DAFLIGHT_MOD = new DaFlightMod();

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
        DAFLIGHT_MOD.onInit(new MCGame(), new ChannelMessaging(new MessageHandler(), new MessageDispatcher()), new UIHelper7(), configPath);
        LiteLoader.getInput().registerKeyBinding(Binds.MENU_BINDING);
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
        return DAFLIGHT_MOD.getPluginChannels();
    }

    @Override
    public void onCustomPayload(String channel, int length, byte[] data)
    {
        DAFLIGHT_MOD.onPluginMessage(channel, data);
    }

    @Override
    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket)
    {
        DAFLIGHT_MOD.onJoinGame();
    }

    @Override
    public DaFlightUI getDaFlightUI()
    {
        return DaFlight.getHud();
    }
}
