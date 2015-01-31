/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.dags.daflightliteloader.minecraft.transformers;

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;

/**
 * @author dags_ <dags@dags.me>
 */

public class MovementTransformer extends EventInjectionTransformer
{
    @Override
    protected void addEvents()
    {
        MethodHead injectionPoint = new MethodHead();

        Event onFall = Event.getOrCreate("onFall", true);
        MethodInfo fall = new MethodInfo(ObfTable.EntityPlayer, ObfTable.fall, "(FF)V");
        addEvent(onFall, fall, injectionPoint);
        onFall.addListener(new MethodInfo(ObfTable.listenerPath, "onFall"));

        Event onUpdateWalkingPlayer = Event.getOrCreate("onUpdateWalkingPlayer", true);
        MethodInfo updateWalkingPlayer = new MethodInfo(ObfTable.EntityPlayerSP, ObfTable.onUpdateWalkingPlayer, "()V");
        addEvent(onUpdateWalkingPlayer, updateWalkingPlayer, injectionPoint);
        onUpdateWalkingPlayer.addListener(new MethodInfo(ObfTable.listenerPath, "onUpdateWalkingPlayer"));

        Event onJump = Event.getOrCreate("onJump", true);
        MethodInfo jump = new MethodInfo(ObfTable.EntityPlayer, ObfTable.jump, "()V");
        addEvent(onJump, jump, injectionPoint);
        onJump.addListener(new MethodInfo(ObfTable.listenerPath, "onJump"));
    }
}
