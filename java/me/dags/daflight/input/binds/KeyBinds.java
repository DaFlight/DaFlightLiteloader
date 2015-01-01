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

package me.dags.daflight.input.binds;

import me.dags.daflight.minecraft.MCGame;
import me.dags.daflight.utils.Config;
import org.lwjgl.input.Keyboard;

public class KeyBinds extends MCGame
{
    public static final MenuBind MENU_BINDING = new MenuBind("Quick Menu", Keyboard.KEY_F10, "DaFlight");

    public KeyBind[] binds;
    public KeyBind[] movementBinds;
    public KeyBind fullBright;
    public KeyBind speedUp;
    public KeyBind speedDown;
    public KeyBind flyUp;
    public KeyBind flyDown;
    public KeyBind enableFly;
    public KeyBind enableSprint;
    public KeyBind speedModifier;
    public KeyBind cineFlight;

    public KeyBind forward;
    public KeyBind backward;
    public KeyBind left;
    public KeyBind right;
    public KeyBind jump;

    public KeyBinds()
    {
        initSettings();
    }

    public void updateMovementKeys()
    {
        forward.setKey(getMinecraft().gameSettings.keyBindForward.getKeyCode());
        backward.setKey(getMinecraft().gameSettings.keyBindBack.getKeyCode());
        left.setKey(getMinecraft().gameSettings.keyBindLeft.getKeyCode());
        right.setKey(getMinecraft().gameSettings.keyBindRight.getKeyCode());
        jump.setKey(getMinecraft().gameSettings.keyBindJump.getKeyCode());
    }

    public void initSettings()
    {
        Config c = Config.getInstance();
        fullBright = new KeyBind("FullBright", c.fullBrightKey, BindType.FULLBRIGHT);
        fullBright.setToggle(c.fullbrightIsToggle);
        fullBright.setCanHold(true);
        speedUp = new KeyBind("Speed++", c.speedUpKey, BindType.SPEEDUP);
        speedUp.setToggle(false);
        speedDown = new KeyBind("Speed--", c.speedDownKey, BindType.SPEEDDOWN);
        speedDown.setToggle(false);

        flyUp = new KeyBind("FlyUp", c.upKey, BindType.UP, 0, 1, 0);
        flyUp.setToggle(false);
        flyDown = new KeyBind("FlyDown", c.downKey, BindType.DOWN, 0, -1, 0);
        flyDown.setToggle(false);

        enableFly = new KeyBind("Fly", c.flyKey, BindType.FLY);
        enableFly.setCanHold(true);
        enableFly.setToggle(c.flyIsToggle);
        enableSprint = new KeyBind("Sprint", c.sprintKey, BindType.SPRINT);
        enableSprint.setCanHold(true);
        enableSprint.setToggle(c.sprintIsToggle);
        speedModifier = new KeyBind("SpeedMod", c.speedKey, BindType.MODIFIER);
        speedModifier.setCanHold(true);
        speedModifier.setToggle(c.speedIsToggle);
        cineFlight = new KeyBind("CineFlight", c.cineFlyKey, BindType.CINEFLIGHT);

        forward = new KeyBind("Forward", "W", BindType.MOVE, 1, 0, 1);
        forward.setCanHold(true);
        forward.setToggle(false);
        backward = new KeyBind("Backward", "S", BindType.MOVE, -1, 0, -1);
        backward.setCanHold(true);
        backward.setToggle(false);
        left = new KeyBind("Left", "A", BindType.STRAFE, 1, 0, -1);
        left.setCanHold(true);
        left.setToggle(false);
        right = new KeyBind("Right", "D", BindType.STRAFE, -1, 0, 1);
        right.setCanHold(true);
        right.setToggle(false);
        jump = new KeyBind("Jump", "SPACE", BindType.GENERIC);
        jump.setCanHold(true);
        jump.setToggle(false);
        updateMovementKeys();

        binds = new KeyBind[]{enableFly, enableSprint, speedModifier, fullBright, cineFlight, flyUp, flyDown, speedUp, speedDown};
        movementBinds = new KeyBind[]{forward, backward, left, right, flyUp, flyDown};
    }
}
