package me.dags.daflight.minecraft.uielements;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;

/**
 * Super-simple implementation of a checkbox control
 * 
 * @author Adam Mummery-Smith
 */
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
