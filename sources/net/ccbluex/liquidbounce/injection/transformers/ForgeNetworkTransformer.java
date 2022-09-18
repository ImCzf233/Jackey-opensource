package net.ccbluex.liquidbounce.injection.transformers;

import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer.class */
public class ForgeNetworkTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
            try {
                ClassNode classNode = ClassUtils.INSTANCE.toClassNode(basicClass);
                classNode.methods.stream().filter(methodNode -> {
                    return methodNode.name.equals("handleVanilla");
                }).forEach(methodNode2 -> {
                    AbstractInsnNode labelNode = new LabelNode();
                    methodNode2.instructions.insertBefore(methodNode2.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(3), new InsnNode(172), labelNode));
                });
                return ClassUtils.INSTANCE.toBytes(classNode);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        if (name.equals("net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler")) {
            try {
                ClassNode classNode2 = ClassUtils.INSTANCE.toClassNode(basicClass);
                classNode2.methods.stream().filter(method -> {
                    return method.name.equals("channelRead0");
                }).forEach(methodNode3 -> {
                    AbstractInsnNode labelNode = new LabelNode();
                    methodNode3.instructions.insertBefore(methodNode3.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(177), labelNode));
                });
                return ClassUtils.INSTANCE.toBytes(classNode2);
            } catch (Throwable throwable2) {
                throwable2.printStackTrace();
            }
        }
        return basicClass;
    }

    public static boolean returnMethod() {
        return AntiForge.enabled && AntiForge.blockFML && !Minecraft.func_71410_x().func_71387_A();
    }
}
