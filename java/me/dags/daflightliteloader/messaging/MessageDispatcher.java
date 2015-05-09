package me.dags.daflightliteloader.messaging;

import com.mumfrey.liteloader.core.ClientPluginChannels;
import com.mumfrey.liteloader.core.PluginChannels;
import io.netty.buffer.Unpooled;
import me.dags.daflightapi.messaging.PluginMessageDispatcher;
import net.minecraft.network.PacketBuffer;

/**
 * @author dags_ <dags@dags.me>
 */

public class MessageDispatcher implements PluginMessageDispatcher
{
    @Override
    public void dispatchMessage(byte[] data)
    {
        PacketBuffer outPacket = new PacketBuffer(Unpooled.copiedBuffer(data));
        ClientPluginChannels.sendMessage("DaFlight", outPacket, PluginChannels.ChannelPolicy.DISPATCH_ALWAYS);
    }
}
