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

package me.dags.daflight.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import me.dags.daflight.minecraft.MinecraftGame;

/**
 * @author dags_ <dags@dags.me>
 */

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "global.json")
public class GlobalConfig extends MinecraftGame implements Exposable
{
    private static GlobalConfig instance;

    @Expose
    @SerializedName("Per_server_Configs")
    public boolean perServerConfigs = false;
    @Expose
    @SerializedName("Brightness")
    private float brightness = 0.5f;

    public static GlobalConfig getInstance()
    {
        if (instance == null)
        {
            instance = new GlobalConfig();
        }
        return instance;
    }

    private GlobalConfig()
    {
        String fileName = Tools.createGlobalConfig();
        LiteLoader.getInstance().registerExposable(this, fileName);
        LiteLoader.getInstance().writeConfig(this);
    }

    public static void applyDefaults()
    {
        GlobalConfig c = getInstance();
        getGameSettings().gammaSetting = c.brightness;
        getGameSettings().saveOptions();
    }

    public static boolean perServerConfig()
    {
        return getInstance().perServerConfigs;
    }

    public static void setPerServerConfig(boolean b)
    {
        getInstance().perServerConfigs = b;
    }

    public static void saveSettings()
    {
        LiteLoader.getInstance().writeConfig(getInstance());
    }

    public static float getBrightness()
    {
        return getInstance().brightness;
    }

    public static void setBrightness(float f)
    {
        if (f < 1.0f)
        {
            getInstance().brightness = f;
            return;
        }
        getInstance().brightness = 0.5F;
    }
}
