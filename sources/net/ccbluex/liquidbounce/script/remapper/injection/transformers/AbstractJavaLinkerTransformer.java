package net.ccbluex.liquidbounce.script.remapper.injection.transformers;

import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.util.Constants;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/remapper/injection/transformers/AbstractJavaLinkerTransformer.class */
public class AbstractJavaLinkerTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("jdk.internal.dynalink.beans.AbstractJavaLinker")) {
            try {
                ClassNode classNode = ClassUtils.INSTANCE.toClassNode(basicClass);
                classNode.methods.forEach(methodNode -> {
                    String str = methodNode.name + methodNode.desc;
                    boolean z = true;
                    switch (str.hashCode()) {
                        case -2098129779:
                            if (str.equals("addMember(Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;Ljava/util/Map;)V")) {
                                z = false;
                                break;
                            }
                            break;
                        case -218897209:
                            if (str.equals("setPropertyGetter(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljdk/internal/dynalink/beans/GuardedInvocationComponent$ValidationType;)V")) {
                                z = true;
                                break;
                            }
                            break;
                        case 1744451451:
                            if (str.equals("addMember(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljava/util/Map;)V")) {
                                z = true;
                                break;
                            }
                            break;
                    }
                    switch (z) {
                        case false:
                            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", Constants.CLASS), new VarInsnNode(25, 1), new VarInsnNode(25, 2), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;)Ljava/lang/String;", false), new VarInsnNode(58, 1)));
                            return;
                        case true:
                            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", Constants.CLASS), new VarInsnNode(25, 1), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)));
                            return;
                        case true:
                            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", Constants.CLASS), new VarInsnNode(25, 1), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "setPropertyGetter", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)));
                            return;
                        default:
                            return;
                    }
                });
                return ClassUtils.INSTANCE.toBytes(classNode);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return basicClass;
    }
}
