package net.ccbluex.liquidbounce.injection.transformers;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import net.ccbluex.liquidbounce.utils.ASMUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/transformers/OptimizeTransformer.class */
public class OptimizeTransformer implements IClassTransformer {
    private static final HashMap<String, String> transformMap = new HashMap<>();

    static {
        addTransform("net.minecraft.util.EnumFacing", "cq", "facings");
        addTransform("net.minecraft.util.EnumChatFormatting", "a", "chatFormatting");
        addTransform("net.minecraft.util.EnumParticleTypes", "cy", "particleTypes");
        addTransform("net.minecraft.util.EnumWorldBlockLayer", "adf", "worldBlockLayers");
    }

    private static void addTransform(String mcpName, String notchName, String targetName) {
        transformMap.put(mcpName, targetName);
        transformMap.put(notchName, targetName);
    }

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!name.startsWith("net.ccbluex") && !name.startsWith("kotlin") && basicClass != null && !transformMap.containsKey(transformedName)) {
            try {
                ClassNode classNode = ASMUtils.INSTANCE.toClassNode(basicClass);
                AtomicBoolean changed = new AtomicBoolean(false);
                classNode.methods.forEach(methodNode -> {
                    for (int i = 0; i < methodNode.instructions.size(); i++) {
                        MethodInsnNode methodInsnNode = methodNode.instructions.get(i);
                        if (methodInsnNode instanceof MethodInsnNode) {
                            MethodInsnNode min = methodInsnNode;
                            if (min.getOpcode() == 184 && min.name.equals("values")) {
                                String owner = min.owner.replaceAll("/", ".");
                                if (transformMap.containsKey(owner)) {
                                    changed.set(true);
                                    min.owner = "net/ccbluex/liquidbounce/injection/access/StaticStorage";
                                    min.name = transformMap.get(owner);
                                }
                            }
                        }
                    }
                });
                if (changed.get()) {
                    return ASMUtils.INSTANCE.toBytes(classNode);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return basicClass;
    }
}
