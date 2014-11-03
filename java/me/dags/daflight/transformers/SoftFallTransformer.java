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

        Event onFall = Event.getOrCreate("onFall", true);
        MethodInfo fall = new MethodInfo(ObfTable.EntityPlayer, ObfTable.fall, "(FF)V");
        addEvent(onFall, fall, injectionPoint);
        onFall.addListener(new MethodInfo("me.dags.daflight.transformers.EventListener", "onFall"));

        Event onGroundCheck = Event.getOrCreate("onGroundCheck", true);
        MethodInfo groundCheck = new MethodInfo(ObfTable.EntityPlayerSP, ObfTable.onGroundCheck, "()Z");
        addEvent(onGroundCheck, groundCheck, injectionPoint);
        onGroundCheck.addListener(new MethodInfo("me.dags.daflight.transformers.EventListener", "onGround"));

        Event onIsSneaking = Event.getOrCreate("onIsSneaking", true);
        MethodInfo isSneaking = new MethodInfo(ObfTable.EntityPlayerSP, ObfTable.isSneaking, "()Z");
        addEvent(onIsSneaking, isSneaking, injectionPoint);
        onIsSneaking.addListener(new MethodInfo("me.dags.daflight.transformers.EventListener", "isSneaking"));

        Event onJump = Event.getOrCreate("onJump", true);
        MethodInfo jump = new MethodInfo(ObfTable.EntityPlayer, ObfTable.onJump, "()V");
        addEvent(onJump, jump, injectionPoint);
        onJump.addListener(new MethodInfo("me.dags.daflight.transformers.EventListener", "onJump"));
    }

}
