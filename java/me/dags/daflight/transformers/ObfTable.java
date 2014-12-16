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

package me.dags.daflight.transformers;

import com.mumfrey.liteloader.core.runtime.Obf;

/**
 * @author dags_ <dags@dags.me>
 */

public class ObfTable extends Obf
{
    public static ObfTable AbstractClientPlayer = new ObfTable("net.minecraft.client.entity.AbstractClientPlayer", "cil");
    public static ObfTable getFOVModifier = new ObfTable("func_175156_o", "o", "getFovModifier");

    public static ObfTable EntityRenderer = new ObfTable("net.minecraft.client.renderer.EntityRenderer", "cji");
    public static ObfTable setupViewBobbing = new ObfTable("func_78475_f", "f", "setupViewBobbing");

    public static ObfTable EntityPlayerSP = new ObfTable("net.minecraft.client.entity.EntityPlayerSP", "cio");
    public static ObfTable onUpdateWalkingPlayer = new ObfTable("func_175161_p", "p", "onUpdateWalkingPlayer");

    public static ObfTable EntityPlayer = new ObfTable("net.minecraft.entity.player.EntityPlayer", "ahd");
    public static ObfTable fall = new ObfTable("func_180430_e", "e", "fall");

    /*
     * Can't seem to transform this :[
        public static ObfTable EntityLivingBase = new ObfTable("net.minecraft.entity.EntityLivingBase", "xm");
        public static ObfTable isOnLadder = new ObfTable("func_70617_f_", "j_", "isOnLadder");
    */

    public ObfTable(String seargeName, String obfName)
    {
        super(seargeName, obfName, seargeName);
    }

    public ObfTable(String seargeName, String obfName, String mcpName)
    {
        super(seargeName, obfName, mcpName);
    }

}
