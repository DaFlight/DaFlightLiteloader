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

import org.lwjgl.input.Keyboard;

public class KeyBind
{
	private BindType type;
	private String name;
	private int keyId;
	private boolean canHold;
	private boolean isToggle;
	private boolean toggleState;
	private boolean press;
	private boolean held;
	private int modX = 0;
	private int modY = 0;
	private int modZ = 0;

	public KeyBind(int i, boolean keyIsToggle)
	{
		name = "";
		type = BindType.GENERIC;
		keyId = i;
		isToggle = keyIsToggle;
		canHold = false;
	}

	public KeyBind(int i)
	{
		name = "";
		keyId = i;
		isToggle = true;
		press = false;
	}

	public KeyBind(String controlName, String keyName, BindType bt)
	{
		name = controlName;
		type = bt;
		keyId = Keyboard.getKeyIndex(keyName);
		isToggle = true;
		press = false;
	}
	
	public KeyBind(String controlName, String keyName, BindType bt, int x, int y, int z)
	{
		name = controlName;
		type = bt;
		keyId = Keyboard.getKeyIndex(keyName);
		isToggle = true;
		press = false;
		modX = x;
		modY = y;
		modZ = z;
	}

	public void setName(String s)
	{
		name = s;
	}

	public void setKey(int i)
	{
		keyId = i;
	}

	public void setKeyFromString(String keyName)
	{
		keyId = Keyboard.getKeyIndex(keyName);
	}

	public void setCanHold(boolean b)
	{
		canHold = b;
	}

	public void setToggle(boolean b)
	{
		isToggle = b;
	}

	public void setState(boolean b)
	{
		toggleState = b;
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return keyId;
	}

	public String getKeyName()
	{
		if (keyId < 0)
		{
			return "NONE";
		}
		return Keyboard.getKeyName(keyId);
	}

	public BindType getType()
	{
		return type;
	}

	public boolean canHold()
	{
		return canHold;
	}

	public boolean isToggle()
	{
		return isToggle;
	}

	public boolean getToggleState()
	{
		return toggleState;
	}

	public boolean keyHeld()
	{
		if (isToggle)
		{
			return false;
		}
		if (keyId == 0)
		{
			return false;
		}
		return Keyboard.isCreated() && Keyboard.isKeyDown(keyId);
	}

	public boolean keyPressed()
	{
		if (keyId == 0)
		{
			return false;
		}
		if (Keyboard.isKeyDown(keyId))
		{
			if (press)
			{
				return false;
			}
			if (isToggle)
			{
				toggleState = !toggleState;
			}
			press = true;
			held = true;
			return true;
		}
		press = false;
		return false;
	}

	public boolean keyReleased()
	{
		if (!isToggle)
		{
			if (keyId == 0)
			{
				return false;
			}
			if (held && !keyHeld())
			{
				held = false;
				return true;
			}
		}
		return false;
	}
	
	public int getModX()
	{
		return modX;
	}
	
	public int getModY()
	{
		return modY;
	}
	
	public int getModZ()
	{
		return modZ;
	}

}
