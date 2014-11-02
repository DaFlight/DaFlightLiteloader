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

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;

/**
 * @author dags_ <dags@dags.me>
 */

public class SoftFallTransformer extends EventInjectionTransformer
{

    @Override
    protected void addEvents()
    {
        MethodHead injectionPoint = new MethodHead();

        Event onQueuePacket = Event.getOrCreate("onPack", true);
        MethodInfo queue = new MethodInfo(ObfTable.NetHandlerPlayClient, ObfTable.addToSendQueue,"(Lnet/minecraft/network/Packet;)V");
        addEvent(onQueuePacket, queue, injectionPoint);
        onQueuePacket.addListener(new MethodInfo("me.dags.daflight.transformers.EventListener", "onQueuePacket"));

        Event onFall = Event.getOrCreate("onFall", true);
        MethodInfo fall = new MethodInfo(ObfTable.EntityPlayer, ObfTable.fall, "(FF)V");
        addEvent(onFall, fall, injectionPoint);
        onFall.addListener(new MethodInfo("me.dags.daflight.transformers.EventListener", "onFall"));
    }

}
