package me.dags.daflightliteloader.minecraft.injections;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

/**
 * @author dags_ <dags@dags.me>
 */

public class PlayerInjection extends Injection
{
    private static final String DA_FLIER = "me/dags/daflightliteloader/minecraft/extended/EntityDaFlier";
    private static final String DA_FLIER_DESC_SRG = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/World;Lnet/minecraft/util/Session;Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/stats/StatFileWriter;)V";
    private static final String DA_FLIER_DESC_OBF = "(Lbao;Lahb;Lbbs;Lbjb;Lpq;)V";


    public PlayerInjection()
    {
        super("net.minecraft.client.multiplayer.PlayerControllerMP",
                "bje",
                "func_147493_a",
                "a",
                "(Lnet/minecraft/world/World;Lnet/minecraft/stats/StatFileWriter;)Lnet/minecraft/client/entity/EntityClientPlayerMP;",
                "(Lahb;Lpq;)Lbjk;");
    }

    public TypeInsnNode getInstNode()
    {
        return new TypeInsnNode(Opcodes.NEW, DA_FLIER);
    }

    public MethodInsnNode getMethodNode(boolean isObf)
    {
        return new MethodInsnNode(Opcodes.INVOKESPECIAL, DA_FLIER, "<init>", constDesc(isObf), false);
    }

    private String constDesc(boolean isObf)
    {
        return isObf ? DA_FLIER_DESC_OBF : DA_FLIER_DESC_SRG;
    }
}
