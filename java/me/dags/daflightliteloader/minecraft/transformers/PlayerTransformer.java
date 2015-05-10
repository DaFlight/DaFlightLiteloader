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
import com.mumfrey.liteloader.transformers.event.inject.BeforeReturn;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;
import me.dags.daflightliteloader.minecraft.EventListener;

/**
 * @author dags_ <dags@dags.me>
 */

public class PlayerTransformer extends EventInjectionTransformer
{
    @Override
    protected void addEvents()
    {
        Event onSetupViewBobbing = Event.getOrCreate("onSetupViewBobbing", true);
        MethodInfo setupViewBobbing = new MethodInfo(ObfTable.EntityRenderer, ObfTable.setupViewBobbing, "(F)V");
        addEvent(onSetupViewBobbing, setupViewBobbing, new MethodHead());
        onSetupViewBobbing.addListener(new MethodInfo(EventListener.class.getCanonicalName(), "onSetupViewBobbing"));

        Event onUpdate = Event.getOrCreate("onUpdate", true);
        MethodInfo onEntityUpdate = new MethodInfo(ObfTable.EntityPlayer, ObfTable.onUpdate, "()V");
        addEvent(onUpdate, onEntityUpdate, new BeforeReturn());
        onUpdate.addListener(new MethodInfo(EventListener.class.getCanonicalName(), "onEntityUpdate"));
    }
}
