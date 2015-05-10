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

package me.dags.daflightliteloader.minecraft.extended;

import com.mumfrey.liteloader.transformers.Obfuscated;
import me.dags.daflight.utils.FieldAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;

/**
 * @author dags_ <dags@dags.me>
 */

public class ControllerMPOverlay
{
    private static PlayerControllerMP __TARGET;

    @Obfuscated(value = {"func_147493_a", "a"})
    public EntityClientPlayerMP func_147493_a(World worldIn, StatFileWriter fileWriter)
    {
        String[] obf = new String[]{"b", "field_78774_b", "netClientHandler"};
        FieldAccess<NetHandlerPlayClient> netHandler = new FieldAccess<NetHandlerPlayClient>(PlayerControllerMP.class, obf);
        NetHandlerPlayClient netHandlerPlayClient = netHandler.get(Minecraft.getMinecraft().playerController);
        return new EntityDaFlyer(Minecraft.getMinecraft(), worldIn, Minecraft.getMinecraft().getSession(), netHandlerPlayClient, fileWriter);
    }
}
