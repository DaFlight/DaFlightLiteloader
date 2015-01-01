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

import me.dags.daflight.LiteModDaFlight;
import me.dags.daflight.input.binds.KeyBinds;
import me.dags.daflight.minecraft.guielements.*;
import me.dags.daflight.utils.Config;
import me.dags.daflight.utils.GlobalConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public abstract class ConfigGui extends GuiScreen
{
    protected final GuiScreen parent;
    protected final Config config = Config.getInstance();

    protected final List<UIElement> uiElements;
    protected boolean singleColumnMode;
    protected int columnHeight = 320;

    private ToggleButton disable;
    private ToggleButton flight3D;
    private ToggleButton showHud;
    private ToggleButton showToolTips;
    private ToggleButton perServer;

    private Slider flySpeed;
    private Slider flyMultiplier;
    private Slider flySmoothing;
    private Slider sprintSpeed;
    private Slider sprintMultiplier;
    private Slider jumpMultiplier;
    private Slider leftRightMultiplier;

    private BindButton flyKey;
    private BindButton cineKey;
    private BindButton flyUpKey;
    private BindButton flyDownKey;
    private BindButton sprintKey;
    private BindButton speedKey;
    private BindButton speedUpKey;
    private BindButton speedDownKey;
    private BindButton fullBrightKey;

    private ToggleButton flyHold;
    private ToggleButton sprintHold;
    private ToggleButton speedHold;
    private ToggleButton fbHold;

    private EntryBox flyStatus;
    private EntryBox cineStatus;
    private EntryBox sprintStatus;
    private EntryBox speedStatus;
    private EntryBox fullbrightStatus;

    protected ScrollBar scrollBar;

    protected boolean isScrollable;
    protected int maxYOffset = 0;
    protected int yOffset = 0;

    public ConfigGui()
    {
        uiElements = new ArrayList<UIElement>();
        init(MCGame.getScaledResolution().getScaledWidth(), MCGame.getScaledResolution().getScaledHeight());
        this.parent = null;
    }

    public ConfigGui(GuiScreen parent)
    {
        uiElements = new ArrayList<UIElement>();
        init(MCGame.getScaledResolution().getScaledWidth(), MCGame.getScaledResolution().getScaledHeight());
        this.parent = parent;
    }

    public void init(int displayWidth, int displayHeight)
    {
        init(displayWidth, displayHeight, false);
    }

    public void init(int displayWidth, int displayHeight, boolean singleColumn)
    {
        uiElements.clear();

        int columnWidth = 200;
        int middleSpacer = 10;
        int settingsWidth = singleColumn ? columnWidth : (columnWidth * 2) + middleSpacer;

        isScrollable = displayHeight < getContentHeight();
        maxYOffset = isScrollable ? displayHeight - getContentHeight() - 10 : 0;

        Logo logo = new Logo();

        int xLeft = (displayWidth - settingsWidth) / 2;
        int xRight = singleColumn ? xLeft : displayWidth - xLeft - columnWidth;
        int yTop = (isScrollable ? 5 : ((displayHeight - columnHeight) / 2)) + 35;

        // Left column
        int y = yTop;
        uiElements.add(logo.setXY((displayWidth / 2) - (logo.getWidth() / 2), yTop - 35));
        uiElements.add(new Label(xLeft, y, "Preferences").setColour(EnumChatFormatting.DARK_AQUA));
        uiElements.add(disable = new ToggleButton(1, xLeft, y += 11, 100, 20, "Disabled", config.disabled));
        uiElements.add(flight3D = new ToggleButton(1, xLeft + 102, y, 99, 20, "3DFlight", config.threeDFlight));
        uiElements.add(showHud = new ToggleButton(1, xLeft, y += 21, 100, 20, "ShowHUD", config.showHud));
        uiElements.add(showToolTips = new ToggleButton(1, xLeft + 102, y, 99, 20, "ToolTips", GlobalConfig.getInstance().configToolTips));
        uiElements.add(perServer = new ToggleButton(1, xLeft, y += 21, 200, 20, "PerServerConfigs", GlobalConfig.perServerConfig()));

        int w1 = 156;
        int w2 = columnWidth - w1 - 1;
        uiElements.add(new Label(xLeft, y += 31, "KeyBinds").setColour(EnumChatFormatting.DARK_AQUA));
        uiElements.add(flyKey = new BindButton(xLeft, y += 11, w1, 20, false, "FlyKey", config.flyKey));
        uiElements.add(flyHold = new ToggleButton(1, xLeft + w1 + 1, y, w2, 20, "Hold", config.flyIsToggle, new String[]{"Hold", "Toggle"}));
        uiElements.add(sprintKey = new BindButton(xLeft, y += 21, w1, 20, false, "Sprint", config.sprintKey));
        uiElements.add(sprintHold = new ToggleButton(1, xLeft + w1 + 1, y, w2, 20, "Hold", config.sprintIsToggle, new String[]{"Hold", "Toggle"}));
        uiElements.add(speedKey = new BindButton(xLeft, y += 21, w1, 20, false, "Speed", config.speedKey));
        uiElements.add(speedHold = new ToggleButton(1, xLeft + w1 + 1, y, w2, 20, "Hold", config.speedIsToggle, new String[]{"Hold", "Toggle"}));
        uiElements.add(fullBrightKey = new BindButton(xLeft, y += 21, w1, 20, false, "FullBright", config.fullBrightKey));
        uiElements.add(fbHold = new ToggleButton(1, xLeft + w1 + 1, y, w2, 20, "Hold", config.fullbrightIsToggle, new String[]{"Hold", "Toggle"}));
        uiElements.add(cineKey = new BindButton(xLeft, y += 21, w1, 20, false, "CineFly", config.cineFlyKey));
        uiElements.add(flyUpKey = new BindButton(xLeft, y += 21, w1, 20, false, "FlyUp", config.upKey));
        uiElements.add(flyDownKey = new BindButton(xLeft, y += 21, w1, 20, false, "FlyDown", config.downKey));
        uiElements.add(speedUpKey = new BindButton(xLeft, y += 21, w1, 20, false, "Speed++", config.speedUpKey));
        uiElements.add(speedDownKey = new BindButton(xLeft, y += 21, w1, 20, false, "Speed--", config.speedDownKey));

        // Right column
        y = singleColumn ? y + 31 : yTop;
        uiElements.add(new Label(xRight, y, "Settings").setColour(EnumChatFormatting.DARK_AQUA));
        uiElements.add(flySpeed = new Slider(1, xRight, y += 11, 0F, 5F, 200).setDisplayString("FlySpeed").setDefaultValue((float) config.flySpeed * 10));
        uiElements.add(flyMultiplier = new Slider(1, xRight, y += 21, 0F, 10F, 200).setDisplayString("FlySpeedMultiplier").setDefaultValue((float) config.flySpeedMult));
        uiElements.add(flySmoothing = new Slider(1, xRight, y += 21, 0F, 1F, 200).setDisplayString("FlySmoothing").setDefaultValue((float) config.flySmoothing));
        uiElements.add(sprintSpeed = new Slider(1, xRight, y += 21, 0F, 5F, 200).setDisplayString("SprintSpeed").setDefaultValue((float) config.sprintSpeed * 10));
        uiElements.add(sprintMultiplier = new Slider(1, xRight, y += 21, 0F, 10F, 200).setDisplayString("SprintSpeedMultiplier").setDefaultValue((float) config.sprintSpeedMult));
        uiElements.add(jumpMultiplier = new Slider(1, xRight, y += 21, 0F, 1F, 200).setDisplayString("JumpMultiplier").setDefaultValue((float) config.jumpModifier));
        uiElements.add(leftRightMultiplier = new Slider(1, xRight, y += 21, 0F, 1F, 200).setDisplayString("Left/RightMultiplier").setDefaultValue((float) config.lrModifier));

        uiElements.add(new Label(xRight, y += 31, "Statuses").setColour(EnumChatFormatting.DARK_AQUA));
        uiElements.add(flyStatus = new EntryBox(xRight, y += 11, 200, 17, "Flight", true).setString(config.flightStatus));
        uiElements.add(cineStatus = new EntryBox(xRight, y += 21, 200, 17, "CineFlight", true).setString(config.cineFlightStatus));
        uiElements.add(sprintStatus = new EntryBox(xRight, y += 21, 200, 17, "Sprint", true).setString(config.runStatus));
        uiElements.add(speedStatus = new EntryBox(xRight, y += 21, 200, 17, "Speed", true).setString(config.speedStatus));
        uiElements.add(fullbrightStatus = new EntryBox(xRight, y + 21, 200, 17, "Fullbright", true).setString(config.fullBrightStatus));

        uiElements.add(scrollBar = new ScrollBar(displayWidth - 4, 0, displayHeight, maxYOffset).setVisible(isScrollable));
        setToolTips();
    }

    public void setToolTips()
    {
        disable.addToolTip(new ToolTip("Enable/Disable", new String[]{"Quickly disable all features of the mod."}));
        flight3D.addToolTip(new ToolTip("3DFlight", new String[]{"Control your vertical movement by looking", "up/down whilst flying forwards."}));
        showHud.addToolTip(new ToolTip("ShowHud", new String[]{"Shows the status messages in the top left", "of the screen when mods are enabled."}));
        showToolTips.addToolTip(new ToolTip("ShowToolTips", new String[]{"Show tooltips like this in the settings menu."}));
        perServer.addToolTip(new ToolTip("PerServerConfigs", new String[]{"Creates a unique settings 'profile' for each", "server that you visit."}));
        ToolTip hold = new ToolTip("Hold/Toggle", new String[]{"Select whether this key should act as a", "toggle or if it should only be active whilst", "held down."});
        flyHold.addToolTip(hold);
        sprintHold.addToolTip(hold);
        speedHold.addToolTip(hold);
        fbHold.addToolTip(hold);
        flySpeed.addToolTip(new ToolTip("FlySpeed", new String[]{"Set the base fly speed."}));
        flyMultiplier.addToolTip(new ToolTip("FlySpeedMultiplier", new String[]{"Set the boosted fly speed (toggled", "by the speed key)"}));
        flySmoothing.addToolTip(new ToolTip("FlySmoothing", new String[]{"Set the amount of momentum to be applied", "when flying."}));
        sprintSpeed.addToolTip(new ToolTip("SprintSpeed", new String[]{"Set the base sprint speed."}));
        sprintMultiplier.addToolTip(new ToolTip("SprintSpeedMultiplier", new String[]{"Set the boosted sprint speed (toggled by", "the speed key)."}));
        jumpMultiplier.addToolTip(new ToolTip("JumpMultiplier", new String[]{"Adjust the amount of vertical speed to", "be applied when jumping with sprint mod", "on."}));
        leftRightMultiplier.addToolTip(new ToolTip("Left/RightMultiplier", new String[]{"Adjust the amount of side-to-side speed", "to be applied when strafing with sprint or", "fly mod on."}));
    }

    @Override
    public void drawScreen(int x, int y, float f)
    {
        for (UIElement e : uiElements)
            e.drawElement(x, y);
        if (showToolTips.getToggleState())
            for (UIElement e : uiElements)
                e.renderToolTips(x, y);
    }

    @Override
    public void mouseClicked(int x, int y, int f)
    {
        for (UIElement e : uiElements)
            e.mouseInput(x, y);
    }

    @Override
    public void mouseReleased(int x, int y, int f)
    {
        for (UIElement e : uiElements)
            e.mouseUnpressed(x, y);
    }

    public boolean keyInput(char keyChar, int keyId)
    {
        boolean exit = keyId == Keyboard.KEY_ESCAPE;
        for (UIElement e : uiElements)
            if (e.keyInput(keyChar, keyId))
                exit = false;
        return exit || KeyBinds.MENU_BINDING.isKeyPressed();
    }

    public void handleScrollbar(int mouseX, int mouseY)
    {
        if (this.isScrollable)
        {
            if (this.scrollBar.isActive())
            {
                int offset = scrollBar.getYOffset();
                if (offset != yOffset)
                {
                    this.yOffset = offset;
                    setScrollPos(this.yOffset);
                }
            }
        }
    }

    public void setScrollBarVisibility(boolean visible)
    {
        this.scrollBar.setVisible(visible);
    }

    private void setScrollPos(int pos)
    {
        for (UIElement e : this.uiElements)
            e.setYPos(pos);
    }

    public void applySizeChange(int width, int height)
    {
        if (singleColumnMode && width > 420)
        {
            init(width, height, singleColumnMode = false);
        }
        else if (!singleColumnMode && width < 420)
        {
            init(width, height, singleColumnMode = true);
        }
        else
        {
            init(width, height, singleColumnMode);
        }
    }

    public int getContentHeight()
    {
        return singleColumnMode ? columnHeight * 2 : columnHeight;
    }

    public void save()
    {
        GlobalConfig.getInstance().perServerConfigs = perServer.getToggleState();
        GlobalConfig.getInstance().configToolTips = showToolTips.getToggleState();

        config.disabled = disable.getToggleState();
        config.threeDFlight = flight3D.getToggleState();
        config.showHud = showHud.getToggleState();

        config.flySpeed = flySpeed.getValue() / 10F;
        config.flySpeedMult = flyMultiplier.getValue();
        config.flySmoothing = flySmoothing.getValue();
        config.sprintSpeed = sprintSpeed.getValue() / 10F;
        config.sprintSpeedMult = sprintMultiplier.getValue();
        config.jumpModifier = jumpMultiplier.getValue();
        config.lrModifier = leftRightMultiplier.getValue();

        config.flyKey = flyKey.getValue();
        config.flyIsToggle = flyHold.getToggleState();
        config.sprintKey = sprintKey.getValue();
        config.speedIsToggle = sprintHold.getToggleState();
        config.speedKey = speedKey.getValue();
        config.speedIsToggle = speedHold.getToggleState();
        config.fullBrightKey = fullBrightKey.getValue();
        config.fullbrightIsToggle = fbHold.getToggleState();
        config.cineFlyKey = cineKey.getValue();
        config.upKey = flyUpKey.getValue();
        config.downKey = flyDownKey.getValue();
        config.speedUpKey = speedUpKey.getValue();
        config.speedDownKey = speedDownKey.getValue();

        config.flightStatus = flyStatus.getValue();
        config.cineFlightStatus = cineStatus.getValue();
        config.runStatus = sprintStatus.getValue();
        config.speedStatus = speedStatus.getValue();
        config.fullBrightStatus = fullbrightStatus.getValue();

        Config.saveSettings();
        Config.applySettings();
        GlobalConfig.saveSettings();
        LiteModDaFlight.getHud().updateMsg();
    }
}
