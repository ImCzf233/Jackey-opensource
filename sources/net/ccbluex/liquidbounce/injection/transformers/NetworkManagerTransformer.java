package net.ccbluex.liquidbounce.injection.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/transformers/NetworkManagerTransformer.class */
public class NetworkManagerTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.ccbluex.liquidbounce.injection.forge.mixins.network.MixinNetworkManagerProxy")) {
            try {
                ClassReader reader = new ClassReader(basicClass);
                ClassNode classNode = new ClassNode();
                reader.accept(classNode, 0);
                classNode.methods.stream().filter(methodNode -> {
                    return methodNode.name.equals("createNetworkManagerAndConnect");
                }).forEach(methodNode2 -> {
                    for (int i = 0; i < methodNode2.instructions.size(); i++) {
                        TypeInsnNode typeInsnNode = methodNode2.instructions.get(i);
                        if (typeInsnNode instanceof TypeInsnNode) {
                            TypeInsnNode tin = typeInsnNode;
                            if (tin.desc.equals("net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetworkManagerProxy$1")) {
                                typeInsnNode.desc = "net/minecraft/network/NetworkManager$5";
                            }
                        } else if (typeInsnNode instanceof MethodInsnNode) {
                            MethodInsnNode min = (MethodInsnNode) typeInsnNode;
                            if (min.owner.equals("net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetworkManagerProxy$1") && min.name.equals("<init>")) {
                                min.owner = "net/minecraft/network/NetworkManager$5";
                            }
                        }
                    }
                });
                ClassWriter writer = new ClassWriter(1);
                classNode.accept(writer);
                return writer.toByteArray();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return basicClass;
    }
}
