package me.dags.daflightliteloader.minecraft.transformers;

import me.dags.daflightliteloader.minecraft.injections.PlayerInjection;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author dags_ <dags@dags.me>
 */

public class ControllerMPTransformer implements IClassTransformer
{
    private final PlayerInjection playerInjection = new PlayerInjection();

    @Override
    public byte[] transform(String className, String transformedName, byte[] classBytes)
    {
        boolean isObf = !transformedName.startsWith("net.minecraft");
        return transformedName.equals(playerInjection.getClass(isObf)) ? transform(transformedName, classBytes, isObf) : classBytes;
    }

    public byte[] transform(String transformedName, byte[] classBytes, boolean isObf)
    {
        System.out.println("Transforming class: " + transformedName + "....") ;
        try
        {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(classBytes);
            reader.accept(node, 0);

            final String NAME = playerInjection.getMethod(isObf);
            final String DESC = playerInjection.getDesc(isObf);
            for (MethodNode mn : node.methods)
            {
                if (mn.name.equals(NAME) && mn.desc.equals(DESC))
                {
                    for (AbstractInsnNode n : mn.instructions.toArray())
                    {
                        if (n.getOpcode() == Opcodes.NEW)
                        {
                            mn.instructions.set(n, playerInjection.getInstNode());
                        }
                        else if (n.getOpcode() == Opcodes.INVOKESPECIAL)
                        {
                            mn.instructions.set(n, playerInjection.getMethodNode(isObf));
                        }
                    }
                    break;
                }
            }
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            writer.visitEnd();
            return writer.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return classBytes;
    }
}
