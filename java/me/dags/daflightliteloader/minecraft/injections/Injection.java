package me.dags.daflightliteloader.minecraft.injections;

/**
 * @author dags_ <dags@dags.me>
 */

public abstract class Injection
{
    public final String srgClass;
    public final String obfClass;
    public final String srgMethod;
    public final String obfMethod;
    public final String srgDescriptor;
    public final String obfDescriptor;

    public Injection(String className, String obfName, String methodName, String obfMethodName, String descriptor, String obfDescript)
    {
        srgClass = className;
        obfClass = obfName;
        srgMethod = methodName;
        obfMethod = obfMethodName;
        srgDescriptor = descriptor;
        obfDescriptor = obfDescript;
    }

    public String getClass(boolean isObf)
    {
        return isObf ? obfClass : srgClass;
    }

    public String getMethod(boolean isObf)
    {
        return isObf ? obfMethod : srgMethod;
    }

    public String getDesc(boolean isObf)
    {
        return isObf ? obfDescriptor : srgDescriptor;
    }
}
