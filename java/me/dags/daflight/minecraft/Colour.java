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

import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dags_ <dags@dags.me>
 */

public class Colour
{

    private static Map<String, EnumChatFormatting> formattingMap = new HashMap<String, EnumChatFormatting>();
    public static final String GREY = EnumChatFormatting.GRAY.toString();
    public static final String DARK_AQUA = EnumChatFormatting.DARK_AQUA.toString();
    public static final String DARK_PURPLE = EnumChatFormatting.DARK_PURPLE.toString();

    static
    {
        formattingMap.put("0", EnumChatFormatting.BLACK);
        formattingMap.put("1", EnumChatFormatting.DARK_BLUE);
        formattingMap.put("2", EnumChatFormatting.DARK_GREEN);
        formattingMap.put("3", EnumChatFormatting.DARK_AQUA);
        formattingMap.put("4", EnumChatFormatting.DARK_RED);
        formattingMap.put("5", EnumChatFormatting.DARK_PURPLE);
        formattingMap.put("6", EnumChatFormatting.GOLD);
        formattingMap.put("7", EnumChatFormatting.GRAY);
        formattingMap.put("8", EnumChatFormatting.DARK_GRAY);
        formattingMap.put("9", EnumChatFormatting.BLUE);
        formattingMap.put("a", EnumChatFormatting.GREEN);
        formattingMap.put("b", EnumChatFormatting.AQUA);
        formattingMap.put("c", EnumChatFormatting.RED);
        formattingMap.put("d", EnumChatFormatting.LIGHT_PURPLE);
        formattingMap.put("e", EnumChatFormatting.YELLOW);
        formattingMap.put("f", EnumChatFormatting.WHITE);
        formattingMap.put("k", EnumChatFormatting.OBFUSCATED);
        formattingMap.put("l", EnumChatFormatting.BOLD);
        formattingMap.put("m", EnumChatFormatting.STRIKETHROUGH);
        formattingMap.put("n", EnumChatFormatting.UNDERLINE);
        formattingMap.put("o", EnumChatFormatting.ITALIC);
        formattingMap.put("r", EnumChatFormatting.RESET);
    }

    public static String stripColour(String s)
    {
        for (String k : formattingMap.keySet())
        {
            String format = formattingMap.get(k).toString();
            if (s.contains(format))
            {
                s = s.replaceAll(format, "&" + k);
            }
        }
        return s;
    }

    public static String addColour(String s)
    {
        if (s.contains("&"))
        {
            StringBuilder sb = new StringBuilder();
            String[] split = s.split("&");
            for (String str : split)
            {
                String start = str.toLowerCase();
                if (str.length() > 1)
                {
                    start = start.substring(0, 1);
                }
                if (formattingMap.containsKey(start))
                {
                    str = str.replace(start, formattingMap.get(start).toString());
                }
                sb.append(str);
            }
            return sb.toString();
        }
        return s;
    }

    public static String getColouredString(String s)
    {
        String value = addColour(s);
        if (value.equals(s))
        {
            return EnumChatFormatting.RESET + s;
        }
        return value;
    }
}
