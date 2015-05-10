package me.dags.daflightliteloader.minecraft.extended;

import com.mumfrey.liteloader.transformers.Obfuscated;
import me.dags.daflight.utils.FieldAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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

    @Obfuscated(value = {"func_178892_a", "a"})
    public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter fileWriter)
    {
        String[] fieldObf = new String[]{"b", "field_78774_b", "netClientHandler"};
        FieldAccess<NetHandlerPlayClient> netHandlerAccessor = new FieldAccess<NetHandlerPlayClient>(PlayerControllerMP.class, fieldObf);
        NetHandlerPlayClient netHandlerPlayClient = netHandlerAccessor.get(Minecraft.getMinecraft().playerController);
        return new EntityDaFlyer(Minecraft.getMinecraft(), worldIn, netHandlerPlayClient, fileWriter);
    }
}
