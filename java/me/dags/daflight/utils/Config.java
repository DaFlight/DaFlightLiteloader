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
import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.player.DaPlayer;

/**
 * @author dags_ <dags@dags.me>
 */

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "daflight.json")
public class Config extends MinecraftGame implements Exposable
{
    /**
     * KeyBinds
     */
    @Expose
    @SerializedName("Fly_Up_Key")
    public String upKey = "SPACE";
    @Expose
    @SerializedName("Fly_Down_Key")
    public String downKey = "LSHIFT";
    @Expose
    @SerializedName("FullBright_Key")
    public String fullBrightKey = "MINUS";
    @Expose
    @SerializedName("Flight_Key")
    public String flyKey = "F";
    @Expose
    @SerializedName("Sprint_Key")
    public String sprintKey = "R";
    @Expose
    @SerializedName("SpeedMod_Key")
    public String speedKey = "X";
    @Expose
    @SerializedName("CineFlight_Key")
    public String cineFlyKey = "C";
    @Expose
    @SerializedName("SpeedUp_Key")
    public String speedUpKey = "RBRACKET";
    @Expose
    @SerializedName("SpeedDown_Key")
    public String speedDownKey = "LBRACKET";

    /**
     * ControlToggles
     */
    @Expose
    @SerializedName("Fly_Is_Toggle")
    public boolean flyIsToggle = true;
    @Expose
    @SerializedName("Sprint_Is_Toggle")
    public boolean sprintIsToggle = true;
    @Expose
    @SerializedName("SpeedMod_Is_Toggle")
    public boolean speedIsToggle = true;
    @Expose
    @SerializedName("FullBright_Is_Toggle")
    public boolean fullbrightIsToggle = true;

    /**
     * Preferences
     */
    @Expose
    @SerializedName("Disable_Mod")
    public boolean disabled = false;
    @Expose
    @SerializedName("View_Bobbing")
    public boolean viewBobbing = true;
    @Expose
    @SerializedName("3D_Flight")
    public boolean threeDFlight = false;
    @Expose
    @SerializedName("Show_Hud")
    public boolean showHud = true;

    /**
     * Parameters
     */
    @Expose
    @SerializedName("Brightness")
    public float brightness = 0.5f;
    @Expose
    @SerializedName("Fly_Speed")
    public double flySpeed = 0.1;
    @Expose
    @SerializedName("Fly_Speed_Multiplier")
    public double flySpeedMult = 5.0;
    @Expose
    @SerializedName("Fly_Smoothing_Factor")
    public double flySmoothing = 0.7;
    @Expose
    @SerializedName("Sprint_Speed")
    public double sprintSpeed = 0.1;
    @Expose
    @SerializedName("Sprint_Speed_Multiplier")
    public double sprintSpeedMult = 5.0;
    @Expose
    @SerializedName("Jump_Multiplier")
    public double jumpModifier = 0.9;
    @Expose
    @SerializedName("Left-Right_Modifier")
    public double lrModifier = 0.85;

    /**
     * HudElements
     */
    @Expose
    @SerializedName("Flight_Status")
    public String flightStatus = "f";
    @Expose
    @SerializedName("Cine_Flight_Status")
    public String cineFlightStatus = "c";
    @Expose
    @SerializedName("Sprint_Status")
    public String runStatus = "r";
    @Expose
    @SerializedName("FullBright_Status")
    public String fullBrightStatus = "fb";
    @Expose
    @SerializedName("Speed_Status")
    public String speedStatus = "*";
    @Expose
    @SerializedName("Status_Shadow")
    public boolean textShadow = true;

    private static Config instance;

    private Config()
    {
        LiteLoader.getInstance().registerExposable(this, "daflight.json");
    }

    private Config(String server)
    {
        String fileName = Tools.getOrCreateConfig("servers", server);
        LiteLoader.getInstance().registerExposable(this, fileName);
        saveSettings();
    }

    public static Config getInstance()
    {
        if (instance == null)
        {
            instance = new Config();
        }
        return instance;
    }

    public static void loadServerConfig()
    {
        instance = new Config(getServerData().serverIP.replace(":", "-"));
    }

    public static void reloadConfig()
    {
        instance = null;
        getInstance();
    }


    public static void saveSettings()
    {
        LiteLoader.getInstance().writeConfig(getInstance());
    }

    public static void applySettings()
    {
        DaPlayer daPlayer = LiteModDaFlight.DAPLAYER;
        Config c = getInstance();
        DaPlayer.KEY_BINDS.initSettings();
        daPlayer.flySpeed.setBaseSpeed(c.flySpeed);
        daPlayer.flySpeed.setMultiplier(c.flySpeedMult);
        daPlayer.sprintSpeed.setBaseSpeed(c.sprintSpeed);
        daPlayer.sprintSpeed.setMultiplier(c.sprintSpeedMult);
    }

    public static void applyDefaults()
    {
        Config c = getInstance();
        getGameSettings().viewBobbing = c.viewBobbing;
        getGameSettings().gammaSetting = c.brightness;
        getGameSettings().saveOptions();
    }

}
