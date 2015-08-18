package me.dags.daflightliteloader.minecraft.transformers;

import me.dags.daflight.minecraft.DFTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class MCStartTransformer extends DFTransformer
{
    // Minecraft stuff
    private final String obfClass = "bsu";
    private final  String srgClass = "net.minecraft.client.Minecraft";
    private final  String obfMethod = "aj";
    private final  String srgMethod = "startGame";
    private final  String obfDescriptor = "()V";
    private final  String srgDescriptor = "()V";
    // RenderGlobal
    private final String obfRenderGlobal = "ckn";
    private final String srgRenderGlobal = "net/minecraft/client/renderer/RenderGlobal";
    //DF Stuff
    private final  String renderGlobalClass = "me/dags/daflightliteloader/minecraft/extended/DFRenderGlobal";
    private final  String obfRenderGlobalDesc = "(Lbsu;)V";
    private final  String srgRenderGlobalDesc = "(Lnet/minecraft/client/Minecraft;)V";

    @Override
    public boolean matches(String transformedName, boolean isObf)
    {
        return transformedName.equals(getClass(isObf));
    }

    @Override
    public byte[] transform(String transformedName, byte[] classBytes, boolean isObf)
    {
        try
        {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(classBytes);
            reader.accept(node, 0);

            final String NAME = getMethod(isObf);
            final String DESC = getMethodDesc(isObf);

            final TypeInsnNode matchType = getTypeMatch(isObf);
            final MethodInsnNode matchMethod = getMethodMatch(isObf);

            for (MethodNode mn : node.methods)
            {
                if (mn.name.equals(NAME) && mn.desc.equals(DESC))
                {
                    for (AbstractInsnNode n : mn.instructions.toArray())
                    {
                        if (n instanceof TypeInsnNode)
                        {
                            TypeInsnNode tn = (TypeInsnNode) n;
                            if (tn.desc.equals(matchType.desc))
                            {
                                mn.instructions.set(n, getInstNode());
                            }
                        }
                        else if (n instanceof MethodInsnNode)
                        {
                            MethodInsnNode min = (MethodInsnNode) n;
                            if (min.owner.equals(matchMethod.owner) && min.name.equals(matchMethod.name))
                            {
                                mn.instructions.set(n, getMethodNode(isObf));
                            }
                        }
                    }
                    break;
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);

            return writer.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return classBytes;
    }

    private TypeInsnNode getTypeMatch(boolean isObf)
    {
        return new TypeInsnNode(Opcodes.NEW, isObf ? obfRenderGlobal : srgRenderGlobal);
    }

    private MethodInsnNode getMethodMatch(boolean isObf)
    {
        return new MethodInsnNode(Opcodes.INVOKESPECIAL, isObf ? obfRenderGlobal : srgRenderGlobal, "<init>", getTargetDesc(isObf), false);
    }

    private String getClass(boolean isObf)
    {
        return isObf ? obfClass : srgClass;
    }

    private String getMethod(boolean isObf)
    {
        return isObf ? obfMethod : srgMethod;
    }

    private String getMethodDesc(boolean isObf)
    {
        return isObf ? obfDescriptor : srgDescriptor;
    }

    private TypeInsnNode getInstNode()
    {
        return new TypeInsnNode(Opcodes.NEW, renderGlobalClass);
    }

    private MethodInsnNode getMethodNode(boolean isObf)
    {
        return new MethodInsnNode(Opcodes.INVOKESPECIAL, renderGlobalClass, "<init>", getTargetDesc(isObf), false);
    }

    private String getTargetDesc(boolean isObf)
    {
        return isObf ? obfRenderGlobalDesc : srgRenderGlobalDesc;
    }
}
