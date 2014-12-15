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

package me.dags.daflight.minecraft.uielements;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;

public class GuiCheck extends GuiCheckbox
{
    public String hoverMessageTrue;
    public String hoverMessageFalse;

    public GuiCheck(int controlId, int xPosition, int yPosition, String displayString)
    {
        super(controlId, xPosition, yPosition, displayString);
    }

    public String getHoverMessage()
    {
        return checked ? hoverMessageTrue : hoverMessageFalse;
    }

    public void setHoverMessages(String s1, String s2)
    {
        hoverMessageTrue = s1;
        hoverMessageFalse = s2;
    }
}
