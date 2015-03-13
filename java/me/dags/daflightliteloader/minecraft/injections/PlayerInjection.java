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
    private static final String DA_FLIER_DESC_SRG = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/World;Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/stats/StatFileWriter;)V";
    private static final String DA_FLIER_DESC_OBF = "(Lbsu;Laqu;Lcee;Ltz;)V";


    public PlayerInjection()
    {
        super("net.minecraft.client.multiplayer.PlayerControllerMP",
                "cem",
                "func_178892_a",
                "a",
                "(Lnet/minecraft/world/World;Lnet/minecraft/stats/StatFileWriter;)Lnet/minecraft/client/entity/EntityPlayerSP;",
                "(Laqu;Ltz;)Lcio;");
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
